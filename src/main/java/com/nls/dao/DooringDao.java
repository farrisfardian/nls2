/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nls.dao;

import com.nls.domain.Dooring;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author faheem
 */
public interface DooringDao extends PagingAndSortingRepository<Dooring, Integer> {

    @Query("from Dooring a where upper(a.kotaTujuan.nama) like upper(:search)  or upper(a.kotaAsal.nama) like upper(:search) ")
    public Page<Dooring> filter(@Param("search") String search, Pageable pageable);

}
