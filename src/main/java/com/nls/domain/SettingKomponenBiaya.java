/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nls.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name = "setting_komponen_biaya", uniqueConstraints = @UniqueConstraint(columnNames = {"id_kota_tujuan", "tgl_berlaku"}))
public class SettingKomponenBiaya {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Temporal(TemporalType.DATE)
    @Column(name = "tgl_berlaku")
    private Date tglBerlaku;

    @ManyToOne
    @JoinColumn(name = "id_kota_tujuan")
    private Kota kotaTujuan;

    @OneToMany(mappedBy = "settingKomponenBiaya", cascade = {javax.persistence.CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<SettingKomponenBiayaDetail> listDetail;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
     * @return the tglBerlaku
     */
    public Date getTglBerlaku() {
        return tglBerlaku;
    }

    /**
     * @param tglBerlaku the tglBerlaku to set
     */
    public void setTglBerlaku(Date tglBerlaku) {
        this.tglBerlaku = tglBerlaku;
    }

    /**
     * @return the listDetail
     */
    public Set<SettingKomponenBiayaDetail> getListDetail() {
        return listDetail;
    }

    /**
     * @param listDetail the listDetail to set
     */
    public void setListDetail(Set<SettingKomponenBiayaDetail> listDetail) {
        this.listDetail = listDetail;
    }

}
