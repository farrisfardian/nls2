/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nls.web;

import com.nls.dao.PricelistPelayaranDao;
import com.nls.dao.jdbc.PricelistPelayaranDaoJdbc;
import com.nls.domain.PricelistPelayaran;
import com.nls.domain.PricelistPelayaranDetail;
import java.security.InvalidParameterException;
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
@RequestMapping("/api/setting/pricelist-pelayaran")
public class PricelistPelayaranController {

    @Autowired
    PricelistPelayaranDao dao;

    @Autowired
    PricelistPelayaranDaoJdbc daoJdbc;

//    @Autowired
//    AppService appService;
    private final Logger logger = LoggerFactory.getLogger(PricelistPelayaranController.class);

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Iterable<PricelistPelayaran> cariSemua() {
        return dao.findAll();
    }

    @RequestMapping(method = RequestMethod.GET)
    public Page<PricelistPelayaran> saringSemua(
            @RequestParam(required = false) String search,
            Pageable pageable,
            HttpServletResponse respons) {
        PageRequest pr = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.Direction.ASC, "nama");
        System.out.println("pageable.getPageNumber() : " + pageable.getPageNumber() + ", pageable.getPageSize() : " + pageable.getPageSize());
        Page<PricelistPelayaran> result = dao.findAll(pr);
        return result;
    }

    @RequestMapping(value = "{column}/{value}", method = RequestMethod.GET)
    public PricelistPelayaran cariSatu(@PathVariable String column, @PathVariable String value) {
        if (column.equalsIgnoreCase("kode")) {
            return dao.findOne(Integer.valueOf(value));
        } else {
            throw new InvalidParameterException("column '" + column + "' not available");
        }
    }

    @RequestMapping(value = "/{nama}", method = RequestMethod.GET)
    @ResponseBody
    public Page<PricelistPelayaran> cariBerdasarkanNama(@PathVariable("nama") String nama,
            Pageable pageable,
            HttpServletResponse response) {
        PageRequest pr = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.Direction.ASC, "nama");
        return dao.filter("%" + nama.toUpperCase() + "%", pr);
    }

    @RequestMapping(value = "/idKotaAsal/idKotaTujuan/tglBerlaku/{idKotaAsal}/{idKotaTujuan}/{tglBerlaku}", method = RequestMethod.GET)
    @ResponseBody
    public Object cariComposite(@PathVariable("idKotaAsal") String idKotaAsal, @PathVariable("idKotaTujuan") String idKotaTujuan, @PathVariable("tglBerlaku") String tglBerlaku,
            Pageable pageable,
            HttpServletResponse response) {
        PageRequest pr = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.Direction.ASC, "tgl_berlaku");
        return daoJdbc.listPricelist(idKotaAsal, idKotaTujuan, tglBerlaku, pr);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void hapus(@PathVariable String id) {
        PricelistPelayaran x = dao.findOne(Integer.valueOf(id));
        if (x == null) {
            throw new InvalidParameterException("Cabang '" + id + "' tidak ditemukan!");
        }
        dao.delete(x);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void simpan(@RequestBody PricelistPelayaran x) {
        if (x.getListDetail() != null) {
            for (PricelistPelayaranDetail c : x.getListDetail()) {
                c.setPricelist(x);
            }
        }
        dao.save(x);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public void perbarui(@PathVariable String id, @RequestBody PricelistPelayaran x) {
        PricelistPelayaran r = dao.findOne(Integer.valueOf(id));
        if (r == null) {
            throw new InvalidParameterException("PricelistPelayaran dengan ID '" + x.getId() + "' tidak ditemukan!");
        }
        x.setId(r.getId());
        dao.save(x);
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public Long countAll() {
        return dao.count();
    }

}
