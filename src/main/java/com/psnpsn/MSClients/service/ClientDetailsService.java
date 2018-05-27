/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.psnpsn.MSClients.service;

import com.psnpsn.MSClients.dao.ClientDAO;
import com.psnpsn.MSClients.model.Client;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Guqnn
 */
@Service("userDetailsService")
public class ClientDetailsService implements UserDetailsService {

    @Autowired
    private ClientDAO clientdao; 
    
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
       Client client = clientdao.findByUsername(s);

        if(client == null) {
            throw new UsernameNotFoundException(String.format("The username %s doesn't exist", s));
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        client.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        });

        UserDetails userDetails = new org.springframework.security.core.userdetails.
                User(client.getUsername(), client.getPassword(), authorities);

        return userDetails;
    }   
}
    

