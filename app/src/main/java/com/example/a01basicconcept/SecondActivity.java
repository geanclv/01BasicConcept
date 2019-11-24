package com.example.a01basicconcept;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    private TextView lblSecond;
    private Button btnSecond;
    private String saludo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        initControls();

        /*Cambiando el icono de la aplicación*/
        ponerIconoEnActionBar();

        /*Activa la flecha para ir atrás en el ActionBar*/
        flechaAtrasEnActionBar();

        /*Tomando los datos enviados por el Intent*/
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && !bundle.isEmpty()) {
            saludo = bundle.getString("saludo");
        } else {
            Toast.makeText(this, "Resultado vacío", Toast.LENGTH_LONG).show();
            saludo = "No han enviado nada =(";
        }

        lblSecond.setText(saludo);

        btnSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void ponerIconoEnActionBar(){
        getSupportActionBar().setDisplayShowHomeEnabled(true); //fuerza el icono en el action bar
        getSupportActionBar().setIcon(R.drawable.ic_satelite_32); //icono del action bar
        //para cambiar el icono de la aplicación debemos ir al Manifest sección "icon"
        //para cambiar el nombre de la aplicación debemos ir al Manifest sección "label"
    }

    /*Activa la flecha para ir atrás en el ActionBar*/
    private void flechaAtrasEnActionBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Además, debemos ir al Manifest e indicar cual es el Activity al que se "va atrás"
    }

    private void initControls(){
        lblSecond = findViewById(R.id.lblSecond);
        btnSecond = findViewById(R.id.btnSecond);
    }
}
