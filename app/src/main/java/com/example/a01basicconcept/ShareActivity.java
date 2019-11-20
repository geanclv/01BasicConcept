package com.example.a01basicconcept;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class ShareActivity extends AppCompatActivity {

    private EditText txtShare;
    private EditText txtSharePhone;
    private ImageButton btnShareHelp;
    private ImageButton btnSharePhone;
    private ImageButton btnShareCamera;

    private final static int PHONE_CALL_CODE = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        initControls();

        btnSharePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nroTelefono = txtSharePhone.getText().toString();
                if (!nroTelefono.isEmpty()) {
                    /* Comprobar la versión del dispositivo (Android) */
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        /*Versiones nuevas para validar permisos*/
                        requestPermissions(new String[]{Manifest.permission.CALL_PHONE},
                                PHONE_CALL_CODE);
                    } else {
                        olderVersions(nroTelefono);
                    }
                }
            }

            /*Versiones antiguas para validar permisos*/
            private void olderVersions(String nroTelefono) {
                Intent intentPhone = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + nroTelefono));
                if(checkPermission(Manifest.permission.CALL_PHONE)) {
                    startActivity(intentPhone);
                } else {
                    Toast.makeText(ShareActivity.this,
                            "No hay permisos", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /*Método para */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        /*Validando la petición de permisos en base al código enviado*/
        switch (requestCode) {
            case PHONE_CALL_CODE:
                break;
            default:
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /*Método para validar si algún permiso ya ha sido dado por el usuario*/
    private boolean checkPermission (String permiso) {
        int resultado = this.checkCallingOrSelfPermission(permiso);
        return resultado == PackageManager.PERMISSION_GRANTED;
    }

    private void initControls() {
        txtShare = findViewById(R.id.txtShare);
        txtSharePhone = findViewById(R.id.txtSharePhone);
        btnShareHelp = findViewById(R.id.btnShareHelp);
        btnSharePhone = findViewById(R.id.btnSharePhone);
        btnShareCamera = findViewById(R.id.btnShareCamera);
    }
}
