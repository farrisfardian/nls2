/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nls.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "m_kota")
public class Kota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50)
    private String nama;

    @Column(name = "kode_nota", length = 50)
    private String kodeNota;
    
    @Column(name = "masuk_pricelist")
    private Boolean masukPricelist;
    
    @Column(name="urutan_pricelist")
    private Integer urutanPricelist;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    /**
     * @return the masukPricelist
     */
    public Boolean getMasukPricelist() {
        return masukPricelist;
    }

    /**
     * @param masukPricelist the masukPricelist to set
     */
    public void setMasukPricelist(Boolean masukPricelist) {
        this.masukPricelist = masukPricelist;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKodeNota() {
        return kodeNota;
    }

    public void setKodeNota(String kodeNota) {
        this.kodeNota = kodeNota;
    }

    /**
     * @return the urutanPricelist
     */
    public Integer getUrutanPricelist() {
        return urutanPricelist;
    }

    /**
     * @param urutanPricelist the urutanPricelist to set
     */
    public void setUrutanPricelist(Integer urutanPricelist) {
        this.urutanPricelist = urutanPricelist;
    }

}
