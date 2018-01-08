/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nls.service;

//import com.nls.domain.User;

import com.nls.domain.User;


/**
 *
 * @author faheem
 */
public interface AppService {

    public User getCurrentUser();

    public String getSessionId();
    
}
