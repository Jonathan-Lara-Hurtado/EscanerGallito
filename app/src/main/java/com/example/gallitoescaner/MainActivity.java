package com.example.gallitoescaner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    public ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);
        configurarBarraProgreso();
        cargaSimulada();

    }


    protected void configurarBarraProgreso(){
        progressBar =(ProgressBar) findViewById(R.id.barraDeInicio);
        progressBar.setProgressTintList(ColorStateList.valueOf(Color.YELLOW));

    }


    protected  void cargaSimulada(){
        /*Referencia
        * https://stackoverflow.com/questions/42452738/android-update-progressbar-with-handler/42453199
        * */

        final Handler handler = new Handler();
    class Tarea implements Runnable{

        @Override
        public  void run(){
                for(int i=0;i<=10;i++){
                    final int value=i;
                    try {
                        Thread.sleep(100);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(value);

                            if(progressBar.getProgress()==10){
                                /*
                                * Refencias
                                * https://wajahatkarim.com/2018/04/closing-all-activities-and-launching-any-specific-activity/
                                * */
                                final Intent scanerApp = new Intent(getApplicationContext(),SegundaActividad.class);
                                scanerApp.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                scanerApp.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                scanerApp.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                scanerApp.putExtra("EXIT",true);
                                startActivity(scanerApp);
                                finish();

                            }

                        }
                    });
                }

            }
        }

        Thread t = new Thread(new Tarea());
        t.start();


    }


}
