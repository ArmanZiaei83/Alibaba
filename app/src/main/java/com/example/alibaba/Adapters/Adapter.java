package com.example.alibaba.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alibaba.DataBase.RepoEntity;
import com.example.alibaba.R;
import com.example.alibaba.SetOnClick;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    List<RepoEntity> entities = new ArrayList<>();
    private SetOnClick setOnClick;

    public List<RepoEntity> getEntities() {
        return entities;
    }

    public void setEntities(List<RepoEntity> entities) {
        this.entities = entities;
    }

    public Adapter(SetOnClick setOnClick) {
        this.setOnClick = setOnClick;
    }

    @NonNull
    @NotNull
    @Override
    public Adapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recview , parent , false);
        MyViewHolder viewHolder = new MyViewHolder(view , setOnClick);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull @NotNull Adapter.MyViewHolder holder, int position) {
        RepoEntity entity = entities.get(position);
        holder.repoName.setText("Repo Name : " + entity.getUserOwner());

    }

    @Override
    public int getItemCount() {
        return entities.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView repoName;
        ImageView repoImage;
        SetOnClick setOnClick;

        public MyViewHolder(@NonNull @NotNull View itemView , SetOnClick setOnClick) {
            super(itemView);
            repoName = itemView.findViewById(R.id.repo_name);
            repoImage = itemView.findViewById(R.id.repo_image);
            this.setOnClick = setOnClick;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            setOnClick.onClick(getAdapterPosition());
        }
    }
}

