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
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
                .addAttribute(JRParameter.REPORT_LOCALE, new Locale("id"))
                .addAttribute("dataSource", dao.perStuffing(Integer.valueOf(id)));
    }

    @RequestMapping(value = "kirim-email/per-stuffing*", method = RequestMethod.GET)
    @ResponseBody
    private Object kirimEmailPerStuffing(HttpServletRequest request) {
        try {
            String uri = request.getRequestURI();
            String format = uri.substring(uri.lastIndexOf(".") + 1);

            String id = request.getParameter("id");
            Boolean ex = Boolean.valueOf(request.getParameter("ex"));
            String subjek = request.getParameter("subjek");
            String email = request.getParameter("email");
            String isi = request.getParameter("isi");
            String realPath = context.getRealPath("/WEB-INF/templates/jrxml/") + System.getProperty("file.separator");
            realPath = realPath.replace("\\", "\\\\");
            String jrxmlPath = realPath + "packing-list-stuffing.jrxml";
            HashMap<String, DataSource> attachments = new HashMap<>();
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlPath);
            ModelMap parameters = new ModelMap().addAttribute("realPath", realPath).addAttribute("lapExpedisi", ex);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            SettingAplikasi lastByTgl = settingAplikasiDao.getLastByTgl(sdf.format(new Date()));

            List<Map<String, Object>> perStuffing = (List<Map<String, Object>>) dao.perStuffing(Integer.valueOf(id));
            JasperPrint fillReport = JasperFillManager.fillReport(jasperReport, parameters, new JRBeanCollectionDataSource(perStuffing));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(fillReport, baos);
            DataSource attachment = new ByteArrayDataSource(baos.toByteArray(), "application/pdf");
            attachments.put((String) perStuffing.get(0).get("tgl_brgkt") + "_" + (String) perStuffing.get(0).get("kota_tujuan") + "_" + (String) perStuffing.get(0).get("emkl") + "_" + (String) perStuffing.get(0).get("nomor_kontainer") + ".PDF", attachment);

            EmailSender emailSender = new EmailSender(lastByTgl.getEmailAplikasi(), lastByTgl.getPasswordEmailAplikasi(), lastByTgl.getMailSmtpHost(), lastByTgl.getMailSmtpAuth(), lastByTgl.getMailSmtpPort());
            emailSender.kirimEmail(email, subjek, isi, attachments);
            logger.warn("format: [{}]", format);
            logger.warn("id: [{}]", id);
            logger.warn("lapExpedisi: [{}]", ex);

            return new ModelMap()
                    .addAttribute("message", "Kirim email sukses");
        } catch (JRException ex1) {
            java.util.logging.Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex1);
            return new ModelMap()
                    .addAttribute("message", "Kirim email gagal");
        }
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
                .addAttribute(JRParameter.REPORT_LOCALE, new Locale("id"))
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
                .addAttribute(JRParameter.REPORT_LOCALE, new Locale("id"))
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
                .addAttribute(JRParameter.REPORT_LOCALE, new Locale("id"))
                .addAttribute("dataSource", dao.perKapalMerkTokoPisahEmkl(Integer.valueOf(id), it));
    }

    @RequestMapping(value = "pricelist-pelayaran*", method = RequestMethod.GET)
    private ModelMap pricelistPelayaran(HttpServletRequest request) throws ParseException {
        String uri = request.getRequestURI();
        String format = uri.substring(uri.lastIndexOf(".") + 1);

        String idKotaAsal = request.getParameter("idKotaAsal");
        String idPelayaran = request.getParameter("idPelayaran");
        String namaKotaAsal = request.getParameter("namaKotaAsal");
        String namaPelayaran = request.getParameter("namaPelayaran");
        String ukuranKontainer = request.getParameter("ukuranKontainer");
        System.out.println("context : " + (context == null ? "null" : "not null"));
        String realPath = context.getRealPath("/WEB-INF/templates/jrxml/") + System.getProperty("file.separator");
        realPath = realPath.replace("\\", "\\\\");
        logger.warn("format: [{}]", format);
        logger.warn("idKotaAsal: [{}]", idKotaAsal);
        logger.warn("ukuranKontainer: [{}]", ukuranKontainer);

        return new ModelMap()
                //                .addAttribute("tanggal1", tg1)
                //                .addAttribute("logo", realPath + "igg-kop.jpg")
                .addAttribute("realPath", realPath)
                .addAttribute("format", format)
                .addAttribute("namaKotaAsal", namaKotaAsal)
                .addAttribute("namaPelayaran", namaPelayaran)
                .addAttribute("ukuranKontainer", ukuranKontainer.replace("_", ""))
                .addAttribute(JRParameter.REPORT_LOCALE, new Locale("id"))
                .addAttribute("dataSource", dao.pricelistPelayaran(idKotaAsal, idPelayaran, ukuranKontainer));
    }

    @RequestMapping(value = "kirim-email/per-merk-toko-pisah-emkl*", method = RequestMethod.GET)
    @ResponseBody
    private Object kirimEmailPerMerkTokoPisahEmkl(HttpServletRequest request) {
        try {
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
            ModelMap parameters = new ModelMap().addAttribute("realPath", realPath).addAttribute(JRParameter.REPORT_LOCALE, new Locale("id"));
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
        } catch (JRException ex) {
            java.util.logging.Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
            return new ModelMap()
                    .addAttribute("message", "Kirim email gagal");
        }
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
                .addAttribute(JRParameter.REPORT_LOCALE, new Locale("id"))
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
                .addAttribute(JRParameter.REPORT_LOCALE, new Locale("id"))
                .addAttribute("total", totalTerbilangByNota.get("total"))
                .addAttribute("terbilang", totalTerbilangByNota.get("terbilang"))
                .addAttribute("dataSourceRekening", dao.getRekening())
                .addAttribute("dataSourceKetJatuhTempo", dao.getKetJatuhTempoByNota(idNota))
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
                .addAttribute(JRParameter.REPORT_LOCALE, new Locale("id"))
                .addAttribute("totalTagihan", (BigDecimal) totalTerbilangByRincianNota.get("total_tagihan"))
                .addAttribute("totalTerbayar", (BigDecimal) totalTerbilangByRincianNota.get("total_terbayar"))
                .addAttribute("totalSisaTagihan", (BigDecimal) totalTerbilangByRincianNota.get("total_sisa_tagihan"))
                .addAttribute("terbilangTotalTagihan", totalTerbilangByRincianNota.get("terbilang_total_tagihan"))
                .addAttribute("terbilangTotalTerbayar", totalTerbilangByRincianNota.get("terbilang_total_terbayar"))
                .addAttribute("terbilangTotalSisaTagihan", totalTerbilangByRincianNota.get("terbilang_total_sisa_tagihan"))
                .addAttribute("tglAwal", arrTglAwal[2] + "/" + arrTglAwal[1] + "/" + arrTglAwal[0])
                .addAttribute("tglAkhir", arrTglAkhir[2] + "/" + arrTglAkhir[1] + "/" + arrTglAkhir[0])
                .addAttribute("dataSourceRekening", dao.getRekening())
                .addAttribute("dataSourceKetJatuhTempo", dao.getKetJatuhTempoByNota("0"))
                .addAttribute("dataSource", dao.getRincianNota(idToko, idMerk, tglAwal, tglAkhir));
    }

    @RequestMapping(value = "rekap-nota-belum-lunas*", method = RequestMethod.GET)
    private ModelMap rekapNotaBelumLunas(HttpServletRequest request) throws ParseException {
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

        return new ModelMap()
                //                .addAttribute("tanggal1", tg1)
                //                .addAttribute("logo", realPath + "igg-kop.jpg")
                .addAttribute("realPath", realPath)
                .addAttribute("format", format)
                .addAttribute(JRParameter.REPORT_LOCALE, new Locale("id"))
                .addAttribute("tglAwal", arrTglAwal[2] + "/" + arrTglAwal[1] + "/" + arrTglAwal[0])
                .addAttribute("tglAkhir", arrTglAkhir[2] + "/" + arrTglAkhir[1] + "/" + arrTglAkhir[0])
                .addAttribute("dataSource", dao.rekapNotaBelumLunas(idToko, idMerk, tglAwal, tglAkhir));
    }

    @RequestMapping(value = "kirim-email/get-rincian-nota*", method = RequestMethod.GET)
    private ModelMap kirimEmailGetRincianNota(HttpServletRequest request) throws ParseException {
        try {
            String uri = request.getRequestURI();
            String format = uri.substring(uri.lastIndexOf(".") + 1);
            String subjek = request.getParameter("subjek");
            String email = request.getParameter("email");
            String isi = request.getParameter("isi");
            String idToko = request.getParameter("idToko");
            String idMerk = request.getParameter("idMerk");
            String tglAwal = request.getParameter("tglAwal");
            String tglAkhir = request.getParameter("tglAkhir");
            String[] arrTglAwal = tglAwal.split("-");
            String[] arrTglAkhir = tglAkhir.split("-");
            String realPath = context.getRealPath("/WEB-INF/templates/jrxml/") + System.getProperty("file.separator");
            realPath = realPath.replace("\\", "\\\\");
            String jrxmlPath = realPath + "packing-list-stuffing.jrxml";
            HashMap<String, DataSource> attachments = new HashMap<>();
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlPath);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            SettingAplikasi lastByTgl = settingAplikasiDao.getLastByTgl(sdf.format(new Date()));

            Map<String, Object> totalTerbilangByRincianNota = (Map<String, Object>) dao.getTotalTerbilangByRincianNota(idToko, idMerk, tglAwal, tglAkhir);
            List<Map<String, Object>> report = (List<Map<String, Object>>) dao.getRincianNota(idToko, idMerk, tglAwal, tglAkhir);

            ModelMap parameters = new ModelMap().addAttribute("realPath", realPath)
                    .addAttribute("format", format)
                    .addAttribute(JRParameter.REPORT_LOCALE, new Locale("id"))
                    .addAttribute("totalTagihan", (BigDecimal) totalTerbilangByRincianNota.get("total_tagihan"))
                    .addAttribute("totalTerbayar", (BigDecimal) totalTerbilangByRincianNota.get("total_terbayar"))
                    .addAttribute("totalSisaTagihan", (BigDecimal) totalTerbilangByRincianNota.get("total_sisa_tagihan"))
                    .addAttribute("terbilangTotalTagihan", totalTerbilangByRincianNota.get("terbilang_total_tagihan"))
                    .addAttribute("terbilangTotalTerbayar", totalTerbilangByRincianNota.get("terbilang_total_terbayar"))
                    .addAttribute("terbilangTotalSisaTagihan", totalTerbilangByRincianNota.get("terbilang_total_sisa_tagihan"))
                    .addAttribute("tglAwal", arrTglAwal[2] + "/" + arrTglAwal[1] + "/" + arrTglAwal[0])
                    .addAttribute("tglAkhir", arrTglAkhir[2] + "/" + arrTglAkhir[1] + "/" + arrTglAkhir[0]);
            JasperPrint fillReport = JasperFillManager.fillReport(jasperReport, parameters, new JRBeanCollectionDataSource(report));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(fillReport, baos);
            DataSource attachment = new ByteArrayDataSource(baos.toByteArray(), "application/pdf");
            attachments.put("RINCIAN_NOTA_" + (String) report.get(0).get("tgl_awal_berangkat") + "_sd_" + (String) report.get(0).get("tgl_akhir_berangkat") + "_" + (String) report.get(0).get("toko") + ".PDF", attachment);

            EmailSender emailSender = new EmailSender(lastByTgl.getEmailAplikasi(), lastByTgl.getPasswordEmailAplikasi(), lastByTgl.getMailSmtpHost(), lastByTgl.getMailSmtpAuth(), lastByTgl.getMailSmtpPort());
            emailSender.kirimEmail(email, subjek, isi, attachments);
            logger.warn("format: [{}]", format);

            return new ModelMap()
                    .addAttribute("message", "Kirim email sukses");
        } catch (JRException ex) {
            java.util.logging.Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
            return new ModelMap()
                    .addAttribute("message", "Kirim email gagal");
        }

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
                .addAttribute(JRParameter.REPORT_LOCALE, new Locale("id"))
                .addAttribute("toko", toko.equalsIgnoreCase("null") ? null : toko)
                .addAttribute("merk", merk.equalsIgnoreCase("null") ? null : merk)
                .addAttribute("kota", kotaTujuan.equalsIgnoreCase("null") ? null : kotaTujuan)
                .addAttribute("kapal", kapal.equalsIgnoreCase("null") ? null : kapal)
                .addAttribute("tglAwal", arrTglAwal[2] + "/" + arrTglAwal[1] + "/" + arrTglAwal[0])
                .addAttribute("tglAkhir", arrTglAkhir[2] + "/" + arrTglAkhir[1] + "/" + arrTglAkhir[0])
                .addAttribute("dataSourceRekening", dao.getRekening())
                .addAttribute("dataSourceKetJatuhTempo", dao.getKetJatuhTempoByNota("0"))
                .addAttribute("dataSource", dao.getRekapNotaTagihan(idToko, idMerk, idKapal, idKotaTujuan, tglAwal, tglAkhir));
    }

    @RequestMapping(value = "get-rekap-pembayaran*", method = RequestMethod.GET)
    private ModelMap getRekapPembayaran(HttpServletRequest request) throws ParseException {
        String uri = request.getRequestURI();
        String format = uri.substring(uri.lastIndexOf(".") + 1);

        String tglAwal = request.getParameter("tglAwal");
        String tglAkhir = request.getParameter("tglAkhir");
        String idKapalBerangkat = request.getParameter("idKapalBerangkat");
        String[] arrTglAwal = tglAwal.split("-");
        String[] arrTglAkhir = tglAkhir.split("-");
        String realPath = context.getRealPath("/WEB-INF/templates/jrxml/") + System.getProperty("file.separator");
        realPath = realPath.replace("\\", "\\\\");
        logger.warn("format: [{}]", format);
        System.out.println("tglAwal = " + tglAwal + ", tglAkhir = " + tglAkhir + ", idKapalBerangkat = " + idKapalBerangkat);
        idKapalBerangkat = idKapalBerangkat.equalsIgnoreCase("null") ? "null" : "ARRAY[" + idKapalBerangkat + "]";

        return new ModelMap()
                //                .addAttribute("tanggal1", tg1)
                //                .addAttribute("logo", realPath + "igg-kop.jpg")
                .addAttribute("realPath", realPath)
                .addAttribute("format", format)
                .addAttribute(JRParameter.REPORT_LOCALE, new Locale("id"))
                .addAttribute("tglAwal", arrTglAwal[2] + "/" + arrTglAwal[1] + "/" + arrTglAwal[0])
                .addAttribute("tglAkhir", arrTglAkhir[2] + "/" + arrTglAkhir[1] + "/" + arrTglAkhir[0])
                .addAttribute("dataSource", dao.getRekapPembayaran(tglAwal, tglAkhir, idKapalBerangkat));
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
                .addAttribute(JRParameter.REPORT_LOCALE, new Locale("id"))
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
    
    @RequestMapping(value = "/rekap-terbayar-tagihan", method = RequestMethod.GET)
    @ResponseBody
    private Object rekapTagihanByGrup(HttpServletRequest request) throws ParseException {
        String uri = request.getRequestURI();
        String format = uri.substring(uri.lastIndexOf(".") + 1);

        String tgl1 = request.getParameter("tgl1");
        String tgl2 = request.getParameter("tgl2");
        String order = request.getParameter("order");
        Integer limit = Integer.parseInt(request.getParameter("limit"));
        logger.warn("tgl1: [{}]", tgl1);
        logger.warn("tgl2: [{}]", tgl2);
        logger.warn("order: [{}]", order);
        return dao.rekapTagihanByGrup(tgl1, tgl2, order, limit);
    }
    
    @RequestMapping(value = "/history-tagihan-toko", method = RequestMethod.GET)
    @ResponseBody
    private Object historyTagihanToko(HttpServletRequest request) throws ParseException {
        String uri = request.getRequestURI();
        String format = uri.substring(uri.lastIndexOf(".") + 1);

        String limit = request.getParameter("limit");
        String thBln2 = request.getParameter("th_bln2");
        String idToko = request.getParameter("idtoko");
        logger.warn("limit: [{}]", limit);
        logger.warn("thBln2: [{}]", thBln2);
        logger.warn("idToko: [{}]", idToko);
        return dao.historyTagihanToko(limit, thBln2, idToko);
    }
}
