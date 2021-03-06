/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nls.web;

import com.nls.dao.KapalBerangkatDao;
import com.nls.dao.jdbc.LookupDao;
import com.nls.domain.KapalBerangkat;
import com.nls.service.AppService;
import java.security.InvalidParameterException;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
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
@Transactional
@RequestMapping("/api/master/kapal-berangkat")
public class KapalBerangkatController {

    @Autowired
    KapalBerangkatDao dao;
    @Autowired
    LookupDao lookupDao;

    @Autowired
    AppService appService;
    private final Logger logger = LoggerFactory.getLogger(KapalBerangkatController.class);

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Iterable<KapalBerangkat> cariSemua() {
        return dao.findAll();
    }

    @RequestMapping(value = "/all-aktif-by-kota/{idKota}", method = RequestMethod.GET)
    public List<KapalBerangkat> filterByIdKotaAndAktif(@PathVariable Integer idKota) {
        return dao.filterByIdKotaAndAktif(idKota);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Page<KapalBerangkat> saringSemua(
            @RequestParam(required = false) String search,
            Pageable pageable,
            HttpServletResponse respons) {
        PageRequest pr = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.Direction.ASC, "id");
        Page<KapalBerangkat> result = dao.findAll(pr);
        return result;
    }

    @RequestMapping(value = "{column}/{value}", method = RequestMethod.GET)
    public KapalBerangkat cariSatu(@PathVariable String column, @PathVariable String value) {
        if (column.equalsIgnoreCase("kode")) {
            return dao.findOne(Integer.valueOf(value));
        } else {
            throw new InvalidParameterException("column '" + column + "' not available");
        }
    }

    @RequestMapping(value = "/{nama}", method = RequestMethod.GET)
    @ResponseBody
    public Page<KapalBerangkat> cariBerdasarkanNama(@PathVariable("nama") String nama,
            Pageable pageable,
            HttpServletResponse response) {
        PageRequest pr = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.Direction.ASC, "id");
        return dao.filter("%" + nama.toUpperCase() + "%", pr);
    }

    @RequestMapping(value = "/tglawal/tglakhir/idkota/idkapal/cari/tglnull/{tglawal}/{tglakhir}/{idkota}/{idkapal}/{cari:.+}/{tglnull}", method = RequestMethod.GET)
    @ResponseBody
    public Object cariComposite(@PathVariable("tglawal") String tglawal, @PathVariable("tglakhir") String tglakhir, @PathVariable("idkota") String idkota, @PathVariable("idkapal") String idkapal, @PathVariable("cari") String cari, @PathVariable("tglnull") String tglnull,
            Pageable pageable,
            HttpServletResponse response) {
        PageRequest pr = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.Direction.ASC, "tanggal");
        return lookupDao.lookupKapalBerangkat((cari.equals("null") ? "" : "%" + cari.toUpperCase() + "%"), idkota, idkapal, tglawal, tglakhir, tglnull, pr);
    }

    @RequestMapping(value = "/idToko/idMerk/{idToko}/{idMerk}", method = RequestMethod.GET)
    @ResponseBody
    public Object cariByTrxTokoMerkTujuan(@PathVariable("idToko") String idToko, @PathVariable("idMerk") String idMerk) {
        return lookupDao.lookupKapalBerangkatPerTokoMerkTujuan(idToko, idMerk);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void hapus(@PathVariable String id) {
        KapalBerangkat x = dao.findOne(Integer.valueOf(id));
        if (x == null) {
            throw new InvalidParameterException("Kapal Berangkat '" + id + "' tidak ditemukan!");
        }
        dao.delete(x);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void simpan(@RequestBody KapalBerangkat x) {
//        User u = appService.getCurrentUser();
        x.setUserIns(appService.getCurrentUser().getLogin());
        x.setTglIns(new Date());
        dao.save(x);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public void perbarui(@PathVariable String id, @RequestBody KapalBerangkat x) {
        KapalBerangkat r = dao.findOne(Integer.valueOf(id));
        if (r == null) {
            throw new InvalidParameterException("KapalBerangkat tidak ditemukan!");
        }
        x.setUserLastUpd(appService.getCurrentUser().getLogin());
        x.setTglLastUpd(new Date());
        x.setId(r.getId());
        dao.save(x);
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public Long countAll() {
        return dao.count();
    }

}
