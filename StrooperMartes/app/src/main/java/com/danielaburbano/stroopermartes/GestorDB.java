package com.danielaburbano.stroopermartes;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by APRENDIZ on 03/07/2018.
 */

public class GestorDB extends SQLiteOpenHelper{
    public GestorDB(Context context) {
        super(context, "datos.db",null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE DATOS (PUNTAJE TEXT, INCORRECTOS TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<Score> listarPuntajes(){
        List<Score> results = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM DATOS ORDER BY PUNTAJE DESC",null);
        if (cursor.moveToFirst()){
            do {
                Score score = new Score();
                score.setPuntaje(cursor.getString(0));
                score.setIncorrectas(cursor.getString(1));
                results.add(score);

            }while (cursor.moveToNext());
        }
        return results;
    }

}
