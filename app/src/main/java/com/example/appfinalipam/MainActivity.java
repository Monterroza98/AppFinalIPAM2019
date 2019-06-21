package com.example.appfinalipam;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.io.Serializable;

public class MainActivity extends Activity implements Serializable {

    Button btnAgregarMiembro;
    ListView lista;
    SQLControlador dbconeccion;
    TextView tvID, tvNombre, tvApellido, tvSexo, tvCarnet, tvCarrera;
    final Estudiante editado= new Estudiante();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbconeccion = new SQLControlador(this);
        dbconeccion.abrirBaseDeDatos();
        btnAgregarMiembro = (Button) findViewById(R.id.btnAgregarEstudiante);
        btnAgregarMiembro.setBackgroundColor(Color.DKGRAY);
        btnAgregarMiembro.setTextColor(Color.WHITE);
        lista = (ListView) findViewById(R.id.listaEstudiantes);
        lista.setBackgroundColor(Color.LTGRAY);
//        tvID.setTextColor(Color.WHITE);
//        tvNombre.setTextColor(Color.WHITE);
//        tvApellido.setTextColor(Color.WHITE);
//        tvCarnet.setTextColor(Color.WHITE);

        agregar();

        llenarDatos();

        modificar();

        eliminar();

    }

    public void agregar(){
        //acción del boton agregar miembro
        btnAgregarMiembro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iagregar = new Intent(MainActivity.this, AgregarEstudiante.class);
                startActivity(iagregar);
            }
        });
    }

    public void llenarDatos(){
        // Tomar los datos desde la base de datos para poner en el cursor y después en el adapter
        Cursor cursor = dbconeccion.leerDatos();

        String[] from = new String[] {
                DBhelper.STUDENT_ID,
                DBhelper.STUDENT_NAME,
                DBhelper.STUDENT_LASTNAME,
                DBhelper.STUDENT_GENDER,
                DBhelper.STUDENT_CARNET,
                DBhelper.STUDENT_CAREER,
        };
        int[] to = new int[] {
                R.id.verID,
                R.id.verNombre,
                R.id.verApellido,
                R.id.verSexo,
                R.id.verCarnet,
                R.id.verCarrera
        };

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                MainActivity.this, R.layout.formato_fila, cursor, from, to);


        adapter.notifyDataSetChanged();
        lista.setAdapter(adapter);
    }

    public void modificar(){
        // acción cuando hacemos click en item para poder modificarlo o eliminarlo
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {

                tvID = (TextView) view.findViewById(R.id.verID);
                tvNombre = (TextView) view.findViewById(R.id.verNombre);
                tvApellido= (TextView) view.findViewById(R.id.verApellido);
                tvSexo= (TextView) view.findViewById(R.id.verSexo);
                tvCarnet= (TextView) view.findViewById(R.id.verCarnet);
                tvCarrera= (TextView) view.findViewById(R.id.verCarrera);

                String edtNombre = tvNombre.getText().toString();
                String edtApellido = tvApellido.getText().toString();
                String edtSexo = tvSexo.getText().toString();
                String edtCarnet = tvCarnet.getText().toString();
                String edtCarrera = tvCarrera.getText().toString();
                int idEDT= Integer.parseInt(tvID.getText().toString());

                Estudiante estudiante= new Estudiante(idEDT, edtNombre, edtApellido, edtSexo, edtCarnet, edtCarrera);
                editado.setId(idEDT);
                editado.setNombre(edtNombre);
                editado.setApellido(edtApellido);
                editado.setSexo(edtSexo);
                editado.setCarnet(edtCarnet);
                editado.setCarrera(edtCarrera);

                Intent modify_intent = new Intent(MainActivity.this, EditarEstudiante.class);
                modify_intent.putExtra("edit", estudiante);
                startActivity(modify_intent);
            }
        });
    }

    public void eliminar(){
        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                tvID = (TextView) view.findViewById(R.id.verID);
                final int idABorrar= Integer.parseInt(tvID.getText().toString());
                AlertDialog alerta= new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Eliminar Registro")
                        .setIcon(0).setMessage("Se eliminará el registro con id: "+ idABorrar)
                        .setNeutralButton(R.string.aceptar,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int i) {
                                        dbconeccion.borrarDatos(idABorrar);
                                        llenarDatos();
                                    }
                                }).setNegativeButton(R.string.cancelar,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {
                                    }
                                }
                        ).create();
                alerta.show();
                return true;
            }
        });
    }


}
