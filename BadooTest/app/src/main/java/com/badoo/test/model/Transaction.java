package com.badoo.test.model;

public class Transaction {
    public double amount;
    public String sku;
    public String currency;

    public Transaction(double amount, String sku, String currency) {
        this.amount = amount;
        this.sku = sku;
        this.currency = currency;
    }
}
