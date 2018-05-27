/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.psnpsn.MSClients.dao;

import com.psnpsn.MSClients.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Guqnn
 */
@Repository
public interface RoleDAO extends JpaRepository<Role, Long> {
    public Role findByRoleName(String roleName);
}
