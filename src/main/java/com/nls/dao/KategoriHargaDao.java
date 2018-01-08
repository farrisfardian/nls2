/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nls.dao;

import com.nls.domain.KategoriHarga;
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
public interface KategoriHargaDao extends PagingAndSortingRepository<KategoriHarga, Integer> {

    public KategoriHarga findByNama(String value);

    @Query("from KategoriHarga a where upper(a.nama) like upper(:search)")
    public Page<KategoriHarga> filter(@Param("search") String search, Pageable pageable);
    
    @Query("from KategoriHarga a where a.satuanKirim.nama = :satuanKirim and coalesce(a.paket,false) = :paket")
    public List<KategoriHarga> filterByPaketSatuanKirim(@Param("paket") Boolean paket,@Param("satuanKirim") String satuanKirim);

}
