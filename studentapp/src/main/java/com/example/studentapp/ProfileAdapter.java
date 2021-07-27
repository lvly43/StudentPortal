package com.example.studentapp;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileViewAdapter> {

    @Override
    public ProfileViewAdapter onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull  ProfileAdapter.ProfileViewAdapter holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ProfileViewAdapter extends RecyclerView.ViewHolder{

        public ProfileViewAdapter(@NonNull  View itemView) {
            super(itemView);
        }
    }

}
