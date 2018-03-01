-- Function: fn_pl_rpt_per_kapal_merk_toko_pisah_emkl(integer, integer[])

-- DROP FUNCTION fn_pl_rpt_per_kapal_merk_toko_pisah_emkl(integer, integer[]);

CREATE OR REPLACE FUNCTION fn_pl_rpt_per_kapal_merk_toko_pisah_emkl(
    integer,
    integer[])
  RETURNS SETOF record AS
$BODY$

declare
	v_kapal	alias for $1;
	v_merk	alias for $2;
	v_toko varchar;
	rcd	record;
begin
select gabung(distinct t.nama)::varchar into v_toko
from m_toko t 
inner join m_merk m on m.id_toko=t.id
where m.id in(select unnest(v_merk));

for rcd in	
	select coalesce(kt.nama,'') kota_tujuan, coalesce(k.nama,'') kondisi,
	v_toko, --coalesce(t.nama,'') customer, 
	coalesce(kp.nama,'') kapal, kb.tgl_berangkat, 
	fn_tanggal_ind(kb.tgl_berangkat) tgl_ind, (coalesce(t.nama,'')||'/ ' ||coalesce(m.nama,''))::varchar merk, coalesce(t.alamat,'') alamat_customer, 
	coalesce(st.no_kontainer,'') nomor_kontainer, coalesce(e.nama,'') emkl, 
	sj.id, sj.tanggal, coalesce(pengirim.nama,'') pengirim, coalesce(ss.coli,0) coli, 
	coalesce(i.nama,'')
-- 	||case when trim(coalesce(d.spesifikasi,''))<>'' then E'\n'||d.spesifikasi else '' end  
-- 	||case when kon.kont=1 then '' else E'\n'||fn_ket_kontainer_pisah(d.id, ss.id_stuffing) end jenis_barang, 
	||case when trim(coalesce(d.spesifikasi,''))<>'' then E'\n'||coalesce(d.spesifikasi,'') else '' end
	||case when trim(coalesce(d.catatan,''))<>'' then E'\n'||'<style isBold="true">'||coalesce(d.catatan,'')||'</style>' else '' end
	||coalesce(fn_ket_kontainer_pisah(ss.id_sj_detail,ss.id_stuffing),'') as  jenis_barang,
	coalesce(d.p,0) p, coalesce(d.l,0) l, coalesce(d.t,0) t, 
	coalesce(d.paket,false) as paket, 
	case when d.paket=true then 'Paket' 
		when d.paket=false and (d.fix_volume > 0 or (coalesce(d.p,0)=0 and coalesce(d.l,0)=0 and coalesce(d.t,0)=0)) then '0'
		else case when coalesce(d.p,0)>0 and coalesce(d.l,0)=0 and coalesce(d.t,0)=0 then coalesce(d.p,0)::numeric(18,3)::varchar else 
			     case when d.p>0 then d.p::varchar else '' end|| case when d.l>0 then ' x ' else '' end ||
			     case when d.l>0 then d.l::varchar else '' end || case when d.t>0 then ' x ' else '' end ||
			     case when d.t>0 then d.t::varchar else '' end
		     end
		end ukuran,   
	
	case when not coalesce(d.paket, false) and d.fix_volume>0 then d.fix_volume else
	coalesce(ss.coli,0) * 
		case 	when d.p>0 and coalesce(d.l,0)=0 and coalesce(d.t,0)=0 then d.p
			when d.p>0 and d.l>0 and coalesce(d.t,0)=0 then d.p*d.l/1000
			when d.p>0 and d.l>0 and d.t>0 then d.p*d.l*d.t/1000000 else 0 end end::numeric(18,4) as kubikasi,
	d.fix_volume, tot.total_coli||' Coli '||fn_ket_kontainer_pisah_sj(sj.id,ss.id_stuffing), 
	coalesce(sj_pisah.pisah, false) or coalesce(det_pisah.pisah, false), sk.nama as satuan_kirim, coalesce(kp_kn.jml_kontainer,0) jml_kontainer		
	from t_sj_stuffing ss 
	inner join t_surat_jalan_detail d on d.id=ss.id_sj_detail
	inner join t_surat_jalan sj on d.id_surat_jalan=sj.id
	inner join t_stuffing st on st.id=ss.id_stuffing
	inner join m_satuan_kirim sk on sk.id=st.id_satuan_kirim
	--inner join m_kontainer kn on kn.id=st.id_kontainer
	inner join m_kota kt on kt.id=st.id_kota
	inner join m_merk m on m.id=sj.id_merk
	inner join m_toko t on t.id=m.id_toko
	inner join m_item i on i.id=d.id_item
	left join m_emkl e on e.id=st.id_emkl
	left join m_kondisi k on k.id=sj.id_kondisi
	left join m_toko pengirim on pengirim.id=sj.id_toko
	left join t_kapal_berangkat kb on kb.id=st.id_kapal_berangkat
	left join m_kapal kp on kp.id=kb.id_kapal
	left join (
		select s.id_sj_detail, count(s2.id_sj_detail) kont 
		from t_sj_stuffing s 
		left join t_sj_stuffing s2 on s.id_sj_detail=s2.id_sj_detail
-- 		where s.id_stuffing=7
		group by s.id_sj_detail
	) kon on kon.id_sj_detail=d.id
	left join (
		select id_surat_jalan, sum(coli) as total_coli from t_surat_jalan_detail group by id_surat_jalan
	) tot on tot.id_surat_jalan = sj.id	
	left join (		
		select id, bool_or(is_pisah) as pisah from (
			select ss.id_sj_detail, sj.id, case when count(*) > 1 then true else false end as is_pisah  
			from t_sj_stuffing  ss
			inner join t_surat_jalan_detail d on d.id=ss.id_sj_detail
			inner join t_surat_jalan sj on d.id_surat_jalan=sj.id
			where sj.id_merk in(select unnest(v_merk))
			group by ss.id_sj_detail, sj.id
		) x group by id
	) det_pisah on det_pisah.id = sj.id
	left join (		
		select id, bool_or(is_pisah) as pisah from (
			select sj.id, case when count(distinct ss.id_stuffing) > 1 then true else false end as is_pisah  
			from t_sj_stuffing  ss
			inner join t_surat_jalan_detail d on d.id=ss.id_sj_detail
			inner join t_surat_jalan sj on d.id_surat_jalan=sj.id
			where sj.id_merk in(select unnest(v_merk))
			group by sj.id
		) x group by id
	) sj_pisah on sj_pisah.id = sj.id
	left join (
		select * from fn_pl_merk_jumlah_kontainer_per_kapal(v_kapal, v_merk) as (id_merk integer, jml_kontainer bigint)
	)kp_kn on kp_kn.id_merk=sj.id_merk
	where case when v_kapal=0 then st.id_kapal_berangkat is null else st.id_kapal_berangkat=v_kapal end and coalesce(ss.coli,0) > 0
	and m.id in (select unnest(v_merk))
	order by coalesce(k.nama,'') , coalesce(kp.nama,''),
	-- kb.tgl_berangkat, 
    st.id_satuan_kirim,
--  sk.nama, 
    coalesce(e.nama,''), t.nama, coalesce(m.nama,''), ss.id_stuffing, sj.tanggal, sj.id, d.id
loop
	return next rcd;
END LOOP;	
/*
select x.*
from(
select kota_tujuan, kondisi, 
customer, kapal, tgl_berangkat, tgl_ind, merk, alamat, nomor_kontainer, emkl, 
id, tanggal, pengirim, coli, jenis_barang, p, l, 
t, paket, case when ukuran not ilike '%x%' and ukuran not ilike '%paket%' and ukuran not ilike '' then round(ukuran::numeric(12,4),3)::text else ukuran end as ukuran, 
kubikasi, fix_volume, total_coli_sj, pisah, jml_kontainer 
from fn_pl_rpt_per_kapal_merk_toko_pisah_emkl(22, ARRAY[113,227]) as (kota_tujuan varchar, kondisi varchar, 
customer varchar, kapal varchar, tgl_berangkat date, tgl_ind varchar, merk varchar, alamat varchar, nomor_kontainer varchar, emkl varchar, 
id integer, tanggal date, pengirim varchar, coli integer, jenis_barang text, p double precision, l double precision, 
t double precision, paket boolean , ukuran text, kubikasi numeric, fix_volume double precision, total_coli_sj text, pisah boolean, satuan_kirim varchar, 
jml_kontainer bigint)
)x
left join (
	select * from fn_pl_merk_jumlah_kontainer(22, array[237]) as (id_merk integer, jml_kontainer bigint)
)k on k.id_merk=

update t_surat_jalan  set id_kondisi =1
select * from t_surat_jalan j 
inner join m_merk m on m.id=j.id_merk  

select * from m_Merk where
select ss.id_sj_detail, sj.id as id_sj, t.id as id_toko, kp.id as id_kapal, i.nama, case when count(*) > 1 then true else false end as is_pisah
		from t_sj_stuffing  ss
		inner join t_surat_jalan_detail d on d.id=ss.id_sj_detail
		inner join t_surat_jalan sj on d.id_surat_jalan=sj.id
		inner join m_toko t on sj.id_toko = t.id
		inner join t_stuffing st on st.id=ss.id_stuffing
		inner join m_item i on d.id_item = i.id
		left join t_kapal_berangkat kb on kb.id=st.id_kapal_berangkat
		left join m_kapal kp on kp.id=kb.id_kapal
		group by ss.id_sj_detail, sj.id, t.id, kp.id, i.nama
		having count(*)>1
		
select * from t_kapal_berangkat kb 
join m_kota k on kb.id_kota = k.id 
join m_kapal kp on kb.id_kapal = kp.id
where id_kapal  = 13 

(select id, coli from t_sj_stuffing  where id_sj_detail  = 3408)  

select id, coli from t_sj_stuffing  where id_sj_detail  = 3408
select * from t_surat_jalan_detail

select * from t_surat_jalan sj 
*/

end

$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION fn_pl_rpt_per_kapal_merk_toko_pisah_emkl(integer, integer[])
  OWNER TO postgres;
