-- Function: fn_get_nota(integer)

-- DROP FUNCTION fn_get_nota(integer);

CREATE OR REPLACE FUNCTION fn_get_nota(v_id_nota integer)
  RETURNS SETOF record AS
$BODY$
declare
	r record;
begin
	for r in
		select id, tanggal, nomor, min_bayar, kepada, kapal, tgl_berangkat, kota_tujuan, jenis_item, kondisi, satuan_kirim, volume, harga, jml_min_bayar , tambahan_min_bayar , no_kontainer, replace(arr_jml_text[1],',','.')||','||arr_jml_text[2] as jml_text , idx, tgl_kapal, coli from (
		select n.id, fn_tanggal_ind(n.tanggal) as tanggal, n.nomor, n.min_bayar, coalesce(t.nama,'')||coalesce('/'||m.nama,'') as kepada, kp.nama as kapal, to_char(kb.tgl_berangkat,'dd/mm/yyyy') as tgl_berangkat, k.nama as kota_tujuan, nd.jenis_items as jenis_item, replace(replace(replace(replace(upper(ko.nama),'PORT','P'),'DOOR','D'),'TO','T'),' ','') as kondisi, sk.nama as satuan_kirim, round(nd.volume::numeric,3)::double precision volume, nd.harga, n.jml_min_bayar, coalesce(nd.tambahan_min_bayar,0)::numeric as tambahan_min_bayar, nd.no_kontainer, string_to_array(case when coalesce(n.min_bayar,false)=true and coalesce(nd.tambahan_min_bayar,0)>0 then trim(to_char(coalesce(nd.tambahan_min_bayar,0)+(round(nd.volume::numeric,3)*nd.harga),'999,999,999,999,990.99')||E'\n(Min)') else trim(to_char(round(nd.volume::numeric,3)*nd.harga,'999,999,999,999,999.99')) end,'.') as arr_jml_text, 1::int idx, kb.tgl_berangkat tgl_kapal, nd.coli
		from t_nota n join t_nota_detail nd on n.id = nd.id_nota
		join m_toko t on t.id = n.id_toko
		left join m_merk m on m.id = n.id_merk
-- 		join m_jenis_item ji on ji.id = nd.id_jenis_item
		join m_kondisi ko on ko.id = nd.id_kondisi
		join m_satuan_kirim sk on sk.id = nd.id_satuan_kirim
		join t_kapal_berangkat kb on kb.id = nd.id_kapal_berangkat
		join m_kota k on k.id = t.id_kota
		join m_kapal kp on kp.id = kb.id_kapal
		WHERE n.id = v_id_nota

		union

		select n.id, fn_tanggal_ind(n.tanggal) as tanggal, n.nomor, n.min_bayar, coalesce(t.nama,'')||coalesce('/'||m.nama,'') as kepada, 'Tambahan Biaya'::varchar as kapal, ''::text as tgl_berangkat, k.nama as kota_tujuan, ji.nama as jenis_item, ''::text as kondisi, ''::varchar as satuan_kirim, round(nd.jumlah::numeric,3)::double precision, nd.harga, n.jml_min_bayar, 0::numeric, '-'::varchar, string_to_array(trim(to_char(round(nd.jumlah::numeric,3)*nd.harga,'999,999,999,999,999.99')),'.') as jml_text, 2::int idx, null, null
		from t_nota n join t_nota_tambahan_biaya nd on n.id = nd.id_nota		 
		join m_toko t on t.id = n.id_toko
		left join m_merk m on m.id = n.id_merk
		join m_tambahan_biaya ji on ji.id = nd.id_tambahan_biaya
		join m_kota k on k.id = t.id_kota
		WHERE n.id = v_id_nota
		) rec order by idx, tgl_berangkat, kapal, no_kontainer
	loop
		return next r;
	end loop;
end
/*
select * from fn_get_nota(2) as (id int, tanggal varchar, nomor varchar, min_bayar boolean, kepada text, kapal varchar, tgl_berangkat text, kota_tujuan varchar, jenis_item varchar, kondisi text, satuan_kirim varchar, volume double precision, harga double precision, jml_min_bayar double precision, tambahan_min_bayar numeric, no_kontainer varchar, jml_text text, idx int, tgl_kapal date, coli int)

select distinct kapal, tgl_berangkat from fn_get_nota(6) as (id int, tanggal varchar, nomor varchar, min_bayar boolean, kepada text, kapal varchar, tgl_berangkat text, kota_tujuan varchar, jenis_item varchar, kondisi text, satuan_kirim varchar, volume double precision, harga double precision, jml_min_bayar double precision, tambahan_min_bayar numeric, no_kontainer varchar, jml_text text, idx int, tgl_kapal date, coli int) where tgl_berangkat is not null

select terbilang2nd(sum(case when min_bayar=true then case when tambahan_min_bayar>0 then tambahan_min_bayar + (harga*volume) else (harga*volume) end else (harga*volume) end)::numeric) terbilang,sum(case when min_bayar=true then case when tambahan_min_bayar>0 then tambahan_min_bayar + (harga*volume) else (harga*volume) end else (harga*volume) end)::numeric::int::double precision total from fn_get_nota(1) as (id int, tanggal varchar, nomor varchar, min_bayar boolean, kepada text, kapal varchar, tgl_berangkat text, kota_tujuan varchar, jenis_item varchar, kondisi text, satuan_kirim varchar, volume double precision, harga double precision, jml_min_bayar double precision, tambahan_min_bayar numeric, no_kontainer varchar, jml_text text, idx int, tgl_kapal date, coli int)

select to_char(9999999.015,'999,999,999,999,999.99')
select string_to_array('1.2.3','.')
select * from t_nota_tambahan_biaya 
		 select * from m_tambahan_biaya 
*/
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION fn_get_nota(integer)
  OWNER TO postgres;
