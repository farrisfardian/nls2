/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nls.domain;

import com.nls.constant.UkuranKontainer;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author faheem
 */
@Entity
@Table(name = "t_stuffing")
public class Stuffing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Temporal(TemporalType.DATE)
    @Column(name = "tgl_closing")
    private Date tglClosing;

    @ManyToOne
    @JoinColumn(name = "id_kapal_berangkat")
    private KapalBerangkat kapalBerangkat;

    @ManyToOne
    @JoinColumn(name = "id_kota")
    private Kota kota;

    @ManyToOne
    @JoinColumn(name = "id_emkl")
    private Emkl emkl;

    @ManyToOne
    @JoinColumn(name = "id_satuan_kirim")
    private SatuanKirim satuanKirim;

    @Enumerated(EnumType.STRING)
    @Column(name = "ukuran_kontainer")
    private UkuranKontainer ukuranKontainer;

    @NotNull
    @NotEmpty
    @Column(name = "no_kontainer", nullable = false, length = 30)
    private String noKontainer;

    @Column(columnDefinition = "boolean default true", nullable = false)
    private Boolean aktif = Boolean.TRUE;

    @ManyToOne
    @JoinColumn(name = "id_kota_asal")
    private Kota kotaAsal;

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

    /**
     * @return the kotaAsal
     */
    public Kota getKotaAsal() {
        return kotaAsal;
    }

    /**
     * @param kotaAsal the kotaAsal to set
     */
    public void setKotaAsal(Kota kotaAsal) {
        this.kotaAsal = kotaAsal;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getTglClosing() {
        return tglClosing;
    }

    public void setTglClosing(Date tglClosing) {
        this.tglClosing = tglClosing;
    }

    public Kota getKota() {
        return kota;
    }

    public void setKota(Kota kota) {
        this.kota = kota;
    }

    public KapalBerangkat getKapalBerangkat() {
        return kapalBerangkat;
    }

    public void setKapalBerangkat(KapalBerangkat kapalBerangkat) {
        this.kapalBerangkat = kapalBerangkat;
    }

    public Emkl getEmkl() {
        return emkl;
    }

    public void setEmkl(Emkl emkl) {
        this.emkl = emkl;
    }

    public SatuanKirim getSatuanKirim() {
        return satuanKirim;
    }

    public void setSatuanKirim(SatuanKirim satuanKirim) {
        this.satuanKirim = satuanKirim;
    }

//    public Kontainer getKontainer() {
//        return kontainer;
//    }
//
//    public void setKontainer(Kontainer kontainer) {
//        this.kontainer = kontainer;
//    }
    public Boolean getAktif() {
        return aktif;
    }

    public void setAktif(Boolean aktif) {
        this.aktif = aktif;
    }

    public String getNoKontainer() {
        return noKontainer;
    }

    public void setNoKontainer(String noKontainer) {
        this.noKontainer = noKontainer;
    }

    public UkuranKontainer getUkuranKontainer() {
        return ukuranKontainer;
    }

    public void setUkuranKontainer(UkuranKontainer ukuranKontainer) {
        this.ukuranKontainer = ukuranKontainer;
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
