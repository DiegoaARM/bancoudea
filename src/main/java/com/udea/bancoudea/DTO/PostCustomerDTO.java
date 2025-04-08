package com.udea.bancoudea.DTO;

public class PostCustomerDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private Double balance;

    public PostCustomerDTO() {
    }

    public PostCustomerDTO(Long id, String firstName, String lastName, Double balance) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}