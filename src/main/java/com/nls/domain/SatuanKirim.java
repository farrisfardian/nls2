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
@Table(name = "m_satuan_kirim")
public class SatuanKirim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50)
    private String nama;
    
    @Column(name = "support_paket")
    private Boolean supportPaket;
    
    @Column(name = "support_ukuran_kontainer")
    private Boolean supportUkuranKontainer;
    
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
     * @return the supportPaket
     */
    public Boolean getSupportPaket() {
        return supportPaket;
    }

    /**
     * @param supportPaket the supportPaket to set
     */
    public void setSupportPaket(Boolean supportPaket) {
        this.supportPaket = supportPaket;
    }

    /**
     * @return the supportUkuranKontainer
     */
    public Boolean getSupportUkuranKontainer() {
        return supportUkuranKontainer;
    }

    /**
     * @param supportUkuranKontainer the supportUkuranKontainer to set
     */
    public void setSupportUkuranKontainer(Boolean supportUkuranKontainer) {
        this.supportUkuranKontainer = supportUkuranKontainer;
    }
    
}
