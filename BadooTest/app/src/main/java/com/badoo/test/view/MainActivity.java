package com.badoo.test.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.badoo.test.R;
import com.badoo.test.controller.TransactionsManager;
import com.badoo.test.helper.Logger;
import com.badoo.test.helper.Utilities;
import com.badoo.test.view.adapter.ProductsAdapter;
import com.badoo.test.view.adapter.RecyclerViewEvents;

// Main activity
// First screen opened, to show a list of products
public class MainActivity extends AppCompatActivity implements RecyclerViewEvents{

    RecyclerView recyclerView;
    ProductsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.home_recycler_view);

        TransactionsManager.getInstance().init();

        adapter = new ProductsAdapter(TransactionsManager.getInstance().getTransactionsMap());
        adapter.setRecyclerViewEvents(this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    /* *********************************************
     * ******* RecyclerViewEvents ******************
     * *********************************************
     */
    @Override
    public void onRecyclerItemClick(Object object) {
        String sku = (String) object;
        Intent intent = new Intent(this, ProductActivity.class);
        intent.putExtra(ProductActivity.BUNDLE_PRODUCT_ID, sku);
        startActivity(intent);
    }
    @Override
    public void onRecyclerItemLongClick(Object object) {
    }
    // RecyclerViewEvents /////////////////////////
}
