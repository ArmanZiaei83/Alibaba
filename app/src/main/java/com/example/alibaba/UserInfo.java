package com.example.alibaba;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class UserInfo extends AppCompatActivity {

    TextView star , fork , desci , collaborators , urls , ownerName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        intiTextViews();

        Bundle bundle = getIntent().getExtras();

        int stars = bundle.getInt("stars");
        int forks = bundle.getInt("forks");
        String decs = bundle.getString("desc");
        String coll = bundle.getString("coll");
        String url = bundle.getString("url");
        String userOwner = bundle.getString("userOwner");


        setTextViewMessage(star , String.valueOf(stars));
        setTextViewMessage(fork , String.valueOf(forks));
        setTextViewMessage(desci , decs);
        setTextViewMessage(collaborators , coll);
        setTextViewMessage(urls , url);
        setTextViewMessage(ownerName , userOwner);
    }

    public void setTextViewMessage (TextView textView , String message){
        textView.append(" " + message);
    }

    public void intiTextViews(){

        star = findViewById(R.id.stars);
        fork = findViewById(R.id.forks);
        desci = findViewById(R.id.description);
        collaborators = findViewById(R.id.collaborators);
        urls = findViewById(R.id.httpUrl);
        ownerName = findViewById(R.id.ownerName);

    }
}