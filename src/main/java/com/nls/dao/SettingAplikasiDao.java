/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nls.dao;

import com.nls.domain.SettingAplikasi;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author cak-ust
 */
public interface SettingAplikasiDao extends PagingAndSortingRepository<SettingAplikasi, String> {

    @Query(value = "select * from setting_aplikasi where to_char(tmt_berlaku,'yyyy-mm-dd') <= :tanggal order by tmt_berlaku desc limit 1", nativeQuery = true)
    public SettingAplikasi getLastByTgl(@Param("tanggal") String tanggal);
}
