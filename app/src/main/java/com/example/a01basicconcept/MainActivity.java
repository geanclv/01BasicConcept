package com.example.a01basicconcept;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        /*Forma 2 de escribir acciones de boton*/
        /*implements View.OnClickListener*/
    {

    private Button btnMain;
    private EditText txtMain;
    private Button btnMainCompartir;
        /*private Switch swiMain;
        private CheckBox chkMain;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initControls();

        /*Forma 1 de escribir acciones de un boton*/
        /*btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mensaje = txtMain.getText().toString();
                showToast(MainActivity.this, mensaje);
            }
        });*/

        /*Forma 2 de escribir acciones de boton*/
        /*btnMain.setOnClickListener(this);*/

        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("saludo", txtMain.getText().toString());
                startActivity(intent);
            }
        });

        btnMainCompartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });
    }

    /*Forma 2 de escribir acciones de boton*/
    /*@Override
    public void onClick(View v) {
        String mensaje = txtMain.getText().toString();
        showToast(this, mensaje);
    }*/

    private void showToast(Context context, String mensaje) {
        Toast.makeText(context, mensaje, Toast.LENGTH_LONG).show();
    }

    public void initControls () {
        btnMain = findViewById(R.id.btnMain);
        txtMain = findViewById(R.id.txtMain);
        btnMainCompartir = findViewById(R.id.btnMainCompartir);
        /*lblMain = findViewById(R.id.lblMain);
        swiMain = findViewById(R.id.swiMain);
        chkMain = findViewById(R.id.chkMain);*/
    }
}