/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nls.dao;

import com.nls.domain.Nota;
import java.util.Date;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author faheem
 */
public interface NotaDao extends PagingAndSortingRepository<Nota, Integer> {

    @Query("from Nota a where a.tokoTujuan.id=:idTokoTujuan")
    public Page<Nota> filterByTokoTujuan(@Param("idTokoTujuan") Integer idTokoTujuan, Pageable pageable);

    @Query("from Nota a where a.tokoTujuan.id = :idTokoTujuan and a.merkTujuan.id = :idMerkTujuan")
    public Page<Nota> filterByTokoMerkTujuan(@Param("idTokoTujuan") Integer idTokoTujuan, @Param("idMerkTujuan") Integer idMerkTujuan, Pageable pageable);

    @Query("from Nota a where upper(a.nomorInvoice) like upper(:search) or upper(a.tokoTujuan.nama) like upper(:search) or upper(a.merkTujuan.nama) like upper(:search)")
    public Page<Nota> filter(@Param("search") String search, Pageable pageable);

}
