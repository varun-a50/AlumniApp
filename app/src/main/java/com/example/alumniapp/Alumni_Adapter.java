package com.example.alumniapp;

import android.content.Context;
import android.content.Intent;
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

public class Alumni_Adapter extends FirebaseRecyclerAdapter<Alumni,Alumni_Adapter.ViewHolder> {
    Context context;


    public Alumni_Adapter(@NonNull FirebaseRecyclerOptions<Alumni> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Alumni model) {
        FirebaseAuth mAuth;
        FirebaseUser user;
        String studentID;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser FirbaseUser = mAuth.getCurrentUser();

        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        String node = model.getRandomKey();
        final  DatabaseReference databaseReference = mDatabase.getReference("Alumnis").child(node).getRef();
        final String myKey = databaseReference.getKey();



        holder.first.setText(model.getFirst_name());
        holder.last.setText(model.getLast_name());
        holder.Mobile.setText(model.getMobile_no());
        holder.Email.setText(model.getEmail_ID());
        holder.Company.setText(model.getCompany_name());
        holder.something.setText(model.getSomething());
        holder.hidden.setText(model.getMid_name());
        holder.year.setText(model.getYear());
        holder.exp.setText(model.getWork_experience());


        Glide.with(holder.image.getContext()).load(model.getProfileUrl()).apply(new RequestOptions().error(R.drawable.alumni).fitCenter()).into(holder.image);

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,Individual_profile_card_alumni.class);
                intent.putExtra("name",model.getFirst_name());
                intent.putExtra("mid_name",model.getMid_name());
                intent.putExtra("last_name",model.getLast_name());
                intent.putExtra("year",model.getYear());
                intent.putExtra("email",model.getEmail_ID());
                intent.putExtra("mobile",model.getMobile_no());
                intent.putExtra("company",model.getCompany_name());
                intent.putExtra("something",model.getSomething());
                intent.putExtra("experience",model.getWork_experience());
                intent.putExtra("imageUrl",model.getProfileUrl());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_alumnis,parent,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView first, last, Mobile, Email,Company,something,hidden,year,exp;
        private FirebaseAuth mAuth;

        private FirebaseUser user;
        private String studentID;
        private DatabaseReference databaseReference;
        FirebaseDatabase firebaseDatabase;

        Button delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            first = itemView.findViewById(R.id.first);
            last = itemView.findViewById(R.id.last);
            Mobile = itemView.findViewById(R.id.mobile);
            Email = itemView.findViewById(R.id.email);
            Company = itemView.findViewById(R.id.company);
            something = itemView.findViewById(R.id.something);

            image = itemView.findViewById(R.id.profile_image);
            delete = itemView.findViewById(R.id.delete);
            hidden = itemView.findViewById(R.id.hidden);
            year = itemView.findViewById(R.id.year);
            exp = itemView.findViewById(R.id.exp);
        }
    }
}
