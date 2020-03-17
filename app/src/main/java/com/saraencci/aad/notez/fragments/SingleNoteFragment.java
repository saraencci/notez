package com.saraencci.aad.notez.fragments;


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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_single_note, container, false);
binding=FragmentSingleNoteBinding.inflate(inflater);
binding.buttonSave.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        saveNote();
    }
});
return binding.getRoot();
    }

    public void runn(View view){
        saveNote();
    }

//    public void saveNote2() {
////        final String sTask = editTextTask.getText().toString().trim();
////        final String sDesc = editTextDesc.getText().toString().trim();
////        final String sFinishBy = editTextFinishBy.getText().toString().trim();
////
////        if (sTask.isEmpty()) {
////            editTextTask.setError("Task required");
////            editTextTask.requestFocus();
////            return;
////        }
////
////        if (sDesc.isEmpty()) {
////            editTextDesc.setError("Desc required");
////            editTextDesc.requestFocus();
////            return;
////        }
////
////        if (sFinishBy.isEmpty()) {
////            editTextFinishBy.setError("Finish by required");
////            editTextFinishBy.requestFocus();
////            return;
////        }
//
//        class Savenote extends AsyncTask<Void, Void, Void> {
//
//            @Override
//            protected Void doInBackground(Void... voids) {
//
//                //creating a note
//                Note note = new Note();
//                note.setContent("tis is test content rtrtrt laooeoeofh  hfhfhfhf fhfhfhhfhfhf fhfhfhhfhfh fhfhhfhfhfhhfhfhhfhfhfhhfhfhfh fhfhffhhfhfhf fhfhfhfhhfh fhfhfhfhhhhf fhfhfhfhhfhhfhfh fhfhfhhfhfhfhhfhfh hfhfhfhhf fhhfhfhhfhdhhdhdhdh");
//                note.setTittle("test tittle");
//                note.setTime("00:04");
//                note.setBgColor("red");
//                note.setCreated("today JJ");
//
//                //adding to database
//                DatabaseClient.getInstance(getContext()).getAppDatabase()
//                        .noteDao()
//                        .insert(note);
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(Void aVoid) {
//                super.onPostExecute(aVoid);
//                // finish();
//                //  startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                Toast.makeText(getContext(), "Saved", Toast.LENGTH_LONG).show();
//            }
//        }
//
//        Savenote st = new Savenote();
//        st.execute();
//
//    }




    public void saveNote() {
        String noteTittle=binding.textInputEditTextNoteTittle.getText().toString().trim();
        String noteContent=binding.textInputEditTextNote.getText().toString().trim();
        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
       // String time= Calendar.getInstance().getTime().toString();
        String time=new SimpleDateFormat("H:m", Locale.getDefault()).format(new Date());

        class Savenote extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {

                //creating a note
                Note note = new Note();


                note.setContent(noteContent);
                note.setTittle(noteTittle);
                note.setTime(time);
                note.setBgColor("#3A3333");
                note.setCreated(date);

                //adding to database
                DatabaseClient.getInstance(getContext()).getAppDatabase()
                        .noteDao()
                        .insert(note);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                // finish();
                //  startActivity(new Intent(getApplicationContext(), MainActivity.class));
                Toast.makeText(getContext(), "Saved", Toast.LENGTH_LONG).show();
            }
        }

        Savenote st = new Savenote();
        st.execute();

    }









}
