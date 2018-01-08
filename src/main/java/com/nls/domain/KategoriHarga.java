/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nls.domain;

import com.nls.constant.UkuranKontainer;
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
@Table(name = "m_kategori_harga")
public class KategoriHarga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String nama;
    
    @Column
    private Boolean paket;
    
    @ManyToOne
    @JoinColumn(name = "id_satuan_kirim")
    private SatuanKirim satuanKirim;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "ukuran_kontainer")
    private UkuranKontainer ukuranKontainer;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    /**
     * @return the paket
     */
    public Boolean getPaket() {
        return paket;
    }

    /**
     * @param paket the paket to set
     */
    public void setPaket(Boolean paket) {
        this.paket = paket;
    }

    /**
     * @return the satuanKirim
     */
    public SatuanKirim getSatuanKirim() {
        return satuanKirim;
    }

    /**
     * @param satuanKirim the satuanKirim to set
     */
    public void setSatuanKirim(SatuanKirim satuanKirim) {
        this.satuanKirim = satuanKirim;
    }

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
    
}
