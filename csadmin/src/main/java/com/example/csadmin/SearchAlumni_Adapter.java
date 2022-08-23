package com.example.csadmin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class SearchAlumni_Adapter extends FirebaseRecyclerAdapter<Alumni, SearchAlumni_Adapter.ViewHolder> {
    Context context;


    public SearchAlumni_Adapter(@NonNull FirebaseRecyclerOptions<Alumni> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Alumni model) {

        holder.first.setText(model.getFirst_name());
        holder.last.setText(model.getLast_name());

        Glide.with(holder.image.getContext()).load(model.getProfileUrl()).apply(new RequestOptions().error(R.drawable.alumni).fitCenter()).into(holder.image);


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_alumni,parent,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView first, last;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            first = itemView.findViewById(R.id.first);
            last = itemView.findViewById(R.id.last);
            image = itemView.findViewById(R.id.profile_image);

        }
    }
}
