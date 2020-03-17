package com.saraencci.aad.notez.utilities;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class BindingCallbacks {
    public void addButtonClicked(View view,Context context ) {
       Toast.makeText(context, "FAB clicked!", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "addButtonClicked:hhghghhhh ");
    }
}
