/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nls.web;

import com.nls.dao.jdbc.ReportDao;
import java.text.ParseException;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author ustadho
 */
@Controller
@RequestMapping("api/report/")
public class ReportController {

    @Autowired
    ServletContext context;

    @Autowired
    ReportDao dao;

    private final Logger logger = LoggerFactory.getLogger(ReportController.class);

    @RequestMapping(value = "coba*", method = RequestMethod.GET)
    private ModelMap chartBruto(HttpServletRequest request) throws ParseException {
        String uri = request.getRequestURI();
        String format = uri.substring(uri.lastIndexOf(".") + 1);

        String tgl = request.getParameter("t");
        String realPath = context.getRealPath("/WEB-INF/templates/jrxml/") + System.getProperty("file.separator");
        realPath = realPath.replace("\\", "\\\\");
        logger.warn("format: [{}]", format);
        logger.warn("tgl: [{}]", tgl);

        return new ModelMap()
                //                .addAttribute("tanggal1", tg1)
                //                .addAttribute("logo", realPath + "igg-kop.jpg")
                .addAttribute("format", format)
                .addAttribute("dataSource", null);
    }

    @RequestMapping(value = "per-stuffing*", method = RequestMethod.GET)
    private ModelMap perStufing(HttpServletRequest request) throws ParseException {
        String uri = request.getRequestURI();
        String format = uri.substring(uri.lastIndexOf(".") + 1);

        String id = request.getParameter("id");
        Boolean ex = Boolean.valueOf(request.getParameter("ex"));
        String realPath = context.getRealPath("/WEB-INF/templates/jrxml/") + System.getProperty("file.separator");
        realPath = realPath.replace("\\", "\\\\");
        logger.warn("format: [{}]", format);
        logger.warn("id: [{}]", id);

        return new ModelMap()
                //                .addAttribute("tanggal1", tg1)
                .addAttribute("realPath", realPath)
                .addAttribute("lapExpedisi", ex)
                .addAttribute("format", format)
                .addAttribute("dataSource", dao.perStuffing(Integer.valueOf(id)));
    }

    @RequestMapping(value = "per-toko*", method = RequestMethod.GET)
    private ModelMap perToko(HttpServletRequest request) throws ParseException {
        String uri = request.getRequestURI();
        String format = uri.substring(uri.lastIndexOf(".") + 1);

        String id = request.getParameter("id");
        String it = request.getParameter("it");
        String realPath = context.getRealPath("/WEB-INF/templates/jrxml/") + System.getProperty("file.separator");
        realPath = realPath.replace("\\", "\\\\");
        logger.warn("format: [{}]", format);
        logger.warn("id: [{}]", id);
        logger.warn("idToko: [{}]", it);

        return new ModelMap()
                //                .addAttribute("tanggal1", tg1)
                //                .addAttribute("logo", realPath + "igg-kop.jpg")
                .addAttribute("realPath", realPath)
                .addAttribute("format", format)
                .addAttribute("dataSource", dao.perKapalToko(Integer.valueOf(id), Integer.valueOf(it)));
    }

    @RequestMapping(value = "per-merk-toko*", method = RequestMethod.GET)
    private ModelMap perMerkToko(HttpServletRequest request) throws ParseException {
        String uri = request.getRequestURI();
        String format = uri.substring(uri.lastIndexOf(".") + 1);

        String id = request.getParameter("id");
        String it = request.getParameter("it");
        String realPath = context.getRealPath("/WEB-INF/templates/jrxml/") + System.getProperty("file.separator");
        realPath = realPath.replace("\\", "\\\\");
        logger.warn("format: [{}]", format);
        logger.warn("id: [{}]", id);
        logger.warn("idMerk: [{}]", it);

        return new ModelMap()
                //                .addAttribute("tanggal1", tg1)
                //                .addAttribute("logo", realPath + "igg-kop.jpg")
                .addAttribute("realPath", realPath)
                .addAttribute("format", format)
                .addAttribute("dataSource", dao.perKapalMerkToko(Integer.valueOf(id), it));
    }

    @RequestMapping(value = "jml-container-pertujuan*", method = RequestMethod.GET)
    private ModelMap jmlContainerPerTujuan(HttpServletRequest request) throws ParseException {
        String uri = request.getRequestURI();
        String format = uri.substring(uri.lastIndexOf(".") + 1);

        String tahun = request.getParameter("tahun");
        String bulan = request.getParameter("bulan");
        String realPath = context.getRealPath("/WEB-INF/templates/jrxml/") + System.getProperty("file.separator");
        realPath = realPath.replace("\\", "\\\\");
        logger.warn("format: [{}]", format);
        logger.warn("tahun: [{}]", tahun);
        logger.warn("bulan: [{}]", bulan);

        return new ModelMap()
                //                .addAttribute("tanggal1", tg1)
                //                .addAttribute("logo", realPath + "igg-kop.jpg")
                .addAttribute("realPath", realPath)
                .addAttribute("format", format)
                .addAttribute("dataSource", dao.jmlContainerPerTujuan(tahun, bulan));
    }

    @RequestMapping(value = "get-nota*", method = RequestMethod.GET)
    private ModelMap getNota(HttpServletRequest request) throws ParseException {
        String uri = request.getRequestURI();
        String format = uri.substring(uri.lastIndexOf(".") + 1);

        String idNota = request.getParameter("idNota");
        String realPath = context.getRealPath("/WEB-INF/templates/jrxml/") + System.getProperty("file.separator");
        realPath = realPath.replace("\\", "\\\\");
        logger.warn("format: [{}]", format);
        logger.warn("tahun: [{}]", idNota);

        Map<String, Object> totalTerbilangByNota = (Map<String, Object>) dao.getTotalTerbilangByNota(idNota);
        return new ModelMap()
                //                .addAttribute("tanggal1", tg1)
                //                .addAttribute("logo", realPath + "igg-kop.jpg")
                .addAttribute("realPath", realPath)
                .addAttribute("format", format)
                .addAttribute("total", totalTerbilangByNota.get("total"))
                .addAttribute("terbilang", totalTerbilangByNota.get("terbilang"))
                .addAttribute("dataSourceKapal", dao.getKapalBerangkatByNota(idNota))
                .addAttribute("dataSource", dao.getNota(idNota));
    }

    @RequestMapping(value = "get-rincian-nota*", method = RequestMethod.GET)
    private ModelMap getRincianNota(HttpServletRequest request) throws ParseException {
        String uri = request.getRequestURI();
        String format = uri.substring(uri.lastIndexOf(".") + 1);

        String idToko = request.getParameter("idToko");
        String idMerk = request.getParameter("idMerk");
        String tglAwal = request.getParameter("tglAwal");
        String tglAkhir = request.getParameter("tglAkhir");
        String realPath = context.getRealPath("/WEB-INF/templates/jrxml/") + System.getProperty("file.separator");
        realPath = realPath.replace("\\", "\\\\");
        logger.warn("format: [{}]", format);

        Map<String, Object> totalTerbilangByRincianNota = (Map<String, Object>) dao.getTotalTerbilangByRincianNota(idToko, idMerk, tglAwal, tglAkhir);
        return new ModelMap()
                //                .addAttribute("tanggal1", tg1)
                //                .addAttribute("logo", realPath + "igg-kop.jpg")
                .addAttribute("realPath", realPath)
                .addAttribute("format", format)
                .addAttribute("totalTagihan", totalTerbilangByRincianNota.get("total_tagihan"))
                .addAttribute("terbilangTotalTagihan", totalTerbilangByRincianNota.get("terbilang_total_tagihan"))
                .addAttribute("totalTerbayar", totalTerbilangByRincianNota.get("terbayar"))
                .addAttribute("totalSisaTagihan", totalTerbilangByRincianNota.get("sisa_tagihan"))
                .addAttribute("dataSource", dao.getRincianNota(idToko, idMerk, tglAwal, tglAkhir));
    }

    @RequestMapping(value = "get-pembayaran-nota*", method = RequestMethod.GET)
    private ModelMap getPembayaranNota(HttpServletRequest request) throws ParseException {
        String uri = request.getRequestURI();
        String format = uri.substring(uri.lastIndexOf(".") + 1);

        String idPembayaranNota = request.getParameter("idPembayaranNota");
        String tampilkanKapalBerangkat = request.getParameter("tampilkanKapalBerangkat");
        String realPath = context.getRealPath("/WEB-INF/templates/jrxml/") + System.getProperty("file.separator");
        realPath = realPath.replace("\\", "\\\\");
        logger.warn("format: [{}]", format);
        logger.warn("tahun: [{}]", idPembayaranNota);

        return new ModelMap()
                //                .addAttribute("tanggal1", tg1)
                //                .addAttribute("logo", realPath + "igg-kop.jpg")
                .addAttribute("realPath", realPath)
                .addAttribute("format", format)
                .addAttribute("tampilkanKapalBerangkat", tampilkanKapalBerangkat.equalsIgnoreCase("true") ? true : false)
                .addAttribute("dataSource", dao.getPembayaranNota(idPembayaranNota));
    }

    @RequestMapping(value = "/rekap-coli-kubikasi", method = RequestMethod.GET)
    @ResponseBody
    private Object rekapColiKubikasiPerGrup(HttpServletRequest request) throws ParseException {
        String uri = request.getRequestURI();
        String format = uri.substring(uri.lastIndexOf(".") + 1);

        String grup = request.getParameter("grup");
        String tgl1 = request.getParameter("tgl1");
        String tgl2 = request.getParameter("tgl2");
        String order = request.getParameter("order");
        Integer limit = Integer.parseInt(request.getParameter("limit"));
        logger.warn("grup: [{}]", grup);
        logger.warn("tgl1: [{}]", tgl1);
        logger.warn("tgl2: [{}]", tgl2);
        logger.warn("order: [{}]", order);
        return dao.rekapColiKubikasiPerGrup(grup, tgl1, tgl2, order, limit);
    }
}
