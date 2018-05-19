/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nls.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author faheem
 */
@Entity
@Table(name = "t_pembayaran_nota_detail")
public class PembayaranDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "tagihan")
    private BigDecimal tagihan;

    @Column(name = "bayar")
    private BigDecimal bayar;
    
    @Column(name = "terbayar")
    private BigDecimal terbayar;
    
    @ManyToOne
    @JoinColumn(name = "id_jenis_pembayaran")
    private JenisPembayaran jenisPembayaran;

    @Column(name = "no_rek_bg_cek", columnDefinition = "text")
    private String noRekBgCek;

    @ManyToOne
    @JoinColumn(name = "id_pembayaran")
    @JsonBackReference
    private Pembayaran pembayaran;
    
    @ManyToOne
    @JoinColumn(name = "id_nota")
    private Nota nota;

    public PembayaranDetail() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the tagihan
     */
    public BigDecimal getTagihan() {
        return tagihan;
    }

    /**
     * @param tagihan the tagihan to set
     */
    public void setTagihan(BigDecimal tagihan) {
        this.tagihan = tagihan;
    }

    /**
     * @return the bayar
     */
    public BigDecimal getBayar() {
        return bayar;
    }

    /**
     * @param bayar the bayar to set
     */
    public void setBayar(BigDecimal bayar) {
        this.bayar = bayar;
    }

    /**
     * @return the terbayar
     */
    public BigDecimal getTerbayar() {
        return terbayar;
    }

    /**
     * @param terbayar the terbayar to set
     */
    public void setTerbayar(BigDecimal terbayar) {
        this.terbayar = terbayar;
    }

    /**
     * @return the pembayaran
     */
    public Pembayaran getPembayaran() {
        return pembayaran;
    }

    /**
     * @param pembayaran the pembayaran to set
     */
    public void setPembayaran(Pembayaran pembayaran) {
        this.pembayaran = pembayaran;
    }

    /**
     * @return the nota
     */
    public Nota getNota() {
        return nota;
    }

    /**
     * @param nota the nota to set
     */
    public void setNota(Nota nota) {
        this.nota = nota;
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

    /**
     * @return the jenisPembayaran
     */
    public JenisPembayaran getJenisPembayaran() {
        return jenisPembayaran;
    }

    /**
     * @param jenisPembayaran the jenisPembayaran to set
     */
    public void setJenisPembayaran(JenisPembayaran jenisPembayaran) {
        this.jenisPembayaran = jenisPembayaran;
    }

}
