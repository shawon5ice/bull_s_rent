package com.ssquare.bullsapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.ssquare.bullsapp.HomePage;
import com.ssquare.bullsapp.R;
import com.ssquare.bullsapp.models.NoteModel;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private List<NoteModel> todoList;
    private HomePage activity;

    public NoteAdapter(HomePage activity){
        this.activity = activity;
    }

    public void setTask(List<NoteModel> todoList){
        this.todoList = todoList;
        //notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_list_item,parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NoteModel item = todoList.get(position);
        holder.title.setText(item.getTitle());
        holder.desc.setText(item.getDescription());
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView desc;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.list_item_title);
            desc = itemView.findViewById(R.id.list_item_description);
        }
    }
}
