package com.example.demo.component;

import com.example.demo.service.ServiceAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitialisationAdmin implements CommandLineRunner {
    @Autowired
    private ServiceAdmin ServiceAdmin;

    @Override
    public void run(String... args) throws Exception {
        ServiceAdmin.compteadmin();

    }
}
