/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nls.web;

import com.nls.dao.SettingAplikasiDao;
import com.nls.dao.jdbc.ReportDao;
import com.nls.domain.SettingAplikasi;
import com.nls.helper.EmailSender;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author ustadho
 */
@Controller
@Transactional
@RequestMapping("api/report/")
public class ReportController {

    @Autowired
    ServletContext context;

    @Autowired
    ReportDao dao;

    @Autowired
    SettingAplikasiDao settingAplikasiDao;

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

    @RequestMapping(value = "per-merk-toko-pisah-emkl*", method = RequestMethod.GET)
    private ModelMap perMerkTokoPisahEmkl(HttpServletRequest request) throws ParseException {
        String uri = request.getRequestURI();
        String format = uri.substring(uri.lastIndexOf(".") + 1);

        String id = request.getParameter("id");
        String it = request.getParameter("it");
        System.out.println("context : " + (context == null ? "null" : "not null"));
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
                .addAttribute("dataSource", dao.perKapalMerkTokoPisahEmkl(Integer.valueOf(id), it));
    }

    @RequestMapping(value = "kirim-email/per-merk-toko-pisah-emkl*", method = RequestMethod.GET)
    @ResponseBody
    private Object kirimEmailPerMerkTokoPisahEmkl(HttpServletRequest request) throws ParseException, JRException {
        String uri = request.getRequestURI();
        String format = uri.substring(uri.lastIndexOf(".") + 1);

        String id = request.getParameter("id");
        String it = request.getParameter("it");
        String subjek = request.getParameter("subjek");
        String email = request.getParameter("email");
        String isi = request.getParameter("isi");
        String realPath = context.getRealPath("/WEB-INF/templates/jrxml/") + System.getProperty("file.separator");
        realPath = realPath.replace("\\", "\\\\");
        String jrxmlPath = realPath + "per-toko-pisah-emkl.jrxml";
        HashMap<String, DataSource> attachments = new HashMap<>();
        JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlPath);
        ModelMap parameters = new ModelMap().addAttribute("realPath", realPath);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SettingAplikasi lastByTgl = settingAplikasiDao.getLastByTgl(sdf.format(new Date()));

        List<Map<String, Object>> distinct = (List<Map<String, Object>>) dao.perKapalMerkTokoPisahEmklDistinct(Integer.valueOf(id), it);
        for (int i = 0; i < distinct.size(); i++) {
            JasperPrint fillReport = JasperFillManager.fillReport(jasperReport, parameters, new JRBeanCollectionDataSource((List<Map<String, Object>>) dao.perKapalMerkTokoPisahEmklFilter(Integer.valueOf(id), it, (String) distinct.get(i).get("kota_tujuan"), (String) distinct.get(i).get("customer"), (String) distinct.get(i).get("tgl_berangkat"), (String) distinct.get(i).get("emkl"))));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(fillReport, baos);
            DataSource attachment = new ByteArrayDataSource(baos.toByteArray(), "application/pdf");
            attachments.put((String) distinct.get(i).get("tgl_berangkat") + "_" + (String) distinct.get(i).get("kota_tujuan") + "_" + (String) distinct.get(i).get("emkl") + "_" + (String) distinct.get(i).get("customer") + ".PDF", attachment);

        }
        EmailSender emailSender = new EmailSender(lastByTgl.getEmailAplikasi(), lastByTgl.getPasswordEmailAplikasi(), lastByTgl.getMailSmtpHost(), lastByTgl.getMailSmtpAuth(), lastByTgl.getMailSmtpPort());
        emailSender.kirimEmail(email, subjek, isi, attachments);
        logger.warn("format: [{}]", format);
        logger.warn("id: [{}]", id);
        logger.warn("idMerk: [{}]", it);

        return new ModelMap()
                .addAttribute("message", "Kirim email sukses");
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
        String[] arrTglAwal = tglAwal.split("-");
        String[] arrTglAkhir = tglAkhir.split("-");
        String realPath = context.getRealPath("/WEB-INF/templates/jrxml/") + System.getProperty("file.separator");
        realPath = realPath.replace("\\", "\\\\");
        logger.warn("format: [{}]", format);

        Map<String, Object> totalTerbilangByRincianNota = (Map<String, Object>) dao.getTotalTerbilangByRincianNota(idToko, idMerk, tglAwal, tglAkhir);
        return new ModelMap()
                //                .addAttribute("tanggal1", tg1)
                //                .addAttribute("logo", realPath + "igg-kop.jpg")
                .addAttribute("realPath", realPath)
                .addAttribute("format", format)
                .addAttribute("totalTagihan", (BigDecimal) totalTerbilangByRincianNota.get("total_tagihan"))
                .addAttribute("totalTerbayar", (BigDecimal) totalTerbilangByRincianNota.get("total_terbayar"))
                .addAttribute("totalSisaTagihan", (BigDecimal) totalTerbilangByRincianNota.get("total_sisa_tagihan"))
                .addAttribute("terbilangTotalTagihan", totalTerbilangByRincianNota.get("terbilang_total_tagihan"))
                .addAttribute("terbilangTotalTerbayar", totalTerbilangByRincianNota.get("terbilang_total_terbayar"))
                .addAttribute("terbilangTotalSisaTagihan", totalTerbilangByRincianNota.get("terbilang_total_sisa_tagihan"))
                .addAttribute("tglAwal", arrTglAwal[2] + "/" + arrTglAwal[1] + "/" + arrTglAwal[0])
                .addAttribute("tglAkhir", arrTglAkhir[2] + "/" + arrTglAkhir[1] + "/" + arrTglAkhir[0])
                .addAttribute("dataSource", dao.getRincianNota(idToko, idMerk, tglAwal, tglAkhir));
    }

    @RequestMapping(value = "get-rekap-nota-tagihan*", method = RequestMethod.GET)
    private ModelMap getRekapNotaTagihan(HttpServletRequest request) throws ParseException {
        String uri = request.getRequestURI();
        String format = uri.substring(uri.lastIndexOf(".") + 1);

        String idToko = request.getParameter("idToko");
        String idMerk = request.getParameter("idMerk");
        String idKotaTujuan = request.getParameter("idKotaTujuan");
        String idKapal = request.getParameter("idKapal");
        String toko = request.getParameter("toko");
        String merk = request.getParameter("merk");
        String kotaTujuan = request.getParameter("kotaTujuan");
        String kapal = request.getParameter("kapal");
        String tglAwal = request.getParameter("tglAwal");
        String tglAkhir = request.getParameter("tglAkhir");
        String[] arrTglAwal = tglAwal.split("-");
        String[] arrTglAkhir = tglAkhir.split("-");
        String realPath = context.getRealPath("/WEB-INF/templates/jrxml/") + System.getProperty("file.separator");
        realPath = realPath.replace("\\", "\\\\");
        logger.warn("format: [{}]", format);

        return new ModelMap()
                //                .addAttribute("tanggal1", tg1)
                //                .addAttribute("logo", realPath + "igg-kop.jpg")
                .addAttribute("realPath", realPath)
                .addAttribute("format", format)
                .addAttribute("toko", toko.equalsIgnoreCase("null") ? null : toko)
                .addAttribute("merk", merk.equalsIgnoreCase("null") ? null : merk)
                .addAttribute("kota", kotaTujuan.equalsIgnoreCase("null") ? null : kotaTujuan)
                .addAttribute("kapal", kapal.equalsIgnoreCase("null") ? null : kapal)
                .addAttribute("tglAwal", arrTglAwal[2] + "/" + arrTglAwal[1] + "/" + arrTglAwal[0])
                .addAttribute("tglAkhir", arrTglAkhir[2] + "/" + arrTglAkhir[1] + "/" + arrTglAkhir[0])
                .addAttribute("dataSource", dao.getRekapNotaTagihan(idToko, idMerk, idKapal, idKotaTujuan, tglAwal, tglAkhir));
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
