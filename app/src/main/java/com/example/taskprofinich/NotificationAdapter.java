package com.example.taskprofinich;

        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;
        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;
        import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Commande commande);
    }

    private List<Commande> commandeList;
    private Context context;
    private OnItemClickListener listener;

    public NotificationAdapter(List<Commande> commandeList, Context context, OnItemClickListener listener) {
        this.commandeList = commandeList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notification_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Commande commande = commandeList.get(position);
        holder.nomClientTextView.setText(commande.getPublication());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(commande);
            }
        });
    }

    @Override
    public int getItemCount() {
        return commandeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nomClientTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            nomClientTextView = itemView.findViewById(R.id.client_name);
        }
    }
}

