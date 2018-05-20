/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nls.dao.jdbc;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;

/**
 *
 * @author ustadho
 */
@Repository
public class LookupDao {

    @Autowired
    MapResultSet mr;
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(LookupDao.class);

    public Object lookupStuffingOpen(Integer idKota) {
        String sql = "select st.id, st.no_kontainer, kt.nama kota_tujuan, coalesce(k.nama,'') nama_kapal, k.id as id_kapal, kb.id as id_kapal_berangkat, kb.tgl_berangkat \n"
                + "from t_stuffing st \n"
                + "left join t_kapal_berangkat kb on kb.id=st.id_kapal_berangkat\n"
                + "left join m_kapal k on k.id=kb.id_kapal\n"
                + "left join m_kota kt on kt.id=kb.id_kota\n"
                + "where st.tgl_closing is null\n"
                + (idKota == 0 ? "" : "and st.id_kota=" + idKota + " \n")
                + "order by coalesce(st.no_kontainer,'')";

        return mr.mapList(sql);
    }

    public Object lookupTagihanTerbayar(String idToko, String idMerk, String status) {
        String sql = "select * from fn_get_tagihan_terbayar(" + idToko + ", " + idMerk + ", " + (status.equalsIgnoreCase("null") ? "null" : "'" + status + "'") + ") as (id int, nomor varchar, tanggal date, tagihan double precision, terbayar numeric)";

        return mr.mapList(sql);
    }

    public Object lookupTagihanTerbayar(String idToko, String idMerk, String status, String idNotas) {
        String sql = "select * from fn_get_tagihan_terbayar(" + idToko + ", " + idMerk + ", " + (status.equalsIgnoreCase("null") ? "null" : "'" + status + "'") + ") as (id int, nomor varchar, tanggal date, tagihan double precision, terbayar numeric) where id in (" + idNotas + ")";

        return mr.mapList(sql);
    }

    public Object lookupTagihanTerbayar(String idToko, String idMerk, String status, String cari, String tglAwal, String tglAkhir, PageRequest page) {
        ModelMap mm = new ModelMap();
        System.out.println("Page.Size: " + page.getPageSize());
        System.out.println("Page.Offset: " + page.getOffset());
        System.out.println("cari: " + cari);
        String query = "select distinct r.*, kb.tgl_berangkat,"
                + "  m.nama merk, m.id as id_merk,\n"
                + "  t.nama toko, t.id as id_toko \n"
                + " from fn_get_tagihan_terbayar(" + idToko + ", " + idMerk + ", " + (status.equalsIgnoreCase("null") ? "null" : "'" + status + "'") + ") as r (id int, nomor varchar, tanggal date, tagihan double precision, terbayar numeric) "
                + "  join t_nota n on n.id=r.id "
                + "  join public.t_nota_detail nd on nd.id_nota = n.id\n"
                + "  join public.t_kapal_berangkat kb on nd.id_kapal_berangkat = kb.id\n"
                + "  join public.m_toko t on n.id_toko = t.id \n"
                + "  left join public.m_merk m on n.id_merk = m.id\n"
                + "  where kb.tgl_berangkat between '" + tglAwal + "'::date and '" + tglAkhir + "'::date "
                + "  and coalesce(m.nama, '')|| coalesce(t.nama, '')|| coalesce(r.nomor, '') ilike '%" + cari + "%' "
                + "  order by r.tanggal \n";
        System.out.println("query all : " + query);
        Integer totalElement = mr.countRecordset(query);
        int totalPages = totalElement == 0 ? 0 : totalElement <= page.getPageSize() ? 1
                : (totalElement / page.getPageSize()) + (totalElement % page.getPageSize() >= 1 ? 1 : 0);
        boolean isFirstPage = totalPages == 0 || page.getPageNumber() == 0;
        boolean isLastPage = totalPages == 0 || page.getPageNumber() == 0;

        query = query + "limit " + page.getPageSize() + "\n"
                + "offset " + (page.getOffset());
        System.out.println("query paging : " + query);
        logger.warn("Query [{}]", query);
        mm.addAttribute("content", mr.mapList(query));
        mm.put("size", page.getPageSize());
        mm.put("totalElements", totalElement);
        mm.put("totalPages", totalPages);
        mm.put("number", page.getPageNumber());
        mm.put("firstPage", isFirstPage);
        mm.put("lastPage", isLastPage);
        return mm;

    }

    public Object lookupKapalBerangkatPerTokoMerkTujuan(String idToko, String idMerk) {
        String sql = "select * from fn_get_kapal_berangkat_by_toko_merk(" + (idToko == null || idToko.equalsIgnoreCase("null") ? "null" : idToko) + ", " + (idMerk == null || idMerk.equalsIgnoreCase("null") ? "null" : idMerk) + ") as (kapal varchar, tgl_berangkat date, tgl_ind varchar, nomor_kontainer varchar, id_kapal_berangkat int, sat_kirim varchar, kota varchar, id_toko int, id_merk int)";
        System.out.println("lookupKapalBerangkatPerTokoMerkTujuan : " + sql);
        return mr.mapList(sql);
    }

    public Object lookupNotaPerTokoMerkTujuan(String idToko, String idMerk) {
        String sql = "select * from fn_get_nota_by_toko_merk(" + (idToko == null || idToko.equalsIgnoreCase("null") ? "null" : idToko) + ", " + (idMerk == null || idMerk.equalsIgnoreCase("null") ? "null" : idMerk) + ") as r (id int, created_by varchar, created_date timestamp without time zone, last_modified_by varchar, last_modified_date timestamp without time zone, nomor varchar, tanggal date, id_toko int, min_bayar boolean, id_merk int, id_kota_asal int, total_tagihan numeric, kota_asal varchar)";
        System.out.println("lookupNotaPerTokoMerkTujuan : " + sql);
        return mr.mapList(sql);
    }

    public Object lookupDetailNotaPerTokoMerkTujuan(String idToko, String idMerk, String idKapalBerangkat) {
        String sql = "select * from fn_gen_detail_nota2(" + (idToko == null || idToko.equalsIgnoreCase("null") ? "null" : idToko) + ", " + (idMerk == null || idMerk.equalsIgnoreCase("null") ? "null" : idMerk) + ", " + (idKapalBerangkat.equalsIgnoreCase("null") ? idKapalBerangkat : "ARRAY[" + idKapalBerangkat + "]") + ") as (kota_tujuan varchar, kondisi varchar, customer varchar, kapal varchar, tgl_berangkat date, tgl_harga date, tgl_ind varchar, merk varchar, nomor_kontainer varchar, jenis_item varchar, id_jenis_item int, id_kategori_harga int, ukuran_kontainer varchar, id_kapal_berangkat int, id_merk int, id_toko int, paket boolean, sat_kirim varchar, id_kapal int, id_kondisi int, id_kota_asal int, kubikasi numeric, jml numeric, coli int, id_sj text, sat_kirim_ori varchar, sisipan bool, harga_satuan numeric)";
        System.out.println("lookupDetailNotaPerTokoMerkTujuan : " + sql);
        return mr.mapList(sql);
    }

    public Object lookupSubtotalDetailNotaPerTokoMerkTujuan(String idToko, String idMerk, String idKapalBerangkat) {
        String sql = "select id_kapal_berangkat, nomor_kontainer, jenis_item, sum(coalesce(harga_satuan,0)*coalesce(case when sat_kirim='FCL' then jml else kubikasi end,0)) subtotal from fn_gen_detail_nota2(" + (idToko == null || idToko.equalsIgnoreCase("null") ? "null" : idToko) + ", " + (idMerk == null || idMerk.equalsIgnoreCase("null") ? "null" : idMerk) + ", " + (idKapalBerangkat.equalsIgnoreCase("null") ? idKapalBerangkat : "ARRAY[" + idKapalBerangkat + "]") + ") as (kota_tujuan varchar, kondisi varchar, customer varchar, kapal varchar, tgl_berangkat date, tgl_harga date, tgl_ind varchar, merk varchar, nomor_kontainer varchar, jenis_item varchar, id_jenis_item int, id_kategori_harga int, ukuran_kontainer varchar, id_kapal_berangkat int, id_merk int, id_toko int, paket boolean, sat_kirim varchar, id_kapal int, id_kondisi int, id_kota_asal int, kubikasi numeric, jml numeric, coli int, id_sj text, sat_kirim_ori varchar, sisipan bool, harga_satuan numeric) group by id_kapal_berangkat,nomor_kontainer, jenis_item";
        System.out.println("lookupSubtotalDetailNotaPerTokoMerkTujuan : "+sql);
//        sql="select * from m_kota";
//        System.out.println("lookupSubtotalDetailNotaPerTokoMerkTujuan : "+sql);
        return mr.mapList(sql);
    }

    public String getNomorNota(Integer idKotaAsal, Integer idKotaTujuan) {
        String sql = "select fn_get_nomor_nota(" + idKotaAsal + "," + idKotaTujuan + ") as nomor";
        System.out.println("getNomorNota : "+sql);
        return mr.mapSingle(sql).get("nomor").toString();
    }

    public String getNomorSuratJalan(Integer idKotaAsal, Integer idKotaTujuan, String tanggal) {
        String sql = "select fn_get_nomor_sj(" + (idKotaAsal == null ? "null" : idKotaAsal) + "," + (idKotaTujuan == null ? "null" : idKotaTujuan) + ",'" + tanggal + "') as nomor";
//        return mr.mapSingle(sql).get("nomor").toString();
        return mr.mapSingle(sql).get("nomor") == null ? null : mr.mapSingle(sql).get("nomor").toString();
    }

    public String getNomorPembayaran() {
        String sql = "select fn_get_nomor_pembayaran() as nomor";
        return mr.mapSingle(sql).get("nomor").toString();
    }

    public Object lookupStuffingAktif(Integer idKota) {
        String sql = "select st.id, st.no_kontainer, kt.nama kota_tujuan, coalesce(k.nama,'') nama_kapal, k.id as id_kapal, kb.id as id_kapal_berangkat, kb.tgl_berangkat \n"
                + "from t_stuffing st \n"
                + "left join t_kapal_berangkat kb on kb.id=st.id_kapal_berangkat\n"
                + "left join m_kapal k on k.id=kb.id_kapal\n"
                + "left join m_kota kt on kt.id=kb.id_kota\n"
                + "where coalesce(st.aktif, true)=true \n"
                + (idKota == 0 ? "" : "and st.id_kota=" + idKota + " \n")
                + "order by coalesce(st.no_kontainer,'')";

        return mr.mapList(sql);
    }

    public Object lookupItem(String s) {
        String sql = "select i.id, coalesce(i.nama,'') nama, coalesce(u.p,0) as p, coalesce(u.l,0) l, coalesce(u.t,0) t \n"
                + "from m_item i \n"
                + "left join m_item_ukuran u on u.id_item=i.id\n"
                + "where aktif=true \n"
                + "and coalesce(i.nama,'') ilike '%" + s + "%' \n"
                + "order by coalesce(i.nama,''), coalesce(u.p,0), coalesce(u.l,0), coalesce(u.t,0) ";

        return mr.mapList(sql);
    }

    public Object lookupMerk(String s, Integer idkota) {
        String sql = "select m.id, m.nama merk, t.id id_toko, t.nama toko, t.alamat, k.nama nama_kota\n"
                + "from m_merk m \n"
                + "inner join m_toko t on t.id=m.id_toko\n"
                + "inner join m_kota k on k.id=t.id_kota\n"
                + "where t.id_kota=" + idkota + "\n"
                + "and coalesce(t.nama,'')||coalesce(m.nama,'') ilike '%" + s + "%' \n"
                + "order by t.nama, m.nama";

        return mr.mapList(sql);
    }

    public Object lookupToko(String s) {
        String sql = "select t.id, t.nama, t.alamat, k.nama nama_kota\n"
                + "from m_toko t \n"
                + "left join m_kota k on k.id=t.id_kota\n"
                + "where coalesce(t.nama,'')||coalesce(t.alamat,'') ilike '%" + s + "%' \n"
                //                + "and t.id not in(select id_toko from m_merk) \n"
                + "order by t.nama";

        return mr.mapList(sql);
    }

    public Object lookupKategoriHarga(String s) {
        String sql = "select t.id, t.nama, coalesce(t.ukuran_kontainer,'') ukuran_kontainer, coalesce(k.nama,'') satuan_kirim, coalesce(t.paket,false) as paket \n"
                + "from m_kategori_harga t \n"
                + "left join m_satuan_kirim k on k.id=t.id_satuan_kirim\n"
                + "where coalesce(t.nama,'')||coalesce(t.ukuran_kontainer,'')||coalesce(k.nama,'') ilike '%" + s + "%' \n"
                //                + "and t.id not in(select id_toko from m_merk) \n"
                + "order by t.nama";

        return mr.mapList(sql);
    }

    public Object lookupSuratJalan(String s) {
        String sql = "select t.id, t.nama, t.alamat, k.nama nama_kota\n"
                + "from m_toko t \n"
                + "left join m_kota k on k.id=t.id_kota\n"
                + "where coalesce(t.nama,'')||coalesce(t.alamat,'') ilike '%" + s + "%' \n"
                //                + "and t.id not in(select id_toko from m_merk) \n"
                + "order by t.nama";

        return mr.mapList(sql);
    }

    public Object lookupTokoPerKapalBerangkat(Integer id) {
        String sql = "select distinct t.id, t.nama, t.alamat, t.kontak, t.telepon\n"
                + "from t_kapal_berangkat kb \n"
                + "inner join t_stuffing st on st.id_kapal_berangkat=kb.id\n"
                + "inner join t_sj_stuffing sj on sj.id_stuffing=st.id\n"
                + "inner join t_surat_jalan_detail d on d.id=sj.id_sj_detail\n"
                + "inner join t_surat_jalan h on h.id=d.id_surat_jalan\n"
                + "inner join m_merk m on m.id=h.id_merk\n"
                + "inner join m_toko t on t.id=m.id_toko\n"
                + "where st.id_kapal_berangkat=" + id + "\n"
                + "order by t.nama";

        return mr.mapList(sql);
    }

    public Object lookupMerkTokoPerKapalBerangkat(Integer idKota, Integer id) {
        String sql = "select distinct t.id, t.nama, t.alamat, t.kontak, t.telepon, t.email, m.id as id_merk, m.nama as merk, false as terpilih \n"
                + "from t_stuffing st \n"
                + "inner join t_sj_stuffing sj on sj.id_stuffing=st.id\n"
                + "inner join t_surat_jalan_detail d on d.id=sj.id_sj_detail\n"
                + "inner join t_surat_jalan h on h.id=d.id_surat_jalan\n"
                + "inner join m_merk m on m.id=h.id_merk\n"
                + "inner join m_toko t on t.id=m.id_toko\n"
                + "left join t_kapal_berangkat kb on st.id_kapal_berangkat=kb.id\n"
                + "where "
                + "st.id_kota=" + idKota + " and \n"
                + "st.id_kapal_berangkat " + (id == 0 ? " is null " : "=" + id) + "\n"
                + "order by t.nama, m.nama";

        System.out.println("sql : " + sql);
        return mr.mapList(sql);
    }

    public Object lookupStuffingPerKapalBerangkat(Integer idKota, Integer id) {
        ModelMap mm = new ModelMap();
        System.out.println("cari: " + id);
        String query = "SELECT \n"
                + "  em.nama as nama_emkl, \n"
                + "  st.id, \n"
                + "  st.tgl_closing, \n"
                + "  st.aktif, \n"
                + "  kp.nama as nama_kapal, \n"
                + "  sk.nama as satuan_kirim, \n"
                + "  kb.tgl_berangkat, \n"
                + "  st.no_kontainer as nomor_kontainer, \n"
                + "  kt.nama as kota_tujuan\n"
                + "FROM \n"
                + "  public.t_stuffing st \n"
                + "  left join public.m_emkl em on st.id_emkl = em.id \n"
                + "  left join public.t_kapal_berangkat kb on st.id_kapal_berangkat = kb.id \n"
                + "  left join public.m_kota kt on st.id_kota = kt.id \n"
                + "  left join public.m_satuan_kirim sk on st.id_satuan_kirim = sk.id \n"
                + "  left join public.m_kapal kp on kb.id_kapal = kp.id\n"
                + "WHERE \n"
                + "st.id_kota=" + idKota + " and \n"
                + (id == 0 ? "st.id_kapal_berangkat is null " : "kb.id = " + id) + "\n"
                + "order by coalesce(kb.tgl_berangkat,'1985-11-11'::date) desc, kt.nama \n";
        logger.warn("Query [{}]", query);
        return mr.mapList(query);
    }

    public Object lookupStuffing(String cari, String tglAwal, String tglAkhir, PageRequest page) {
        ModelMap mm = new ModelMap();
        System.out.println("Page.Size: " + page.getPageSize());
        System.out.println("Page.Offset: " + page.getOffset());
        System.out.println("cari: " + cari);
        String query = "SELECT \n"
                + "  em.nama as nama_emkl, \n"
                + "  st.id, \n"
                + "  st.tgl_closing, \n"
                + "  st.aktif, \n"
                + "  kp.nama as nama_kapal, \n"
                + "  sk.nama as satuan_kirim, \n"
                + "  kb.tgl_berangkat, \n"
                + "  st.no_kontainer as nomor_kontainer, \n"
                + "  st.ukuran_kontainer, \n"
                + "  kt.nama as kota_tujuan\n"
                + "FROM \n"
                + "  public.t_stuffing st \n"
                + "  left join public.m_emkl em on st.id_emkl = em.id \n"
                + "  left join public.t_kapal_berangkat kb on st.id_kapal_berangkat = kb.id \n"
                //                + "  left join public.m_kontainer ko on st.id_kontainer = ko.id \n"
                + "  left join public.m_kota kt on st.id_kota = kt.id \n"
                + "  left join public.m_satuan_kirim sk on st.id_satuan_kirim = sk.id \n"
                + "  left join public.m_kapal kp on kb.id_kapal = kp.id\n"
                + "WHERE \n"
                + "  coalesce(kb.tgl_berangkat,current_date) between '" + tglAwal + "'::date and '" + tglAkhir + "'::date\n"
                + "  and coalesce(em.nama,'')||coalesce(kp.nama,'')||coalesce(sk.nama,'')||coalesce(st.no_kontainer,'')||coalesce(kt.nama,'') ilike '%" + cari + "%' "
                + "order by coalesce(kb.tgl_berangkat,'1985-11-11'::date) desc, kt.nama \n";
        System.out.println("query all : " + query);
        Integer totalElement = mr.countRecordset(query);
        int totalPages = totalElement == 0 ? 0 : totalElement <= page.getPageSize() ? 1
                : (totalElement / page.getPageSize()) + (totalElement % page.getPageSize() >= 1 ? 1 : 0);
        boolean isFirstPage = totalPages == 0 || page.getPageNumber() == 0;
        boolean isLastPage = totalPages == 0 || page.getPageNumber() == 0;

        query = query + "limit " + page.getPageSize() + "\n"
                + "offset " + (page.getOffset());
        System.out.println("query paging : " + query);
        logger.warn("Query [{}]", query);
        mm.addAttribute("content", mr.mapList(query));
        mm.put("size", page.getPageSize());
        mm.put("totalElements", totalElement);
        mm.put("totalPages", totalPages);
        mm.put("number", page.getPageNumber());
        mm.put("firstPage", isFirstPage);
        mm.put("lastPage", isLastPage);
        return mm;
    }

    public Object lookupNota(String cari, String tglAwal, String tglAkhir, PageRequest page) {
        ModelMap mm = new ModelMap();
        System.out.println("Page.Size: " + page.getPageSize());
        System.out.println("Page.Offset: " + page.getOffset());
        System.out.println("cari: " + cari);
        String query = "SELECT distinct n.id,\n"
                + "  m.nama merk, \n"
                + "  t.nama toko, \n"
                + "  k.nama kota, \n"
                + "  n.nomor, \n"
                + "  n.tanggal, \n"
                + "  coalesce(kb.tgl_berangkat,'1985-11-11'::date) as tgl_berangkat\n"
                + "FROM \n"
                + "  public.t_nota n \n"
                + "  join public.t_nota_detail nd on nd.id_nota = n.id\n"
                + "  join public.t_kapal_berangkat kb on nd.id_kapal_berangkat = kb.id\n"
                + "  join public.m_toko t on n.id_toko = t.id \n"
                + "  left join public.m_merk m on n.id_merk = m.id\n"
                + "  join m_kota k on k.id = t.id_kota\n"
                + "WHERE \n"
                + "  coalesce(kb.tgl_berangkat,current_date) between '" + tglAwal + "'::date and '" + tglAkhir + "'::date\n"
                + "  and coalesce(m.nama, '')|| coalesce(t.nama, '')|| coalesce(n.nomor, '') ilike '%" + cari + "%' "
                + "order by coalesce(kb.tgl_berangkat,'1985-11-11'::date) desc, n.tanggal desc \n";
        System.out.println("query all : " + query);
        Integer totalElement = mr.countRecordset(query);
        int totalPages = totalElement == 0 ? 0 : totalElement <= page.getPageSize() ? 1
                : (totalElement / page.getPageSize()) + (totalElement % page.getPageSize() >= 1 ? 1 : 0);
        boolean isFirstPage = totalPages == 0 || page.getPageNumber() == 0;
        boolean isLastPage = totalPages == 0 || page.getPageNumber() == 0;

        query = query + "limit " + page.getPageSize() + "\n"
                + "offset " + (page.getOffset());
        System.out.println("query paging : " + query);
        logger.warn("Query [{}]", query);
        mm.addAttribute("content", mr.mapList(query));
        mm.put("size", page.getPageSize());
        mm.put("totalElements", totalElement);
        mm.put("totalPages", totalPages);
        mm.put("number", page.getPageNumber());
        mm.put("firstPage", isFirstPage);
        mm.put("lastPage", isLastPage);
        return mm;
    }

    public Object lookupPembayaran(String cari, String tglAwal, String tglAkhir, PageRequest page) {
        ModelMap mm = new ModelMap();
        System.out.println("Page.Size: " + page.getPageSize());
        System.out.println("Page.Offset: " + page.getOffset());
        System.out.println("cari: " + cari);
        String query = "SELECT n.id,\n"
                + "  m.nama merk, \n"
                + "  t.nama toko, \n"
                + "  n.nomor, \n"
                + "  n.total_bayar, \n"
                + "  n.tanggal \n"
                + "FROM \n"
                + "  public.t_pembayaran_nota n \n"
                + "  join public.m_toko t on n.id_toko = t.id \n"
                + "  left join public.m_merk m on n.id_merk = m.id\n"
                + "WHERE \n"
                + "  coalesce(n.tanggal,current_date) between '" + tglAwal + "'::date and '" + tglAkhir + "'::date\n"
                + "  and coalesce(m.nama, '')|| coalesce(t.nama, '')|| coalesce(n.nomor, '') ilike '%" + cari + "%' "
                + "order by n.tanggal desc \n";
        System.out.println("query all : " + query);
        Integer totalElement = mr.countRecordset(query);
        int totalPages = totalElement == 0 ? 0 : totalElement <= page.getPageSize() ? 1
                : (totalElement / page.getPageSize()) + (totalElement % page.getPageSize() >= 1 ? 1 : 0);
        boolean isFirstPage = totalPages == 0 || page.getPageNumber() == 0;
        boolean isLastPage = totalPages == 0 || page.getPageNumber() == 0;

        query = query + "limit " + page.getPageSize() + "\n"
                + "offset " + (page.getOffset());
        System.out.println("query paging : " + query);
        logger.warn("Query [{}]", query);
        mm.addAttribute("content", mr.mapList(query));
        mm.put("size", page.getPageSize());
        mm.put("totalElements", totalElement);
        mm.put("totalPages", totalPages);
        mm.put("number", page.getPageNumber());
        mm.put("firstPage", isFirstPage);
        mm.put("lastPage", isLastPage);
        return mm;
    }

    public Object lookupSuratJalan(String cari, String idKota, String tglAwal, String tglAkhir, String statusNota, PageRequest page) {
        ModelMap mm = new ModelMap();
        System.out.println("Page.Size: " + page.getPageSize());
        System.out.println("Page.Offset: " + page.getOffset());
        System.out.println("cari: " + cari);
        String query = "SELECT \n"
                + "  sj.id, \n"
                + "  tk.nama as pengirim, \n"
                + "  sj.tanggal, \n"
                + "  m.nama merk, m.id as id_merk_tujuan,\n"
                + "  tm.nama toko_tujuan, tm.id as id_toko_tujuan,\n"
                + "  km.nama kota_tujuan, \n"
                + "  sj.nomor as no_sj, \n"
                + "  st.no_kontainer as no_kontainer,\n"
                + "  st.id id_stuffing, sj.indeks, st.id_kapal_berangkat, kb.tgl_berangkat, k.nama as kapal, \n"
                + "  sj.id in (select distinct unnest(string_to_array(string_agg(id_sj,','),','))::int from t_nota_detail where id_sj is not null) ada_nota \n"
                + "FROM \n"
                + "  public.t_surat_jalan sj \n"
                + "  left join public.m_merk m  on sj.id_merk = m.id\n"
                + "  left join public.m_toko tm on m.id_toko = tm.id   \n"
                + "  left join public.m_kota km on tm.id_kota = km.id \n"
                + "  left join public.m_toko tk on sj.id_toko = tk.id\n"
                + "  left join public.t_stuffing st on sj.id_stuffing= st.id\n"
                + "  left join public.t_kapal_berangkat kb on st.id_kapal_berangkat= kb.id\n"
                + "  left join public.m_kapal k on kb.id_kapal = k.id\n"
                + "WHERE \n"
                + "  sj.tanggal between '" + tglAwal + "'::date and '" + tglAkhir + "'::date\n"
                + "  and case when 0 = " + idKota + " then true else km.id = " + idKota + " end\n"
                + "  and case when 'Semua' = '" + statusNota + "' then true \n"
                + "  when 'Sudah' = '" + statusNota + "' then sj.id in (select distinct unnest(string_to_array(string_agg(id_sj,','),','))::int from t_nota_detail where id_sj is not null) \n"
                + "  when 'Belum' = '" + statusNota + "' then sj.id not in (select distinct unnest(string_to_array(string_agg(id_sj,','),','))::int from t_nota_detail where id_sj is not null) \n"
                + "  end\n"
                //                + "  and tk.nama||m.nama||tm.nama||km.nama||sj.nomor ilike '%" + cari + "%' order by sj.tanggal, tm.nama \n";
                + "  and coalesce(tk.nama,'')||coalesce(m.nama,'')||coalesce(tm.nama,'')||coalesce(km.nama,'')||coalesce(sj.nomor,'')||coalesce(st.no_kontainer,'')||coalesce(sj.indeks,'') ilike '%" + cari + "%' order by coalesce(sj.indeks,''), sj.tanggal, tm.nama \n";
        System.out.println("query all : " + query);
        Integer totalElement = mr.countRecordset(query);
        int totalPages = totalElement == 0 ? 0 : totalElement <= page.getPageSize() ? 1
                : (totalElement / page.getPageSize()) + (totalElement % page.getPageSize() >= 1 ? 1 : 0);
        boolean isFirstPage = totalPages == 0 || page.getPageNumber() == 0;
        boolean isLastPage = totalPages == 0 || page.getPageNumber() == 0;

        query = query + "limit " + page.getPageSize() + "\n"
                + "offset " + (page.getOffset());
        System.out.println("query paging : " + query);
        logger.warn("Query [{}]", query);
        mm.addAttribute("content", mr.mapList(query));
        mm.put("size", page.getPageSize());
        mm.put("totalElements", totalElement);
        mm.put("totalPages", totalPages);
        mm.put("number", page.getPageNumber());
        mm.put("firstPage", isFirstPage);
        mm.put("lastPage", isLastPage);
        return mm;
    }

    public Object lookupInfoItem(String cari, String idToko, String tglAwal, String tglAkhir, PageRequest page) {
        ModelMap mm = new ModelMap();
        System.out.println("Page.Size: " + page.getPageSize());
        System.out.println("Page.Offset: " + page.getOffset());
        System.out.println("cari: " + cari);
        String query = "SELECT \n"
                + "  sj.id, \n"
                + "  sj.id_stuffing, \n"
                + "  tk.nama as pengirim, \n"
                + "  sj.tanggal, \n"
                + "  m.nama merk, \n"
                + "  i.nama as item,\n"
                + "  tm.nama toko_tujuan, \n"
                + "  km.nama kota_tujuan, \n"
                + "  sj.nomor as no_sj, \n"
                + "  st.no_kontainer as no_kontainer,\n"
                + "  k.nama as kapal, \n  "
                + "  sjd.coli,\n"
                + "  round(hitung_subtotal_metrik(sjd.id)::numeric,3) as kubikasi, sj.indeks "
                + "FROM \n"
                + "  public.t_surat_jalan sj \n"
                + "  join t_surat_jalan_detail sjd on sjd.id_surat_jalan = sj.id\n"
                + "  join m_item i on i.id = sjd.id_item\n"
                + "  left join public.m_merk m  on sj.id_merk = m.id\n"
                + "  left join public.m_toko tm on m.id_toko = tm.id   \n"
                + "  left join public.m_kota km on tm.id_kota = km.id \n"
                + "  left join public.m_toko tk on sj.id_toko = tk.id\n"
                + "  left join public.t_stuffing st on sj.id_stuffing= st.id\n"
                + "  left join t_kapal_berangkat kb on st.id_kapal_berangkat = kb.id\n"
                + "  left join m_kapal k on k.id = kb.id_kapal\n"
                + "WHERE \n"
                + "  sj.tanggal between '" + tglAwal + "'::date and '" + tglAkhir + "'::date\n"
                + "  and case when " + idToko + " = 0 then true else tm.id = " + idToko + " end  \n"
                + "  and coalesce(tk.nama,'')||coalesce(m.nama,'')||coalesce(tm.nama,'')||coalesce(km.nama,'')||coalesce(sj.nomor,'')||coalesce(st.no_kontainer,'')||coalesce(i.nama,'') ilike '%" + cari + "%' order by sj.tanggal, tm.nama \n";
        System.out.println("query all : " + query);
        Integer totalElement = mr.countRecordset(query);
        int totalPages = totalElement == 0 ? 0 : totalElement <= page.getPageSize() ? 1
                : (totalElement / page.getPageSize()) + (totalElement % page.getPageSize() >= 1 ? 1 : 0);
        boolean isFirstPage = totalPages == 0 || page.getPageNumber() == 0;
        boolean isLastPage = totalPages == 0 || page.getPageNumber() == 0;

        query = query + "limit " + page.getPageSize() + "\n"
                + "offset " + (page.getOffset());
        System.out.println("query paging : " + query);
        logger.warn("Query [{}]", query);
        mm.addAttribute("content", mr.mapList(query));
        mm.put("size", page.getPageSize());
        mm.put("totalElements", totalElement);
        mm.put("totalPages", totalPages);
        mm.put("number", page.getPageNumber());
        mm.put("firstPage", isFirstPage);
        mm.put("lastPage", isLastPage);
        return mm;
    }

    public Object lookupKapalBerangkat(String cari, String idKota, String idKapal, String tglAwal, String tglAkhir, PageRequest page) {
        ModelMap mm = new ModelMap();
        System.out.println("Page.Size: " + page.getPageSize());
        System.out.println("Page.Offset: " + page.getOffset());
        System.out.println("cari: " + cari);
        String query = "SELECT \n"
                + "  kt.nama as kota, \n"
                + "  kp.nama as kapal, \n"
                + "  kb.tgl_berangkat, \n"
                + "  kb.aktif,\n"
                + "  kb.id,\n"
                + "  kb.no_voyage\n"
                + "FROM \n"
                + "  public.t_kapal_berangkat kb\n"
                + "  left join public.m_kapal kp on kb.id_kapal = kp.id\n"
                + "  left join public.m_kota kt on kb.id_kota = kt.id\n"
                + "WHERE \n"
                + "     coalesce(kt.nama,'')||coalesce(kp.nama,'') ilike '%" + cari + "%' \n"
                + "     and case when " + idKapal + " = 0 then true else kp.id = " + idKapal + " end\n"
                + "     and case when " + idKota + " = 0 then true else kt.id = " + idKota + " end"
                + "     and "
                + "  kb.tgl_berangkat between '" + tglAwal + "'::date and '" + tglAkhir + "'::date\n" + ""
                + " order by kb.tgl_berangkat, kt.nama\n";
        System.out.println("query all : " + query);
        Integer totalElement = mr.countRecordset(query);
        int totalPages = totalElement == 0 ? 0 : totalElement <= page.getPageSize() ? 1
                : (totalElement / page.getPageSize()) + (totalElement % page.getPageSize() >= 1 ? 1 : 0);
        boolean isFirstPage = totalPages == 0 || page.getPageNumber() == 0;
        boolean isLastPage = totalPages == 0 || page.getPageNumber() == 0;

        query = query + "limit " + page.getPageSize() + "\n"
                + "offset " + (page.getOffset());
        System.out.println("query paging : " + query);
        logger.warn("Query [{}]", query);
        mm.addAttribute("content", mr.mapList(query));
        mm.put("size", page.getPageSize());
        mm.put("totalElements", totalElement);
        mm.put("totalPages", totalPages);
        mm.put("number", page.getPageNumber());
        mm.put("firstPage", isFirstPage);
        mm.put("lastPage", isLastPage);
        return mm;
    }

    public Object lookupEmkl(String cari, String idKota, PageRequest page) {
        ModelMap mm = new ModelMap();
        System.out.println("Page.Size: " + page.getPageSize());
        System.out.println("Page.Offset: " + page.getOffset());
        System.out.println("cari: " + cari);
        String query = "SELECT \n"
                + "  m_emkl.id, \n"
                + "  m_emkl.nama, \n"
                + "  m_kota.nama as kota, \n"
                + "  m_emkl.alamat, \n"
                + "  m_emkl.kontak, \n"
                + "  m_emkl.telepon\n"
                + "FROM \n"
                + "  public.m_emkl \n"
                + "  left join  public.m_kota on m_emkl.id_kota = m_kota.id\n"
                + "WHERE \n"
                + "    coalesce(m_emkl.nama,'') || coalesce(m_kota.nama,'') || coalesce(m_emkl.alamat,'') \n"
                + "    || coalesce(m_emkl.kontak,'') ||  coalesce(m_emkl.telepon,'') ilike '%" + cari + "%' \n"
                + "    and case when " + idKota + " = 0 then true else m_emkl.id_kota = " + idKota + " end "
                + " order by m_emkl.nama \n";
        System.out.println("query all : " + query);
        Integer totalElement = mr.countRecordset(query);
        int totalPages = totalElement == 0 ? 0 : totalElement <= page.getPageSize() ? 1
                : (totalElement / page.getPageSize()) + (totalElement % page.getPageSize() >= 1 ? 1 : 0);
        boolean isFirstPage = totalPages == 0 || page.getPageNumber() == 0;
        boolean isLastPage = totalPages == 0 || page.getPageNumber() == 0;

        query = query + "limit " + page.getPageSize() + "\n"
                + "offset " + (page.getOffset());
        System.out.println("query paging : " + query);
        logger.warn("Query [{}]", query);
        mm.addAttribute("content", mr.mapList(query));
        mm.put("size", page.getPageSize());
        mm.put("totalElements", totalElement);
        mm.put("totalPages", totalPages);
        mm.put("number", page.getPageNumber());
        mm.put("firstPage", isFirstPage);
        mm.put("lastPage", isLastPage);
        return mm;
    }

    public Object lookupBahanSj(String cari, Boolean outstand, String tglAwal, String tglAkhir, PageRequest page) {
        ModelMap mm = new ModelMap();
        System.out.println("Page.Size: " + page.getPageSize());
        System.out.println("Page.Offset: " + page.getOffset());
        System.out.println("cari: " + cari);
        String query = "SELECT * from t_bahan_sj \n"
                + "WHERE \n"
                + "  coalesce(keterangan,'') ilike '%" + cari + "%' \n"
                + "  and "
                + "  tgl_ins between '" + tglAwal + "'::date and '" + tglAkhir + "'::date\n"
                + "  and "
                + "  case when " + outstand + " = true then id not in (select id_bahan from t_surat_jalan) else true end "
                + " order by tgl_ins\n";
        System.out.println("query all : " + query);
        Integer totalElement = mr.countRecordset(query);
        int totalPages = totalElement == 0 ? 0 : totalElement <= page.getPageSize() ? 1
                : (totalElement / page.getPageSize()) + (totalElement % page.getPageSize() >= 1 ? 1 : 0);
        boolean isFirstPage = totalPages == 0 || page.getPageNumber() == 0;
        boolean isLastPage = totalPages == 0 || page.getPageNumber() == 0;

        query = query + "limit " + page.getPageSize() + "\n"
                + "offset " + (page.getOffset());
        System.out.println("query paging : " + query);
        logger.warn("Query [{}]", query);
        mm.addAttribute("content", mr.mapList(query));
        mm.put("size", page.getPageSize());
        mm.put("totalElements", totalElement);
        mm.put("totalPages", totalPages);
        mm.put("number", page.getPageNumber());
        mm.put("firstPage", isFirstPage);
        mm.put("lastPage", isLastPage);
        return mm;
    }

    public Object lookupToko(String cari, String idKota, PageRequest page) {
        ModelMap mm = new ModelMap();
        System.out.println("Page.Size: " + page.getPageSize());
        System.out.println("Page.Offset: " + page.getOffset());
        System.out.println("cari: " + cari);
        String query = "SELECT \n"
                + "  m_toko.id, \n"
                + "  m_toko.nama, \n"
                + "  m_kota.nama as kota, \n"
                + "  m_toko.alamat, \n"
                + "  m_toko.kontak, \n"
                + "  m_toko.telepon\n"
                + "FROM \n"
                + "  public.m_toko \n"
                + "  left join  public.m_kota on m_toko.id_kota = m_kota.id\n"
                + "WHERE \n"
                + "    coalesce(m_toko.nama,'') || coalesce(m_kota.nama,'') || coalesce(m_toko.alamat,'') \n"
                + "    || coalesce(m_toko.kontak,'') ||  coalesce(m_toko.telepon,'') ilike '%" + cari + "%' \n"
                + "    and case when " + idKota + " = 0 then true else m_toko.id_kota = " + idKota + " end "
                + " order by m_toko.nama \n";
        System.out.println("query all : " + query);
        Integer totalElement = mr.countRecordset(query);
        int totalPages = totalElement == 0 ? 0 : totalElement <= page.getPageSize() ? 1
                : (totalElement / page.getPageSize()) + (totalElement % page.getPageSize() >= 1 ? 1 : 0);
        boolean isFirstPage = totalPages == 0 || page.getPageNumber() == 0;
        boolean isLastPage = totalPages == 0 || page.getPageNumber() == 0;

        query = query + "limit " + page.getPageSize() + "\n"
                + "offset " + (page.getOffset());
        System.out.println("query paging : " + query);
        logger.warn("Query [{}]", query);
        mm.addAttribute("content", mr.mapList(query));
        mm.put("size", page.getPageSize());
        mm.put("totalElements", totalElement);
        mm.put("totalPages", totalPages);
        mm.put("number", page.getPageNumber());
        mm.put("firstPage", isFirstPage);
        mm.put("lastPage", isLastPage);
        return mm;
    }

    public Object lookupSjStuffing(String idSjDetail) {
        ModelMap mm = new ModelMap();
        String query = "SELECT \n"
                + "  sjst.coli, \n"
                + "  sjst.id_sj_detail, \n"
                + "  sjst.id, \n"
                + "  sjst.id_stuffing, \n"
                + "  st_b.no_kontainer as no_kontainer_b, \n"
                + "  kp_b.nama as nama_kapal_b, \n"
                + "  kb_b.tgl_berangkat as tgl_berangkat_b, \n"
                + "  sjd.id_surat_jalan, \n"
                + "  kb_a.tgl_berangkat as tgl_berangkat_a, \n"
                + "  kp_a.nama as nama_kapal_a, \n"
                + "  st_a.no_kontainer as nomor_kontainer_a\n"
                + "FROM \n"
                + "  public.t_sj_stuffing sjst \n"
                + "  left join public.t_stuffing st_b on sjst.id_stuffing = st_b.id \n"
                + "  left join public.t_kapal_berangkat kb_b on st_b.id_kapal_berangkat = kb_b.id \n"
                + "  left join public.m_kapal kp_b on kb_b.id_kapal = kp_b.id\n"
                + "  left join public.t_surat_jalan_detail sjd on sjst.id_sj_detail = sjd.id\n"
                + "  left join public.t_surat_jalan sj on sjd.id_surat_jalan = sj.id\n"
                + "  left join public.t_stuffing st_a on sj.id_stuffing = st_a.id\n"
                + "  left join public.t_kapal_berangkat kb_a on st_a.id_kapal_berangkat = kb_a.id  \n"
                + "  left join public.m_kapal kp_a on kb_a.id_kapal = kp_a.id \n"
                + "WHERE \n"
                + "  sjst.id_sj_detail = " + idSjDetail;
        return mr.mapList(query);
    }
}
