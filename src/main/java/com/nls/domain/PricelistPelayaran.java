/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nls.domain;

import com.nls.constant.JenisStuffing;
import com.nls.constant.UkuranKontainer;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author faheem
 */
@Entity
@Table(name = "setting_pricelist_pelayaran")
public class PricelistPelayaran {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Temporal(TemporalType.DATE)
    @Column(name = "tmt_berlaku")
    private Date tmtBerlaku;

    @Column(name = "harga")
    private Double harga;

    @ManyToOne
    @JoinColumn(name = "id_kota_tujuan")
    private Kota kotaTujuan;

    @ManyToOne
    @JoinColumn(name = "id_kota_asal")
    private Kota kotaAsal;

    @ManyToOne
    @JoinColumn(name = "id_kategori_barang")
    private KategoriBarang kategoriBarang;

    @ManyToOne
    @JoinColumn(name = "id_pelayaran")
    private Pelayaran pelayaran;

    @ManyToOne
    @JoinColumn(name = "id_satuan_kirim")
    private SatuanKirim satuanKirim;

    @Enumerated(EnumType.STRING)
    @Column(name = "jenis_stuffing")
    private JenisStuffing jenisStuffing;

    @Enumerated(EnumType.STRING)
    @Column(name = "ukuran_kontainer")
    private UkuranKontainer ukuranKontainer;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the harga
     */
    public Double getHarga() {
        return harga;
    }

    /**
     * @param harga the harga to set
     */
    public void setHarga(Double harga) {
        this.harga = harga;
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
     * @return the kategoriBarang
     */
    public KategoriBarang getKategoriBarang() {
        return kategoriBarang;
    }

    /**
     * @param kategoriBarang the kategoriBarang to set
     */
    public void setKategoriBarang(KategoriBarang kategoriBarang) {
        this.kategoriBarang = kategoriBarang;
    }

    /**
     * @return the tmtBerlaku
     */
    public Date getTmtBerlaku() {
        return tmtBerlaku;
    }

    /**
     * @param tmtBerlaku the tmtBerlaku to set
     */
    public void setTmtBerlaku(Date tmtBerlaku) {
        this.tmtBerlaku = tmtBerlaku;
    }

}
