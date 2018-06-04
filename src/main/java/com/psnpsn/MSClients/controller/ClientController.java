/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.psnpsn.MSClients.controller;

import com.psnpsn.MSClients.model.Client;
import com.psnpsn.MSClients.service.GenericService;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author Guqnn
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ClientController {
    @Autowired
    private GenericService cservice;
   
    @PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
    @GetMapping("/clients")
    public List<Client> getAllClients() {
        List<Client> list = cservice.getAll();
        System.out.println("clients");
        System.out.println(list);
	return list;
    }
    @PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
    @GetMapping("/clients/{username}")
    public Client getClientById(@PathVariable String username) {
        return cservice.getByUsername(username);
    }
    
    @PostMapping("/clients")
    public ResponseEntity<Object> createClient(@RequestBody Client client) {
	Client savedClient = cservice.add(client);

	URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
			.buildAndExpand(savedClient.getId()).toUri();

	return ResponseEntity.created(location).build();
    }
    
}
