/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nls.web;

import com.nls.constant.UkuranKontainer;
import com.nls.constant.UkuranPaket;
import com.nls.dao.JenisItemDao;
import com.nls.dao.KapalBerangkatDao;
import com.nls.dao.KondisiDao;
import com.nls.dao.NotaDao;
import com.nls.dao.SatuanKirimDao;
import com.nls.dao.jdbc.LookupDao;
import com.nls.domain.KapalBerangkat;
import com.nls.domain.Nota;
import com.nls.domain.NotaDetail;
import com.nls.domain.NotaKapalBerangkat;
import com.nls.domain.NotaTambahanBiaya;
import com.nls.service.AppService;
import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ustadho
 */
@CrossOrigin
@RestController
@RequestMapping("api/transaksi/nota")
public class NotaController {

    @Autowired
    NotaDao dao;

    @Autowired
    KapalBerangkatDao kapalBerangkatDao;

    @Autowired
    JenisItemDao jenisItemDao;

    @Autowired
    KondisiDao kondisiDao;

    @Autowired
    SatuanKirimDao satuanKirimDao;

    @Autowired
    LookupDao lookupDao;
    
    private final Logger logger = LoggerFactory.getLogger(NotaController.class);

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Iterable<Nota> cariSemua() {
        return dao.findAll();
    }

    @RequestMapping(method = RequestMethod.GET)
    public Page<Nota> saringSemua(
            @RequestParam(required = false) String search,
            Pageable pageable,
            HttpServletResponse respons) {
        PageRequest pr = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.Direction.ASC, "tanggal");
        Page<Nota> result = dao.findAll(pr);
        return result;
    }

    @RequestMapping(value = "{column}/{value}", method = RequestMethod.GET)
    public Nota cariSatu(@PathVariable String column, @PathVariable String value) {
        if (column.equalsIgnoreCase("kode")) {
            return dao.findOne(Integer.valueOf(value));
        } else {
            throw new InvalidParameterException("column '" + column + "' not available");
        }
    }

    @RequestMapping(value = "/{s:.+}", method = RequestMethod.GET)
    @ResponseBody
    public Page<Nota> cariBerdasarkanNama(@PathVariable("s") String s,
            Pageable pageable,
            HttpServletResponse response) {
        PageRequest pr = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.Direction.ASC, "tanggal");
        return dao.filter("%" + s.toUpperCase() + "%", pr);
    }

    @RequestMapping(value = "/tglawal/tglakhir/cari/{tglawal}/{tglakhir}/{cari:.+}", method = RequestMethod.GET)
    @ResponseBody
    public Object cariComposite(@PathVariable("tglawal") String tglawal, @PathVariable("tglakhir") String tglakhir, @PathVariable("cari") String cari,
            Pageable pageable,
            HttpServletResponse response) {
        PageRequest pr = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.Direction.ASC, "tanggal");
        return lookupDao.lookupNota((cari.equals("null") ? "" : "%" + cari.toUpperCase() + "%"), tglawal, tglakhir, pr);
    }

    @RequestMapping(value = "gen-detail-nota/{idTokoTujuan}/{idMerkTujuan}/{idKapalBerangkat}", method = RequestMethod.GET)
    public Object genDetailNota(@PathVariable String idTokoTujuan, @PathVariable String idMerkTujuan, @PathVariable String idKapalBerangkat) {
        List<NotaDetail> list = new ArrayList<>();
        List<Map<String, Object>> lookupDetailNotaPerTokoMerkTujuan = (List<Map<String, Object>>) lookupDao.lookupDetailNotaPerTokoMerkTujuan(idTokoTujuan, idMerkTujuan, idKapalBerangkat);
        for (Map<String, Object> x : lookupDetailNotaPerTokoMerkTujuan) {
            NotaDetail y = new NotaDetail();
            y.setUkuranKontainer(x.get("ukuran_kontainer") == null ? null : UkuranKontainer.valueOf((String) x.get("ukuran_kontainer")));
            y.setSatuanKirim(satuanKirimDao.findByNama((String) x.get("sat_kirim")));
            y.setNoKontainer((String) x.get("nomor_kontainer"));
            y.setKondisi(x.get("id_kondisi") == null ? null : kondisiDao.findOne((Integer) x.get("id_kondisi")));
            y.setKapalBerangkat(x.get("id_kapal_berangkat") == null ? null : kapalBerangkatDao.findOne((Integer) x.get("id_kapal_berangkat")));
            y.setJenisItem(x.get("id_jenis_item") == null ? null : jenisItemDao.findOne((Integer) x.get("id_jenis_item")));
            y.setVolume(((String) x.get("sat_kirim")).equalsIgnoreCase("LCL") ? (BigDecimal) x.get("kubikasi") : (BigDecimal) x.get("jml"));
            y.setHarga((BigDecimal) x.get("harga_satuan"));
            list.add(y);
        }
        return list;
    }

    @RequestMapping(value = "gen-subtotal-detail-nota/{idTokoTujuan}/{idMerkTujuan}/{idKapalBerangkat}", method = RequestMethod.GET)
    public Object genSubtotalDetailNota(@PathVariable String idTokoTujuan, @PathVariable String idMerkTujuan, @PathVariable String idKapalBerangkat) {
        return lookupDao.lookupSubtotalDetailNotaPerTokoMerkTujuan(idTokoTujuan, idMerkTujuan, idKapalBerangkat);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void hapus(@PathVariable String id) {
        Nota x = dao.findOne(Integer.valueOf(id));
        if (x == null) {
            throw new InvalidParameterException("Nota '" + id + "' tidak ditemukan!");
        }
        dao.delete(x);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Nota simpan(@RequestBody Nota x) {
        if (x.getListDetail() != null) {
            for (NotaDetail c : x.getListDetail()) {
                c.setNota(x);
            }
        }
        if (x.getListTambahanBiaya() != null) {
            for (NotaTambahanBiaya c : x.getListTambahanBiaya()) {
                c.setNota(x);
            }
        }
        if (x.getListKapalBerangkat() != null) {
            for (NotaKapalBerangkat c : x.getListKapalBerangkat()) {
                c.setNota(x);
            }
        }
        String idKapalBerangkat = "";
        int i = 0;
        if (!x.getNomorManual()) {
            String nomorNota = lookupDao.getNomorNota(x.getKotaAsal().getId(), x.getTokoTujuan().getKota().getId());
            x.setNomorInvoice(nomorNota);
        }
        dao.save(x);
        return x;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Nota perbarui(@PathVariable String id, @RequestBody Nota x) {
        Nota r = dao.findOne(Integer.valueOf(id));
        if (r == null) {
            throw new InvalidParameterException("Nota dengan ID '" + x.getId() + "' tidak ditemukan!");
        }
        x.setId(r.getId());
        if (x.getListDetail() != null) {
            for (NotaDetail d : x.getListDetail()) {
                d.setNota(x);
            }
        }
        if (x.getListTambahanBiaya() != null) {
            for (NotaTambahanBiaya c : x.getListTambahanBiaya()) {
                c.setNota(x);
            }
        }
        if (x.getListKapalBerangkat() != null) {
            for (NotaKapalBerangkat c : x.getListKapalBerangkat()) {
                c.setNota(x);
            }
        }
        if (x.getNomorInvoice() == null) {
            String nomorNota = lookupDao.getNomorNota(x.getKotaAsal().getId(), x.getTokoTujuan().getKota().getId());
            x.setNomorInvoice(nomorNota);
        }
        dao.save(x);
        return x;
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public Long countAll() {
        return dao.count();
    }
}
