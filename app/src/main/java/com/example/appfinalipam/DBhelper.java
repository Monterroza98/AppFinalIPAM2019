package com.example.appfinalipam;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBhelper extends SQLiteOpenHelper {

    // Información de la tabla
    public static final String TABLE_STUDENTS = "estudiantes";
    public static final String STUDENT_ID= "_id";
    public static final String STUDENT_NAME = "nombre";
    public static final String STUDENT_LASTNAME = "apellido";
    public static final String STUDENT_GENDER = "sexo";
    public static final String STUDENT_CARNET = "carnet";
    public static final String STUDENT_CAREER = "carrera";


    // información del a base de datos
    static final String DB_NAME = "DBSTUDENT";
    static final int DB_VERSION = 1;

    // Información de la base de datos
    private static final String CREATE_TABLE = "create table "
            + TABLE_STUDENTS + "(" + STUDENT_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + STUDENT_NAME + " TEXT NOT NULL, "
            + STUDENT_LASTNAME+ "TEXT NOT NULL, "
            + STUDENT_GENDER+ "TEXT NOT NULL, "
            + STUDENT_CARNET+ "TEXT NOT NULL, "
            + STUDENT_CAREER+ "TEXT NOT NULL);";

    public DBhelper(Context context) {
        super(context, DB_NAME, null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        onCreate(db);
    }
}