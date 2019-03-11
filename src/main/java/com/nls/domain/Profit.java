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
@Table(name = "setting_provit", uniqueConstraints = @UniqueConstraint(columnNames = {"id_kota_asal", "tgl_berlaku"}))
public class Profit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Temporal(TemporalType.DATE)
    @Column(name = "tgl_berlaku")
    private Date tglBerlaku;

    @ManyToOne
    @JoinColumn(name = "id_kota_asal")
    private Kota kotaAsal;

    @OneToMany(mappedBy = "provit", cascade = {javax.persistence.CascadeType.ALL}, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<ProfitDetail> listDetail;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
     * @return the listDetail
     */
    public Set<ProfitDetail> getListDetail() {
        return listDetail;
    }

    /**
     * @param listDetail the listDetail to set
     */
    public void setListDetail(Set<ProfitDetail> listDetail) {
        this.listDetail = listDetail;
    }

}
