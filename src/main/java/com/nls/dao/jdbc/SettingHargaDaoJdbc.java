/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nls.dao.jdbc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;

/**
 *
 * @author faheem
 */
@Repository
public class SettingHargaDaoJdbc {

    @Autowired
    MapResultSet mr;

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(SettingHargaDaoJdbc.class);

    public Object listSettingHarga(String idKota, String idToko, String idKondisi, String tglBerlaku, PageRequest page) {
        ModelMap mm = new ModelMap();
        System.out.println("Page.Size: " + page.getPageSize());
        System.out.println("Page.Offset: " + page.getOffset());
        System.out.println("tanggal1: " + tglBerlaku);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String query = "select * from fn_get_setting_harga2(" + idToko + ", " + idKota + ", " + idKondisi + ", '" + tglBerlaku + "') as (id int,\n"
                + "  aktif boolean,\n"
                + "  nama character varying(255),\n"
                + "  tgl_berlaku date,\n"
                + "  id_kondisi integer,\n"
                + "  id_kota_tujuan integer,\n"
                + "  id_satuan_kirim integer, \n"
                + "  kota varchar, kota_asal varchar, kondisi varchar, toko varchar,\n"
                + "  detail text) ";
        logger.warn("Query [{}]", query);
        Integer totalElement = mr.countRecordset(query);
        logger.warn("totalElement [{}]", totalElement);
        int totalPages = totalElement == 0 ? 0 : totalElement <= page.getPageSize() ? 1
                : (totalElement / page.getPageSize()) + (totalElement % page.getPageSize() >= 1 ? 1 : 0);
        boolean isFirstPage = totalPages == 0 || page.getPageNumber() == 0;
        boolean isLastPage = totalPages == 0 || page.getPageNumber() == 0;

        query = query
                + "limit " + page.getPageSize() + "\n"
                + "offset " + page.getOffset();
//        logger.warn("Query [{}]", query);
        mm.addAttribute("content", mr.mapList(query));
        mm.put("size", page.getPageSize());
        mm.put("totalElements", totalElement);
        mm.put("totalPages", totalPages);
        mm.put("number", page.getPageNumber());
        mm.put("firstPage", isFirstPage);
        mm.put("lastPage", isLastPage);

        return mm;
    }
    SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
}
