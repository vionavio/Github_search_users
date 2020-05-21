package com.vio.githusearchtest.adapter;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.vio.githusearchtest.listener.OnLoadMoreListener;
import com.vio.githusearchtest.R;
import com.vio.githusearchtest.model.SearchData;

import java.util.ArrayList;


public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private OnLoadMoreListener onLoadMoreListener;

    private boolean isLoading;
    private Activity activity;
    private int visibleThreshold = 1;
    private int lastVisibleItem, totalItemCount;
    private ArrayList<SearchData> data;

    public SearchAdapter(RecyclerView recyclerView, ArrayList<SearchData> data, Activity activity) {
        this.data = data;
        this.activity = activity;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public void setData(ArrayList<SearchData> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items, parent, false);
            return new UserViewHolder(view);
        }
        else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;
    }


    @Override
    public void onBindViewHolder(@NonNull final   SearchAdapter.ViewHolder holder, int position) {

        if (holder.getItemViewType() == VIEW_TYPE_ITEM) {
            //UserViewHolder viewholder = (UserViewHolder) holder;
            SearchData item = data.get(position);
            Glide.with(holder.itemView.getContext())
                    .load(item.getAvatarUrl())
                    .apply(new RequestOptions().override(120, 120))
                    .into(holder.imageView);
            holder.nama.setText(item.getLogin());

        } else {
            LoadingViewHolder viewholder = (LoadingViewHolder) holder;
            viewholder.progressBar.setIndeterminate(true);
        }
    }


    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setLoaded() {
        isLoading = false;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nama;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.tvUsername);
            imageView = itemView.findViewById(R.id.ivPicture);
        }
    }

    // for user item list
    public class UserViewHolder extends ViewHolder {
        TextView nama;
        ImageView imageView;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.tvUsername);
            imageView = itemView.findViewById(R.id.ivPicture);
        }
    }

    // for bottom loading list
    public class LoadingViewHolder extends ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View view) {
            super(view);
            progressBar = view.findViewById(R.id.progressBar);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }



}
