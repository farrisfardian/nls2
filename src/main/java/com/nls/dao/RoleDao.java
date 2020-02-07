/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nls.dao;

import com.nls.domain.Menu;
import com.nls.domain.Role;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author cak-ust
 */
public interface RoleDao extends PagingAndSortingRepository<Role, String>{
    @Query("select r.menuSet from Role r where r.id=:roleId")
    public Set<Menu> findMenuSetRole(@Param("roleId") String roleId);
    
    @Query("select m from Role m where upper(m.name) like upper(:search) or upper(m.description) like upper(:search) "
            + "order by m.name")
    public Page<Role> filter(@Param("search") String search ,Pageable pageable);
    
}
