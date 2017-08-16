-- Function: fn_pl_rpt_per_stuffing(integer)

-- DROP FUNCTION fn_pl_rpt_per_stuffing(integer);

CREATE OR REPLACE FUNCTION fn_pl_rpt_per_stuffing(integer)
  RETURNS SETOF record AS
$BODY$
declare
	v_id	alias for $1;
	rcd	record;
begin
for rcd in	
	select coalesce(kt.nama,'') kota_tujuan, coalesce(k.nama,'') kondisi,
	coalesce(t.nama,'') customer, coalesce(kp.nama,'') kapal, kb.tgl_berangkat, 
	fn_tanggal_ind(kb.tgl_berangkat) tgl_ind, coalesce(m.nama,'') merk, coalesce(t.alamat,'') alamat_customer, 
	coalesce(kn.nomor,'') nomor_kontainer, coalesce(e.nama,'') emkl, 
	sj.id, sj.tanggal, coalesce(pengirim.nama,'') pengirim, coalesce(ss.coli,0) coli, 
	coalesce(i.nama,'')
-- 	||case when trim(coalesce(d.spesifikasi,''))<>'' then '<br>'||d.spesifikasi else '' end
-- 	||case when trim(coalesce(d.catatan,''))<>'' then '<br>'||'<b>'||d.catatan||'</b>' else '' end
	||case when trim(coalesce(d.spesifikasi,''))<>'' then E'\n'||d.spesifikasi else '' end
	||case when trim(coalesce(d.catatan,''))<>'' then E'\n'||'<style isBold="true">'||d.catatan||'</style>' else '' end
	||fn_ket_kontainer_pisah(ss.id_sj_detail,ss.id_stuffing) as  jenis_barang, coalesce(d.p,0) p, coalesce(d.l,0) l, coalesce(d.t,0) t, 
	d.paket, 
	case when d.paket=true then 'Paket' 
		when d.paket=false and d.fix_volume > 0 then ''
		else case when d.p>0 then d.p::varchar else '' end|| case when d.l>0 then ' x ' else '' end ||
		     case when d.l>0 then d.l::varchar else '' end || case when d.t>0 then ' x ' else '' end ||
		     case when d.t>0 then d.t::varchar else '' end
		end ukuran, 
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
	where ss.id_stuffing=v_id 
	order by ss.id_stuffing, coalesce(kt.nama,'') desc, coalesce(k.nama,''), coalesce(kp.nama,''),
	kb.tgl_berangkat, coalesce(m.nama,''), sj.tanggal, sj.id, d.id
loop
	return next rcd;
END LOOP;	
/*
select * from fn_pl_rpt_per_stuffing(7)as (kota_tujuan varchar, kondisi varchar, 
customer varchar, kapal varchar, tgl_berangkat date, tgl_ind varchar, merk varchar, alamat varchar, nomor_kontainer varchar, emkl varchar, 
id integer, tanggal date, pengirim varchar, coli integer, jenis_barang text, p double precision, l double precision, 
t double precision, paket boolean , ukuran text, kubikasi numeric, fix_volume double precision, total_coli_sj bigint, pisah boolean)

select * from t_surat_jalan  where id_kondisi  is null
select * from t_sj_stuffing
select bool_or(show) from (
select false as show
union
select false as show
union
select false as show
) r

*/

end
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION fn_pl_rpt_per_stuffing(integer)
  OWNER TO postgres;
