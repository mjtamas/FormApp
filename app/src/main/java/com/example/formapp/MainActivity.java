package com.example.formapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText edt_name,edt_mail;
    Button btn_submit;
    RecyclerView recyclerView;

    //Firebase
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    FirebaseRecyclerOptions<Submit> options;
    FirebaseRecyclerAdapter<Submit,MyRecyclerViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edt_name = (EditText)findViewById(R.id.edt_name);
        edt_mail = (EditText)findViewById(R.id.edt_mail);
        btn_submit = (Button)findViewById(R.id.btn_submit);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("FORMAPP_FIREBASE");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                submitForm();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });

    }
    @Override
    protected void onStop(){
        if (adapter != null)
            adapter.stopListening();
        super.onStop();
    }

    private void submitForm(){
        String name =edt_name.getText().toString();
        String mail =edt_mail.getText().toString();

        Submit submit = new Submit(name,mail);

        databaseReference.push() //egyedi id letrehozasahoz
            .setValue(submit);

        adapter.notifyDataSetChanged();


    }

    private void displayMail() {
        options =
                new FirebaseRecyclerOptions.Builder<Submit>()
                .setQuery(databaseReference,Submit.class)
                .build();

        adapter =
                new FirebaseRecyclerAdapter<Submit, MyRecyclerViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull MyRecyclerViewHolder holder, int position, @NonNull Submit model) {
                        holder.txt_name.setText(model.getName());
                        holder.txt_mail.setText(model.getMail());

                    }

                    @NonNull
                    @Override
                    public MyRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View itemView = LayoutInflater.from(getBaseContext()).inflate(R.layout.submit_item,parent,false);
                        return new MyRecyclerViewHolder(itemView);
                    }
                };
         adapter.startListening();
         recyclerView.setAdapter(adapter);


    }
}
