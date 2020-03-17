package com.saraencci.aad.notez.fragments;


import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.saraencci.aad.notez.R;
import com.saraencci.aad.notez.data.DatabaseClient;
import com.saraencci.aad.notez.data.Note;
import com.saraencci.aad.notez.data.RecyclerViewAdapter;
import com.saraencci.aad.notez.databinding.FragmentNoteBinding;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoteFragment extends Fragment {
    private RecyclerViewAdapter adapter;


    public NoteFragment() {
        // Required empty public constructor
    }
    FragmentNoteBinding myBinding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // bind the layout for this fragment
        myBinding =FragmentNoteBinding.inflate(inflater);
        myBinding.noteRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
      // myBinding.noteRecyclerView.hasFixedSize(true);
        getNotes();
return  myBinding.getRoot();
       // return inflater.inflate(R.layout.fragment_note, container, false);
    }
    private void getNotes() {
        class GetNotes extends AsyncTask<Void, Void, List<Note>> {

            @Override
            protected List<Note> doInBackground(Void... voids) {
                List<Note> noteList = DatabaseClient
                        .getInstance(getActivity().getApplicationContext())
                        .getAppDatabase()
                        .noteDao()
                        .getAll();
                return noteList;
            }

            @Override
            protected void onPostExecute(List<Note> notes) {
                super.onPostExecute(notes);
                adapter=new RecyclerViewAdapter(getContext(),notes);
                myBinding.noteRecyclerView.setAdapter(adapter);
            }
        }
        GetNotes gn = new GetNotes();
        gn.execute();
    }

    public class MyClickHandlers {

        public void onFabClicked(View view) {
            Toast.makeText(getContext(), "FAB clicked!", Toast.LENGTH_SHORT).show();
        }
    }
}
