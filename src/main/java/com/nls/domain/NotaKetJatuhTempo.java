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
@Table(name = "t_nota_ket_jatuh_tempo")
public class NotaKetJatuhTempo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_nota")
    @JsonBackReference
    private Nota nota;

    @ManyToOne
    @JoinColumn(name = "id_ket_jatuh_tempo")
    private KetJatuhTempo ketJatuhTempo;

    public NotaKetJatuhTempo() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Nota getNota() {
        return nota;
    }

    public void setNota(Nota nota) {
        this.nota = nota;
    }

    /**
     * @return the ketJatuhTempo
     */
    public KetJatuhTempo getKetJatuhTempo() {
        return ketJatuhTempo;
    }

    /**
     * @param ketJatuhTempo the ketJatuhTempo to set
     */
    public void setKetJatuhTempo(KetJatuhTempo ketJatuhTempo) {
        this.ketJatuhTempo = ketJatuhTempo;
    }

}
