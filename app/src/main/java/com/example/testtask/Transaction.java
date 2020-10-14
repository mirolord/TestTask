package com.example.testtask;

import android.content.ContentValues;

import java.math.BigDecimal;
import java.util.Date;

enum TransactionType{ Зачисление, Снятие};

//Транзакция
public class Transaction {
    private Date дата;
    private BigDecimal сумма;
    private Currency валюта;
    private String комментарий = "";
    private TransactionType типТранзакции;
    private String названиеСчёта;
    private boolean isSuccessful = true;
    public Date getDate() { return дата; }
    public BigDecimal getAmount() { return сумма; }
    public Currency getCurrency() { return валюта; }
    public String getComment(){return комментарий;}
    public TransactionType getTransactionType(){return типТранзакции;}
    public boolean successful(){return isSuccessful;};



    public Transaction(Account account, BigDecimal amount, Currency currency, TransactionType transactionType, String comment){
        if (transactionType == TransactionType.Снятие) {
            isSuccessful = account.WithdrawMoney(Currency.Converter(amount, currency, Currency.RUB));
            if(!isSuccessful) return;
        }
        else  account.CreditingAccount(Currency.Converter(amount, currency, Currency.RUB));

        сумма = amount;
        типТранзакции = transactionType;
        дата = new Date(System.currentTimeMillis());
        валюта = currency;
        комментарий = comment;
        названиеСчёта = account.getName();
    }



    @Override
    public String toString() {
        String text = названиеСчёта;
        text += '\n' + типТранзакции.toString() + ' ' + сумма.toString()+валюта;
        if (валюта != Currency.RUB) text += " (" + Currency.ConverterRound(сумма, валюта, Currency.RUB).toString()+Currency.RUB+")";
        text += '\n' + дата.toString();
        text += '\n' + комментарий;
        return text;
    }

    public void save() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.TRANSACTION_INFO,toString());
        MainActivity.database.insert(DatabaseHelper.TABLE_TRANSACTIONS,null,contentValues);
    }
}
