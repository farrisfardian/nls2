/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nls.web;

import com.nls.dao.KontainerDao;
import com.nls.domain.Kontainer;
import com.nls.service.AppService;
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
@RequestMapping("/api/master/kontainer")
public class KontainerController {

    @Autowired
    KontainerDao dao;

//    @Autowired
//    AppService appService;
    private final Logger logger = LoggerFactory.getLogger(KontainerController.class);

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Iterable<Kontainer> cariSemua() {
        return dao.findAll();
    }

    @RequestMapping(method = RequestMethod.GET)
    public Page<Kontainer> saringSemua(
            @RequestParam(required = false) String search,
            Pageable pageable,
            HttpServletResponse respons) {
        PageRequest pr = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.Direction.ASC, "nomor");
        Page<Kontainer> result = dao.findAll(pr);
        return result;
    }

    @RequestMapping(value = "{column}/{value}", method = RequestMethod.GET)
    public Kontainer cariSatu(@PathVariable String column, @PathVariable String value) {
        if (column.equalsIgnoreCase("kode")) {
            return dao.findOne(Integer.valueOf(value));
        } else if (column.equalsIgnoreCase("nomor")) {
            return dao.findByNomor(value);
        } else {
            throw new InvalidParameterException("column '" + column + "' not available");
        }
    }

    @RequestMapping(value = "/{nomor}", method = RequestMethod.GET)
    @ResponseBody
    public Page<Kontainer> cariBerdasarkanNama(@PathVariable("nomor") String nomor,
            Pageable pageable,
            HttpServletResponse response) {
        PageRequest pr = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.Direction.ASC, "nomor");
        return dao.filter("%" + nomor.toUpperCase() + "%", pr);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void hapus(@PathVariable String id) {
        Kontainer x = dao.findOne(Integer.valueOf(id));
        if (x == null) {
            throw new InvalidParameterException("Cabang '" + id + "' tidak ditemukan!");
        }
        dao.delete(x);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void simpan(@RequestBody Kontainer x) {
//        User u = appService.getCurrentUser();
        dao.save(x);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public void perbarui(@PathVariable String id, @RequestBody Kontainer x) {
        Kontainer r = dao.findOne(Integer.valueOf(id));
        if (r == null) {
            throw new InvalidParameterException("Kontainer dengan nomor '" + x.getNomor()+ "' tidak ditemukan!");
        }
        x.setId(r.getId());
        dao.save(x);
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public Long countAll() {
        return dao.count();
    }

}
