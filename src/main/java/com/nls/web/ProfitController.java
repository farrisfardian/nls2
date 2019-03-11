/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nls.web;

import com.nls.dao.jdbc.ProfitDaoJdbc;
import com.nls.domain.Profit;
import com.nls.domain.ProfitDetail;
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
import com.nls.dao.ProfitDao;

/**
 *
 * @author faheem
 */
@CrossOrigin
@RestController
@RequestMapping("/api/setting/provit")
public class ProfitController {

    @Autowired
    ProfitDao dao;

    @Autowired
    ProfitDaoJdbc daoJdbc;

//    @Autowired
//    AppService appService;
    private final Logger logger = LoggerFactory.getLogger(ProfitController.class);

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Iterable<Profit> cariSemua() {
        return dao.findAll();
    }

    @RequestMapping(method = RequestMethod.GET)
    public Page<Profit> saringSemua(
            @RequestParam(required = false) String search,
            Pageable pageable,
            HttpServletResponse respons) {
        PageRequest pr = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.Direction.ASC, "nama");
        System.out.println("pageable.getPageNumber() : " + pageable.getPageNumber() + ", pageable.getPageSize() : " + pageable.getPageSize());
        Page<Profit> result = dao.findAll(pr);
        return result;
    }

    @RequestMapping(value = "{column}/{value}", method = RequestMethod.GET)
    public Profit cariSatu(@PathVariable String column, @PathVariable String value) {
        if (column.equalsIgnoreCase("kode")) {
            return dao.findOne(Integer.valueOf(value));
        } else {
            throw new InvalidParameterException("column '" + column + "' not available");
        }
    }

    @RequestMapping(value = "/{nama}", method = RequestMethod.GET)
    @ResponseBody
    public Page<Profit> cariBerdasarkanNama(@PathVariable("nama") String nama,
            Pageable pageable,
            HttpServletResponse response) {
        PageRequest pr = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.Direction.ASC, "nama");
        return dao.filter("%" + nama.toUpperCase() + "%", pr);
    }

    @RequestMapping(value = "/idKotaAsal/tglBerlaku/{idKotaAsal}/{tglBerlaku}", method = RequestMethod.GET)
    @ResponseBody
    public Object cariComposite(@PathVariable("idKotaAsal") String idKotaAsal, @PathVariable("tglBerlaku") String tglBerlaku,
            Pageable pageable,
            HttpServletResponse response) {
        PageRequest pr = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.Direction.ASC, "tgl_berlaku");
        return daoJdbc.listProvit(idKotaAsal, tglBerlaku, pr);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void hapus(@PathVariable String id) {
        Profit x = dao.findOne(Integer.valueOf(id));
        if (x == null) {
            throw new InvalidParameterException("Provit '" + id + "' tidak ditemukan!");
        }
        dao.delete(x);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void simpan(@RequestBody Profit x) {
        if (x.getListDetail() != null) {
            for (ProfitDetail c : x.getListDetail()) {
                c.setProvit(x);
            }
        }
        dao.save(x);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public void perbarui(@PathVariable String id, @RequestBody Profit x) {
        Profit r = dao.findOne(Integer.valueOf(id));
        if (r == null) {
            throw new InvalidParameterException("Provit dengan ID '" + x.getId() + "' tidak ditemukan!");
        }
        x.setId(r.getId());
        dao.save(x);
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public Long countAll() {
        return dao.count();
    }

}
