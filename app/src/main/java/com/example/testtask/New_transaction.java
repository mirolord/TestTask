package com.example.testtask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;

public class New_transaction extends AppCompatActivity {

    private Account account;
    private TransactionType transactionType;

    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_transaction);

        account = MainActivity.CurrentAccount;
        transactionType = (TransactionType)getIntent().getSerializableExtra("TransactionType");
        TextView accountName = (TextView)findViewById(R.id.textViewAccountName);
        accountName.setText(account.getName());
        TextView transactionType = (TextView)findViewById(R.id.textViewTransactionType);
        transactionType.setText(this.transactionType.toString());
        spinner = (Spinner)findViewById(R.id.spinner);
        spinner.setAdapter(new ArrayAdapter<Currency>(this, android.R.layout.simple_spinner_item, Currency.values()));

    }

    public void onButtonClickContinue(View v){

        EditText editTextAmount = (EditText)findViewById(R.id.editTextAmount);
        BigDecimal amount;
        try {
            amount = new BigDecimal(editTextAmount.getText().toString());
        }
        catch (Exception e){
            return;
        }
        EditText editTextComment = (EditText)findViewById(R.id.editTextComment);
        String comment = editTextComment.getText().toString();
        Currency сurrency = (Currency)spinner.getSelectedItem();

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            Toast.makeText(getApplicationContext(), "Сумма должна быть больше нуля!", Toast.LENGTH_SHORT).show();
            return;
        }
        Transaction transaction = new Transaction(account, amount, сurrency, transactionType, comment);
        if(transaction.successful()) transaction.save();//transactions.AddTransaction(transaction);
        else {
            Toast.makeText(getApplicationContext(), "Недостаточно средств!", Toast.LENGTH_SHORT).show();
            return;
        }

        finish();
    }
    public void onButtonCliclBack(View v){
       finish();
    }
}