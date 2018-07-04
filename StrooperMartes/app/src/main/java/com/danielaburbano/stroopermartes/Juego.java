package com.danielaburbano.stroopermartes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Juego extends AppCompatActivity  implements View.OnClickListener{
    List<String> listaPalabra = new ArrayList<>();
    List<Integer> listaColores = new ArrayList<>();
    TextView txtCorrectas, txtIncorrectas, txtPalabra, txtAcierto, txtTiempo, txtIntentos;
    Button btnColor1, btnColor2, btnColor3, btnColor4;
    List<Integer> tmpList;
    int [] segundos = {0,30};
    public static int pCorrectas, pIncorrectas, pAcierto, pIntentos;
    int valorcito;
    int ipR, icR;
    boolean bandera= true;
    int ab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);
        segundos = new int[]{0, 30};
        ab=0;
        pCorrectas=0;
        pIncorrectas=0;
        pAcierto=0;
        pIntentos=0;
        bandera=true;
        inizialite();
        listar();
        randomizar();
        insertarValores();
        txtTiempo.setText("Seg: 00:"+segundos[1]);
        runGame();

    }

    public void runGame(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (bandera) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            segundos[0] += 1;
                            segundos[1] = segundos[1] - 1;

                            if (segundos[0] == 3) {
                                pIncorrectas += 1;
                                pIntentos += 1;
                                randomizar();
                                insertarValores();
                                segundos[0]=0;
                            }

                            txtTiempo.setText("Seg: 00:" + segundos[1]);
                            salir();

                        }

                    });
                }

            }
        });

        thread.start();
    }

    public void inizialite(){
        txtCorrectas = findViewById(R.id.txtcorrectas);
        txtIncorrectas = findViewById(R.id.txtincorrectas);
        txtPalabra = findViewById(R.id.txtPalabraJ);
        txtAcierto = findViewById(R.id.txtaciertos);
        txtTiempo = findViewById(R.id.txttiempo);
        txtIntentos = findViewById(R.id.txtintentos);
        btnColor1 = findViewById(R.id.btnColor1);
        btnColor2 = findViewById(R.id.btnColor2);
        btnColor3 = findViewById(R.id.btnColor3);
        btnColor4 = findViewById(R.id.btnColor4);

        btnColor1.setOnClickListener(this);
        btnColor2.setOnClickListener(this);
        btnColor3.setOnClickListener(this);
        btnColor4.setOnClickListener(this);

    }

    public void salir(){

        if ((pIncorrectas==3 || segundos[1]==0)&& ab==0 ){
            ab=1;
            Intent intent = new Intent(Juego.this,Resumen.class);
            finish();
            startActivity(intent);
            insertarValores();

            bandera= false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        bandera=false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        bandera=false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bandera=false;
    }

    public void listar(){
        listaPalabra.add("AMARILLO");
        listaColores.add(getColor(R.color.amarilloJuego));
        listaPalabra.add("AZUL");
        listaColores.add(getColor(R.color.azulJuego));
        listaPalabra.add("ROJO");
        listaColores.add(getColor(R.color.rojoJuego));
        listaPalabra.add("VERDE");
        listaColores.add(getColor(R.color.verdeJuego));

    }


    public void randomizar(){
        tmpList = listaColores;
        Collections.shuffle(tmpList);
        ipR= (int) (Math.random()*4);
        icR= (int) (Math.random()*4);
        txtPalabra.setText(listaPalabra.get(ipR));
        txtPalabra.setTextColor(listaColores.get(icR));
        btnColor1.setBackgroundColor(tmpList.get(0));
        btnColor2.setBackgroundColor(tmpList.get(1));
        btnColor3.setBackgroundColor(tmpList.get(2));
        btnColor4.setBackgroundColor(tmpList.get(3));



    }

    public void validar(){
        if (valorcito==1){
            if (icR==0){
                pCorrectas+=1;
            }else {
                pIncorrectas+=1;
            }
        }

        if (valorcito==2){
            if (icR==1){
                pCorrectas+=1;
            }else {
                pIncorrectas+=1;
            }
        }


        if (valorcito==3){
            if (icR==2){
                pCorrectas+=1;
            }else {
                pIncorrectas+=1;
            }
        }


        if (valorcito==4){
            if (icR==3){
                pCorrectas+=1;
            }else {
                pIncorrectas+=1;
            }

        }
        pIntentos+=1;


    }

    public void insertarValores(){
        if (pCorrectas>0){
           double tmp1 = pCorrectas, tmp2= pIntentos;
           float tmpP = (float)  (tmp1/ tmp2)*100;
           pAcierto= (int)tmpP;
        }else{
            pAcierto=0;
        }

        txtCorrectas.setText("Correctas: "+pCorrectas);
        txtIncorrectas.setText("Incorrectas: "+pIncorrectas);
        txtAcierto.setText("Aciertos: "+pAcierto);
        txtIntentos.setText("Intentos: "+pIntentos);


    }


    public void variosMetodos(){
        segundos[0]=0;
        validar();
        salir();
        randomizar();
        insertarValores();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnColor1:
                valorcito=1;
                variosMetodos();
                break;

            case R.id.btnColor2:
                valorcito=2;
                variosMetodos();
                break;

            case R.id.btnColor3:
                valorcito=3;
                variosMetodos();
                break;

            case R.id.btnColor4:
                valorcito=4;
                variosMetodos();
                break;

        }

    }
}