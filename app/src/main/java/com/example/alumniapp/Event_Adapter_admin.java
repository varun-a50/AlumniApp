package com.example.alumniapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Event_Adapter_admin extends FirebaseRecyclerAdapter<Event, Event_Adapter_admin.ViewHolder> {

    Context context;


    public Event_Adapter_admin(@NonNull FirebaseRecyclerOptions<Event> options2, Context context) {
        super(options2);
        this.context = context;
    }


    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Event model) {


        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        String node = model.getRandomKey();
        final  DatabaseReference databaseReference = mDatabase.getReference("Events").child(node).getRef();
        final String myKey = databaseReference.getKey();



        holder.first.setText(model.getFirst_Name());
        holder.last.setText(model.getLast_Name());
        holder.date.setText(model.getDate());
        holder.time.setText(model.getTime());
        holder.title.setText(model.getTitle());
        holder.details.setText(model.getDetails());
        Glide.with(holder.image.getContext()).load(model.getImageUrl()).apply(new RequestOptions().error(R.drawable.alumni).fitCenter()).into(holder.image);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(myKey != null) {
                    databaseReference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(context.getApplicationContext(), "Done", Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context.getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();

                        }
                    });
                }
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_admin,parent,false);
        return new ViewHolder(view);
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        final  DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Events").getRef();
        final String myKey = databaseReference.getKey();
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        ImageView image;
        TextView first, last, date, time, title, details;

        private FirebaseUser user;
        private String adminID;
        FirebaseUser FirbaseUser;
        FirebaseDatabase firebaseDatabase;

        Button delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            first = itemView.findViewById(R.id.first);
            last = itemView.findViewById(R.id.last);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            title = itemView.findViewById(R.id.title);
            details = itemView.findViewById(R.id.details);
            image = itemView.findViewById(R.id.profile_image);
            delete = itemView.findViewById(R.id.delete);


            user = FirebaseAuth.getInstance().getCurrentUser();




        }

    }
}
