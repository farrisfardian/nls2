/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nls.dao;

import com.nls.domain.Pelayaran;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author faheem
 */
public interface PelayaranDao extends PagingAndSortingRepository<Pelayaran, Integer> {

    public Pelayaran findByNama(String value);

    @Query("from Pelayaran a where upper(a.nama) like upper(:search)")
    public Page<Pelayaran> filter(@Param("search") String search, Pageable pageable);

}
