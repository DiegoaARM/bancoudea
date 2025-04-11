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

    public CustomerDTO createCustomer(PostCustomerDTO postCustomerDTO) {
        if (postCustomerDTO.getBalance() == null) {
            throw new IllegalArgumentException("Balance cannot be null");
        }

        if (postCustomerDTO.getBalance() < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }

        if (postCustomerDTO.getFirstName() == null || postCustomerDTO.getLastName() == null) {
            throw new IllegalArgumentException("First name and last name cannot be null");
        }

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(postCustomerDTO.getId());
        customerDTO.setBalance(postCustomerDTO.getBalance());
        customerDTO.setFirstName(postCustomerDTO.getFirstName());
        customerDTO.setLastName(postCustomerDTO.getLastName());

        // Generar un número de cuenta único
        String accountNumber = generateUniqueAccountNumber();
        customerDTO.setAccountNumber(accountNumber);

        Customer customer = customerMapper.toEntity(customerDTO);
        return customerMapper.toDTO(customerRepository.save(customer));
    }

    private String generateUniqueAccountNumber() {
        Random random = new Random();
        String accountNumber;

        do {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < 10; i++) {
                builder.append(random.nextInt(10));
            }
            accountNumber = builder.toString();
        } while (customerRepository.existsByAccountNumber(accountNumber));

        return accountNumber;
    }

    public CustomerDTO updateCustomer(CustomerDTO customerDTO){
        if (!customerRepository.existsById(customerDTO.getId())) {
            throw new RuntimeException("Customer with ID " + customerDTO.getId() + " not found for update");
        }
        Customer customer = customerMapper.toEntity(customerDTO);
        return customerMapper.toDTO(customerRepository.save(customer));
    }

    public void deleteCustomer(Long id){
        if (!customerRepository.existsById(id)) {
            throw new RuntimeException("Customer with ID " + id + " not found for delete");
        }
        customerRepository.deleteById(id);
    }


}


