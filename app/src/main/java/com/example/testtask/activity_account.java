package com.example.testtask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.math.BigDecimal;

public class activity_account extends AppCompatActivity {

    private Account account;

    @Override
    public void onResume(){
        super.onResume();
        ((TextView) findViewById(R.id.textViewAmount)).setText(account.getRoundAnount().toString());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        account = MainActivity.CurrentAccount;
        ((TextView) findViewById(R.id.textViewAmount)).setText(account.getRoundAnount().toString());
        ((TextView) findViewById(R.id.textViewName)).setText(account.getName());
        ((TextView) findViewById(R.id.textViewCurrency)).setText(Currency.RUB.toString());
    }

    public void onButtonClickWithdrawal(View v){
        openTransactionActivity(TransactionType.Снятие);
    }
    public void onButtonClickCrediting(View v){
        openTransactionActivity(TransactionType.Зачисление);
    }
    private  void openTransactionActivity(TransactionType transactionType){
        Intent intent = new Intent(".New_transaction");
        intent.putExtra("TransactionType", transactionType);
        startActivity(intent);
    }

    public void onButtonCliclBack(View v){
        finish();
    }
    public void onButtonCliclDelete(View v){
        MainActivity.deleteAccount(account);
        MainActivity.database.delete(DatabaseHelper.TABLE_ACCOUNTS, DatabaseHelper.COL1 + " = " + account.getId() , null);
        finish();
    }
}