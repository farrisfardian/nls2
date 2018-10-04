/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nls.dao;

import com.nls.domain.PricelistPelayaran;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author faheem
 */
public interface PricelistPelayaranDao extends PagingAndSortingRepository<PricelistPelayaran, Integer> {

    @Query("from PricelistPelayaran a where upper(a.pelayaran.nama) like upper(:search) or upper(a.kotaTujuan.nama) like upper(:search)  or upper(a.kotaAsal.nama) like upper(:search)  or upper(a.satuanKirim.nama) like upper(:search) ")
    public Page<PricelistPelayaran> filter(@Param("search") String search, Pageable pageable);

}
