package com.example.appfinalipam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class SQLControlador {

    private DBhelper dbhelper;
    private Context ourcontext;
    private SQLiteDatabase database;

    public SQLControlador(Context c) {
        ourcontext = c;
    }

    public SQLControlador abrirBaseDeDatos() throws SQLException {
        dbhelper = new DBhelper(ourcontext);
        database = dbhelper.getWritableDatabase();
        return this;
    }

    public void cerrar() {
        dbhelper.close();
    }

    public void insertarDatos(String nombre, String apellido, String sexo, String carnet, String carrera) {
        ContentValues cv = new ContentValues();
        cv.put(DBhelper.STUDENT_NAME, nombre);
        cv.put(DBhelper.STUDENT_LASTNAME, apellido);
        cv.put(DBhelper.STUDENT_GENDER, sexo);
        cv.put(DBhelper.STUDENT_CARNET, carnet);
        cv.put(DBhelper.STUDENT_CAREER, carrera);
        database.insert(DBhelper.TABLE_STUDENTS, null, cv);
    }

    public Cursor leerDatos() {
        String[] todasLasColumnas = new String[] {
                DBhelper.STUDENT_ID,
                DBhelper.STUDENT_NAME,
                DBhelper.STUDENT_LASTNAME,
                DBhelper.STUDENT_GENDER,
                DBhelper.STUDENT_CARNET,
                DBhelper.STUDENT_CAREER,

        };
        Cursor c = database.query(DBhelper.TABLE_STUDENTS, todasLasColumnas, null,
                null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public int actualizarDatos(long id, String nombre, String apellido, String sexo, String carnet, String carrera) {
        ContentValues cvActualizar = new ContentValues();
        cvActualizar.put(DBhelper.STUDENT_NAME, nombre);
        cvActualizar.put(DBhelper.STUDENT_LASTNAME, apellido);
        cvActualizar.put(DBhelper.STUDENT_GENDER, sexo);
        cvActualizar.put(DBhelper.STUDENT_CARNET, carnet);
        cvActualizar.put(DBhelper.STUDENT_CAREER, carrera);
        int i = database.update(DBhelper.TABLE_STUDENTS, cvActualizar,
                DBhelper.STUDENT_ID + " = " + id, null);
        return i;
    }

    public void deleteData(long id) {
        database.delete(DBhelper.TABLE_STUDENTS, DBhelper.STUDENT_ID + "="
                + id, null);
    }
}