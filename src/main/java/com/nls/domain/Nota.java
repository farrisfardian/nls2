/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nls.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "t_nota")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Nota extends AbstractAuditingEntity implements Serializable {

    /**
     * @return the listKetJatuhTempo
     */
    public Set<NotaKetJatuhTempo> getListKetJatuhTempo() {
        return listKetJatuhTempo;
    }

    /**
     * @param listKetJatuhTempo the listKetJatuhTempo to set
     */
    public void setListKetJatuhTempo(Set<NotaKetJatuhTempo> listKetJatuhTempo) {
        this.listKetJatuhTempo = listKetJatuhTempo;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Temporal(TemporalType.DATE)
    @Column(name = "tanggal")
    private Date tanggal;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "time_ins")
    private Date timeIns;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "time_upd")
    private Date timeUpd;

    @Column(name = "user_ins")
    private String userIns;

    @Column(name = "user_upd")
    private String userUpd;

    @Column(name = "nomor")
    private String nomorInvoice;

    @Column(name = "nomor_manual")
    private Boolean nomorManual;

    @ManyToOne
    @JoinColumn(name = "id_toko")
    private Toko tokoTujuan;

    @ManyToOne
    @JoinColumn(name = "id_merk")
    private Merk merkTujuan;

    @Column(name = "min_bayar")
    private Boolean minBayar;

    @Column(name = "jml_min_bayar")
    private Double jmlMinBayar;

    @Column(name = "total_tagihan")
    private BigDecimal totalTagihan;

    @ManyToOne
    @JoinColumn(name = "id_kota_asal")
    private Kota kotaAsal;

    @OneToMany(mappedBy = "nota", cascade = {javax.persistence.CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<NotaDetail> listDetail;
    @OneToMany(mappedBy = "nota", cascade = {javax.persistence.CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<NotaTambahanBiaya> listTambahanBiaya;
    @OneToMany(mappedBy = "nota", cascade = {javax.persistence.CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<NotaKapalBerangkat> listKapalBerangkat;
    @OneToMany(mappedBy = "nota", cascade = {javax.persistence.CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<NotaKetJatuhTempo> listKetJatuhTempo;

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

    public String getNomorInvoice() {
        return nomorInvoice;
    }

    public void setNomorInvoice(String nomorInvoice) {
        this.nomorInvoice = nomorInvoice;
    }

    public Toko getTokoTujuan() {
        return tokoTujuan;
    }

    public void setTokoTujuan(Toko tokoTujuan) {
        this.tokoTujuan = tokoTujuan;
    }

    public Set<NotaDetail> getListDetail() {
        return listDetail;
    }

    public void setListDetail(Set<NotaDetail> listDetail) {
        this.listDetail = listDetail;
    }

    public Merk getMerkTujuan() {
        return merkTujuan;
    }

    public void setMerkTujuan(Merk merkTujuan) {
        this.merkTujuan = merkTujuan;
    }

    /**
     * @return the minBayar
     */
    public Boolean getMinBayar() {
        return minBayar;
    }

    /**
     * @param minBayar the minBayar to set
     */
    public void setMinBayar(Boolean minBayar) {
        this.minBayar = minBayar;
    }

    /**
     * @return the jmlMinBayar
     */
    public Double getJmlMinBayar() {
        return jmlMinBayar;
    }

    /**
     * @param jmlMinBayar the jmlMinBayar to set
     */
    public void setJmlMinBayar(Double jmlMinBayar) {
        this.jmlMinBayar = jmlMinBayar;
    }

    /**
     * @return the listTambahanBiaya
     */
    public Set<NotaTambahanBiaya> getListTambahanBiaya() {
        return listTambahanBiaya;
    }

    /**
     * @param listTambahanBiaya the listTambahanBiaya to set
     */
    public void setListTambahanBiaya(Set<NotaTambahanBiaya> listTambahanBiaya) {
        this.listTambahanBiaya = listTambahanBiaya;
    }

    /**
     * @return the listKapalBerangkat
     */
    public Set<NotaKapalBerangkat> getListKapalBerangkat() {
        return listKapalBerangkat;
    }

    /**
     * @param listKapalBerangkat the listKapalBerangkat to set
     */
    public void setListKapalBerangkat(Set<NotaKapalBerangkat> listKapalBerangkat) {
        this.listKapalBerangkat = listKapalBerangkat;
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
     * @return the nomorManual
     */
    public Boolean getNomorManual() {
        return nomorManual;
    }

    /**
     * @param nomorManual the nomorManual to set
     */
    public void setNomorManual(Boolean nomorManual) {
        this.nomorManual = nomorManual;
    }

    /**
     * @return the timeIns
     */
    public Date getTimeIns() {
        return timeIns;
    }

    /**
     * @param timeIns the timeIns to set
     */
    public void setTimeIns(Date timeIns) {
        this.timeIns = timeIns;
    }

    /**
     * @return the timeUpd
     */
    public Date getTimeUpd() {
        return timeUpd;
    }

    /**
     * @param timeUpd the timeUpd to set
     */
    public void setTimeUpd(Date timeUpd) {
        this.timeUpd = timeUpd;
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
     * @return the userUpd
     */
    public String getUserUpd() {
        return userUpd;
    }

    /**
     * @param userUpd the userUpd to set
     */
    public void setUserUpd(String userUpd) {
        this.userUpd = userUpd;
    }

    /**
     * @return the totalTagihan
     */
    public BigDecimal getTotalTagihan() {
        return totalTagihan;
    }

    /**
     * @param totalTagihan the totalTagihan to set
     */
    public void setTotalTagihan(BigDecimal totalTagihan) {
        this.totalTagihan = totalTagihan;
    }

}
