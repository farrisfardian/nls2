/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nls.web;

import com.nls.constant.JenisStuffing;
import com.nls.constant.UkuranKontainer;
import com.nls.constant.UkuranPaket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author faheem
 */
@CrossOrigin
@RestController
@RequestMapping("/api/master/enum")
public class EnumResource {

    private final Logger logger = LoggerFactory.getLogger(EnumResource.class);

    @RequestMapping(value = "/ukuran-paket", method = RequestMethod.GET)
    public UkuranPaket[] getEnumUkuranPaket() {
        return UkuranPaket.values();
    }
    
    @RequestMapping(value = "/ukuran-kontainer", method = RequestMethod.GET)
    public UkuranKontainer[] getEnumUkuranKontainer() {
        return UkuranKontainer.values();
    }
    
    @RequestMapping(value = "/jenis-stuffing", method = RequestMethod.GET)
    public JenisStuffing[] getEnumJenisStuffing() {
        return JenisStuffing.values();
    }
}
