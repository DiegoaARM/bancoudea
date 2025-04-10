package com.udea.bancoudea.controller;

import com.udea.bancoudea.DTO.CustomerDTO;
import com.udea.bancoudea.DTO.PostCustomerDTO;
import com.udea.bancoudea.service.CustomerService;
import org.springframework.http.HttpEntity;
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

    //get all clients
    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers(){
        return ResponseEntity.ok(customerFacade.getAllCustomer());
    }

    //get client by ID
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable long id){
        return ResponseEntity.ok(customerFacade.getCustomerById(id));
    }

    //create a new client
    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody PostCustomerDTO postCustomerDTO){
        return ResponseEntity.ok(customerFacade.createCustomer(postCustomerDTO));
    }

    @PutMapping
    public ResponseEntity<CustomerDTO> updateCustomer(@RequestBody CustomerDTO customerDTO){
        return ResponseEntity.ok(customerFacade.updateCustomer(customerDTO));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCustomer(@RequestBody CustomerDTO customerDTO){
        customerFacade.deleteCustomer(customerDTO.getId());
        return ResponseEntity.noContent().build();
    }

}
