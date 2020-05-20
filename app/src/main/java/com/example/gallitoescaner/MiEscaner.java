package com.example.gallitoescaner;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.zxing.Result;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MiEscaner extends AppCompatActivity  implements ZXingScannerView.ResultHandler,View.OnClickListener {

    private ZXingScannerView mScannerView;
    private static final int WRITE_EXST = 1;
    private static final int REQUEST_PERMISSION = 123;
    int CAMERA;
    String position,formt;

    private String textoIp;
    private String textoPuerto;

    private Button[] btn = new Button[3];
    private Button btn_unfocus;
    private int[] btn_id = {R.id.btnBusqueda,R.id.btnAgregar,R.id.btnQuitar};
    public String BotonSeleccionado  = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_escaner);
        Bundle datos = this.getIntent().getExtras();

        for(int i=0; i < btn.length;i++){
            btn[i] = (Button) findViewById(btn_id[i]);
            btn[i].setBackgroundColor(Color.rgb(207, 207, 207));
            btn[i].setOnClickListener(this);

        }

        btn_unfocus = btn[0];


        textoIp = datos.getString("Ip");
        textoPuerto = datos.getString("Puerto");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        if( ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.CAMERA},5);
            }
        }

        ViewGroup contentFrame = (ViewGroup) findViewById(R.id.content_frame);
        mScannerView = new ZXingScannerView(this);
        contentFrame.addView(mScannerView);
    }

    @Override
     public void onClick(View v){

        switch (v.getId()){
            case R.id.btnBusqueda :
                setFocus(btn_unfocus, btn[0]);
                BotonSeleccionado= "Busqueda";
                break;

            case R.id.btnAgregar :
                setFocus(btn_unfocus, btn[1]);
                BotonSeleccionado= "Agregar";
                break;

            case R.id.btnQuitar:
                setFocus(btn_unfocus, btn[2]);
                BotonSeleccionado= "Quitar";
                break;
        }

     }


    private void setFocus(Button btn_unfocus, Button btn_focus){
        btn_unfocus.setTextColor(Color.rgb(49, 50, 51));
        btn_unfocus.setBackgroundColor(Color.rgb(207, 207, 207));
        btn_focus.setTextColor(Color.rgb(255, 255, 255));
        btn_focus.setBackgroundColor(Color.rgb(3, 106, 150));
        this.btn_unfocus = btn_focus;
    }


    @Override
    protected void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(final Result result) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    cliente(result.toString());
                }catch (Exception e){
                    Log.e("Error3","Exception"+e.getMessage());
                }


            }
        });
    }


    private void cliente(String res){
        String ip =textoIp ;
        int puerto =Integer.parseInt(textoPuerto);

        Log.e("error",ip);
        Log.e("error1",""+puerto);
        try {
            Socket sk = new Socket(ip,puerto);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(sk.getInputStream()));
            PrintWriter salida = new PrintWriter(new OutputStreamWriter(sk.getOutputStream()),true);
            salida.println(BotonSeleccionado+","+res);
            sk.close();
            mScannerView.resumeCameraPreview(this);
        }catch (Exception e){
            Log.d("ErrorComunanicacion", e.toString());
        }

    }



}
