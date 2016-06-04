package com.badoo.test.controller;

import com.badoo.test.helper.Logger;
import com.badoo.test.helper.Utilities;
import com.badoo.test.model.Rate;
import com.badoo.test.model.Transaction;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionsManager {
    private static final String ASSETS_FIRST_FOLDER = "First_data_set"; // First Asset folder path
    private static final String ASSETS_SECOND_FOLDER = "Second_data_set"; // Second Asset folder path
    public static final String ASSETS_USED = ASSETS_FIRST_FOLDER;         // Asset folder used (First or second)

    private static final String RATES_FILE_NAME = "rates.json";
    private static final String TRANSACTIONS_FILE_NAME = "transactions.json";

    Gson gson; // To parse Json
    Map<String, ArrayList<Transaction>> transactionsMap; // <SKU, List>
    ArrayList<Transaction> transactions; // Transactions list parsed
    ArrayList<Rate> rates; // Rates list

    private static TransactionsManager _instance = null;
    public static TransactionsManager getInstance() {
        if(_instance == null)
            _instance = new TransactionsManager();
        return _instance;
    }

    /*
     * Constructor
     */
    public TransactionsManager() {
        gson = new Gson();
    }

    /*
     * Initialize rates and transactions
     */
    public void init() {

        // Read Json from files
        String ratesJson = Utilities.readJsonFromAssetFile(ASSETS_USED+"/"+ RATES_FILE_NAME);
        String transactionsJson = Utilities.readJsonFromAssetFile(ASSETS_USED+"/"+ TRANSACTIONS_FILE_NAME);

        // Parse Transactions
        Type transactionsListType = new TypeToken<List<Transaction>>() {}.getType();
        transactions = gson.fromJson(transactionsJson, transactionsListType);
        Logger.instance().v("TransactionsList", transactions);

        // Parse Rates
        Type ratesListType = new TypeToken<List<Rate>>() {}.getType();
        rates = gson.fromJson(ratesJson, ratesListType);
        Logger.instance().v("Rates", rates);

        // Initialize transactions Map, it will hold singl SKU and all the list for this SKU
        transactionsMap = new HashMap<>();
        if(transactions != null) {
            for (Transaction transaction: transactions) {
                ArrayList<Transaction> transactionsListFromMap = transactionsMap.get(transaction.sku);
                // SKU found in the map, so Add the transaction to it
                if(transactionsListFromMap != null && transactionsListFromMap.size() > 0) {
                    transactionsListFromMap.add(transaction);
                    transactionsMap.put(transaction.sku, transactionsListFromMap);
                }
                // SKU Not found
                else {
                    transactionsListFromMap = new ArrayList<>();
                    transactionsListFromMap.add(transaction);
                    transactionsMap.put(transaction.sku, transactionsListFromMap);
                }
            } // Loop on transactions list

        } // if not empty transactions list

        Logger.instance().v("transactionsMap", transactionsMap);
    }

    /*
     * Get Amount from it's currency to GBP
     */
    public double getAmountInGBP(double amount, String amountCurrency) {
        if(amountCurrency.equalsIgnoreCase("GBP"))
            return amount;
        if(rates != null) {
            // Check for GBP direct, convert from the currency to GBP
            for (Rate rate : rates) {
                if (rate.from.equalsIgnoreCase(amountCurrency) && rate.to.equalsIgnoreCase("GBP")) {
                    return rate.rate * amount;
                }
            }
            // If Currency to GBP not found, then try to get it and it's 'to' and pass it agaun with recursion to same function
            for (Rate rate : rates) {
                if (rate.from.equalsIgnoreCase(amountCurrency)) {
                    return getAmountInGBP(amount * rate.rate, rate.to);
                }
            }
        }
        return -1; // -1 for not converted rate
    }

    public double getTotalAmount(ArrayList<Transaction> transactionsList) {
        double total = 0;
        if(transactionsList != null) {
            for(Transaction transaction : transactionsList) {
                total += getAmountInGBP(transaction.amount, transaction.currency);
            }
        }
        return total;
    }
    /*
     * Setters & Getters
     */
    public ArrayList<Transaction> getTransactions(String sku) {
        return transactionsMap.get(sku);
    }
    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }
    public Map<String, ArrayList<Transaction>> getTransactionsMap() {
        return transactionsMap;
    }
    public ArrayList<Rate> getRates() {
        return rates;
    }
}
