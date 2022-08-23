package com.example.csadmin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class Event2_Adapter extends FirebaseRecyclerAdapter<Event,Event2_Adapter.ViewHolder> {
    Context context;

    public Event2_Adapter(@NonNull FirebaseRecyclerOptions<Event> options2,Context context) {
        super(options2);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Event model) {
        holder.date1.setText(model.getDate());
        holder.time1.setText(model.getTime());
        holder.title1.setText(model.getTitle());


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_trick,parent,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView date1, time1, title1;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            date1 = itemView.findViewById(R.id.date);
            time1 = itemView.findViewById(R.id.time);
            title1= itemView.findViewById(R.id.title);

        }
    }
}
