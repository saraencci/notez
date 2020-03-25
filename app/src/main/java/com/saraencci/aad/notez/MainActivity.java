package com.saraencci.aad.notez;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.saraencci.aad.notez.data.DatabaseClient;
import com.saraencci.aad.notez.data.Note;
import com.saraencci.aad.notez.databinding.ActivityMainBinding;
import com.saraencci.aad.notez.fragments.NoteFragment;
import com.saraencci.aad.notez.fragments.SingleNoteFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //adding data binding
        ActivityMainBinding myBinding;
        myBinding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        myBinding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newnoteFragment();
            }
        });

        sp = getSharedPreferences("myPref",MODE_PRIVATE);
        //checking if its the first run
        if(!(sp.getString("run","runn")).equals("did_run")){
            firstRun();
        }
        else
        getNotes();


    }
    private void firstRun(){
        sp.edit().putString("run","did_run").commit();
        Toast.makeText(this, "did", Toast.LENGTH_SHORT).show();
        addNewDefaultNote();
    }


    private void addNewDefaultNote() {
        addNote();
    }

    void init(){
    NoteFragment frag=new NoteFragment();
    FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
    transaction.replace(R.id.fragment,frag);
    transaction.addToBackStack(null);
    transaction.commit();
}
 private void getNotes() {
        class GetNotes extends AsyncTask<Void, Void, List<Note>> {
            @Override
            protected List<Note> doInBackground(Void... voids) {
                List<Note> noteList=new ArrayList<>();
                noteList= DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .noteDao()
                        .getAll();
                return noteList;
            }

            @Override
            protected void onPostExecute(List<Note> notes) {
                super.onPostExecute(notes);
                init();
            }
        }

        GetNotes gn = new GetNotes();
        gn.execute();
    }
    public  void newnoteFragment(){
        Fragment fragment=new SingleNoteFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        // Replace fragmentCotainer with your container id
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.fragment);
        // Return if the class are the same
        if(currentFragment.getClass().equals(fragment.getClass()))
            return;
        FragmentTransaction transaction=  fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment, fragment).addToBackStack(null);
        transaction.commit();
    }

    public class MyClickHandlers {
        public void onFabClicked(View view) {
            Toast.makeText(getApplicationContext(), "FAB clicked!", Toast.LENGTH_SHORT).show();
        }
    }


    private void addNote() {

        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String time=new SimpleDateFormat("H:m", Locale.getDefault()).format(new Date());
        //creating a new note
        Note note = new Note();
        note.setContent("your notes will be here");
        note.setTittle("notes");
        note.setTime(time);
        note.setBgColor("#A5B6E4");
        note.setCreated(date);
        class  addNote extends AsyncTask<Void,Void,Void>{

            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .noteDao().insert(note);
                note.setContent("thanks for chooosin us ");
                note.setTittle("thanks");
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .noteDao().insert(note);

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "initialized", Toast.LENGTH_SHORT).show();
                getNotes();
            }
        }
        addNote an=new addNote();
        an.execute();
    }




}
