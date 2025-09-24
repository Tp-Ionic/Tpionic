package com.example.demo.service;

import com.example.demo.model.Admin;
import com.example.demo.repository.Adminrepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class service_admin {
@Autowired
private Adminrepository adminrepository;
private String email;
private String motdepasse;

public void compteadmin{
        List<Admin> admins= adminrepository.findAll();
        if (admins.isEmpty()){
            Admin admin=new Admin();
            admin.setNom("Tall");
        }
    }
}
