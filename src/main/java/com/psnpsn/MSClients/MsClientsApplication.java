package com.psnpsn.MSClients;

import com.psnpsn.MSClients.dao.ClientDAO;
import com.psnpsn.MSClients.dao.RoleDAO;
import com.psnpsn.MSClients.model.Client;
import com.psnpsn.MSClients.model.Role;
import com.psnpsn.MSClients.service.ClientService;
import java.time.LocalDateTime;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class MsClientsApplication {

	public static void main(String[] args) {
            ApplicationContext context = SpringApplication.run(MsClientsApplication.class, args);
            
            RoleDAO roleDao = context.getBean(RoleDAO.class);
            ClientService clientService = context.getBean(ClientService.class);
            
            Role roleUser = new Role();
            Role roleAdmin = new Role();
            roleUser.setRoleName("STANDARD_USER");
            roleUser.setDescription("Standard User - N'a pas de droits admin");
            roleDao.save(roleUser);
            roleAdmin.setRoleName("ADMIN_USER");
            roleAdmin.setDescription("Admin User - Il a les permissions d'effectuer les taches d'un admin");
            roleDao.save(roleAdmin);
            
            Client client = new Client();
            client.setUsername("user@user");
            client.setPassword("user");
            client.setNom("User");
            client.setPrenom("Uzer");
            client.setAdresse("Marsa, Tunis, Tunisia");
            client.setNumTel("53456433");
            client.setDateCreation(LocalDateTime.now());
            clientService.add(client);
            
        }
}
