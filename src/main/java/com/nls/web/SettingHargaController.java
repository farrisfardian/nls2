/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nls.web;

import com.nls.dao.SettingHargaDao;
import com.nls.dao.jdbc.LookupDao;
import com.nls.dao.jdbc.SettingHargaDaoJdbc;
import com.nls.domain.SettingHarga;
import com.nls.domain.SettingHargaDetail;
import com.nls.service.AppService;
import java.security.InvalidParameterException;
import java.util.Date;
import javax.servlet.http.HttpServletResponse;
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
@RequestMapping("api/master/setting-harga")
public class SettingHargaController {

    @Autowired
    SettingHargaDao dao;

    @Autowired
    SettingHargaDaoJdbc daoJdbc;

    @Autowired
    AppService appService;
    private final Logger logger = LoggerFactory.getLogger(SettingHargaController.class);

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Iterable<SettingHarga> cariSemua() {
        return dao.findAll();
    }

    @RequestMapping(method = RequestMethod.GET)
    public Page<SettingHarga> saringSemua(
            @RequestParam(required = false) String search,
            Pageable pageable,
            HttpServletResponse respons) {
        PageRequest pr = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.Direction.ASC, "tglBerlaku");
        Page<SettingHarga> result = dao.findAll(pr);
        return result;
    }

    @RequestMapping(value = "{column}/{value}", method = RequestMethod.GET)
    public SettingHarga cariSatu(@PathVariable String column, @PathVariable String value) {
        if (column.equalsIgnoreCase("kode")) {
            return dao.findOne(Integer.valueOf(value));
        } else {
            throw new InvalidParameterException("column '" + column + "' not available");
        }
    }

    @RequestMapping(value = "/idKota/idToko/idKondisi/tglBerlaku/{idKota}/{idToko}/{idKondisi}/{tglBerlaku}", method = RequestMethod.GET)
    @ResponseBody
    public Object cariComposite(@PathVariable("idKota") String idKota, @PathVariable("idToko") String idToko, @PathVariable("idKondisi") String idKondisi, @PathVariable("tglBerlaku") String tglBerlaku,
            Pageable pageable,
            HttpServletResponse response) {
        PageRequest pr = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.Direction.ASC, "tgl_berlaku");
        return daoJdbc.listSettingHarga(idKota, idToko, idKondisi, tglBerlaku, pr);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void hapus(@PathVariable String id) {
        SettingHarga x = dao.findOne(Integer.valueOf(id));
        if (x == null) {
            throw new InvalidParameterException("SettingHarga '" + id + "' tidak ditemukan!");
        }
        dao.delete(x);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void simpan(@RequestBody SettingHarga x) {
//        User u = appService.getCurrentUser();
        if (x.getListDetail() != null) {
            for (SettingHargaDetail y : x.getListDetail()) {
                y.setSettingHarga(x);
            }
        }
        x.setUserIns(appService.getCurrentUser().getLogin());
        x.setTglIns(new Date());
        dao.save(x);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public void perbarui(@PathVariable String id, @RequestBody SettingHarga x) {
        SettingHarga r = dao.findOne(Integer.valueOf(id));
        if (r == null) {
            throw new InvalidParameterException("SettingHarga dengan ID '" + x.getId() + "' tidak ditemukan!");
        }
        for (SettingHargaDetail y : x.getListDetail()) {
            y.setSettingHarga(x);
        }
        x.setId(r.getId());
        x.setUserLastUpd(appService.getCurrentUser().getLogin());
        x.setTglLastUpd(new Date());
        dao.save(x);
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public Long countAll() {
        return dao.count();
    }

}
