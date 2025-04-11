package com.udea.bancoudea.controller;

import com.udea.bancoudea.DTO.CustomerDTO;
import com.udea.bancoudea.DTO.PostCustomerDTO;
import com.udea.bancoudea.service.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "http://localhost:5173")
public class CustomerController {

    private final CustomerService customerFacade;

    public CustomerController(CustomerService customerFacade) {
        this.customerFacade = customerFacade;
    }

    private boolean isValidReferer(HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        System.out.println("Referer recibido: " + referer); // ← Debug
        return referer != null && referer.startsWith("http://localhost:5173/");
    }

    //get all clients
    @GetMapping
    public ResponseEntity<?> getAllCustomers(HttpServletRequest request){
        if (!isValidReferer(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acceso denegado: Referer no válido");
        }
        return ResponseEntity.ok(customerFacade.getAllCustomer());
    }

    //get client by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomer(@PathVariable long id, HttpServletRequest request){
        if (!isValidReferer(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acceso denegado: Referer no válido");
        }
        return ResponseEntity.ok(customerFacade.getCustomerById(id));
    }

    //create a new client
    @PostMapping
    public ResponseEntity<?> createCustomer(@RequestBody PostCustomerDTO postCustomerDTO, HttpServletRequest request){
        if (!isValidReferer(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acceso denegado: Referer no válido");
        }
        return ResponseEntity.ok(customerFacade.createCustomer(postCustomerDTO));
    }

    @PutMapping
    public ResponseEntity<?> updateCustomer(@RequestBody CustomerDTO customerDTO, HttpServletRequest request){
        if (!isValidReferer(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acceso denegado: Referer no válido");
        }
        return ResponseEntity.ok(customerFacade.updateCustomer(customerDTO));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteCustomer(@RequestBody CustomerDTO customerDTO, HttpServletRequest request){
        if (!isValidReferer(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acceso denegado: Referer no válido");
        }
        customerFacade.deleteCustomer(customerDTO.getId());
        return ResponseEntity.noContent().build();
    }


}
