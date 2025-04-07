package com.udea.bancoudea.controller;

import com.udea.bancoudea.DTO.TransactionDTO;
import com.udea.bancoudea.DTO.TransferRequestDTO;
import com.udea.bancoudea.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionFacade;

    public TransactionController(TransactionService transactionFacade) { this.transactionFacade = transactionFacade; }

    @GetMapping
    public ResponseEntity<List<TransactionDTO>> getAllTransactions(){
        return ResponseEntity.ok(transactionFacade.getAllTransactions());
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<List<TransactionDTO>> getTransactionByAccountNumber(@PathVariable String accountNumber){
        return ResponseEntity.ok(transactionFacade.getTransactionsForAccounts(accountNumber));
    }

    @PostMapping
    public ResponseEntity<TransactionDTO> createTransaction(@RequestBody TransferRequestDTO transferRequestDTO){
        return ResponseEntity.ok(transactionFacade.transferMoney(transferRequestDTO));
    }
}
