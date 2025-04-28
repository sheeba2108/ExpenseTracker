package com.expensetracker.project.controller;

import com.expensetracker.project.entity.Transaction;
import com.expensetracker.project.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200") // Allow frontend to call API
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    // Add new transaction
    @PostMapping("/add/{userId}")
    public ResponseEntity<Transaction> addTransaction(@RequestBody Transaction transaction, @PathVariable Long userId) {
        Transaction savedTransaction = transactionService.addTransaction(transaction, userId);
        return ResponseEntity.ok(savedTransaction);
    }

    // Get all transactions for a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Transaction>> getTransactionsByUserId(@PathVariable Long userId) {
        List<Transaction> transactions = transactionService.getTransactionsByUserId(userId);
        return ResponseEntity.ok(transactions);
    }

    // Get transaction by ID
    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
        Optional<Transaction> transaction = transactionService.getTransactionById(id);
        return transaction.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update transaction
    @PutMapping("/update/{id}/{userId}")
    public ResponseEntity<Transaction> updateTransaction(
            @PathVariable Long id,
            @RequestBody Transaction updatedTransaction,
            @PathVariable Long userId) {
        Transaction updated = transactionService.updateTransaction(id, updatedTransaction, userId);
        return ResponseEntity.ok(updated);
    }

    // Delete transaction
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }
}
//http://localhost:8080/api/transactions/add/{userId}
//http://localhost:8080/api/transactions/user/{userId}
//http://localhost:8080/api/transactions/{id}
//http://localhost:8080/api/transactions/delete/{id}
