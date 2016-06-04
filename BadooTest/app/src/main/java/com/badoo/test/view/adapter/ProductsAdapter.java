package com.badoo.test.view.adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.badoo.test.R;
import com.badoo.test.model.Transaction;
import com.badoo.test.view.BaseApplication;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class ProductsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Map<String, ArrayList<Transaction>> transactionsMap;
    ArrayList<AdapterModel> adapterList; // To wrap the values of transactions Map, list to hold the titke & desc to be displayed.
    RecyclerViewEvents recyclerViewEvents;

    public ProductsAdapter(Map<String, ArrayList<Transaction>> transactionsMap) {
        this.transactionsMap = transactionsMap;
        if(transactionsMap != null) {
            adapterList = new ArrayList<>();
            Iterator iterator = transactionsMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String,ArrayList<Transaction>> entry = (Map.Entry<String, ArrayList<Transaction>>) iterator.next();
                adapterList.add(new AdapterModel(entry.getKey(), entry.getValue().size()));
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder _holder, final int position) {
        ViewHolder holder = (ViewHolder) _holder;
        holder.tvProductSKU.setText(adapterList.get(position).sku);
        holder.tvProductDesc.setText(adapterList.get(position).transactions + " "+ BaseApplication.getContext().getString(R.string.transactions));

        holder.view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (recyclerViewEvents != null)
                    recyclerViewEvents.onRecyclerItemClick(adapterList.get(position).sku);
            }
        });

    }

    @Override
    public int getItemCount() {
        return (adapterList != null) ? adapterList.size() : 0;
    }


    public void setRecyclerViewEvents(RecyclerViewEvents recyclerViewEvents) {
        this.recyclerViewEvents = recyclerViewEvents;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvProductSKU, tvProductDesc;
        public View view;
        public ViewHolder(View view) {
            super(view);
            tvProductSKU = (TextView) view.findViewById(R.id.tvProductSKU);
            tvProductDesc = (TextView) view.findViewById(R.id.tvProductDesc);
            this.view = view;
        }
    }

    /*
     * ***********************************************
     * ************ Adapter Model ********************
     * ***********************************************
     */
    public static class AdapterModel {
        public String sku;
        public int transactions;

        public AdapterModel(String sku, int transactions) {
            this.sku = sku;
            this.transactions = transactions;
        }
    }
}
