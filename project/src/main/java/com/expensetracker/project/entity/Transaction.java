package com.expensetracker.project.entity;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Double amount;
    private String type; // income or expense
    private String category;
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY) // Use Lazy fetching
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Transaction() {}

    public Transaction(String title, Double amount, String type, String category, LocalDate date, User user) {
        this.title = title;
        this.amount = amount;
        this.type = type;
        this.category = category;
        this.date = date;
        this.user = user;
    }


    public Long getId() {        return id;
    }
    public void setId(Long id) {        this.id = id;
    }
    public String getTitle() {        return title;
    }
    public void setTitle(String title) {        this.title = title;
    }
    public Double getAmount() {        return amount;
    }
    public void setAmount(Double amount) {        this.amount = amount;
    }
    public String getType() {        return type;
    }
    public void setType(String type) {        this.type = type;
    }
    public String getCategory() {        return category;
    }
    public void setCategory(String category) {        this.category = category;
    }
    public LocalDate getDate() {        return date;
    }
    public void setDate(LocalDate date) {        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

