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
@Table(name = "setting_dooring_detail")
public class DooringDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "harga")
    private BigDecimal harga;

    @ManyToOne
    @JoinColumn(name = "id_kategori_barang")
    private KategoriBarang kategoriBarang;

    @ManyToOne
    @JoinColumn(name = "id_dooring")
    @JsonBackReference
    private Dooring dooring;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
     * @return the dooring
     */
    public Dooring getDooring() {
        return dooring;
    }

    /**
     * @param dooring the dooring to set
     */
    public void setDooring(Dooring dooring) {
        this.dooring = dooring;
    }

}
