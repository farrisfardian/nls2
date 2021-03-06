/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nls.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "t_kapal_berangkat")
public class KapalBerangkat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Temporal(TemporalType.DATE)
    @Column(name = "tgl_berangkat")
    private Date tglBerangkat;
    
    @Column(name = "aktif")
    private Boolean aktif;
    
    @Column(name = "no_voyage")
    private String noVoyage;
    
    @ManyToOne
    @JoinColumn(name = "id_kota")
    private Kota kota;
    
    @ManyToOne
    @JoinColumn(name = "id_kapal")
    private Kapal kapal;
    
    @Column(name = "user_ins")
    private String userIns;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "tgl_ins")
    private Date tglIns;
    
    @Column(name = "user_last_upd")
    private String userLastUpd;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "tgl_last_upd")
    private Date tglLastUpd;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getTglBerangkat() {
        return tglBerangkat;
    }

    public void setTglBerangkat(Date tglBerangkat) {
        this.tglBerangkat = tglBerangkat;
    }

    public Kota getKota() {
        return kota;
    }

    public void setKota(Kota kota) {
        this.kota = kota;
    }

    public Kapal getKapal() {
        return kapal;
    }

    public void setKapal(Kapal kapal) {
        this.kapal = kapal;
    }

    public Boolean getAktif() {
        return aktif;
    }

    public void setAktif(Boolean aktif) {
        this.aktif = aktif;
    }

    /**
     * @return the noVoyage
     */
    public String getNoVoyage() {
        return noVoyage;
    }

    /**
     * @param noVoyage the noVoyage to set
     */
    public void setNoVoyage(String noVoyage) {
        this.noVoyage = noVoyage;
    }

    /**
     * @return the userIns
     */
    public String getUserIns() {
        return userIns;
    }

    /**
     * @param userIns the userIns to set
     */
    public void setUserIns(String userIns) {
        this.userIns = userIns;
    }

    /**
     * @return the tglIns
     */
    public Date getTglIns() {
        return tglIns;
    }

    /**
     * @param tglIns the tglIns to set
     */
    public void setTglIns(Date tglIns) {
        this.tglIns = tglIns;
    }

    /**
     * @return the userLastUpd
     */
    public String getUserLastUpd() {
        return userLastUpd;
    }

    /**
     * @param userLastUpd the userLastUpd to set
     */
    public void setUserLastUpd(String userLastUpd) {
        this.userLastUpd = userLastUpd;
    }

    /**
     * @return the tglLastUpd
     */
    public Date getTglLastUpd() {
        return tglLastUpd;
    }

    /**
     * @param tglLastUpd the tglLastUpd to set
     */
    public void setTglLastUpd(Date tglLastUpd) {
        this.tglLastUpd = tglLastUpd;
    }

}
