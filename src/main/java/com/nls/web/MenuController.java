/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nls.web;

import com.nls.dao.MenuDao;
import com.nls.domain.Menu;
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
import java.util.List;

/**
 *
 * @author faheem
 */
@CrossOrigin
@RestController
@RequestMapping("/api/setting/menu")
public class MenuController {
    
    @Autowired
    MenuDao dao;

//    @Autowired
//    AppService appService;
    private final Logger logger = LoggerFactory.getLogger(MenuController.class);
    
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Iterable<Menu> cariSemua() {
        return dao.findAll();
    }
    
    @RequestMapping(value = "/all-parent", method = RequestMethod.GET)
    public List<Menu> cariSemuaParent() {
        return dao.findMenuParent();
    }
    
    @RequestMapping(value = "/not-in/{ids}", method = RequestMethod.GET)
    public List<Menu> findByIdNotIn(@PathVariable String ids) {
        return dao.findByIdNotIn(ids);
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public Page<Menu> saringSemua(
            @RequestParam(required = false) String search,
            Pageable pageable,
            HttpServletResponse respons) {
        PageRequest pr = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.Direction.ASC, "title");
        System.out.println("pageable.getPageNumber() : " + pageable.getPageNumber() + ", pageable.getPageSize() : " + pageable.getPageSize());
        Page<Menu> result = dao.findAll(pr);
        return result;
    }
    
    @RequestMapping(value = "{column}/{value}", method = RequestMethod.GET)
    public Menu cariSatu(@PathVariable String column, @PathVariable String value) {
        if (column.equalsIgnoreCase("kode")) {
            return dao.findOne(value);
        } else {
            throw new InvalidParameterException("column '" + column + "' not available");
        }
    }
    
    @RequestMapping(value = "/{nama}", method = RequestMethod.GET)
    @ResponseBody
    public Page<Menu> cariBerdasarkanNama(@PathVariable("nama") String nama,
            Pageable pageable,
            HttpServletResponse response) {
        PageRequest pr = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.Direction.ASC, "title");
        return dao.filter("%" + nama.toUpperCase() + "%", pr);
    }
    
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void hapus(@PathVariable String id) {
        Menu x = dao.findOne(id);
        if (x == null) {
            throw new InvalidParameterException("Gerai '" + id + "' tidak ditemukan!");
        }
        dao.delete(x);
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public void simpan(@RequestBody Menu x) {
//        User u = appService.getCurrentUser();
        if (x.getIsParent() == null) {
            x.setIsParent(false);
        }
        if (x.getIsParent()) {
            x.setParent(null);
        }
        dao.save(x);
    }
    
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public void perbarui(@PathVariable String id, @RequestBody Menu x) {
        Menu r = dao.findOne(id);
        if (r == null) {
            throw new InvalidParameterException("Gerai dengan nama '" + x.getTitle() + "' tidak ditemukan!");
        }
        if (x.getIsParent() == null) {
            x.setIsParent(false);
        }
        if (x.getIsParent()) {
            x.setParent(null);
        }
        x.setId(r.getId());
        dao.save(x);
    }
    
    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public Long countAll() {
        return dao.count();
    }
    
}
