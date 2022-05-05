package com.example.letsrollsomedicesnew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public ArrayList<EditText> collectAllDiceAmount(){
        ArrayList<EditText> result =new ArrayList<>();
        result.add(findViewById(R.id.diceAmount4));
        result.add(findViewById(R.id.diceAmount6));
        result.add(findViewById(R.id.diceAmount8));
        result.add(findViewById(R.id.diceAmount10));
        result.add(findViewById(R.id.diceAmount12));
        result.add(findViewById(R.id.diceAmount20));
        return result;
    }
    public ArrayList<EditText> collectAllModifiers(){
        ArrayList<EditText> result =new ArrayList<>();
        result.add(findViewById(R.id.modifierAmount4));
        result.add(findViewById(R.id.modifierAmount6));
        result.add(findViewById(R.id.modifierAmount8));
        result.add(findViewById(R.id.modifierAmount10));
        result.add(findViewById(R.id.modifierAmount12));
        result.add(findViewById(R.id.modifierAmount20));
        return result;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        if(id==R.id.clearHistory){
            TextView history = findViewById(R.id.result);
            history.setText("");
        }
        if(id==R.id.clearDices){
            ArrayList<EditText> dices=collectAllDiceAmount();
            for(EditText i: dices) i.setText("0");
            ArrayList<EditText> mod=collectAllModifiers();
            for(EditText i: mod) i.setText("0");
        }
        return super.onOptionsItemSelected(item);
    }
    public String insertDice(String insert){
        String correct=null;
        if((char)insert.charAt(0)=='-'){
            correct=insert;
        }
        else
        if((char)insert.charAt(0)=='0')
            correct=null;
        else
            correct='+'+insert;

        return correct;
    }
    @SuppressLint("SetTextI18n")
    public void makeRoll(View view) {
        TextView result=findViewById(R.id.result);
        Dice dice=new Dice();
        String request="";
        ArrayList<EditText> dices=new ArrayList<EditText>();
        dices=collectAllDiceAmount();
        ArrayList<EditText> mod=new ArrayList<EditText>();
        mod=collectAllModifiers();
        String sides="";
        int j=0;
        for(EditText i:dices){
            EditText m=mod.get(j);
            if(insertDice(String.valueOf(i.getText()))!=null) {
                sides = String.valueOf(i.getTransitionName());
                request += insertDice(String.valueOf(i.getText())) + 'd' + sides;
                if(insertDice(String.valueOf(m.getText()))!=null)request +=insertDice(String.valueOf(m.getText()));
            }
            j++;
        }
        dice.doTheDiceRoll(request);
        result.setText(result.getText()+dice.showResult());
    }

    public void removeDice(View view) {
        String keyId=view.getTransitionName();
        ArrayList<EditText> dices = collectAllDiceAmount();
        for(EditText i : dices){
            if(i.getTransitionName().equals(keyId))
                i.setText(String.valueOf(Integer.valueOf(String.valueOf(i.getText()))-1));
        }
    }

    public void addDice(View view) {
        String keyId=view.getTransitionName();
        ArrayList<EditText> dices = collectAllDiceAmount();
        for(EditText i : dices){
            if(i.getTransitionName().equals(keyId))
                i.setText(String.valueOf(Integer.valueOf(String.valueOf(i.getText())) + 1));
        }
    }

    public void removeMod(View view) {
        String keyId=view.getTransitionName();
        ArrayList<EditText> mod = collectAllModifiers();
        for(EditText i : mod){
            if(i.getTransitionName().equals(keyId))
                i.setText(String.valueOf(Integer.valueOf(String.valueOf(i.getText()))-1));
        }
    }

    public void addMod(View view) {
        String keyId=view.getTransitionName();
        ArrayList<EditText> mod = collectAllModifiers();
        for(EditText i : mod){
            if(i.getTransitionName().equals(keyId))
                i.setText(String.valueOf(Integer.valueOf(String.valueOf(i.getText())) + 1));
        }
    }
}