/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nls.dao;

import com.nls.domain.Pembayaran;
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
public interface PembayaranDao extends PagingAndSortingRepository<Pembayaran, Integer> {

    @Query("from Pembayaran a where a.tokoTujuan.id=:idTokoTujuan")
    public Page<Pembayaran> filterByTokoTujuan(@Param("idTokoTujuan") Integer idTokoTujuan, Pageable pageable);

    @Query("from Pembayaran a where a.tokoTujuan.id = :idTokoTujuan and a.merkTujuan.id = :idMerkTujuan")
    public Page<Pembayaran> filterByTokoMerkTujuan(@Param("idTokoTujuan") Integer idTokoTujuan, @Param("idMerkTujuan") Integer idMerkTujuan, Pageable pageable);

    @Query("from Pembayaran a where upper(a.nomor) like upper(:search) or upper(a.tokoTujuan.nama) like upper(:search) or upper(a.merkTujuan.nama) like upper(:search)")
    public Page<Pembayaran> filter(@Param("search") String search, Pageable pageable);

}
