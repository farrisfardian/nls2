/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nls.dao;

import com.nls.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 *
 * @author ustadho
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}

