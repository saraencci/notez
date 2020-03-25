package com.saraencci.aad.notez.fragments;


import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.saraencci.aad.notez.R;
import com.saraencci.aad.notez.data.DatabaseClient;
import com.saraencci.aad.notez.data.Note;
import com.saraencci.aad.notez.databinding.FragmentSingleNoteBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class SingleNoteFragment extends Fragment {


    public SingleNoteFragment() {
        // Required empty public constructor
    }
   FragmentSingleNoteBinding binding;
    int transactionCode=1,noteId;
    private String bacgroundColor="#A5B6E4";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
binding=FragmentSingleNoteBinding.inflate(inflater);


        Bundle bundle = this.getArguments();
        //checking if bundle was sent
        if (bundle != null) {
         //   setting transaction for updating
            transactionCode=2;
            //retreaving sent note data
            bacgroundColor=bundle.getString("color","#A5B6E4");
            binding.homeNote.setBackgroundColor(Color.parseColor(bacgroundColor));
            binding.textInputEditTextNoteTittle.setText(bundle.getString("tittle",""));
            binding.textInputEditTextNote.setText(bundle.getString("content",""));
            noteId=bundle.getInt("id",23);

        }

binding.buttonSave.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(transactionCode==1){
            //save new note
            saveNote();
        }
        else if(transactionCode==2){
            //update existing note
            updateNote();
        }

    }
});

return binding.getRoot();
    }

    private void updateNote() {
        String noteTittle=binding.textInputEditTextNoteTittle.getText().toString().trim();
        String noteContent=binding.textInputEditTextNote.getText().toString().trim();
        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String time=new SimpleDateFormat("H:m", Locale.getDefault()).format(new Date());
        //creating a new note
        Note note = new Note();
        note.setContent(noteContent);
        note.setTittle(noteTittle);
        note.setTime(time);
        note.setId(noteId);
        note.setBgColor(bacgroundColor);
        note.setCreated(date);
        class  UpdateNote extends AsyncTask<Void,Void,Void>{

            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(getContext()).getAppDatabase()
                        .noteDao().update(note);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getActivity().getApplicationContext(), "note updated successfully", Toast.LENGTH_SHORT).show();
            }
        }
        UpdateNote un=new UpdateNote();
        un.execute();
    }

    public void saveNote() {
        String noteTittle=binding.textInputEditTextNoteTittle.getText().toString().trim();
        String noteContent=binding.textInputEditTextNote.getText().toString().trim();
        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String time=new SimpleDateFormat("H:m", Locale.getDefault()).format(new Date());
        //creating the note object
        Note note = new Note();
        note.setContent(noteContent);
        note.setTittle(noteTittle);
        note.setTime(time);
        note.setBgColor(bacgroundColor);
        note.setCreated(date);

        class Savenote extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                //adding to database
                DatabaseClient.getInstance(getContext()).getAppDatabase()
                        .noteDao()
                        .insert(note);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getContext(), "Note Saved", Toast.LENGTH_LONG).show();
            }
        }

        Savenote st = new Savenote();
        st.execute();

    }
}
