package com.badoo.test.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.badoo.test.R;
import com.badoo.test.controller.TransactionsManager;
import com.badoo.test.model.Transaction;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class ProductTransactionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<Transaction> transactions;

    public ProductTransactionsAdapter(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_transactions_item, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder _holder, final int position) {
        ViewHolder holder = (ViewHolder) _holder;
        holder.tvProductSKU.setText(transactions.get(position).currency+" "+transactions.get(position).amount+"");
        holder.tvProductDesc.setText(String.format("GBP %.2f", TransactionsManager.getInstance().getAmountInGBP(transactions.get(position).amount, transactions.get(position).currency)) );
    }

    @Override
    public int getItemCount() {
        return (transactions != null) ? transactions.size() : 0;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvProductSKU, tvProductDesc;
        public View view;
        public ViewHolder(View view) {
            super(view);
            tvProductSKU = (TextView) view.findViewById(R.id.tvProductTransSKU);
            tvProductDesc = (TextView) view.findViewById(R.id.tvProductTransDesc);
            this.view = view;
        }
    }
}
