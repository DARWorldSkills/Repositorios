package com.danielaburbano.stroopermartes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class Puntaje extends AppCompatActivity {

    TextView txtprimero, txtsegundo, txttercero, txtcuarto, txtquinto;
    TextView [] posiciones = {txtprimero, txtsegundo, txttercero, txtcuarto, txtquinto};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntaje);
        inizialite();
        inputData();
    }

    private void inizialite() {
        txtprimero = findViewById(R.id.txtprimero);
        txtsegundo = findViewById(R.id.txtsegundo);
        txttercero = findViewById(R.id.txttercero);
        txtcuarto = findViewById(R.id.txtcuarto);
        txtquinto = findViewById(R.id.txtquinto);

    }

    private void inputData() {
        GestorDB gestorDB = new GestorDB(this);
        List<Score> resultados =gestorDB.listarPuntajes();

        if (resultados.size()>=1) {
            txtprimero.setText(resultados.get(0).getPuntaje()+"%");

        }

        if (resultados.size()>=2) {
            txtsegundo.setText(resultados.get(1).getPuntaje()+"%");
        }

        if (resultados.size()>=3) {
            txttercero.setText(resultados.get(2).getPuntaje()+"%");
        }

        if (resultados.size()>=4) {
            txtcuarto.setText(resultados.get(3).getPuntaje()+"%");

        }

        if (resultados.size()>=5) {
            txtquinto.setText(resultados.get(4).getPuntaje()+"%");
        }

    }



    public void salir(View view) {
        finish();
    }
}
