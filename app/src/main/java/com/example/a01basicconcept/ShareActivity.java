package com.example.a01basicconcept;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
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
    private ImageButton btnShareWeb;
    private ImageButton btnSharePhone;
    private ImageButton btnShareCamera;

    private final static int PHONE_CALL_CODE = 99;
    private final static int PICTURE_FROM_CAMERA = 88;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        initControls();

        /* Botòn para llamadas telefónicas */
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

        /* Botón para abrir navegador web con url */
        btnShareWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = txtShare.getText().toString();
                if (!url.isEmpty()) {
                    Intent intentWeb = new Intent();
                    intentWeb.setAction(Intent.ACTION_VIEW);
                    intentWeb.setData(Uri.parse("http://" + url));

                    /* Otros tipos de Intent */
                    //Abrir app de contactos
                    Intent intentContacts = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("content://contacts/people"));
                    //Email rápido
                    String email = "geanclv@gmail.com";
                    Intent intentEmail = new Intent(Intent.ACTION_SENDTO,
                            Uri.parse("mailto:" + email));
                    //Email completo
                    Intent intentEmailFull = new Intent(Intent.ACTION_SEND);
                    intentEmailFull.setType("message/rfc822");
                    intentEmailFull.putExtra(Intent.EXTRA_SUBJECT, "titulo");
                    intentEmailFull.putExtra(Intent.EXTRA_TEXT, "cuerpo del mail");
                    intentEmailFull.putExtra(Intent.EXTRA_EMAIL,
                            new String[]{email, "gean@hotmail.com"});
                    intentEmailFull.putExtra(Intent.EXTRA_CC,
                            new String[]{"gean_lv@hotmail.com", "gean@hotmail.com"});
                    /*Pide al usuario que seleccione que app abrir para completar la acción*/
                    startActivity(Intent.createChooser(intentEmailFull, "Elige cliente email"));
                    //Teléfono 2, no necesita permisos
                    Intent intentPhone = new Intent(Intent.ACTION_DIAL,
                            Uri.parse("tel:123"));
                    //startActivity(intentEmailFull);
                }
            }
        });

        btnShareCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Abrir la cámara
                Intent intentCamara = new Intent("android.media.action.IMAGE_CAPTURE");
                //startActivity(intentCamara);
                /*Abre la cámara y luego de una foto devuelve la foto a la app,
                se complementa con el método onActivityResult*/
                startActivityForResult(intentCamara, PICTURE_FROM_CAMERA);
            }
        });
    }

    /*Método complementario para abrir la galería de imágenes*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Toast.makeText(this, "onac", Toast.LENGTH_LONG).show();
        switch (requestCode) {
            case PICTURE_FROM_CAMERA:
                if (resultCode == Activity.RESULT_OK) {
                    String resultado = data.toUri(0); //devuelve la imagen a la cual le tomamos foto
                    Toast.makeText(this, "Resultado: " + resultado, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Error mostrando galeria", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
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
        btnShareWeb = findViewById(R.id.btnShareWeb);
        btnSharePhone = findViewById(R.id.btnSharePhone);
        btnShareCamera = findViewById(R.id.btnShareCamera);
    }
}
