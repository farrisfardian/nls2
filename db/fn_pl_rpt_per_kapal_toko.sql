-- Function: fn_pl_rpt_per_kapal_toko(integer, integer)

-- DROP FUNCTION fn_pl_rpt_per_kapal_toko(integer, integer);

CREATE OR REPLACE FUNCTION fn_pl_rpt_per_kapal_toko(integer, integer)
  RETURNS SETOF record AS
$BODY$
declare
	v_kapal	alias for $1;
	v_toko	alias for $2;
	rcd	record;
begin
for rcd in	
	select coalesce(kt.nama,'') kota_tujuan, coalesce(k.nama,'') kondisi,
	coalesce(t.nama,'') customer, coalesce(kp.nama,'') kapal, kb.tgl_berangkat, 
	fn_tanggal_ind(kb.tgl_berangkat) tgl_ind, coalesce(m.nama,'') merk, coalesce(t.alamat,'') alamat_customer, 
	coalesce(kn.nomor,'') nomor_kontainer, coalesce(e.nama,'') emkl, 
	sj.id, sj.tanggal, coalesce(pengirim.nama,'') pengirim, coalesce(ss.coli,0) coli, 
	coalesce(i.nama,'')
-- 	||case when trim(coalesce(d.spesifikasi,''))<>'' then E'\n'||d.spesifikasi else '' end  
-- 	||case when kon.kont=1 then '' else E'\n'||fn_ket_kontainer_pisah(d.id, ss.id_stuffing) end jenis_barang, 
	||case when trim(coalesce(d.spesifikasi,''))<>'' then E'\n'||d.spesifikasi else '' end
	||case when trim(coalesce(d.catatan,''))<>'' then E'\n'||'<style isBold="true">'||d.catatan||'</style>' else '' end
	||fn_ket_kontainer_pisah(ss.id_sj_detail,ss.id_stuffing) as  jenis_barang,
	coalesce(d.p,0) p, coalesce(d.l,0) l, coalesce(d.t,0) t, 
	d.paket, case when d.paket=true then 'Paket' 
		when d.paket=false and d.fix_volume > 0 then ''
		else case when d.p>0 then d.p::varchar else '' end|| case when d.l>0 then ' x ' else '' end ||
		     case when d.l>0 then d.l::varchar else '' end || case when d.t>0 then ' x ' else '' end ||
		     case when d.t>0 then d.t::varchar else '' end
		end ,     
	coalesce(d.coli,0) * case when not coalesce(d.paket, false) and d.fix_volume>0 then d.fix_volume else
		case 	when d.p>0 and coalesce(d.l,0)=0 and coalesce(d.t,0)=0 then d.p
			when d.p>0 and d.l>0 and coalesce(d.t,0)=0 then d.p*d.l/1000
			when d.p>0 and d.l>0 and d.t>0 then d.p*d.l*d.t/1000000 else 0 end end::numeric(18,4) as kubikasi,
	d.fix_volume, tot.total_coli, pisah.pisah		
	from t_sj_stuffing ss 
	inner join t_surat_jalan_detail d on d.id=ss.id_sj_detail
	inner join t_surat_jalan sj on d.id_surat_jalan=sj.id
	inner join t_stuffing st on st.id=ss.id_stuffing
	inner join m_kontainer kn on kn.id=st.id_kontainer
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
		group by ss.id_sj_detail, sj.id) x group by id
	) pisah on pisah.id = sj.id
	where kb.id=v_kapal
	and m.id_toko=v_toko
	order by coalesce(k.nama,'') , coalesce(kp.nama,''),
	kb.tgl_berangkat, t.nama, coalesce(m.nama,''), ss.id_stuffing, sj.tanggal, sj.id, d.id
loop
	return next rcd;
END LOOP;	
/*
select * from fn_pl_rpt_per_kapal_toko(15,159) as (kota_tujuan varchar, kondisi varchar, 
customer varchar, kapal varchar, tgl_berangkat date, tgl_ind varchar, merk varchar, alamat varchar, nomor_kontainer varchar, emkl varchar, 
id integer, tanggal date, pengirim varchar, coli integer, jenis_barang text, p double precision, l double precision, 
t double precision, paket boolean , ukuran text, kubikasi numeric, fix_volume double precision, total_coli_sj bigint, pisah boolean)
update t_surat_jalan  set id_kondisi =1
select * from t_surat_jalan j 
inner join m_merk m on m.id=j.id_merk  

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
ALTER FUNCTION fn_pl_rpt_per_kapal_toko(integer, integer)
  OWNER TO postgres;
