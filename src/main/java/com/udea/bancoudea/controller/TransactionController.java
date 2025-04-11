package com.udea.bancoudea.controller;

import com.udea.bancoudea.DTO.TransactionDTO;
import com.udea.bancoudea.DTO.TransferRequestDTO;
import com.udea.bancoudea.service.TransactionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "http://localhost:5173")
public class TransactionController {

    private final TransactionService transactionFacade;

    public TransactionController(TransactionService transactionFacade) { this.transactionFacade = transactionFacade; }

    private boolean isValidReferer(HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        System.out.println("Referer recibido: " + referer); // ← Debug
        return referer != null && referer.startsWith("http://localhost:5173/");
    }

    @GetMapping
    public ResponseEntity<?> getAllTransactions(HttpServletRequest request){
        if (!isValidReferer(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acceso denegado: Referer no válido");
        }
        return ResponseEntity.ok(transactionFacade.getAllTransactions());
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<?> getTransactionByAccountNumber(@PathVariable String accountNumber, HttpServletRequest request){
        if (!isValidReferer(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acceso denegado: Referer no válido");
        }
        return ResponseEntity.ok(transactionFacade.getTransactionsForAccounts(accountNumber));
    }

    @PostMapping
    public ResponseEntity<?> createTransaction(@RequestBody TransferRequestDTO transferRequestDTO, HttpServletRequest request){
        if (!isValidReferer(request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acceso denegado: Referer no válido");
        }
        return ResponseEntity.ok(transactionFacade.transferMoney(transferRequestDTO));
    }
}
