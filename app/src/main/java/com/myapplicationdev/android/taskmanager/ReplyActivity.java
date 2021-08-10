package com.myapplicationdev.android.taskmanager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.RemoteInput;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

public class ReplyActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);

        CharSequence reply = null;
        Intent intent = getIntent();
        int id = intent.getIntExtra("taskID", -1);

        Log.i("test", String.valueOf(id));

        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if (remoteInput != null) {
            reply = remoteInput.getCharSequence("status");
        }

        if (reply != null) {
            if (reply.equals("Completed") && id != -1) {
                DBHelper dbHelper = new DBHelper(ReplyActivity.this);
                dbHelper.deleteTask(id);
                dbHelper.close();

                Log.i("taskID", String.valueOf(id));

                setResult(RESULT_OK);
                finish();
            }
        }
    }
}