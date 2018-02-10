/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nls.web;

import com.nls.dao.SettingAplikasiDao;
import com.nls.domain.SettingAplikasi;
import java.security.InvalidParameterException;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ustadho
 */
@RestController
@Transactional
@RequestMapping("/api/master/setting-aplikasi")
public class SettingAplikasiController {

    @Autowired
    SettingAplikasiDao dao;

    private final Logger logger = LoggerFactory.getLogger(SettingAplikasiController.class);

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Iterable<SettingAplikasi> findAll() {
        return dao.findAll();
    }

    @RequestMapping(method = RequestMethod.GET)
    public Page<SettingAplikasi> filterAll(
            @RequestParam(required = false) String search,
            Pageable pageable,
            HttpServletResponse respons) {
        PageRequest pr = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.Direction.ASC, "tmtBerlaku");
        return dao.findAll(pr);
    }

    @RequestMapping(value = "{column}/{value}", method = RequestMethod.GET)
    public SettingAplikasi findOne(@PathVariable String column, @PathVariable String value) {
        if (column.equalsIgnoreCase("id")) {
            return dao.findOne(value);
        } else if (column.equalsIgnoreCase("last")) {
            return dao.getLastByTgl(value);
        } else {
            throw new InvalidParameterException("column '" + column + "' not available");
        }
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable String id) {
        SettingAplikasi x = dao.findOne(id);
        if (x == null) {
            throw new InvalidParameterException("SettingAplikasi '" + id + "' tidak ditemukan!");
        }
        dao.delete(x);
    }

    @RequestMapping(method = RequestMethod.POST)
    public SettingAplikasi save(@RequestBody SettingAplikasi x) {
        dao.save(x);
        return x;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public SettingAplikasi update(@PathVariable String id, @RequestBody SettingAplikasi jm) {
        SettingAplikasi r = dao.findOne(id);
        if (r == null) {
            throw new InvalidParameterException("SettingAplikasi dengan id '" + jm.getId() + "' tidak ditemukan!");
        }
        jm.setId(r.getId());
        dao.save(jm);
        return jm;
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public Long countAll() {
        return dao.count();
    }
}
