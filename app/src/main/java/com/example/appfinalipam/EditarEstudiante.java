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

public class EditarEstudiante extends Activity implements View.OnClickListener {

    EditText edtID, edtNombre, edtApellido, edtCarnet;
    RadioButton rbtnMasc, rbtnFem;
    ExpandableListView listaCarreraEdt;
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
        listaCarreraEdt = (ExpandableListView) findViewById(R.id.listCarreraEdt);
        btnEdt = (Button) findViewById(R.id.btnEditarEstudiante);

        Intent i = getIntent();
        String valorID = i.getStringExtra("edtID");
        String valorNombre = i.getStringExtra("edtNombre");
        String valorApellido = i.getStringExtra("edtApellido");
        String valorSexo = i.getStringExtra("edtSexo");
        String valorCarnet = i.getStringExtra("edtCarnet");
        String valorCarrera = i.getStringExtra("edtCarrera");

        id= Long.parseLong(valorID);

        edtID.setText(valorID);
        edtNombre.setText(valorNombre);
        edtApellido.setText(valorApellido);
        edtCarnet.setText(valorCarnet);
        if(valorSexo.equals("Masculino")){
            rbtnMasc.setChecked(true);
        }else{
            rbtnFem.setChecked(true);
        }
        listaCarreraEdt.setSelection(0);

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
                }else {
                    sexo= "Femenino";
                }
                String carrera= listaCarreraEdt.getSelectedItem().toString();

                dbcon.actualizarDatos(id, nombre, apellido, sexo, carnet, carrera);
                this.returnHome();
        }

    public void returnHome() {

        Intent home_intent = new Intent(getApplicationContext(),
                MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(home_intent);
    }
}
