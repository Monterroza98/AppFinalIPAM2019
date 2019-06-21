package com.example.appfinalipam;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

public class AgregarEstudiante extends Activity implements View.OnClickListener {

    EditText nombre, apellido, carnet;
    RadioButton rbtnFem, rbtnMasc;
    Spinner listaCarrera;
    Button btnAgregar;
    SQLControlador dbconeccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregar_estudiante);

        nombre = (EditText) findViewById(R.id.addNombre);
        apellido = (EditText) findViewById(R.id.addApellido);
        carnet = (EditText) findViewById(R.id.addCarnet);
        rbtnMasc = (RadioButton) findViewById(R.id.rbtnMascAdd);
        rbtnFem = (RadioButton) findViewById(R.id.rbtnFemAdd);
        listaCarrera= (Spinner) findViewById(R.id.listCarreraAdd);
        btnAgregar = (Button) findViewById(R.id.btnGuardarEstudiante);
        btnAgregar.setBackgroundColor(Color.DKGRAY);
        btnAgregar.setTextColor(Color.WHITE);
        dbconeccion = new SQLControlador(this);
        dbconeccion.abrirBaseDeDatos();
        btnAgregar.setOnClickListener(this);

        final String[] datos =
                new String[]{"Ingenieria de Sistemas",
                        "Ingenieria Industrial",
                        "Ingenieria Civil","Arquitectura"};

        ArrayAdapter<String> adaptador =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, datos);

        listaCarrera.setAdapter(adaptador);
    }

    @Override
    public void onClick(View v) {
        Context context=getApplicationContext();
        String nombreAdd = nombre.getText().toString();
        String apellidoAdd = apellido.getText().toString();
        String carnetAdd = carnet.getText().toString();
        String carreraAdd = listaCarrera.getSelectedItem().toString();
        String sexoAdd;
        if(rbtnMasc.isChecked()) {
            sexoAdd="Masculino";
        }else if(rbtnFem.isChecked()) {
            sexoAdd="Femenino";
        }else{
            sexoAdd="";
        }

        if(nombreAdd.isEmpty() || nombreAdd.trim().length()<1
                || apellidoAdd.isEmpty() || apellidoAdd.trim().length()<1
                || carnetAdd.isEmpty() || carnetAdd.trim().length()<1
                || carreraAdd.isEmpty() || carreraAdd.trim().length()<1
                || sexoAdd.isEmpty() || sexoAdd.trim().length()<1){
            Toast.makeText(context, "Favor llenar todos los campos", Toast.LENGTH_SHORT).show();

        }else{
            dbconeccion.insertarDatos(nombreAdd, apellidoAdd, sexoAdd, carnetAdd, carreraAdd);
            Toast.makeText(context, "Se ha agregado el registro con exito", Toast.LENGTH_SHORT).show();
            Intent main = new Intent(AgregarEstudiante.this, MainActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(main);
        }


        }

}
