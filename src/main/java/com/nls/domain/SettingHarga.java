/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nls.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author faheem
 */
@Entity
@Table(name = "c_setting_harga", uniqueConstraints = @UniqueConstraint(columnNames = {"id_kota_asal", "id_kota_tujuan", "id_kondisi", "tgl_berlaku", "id_toko"}))
public class SettingHarga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_kota_tujuan")
    private Kota kotaTujuan;

    @ManyToOne
    @JoinColumn(name = "id_kota_asal")
    private Kota kotaAsal;

    @ManyToOne
    @JoinColumn(name = "id_toko")
    private Toko toko;

    @ManyToOne
    @JoinColumn(name = "id_kondisi")
    private Kondisi kondisi;

    @Temporal(TemporalType.DATE)
    @Column(name = "tgl_berlaku")
    private Date tglBerlaku;
    
    @ManyToOne
    @JoinColumn(name = "id_pelayaran")
    private Pelayaran pelayaran;

    @OneToMany(mappedBy = "settingHarga", cascade = {javax.persistence.CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<SettingHargaDetail> listDetail;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Kota getKotaTujuan() {
        return kotaTujuan;
    }

    public void setKotaTujuan(Kota kotaTujuan) {
        this.kotaTujuan = kotaTujuan;
    }

    public Kondisi getKondisi() {
        return kondisi;
    }

    public void setKondisi(Kondisi kondisi) {
        this.kondisi = kondisi;
    }

    public Date getTglBerlaku() {
        return tglBerlaku;
    }

    public void setTglBerlaku(Date tglBerlaku) {
        this.tglBerlaku = tglBerlaku;
    }

    public Toko getToko() {
        return toko;
    }

    public void setToko(Toko toko) {
        this.toko = toko;
    }

    /**
     * @return the listDetail
     */
    public Set<SettingHargaDetail> getListDetail() {
        return listDetail;
    }

    /**
     * @param listDetail the listDetail to set
     */
    public void setListDetail(Set<SettingHargaDetail> listDetail) {
        this.listDetail = listDetail;
    }

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

    /**
     * @return the pelayaran
     */
    public Pelayaran getPelayaran() {
        return pelayaran;
    }

    /**
     * @param pelayaran the pelayaran to set
     */
    public void setPelayaran(Pelayaran pelayaran) {
        this.pelayaran = pelayaran;
    }
}
