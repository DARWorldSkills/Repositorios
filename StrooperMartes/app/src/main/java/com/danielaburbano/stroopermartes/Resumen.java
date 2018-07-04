package com.danielaburbano.stroopermartes;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Resumen extends AppCompatActivity {
    TextView txtPalabrasCo, txtPalabrasIn, txtAciertos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen);
        inizialite();
        if (Menu.modo==1){
            guardarDatos();
        }

    }

    private void guardarDatos() {
        GestorDB gestorDB = new GestorDB(this);
        SQLiteDatabase db = gestorDB.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("PUNTAJE",Integer.toString(Juego.pAcierto));
        contentValues.put("INCORRECTOS",Integer.toString(Juego.pIncorrectas));
        db.insert("DATOS",null,contentValues);
    }

    public void inizialite(){
        txtPalabrasCo= findViewById(R.id.txtpalabrasC);
        txtPalabrasIn= findViewById(R.id.txtpalabrasI);
        txtAciertos= findViewById(R.id.txtReaccion);

        txtPalabrasCo.setText(Integer.toString(Juego.pCorrectas));
        txtPalabrasIn.setText(Integer.toString(Juego.pIncorrectas));
        txtAciertos.setText(Integer.toString(Juego.pAcierto));
    }

    public void salir(View view) {
        finish();
    }
}
