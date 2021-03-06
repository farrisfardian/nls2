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
@Table(name = "t_nota_detail")
public class NotaDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @NotEmpty
    @Column(name = "no_kontainer", nullable = false, length = 30)
    private String noKontainer;

    @Column(name = "volume")
    private BigDecimal volume;

    @Column(name = "harga")
    private BigDecimal harga;

    @Column(name = "tambahan_min_bayar")
    private BigDecimal tambahanMinBayar;

    @ManyToOne
    @JoinColumn(name = "id_jenis_item")
    private JenisItem jenisItem;

    @Column(name = "jenis_items")
    private String jenisItems;

    @Column(name = "id_sj", columnDefinition = "text")
    private String idSj;

    @Column(name = "coli")
    private Integer coli;

    @ManyToOne
    @JoinColumn(name = "id_kondisi")
    private Kondisi kondisi;

    @ManyToOne
    @JoinColumn(name = "id_satuan_kirim")
    private SatuanKirim satuanKirim;

    @Enumerated(EnumType.STRING)
    @Column(name = "ukuran_kontainer")
    private UkuranKontainer ukuranKontainer;

    @ManyToOne
    @JoinColumn(name = "id_nota")
    @JsonBackReference
    private Nota nota;

    @ManyToOne
    @JoinColumn(name = "id_kapal_berangkat")
    private KapalBerangkat kapalBerangkat;

    public NotaDetail() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNoKontainer() {
        return noKontainer;
    }

    public void setNoKontainer(String noKontainer) {
        this.noKontainer = noKontainer;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public BigDecimal getHarga() {
        return harga;
    }

    public void setHarga(BigDecimal harga) {
        this.harga = harga;
    }

    public JenisItem getJenisItem() {
        return jenisItem;
    }

    public void setJenisItem(JenisItem jenisItem) {
        this.jenisItem = jenisItem;
    }

    public Kondisi getKondisi() {
        return kondisi;
    }

    public void setKondisi(Kondisi kondisi) {
        this.kondisi = kondisi;
    }

    public SatuanKirim getSatuanKirim() {
        return satuanKirim;
    }

    public void setSatuanKirim(SatuanKirim satuanKirim) {
        this.satuanKirim = satuanKirim;
    }

    public Nota getNota() {
        return nota;
    }

    public void setNota(Nota nota) {
        this.nota = nota;
    }

    public KapalBerangkat getKapalBerangkat() {
        return kapalBerangkat;
    }

    public void setKapalBerangkat(KapalBerangkat kapalBerangkat) {
        this.kapalBerangkat = kapalBerangkat;
    }

    /**
     * @return the ukuranKontainer
     */
    public UkuranKontainer getUkuranKontainer() {
        return ukuranKontainer;
    }

    /**
     * @param ukuranKontainer the ukuranKontainer to set
     */
    public void setUkuranKontainer(UkuranKontainer ukuranKontainer) {
        this.ukuranKontainer = ukuranKontainer;
    }

    /**
     * @return the tambahanMinBayar
     */
    public BigDecimal getTambahanMinBayar() {
        return tambahanMinBayar;
    }

    /**
     * @param tambahanMinBayar the tambahanMinBayar to set
     */
    public void setTambahanMinBayar(BigDecimal tambahanMinBayar) {
        this.tambahanMinBayar = tambahanMinBayar;
    }

    /**
     * @return the jenisItems
     */
    public String getJenisItems() {
        return jenisItems;
    }

    /**
     * @param jenisItems the jenisItems to set
     */
    public void setJenisItems(String jenisItems) {
        this.jenisItems = jenisItems;
    }

    /**
     * @return the coli
     */
    public Integer getColi() {
        return coli;
    }

    /**
     * @param coli the coli to set
     */
    public void setColi(Integer coli) {
        this.coli = coli;
    }

    /**
     * @return the idSj
     */
    public String getIdSj() {
        return idSj;
    }

    /**
     * @param idSj the idSj to set
     */
    public void setIdSj(String idSj) {
        this.idSj = idSj;
    }

}
