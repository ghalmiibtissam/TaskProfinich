package com.example.taskprofinich;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<Post> postList;
    private Context context;

    public PostAdapter(List<Post> postList, Context context) {
        this.postList = postList;
        this.context = context;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = postList.get(position);
        holder.textView.setText(post.getText());
        Glide.with(context).load(post.getImageUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        if (postList == null) {
            return 0;
        }
        return postList.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;
        public ImageView imageView;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.post_text);
            imageView = itemView.findViewById(R.id.post_image);
        }
    }
}
