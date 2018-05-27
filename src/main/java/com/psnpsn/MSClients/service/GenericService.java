/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.psnpsn.MSClients.service;

import com.psnpsn.MSClients.model.Client;
import java.util.List;

/**
 *
 * @author Guqnn
 */
public interface GenericService {
    List<Client> getAll();
    Client getOne(long id);
    Client add(Client entity);
    Client update(long id);
    void delete(Client entity);
}
