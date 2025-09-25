package com.example.demo.service;

import com.example.demo.Enumeration.Role;
import com.example.demo.model.Admin;
import com.example.demo.repository.Adminrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
@Service

public class ServiceAdmin {
@Autowired
private Adminrepository adminrepository;

    private String email=System.getenv("Email");
private String motdepasse=System.getenv("Password");

public void compteadmin() {
        List<Admin> admins= adminrepository.findAll();
        if (admins.isEmpty()){
            Admin admin=new Admin();
            admin.setNom("Kadiatou Tall");
            admin.setEmail(email);
            admin.setMotDePasse(BCrypt.hashpw(motdepasse,BCrypt.gensalt()));
            admin.setRole(Role.ROLE_ADMIN);

            adminrepository.save(admin);
        }
    }
}
