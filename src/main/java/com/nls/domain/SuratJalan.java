/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nls.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 *
 * @author faheem
 */
@Entity
@Table(name = "t_surat_jalan")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SuratJalan extends AbstractAuditingEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Temporal(TemporalType.DATE)
    @Column(name = "tanggal")
    private Date tanggal;

    @Column(name = "nomor")
    private String nomorSJ;
    
    @Column(name = "indeks")
    private String indeks;

    @Column(name = "tagihan_pengirim")
    private Boolean tagihanPengirim;

    @Column(name = "sisipan")
    private Boolean sisipan;

    @ManyToOne
    @JoinColumn(name = "id_stuffing")
    private Stuffing stuffing;

    @ManyToOne
    @JoinColumn(name = "id_bahan")
    private BahanSj bahan;

    @ManyToOne
    @JoinColumn(name = "id_toko")
    private Toko toko;

    @ManyToOne
    @JoinColumn(name = "id_merk")
    private Merk merk;

    @ManyToOne
    @JoinColumn(name = "id_kondisi")
    private Kondisi kondisi;

    @ManyToOne
    @JoinColumn(name = "id_jenis_item")
    private JenisItem jenisItem;

    @OneToMany(mappedBy = "suratJalan", cascade = {javax.persistence.CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<SuratJalanDetail> listSuratJalanDetail;
    
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

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public String getNomorSJ() {
        return nomorSJ;
    }

    public void setNomorSJ(String nomorSJ) {
        this.nomorSJ = nomorSJ;
    }

    public Boolean getTagihanPengirim() {
        return tagihanPengirim;
    }

    public void setTagihanPengirim(Boolean tagihanPengirim) {
        this.tagihanPengirim = tagihanPengirim;
    }

    public Stuffing getStuffing() {
        return stuffing;
    }

    public void setStuffing(Stuffing stuffing) {
        this.stuffing = stuffing;
    }

    public Toko getToko() {
        return toko;
    }

    public void setToko(Toko toko) {
        this.toko = toko;
    }

    public Merk getMerk() {
        return merk;
    }

    public void setMerk(Merk merk) {
        this.merk = merk;
    }

    public Kondisi getKondisi() {
        return kondisi;
    }

    public void setKondisi(Kondisi kondisi) {
        this.kondisi = kondisi;
    }

    public Set<SuratJalanDetail> getListSuratJalanDetail() {
        return listSuratJalanDetail;
    }

    public void setListSuratJalanDetail(Set<SuratJalanDetail> listSuratJalanDetail) {
        this.listSuratJalanDetail = listSuratJalanDetail;
    }

    public BahanSj getBahan() {
        return bahan;
    }

    public void setBahan(BahanSj bahan) {
        this.bahan = bahan;
    }

    public JenisItem getJenisItem() {
        return jenisItem;
    }

    public void setJenisItem(JenisItem jenisItem) {
        this.jenisItem = jenisItem;
    }

    /**
     * @return the sisipan
     */
    public Boolean getSisipan() {
        return sisipan;
    }

    /**
     * @param sisipan the sisipan to set
     */
    public void setSisipan(Boolean sisipan) {
        this.sisipan = sisipan;
    }

    /**
     * @return the indeks
     */
    public String getIndeks() {
        return indeks;
    }

    /**
     * @param indeks the indeks to set
     */
    public void setIndeks(String indeks) {
        this.indeks = indeks;
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
