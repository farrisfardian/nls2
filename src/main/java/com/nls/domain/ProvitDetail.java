/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nls.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.nls.constant.JenisStuffing;
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

/**
 *
 * @author faheem
 */
@Entity
@Table(name = "setting_provit_detail")
public class ProvitDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nominal")
    private BigDecimal nominal;

    @ManyToOne
    @JoinColumn(name = "id_kota_tujuan")
    private Kota kotaTujuan;

    @ManyToOne
    @JoinColumn(name = "id_provit")
    @JsonBackReference
    private Provit provit;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the nominal
     */
    public BigDecimal getNominal() {
        return nominal;
    }

    /**
     * @param nominal the nominal to set
     */
    public void setNominal(BigDecimal nominal) {
        this.nominal = nominal;
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
     * @return the dooring
     */
    public Provit getProvit() {
        return provit;
    }

    /**
     * @param dooring the dooring to set
     */
    public void setProvit(Provit dooring) {
        this.provit = dooring;
    }

}
