package com.expensetracker.project.service;

import com.expensetracker.project.entity.Transaction;
import com.expensetracker.project.entity.User; // Import User entity
import com.expensetracker.project.repository.TransactionRepository;
import com.expensetracker.project.repository.UserRepository; // Import UserRepository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Import for managing transactions
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository; // Inject UserRepository to validate user

    // Add a new transaction, validating the user
    @Transactional // Use @Transactional for database operations
    public Transaction addTransaction(Transaction transaction, Long userId) {
        // First, verify that the user exists
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new IllegalArgumentException("User with id " + userId + " not found");
        }
        User user = userOptional.get();
        transaction.setUser(user); // Set the user for the transaction
        return transactionRepository.save(transaction);
    }

    // Get all transactions for a specific user
    public List<Transaction> getTransactionsByUserId(Long userId) {
        return transactionRepository.findByUserId(userId);
    }

    // Get a specific transaction by its ID
    public Optional<Transaction> getTransactionById(Long id) {
        return transactionRepository.findById(id);
    }

    // Delete a specific transaction by its ID
    @Transactional
    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }

    // Update an existing transaction
    @Transactional
    public Transaction updateTransaction(Long id, Transaction updatedTransaction, Long userId) {
        Optional<Transaction> existingTransactionOptional = transactionRepository.findById(id);
        if (existingTransactionOptional.isEmpty()) {
            throw new IllegalArgumentException("Transaction with id " + id + " not found");
        }
        Transaction existingTransaction = existingTransactionOptional.get();

        //validate user.
        if(existingTransaction.getUser().getId() != userId){
            throw new IllegalArgumentException("Transaction with id " + id + " does not belong to user " + userId);
        }
        existingTransaction.setTitle(updatedTransaction.getTitle());
        existingTransaction.setAmount(updatedTransaction.getAmount());
        existingTransaction.setType(updatedTransaction.getType());
        existingTransaction.setCategory(updatedTransaction.getCategory());
        existingTransaction.setDate(updatedTransaction.getDate());

        return transactionRepository.save(existingTransaction);
    }
}
