/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nls.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author faheem
 */
@Entity
@Table(name = "setting_komponen_biaya")
public class SettingKomponenBiaya {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Temporal(TemporalType.DATE)
    @Column(name = "tmt_berlaku")
    private Date tmtBerlaku;
    
    @Column(name = "biaya_dooring")
    private Double biayaDooring;

    @Column(name = "profit")
    private Double profit;

    @Column(name = "biaya_dooring_luar_kota")
    private Double biayaDooringLuarKota;

    @Column(name = "profit_luar_kota")
    private Double profitLuarKota;
    
    @ManyToOne
    @JoinColumn(name = "id_kota_tujuan")
    private Kota kotaTujuan;
    
    @ManyToOne
    @JoinColumn(name = "id_kategori_barang")
    private KategoriBarang kategoriBarang;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the biayaDooring
     */
    public Double getBiayaDooring() {
        return biayaDooring;
    }

    /**
     * @param biayaDooring the biayaDooring to set
     */
    public void setBiayaDooring(Double biayaDooring) {
        this.biayaDooring = biayaDooring;
    }

    /**
     * @return the profit
     */
    public Double getProfit() {
        return profit;
    }

    /**
     * @param profit the profit to set
     */
    public void setProfit(Double profit) {
        this.profit = profit;
    }

    /**
     * @return the biayaDooringLuarKota
     */
    public Double getBiayaDooringLuarKota() {
        return biayaDooringLuarKota;
    }

    /**
     * @param biayaDooringLuarKota the biayaDooringLuarKota to set
     */
    public void setBiayaDooringLuarKota(Double biayaDooringLuarKota) {
        this.biayaDooringLuarKota = biayaDooringLuarKota;
    }

    /**
     * @return the profitLuarKota
     */
    public Double getProfitLuarKota() {
        return profitLuarKota;
    }

    /**
     * @param profitLuarKota the profitLuarKota to set
     */
    public void setProfitLuarKota(Double profitLuarKota) {
        this.profitLuarKota = profitLuarKota;
    }

    /**
     * @return the kotaTujuan
     */
    public Kota getKotaTujuan() {
        return kotaTujuan;
    }

    /**
     * @param kotaTujuan the kotaTujuan to set
     */
    public void setKotaTujuan(Kota kotaTujuan) {
        this.kotaTujuan = kotaTujuan;
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
     * @return the tmtBerlaku
     */
    public Date getTmtBerlaku() {
        return tmtBerlaku;
    }

    /**
     * @param tmtBerlaku the tmtBerlaku to set
     */
    public void setTmtBerlaku(Date tmtBerlaku) {
        this.tmtBerlaku = tmtBerlaku;
    }
    
}
