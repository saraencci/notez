package com.saraencci.aad.notez.data;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saraencci.aad.notez.R;
import com.saraencci.aad.notez.databinding.NoteLayoutBinding;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.saraencci.aad.notez.R.layout.note_layout;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.myViewHolder> {
    private Context context;
    private List<Note> notes=new ArrayList<>();

    public RecyclerViewAdapter(Context context, List<Note> notes) {
        this.context = context;
        this.notes = notes;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        NoteLayoutBinding noteLayoutBinding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.note_layout,parent,false);
        myViewHolder myViewHolder=new myViewHolder(noteLayoutBinding);


        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        Note note =notes.get(position);
        holder.bind(note);
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: "+notes.size());
        if (notes.size()==0){
            generate();

        }

        return notes.size();
    }

    private void generate() {

        for (int i=0;i<10;i++){
            Note nove=new Note();
            nove.setTittle("title"+i);
            nove.setContent("content"+i);
            notes.add(nove);

        }

    }

    public static class myViewHolder extends RecyclerView.ViewHolder {

        NoteLayoutBinding noteLayoutBinding;

        public myViewHolder(@NonNull NoteLayoutBinding itemView) {
            super(itemView.getRoot());
            this.noteLayoutBinding=itemView;
        }
        public void bind(Note note){
            noteLayoutBinding.setNote(note);
            noteLayoutBinding.executePendingBindings();
        }
    }
}
