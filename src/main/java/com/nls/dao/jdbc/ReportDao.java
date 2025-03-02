/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nls.dao.jdbc;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ustadho
 */
@Repository
public class ReportDao {

    @Autowired
    MapResultSet mr;

    public Object perStuffing(Integer id) {
        String sql = "select kota_tujuan, kondisi, \n"
                + "customer, kapal, tgl_berangkat, to_char(tgl_berangkat,'yyyy-mm-dd') tgl_brgkt, tgl_ind, merk, alamat, nomor_kontainer, emkl, \n"
                + "id, tanggal, pengirim, coli, jenis_barang, p, l, \n"
                + "t, paket, ukuran, kubikasi, fix_volume, total_coli_sj, pisah from fn_pl_rpt_per_stuffing(" + id + ") as (kota_tujuan varchar, kondisi varchar,\n"
                + "customer varchar, kapal varchar, tgl_berangkat date, tgl_ind varchar, merk varchar, alamat varchar, nomor_kontainer varchar, emkl varchar,\n"
                + "id integer, tanggal date, pengirim varchar, coli integer, jenis_barang text, p double precision, l double precision,\n"
                + "t double precision, paket boolean , ukuran text, kubikasi numeric, fix_volume double precision, total_coli_sj text, pisah boolean)";
        System.out.println("perStuffing : " + sql);
        return mr.mapList(sql);
    }

    public Object pricelistPelayaran(String idKotaAsal, String idPelayaran, String ukuranKontainer) {
        String sql = "select * from fn_rpt_pricelist_pelayaran(" + idKotaAsal + ", " + idPelayaran + ",'" + ukuranKontainer + "'::varchar) as (asal varchar, tujuan varchar, item varchar, ukuran_kontainer text, tgl_berlaku date, jml numeric);";
        System.out.println("perStuffing : " + sql);
        return mr.mapList(sql);
    }

    public Object perKapalToko(Integer idKapal, Integer idToko) {
        String sql = "select kota_tujuan, kondisi, \n"
                + "customer, kapal, tgl_berangkat, tgl_ind, merk, alamat, nomor_kontainer, emkl, \n"
                + "id, tanggal, pengirim, coli, jenis_barang, p, l, \n"
                + "t, paket, case when ukuran not ilike '%x%' and ukuran not ilike '%paket%' and ukuran not ilike '' then '0'::text else ukuran end as ukuran, kubikasi, fix_volume, total_coli_sj, pisah, satuan_kirim  from fn_pl_rpt_per_kapal_toko(" + idKapal + ", " + idToko + ") as (kota_tujuan varchar, kondisi varchar, \n"
                + "customer varchar, kapal varchar, tgl_berangkat date, tgl_ind varchar, merk varchar, alamat varchar, nomor_kontainer varchar, emkl varchar, \n"
                + "id integer, tanggal date, pengirim varchar, coli integer, jenis_barang text, p double precision, l double precision, \n"
                + "t double precision, paket boolean ,ukuran text, kubikasi numeric, fix_volume double precision, total_coli_sj text, pisah boolean, satuan_kirim varchar)";
        return mr.mapList(sql);
    }

    public Object perKapalMerkToko(Integer idKapal, String idMerkToko) {
        //case when ukuran not ilike '%x%' and ukuran not ilike '%paket%' and ukuran not ilike '' then '0'::text else ukuran end as 
        String sql = "select kota_tujuan, kondisi, \n"
                + "customer, kapal, tgl_berangkat, tgl_ind, merk, alamat, nomor_kontainer, emkl, \n"
                + "id, tanggal, pengirim, coli, jenis_barang, p, l, \n"
                + "t, paket, ukuran, "
                + "kubikasi, fix_volume, total_coli_sj, pisah, satuan_kirim, jml_kontainer  "
                + "from fn_pl_rpt_per_kapal_merk_toko(" + idKapal + ", ARRAY[" + idMerkToko + "]) as (kota_tujuan varchar, kondisi varchar, \n"
                + "customer varchar, kapal varchar, tgl_berangkat date, tgl_ind varchar, merk varchar, alamat varchar, nomor_kontainer varchar, emkl varchar, \n"
                + "id integer, tanggal date, pengirim varchar, coli integer, jenis_barang text, p double precision, l double precision, \n"
                + "t double precision, paket boolean ,ukuran text, kubikasi numeric, fix_volume double precision, total_coli_sj text, pisah boolean, satuan_kirim varchar, "
                + "jml_kontainer bigint)";
        System.out.println("perKapalMerkToko: " + sql);
        return mr.mapList(sql);
    }

    public Object perKapalMerkTokoPisahEmkl(Integer idKapal, String idMerkToko) {
        //case when ukuran not ilike '%x%' and ukuran not ilike '%paket%' and ukuran not ilike '' then '0'::text else ukuran end as 
        String sql = "select kota_tujuan, kondisi, \n"
                + "customer, kapal, tgl_berangkat, tgl_ind, merk, alamat, nomor_kontainer, emkl, \n"
                + "id, tanggal, pengirim, coli, jenis_barang, p, l, \n"
                + "t, paket, ukuran, "
                + "kubikasi, fix_volume, total_coli_sj, pisah, satuan_kirim, jml_kontainer  "
                + "from fn_pl_rpt_per_kapal_merk_toko_pisah_emkl(" + idKapal + ", ARRAY[" + idMerkToko + "]) as (kota_tujuan varchar, kondisi varchar, \n"
                + "customer varchar, kapal varchar, tgl_berangkat date, tgl_ind varchar, merk varchar, alamat varchar, nomor_kontainer varchar, emkl varchar, \n"
                + "id integer, tanggal date, pengirim varchar, coli integer, jenis_barang text, p double precision, l double precision, \n"
                + "t double precision, paket boolean ,ukuran text, kubikasi numeric, fix_volume double precision, total_coli_sj text, pisah boolean, satuan_kirim varchar, "
                + "jml_kontainer bigint)";
        System.out.println("perKapalMerkToko: " + sql);
        return mr.mapList(sql);
    }
    
    public Object perKapalMerkTokoPisahEmklHarga(Integer idKapal, String idMerkToko) {
        //case when ukuran not ilike '%x%' and ukuran not ilike '%paket%' and ukuran not ilike '' then '0'::text else ukuran end as 
        String sql = "select kota_tujuan, kondisi, \n"
                + "customer, kapal, tgl_berangkat, tgl_ind, merk, alamat, nomor_kontainer, emkl, \n"
                + "id, tanggal, pengirim, coli, jenis_barang, p, l, \n"
                + "t, paket, ukuran, "
                + "kubikasi, fix_volume, total_coli_sj, pisah, satuan_kirim, jml_kontainer, harga_satuan, "
                + "case when paket = true then coli*harga_satuan else kubikasi*harga_satuan end as total  "
                + "from fn_pl_rpt_per_kapal_merk_toko_pisah_emkl_harga(" + idKapal + ", ARRAY[" + idMerkToko + "]) as (kota_tujuan varchar, kondisi varchar, \n"
                + "customer varchar, kapal varchar, tgl_berangkat date, tgl_ind varchar, merk varchar, alamat varchar, nomor_kontainer varchar, emkl varchar, \n"
                + "id integer, tanggal date, pengirim varchar, coli integer, jenis_barang text, p double precision, l double precision, \n"
                + "t double precision, paket boolean ,ukuran text, kubikasi numeric, fix_volume double precision, total_coli_sj text, pisah boolean, satuan_kirim varchar, "
                + "jml_kontainer bigint, harga_satuan numeric)";
        System.out.println("perKapalMerkToko: " + sql);
        return mr.mapList(sql);
    }
    
    public Object perKapalMerkTokoPisahEmklHargaByNota(Integer idNota) {
        //case when ukuran not ilike '%x%' and ukuran not ilike '%paket%' and ukuran not ilike '' then '0'::text else ukuran end as 
        String sql = "select kota_tujuan, kondisi, \n"
                + "customer, kapal, tgl_berangkat, tgl_ind, merk, alamat, nomor_kontainer, emkl, \n"
                + "id, tanggal, pengirim, coli, jenis_barang, p, l, \n"
                + "t, paket, ukuran, "
                + "kubikasi, fix_volume, total_coli_sj, pisah, satuan_kirim, jml_kontainer, harga_satuan, "
                + "case when paket = true then coli*harga_satuan else kubikasi*harga_satuan end as total  "
                + "from fn_pl_rpt_per_kapal_merk_toko_pisah_emkl_harga_by_nota(" + idNota + ") as (kota_tujuan varchar, kondisi varchar, \n"
                + "customer varchar, kapal varchar, tgl_berangkat date, tgl_ind varchar, merk varchar, alamat varchar, nomor_kontainer varchar, emkl varchar, \n"
                + "id integer, tanggal date, pengirim varchar, coli integer, jenis_barang text, p double precision, l double precision, \n"
                + "t double precision, paket boolean ,ukuran text, kubikasi numeric, fix_volume double precision, total_coli_sj text, pisah boolean, satuan_kirim varchar, "
                + "jml_kontainer bigint, harga_satuan numeric)";
        System.out.println("perKapalMerkToko: " + sql);
        return mr.mapList(sql);
    }

    public Object perKapalMerkTokoPisahEmklFilter(Integer idKapal, String idMerkToko, String kotaTujuan, String customer, String tglBerangkat, String emkl) {
        //case when ukuran not ilike '%x%' and ukuran not ilike '%paket%' and ukuran not ilike '' then '0'::text else ukuran end as 
        String sql = "select kota_tujuan, kondisi, \n"
                + "customer, kapal, tgl_berangkat, tgl_ind, merk, alamat, nomor_kontainer, emkl, \n"
                + "id, tanggal, pengirim, coli, jenis_barang, p, l, \n"
                + "t, paket, ukuran, "
                + "kubikasi, fix_volume, total_coli_sj, pisah, satuan_kirim, jml_kontainer  "
                + "from fn_pl_rpt_per_kapal_merk_toko_pisah_emkl(" + idKapal + ", ARRAY[" + idMerkToko + "]) as (kota_tujuan varchar, kondisi varchar, \n"
                + "customer varchar, kapal varchar, tgl_berangkat date, tgl_ind varchar, merk varchar, alamat varchar, nomor_kontainer varchar, emkl varchar, \n"
                + "id integer, tanggal date, pengirim varchar, coli integer, jenis_barang text, p double precision, l double precision, \n"
                + "t double precision, paket boolean ,ukuran text, kubikasi numeric, fix_volume double precision, total_coli_sj text, pisah boolean, satuan_kirim varchar, "
                + "jml_kontainer bigint) where kota_tujuan = '" + kotaTujuan + "' and \n"
                + "customer= '" + customer + "' " + (tglBerangkat == null || tglBerangkat.equalsIgnoreCase("null") ? "and tgl_berangkat is null" : "and to_char(tgl_berangkat,'yyyy-mm-dd')= '" + tglBerangkat + "'") + " and emkl = '" + emkl + "' \n";
        System.out.println("perKapalMerkToko: " + sql);
        return mr.mapList(sql);
    }

    public Object perKapalMerkTokoPisahEmklDistinct(Integer idKapal, String idMerkToko) {
        String sql = "select distinct kota_tujuan, \n"
                + "customer, to_char(tgl_berangkat,'yyyy-mm-dd') tgl_berangkat, emkl \n"
                + "from fn_pl_rpt_per_kapal_merk_toko_pisah_emkl(" + idKapal + ", ARRAY[" + idMerkToko + "]) as (kota_tujuan varchar, kondisi varchar, \n"
                + "customer varchar, kapal varchar, tgl_berangkat date, tgl_ind varchar, merk varchar, alamat varchar, nomor_kontainer varchar, emkl varchar, \n"
                + "id integer, tanggal date, pengirim varchar, coli integer, jenis_barang text, p double precision, l double precision, \n"
                + "t double precision, paket boolean ,ukuran text, kubikasi numeric, fix_volume double precision, total_coli_sj text, pisah boolean, satuan_kirim varchar, "
                + "jml_kontainer bigint)";
        System.out.println("perKapalMerkToko: " + sql);
        return mr.mapList(sql);
    }

    public Object jmlContainerPerTujuan(String tahun, String bulan) {
        String sql = "select * from fn_jml_container_per_tujuan(" + tahun + ", " + bulan + ") as (jml_container bigint, id_kota int, kota varchar, tahun int, bulan varchar)";
        System.out.println("jmlContainerPerTujuan: " + sql);
        return mr.mapList(sql);
    }

    /**
     *
     * @param idNota
     * @return Data invoice
     */
    public Object getNota(String idNota) {
        String sql = "select * from fn_get_nota(" + idNota + ") as (id int, tanggal varchar, nomor varchar, min_bayar boolean, kepada text, kapal varchar, tgl_berangkat text, kota_tujuan varchar, jenis_item varchar, kondisi text, satuan_kirim varchar, volume double precision, harga double precision, jml_min_bayar double precision, tambahan_min_bayar numeric, no_kontainer varchar, jml_text text, idx int, tgl_kapal date, coli int)";
        System.out.println("getNota: " + sql);
        return mr.mapList(sql);
    }

    /**
     *
     * @param idPembayaranNota
     * @return Data invoice
     */
    public Object getPembayaranNota(String idPembayaranNota) {
        String sql = "select * from fn_get_pembayaran_nota(" + idPembayaranNota + ") as (id int, tgl_bayar varchar, no_pembayaran varchar, total_bayar numeric, kepada text, no_invoice varchar, tgl_invoice varchar, tagihan numeric, terbayar numeric, sisa numeric, bayar numeric, terbilang_total text,kapal_berangkat text)";
        System.out.println("getPembayaranNota: " + sql);
        return mr.mapList(sql);
    }

    /**
     *
     * @param idPembayaranNota
     * @return Data invoice
     */
    public Object getRincianNota(String idToko, String idMerk, String tglAwal, String tglAkhir) {
        String sql = "select *,total_tagihan-terbayar as sisa, fn_tanggal_ind(current_date) as tanggal, distinct_text(jenis_items,', ') as jenis_item from fn_get_rincian_nota(" + idToko + ", " + idMerk + ", '" + tglAwal + "', '" + tglAkhir + "') as (id int, nomor varchar, toko varchar, merk varchar, total_tagihan numeric, terbayar numeric, jenis_items text, kapal_berangkat text, tgl_awal_berangkat text, tgl_akhir_berangkat text, rincian_bayar text)";
        System.out.println("getRincianNota: " + sql);
        return mr.mapList(sql);
    }

    /**
     *
     * @param idPembayaranNota
     * @return Data invoice
     */
    public Object rekapNotaBelumLunas(String idToko, String idMerk, String tglAwal, String tglAkhir) {
        String sql = "select *,total_tagihan-terbayar as sisa, fn_tanggal_ind(current_date) as tanggal, distinct_text(jenis_items,', ') as jenis_item from fn_get_rincian_nota(" + idToko + ", " + idMerk + ", '" + tglAwal + "', '" + tglAkhir + "') as (id int, nomor varchar, toko varchar, merk varchar, total_tagihan numeric, terbayar numeric, jenis_items text, kapal_berangkat text, tgl_awal_berangkat text, tgl_akhir_berangkat text, rincian_bayar text) where total_tagihan>terbayar";
        System.out.println("getRincianNota: " + sql);
        return mr.mapList(sql);
    }

    public Object getRekapNotaTagihan(String idToko, String idMerk, String idKapal, String idKota, String tglAwal, String tglAkhir) {
        String sql = "select * from fn_rekap_nota_tagihan(" + idToko + ", " + idMerk + ", " + idKapal + ", " + idKota + ", '" + tglAwal + "', '" + tglAkhir + "') as (nomor varchar, min_bayar bool, jml_min_bayar double precision, jenis_item varchar, toko varchar, merk varchar, kapal varchar, no_kontainer varchar, tgl_berangkat date, total double precision, tanggal varchar)";
        System.out.println("getRekapNotaTagihan: " + sql);
        return mr.mapList(sql);
    }

    public Object getRekapPembayaran(String tglAwal, String tglAkhir, String kapalBerangkat) {
        String sql = "select *, fn_tanggal_ind(current_date) as tanggal from fn_rekap_pembayaran_per_berangkat('" + tglAwal + "', '" + tglAkhir + "', " + kapalBerangkat + ") as (nota_tagihan varchar, nota_bayar varchar, tgl_bayar date, kapal varchar, tgl_berangkat date, tagihan_kapal double precision, tot_tagihan_nota double precision, prosentase double precision, tot_terbayar numeric, terbayar_kapal double precision, semua_kapal text)";
        System.out.println("getRekapPembayaran: " + sql);
        return mr.mapList(sql);
    }

    /**
     *
     * @param idNota
     * @return
     */
    public Object getKapalBerangkatByNota(String idNota) {
        String sql = "select distinct kapal, tgl_berangkat from fn_get_nota(" + idNota + ") as (id int, tanggal varchar, nomor varchar, min_bayar boolean, kepada text, kapal varchar, tgl_berangkat text, kota_tujuan varchar, jenis_item varchar, kondisi text, satuan_kirim varchar, volume double precision, harga double precision, jml_min_bayar double precision, tambahan_min_bayar numeric, no_kontainer varchar, jml_text text, idx int, tgl_kapal date, coli int) where kapal <> 'Tambahan Biaya'";
        System.out.println("getKapalBerangkatByNota: " + sql);
        return mr.mapList(sql);
    }

    /**
     *
     * @return
     */
    public Object getRekening() {
        String sql = "select * from m_rekening where coalesce(tampil_di_report,false) order by urut";
        System.out.println("getRekening: " + sql);
        return mr.mapList(sql);
    }

    /**
     *
     * @param idNota
     * @return
     */
    public Object getKetJatuhTempoByNota(String idNota) {
        String sql = "select jt.nama from t_nota_ket_jatuh_tempo nk \n"
                + "join m_ket_jatuh_tempo jt on nk.id_ket_jatuh_tempo = jt.id\n"
                + "where id_nota = " + idNota + "\n"
                + "order by jt.urut";
        List<Map<String, Object>> mapList = mr.mapList(sql);
        if (mapList.isEmpty()) {
            sql = "select jt.nama from m_ket_jatuh_tempo jt \n"
                    + "where coalesce(is_default,false) \n"
                    + "order by jt.urut";
            mapList = mr.mapList(sql);
        }
        System.out.println("getKetJatuhTempoByNota: " + sql);
        return mapList;
    }

    /**
     *
     * @param idNota
     * @return
     */
    public Object getTotalTerbilangByNota(String idNota) {
        String sql = "select terbilang2nd(sum(case when min_bayar=true then case when tambahan_min_bayar>0 then tambahan_min_bayar + (harga*round(volume::numeric,3)) else (harga*round(volume::numeric,3)) end else (harga*round(volume::numeric,3)) end)::numeric)||' Rupiah' terbilang,sum(case when min_bayar=true then case when tambahan_min_bayar>0 then tambahan_min_bayar + (harga*round(volume::numeric,3)) else (harga*round(volume::numeric,3)) end else (harga*round(volume::numeric,3)) end)::numeric::int::double precision total from fn_get_nota(" + idNota + ") as (id int, tanggal varchar, nomor varchar, min_bayar boolean, kepada text, kapal varchar, tgl_berangkat text, kota_tujuan varchar, jenis_item varchar, kondisi text, satuan_kirim varchar, volume double precision, harga double precision, jml_min_bayar double precision, tambahan_min_bayar numeric, no_kontainer varchar, jml_text text, idx int, tgl_kapal date, coli int)";
        System.out.println("getTotalTerbilangByNota: " + sql);
        return mr.mapSingle(sql);
    }

    /**
     *
     * @param idNota
     * @return
     */
    public Object getTotalTerbilangByRincianNota(String idToko, String idMerk, String tglAwal, String tglAkhir) {
        String sql = "select coalesce(sum(coalesce(total_tagihan,0)),0) as total_tagihan, coalesce(sum(coalesce(terbayar,0)),0) as total_terbayar, coalesce(sum(coalesce(total_tagihan,0) - coalesce(terbayar,0)),0) as total_sisa_tagihan, terbilang2nd(coalesce(sum(coalesce(total_tagihan,0)),0)) as terbilang_total_tagihan, terbilang2nd(coalesce(sum(coalesce(terbayar,0)),0)) terbilang_total_terbayar, case when (coalesce(sum(coalesce(total_tagihan,0) - coalesce(terbayar,0)),0))<0 then 'Minus ' else '' end || terbilang2nd(abs(coalesce(sum(coalesce(total_tagihan,0) - coalesce(terbayar,0)),0))) as terbilang_total_sisa_tagihan from fn_get_rincian_nota(" + idToko + ", " + idMerk + ", '" + tglAwal + "', '" + tglAkhir + "') as (id int, nomor varchar, toko varchar, merk varchar, total_tagihan numeric, terbayar numeric, jenis_item text, kapal_berangkat text, tgl_awal_berangkat text, tgl_akhir_berangkat text, rincian_bayar text)";
        System.out.println("getTotalTerbilangByNota: " + sql);
        return mr.mapSingle(sql);
    }

    /**
     *
     * @param grup contoh : 'kota_tujuan', 'kondisi', 'customer', 'kapal',
     * 'nomor_kontainer', 'emkl', 'pengirim'
     * @param tglMulai format : yyyy-mm-dd
     * @param tglSampai format : yyyy-mm-dd
     * @param order contoh : 'grup_asc', 'grup_desc', 'coli_asc', 'coli_desc',
     * 'kubikasi_asc', 'kubikasi_desc'
     * @return
     */
    public Object rekapColiKubikasiPerGrup(String grup, String tglMulai, String tglSampai, String order, int limit) {
        String sql = "select * from fn_rekap_by_grup_new('" + grup + "'," + (tglMulai == null ? "null" : "'" + tglMulai + "'") + "," + (tglSampai == null ? "null" : "'" + tglSampai + "'") + ",'" + order + "') as (grup varchar, coli bigint, kubikasi numeric, kontainer bigint) limit " + limit;
        return mr.mapList(sql);
    }

    /**
     *
     * @param tglMulai format : yyyy-mm-dd
     * @param tglSampai format : yyyy-mm-dd
     * @param order contoh : 'grup_asc', 'grup_desc', 'coli_asc', 'coli_desc',
     * 'kubikasi_asc', 'kubikasi_desc'
     * @return
     */
    public Object rekapTagihanByGrup(String tglMulai, String tglSampai, String order, int limit) {
        String sql = "select * from fn_rekap_tagihan_by_grup_new('" + tglMulai + "','" + tglSampai + "','" + order + "') as (grup varchar, tagihan numeric, terbayar numeric, sisa numeric, pros_bayar numeric) limit " + limit;
        return mr.mapList(sql);
    }

    /**
     *
     * @param limit contoh : 6
     * @param bulanSampai format : yyyy-mm
     * @param toko contoh : 1165
     * @return
     */
    public Object historyTagihanToko(String limit, String bulanSampai, String idToko) {
        String sql = "select * from fn_get_history_tagihan_toko(" + idToko + ", '" + bulanSampai + "', " + limit + ") as (tahun_bulan text, str_label text, tagihan double precision)";
        return mr.mapList(sql);
    }
}
