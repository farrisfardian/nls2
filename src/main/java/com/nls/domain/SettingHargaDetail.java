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
@Table(name = "c_setting_harga_detail")
public class SettingHargaDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "harga")
    private BigDecimal harga;

    @ManyToOne
    @JoinColumn(name = "id_kategori_harga")
    private KategoriHarga kategoriHarga;

    @ManyToOne
    @JoinColumn(name = "id_setting_harga")
    @JsonBackReference
    private SettingHarga settingHarga;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    /**
     * @return the kategoriHarga
     */
    public KategoriHarga getKategoriHarga() {
        return kategoriHarga;
    }

    /**
     * @param kategoriHarga the kategoriHarga to set
     */
    public void setKategoriHarga(KategoriHarga kategoriHarga) {
        this.kategoriHarga = kategoriHarga;
    }

    /**
     * @return the harga
     */
    public BigDecimal getHarga() {
        return harga;
    }

    /**
     * @param harga the harga to set
     */
    public void setHarga(BigDecimal harga) {
        this.harga = harga;
    }

    /**
     * @return the settingHarga
     */
    public SettingHarga getSettingHarga() {
        return settingHarga;
    }

    /**
     * @param settingHarga the settingHarga to set
     */
    public void setSettingHarga(SettingHarga settingHarga) {
        this.settingHarga = settingHarga;
    }

}
