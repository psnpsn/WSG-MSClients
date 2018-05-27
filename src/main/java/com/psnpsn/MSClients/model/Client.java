/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.psnpsn.MSClients.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 *
 * @author Guqnn
 */
@Entity
@Table(name="clients")
public class Client {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private long id;
    
    @Column(name="username")
    private String username;
    
    @Column(name="password")
    private String password;
    
    private String nom;
    private String prenom;
    private String adresse;
    private String numTel;
    private LocalDateTime dateCreation;
    private LocalDateTime dateLogin;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "app_role", joinColumns
            = @JoinColumn(name = "user_id",
            referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id",
                    referencedColumnName = "id"))
    private List<Role> roles;
    
    public Client() {
    }

    public Client(long id, String username, String password, String nom, String prenom, String adresse, String numTel, LocalDateTime dateCreation, LocalDateTime dateLogin, List<Role> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.numTel = numTel;
        this.dateCreation = dateCreation;
        this.dateLogin = dateLogin;
        this.roles = roles;
    }

    

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getNumTel() {
        return numTel;
    }

    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDateTime getDateLogin() {
        return dateLogin;
    }

    public void setDateLogin(LocalDateTime dateLogin) {
        this.dateLogin = dateLogin;
    }
    
     public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
    
    public void addRole(Role role){
        this.roles.add(role);
    }
    
}
