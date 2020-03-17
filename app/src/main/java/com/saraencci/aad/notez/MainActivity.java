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

import java.util.List;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //adding data binding
        ActivityMainBinding myBinding;
        myBinding= DataBindingUtil.setContentView(this,R.layout.activity_main);
       // setContentView(R.layout.activity_main);

        myBinding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newnoteFragment();
            }
        });

        init();

        sp = getSharedPreferences("myPref",MODE_PRIVATE);
        if(sp.getString("run","null").equals("null")){
            firstRun();
        }
        else
        getNotes();
    }
    private void firstRun(){
        sp.edit().putString("run","did a run");
        saveNote();
        getNotes();

    }

public void run(View view){
        saveNote();
}
void init(){
    NoteFragment frag=new NoteFragment();
    FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
    transaction.replace(R.id.fragment,frag);

}




    private void saveNote() {
//        final String sTask = editTextTask.getText().toString().trim();
//        final String sDesc = editTextDesc.getText().toString().trim();
//        final String sFinishBy = editTextFinishBy.getText().toString().trim();
//
//        if (sTask.isEmpty()) {
//            editTextTask.setError("Task required");
//            editTextTask.requestFocus();
//            return;
//        }
//
//        if (sDesc.isEmpty()) {
//            editTextDesc.setError("Desc required");
//            editTextDesc.requestFocus();
//            return;
//        }
//
//        if (sFinishBy.isEmpty()) {
//            editTextFinishBy.setError("Finish by required");
//            editTextFinishBy.requestFocus();
//            return;
//        }

        class Savenote extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                int no=5;
                for(int i=2;i<no;i++){
                    String data=" my data ma data hshhdhd fhfhfhhf fhffhfhhf  the data ";
                    String data2="";

                    for (int k=0;k<i;k++){
                        data2+=data;

                    }
                    String [] colors={"#462fd4","#85dfa5","#dd0f68","#35a21c"};
                    int m=i%4;
                    //creating a note
                    Note note = new Note();
                    note.setContent(data2);
                    note.setTittle("   "+ i+" test tittle");
                    note.setTime("00:04");
                    note.setBgColor(colors[m]);
                    note.setCreated("today JJ");

                    //adding to database
                    DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                            .noteDao()
                            .insert(note);


                }


                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
               // finish();
              //  startActivity(new Intent(getApplicationContext(), MainActivity.class));
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
            }
        }

        Savenote st = new Savenote();
        st.execute();
        getNotes();

    }





    private void getNotes() {
        class GetNotes extends AsyncTask<Void, Void, List<Note>> {

            @Override
            protected List<Note> doInBackground(Void... voids) {
                List<Note> noteList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .noteDao()
                        .getAll();
                return noteList;
            }

            @Override
            protected void onPostExecute(List<Note> notes) {
                super.onPostExecute(notes);
//                TasksAdapter adapter = new TasksAdapter(MainActivity.this, tasks);
//                recyclerView.setAdapter(adapter);
//                TextView tv=findViewById(R.id.textView);
//                tv.setText(notes.size()+"thos is the new size\n\n\n"+notes.get(notes.size()-1).getContent());
            }
        }

        GetNotes gn = new GetNotes();
        gn.execute();
    }



    public class BindingCallbacks {
        Context context;

        public BindingCallbacks(Context context) {
            this.context = context;
        }

        public void addButtonClicked(View view ) {
           // Toast.makeText(this, "FAB clicked!", Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "fab clicked", Toast.LENGTH_SHORT).show();
            Log.d("Daattat", "addButtonClicked: ");
        }

    }

    public  void newnoteFragment(){
        Fragment fragment=new SingleNoteFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction=  fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment, fragment).addToBackStack(null);
        Log.d("Daattat", "addButtonClicked: ");
        transaction.commit();


    }

    public class MyClickHandlers {

        public void onFabClicked(View view) {
            Toast.makeText(getApplicationContext(), "FAB clicked!", Toast.LENGTH_SHORT).show();
        }
    }




}
