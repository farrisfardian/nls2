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
@Table(name = "t_pembayaran_nota")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Pembayaran extends AbstractAuditingEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Temporal(TemporalType.DATE)
    @Column(name = "tanggal")
    private Date tanggal;

    @Column(name = "nomor")
    private String nomor;
    
    @Column(name = "no_rek_bg_cek", columnDefinition = "text")
    private String noRekBgCek;

    @Column(name = "nomor_manual")
    private Boolean nomorManual;

    @ManyToOne
    @JoinColumn(name = "id_toko")
    private Toko tokoTujuan;

    @ManyToOne
    @JoinColumn(name = "id_merk")
    private Merk merkTujuan;
    
    @ManyToOne
    @JoinColumn(name = "id_jenis_pembayaran")
    private JenisPembayaran jenisPembayaran;

    @Column(name = "total_bayar")
    private BigDecimal totalBayar;

    @OneToMany(mappedBy = "pembayaran", cascade = {javax.persistence.CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<PembayaranDetail> listDetail;

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

    public Toko getTokoTujuan() {
        return tokoTujuan;
    }

    public void setTokoTujuan(Toko tokoTujuan) {
        this.tokoTujuan = tokoTujuan;
    }

    public Merk getMerkTujuan() {
        return merkTujuan;
    }

    public void setMerkTujuan(Merk merkTujuan) {
        this.merkTujuan = merkTujuan;
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
     * @return the nomor
     */
    public String getNomor() {
        return nomor;
    }

    /**
     * @param nomor the nomor to set
     */
    public void setNomor(String nomor) {
        this.nomor = nomor;
    }

    /**
     * @return the totalBayar
     */
    public BigDecimal getTotalBayar() {
        return totalBayar;
    }

    /**
     * @param totalBayar the totalBayar to set
     */
    public void setTotalBayar(BigDecimal totalBayar) {
        this.totalBayar = totalBayar;
    }

    /**
     * @return the listDetail
     */
    public Set<PembayaranDetail> getListDetail() {
        return listDetail;
    }

    /**
     * @param listDetail the listDetail to set
     */
    public void setListDetail(Set<PembayaranDetail> listDetail) {
        this.listDetail = listDetail;
    }

    /**
     * @return the noRekBgCek
     */
    public String getNoRekBgCek() {
        return noRekBgCek;
    }

    /**
     * @param noRekBgCek the noRekBgCek to set
     */
    public void setNoRekBgCek(String noRekBgCek) {
        this.noRekBgCek = noRekBgCek;
    }

}
