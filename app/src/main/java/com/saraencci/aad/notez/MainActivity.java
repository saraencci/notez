package com.saraencci.aad.notez;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.saraencci.aad.notez.data.DatabaseClient;
import com.saraencci.aad.notez.data.Note;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getNotes();
    }

public void run(View view){
        saveNote();
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

                //creating a task
                Note note = new Note();
                note.setContent("tis is test content rtrtrt");
                note.setTittle("test tittle");
                note.setTime("00:04");
                note.setBgColor("red");
                note.setCreated("today JJ");
//                task.setTask(sTask);
//                task.setDesc(sDesc);
//                task.setFinishBy(sFinishBy);
//                task.setFinished(false);

                //adding to database
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .noteDao()
                        .insert(note);
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
                TextView tv=findViewById(R.id.textView);
                tv.setText(notes.size()+"thos is the new size\n\n\n"+notes.get(notes.size()-1).getContent());
            }
        }

        GetNotes gn = new GetNotes();
        gn.execute();
    }

}
