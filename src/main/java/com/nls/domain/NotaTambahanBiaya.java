/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nls.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "t_nota_tambahan_biaya")
public class NotaTambahanBiaya {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "jumlah")
    private BigDecimal jumlah;

    @Column(name = "harga")
    private BigDecimal harga;

    @Column(name = "no_kontainer", length = 30)
    private String noKontainer;

    @Column(name = "jenis_items")
    private String jenisItems;

    @ManyToOne
    @JoinColumn(name = "id_nota")
    @JsonBackReference
    private Nota nota;

    @ManyToOne
    @JoinColumn(name = "id_tambahan_biaya")
    private TambahanBiaya tambahanBiaya;

    @ManyToOne
    @JoinColumn(name = "id_kapal_berangkat")
    private KapalBerangkat kapalBerangkat;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getJumlah() {
        return jumlah;
    }

    public void setJumlah(BigDecimal jumlah) {
        this.jumlah = jumlah;
    }

    public BigDecimal getHarga() {
        return harga;
    }

    public void setHarga(BigDecimal harga) {
        this.harga = harga;
    }

    public Nota getNota() {
        return nota;
    }

    public void setNota(Nota nota) {
        this.nota = nota;
    }

    public TambahanBiaya getTambahanBiaya() {
        return tambahanBiaya;
    }

    public void setTambahanBiaya(TambahanBiaya tambahanBiaya) {
        this.tambahanBiaya = tambahanBiaya;
    }

    /**
     * @return the kapalBerangkat
     */
    public KapalBerangkat getKapalBerangkat() {
        return kapalBerangkat;
    }

    /**
     * @param kapalBerangkat the kapalBerangkat to set
     */
    public void setKapalBerangkat(KapalBerangkat kapalBerangkat) {
        this.kapalBerangkat = kapalBerangkat;
    }

    /**
     * @return the noKontainer
     */
    public String getNoKontainer() {
        return noKontainer;
    }

    /**
     * @param noKontainer the noKontainer to set
     */
    public void setNoKontainer(String noKontainer) {
        this.noKontainer = noKontainer;
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
}
