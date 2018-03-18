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
import com.nls.dao.PembayaranDao;
import com.nls.dao.SatuanKirimDao;
import com.nls.dao.jdbc.LookupDao;
import com.nls.domain.KapalBerangkat;
import com.nls.domain.Pembayaran;
import com.nls.domain.PembayaranDetail;
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
@RequestMapping("api/transaksi/pembayaran-nota")
public class PembayaranController {

    @Autowired
    PembayaranDao dao;

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

    private final Logger logger = LoggerFactory.getLogger(PembayaranController.class);

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Iterable<Pembayaran> cariSemua() {
        return dao.findAll();
    }

    @RequestMapping(method = RequestMethod.GET)
    public Page<Pembayaran> saringSemua(
            @RequestParam(required = false) String search,
            Pageable pageable,
            HttpServletResponse respons) {
        PageRequest pr = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.Direction.ASC, "tanggal");
        Page<Pembayaran> result = dao.findAll(pr);
        return result;
    }

    @RequestMapping(value = "{column}/{value}", method = RequestMethod.GET)
    public Pembayaran cariSatu(@PathVariable String column, @PathVariable String value) {
        if (column.equalsIgnoreCase("kode")) {
            return dao.findOne(Integer.valueOf(value));
        } else {
            throw new InvalidParameterException("column '" + column + "' not available");
        }
    }

    @RequestMapping(value = "/{s:.+}", method = RequestMethod.GET)
    @ResponseBody
    public Page<Pembayaran> cariBerdasarkanNama(@PathVariable("s") String s,
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
        return lookupDao.lookupPembayaran((cari.equals("null") ? "" : "%" + cari.toUpperCase() + "%"), tglawal, tglakhir, pr);
    }
    
    @RequestMapping(value = "get-tagihan-terbayar/{idTokoTujuan}/{idMerkTujuan}/{status}", method = RequestMethod.GET)
    public Object genDetailPembayaran(@PathVariable String idTokoTujuan, @PathVariable String idMerkTujuan, @PathVariable String status) {
        List<Map<String, Object>> list = (List<Map<String, Object>>) lookupDao.lookupTagihanTerbayar(idTokoTujuan, idMerkTujuan, status);
        return list;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Pembayaran simpan(@RequestBody Pembayaran x) {
        if (x.getListDetail() != null) {
            for (PembayaranDetail c : x.getListDetail()) {
                c.setPembayaran(x);
            }
        }
        String idKapalBerangkat = "";
        int i = 0;
        if (!x.getNomorManual()) {
            String nomorPembayaran = lookupDao.getNomorPembayaran();
            x.setNomor(nomorPembayaran);
        }
        dao.save(x);
        return x;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Pembayaran perbarui(@PathVariable String id, @RequestBody Pembayaran x) {
        Pembayaran r = dao.findOne(Integer.valueOf(id));
        if (r == null) {
            throw new InvalidParameterException("Pembayaran dengan ID '" + x.getId() + "' tidak ditemukan!");
        }
        x.setId(r.getId());
        if (x.getListDetail() != null) {
            for (PembayaranDetail d : x.getListDetail()) {
                d.setPembayaran(x);
            }
        }
        if (x.getNomor() == null) {
            String nomorPembayaran = lookupDao.getNomorPembayaran();
            x.setNomor(nomorPembayaran);
        }
        dao.save(x);
        return x;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void hapus(@PathVariable String id) {
        Pembayaran x = dao.findOne(Integer.valueOf(id));
        if (x == null) {
            throw new InvalidParameterException("Nota '" + id + "' tidak ditemukan!");
        }
        dao.delete(x);
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public Long countAll() {
        return dao.count();
    }
}
