package com.example.formapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyRecyclerViewHolder extends RecyclerView.ViewHolder {
    TextView txt_name,txt_mail;
    public MyRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);

        txt_name = (TextView)itemView.findViewById(R.id.txt_name);
        txt_mail = (TextView)itemView.findViewById(R.id.txt_mail);

    }
}
