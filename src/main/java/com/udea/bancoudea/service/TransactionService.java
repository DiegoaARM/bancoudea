package com.udea.bancoudea.service;

import com.udea.bancoudea.DTO.TransactionDTO;
import com.udea.bancoudea.DTO.TransferRequestDTO;
import com.udea.bancoudea.entity.Customer;
import com.udea.bancoudea.entity.Transaction;
import com.udea.bancoudea.mapper.TransactionMapper;
import com.udea.bancoudea.repository.CustomerRepository;
import com.udea.bancoudea.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private CustomerRepository customerRepository;

    public List<TransactionDTO> getAllTransactions(){
        return transactionRepository.findAll().stream()
                .map(transactionMapper::toDTO).toList();
    }

    public TransactionDTO transferMoney(TransferRequestDTO transferRequestDTO){
        //validate that the numbers of the account not being nulls
        if (transferRequestDTO.getReceiverAccountNumber() == null ||
                transferRequestDTO.getSenderAccountNumber() == null) {
            throw new IllegalArgumentException("Sender and receiver account numbers are required");
        }

        if (transferRequestDTO.getSenderAccountNumber().equals(transferRequestDTO.getReceiverAccountNumber())) {
            throw new IllegalArgumentException("Sender and receiver accounts must be different");
        }

        if (transferRequestDTO.getAmount() == null || transferRequestDTO.getAmount() <= 0) {
            throw new IllegalArgumentException("Transfer amount must be greater than 0");
        }

        //find the clients by number of account
        Customer sender = customerRepository.findByAccountNumber(transferRequestDTO.getSenderAccountNumber())
                .orElseThrow(()->new RuntimeException("Sender Account Number not found"));

        Customer receiver = customerRepository.findByAccountNumber(transferRequestDTO.getReceiverAccountNumber())
                .orElseThrow(()->new RuntimeException("Receiver Account Number not found"));

        //validate if sender has enough money
        if(sender.getBalance() < transferRequestDTO.getAmount()){
            throw new IllegalArgumentException("Sender Balance not enough");
        }

        //make the transfer
        sender.setBalance(sender.getBalance() - transferRequestDTO.getAmount());
        receiver.setBalance(receiver.getBalance() + transferRequestDTO.getAmount());

        //save the changes on the accounts
        customerRepository.save(sender);
        customerRepository.save(receiver);

        //create and save the transaction
        Transaction transaction = new Transaction();
        transaction.setSenderAccountNumber(sender.getAccountNumber());
        transaction.setReceiverAccountNumber(receiver.getAccountNumber());
        transaction.setAmount(transferRequestDTO.getAmount());
        transactionRepository.save(transaction);

        //return the transaction created as a DTO
        TransactionDTO savedTransactionDTO = new TransactionDTO();
        savedTransactionDTO.setSenderAccountNumber(sender.getAccountNumber());
        savedTransactionDTO.setReceiverAccountNumber(receiver.getAccountNumber());
        savedTransactionDTO.setAmount(transferRequestDTO.getAmount());
        return savedTransactionDTO;

    }

    public List<TransactionDTO> getTransactionsForAccounts(String accountNumber){
        List<Transaction> transactions = transactionRepository.findBySenderAccountNumberOrReceiverAccountNumber(accountNumber,accountNumber);
        return transactions.stream().map(transaction -> {
            TransactionDTO transactionDTO = new TransactionDTO();
            transactionDTO.setId(transaction.getId());
            transactionDTO.setSenderAccountNumber(transaction.getSenderAccountNumber());
            transactionDTO.setReceiverAccountNumber(transaction.getReceiverAccountNumber());
            transactionDTO.setAmount(transaction.getAmount());
            transactionDTO.setTimestamp(transaction.getTimestamp());
            return transactionDTO;
        }).collect(Collectors.toList());
    }

}
