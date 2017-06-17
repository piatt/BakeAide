package com.piatt.udacity.bakeaide.view;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T, VH extends BaseViewHolder<T>> extends RecyclerView.Adapter<VH> {
    private int layoutResourceId;
    private List<T> items = new ArrayList<>();

    public BaseAdapter(int layoutResourceId) {
        this.layoutResourceId = layoutResourceId;
    }

    public BaseAdapter(@LayoutRes int layoutResourceId, List<T> items) {
        this.layoutResourceId = layoutResourceId;
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutResourceId, parent, false);
        return getViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.onBind(getItem(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    protected abstract VH getViewHolder(View view);

    protected T getItem(int position) {
        return items.get(position);
    }

    protected void setItems(List<T> items) {
        this.items = items;
        notifyDataSetChanged();
    }
}