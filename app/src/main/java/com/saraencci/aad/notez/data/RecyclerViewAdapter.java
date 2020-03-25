package com.saraencci.aad.notez.data;

import android.content.Context;
import android.os.AsyncTask;
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
    OnNoteItemClick onNoteItemClick;

    public RecyclerViewAdapter(Context context, List<Note> notes,OnNoteItemClick onNoteItemClick) {
        this.context = context;
        this.notes = notes;
        this.onNoteItemClick=onNoteItemClick;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        NoteLayoutBinding noteLayoutBinding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.note_layout,parent,false);
        myViewHolder myViewHolder=new myViewHolder(noteLayoutBinding,onNoteItemClick);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        Note note =notes.get(position);
        holder.bind(note);
    }

    @Override
    public int getItemCount() {


        return notes.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//setting up databinding
        NoteLayoutBinding noteLayoutBinding;
        OnNoteItemClick onNoteItemClick;

        public myViewHolder(@NonNull NoteLayoutBinding itemView,OnNoteItemClick onNoteItemClick) {
            super(itemView.getRoot());
            this.noteLayoutBinding=itemView;
            itemView.textViewBodyText.setOnClickListener(this);
            itemView.buttonDelete.setOnClickListener(this);
            this.onNoteItemClick=onNoteItemClick;

        }
        public void bind(Note note){
            //binding the note
            noteLayoutBinding.setNote(note);
            noteLayoutBinding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.buttonDelete:
                    //action to delete
                    onNoteItemClick.onNoteClick(getAdapterPosition(),"delete");
                    break;
                case R.id.textView_bodyText:
                    // action to vew and update
                    onNoteItemClick.onNoteClick(getAdapterPosition(),"view");
                    break;
            }

        }
    }
    public interface OnNoteItemClick {
        void onNoteClick(int pos,String action);

    }


}
