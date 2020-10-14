package com.example.testtask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class transactions extends AppCompatActivity {

    private static ArrayList<String> transactions;
    private ListView listView;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

        transactions = new ArrayList<String>();
        loadData();
        listView = (ListView)findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(this,R.layout.accounts, transactions);
        listView.setAdapter(adapter);
    }
    private void loadData(){
        ContentValues contentValues = new ContentValues();

        Cursor c = MainActivity.database.query(DatabaseHelper.TABLE_TRANSACTIONS, null, null, null, null, null, null);
        if (c.moveToFirst()) {
            int TRANSACTION_INFO = c.getColumnIndex("TRANSACTION_INFO");
            do {
                transactions.add(c.getString(TRANSACTION_INFO));
            } while (c.moveToNext());
        }
        c.close();
    }

    //переход между окнами
    public void onButtonClickAccounts(View v){
        Intent startIntent = new Intent(this, MainActivity.class);
        startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startIntent);
    }
    public void onButtonClickSettings(View v){
        Intent intent = new Intent(".Settings");
        startActivity(intent);
    }

    public void onButtonClickClear(View v){
        MainActivity.database.execSQL("DELETE FROM "+ DatabaseHelper.TABLE_TRANSACTIONS);

        transactions = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this,R.layout.accounts, transactions);
        listView.setAdapter(adapter);
    }
}