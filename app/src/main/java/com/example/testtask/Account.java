package com.example.testtask;

import android.content.ContentValues;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

enum Currency
{
    RUB(BigDecimal.valueOf(1)), USD(BigDecimal.valueOf(76.77d)), EUR(BigDecimal.valueOf(90.82d));
    private BigDecimal course;
    public BigDecimal getCourse() { return this.course; }
    public void setCourse(BigDecimal value){ this.course = value;}



    private Currency(BigDecimal course) {this.course = course; }




    public static BigDecimal Converter(BigDecimal value, Currency from, Currency to){
        BigDecimal ratio = from.getCourse().divide(to.getCourse());
        return  value.multiply(ratio);
    }
    public static BigDecimal ConverterRound(BigDecimal value, Currency from, Currency to){
        BigDecimal ratio = from.getCourse().divide(to.getCourse());
        return  value.multiply(ratio).setScale(2, RoundingMode.CEILING);
    }
}

//Счёт
public class Account implements Serializable {
    private Long id;
    private String наименование;
    private BigDecimal сумма = BigDecimal.valueOf(0);;
    private Currency валюта = Currency.RUB;
    public Long getId(){return id;}
    public String getName(){return наименование;}
    //public Currency getAccountValute(){return валюта;}
    public BigDecimal getAmount(){return сумма;}
    public BigDecimal getRoundAnount(){return сумма.setScale(2, RoundingMode.CEILING);}



    public Account(String name, Long id){
        this.id = id;
        наименование = name;
    }
    public Account(String name, double amount, Long id){
        this.id = id;
        сумма = BigDecimal.valueOf(amount);
        наименование = name;
    }



    //списание
    public boolean WithdrawMoney(BigDecimal value){
        BigDecimal newValue = сумма.subtract(value);
        if(newValue.compareTo(BigDecimal.ZERO) < 0) return false;
        сумма = newValue;
        changeDataAmount();
        return true;
    }
    //зачисление
    public void CreditingAccount(BigDecimal value){
        сумма = сумма.add(value);
        changeDataAmount();
    }
    private void changeDataAmount(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL3, сумма.toString());
        MainActivity.database.update(DatabaseHelper.TABLE_ACCOUNTS, contentValues,  DatabaseHelper.COL1 + " = " + id , null );
        //MainActivity.database.replace(DatabaseHelper.TABLE_NAME,null,contentValues);
    }

    @Override
    public String toString() {
        return наименование;
    }
}
