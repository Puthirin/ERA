package com.example.puthirin.era;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.puthirin.era.Url.GetBook;
import com.example.puthirin.era.Url.StringUrl;

public class BookDetail extends AppCompatActivity {
    StringUrl stringUrl = new StringUrl();
    GetBook getBook = new GetBook();
    TextView Title, Author, Description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
//        Title = (TextView)findViewById(R.id.t)
    }
}
