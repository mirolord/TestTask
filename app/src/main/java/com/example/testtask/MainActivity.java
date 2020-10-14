package com.example.testtask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private Button button;
    private ListView listView;
    private ArrayAdapter<Account> adapter;
    private ArrayList<String> names = new ArrayList<String>();
    private static ArrayList<Account> accounts;
    public static Account CurrentAccount;
    public static SQLiteDatabase database;
    private static DatabaseHelper mDatabaseHelper;

    @Override
    public void onResume() {
        super.onResume();

        adapter.notifyDataSetChanged();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.buttonAddAccount);
        listView = (ListView)findViewById(R.id.listView);
        mDatabaseHelper = new DatabaseHelper(this);
        database =  mDatabaseHelper.getWritableDatabase();
        accounts = new ArrayList<Account>();
        adapter = new ArrayAdapter<Account>(this,R.layout.support_simple_spinner_dropdown_item,accounts);
        listView.setAdapter(adapter);

        listener1();
        listener2();
        loadData();

        //database.execSQL("DROP TABLE IF EXISTS " + DatabaseHelper.TABLE_TRANSACTIONS);
        //deleteDatabase(DatabaseHelper.TABLE_TRANSACTIONS);

    }

    //SQL
    private void loadData(){
        ContentValues contentValues = new ContentValues();
        Cursor c = database.query(DatabaseHelper.TABLE_ACCOUNTS, null, null, null, null, null, null);
        if (c.moveToFirst()) {
            int idColIndex = c.getColumnIndex("ID");
            int nameColIndex = c.getColumnIndex("NAME");
            int amountColIndex = c.getColumnIndex("AMOUNT");

            do {
                accounts.add(new Account(c.getString(nameColIndex), c.getDouble(amountColIndex), c.getLong(idColIndex)));
            } while (c.moveToNext());
        }
        c.close();
    }

    //переход между окнами
    public void onButtonClickTransactions(View v){
        Intent intent = new Intent(".transactions");
        startActivity(intent);
    }
    public void onButtonClickSettings(View v){
        Intent intent = new Intent(".Settings");
        startActivity(intent);
    }

    //добавление счёта
    public void listener2(){

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = editText.getText().toString();
                //SQL
                ContentValues contentValues = new ContentValues();
                contentValues.put(DatabaseHelper.COL2, result);
                Long id = MainActivity.database.insert(DatabaseHelper.TABLE_ACCOUNTS,null,contentValues);

                accounts.add(new Account(result, id));
                adapter.notifyDataSetChanged();
            }
        });
    }
    //выбор счёта
    public void listener1(){
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(".activity_account");
                        Account account = (Account)listView.getItemAtPosition(position);
                        CurrentAccount = account;
                        startActivity(intent);
                    }
                }
        );
    }
    //удаление счёта
    public static void deleteAccount(Account account){
        accounts.remove(account);
    }
}