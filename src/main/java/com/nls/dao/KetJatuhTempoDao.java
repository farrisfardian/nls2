/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nls.dao;

import com.nls.domain.KetJatuhTempo;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author faheem
 */
public interface KetJatuhTempoDao extends PagingAndSortingRepository<KetJatuhTempo, Integer> {

    public KetJatuhTempo findByNama(String value);

    @Query("from KetJatuhTempo a where upper(a.nama) like upper(:search)")
    public Page<KetJatuhTempo> filter(@Param("search") String search, Pageable pageable);

}
