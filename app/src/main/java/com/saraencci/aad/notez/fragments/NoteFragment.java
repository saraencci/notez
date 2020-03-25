package com.saraencci.aad.notez.fragments;


import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
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

import static androidx.constraintlayout.widget.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoteFragment extends Fragment implements RecyclerViewAdapter.OnNoteItemClick {
    private RecyclerViewAdapter adapter;
    private List<Note> noteList;


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

    @Override
    public void onResume() {
        super.onResume();
        getNotes();
    }

    private void getNotes() {
        class GetNotes extends AsyncTask<Void, Void, List<Note>> {

            @Override
            protected List<Note> doInBackground(Void... voids) {
                noteList = DatabaseClient
                        .getInstance(getActivity().getApplicationContext())
                        .getAppDatabase()
                        .noteDao()
                        .getAll();
                return noteList;
            }

            @Override
            protected void onPostExecute(List<Note> notes) {
                super.onPostExecute(notes);
                adapter=new RecyclerViewAdapter(getContext(),notes,NoteFragment.this::onNoteClick);
                myBinding.noteRecyclerView.setAdapter(adapter);
            }
        }
        GetNotes gn = new GetNotes();
        gn.execute();
    }

    @Override
    public void onNoteClick(int pos,String action) {
        Log.d(TAG, "onNoteClick: interface"+pos);
        switch (action){
            case "view":
                //switch to the single note fragment
                SingleNoteFragment frag=new SingleNoteFragment();
                //set up the bundle to pass data
                Bundle bundle = new Bundle();
                bundle.putString("tittle", noteList.get(pos).getTittle());
                bundle.putString("content", noteList.get(pos).getContent());
                bundle.putString("color", noteList.get(pos).getBgColor());
                bundle.putString("time", noteList.get(pos).getTime());
                bundle.putInt("id", noteList.get(pos).getId());
                bundle.putString("created", noteList.get(pos).getCreated());
                frag.setArguments(bundle);
                FragmentTransaction transaction= getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment,frag);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case "delete":
                // delete the note
                deleteNote(noteList.get(pos));
                break;
        }
    }

    public void  deleteNote(Note note){
        class DeleteNote  extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(getActivity().getApplicationContext()).getAppDatabase()
                        .noteDao()
                        .delete(note);

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                noteList.clear();
                Log.d(TAG, "doInBackground: elete note");
                getNotes();
            }
        }

        DeleteNote dn=new DeleteNote();
        dn.execute();
    }
}
