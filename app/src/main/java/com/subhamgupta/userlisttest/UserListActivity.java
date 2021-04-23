package com.subhamgupta.userlisttest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;

import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class UserListActivity extends AppCompatActivity {
    TextView tvtitle, tvarticle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_list);
        tvtitle = findViewById(R.id.tvtitles);
        tvarticle = findViewById(R.id.tvarticles);
        tvarticle.setMovementMethod(new ScrollingMovementMethod());
        tvarticle.setTextIsSelectable(true);
        registerForContextMenu(tvarticle);


        String title = getIntent().getStringExtra("title");
        String article = getIntent().getStringExtra("article");
        Log.e("title",title);
        Log.e("article",article);
        tvtitle.setText(title);
        tvarticle.setText(article);





    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {

        menu.add(0, v.getId(),0, "Copy All");

        //menu.setHeaderTitle("Copy text"); //setting header title for menu
        TextView textView = (TextView) v; //calling our textView
        ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("text", textView.getText());
        manager.setPrimaryClip(clipData);
    }


}