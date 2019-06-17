package com.example.appfinalipam;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{

    Button btnAgregarMiembro;
    ListView lista;
    SQLControlador dbconeccion;
    TextView tvID, tvNombre, tvApellido, tvSexo, tvCarnet, tvCarrera;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbconeccion = new SQLControlador(this);
        dbconeccion.abrirBaseDeDatos();
        btnAgregarMiembro = (Button) findViewById(R.id.btnAgregarEstudiante);
        lista = (ListView) findViewById(R.id.listaEstudiantes);

        //acción del boton agregar miembro
        btnAgregarMiembro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iagregar = new Intent(MainActivity.this, AgregarEstudiante.class);
                startActivity(iagregar);
            }
        });

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

                String edtID = tvID.getText().toString();
                String edtNombre = tvNombre.getText().toString();
                String edtApellido = tvApellido.getText().toString();
                String edtSexo = tvSexo.getText().toString();
                String edtCarnet = tvCarnet.getText().toString();
                String edtCarrera = tvCarrera.getText().toString();


                Intent modify_intent = new Intent(getApplicationContext(), EditarEstudiante.class);
                modify_intent.putExtra("edtID", edtID);
                modify_intent.putExtra("edtNombre", edtNombre);
                modify_intent.putExtra("edtApellido", edtApellido);
                modify_intent.putExtra("edtSexo", edtSexo);
                modify_intent.putExtra("edtCarnet", edtCarnet);
                modify_intent.putExtra("edtCarrera", edtCarrera);
                startActivity(modify_intent);
            }
        });




    }
}
