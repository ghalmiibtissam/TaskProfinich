package com.example.taskprofinich;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class WorkerAdapter extends RecyclerView.Adapter<WorkerAdapter.WorkerViewHolder> {

    private List<Worker> workerList;
    private OnWorkerClickListener onWorkerClickListener;

    public interface OnWorkerClickListener {
        void onWorkerClick(Worker worker);
    }

    public WorkerAdapter(List<Worker> workerList, OnWorkerClickListener onWorkerClickListener) {
        this.workerList = workerList;
        this.onWorkerClickListener = onWorkerClickListener;
    }

    @NonNull
    @Override
    public WorkerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.worker_items, parent, false);
        return new WorkerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkerViewHolder holder, int position) {
        Worker worker = workerList.get(position);
        holder.nameTextView.setText(worker.getName());
        holder.locationTextView.setText(worker.getLocation());

        Glide.with(holder.profileImageView.getContext())
                .load(worker.getProfilePic())
                .into(holder.profileImageView);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onWorkerClickListener.onWorkerClick(worker);
            }
        });
    }

    @Override
    public int getItemCount() {
        return workerList.size();
    }

    public static class WorkerViewHolder extends RecyclerView.ViewHolder {

        public ImageView profileImageView;
        public TextView nameTextView;
        public TextView locationTextView;

        public WorkerViewHolder(View itemView) {
            super(itemView);
            profileImageView = itemView.findViewById(R.id.profileImageView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            locationTextView = itemView.findViewById(R.id.locationTextView);
        }
    }
}
