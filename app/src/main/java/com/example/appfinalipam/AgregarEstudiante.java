package com.example.appfinalipam;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.RadioButton;

public class AgregarEstudiante extends Activity implements View.OnClickListener {

    EditText nombre, apellido, carnet;
    RadioButton rbtnFem, rbtnMasc;
    ExpandableListView listaCarrera;
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
        listaCarrera= (ExpandableListView) findViewById(R.id.listCarreraAdd);
        btnAgregar = (Button) findViewById(R.id.btnGuardarEstudiante);
        dbconeccion = new SQLControlador(this);
        dbconeccion.abrirBaseDeDatos();
        btnAgregar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String nombreAdd = nombre.getText().toString();
        String apellidoAdd = apellido.getText().toString();
        String carnetAdd = carnet.getText().toString();
        String carreraAdd = listaCarrera.getSelectedItem().toString();
        String sexoAdd;
        if(rbtnMasc.isChecked()) {
            sexoAdd="Masculino";
        }else {
            sexoAdd="Femenino";
        }

        dbconeccion.insertarDatos(nombreAdd, apellidoAdd, sexoAdd, carnetAdd, carreraAdd);
        Intent main = new Intent(AgregarEstudiante.this, MainActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(main);
        }

}
