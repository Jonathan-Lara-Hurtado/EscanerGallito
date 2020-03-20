package com.example.gallitoescaner;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.method.DigitsKeyListener;
import android.text.method.KeyListener;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class SegundaActividad  extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 0 ;
    private static final int MY_PERMISSIONS_REQUEST_INTERNET =0 ;
    private TextView txtIp;
    private TextView txtPuerto;
    private Button btnConexion;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_segunda);
        txtIp = findViewById(R.id.txtIp);
        txtPuerto = findViewById(R.id.txtPuerto);
        btnConexion = findViewById(R.id.btnConectar);
        KeyListener keyListener = DigitsKeyListener.getInstance("123456789.");
        txtIp.setKeyListener(keyListener);


        btnConexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent viewEscaner = new Intent(getApplicationContext(),MiEscaner.class);
                viewEscaner.putExtra("Ip",txtIp.getText().toString());
                viewEscaner.putExtra("Puerto",txtPuerto.getText().toString());
                startActivity(viewEscaner);
            }
        });
    }
}
