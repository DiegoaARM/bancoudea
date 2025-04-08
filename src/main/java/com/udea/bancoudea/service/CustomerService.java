package com.udea.bancoudea.service;

import com.udea.bancoudea.DTO.CustomerDTO;
import com.udea.bancoudea.DTO.PostCustomerDTO;
import com.udea.bancoudea.entity.Customer;
import com.udea.bancoudea.mapper.CustomerMapper;
import com.udea.bancoudea.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    public List<CustomerDTO> getAllCustomer(){
        return customerRepository.findAll().stream()
                .map(customerMapper::toDTO).toList();
    }

    public CustomerDTO getCustomerById(Long id){
        return customerRepository.findById(id).map(customerMapper::toDTO)
                .orElseThrow(()->new RuntimeException("Cliente no encontrado"));
    }

    public CustomerDTO createCustomer(PostCustomerDTO postCustomerDTO){
        if(postCustomerDTO.getBalance()==null){
            throw new IllegalArgumentException("Balance cannot be null");
        }
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(postCustomerDTO.getId());
        customerDTO.setBalance(postCustomerDTO.getBalance());
        customerDTO.setFirstName(postCustomerDTO.getFirstName());
        customerDTO.setLastName(postCustomerDTO.getLastName());

        Random random = new Random();
        StringBuilder accountNumber = new StringBuilder();

        // Generamos un número de cuenta de 10 dígitos
        for (int i = 0; i < 10; i++) {
            accountNumber.append(random.nextInt(10)); // agrega un dígito del 0 al 9
        }

        customerDTO.setAccountNumber(accountNumber.toString());

        Customer customer = customerMapper.toEntity(customerDTO);
        return customerMapper.toDTO(customerRepository.save(customer));
    }

    public CustomerDTO updateCustomer(CustomerDTO customerDTO){
        Customer customer = customerMapper.toEntity(customerDTO);
        return customerMapper.toDTO(customerRepository.save(customer));
    }

    public void deleteCustomer(Long id){
        customerRepository.deleteById(id);
    }


}


