package com.uae.tambolaapp;


import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.recyclerview.widget.RecyclerView;


public class NumberGridAdapter extends RecyclerView.Adapter<NumberGridAdapter.ViewHolder> {

    private ArrayList<Integer> list;
    private HashMap<Integer, Boolean> hashMap;

    NumberGridAdapter(ArrayList<Integer> list, HashMap<Integer, Boolean> hashMap) {
        this.list = list;
        this.hashMap = hashMap;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.number_grid_adapter, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.textView.setText(String.valueOf(list.get(position)));

        if (hashMap.get(position + 1)) {
            holder.textView.setBackgroundResource(R.drawable.badge_icon_red);
            holder.textView.setTextColor(Color.parseColor("#ffffff"));
        } else {
            holder.textView.setBackgroundResource(R.drawable.badge_icon);
            holder.textView.setTextColor(Color.parseColor("#696969"));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    void reFill(HashMap<Integer, Boolean> hashMap) {
        this.hashMap = hashMap;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textView;

        ViewHolder(View itemView) {
            super(itemView);

            itemView.setClickable(true);
            itemView.setOnClickListener(this);
            textView = (TextView) itemView.findViewById(R.id.textVal);

        }

        @Override
        public void onClick(View v) {

            Log.d("TAG", " onClick: " + getLayoutPosition());

        }
    }

}
