package com.example.testtask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;

public class Settings extends AppCompatActivity {

    EditText CurrencyEUR, CurrencyUSD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        CurrencyUSD = (EditText)findViewById(R.id.CurrencyUSD);
        CurrencyUSD.setText(Currency.USD.getCourse().toString());
        CurrencyEUR = (EditText)findViewById(R.id.CurrencyEUR);
        CurrencyEUR.setText(Currency.EUR.getCourse().toString());

        CurrencyEUR.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                Currency.EUR.setCourse(new BigDecimal(CurrencyEUR.getText().toString()));
            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { }
        });
        CurrencyUSD.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                Currency.USD.setCourse(new BigDecimal(CurrencyUSD.getText().toString()));
            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { }
        });
    }

    public void onButtonClickAccounts(View v){
        Intent startIntent = new Intent(this, MainActivity.class);
        startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startIntent);
    }
    public void onButtonClickTransactions(View v){
        Intent intent = new Intent(".transactions");
        startActivity(intent);
    }
}