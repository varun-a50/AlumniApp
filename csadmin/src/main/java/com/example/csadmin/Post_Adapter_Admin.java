package com.example.csadmin;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Post_Adapter_Admin extends FirebaseRecyclerAdapter<Post, Post_Adapter_Admin.ViewHolder> {
    Context context;

    public Post_Adapter_Admin(@NonNull FirebaseRecyclerOptions<Post> options, Context context) {
        super(options);
        this.context = context;
    }


    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Post model) {

        int p =holder.getAdapterPosition();
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        String node = model.getRandomkey();
        final  DatabaseReference databaseReference = mDatabase.getReference("Posts").child(node).getRef();
        final String myKey = databaseReference.getKey();



        holder.first.setText(model.getFirst_Name());
        holder.last.setText(model.getLast_Name());
        holder.Post.setText(model.getPost());


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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_admin,parent,false);

        return new ViewHolder(view);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView first, last,Post;
        FirebaseDatabase mDatabase;
        DatabaseReference databaseReference;

        Button delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mDatabase = FirebaseDatabase.getInstance();

            databaseReference = mDatabase.getReference(Post.class.getSimpleName());
            final String myKey = databaseReference.getKey();
            first = itemView.findViewById(R.id.first);
            last = itemView.findViewById(R.id.last);
            image = itemView.findViewById(R.id.profile_image);
            delete = itemView.findViewById(R.id.delete);
            Post = itemView.findViewById(R.id.post);
        }


    }
}
