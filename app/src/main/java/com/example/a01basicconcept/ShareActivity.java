package com.example.a01basicconcept;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
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
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        /*Versiones nuevas para validar permisos*/
                        /*requestPermissions(new String[]{Manifest.permission.CALL_PHONE},
                                PHONE_CALL_CODE);*/ /*->Reemplazamos por el conjunto de código siguiente*/

                        /* Comprobando si ha aceptado el permiso, no lo ha aceptado,
                         o nunca se le ha preguntado */
                        if (checkPermission(Manifest.permission.CALL_PHONE)) {
                            /* Ha aceptado */
                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + nroTelefono));
                            startActivity(intent);
                        } else {
                            /* O ha denegado, o es la primera vez que se le pregunta */
                            /* Preguntando si ya se le ha consultado por un permiso al usuario*/
                            if (!shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)) {
                                /* No se e ha preguntado aun*/
                                requestPermissions(new String[]{Manifest.permission.CALL_PHONE},
                                        PHONE_CALL_CODE);
                            } else {
                                /* Ha denegado */
                                Toast.makeText(ShareActivity.this, "Active los permisos",
                                        Toast.LENGTH_LONG).show();
                                /* Mostrando el panel de configuraciones */
                                Intent i = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                i.addCategory(Intent.CATEGORY_DEFAULT);
                                i.setData(Uri.parse("package:" + getPackageName()));
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                                startActivity(i);
                            }
                        }
                    } else {
                        olderVersions(nroTelefono);
                    }
                }
            }

            /*Versiones antiguas para validar permisos*/
            private void olderVersions(String nroTelefono) {
                Intent intentPhone = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + nroTelefono));
                if (checkPermission(Manifest.permission.CALL_PHONE)) {
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
                String permiso = permissions[0];
                int resultado = grantResults[0];

                if (permiso.equals(Manifest.permission.CALL_PHONE)) {
                    /* Comprobando si ha sido aceptada la petición de permiso */
                    if (resultado == PackageManager.PERMISSION_GRANTED) {
                        /* Permiso concedido */
                        String nroTelefono = txtSharePhone.getText().toString();
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + nroTelefono));
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                                != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        startActivity(intent);
                    } else {
                        /* Permiso denegado */
                        Toast.makeText(ShareActivity.this, "Permiso denegado",
                                Toast.LENGTH_LONG).show();
                    }
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }

    /*Método para validar si algún permiso ya ha sido dado por el usuario*/
    private boolean checkPermission(String permiso) {
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
