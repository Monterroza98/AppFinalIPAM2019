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

import java.io.Serializable;

public class EditarEstudiante extends AppCompatActivity implements View.OnClickListener, Serializable {

    EditText edtID, edtNombre, edtApellido, edtCarnet;
    RadioButton rbtnMasc, rbtnFem;
    Spinner listaCarreraEdt;
    Button btnEdt;

    long id;

    SQLControlador dbcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_estudiante);

        dbcon = new SQLControlador(this);
        dbcon.abrirBaseDeDatos();

        edtID = (EditText) findViewById(R.id.edtID);
        edtNombre = (EditText) findViewById(R.id.edtNombre);
        edtApellido = (EditText) findViewById(R.id.edtApellido);
        edtCarnet = (EditText) findViewById(R.id.edtCarnet);
        rbtnMasc = (RadioButton) findViewById(R.id.rbtnMascEdt);
        rbtnFem = (RadioButton) findViewById(R.id.rbtnFemEdt);
        listaCarreraEdt = (Spinner) findViewById(R.id.listCarreraEdt);
        btnEdt = (Button) findViewById(R.id.btnEditarEstudiante);
        btnEdt.setBackgroundColor(Color.DKGRAY);
        btnEdt.setTextColor(Color.WHITE);


        Intent i = getIntent();
        Estudiante edit;
        edit= (Estudiante) getIntent().getSerializableExtra("edit");

        int valorID = edit.getId();
        String valorNombre = edit.getNombre();
        String valorApellido =edit.getApellido();
        String valorSexo =edit.getSexo();
        String valorCarnet =edit.getCarnet();
        String valorCarrera = edit.getCarrera();

        int posicionCarrera=0;
        id=valorID;

        final String[] datos =
                new String[]{"Ingenieria de Sistemas",
                        "Ingenieria Industrial",
                        "Ingenieria Civil","Arquitectura"};

        ArrayAdapter<String> adaptador =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, datos);

        listaCarreraEdt.setAdapter(adaptador);

        for(int j=0;j< datos.length; j++){
            if(datos[j].equals(valorCarrera)){
                posicionCarrera=j;
            }
        }

        edtID.setText(String.valueOf(valorID));
        edtNombre.setText(valorNombre);
        edtApellido.setText(valorApellido);
        edtCarnet.setText(valorCarnet);
        if(valorSexo.equals("Masculino")){
            rbtnMasc.setChecked(true);
        }else{
            rbtnFem.setChecked(true);
        }
        listaCarreraEdt.setSelection(posicionCarrera);

        btnEdt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
                String nombre = edtNombre.getText().toString();
                String apellido= edtApellido.getText().toString();
                String carnet= edtCarnet.getText().toString();
                String sexo;
                if(rbtnMasc.isChecked()) {
                    sexo= "Masculino";
                }else if(rbtnFem.isChecked()){
                    sexo= "Femenino";
                }else{
                    sexo="";
                }
                String carrera= listaCarreraEdt.getSelectedItem().toString();
                Context context=getApplicationContext();

        if(nombre.isEmpty() || nombre.trim().length()<1){
            Toast.makeText(context, "Favor llenar el nombre", Toast.LENGTH_SHORT).show();
            edtNombre.setError("Vacio");
        }else if(apellido.isEmpty() || apellido.trim().length()<1){
            Toast.makeText(context, "Favor llenar el apellido", Toast.LENGTH_SHORT).show();
            edtApellido.setError("Vacio");
        }else if(carnet.isEmpty() || carnet.trim().length()<1){
            Toast.makeText(context, "Favor llenar el carnet", Toast.LENGTH_SHORT).show();
            edtCarnet.setError("Vacio");
        }else if(carrera.isEmpty() || carrera.trim().length()<1){
            Toast.makeText(context, "Favor elegir carrera", Toast.LENGTH_SHORT).show();
        }else if(sexo.isEmpty() || sexo.trim().length()<1){
            Toast.makeText(context, "Favor elegir sexo", Toast.LENGTH_SHORT).show();
        }else {
            dbcon.actualizarDatos(id, nombre, apellido, sexo, carnet, carrera);
            Toast.makeText(context, "Se ha editado el registro con id: " + id, Toast.LENGTH_SHORT).show();
            this.returnHome();
        }
        }

    public void returnHome() {

        Intent home_intent = new Intent(getApplicationContext(),
                MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(home_intent);
    }
}
