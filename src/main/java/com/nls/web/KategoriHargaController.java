/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nls.web;

import com.nls.dao.KategoriHargaDao;
import com.nls.dao.jdbc.LookupDao;
import com.nls.domain.KategoriHarga;
import java.security.InvalidParameterException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
//import org.springframework.stereotype.Controller;
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
 * @author faheem
 */
@CrossOrigin
@RestController
@RequestMapping("/api/master/kategori-harga")
public class KategoriHargaController {

    @Autowired
    KategoriHargaDao dao;

    @Autowired
    LookupDao lookupDao;

    private final Logger logger = LoggerFactory.getLogger(KategoriHargaController.class);

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Iterable<KategoriHarga> cariSemua() {
        return dao.findAll();
    }

    @RequestMapping(method = RequestMethod.GET)
    public Page<KategoriHarga> saringSemua(
            @RequestParam(required = false) String search,
            Pageable pageable,
            HttpServletResponse respons) {
        PageRequest pr = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.Direction.ASC, "nama");
        Page<KategoriHarga> result = dao.findAll(pr);
        return result;
    }

    @RequestMapping(value = "/filter-paket-satuan-kirim/{paket}/{satuanKirim}", method = RequestMethod.GET)
    public List<KategoriHarga> filterByPaketSatuanKirim(@PathVariable("paket") Boolean paket, @PathVariable("satuanKirim") String satuanKirim,
            HttpServletResponse respons) {
        List<KategoriHarga> result = dao.filterByPaketSatuanKirim(paket, satuanKirim);
        return result;
    }
    
    @RequestMapping(value = "/kategori/lookup/{s}", method = RequestMethod.GET)
    public Object lookupKategoriHarga(@PathVariable("s") String s) {
        return lookupDao.lookupKategoriHarga(s.equalsIgnoreCase("null") ? "" : s);
    }

    @RequestMapping(value = "{column}/{value}", method = RequestMethod.GET)
    public KategoriHarga cariSatu(@PathVariable String column, @PathVariable String value) {
        if (column.equalsIgnoreCase("kode")) {
            return dao.findOne(Integer.valueOf(value));
        } else if (column.equalsIgnoreCase("nama")) {
            return dao.findByNama(value);
        } else {
            throw new InvalidParameterException("column '" + column + "' not available");
        }
    }

    @RequestMapping(value = "/{nama}", method = RequestMethod.GET)
    @ResponseBody
    public Page<KategoriHarga> cariBerdasarkanNama(@PathVariable("nama") String nama,
            Pageable pageable,
            HttpServletResponse response) {
        PageRequest pr = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.Direction.ASC, "nama");
        return dao.filter("%" + nama.toUpperCase() + "%", pr);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void hapus(@PathVariable String id) {
        KategoriHarga x = dao.findOne(Integer.valueOf(id));
        if (x == null) {
            throw new InvalidParameterException("Cabang '" + id + "' tidak ditemukan!");
        }
        dao.delete(x);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void simpan(@RequestBody KategoriHarga x) {
//        User u = appService.getCurrentUser();
        dao.save(x);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public void perbarui(@PathVariable String id, @RequestBody KategoriHarga x) {
        KategoriHarga r = dao.findOne(Integer.valueOf(id));
        if (r == null) {
            throw new InvalidParameterException("KategoriHarga dengan nama '" + x.getNama() + "' tidak ditemukan!");
        }
        x.setId(r.getId());
        dao.save(x);
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public Long countAll() {
        return dao.count();
    }

}
