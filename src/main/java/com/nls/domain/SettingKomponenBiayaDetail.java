/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nls.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "setting_komponen_biaya_detail")
public class SettingKomponenBiayaDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_kategori_barang")
    private KategoriBarang kategoriBarang;

    @Column(name = "biaya_dooring")
    private BigDecimal biayaDooring;

    @Column(name = "profit")
    private BigDecimal profit;

    @Column(name = "biaya_dooring_luar_kota")
    private BigDecimal biayaDooringLuarKota;

    @Column(name = "profit_luar_kota")
    private BigDecimal profitLuarKota;

    @ManyToOne
    @JoinColumn(name = "id_setting_komponen_biaya")
    @JsonBackReference
    private SettingKomponenBiaya settingKomponenBiaya;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the kategoriBarang
     */
    public KategoriBarang getKategoriBarang() {
        return kategoriBarang;
    }

    /**
     * @param kategoriBarang the kategoriBarang to set
     */
    public void setKategoriBarang(KategoriBarang kategoriBarang) {
        this.kategoriBarang = kategoriBarang;
    }

    /**
     * @return the biayaDooring
     */
    public BigDecimal getBiayaDooring() {
        return biayaDooring;
    }

    /**
     * @param biayaDooring the biayaDooring to set
     */
    public void setBiayaDooring(BigDecimal biayaDooring) {
        this.biayaDooring = biayaDooring;
    }

    /**
     * @return the profit
     */
    public BigDecimal getProfit() {
        return profit;
    }

    /**
     * @param profit the profit to set
     */
    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    /**
     * @return the biayaDooringLuarKota
     */
    public BigDecimal getBiayaDooringLuarKota() {
        return biayaDooringLuarKota;
    }

    /**
     * @param biayaDooringLuarKota the biayaDooringLuarKota to set
     */
    public void setBiayaDooringLuarKota(BigDecimal biayaDooringLuarKota) {
        this.biayaDooringLuarKota = biayaDooringLuarKota;
    }

    /**
     * @return the profitLuarKota
     */
    public BigDecimal getProfitLuarKota() {
        return profitLuarKota;
    }

    /**
     * @param profitLuarKota the profitLuarKota to set
     */
    public void setProfitLuarKota(BigDecimal profitLuarKota) {
        this.profitLuarKota = profitLuarKota;
    }

    /**
     * @return the settingKomponenBiaya
     */
    public SettingKomponenBiaya getSettingKomponenBiaya() {
        return settingKomponenBiaya;
    }

    /**
     * @param settingKomponenBiaya the settingKomponenBiaya to set
     */
    public void setSettingKomponenBiaya(SettingKomponenBiaya settingKomponenBiaya) {
        this.settingKomponenBiaya = settingKomponenBiaya;
    }

}
