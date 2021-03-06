/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nls.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.nls.constant.JenisStuffing;
import com.nls.constant.UkuranKontainer;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author faheem
 */
@Entity
@Table(name = "setting_pricelist_pelayaran_detail")
public class PricelistPelayaranDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "harga_dari_pelayaran")
    private BigDecimal hargaDariPelayaran;

    @Column(name = "provit")
    private BigDecimal provit;

    @Column(name = "harga_fcl")
    private BigDecimal hargaFcl;

    @Column(name = "biaya_dooring_ringan")
    private BigDecimal biayaDooringRingan;

    @Column(name = "biaya_dooring_berat")
    private BigDecimal biayaDooringBerat;

    @Column(name = "harga_fcl_berat")
    private BigDecimal hargaFclBerat;

    @Column(name = "harga_fcl_ringan")
    private BigDecimal hargaFclRingan;

    @Column(name = "harga_lcl_berat")
    private BigDecimal hargaLclBerat;

    @Column(name = "harga_lcl_ringan")
    private BigDecimal hargaLclRingan;
//
//    @ManyToOne
//    @JoinColumn(name = "id_kategori_barang")
//    private KategoriBarang kategoriBarang;

//    @Enumerated(EnumType.STRING)
//    @Column(name = "jenis_stuffing")
//    private JenisStuffing jenisStuffing;
    @Enumerated(EnumType.STRING)
    @Column(name = "ukuran_kontainer")
    private UkuranKontainer ukuranKontainer;

    @ManyToOne
    @JoinColumn(name = "id_pricelist")
    @JsonBackReference
    private PricelistPelayaran pricelist;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the hargaDariPelayaran
     */
    public BigDecimal getHargaDariPelayaran() {
        return hargaDariPelayaran;
    }

    /**
     * @param hargaDariPelayaran the hargaDariPelayaran to set
     */
    public void setHargaDariPelayaran(BigDecimal hargaDariPelayaran) {
        this.hargaDariPelayaran = hargaDariPelayaran;
    }

//    /**
//     * @return the kategoriBarang
//     */
//    public KategoriBarang getKategoriBarang() {
//        return kategoriBarang;
//    }
//
//    /**
//     * @param kategoriBarang the kategoriBarang to set
//     */
//    public void setKategoriBarang(KategoriBarang kategoriBarang) {
//        this.kategoriBarang = kategoriBarang;
//    }
    /**
     * @return the jenisStuffing
     */
//    public JenisStuffing getJenisStuffing() {
//        return jenisStuffing;
//    }
    /**
     * @param jenisStuffing the jenisStuffing to set
     */
//    public void setJenisStuffing(JenisStuffing jenisStuffing) {
//        this.jenisStuffing = jenisStuffing;
//    }
    /**
     * @return the ukuranKontainer
     */
    public UkuranKontainer getUkuranKontainer() {
        return ukuranKontainer;
    }

    /**
     * @param ukuranKontainer the ukuranKontainer to set
     */
    public void setUkuranKontainer(UkuranKontainer ukuranKontainer) {
        this.ukuranKontainer = ukuranKontainer;
    }

    /**
     * @return the pricelist
     */
    public PricelistPelayaran getPricelist() {
        return pricelist;
    }

    /**
     * @param pricelist the pricelist to set
     */
    public void setPricelist(PricelistPelayaran pricelist) {
        this.pricelist = pricelist;
    }

    /**
     * @return the provit
     */
    public BigDecimal getProvit() {
        return provit;
    }

    /**
     * @param provit the provit to set
     */
    public void setProvit(BigDecimal provit) {
        this.provit = provit;
    }

    /**
     * @return the hargaFcl
     */
    public BigDecimal getHargaFcl() {
        return hargaFcl;
    }

    /**
     * @param hargaFcl the hargaFcl to set
     */
    public void setHargaFcl(BigDecimal hargaFcl) {
        this.hargaFcl = hargaFcl;
    }

    /**
     * @return the biayaDooringRingan
     */
    public BigDecimal getBiayaDooringRingan() {
        return biayaDooringRingan;
    }

    /**
     * @param biayaDooringRingan the biayaDooringRingan to set
     */
    public void setBiayaDooringRingan(BigDecimal biayaDooringRingan) {
        this.biayaDooringRingan = biayaDooringRingan;
    }

    /**
     * @return the biayaDooringBerat
     */
    public BigDecimal getBiayaDooringBerat() {
        return biayaDooringBerat;
    }

    /**
     * @param biayaDooringBerat the biayaDooringBerat to set
     */
    public void setBiayaDooringBerat(BigDecimal biayaDooringBerat) {
        this.biayaDooringBerat = biayaDooringBerat;
    }

    /**
     * @return the hargaFclBerat
     */
    public BigDecimal getHargaFclBerat() {
        return hargaFclBerat;
    }

    /**
     * @param hargaFclBerat the hargaFclBerat to set
     */
    public void setHargaFclBerat(BigDecimal hargaFclBerat) {
        this.hargaFclBerat = hargaFclBerat;
    }

    /**
     * @return the hargaFclRingan
     */
    public BigDecimal getHargaFclRingan() {
        return hargaFclRingan;
    }

    /**
     * @param hargaFclRingan the hargaFclRingan to set
     */
    public void setHargaFclRingan(BigDecimal hargaFclRingan) {
        this.hargaFclRingan = hargaFclRingan;
    }

    /**
     * @return the hargaLclBerat
     */
    public BigDecimal getHargaLclBerat() {
        return hargaLclBerat;
    }

    /**
     * @param hargaLclBerat the hargaLclBerat to set
     */
    public void setHargaLclBerat(BigDecimal hargaLclBerat) {
        this.hargaLclBerat = hargaLclBerat;
    }

    /**
     * @return the hargaLclRingan
     */
    public BigDecimal getHargaLclRingan() {
        return hargaLclRingan;
    }

    /**
     * @param hargaLclRingan the hargaLclRingan to set
     */
    public void setHargaLclRingan(BigDecimal hargaLclRingan) {
        this.hargaLclRingan = hargaLclRingan;
    }

}
