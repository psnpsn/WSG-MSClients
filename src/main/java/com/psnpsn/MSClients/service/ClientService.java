/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.psnpsn.MSClients.service;

import com.psnpsn.MSClients.dao.ClientDAO;
import com.psnpsn.MSClients.dao.RoleDAO;
import com.psnpsn.MSClients.model.Client;
import com.psnpsn.MSClients.model.Role;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Guqnn
 */
@Service
public class ClientService implements GenericService {
    
    @Autowired
    private RoleDAO roleDAO;

    private ClientDAO clientDAO;
    
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Autowired
    public ClientService(ClientDAO clientDAO,
            BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.clientDAO = clientDAO;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    
    @Override
    public List<Client> getAll() {
        return clientDAO.findAll();
    }

    @Override
    public Client getOne(long id) {
        Optional<Client> client = clientDAO.findById(id);
        return client.get(); 
    }

    @Override
    public Client add(Client client) {
        client.setPassword(bCryptPasswordEncoder.encode(client.getPassword()));
        Role role = roleDAO.findByRoleName("STANDARD_USER");
        List<Role> list = new ArrayList<>();
        list.add(role);
        //client.setRoles(list);
        return clientDAO.save(client);
    }

    @Override
    public Client update(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Client car) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
