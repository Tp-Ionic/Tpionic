package com.example.demo.controller;

import com.example.demo.dto.dto_parrain;
import com.example.demo.dto.dto_parrain.CreateRequest;
import com.example.demo.dto.dto_parrain.UpdateRequest;
import com.example.demo.service.service_parrain;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parrains")
public class controller_parrain {

    private final service_parrain service;

    public controller_parrain(service_parrain service) {
        this.service = service;
    }

    @PostMapping
    public dto_parrain.Response creer(@RequestBody CreateRequest req){
        return service.creer(req);
    }

    @GetMapping
    public List<dto_parrain.Response> list(){
        return service.list();
    }

    @GetMapping("/{id}")
    public dto_parrain.Response get(@PathVariable int id){
        return service.get(id);
    }

    @PutMapping("/{id}")
    public dto_parrain.Response update(@PathVariable int id, @RequestBody UpdateRequest req){
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        service.delete(id);
    }
}