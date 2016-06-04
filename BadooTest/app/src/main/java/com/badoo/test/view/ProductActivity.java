package com.badoo.test.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.badoo.test.R;
import com.badoo.test.controller.TransactionsManager;
import com.badoo.test.model.Transaction;
import com.badoo.test.view.adapter.ProductTransactionsAdapter;
import com.badoo.test.view.adapter.ProductsAdapter;
import com.badoo.test.view.adapter.RecyclerViewEvents;

import java.util.ArrayList;

// Product details activity
// Second screen opened, to show list of transactions
public class ProductActivity extends AppCompatActivity{

    public static final String BUNDLE_PRODUCT_ID = "ProductSKU";

    String sku;
    ArrayList<Transaction> transactions;

    RecyclerView recyclerView;
    ProductTransactionsAdapter adapter;
    TextView tvProductTransactionsTotal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_trans);

        recyclerView = (RecyclerView) findViewById(R.id.product_recycler_view);
        tvProductTransactionsTotal = (TextView) findViewById(R.id.tvProductTransactionsTotal);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null)
            sku = bundle.getString(BUNDLE_PRODUCT_ID);
        else
            return;

        transactions = TransactionsManager.getInstance().getTransactions(sku);
        tvProductTransactionsTotal.setText(sku + "\r-\r" + getString(R.string.total)+" "+String.format("%.2f", TransactionsManager.getInstance().getTotalAmount(transactions)));

        adapter = new ProductTransactionsAdapter(transactions);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
    }


}
