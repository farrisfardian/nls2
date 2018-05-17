/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  greatsoft
 * Created: Feb 21, 2017
 */
-- Function: fn_tg_surat_jalan_detail_stuffing()

-- DROP FUNCTION fn_tg_surat_jalan_detail_stuffing();

CREATE OR REPLACE FUNCTION fn_tg_surat_jalan_detail_stuffing()
  RETURNS trigger AS
$BODY$
begin
if TG_OP='INSERT' then
	insert into t_sj_stuffing(id_sj_detail, id_stuffing, coli)
	select d.id, h.id_stuffing, d.coli
	from t_surat_jalan_detail d 
	inner join t_surat_jalan h on h.id=d.id_surat_jalan
	where d.id=NEW.id;

	return NEW;
end if;
if TG_OP='UPDATE' then
	if((select count(*) from t_sj_stuffing where id_sj_detail=NEW.id)=1) then
		update t_sj_stuffing set id_stuffing=s.id_stuffing, coli = NEW.coli
		from t_surat_jalan s 
		inner join t_surat_jalan_detail d on d.id_surat_jalan=s.id
		where id_sj_detail=NEW.id and d.id=NEW.id;
	else
		delete from t_sj_stuffing  where id_sj_detail = OLD.id;
		insert into t_sj_stuffing(id_sj_detail, id_stuffing, coli)
		select d.id, h.id_stuffing, d.coli
		from t_surat_jalan_detail d 
		inner join t_surat_jalan h on h.id=d.id_surat_jalan
		where d.id=NEW.id;
	end if;
	return NEW;
end if;
if TG_OP='DELETE' then
	delete from t_sj_stuffing  where id_sj_detail = OLD.id;

	return OLD;
end if;
return null;
end
$BODY$
  LANGUAGE plpgsql;

-- Function: fn_ket_kontainer_pisah(integer, integer)

-- DROP FUNCTION fn_ket_kontainer_pisah(integer, integer);

CREATE OR REPLACE FUNCTION fn_ket_kontainer_pisah(integer, integer)
  RETURNS text AS
$BODY$
declare
	v_id_sj_det alias for $1;
	v_id_stuffing alias for $2;
	v_tot_terima int;
	v_id_kapal int;
	rcd	record;
	v_ket	text;
begin
	select into v_tot_terima coalesce(coli,0) from t_surat_jalan_detail where id = v_id_sj_det;

	v_ket:='';
	
	select into v_id_kapal id_kapal_berangkat 
	from t_sj_stuffing sjs 
	inner join t_stuffing s on s.id=sjs.id_stuffing;
	raise notice 'v_id_kapal : %',v_id_kapal;
	for rcd in
		select sjs.coli, s.id_kapal_berangkat, coalesce(kn.nomor,'') no_kontainer, coalesce(k.nama,'') nama_kapal , kb.tgl_berangkat
		from t_sj_stuffing sjs 
		inner join t_stuffing s on s.id=sjs.id_stuffing
		inner join m_kontainer kn on kn.id=s.id_kontainer
		left join t_kapal_berangkat kb on kb.id=s.id_kapal_berangkat 
		left join m_kapal k on k.id=kb.id_kapal
		where id_stuffing<>v_id_stuffing and coalesce(sjs.coli,0)>0 and 
		id_sj_detail=v_id_sj_det
	loop
		v_ket=v_ket||E'\n'||coalesce(rcd.coli,0)||' Coli di '||rcd.no_kontainer;
		raise notice 'v_ket : %',v_ket;
		if(rcd.id_kapal_berangkat<>v_id_kapal) then
			v_ket=v_ket||' ' ||coalesce(rcd.nama_kapal,'')||' Tgl. Berangkat '||coalesce(to_char(rcd.tgl_berangkat,'dd/MM/yy'),'');
			raise notice 'v_ket : %',v_ket;
		end if;
	end loop;	

	return v_ket;
-- select fn_ket_kontainer_pisah(4248,52)
end
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION fn_ket_kontainer_pisah(integer, integer)
  OWNER TO postgres;


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
	fn_tanggal_ind(kb.tgl_berangkat) tgl_ind, (coalesce(t.nama,'')||'/ ' ||coalesce(m.nama,''))::varchar merk, coalesce(t.alamat,'') alamat_customer, 
	coalesce(kn.nomor,'') nomor_kontainer, coalesce(e.nama,'') emkl, 
	sj.id, sj.tanggal, coalesce(pengirim.nama,'') pengirim, coalesce(ss.coli,0) coli, 
	coalesce(i.nama,'')
-- 	||case when trim(coalesce(d.spesifikasi,''))<>'' then E'\n'||d.spesifikasi else '' end  
-- 	||case when kon.kont=1 then '' else E'\n'||fn_ket_kontainer_pisah(d.id, ss.id_stuffing) end jenis_barang, 
	||case when trim(coalesce(d.spesifikasi,''))<>'' then E'\n'||coalesce(d.spesifikasi,'') else '' end
	||case when trim(coalesce(d.catatan,''))<>'' then E'\n'||'<style isBold="true">'||coalesce(d.catatan,'')||'</style>' else '' end
	||coalesce(fn_ket_kontainer_pisah(ss.id_sj_detail,ss.id_stuffing),'') as  jenis_barang,
	coalesce(d.p,0) p, coalesce(d.l,0) l, coalesce(d.t,0) t, 
	d.paket, case when d.paket=true then 'Paket' 
		when d.paket=false and d.fix_volume > 0 then '0'
		else case when d.p>0 then d.p::varchar else '' end|| case when d.l>0 then ' x ' else '' end ||
		     case when d.l>0 then d.l::varchar else '' end || case when d.t>0 then ' x ' else '' end ||
		     case when d.t>0 then d.t::varchar else '' end
		end ,     
	coalesce(d.coli,0) * case when not coalesce(d.paket, false) and d.fix_volume>0 then d.fix_volume else
		case 	when d.p>0 and coalesce(d.l,0)=0 and coalesce(d.t,0)=0 then d.p
			when d.p>0 and d.l>0 and coalesce(d.t,0)=0 then d.p*d.l/1000
			when d.p>0 and d.l>0 and d.t>0 then d.p*d.l*d.t/1000000 else 0 end end::numeric(18,4) as kubikasi,
	d.fix_volume, tot.total_coli||' Coli '||fn_ket_kontainer_pisah_sj(sj.id,ss.id_stuffing), pisah.pisah, sk.nama as satuan_kirim		
	from t_sj_stuffing ss 
	inner join t_surat_jalan_detail d on d.id=ss.id_sj_detail
	inner join t_surat_jalan sj on d.id_surat_jalan=sj.id
	inner join t_stuffing st on st.id=ss.id_stuffing
	inner join m_satuan_kirim sk on sk.id=st.id_satuan_kirim
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
	where kb.id=v_kapal and coalesce(ss.coli,0) > 0
	and m.id_toko=v_toko
	order by coalesce(k.nama,'') , coalesce(kp.nama,''),
	kb.tgl_berangkat, t.nama, sk.nama, coalesce(m.nama,''), ss.id_stuffing, sj.tanggal, sj.id, d.id
loop
	return next rcd;
END LOOP;	
/*
select kota_tujuan, kondisi, 
customer, kapal, tgl_berangkat, tgl_ind, merk, alamat, nomor_kontainer, emkl, 
id, tanggal, pengirim, coli, jenis_barang, p, l, 
t, paket, case when ukuran not ilike '%x%' and ukuran not ilike '%paket%' and ukuran not ilike '' then round(ukuran::numeric(12,4),3)::text else ukuran end as ukuran, kubikasi, fix_volume, total_coli_sj, pisah from fn_pl_rpt_per_kapal_toko(15,159) as (kota_tujuan varchar, kondisi varchar, 
customer varchar, kapal varchar, tgl_berangkat date, tgl_ind varchar, merk varchar, alamat varchar, nomor_kontainer varchar, emkl varchar, 
id integer, tanggal date, pengirim varchar, coli integer, jenis_barang text, p double precision, l double precision, 
t double precision, paket boolean , ukuran text, kubikasi numeric, fix_volume double precision, total_coli_sj text, pisah boolean, satuan_kirim varchar)
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



CREATE OR REPLACE FUNCTION fn_pl_rpt_per_kapal_merk_toko(integer, integer[])
  RETURNS SETOF record AS
$BODY$

declare
	v_kapal	alias for $1;
	v_merk	alias for $2;
	rcd	record;
begin
for rcd in	
	select coalesce(kt.nama,'') kota_tujuan, coalesce(k.nama,'') kondisi,
	coalesce(t.nama,'') customer, coalesce(kp.nama,'') kapal, kb.tgl_berangkat, 
	fn_tanggal_ind(kb.tgl_berangkat) tgl_ind, (coalesce(t.nama,'')||'/ ' ||coalesce(m.nama,''))::varchar merk, coalesce(t.alamat,'') alamat_customer, 
	coalesce(kn.nomor,'') nomor_kontainer, coalesce(e.nama,'') emkl, 
	sj.id, sj.tanggal, coalesce(pengirim.nama,'') pengirim, coalesce(ss.coli,0) coli, 
	coalesce(i.nama,'')
-- 	||case when trim(coalesce(d.spesifikasi,''))<>'' then E'\n'||d.spesifikasi else '' end  
-- 	||case when kon.kont=1 then '' else E'\n'||fn_ket_kontainer_pisah(d.id, ss.id_stuffing) end jenis_barang, 
	||case when trim(coalesce(d.spesifikasi,''))<>'' then E'\n'||coalesce(d.spesifikasi,'') else '' end
	||case when trim(coalesce(d.catatan,''))<>'' then E'\n'||'<style isBold="true">'||coalesce(d.catatan,'')||'</style>' else '' end
	||coalesce(fn_ket_kontainer_pisah(ss.id_sj_detail,ss.id_stuffing),'') as  jenis_barang,
	coalesce(d.p,0) p, coalesce(d.l,0) l, coalesce(d.t,0) t, 
	d.paket, case when d.paket=true then 'Paket' 
		when d.paket=false and d.fix_volume > 0 then '0'
		else case when d.p>0 then d.p::varchar else '' end|| case when d.l>0 then ' x ' else '' end ||
		     case when d.l>0 then d.l::varchar else '' end || case when d.t>0 then ' x ' else '' end ||
		     case when d.t>0 then d.t::varchar else '' end
		end ,     
	coalesce(d.coli,0) * case when not coalesce(d.paket, false) and d.fix_volume>0 then d.fix_volume else
		case 	when d.p>0 and coalesce(d.l,0)=0 and coalesce(d.t,0)=0 then d.p
			when d.p>0 and d.l>0 and coalesce(d.t,0)=0 then d.p*d.l/1000
			when d.p>0 and d.l>0 and d.t>0 then d.p*d.l*d.t/1000000 else 0 end end::numeric(18,4) as kubikasi,
	d.fix_volume, tot.total_coli||' Coli '||fn_ket_kontainer_pisah_sj(sj.id,ss.id_stuffing), pisah.pisah, sk.nama as satuan_kirim		
	from t_sj_stuffing ss 
	inner join t_surat_jalan_detail d on d.id=ss.id_sj_detail
	inner join t_surat_jalan sj on d.id_surat_jalan=sj.id
	inner join t_stuffing st on st.id=ss.id_stuffing
	inner join m_satuan_kirim sk on sk.id=st.id_satuan_kirim
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
	where kb.id=v_kapal and coalesce(ss.coli,0) > 0
	and m.id in (select unnest(v_merk))
	order by coalesce(k.nama,'') , coalesce(kp.nama,''),
	kb.tgl_berangkat, t.nama, sk.nama, coalesce(m.nama,''), ss.id_stuffing, sj.tanggal, sj.id, d.id
loop
	return next rcd;
END LOOP;	
/*
select kota_tujuan, kondisi, 
customer, kapal, tgl_berangkat, tgl_ind, merk, alamat, nomor_kontainer, emkl, 
id, tanggal, pengirim, coli, jenis_barang, p, l, 
t, paket, case when ukuran not ilike '%x%' and ukuran not ilike '%paket%' and ukuran not ilike '' then round(ukuran::numeric(12,4),3)::text else ukuran end as ukuran, kubikasi, fix_volume, total_coli_sj, pisah from fn_pl_rpt_per_kapal_merk_toko(22,ARRAY[113,227]) as (kota_tujuan varchar, kondisi varchar, 
customer varchar, kapal varchar, tgl_berangkat date, tgl_ind varchar, merk varchar, alamat varchar, nomor_kontainer varchar, emkl varchar, 
id integer, tanggal date, pengirim varchar, coli integer, jenis_barang text, p double precision, l double precision, 
t double precision, paket boolean , ukuran text, kubikasi numeric, fix_volume double precision, total_coli_sj text, pisah boolean, satuan_kirim varchar)
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
  LANGUAGE plpgsql;

-- Function: fn_ket_kontainer_pisah_sj(integer, integer)

-- DROP FUNCTION fn_ket_kontainer_pisah_sj(integer, integer);

CREATE OR REPLACE FUNCTION fn_ket_kontainer_pisah_sj(integer, integer)
  RETURNS text AS
$BODY$
declare
	v_id_sj alias for $1;
	v_id_stuffing alias for $2;
	v_id_kapal int;
	rcd	record;
	v_ket	text;
begin	

	v_ket:='';

	select into v_id_kapal id_kapal_berangkat 
	from t_sj_stuffing sjs 
	inner join t_stuffing s on s.id=sjs.id_stuffing
	where s.id = v_id_stuffing;
	raise notice 'v_id_kapal : %',v_id_kapal;
	
	for rcd in
		select d.id_surat_jalan, st.id_stuffing, c.nomor as no_kontainer, sum(st.coli) coli, 
		s.id_kapal_berangkat, k.nama as nama_kapal, kb.tgl_berangkat
		 from t_surat_jalan_detail d
		inner join t_sj_stuffing st on st.id_sj_detail=d.id
		inner join t_stuffing s on s.id=st.id_stuffing
		inner join m_kontainer c on c.id=s.id_kontainer
		left join t_kapal_berangkat kb on s.id_kapal_berangkat = kb.id
		left join m_kapal k on kb.id_kapal = k.id
		where id_surat_jalan=v_id_sj AND s.id <> v_id_stuffing and coalesce(st.coli,0)>0
		group by st.id_stuffing, c.nomor, d.id_surat_jalan, s.id_kapal_berangkat, k.nama, kb.tgl_berangkat
	loop
		v_ket=v_ket||E'\n'||coalesce(rcd.coli,0)||' Coli di '||rcd.no_kontainer;
		raise notice 'v_ket : %',v_ket;
		if(rcd.id_kapal_berangkat<>v_id_kapal) then
			v_ket=v_ket||' ' ||coalesce(rcd.nama_kapal,'')||' Tgl. Berangkat '||coalesce(to_char(rcd.tgl_berangkat,'dd/MM/yy'),'');
			raise notice 'v_ket : %',v_ket;
		end if;
	end loop;	

	return v_ket;
-- select fn_ket_kontainer_pisah_sj(982, 52)
end
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION fn_ket_kontainer_pisah_sj(integer, integer)
  OWNER TO test;

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
	fn_tanggal_ind(kb.tgl_berangkat) tgl_ind, (coalesce(t.nama,'')||'/ ' ||coalesce(m.nama,''))::varchar merk, coalesce(t.alamat,'') alamat_customer, 
	coalesce(kn.nomor,'') nomor_kontainer, coalesce(e.nama,'') emkl, 
	sj.id, sj.tanggal, coalesce(pengirim.nama,'') pengirim, coalesce(ss.coli,0) coli, 
	coalesce(i.nama,'')
-- 	||case when trim(coalesce(d.spesifikasi,''))<>'' then '<br>'||d.spesifikasi else '' end
-- 	||case when trim(coalesce(d.catatan,''))<>'' then '<br>'||'<b>'||d.catatan||'</b>' else '' end
	||case when trim(coalesce(d.spesifikasi,''))<>'' then (E'\n'||coalesce(d.spesifikasi,'')) else '' end
	||case when trim(coalesce(d.catatan,''))<>'' then (E'\n'||'<style isBold="true">'||coalesce(d.catatan,'')||'</style>') else '' end
	||coalesce(fn_ket_kontainer_pisah(ss.id_sj_detail,ss.id_stuffing),'') as  jenis_barang, coalesce(d.p,0) p, coalesce(d.l,0) l, coalesce(d.t,0) t, 
	d.paket, 
	case when d.paket=true then 'Paket' 
		when d.paket=false and d.fix_volume > 0 then '0'
		else case when d.p>0 then d.p::varchar else '' end|| case when d.l>0 then ' x ' else '' end ||
		     case when d.l>0 then d.l::varchar else '' end || case when d.t>0 then ' x ' else '' end ||
		     case when d.t>0 then d.t::varchar else '' end
		end ukuran, 
	coalesce(d.coli,0) * case when not coalesce(d.paket, false) and d.fix_volume>0 then d.fix_volume else
		case 	when d.p>0 and coalesce(d.l,0)=0 and coalesce(d.t,0)=0 then d.p
			when d.p>0 and d.l>0 and coalesce(d.t,0)=0 then d.p*d.l/1000
			when d.p>0 and d.l>0 and d.t>0 then d.p*d.l*d.t/1000000 else 0 end end::numeric(18,4) as kubikasi,
	d.fix_volume, tot.total_coli||' Coli '||fn_ket_kontainer_pisah_sj(sj.id,v_id), pisah.pisah		
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
	where ss.id_stuffing=v_id and coalesce(ss.coli,0) > 0
	order by ss.id_stuffing, coalesce(kt.nama,'') desc, coalesce(k.nama,''), coalesce(kp.nama,''),
	kb.tgl_berangkat, coalesce(m.nama,''), sj.tanggal, sj.id, d.id
loop
	return next rcd;
END LOOP;	
/*
select kota_tujuan, kondisi, 
customer, kapal, tgl_berangkat, tgl_ind, merk, alamat, nomor_kontainer, emkl, 
id, tanggal, pengirim, coli, jenis_barang, p, l, 
t, paket, case when ukuran not ilike '%x%' and ukuran not ilike '%paket%' and ukuran not ilike '' then round(ukuran::numeric(12,4),3)::text else ukuran end as ukuran, kubikasi, fix_volume, total_coli_sj, pisah from fn_pl_rpt_per_stuffing(52)as (kota_tujuan varchar, kondisi varchar, 
customer varchar, kapal varchar, tgl_berangkat date, tgl_ind varchar, merk varchar, alamat varchar, nomor_kontainer varchar, emkl varchar, 
id integer, tanggal date, pengirim varchar, coli integer, jenis_barang text, p double precision, l double precision, 
t double precision, paket boolean , ukuran text, kubikasi numeric, fix_volume double precision, total_coli_sj text, pisah boolean)
where pisah = true

select d.id_surat_jalan, sum(st.coli), k.nomor
from t_sj_stuffing st 
inner join t_surat_jalan_detail d on d.id=st.id_sj_detail
inner join t_stuffing sf on sf.id=st.id_stuffing
inner join m_kontainer k on k.id=sf.id_kontainer
where sf.id<>52
group by d.id_surat_jalan, k.nomor




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

-- Function: fn_rekap_by_grup(character varying, character varying, character varying, character varying)

-- DROP FUNCTION fn_rekap_by_grup(character varying, character varying, character varying, character varying);

CREATE OR REPLACE FUNCTION fn_rekap_by_grup(v_grup character varying, v_tgl_mulai character varying, v_tgl_sampai character varying, v_order character varying)
  RETURNS SETOF record AS
$BODY$
declare
	r  record;
	v_sql text;
begin
	v_sql := 'select * from (
		select case '''||v_grup||''' when ''kota_tujuan'' then coalesce(kt.nama,'''')
		when ''kondisi'' then coalesce(k.nama,'''')
		when ''customer'' then coalesce(t.nama,'''') 
		when ''kapal'' then coalesce(kp.nama,'''') 
		when ''nomor_kontainer'' then coalesce(kn.nomor,'''')
		when ''emkl'' then coalesce(e.nama,'''')
		when ''pengirim'' then coalesce(pengirim.nama,'''') end::varchar as grup, 
		sum(coalesce(ss.coli,0)) as coli, 
		sum(coalesce(d.coli,0) * case when not coalesce(d.paket, false) and d.fix_volume>0 then d.fix_volume else
			case 	when d.p>0 and coalesce(d.l,0)=0 and coalesce(d.t,0)=0 then d.p
				when d.p>0 and d.l>0 and coalesce(d.t,0)=0 then d.p*d.l/1000
				when d.p>0 and d.l>0 and d.t>0 then d.p*d.l*d.t/1000000 else 0 end end::numeric(18,4)) as kubikasi
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
		where coalesce(ss.coli,0) > 0 and '
		||case when v_tgl_mulai is null or v_tgl_sampai is null then 'true' else 'kb.tgl_berangkat between '''||v_tgl_mulai||'''::date and '''||v_tgl_sampai||'''::date' end
		||' group by
		case '''||v_grup||''' when ''kota_tujuan'' then coalesce(kt.nama,'''')
		when ''kondisi'' then coalesce(k.nama,'''')
		when ''customer'' then coalesce(t.nama,'''') 
		when ''kapal'' then coalesce(kp.nama,'''') 
		when ''nomor_kontainer'' then coalesce(kn.nomor,'''')
		when ''emkl'' then coalesce(e.nama,'''')
		when ''pengirim'' then coalesce(pengirim.nama,'''') end
	    ) r order by '||case 
	    when v_order ilike 'grup_desc' then 'grup desc'
	    when v_order ilike 'grup_asc' then 'grup asc'
	    when v_order ilike 'coli_desc' then 'coli desc'
	    when v_order ilike 'coli_asc' then 'coli asc'
	    when v_order ilike 'kubikasi_desc' then 'kubikasi desc'
	    when v_order ilike 'kubikasi_asc' then 'kubikasi asc'
	    end	;
	for r in
	    execute v_sql
	loop
		return next r;
	end loop;	
end	
/*
v_grup -> 'kota_tujuan', 'kondisi', 'customer', 'kapal', 'nomor_kontainer', 'emkl', 'pengirim'
v_order -> 'grup_asc', 'grup_desc', 'coli_asc', 'coli_desc', 'kubikasi_asc', 'kubikasi_desc'
select * from fn_rekap_by_grup('kondisi','2017-03-03','2017-03-03','kubikasi_desc') as (grup varchar, coli bigint, kubikasi numeric)
limit 5
*/
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION fn_rekap_by_grup(character varying, character varying, character varying, character varying)
  OWNER TO postgres;

-- Function: fn_tanggal_ind(date)

-- DROP FUNCTION fn_tanggal_ind(date);

CREATE OR REPLACE FUNCTION fn_tanggal_ind(date)
  RETURNS character varying AS
$BODY$
declare v_return varchar(20);

begin
	select into v_return
	case 	when date_part('month', $1)=1 then to_char($1, 'dd')||' Januari ' ||to_char($1, 'yyyy')
		when date_part('month', $1)=2 then to_char($1, 'dd')||' Februari ' ||to_char($1, 'yyyy')
		when date_part('month', $1)=3 then to_char($1, 'dd')||' Maret ' ||to_char($1, 'yyyy')
		when date_part('month', $1)=4 then to_char($1, 'dd')||' April ' ||to_char($1, 'yyyy')
		when date_part('month', $1)=5 then to_char($1, 'dd')||' Mei ' ||to_char($1, 'yyyy')
		when date_part('month', $1)=6 then to_char($1, 'dd')||' Juni ' ||to_char($1, 'yyyy')
		when date_part('month', $1)=7 then to_char($1, 'dd')||' Juli ' ||to_char($1, 'yyyy')
		when date_part('month', $1)=8 then to_char($1, 'dd')||' Agustus ' ||to_char($1, 'yyyy')
		when date_part('month', $1)=9 then to_char($1, 'dd')||' September ' ||to_char($1, 'yyyy')
		when date_part('month', $1)=10 then to_char($1, 'dd')||' Oktober ' ||to_char($1, 'yyyy')
		when date_part('month', $1)=11 then to_char($1, 'dd')||' November ' ||to_char($1, 'yyyy')
		when date_part('month', $1)=12 then to_char($1, 'dd')||' Desember ' ||to_char($1, 'yyyy')
		ELSE ''
	end ::varchar;
	
return v_return;
end

$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION fn_tanggal_ind(date)
  OWNER TO postgres;

create or replace function hitung_subtotal_metrik(v_id_sj_detail int) returns double precision as $$
declare
	v_subtotal double precision;
        v_totkubikasi double precision;
        r record;
begin
	select * into r from t_surat_jalan_detail  where id  = v_id_sj_detail;
	v_subtotal:=0.0;
	v_totkubikasi:=0;
	if (coalesce(r.paket,false) != true) then
	    if (r.t = 0) then 
		if (r.l = 0 and r.t = 0) then
		    v_subtotal := r.p;
		else 
		    v_subtotal := r.p * r.l / 1000;
		end if;
	    else 
		v_subtotal := r.p * r.l * r.t / 1000000;
	    end if;
	    v_totkubikasi := v_totkubikasi + (r.coli * v_subtotal);
	end if;
	return v_totkubikasi;
end
$$language plpgsql;

-- Function: fn_gen_detail_nota2(integer, integer, integer[])

-- DROP FUNCTION fn_gen_detail_nota2(integer, integer, integer[]);

CREATE OR REPLACE FUNCTION fn_gen_detail_nota2(
    v_id_toko_tujuan integer,
    v_id_merk_tujuan integer,
    v_id_kapal_berangkat integer[])
  RETURNS SETOF record AS
$BODY$
declare
	r record;
begin
	for r in
		select b_lcl.*,
		sh.harga
		 from 
		(
		select coalesce(kt.nama,'') kota_tujuan, coalesce(k.nama,'') kondisi,
			coalesce(t.nama,'') customer, coalesce(kp.nama,'') kapal, kb.tgl_berangkat,
			fn_get_tgl_setting_harga(kb.tgl_berangkat::varchar, m.id_toko, st.id_kota_asal, kt.id, sj.id_kondisi) tgl_harga,	
			fn_tanggal_ind(kb.tgl_berangkat) tgl_ind, 
			case when v_id_merk_tujuan is null then ''::varchar else 
			(coalesce(t.nama,'')||'/ ' ||coalesce(m.nama,''))::varchar end merk, 
			coalesce(st.no_kontainer,'') nomor_kontainer, coalesce(ji.nama,'') jenis_item, 
			ji.id as id_jenis_item,
			case when kh.ukuran_kontainer is not null then 1 else d.id_kategori_harga end id_kategori_harga, st.ukuran_kontainer, st.id_kapal_berangkat, 
			case when v_id_merk_tujuan is null then 0::int else sj.id_merk end id_merk, 
			m.id_toko,coalesce(d.paket, false) paket, 'LCL'::varchar sat_kirim,kp.id as id_kapal,
			sj.id_kondisi,st.id_kota_asal,
			ROUND(sum(case when coalesce(d.paket, false) then coalesce(ss.coli,0) 
			when not coalesce(d.paket, false) and d.fix_volume>0 then coalesce(ss.coli,0)*(d.fix_volume/(select sum(coli) from t_sj_stuffing where id_sj_detail = d.id)) else
			coalesce(ss.coli,0) * 
				case 	when d.p>0 and coalesce(d.l,0)=0 and coalesce(d.t,0)=0 then d.p
					when d.p>0 and d.l>0 and coalesce(d.t,0)=0 then d.p*d.l/1000
					when d.p>0 and d.l>0 and d.t>0 then d.p*d.l*d.t/1000000 else 0 end end::numeric(18,4)),3) as kubikasi,
			0::numeric jml, sum(ss.coli)::int as coli, string_agg(distinct sj.id::varchar,',') as id_sj, sk.nama as sat_kirim_ori, coalesce(sj.sisipan,false) as sisipan
			from t_sj_stuffing ss 
			inner join t_surat_jalan_detail d on d.id=ss.id_sj_detail
			left join m_kategori_harga kh on kh.id = d.id_kategori_harga
			inner join t_surat_jalan sj on d.id_surat_jalan=sj.id
			inner join t_stuffing st on st.id=ss.id_stuffing
			inner join m_satuan_kirim sk on sk.id = st.id_satuan_kirim        
			inner join m_kota kt on kt.id=st.id_kota
			left join m_kota ka on ka.id=sj.id_kota_asal
			inner join m_merk m on m.id=sj.id_merk
			inner join m_toko t on t.id=m.id_toko
			inner join m_item i on i.id=d.id_item
			inner join m_emkl e on e.id=st.id_emkl
			inner join m_kondisi k on k.id=sj.id_kondisi
			inner join m_toko pengirim on pengirim.id=sj.id_toko
			inner join t_kapal_berangkat kb on kb.id=st.id_kapal_berangkat
			inner join m_kapal kp on kp.id=kb.id_kapal
			left join m_jenis_item ji on ji.id = sj.id_jenis_item			
			where (sk.nama = 'LCL' or coalesce(sj.sisipan,false) = true)
			and case when v_id_toko_tujuan is null then true else t.id = v_id_toko_tujuan end 
		 	and case when v_id_merk_tujuan is null then true else sj.id_merk = v_id_merk_tujuan end
		 	and case when v_id_kapal_berangkat is null then true else st.id_kapal_berangkat in (select unnest(v_id_kapal_berangkat)) end
			group by 			
			coalesce(kt.nama,''), coalesce(k.nama,''),  
			coalesce(t.nama,''), coalesce(kp.nama,''), kb.tgl_berangkat, 
			fn_get_tgl_setting_harga(kb.tgl_berangkat::varchar, m.id_toko),
			coalesce(d.paket, false),
			fn_tanggal_ind(kb.tgl_berangkat), 
			case when v_id_merk_tujuan is null then ''::varchar else 
			(coalesce(t.nama,'')||'/ ' ||coalesce(m.nama,''))::varchar end, 
			coalesce(st.no_kontainer,''), coalesce(ji.nama,''),ji.id , d.id_kategori_harga, 
			st.ukuran_kontainer, st.id_kapal_berangkat, 
			case when v_id_merk_tujuan is null then 0::int else sj.id_merk end, m.id_toko, kp.id, 
			sj.id_kondisi, st.id_kota_asal, kt.id, kh.ukuran_kontainer, sk.nama, coalesce(sj.sisipan,false)
			order by 
			coalesce(kt.nama,''), coalesce(k.nama,''),
			coalesce(t.nama,''), coalesce(kp.nama,''), kb.tgl_berangkat, 
			fn_tanggal_ind(kb.tgl_berangkat), 
			case when v_id_merk_tujuan is null then ''::varchar else 
			(coalesce(t.nama,'')||'/ ' ||coalesce(m.nama,''))::varchar end, 
			coalesce(st.no_kontainer,''), coalesce(ji.nama,''), d.id_kategori_harga
		) b_lcl left join
		(select s.tgl_berlaku, s.id_kondisi, s.id_toko, d.id_kategori_harga, d.harga, s.id_kota_asal from c_setting_harga s join c_setting_harga_detail d on d.id_setting_harga = s.id) sh on b_lcl.id_toko = sh.id_toko and b_lcl.tgl_harga = sh.tgl_berlaku and b_lcl.id_kondisi = sh.id_kondisi and b_lcl.id_kategori_harga = sh.id_kategori_harga and b_lcl.id_kota_asal = sh.id_kota_asal
		-- where kubikasi is  null 
		-- order by tgl_berangkat, kota_tujuan, paket, kubikasi

		union

		select b_fcl.*,
		sh.harga
		from
		(select kota_tujuan, kondisi, customer, kapal, tgl_berangkat, tgl_harga, tgl_ind, merk, nomor_kontainer, jenis_item, id_jenis_item, id_kategori_harga, ukuran_kontainer, id_kapal_berangkat, id_merk, id_toko, paket, sat_kirim, id_kapal,id_kondisi,id_kota_asal,kubikasi, count(*)::numeric jml,sum(coli)::int as coli, string_agg(id_sj::varchar,',') as id_sj, sat_kirim_ori, sisipan
		 from 
		(
		select coalesce(kt.nama,'') kota_tujuan, coalesce(k.nama,'') kondisi,
			coalesce(t.nama,'') customer, coalesce(kp.nama,'') kapal, kb.tgl_berangkat,
			fn_get_tgl_setting_harga(kb.tgl_berangkat::varchar, m.id_toko, st.id_kota_asal, kt.id, sj.id_kondisi) tgl_harga,		
			fn_tanggal_ind(kb.tgl_berangkat) tgl_ind, 
			case when v_id_merk_tujuan is null then ''::varchar else 
			(coalesce(t.nama,'')||'/ ' ||coalesce(m.nama,''))::varchar end merk, 			
			coalesce(st.no_kontainer,'') nomor_kontainer, string_agg(distinct coalesce(ji.nama,''),', ')::varchar jenis_item, 
			0::int as id_jenis_item,
-- 			ji.id as id_jenis_item,
			case when kh.ukuran_kontainer is null then (select id from m_kategori_harga where ukuran_kontainer = st.ukuran_kontainer order by nama limit 1) else d.id_kategori_harga end id_kategori_harga, st.ukuran_kontainer, st.id_kapal_berangkat, 
-- 			0::int id_kategori_harga, st.ukuran_kontainer, st.id_kapal_berangkat, 
			case when v_id_merk_tujuan is null then 0::int else sj.id_merk end id_merk, 
			m.id_toko,false paket, 'FCL'::varchar sat_kirim,kp.id as id_kapal,sj.id_kondisi,st.id_kota_asal,
			sum(0::numeric(18,4)) as kubikasi, sum(ss.coli) as coli, string_agg(distinct sj.id::varchar,',') as id_sj, sk.nama as sat_kirim_ori, coalesce(sj.sisipan,false) as sisipan
			from t_sj_stuffing ss 
			inner join t_surat_jalan_detail d on d.id=ss.id_sj_detail
			left join m_kategori_harga kh on kh.id = d.id_kategori_harga
			inner join t_surat_jalan sj on d.id_surat_jalan=sj.id
			inner join t_stuffing st on st.id=ss.id_stuffing
			inner join m_satuan_kirim sk on sk.id = st.id_satuan_kirim        
			inner join m_kota kt on kt.id=st.id_kota
			left join m_kota ka on ka.id=sj.id_kota_asal
			inner join m_merk m on m.id=sj.id_merk
			inner join m_toko t on t.id=m.id_toko
			inner join m_item i on i.id=d.id_item
			inner join m_emkl e on e.id=st.id_emkl
			inner join m_kondisi k on k.id=sj.id_kondisi
			inner join m_toko pengirim on pengirim.id=sj.id_toko
			inner join t_kapal_berangkat kb on kb.id=st.id_kapal_berangkat
			inner join m_kapal kp on kp.id=kb.id_kapal
			left join m_jenis_item ji on ji.id = sj.id_jenis_item			
			where (sk.nama = 'FCL'  and coalesce(sj.sisipan,false) = false)
			and case when v_id_toko_tujuan is null then true else t.id = v_id_toko_tujuan end 
		 	and case when v_id_merk_tujuan is null then true else sj.id_merk = v_id_merk_tujuan end
		 	and case when v_id_kapal_berangkat is null then true else st.id_kapal_berangkat in (select unnest(v_id_kapal_berangkat)) end
			group by 
			coalesce(kt.nama,''), coalesce(k.nama,''),  
			coalesce(t.nama,''), coalesce(kp.nama,''), kb.tgl_berangkat,
			fn_get_tgl_setting_harga(kb.tgl_berangkat::varchar, m.id_toko),
			fn_tanggal_ind(kb.tgl_berangkat), st.ukuran_kontainer,
			case when v_id_merk_tujuan is null then ''::varchar else 
			(coalesce(t.nama,'')||'/ ' ||coalesce(m.nama,''))::varchar end, 
			coalesce(st.no_kontainer,''), st.id_kapal_berangkat, 
			case when v_id_merk_tujuan is null then 0::int else sj.id_merk end, m.id_toko, kp.id,
			sj.id_kondisi, st.id_kota_asal,d.id_kategori_harga, kt.id, kh.ukuran_kontainer, sk.nama, coalesce(sj.sisipan,false)
			order by 
			coalesce(kt.nama,''), coalesce(k.nama,''), st.ukuran_kontainer, 
			coalesce(t.nama,''), coalesce(kp.nama,''), kb.tgl_berangkat, 
			fn_tanggal_ind(kb.tgl_berangkat), 
			case when v_id_merk_tujuan is null then ''::varchar else 
			(coalesce(t.nama,'')||'/ ' ||coalesce(m.nama,''))::varchar end, 
			coalesce(st.no_kontainer,'')
		) b_fcl		
		group by kota_tujuan, kondisi, customer, kapal, tgl_berangkat, tgl_harga, tgl_ind, merk, nomor_kontainer,  id_kategori_harga, ukuran_kontainer, id_kapal_berangkat, id_kondisi, id_kota_asal, id_merk, id_toko, paket, sat_kirim, id_kapal, kubikasi, jenis_item, id_jenis_item, sat_kirim_ori, sisipan) b_fcl
		-- having count(*)>1
		-- where kubikasi is  null 
		left join
		(select s.tgl_berlaku, s.id_kondisi, s.id_toko, k.ukuran_kontainer, d.id_kategori_harga, d.harga, s.id_kota_asal from c_setting_harga s join c_setting_harga_detail d on d.id_setting_harga = s.id join m_kategori_harga k on k.id = d.id_kategori_harga) sh on b_fcl.id_toko = sh.id_toko and b_fcl.tgl_harga = sh.tgl_berlaku and b_fcl.id_kondisi = sh.id_kondisi and b_fcl.ukuran_kontainer = sh.ukuran_kontainer and b_fcl.id_kota_asal = sh.id_kota_asal and b_fcl.id_kategori_harga = sh.id_kategori_harga
		order by sat_kirim, tgl_berangkat, kota_tujuan, nomor_kontainer, paket, kubikasi
	loop
		return next r;
	end loop;
end
/*
select * from fn_gen_detail_nota2(344, null, ARRAY[122,27,52]) as (kota_tujuan varchar, kondisi varchar, customer varchar, kapal varchar, tgl_berangkat date, tgl_harga date, tgl_ind varchar, merk varchar, nomor_kontainer varchar, jenis_item varchar, id_jenis_item int, id_kategori_harga int, ukuran_kontainer varchar, id_kapal_berangkat int, id_merk int, id_toko int, paket boolean, sat_kirim varchar, id_kapal int, id_kondisi int, id_kota_asal int, kubikasi numeric, jml numeric, coli int, id_sj text, sat_kirim_ori varchar, sisipan bool, harga_satuan numeric)

select * from fn_gen_detail_nota2(413, 347, ARRAY[151]) as (kota_tujuan varchar, kondisi varchar, customer varchar, kapal varchar, tgl_berangkat date, tgl_harga date, tgl_ind varchar, merk varchar, nomor_kontainer varchar, jenis_item varchar, id_jenis_item int, id_kategori_harga int, ukuran_kontainer varchar, id_kapal_berangkat int, id_merk int, id_toko int, paket boolean, sat_kirim varchar, id_kapal int, id_kondisi int, id_kota_asal int, kubikasi numeric, jml numeric, coli int, id_sj text, sat_kirim_ori varchar, sisipan bool, harga_satuan numeric)

select * from fn_gen_detail_nota2(413, 347, ARRAY[176]) as (kota_tujuan varchar, kondisi varchar, customer varchar, kapal varchar, tgl_berangkat date, tgl_harga date, tgl_ind varchar, merk varchar, nomor_kontainer varchar, jenis_item varchar, id_jenis_item int, id_kategori_harga int, ukuran_kontainer varchar, id_kapal_berangkat int, id_merk int, id_toko int, paket boolean, sat_kirim varchar, id_kapal int, id_kondisi int, id_kota_asal int, kubikasi numeric, jml numeric, coli int, id_sj text, sat_kirim_ori varchar, sisipan bool, harga_satuan numeric)

*/
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION fn_gen_detail_nota2(integer, integer, integer[])
  OWNER TO postgres;
