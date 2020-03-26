package com.example.crudfirebase.Activities.Activities.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crudfirebase.Activities.Activities.Activities.Models.Persona;
import com.example.crudfirebase.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private List<Persona> listPersona = new ArrayList<Persona>();
    ArrayAdapter<Persona> arrayAdapterPersona;

    EditText etNombres, etApellidos, etCorreo, etPassword;
    ListView lvPersonas;//crear los objetos equivalentes al diseño
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Persona personaSeleccionada;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNombres = (EditText)findViewById(R.id.etNombresPersona);//conectar los objetos de diseño con estos logicos
        etApellidos = (EditText)findViewById(R.id.etApellidosPersona);
        etCorreo = (EditText)findViewById(R.id.etCorreoPersona);
        etPassword = (EditText)findViewById(R.id.etPasswordPersona);
        lvPersonas = (ListView)findViewById(R.id.lvDatosPersonas);
        inicializarFirebase();
        
        listarDatos();

        lvPersonas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                personaSeleccionada = (Persona) parent.getItemAtPosition(position);//cuando el usuario presioné en un item del listview obtenemos su posicion y los datos de esa persona  que presionó
                etNombres.setText(personaSeleccionada.getNombre());//ponemos en nuestros campos edittext los datos de la persona que el usuario presionó
                etApellidos.setText(personaSeleccionada.getApellidos());
                etCorreo.setText(personaSeleccionada.getCorreo());
                etPassword.setText(personaSeleccionada.getPassword());
            }
        });
    }

    private void listarDatos() {
        databaseReference.child("Persona").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listPersona.clear();//limpiamos por si tiene datos
                for (DataSnapshot objSnapshot: dataSnapshot.getChildren()){
                    Persona persona = objSnapshot.getValue(Persona.class);//creamos un objeto de la clase modelo persona y obtenemos los datos de la clase persona
                    listPersona.add(persona);//agregamos los datos en la lista

                    arrayAdapterPersona = new ArrayAdapter<Persona>(getApplicationContext(),R.layout.listview_style,listPersona);
                    lvPersonas.setAdapter(arrayAdapterPersona);

                    //al abrir la app vemos que nos lista las personas por el nombre pero en realidad allí estan todos los datos de la persona solo que en la clase
                    //modelo al final en el metodo toString le decimos que nos retorne el nombre por esto lista por nombres si pusieramos por ejemplo que retornará
                    //el correo listaria por correo y no por nombre
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);//inicializamos firebase app
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {//creamos este metodo para cargar el menu que creamos anteriormente
        getMenuInflater().inflate(R.menu.menu_main, menu);//le pasamos el diseño de nuestro menu
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {//cuando le damos click a un item del menu

        String sNombres, sApellidos, sCorreo, sPassword;
        sNombres = etNombres.getText().toString();
        sApellidos = etApellidos.getText().toString();
        sCorreo = etCorreo.getText().toString();
        sPassword = etPassword.getText().toString();
        Persona persona = new Persona();//creamos un objeto de la clase modelo persona para actualizar, insertar y eliminar

        switch(item.getItemId()){
            case R.id.iconAdd://cuando le de click al icono de agregar
                if(validacion(sNombres, sApellidos, sCorreo, sPassword) == true){//creo un metodo donde le paso todos los strings, este metodo me verifica si estan vacios o llenos
                    toastMessage("Informacion agregada");//mostramos en un toast

                    persona.setuId(UUID.randomUUID().toString());//agregamos a nuestro objeto una id random automatica
                    persona.setNombre(sNombres);//agregamos el resto de informacion a nuestro objeto
                    persona.setApellidos(sApellidos);
                    persona.setCorreo(sCorreo);
                    persona.setPassword(sPassword);
                    databaseReference.child("Persona").child(persona.getuId()).setValue(persona);//en la referencia de nuestra base de datos creamos un nodo llamado persona que tendra como principal la uId que será el nombre de la tabla y allí agregaremos todos los datos que tiene nuestro objeto persona

                    limpiarCampos();
                }else{
                    toastMessage("Porfavor valida todos los campos");
                }
                break;


            case R.id.iconDelete:
                persona.setuId(personaSeleccionada.getuId());//obtenemos el id de la persona seleccionada
                databaseReference.child("Persona").child(persona.getuId()).removeValue();//vamos a la tabla Persona en la base de datos y seleccionamos el nodo que tiene el id que seleccionó el usuario y removemos todos los datos
                limpiarCampos();
                break;

            case R.id.iconSave://actualizar datos
                persona.setuId(personaSeleccionada.getuId());//el id siempre será igual para poder actualizar correctamente
                persona.setNombre(etNombres.getText().toString().trim());//obtenemos el dato nuevo deledittext y lo actualizamos en la base de datos
                persona.setApellidos(etApellidos.getText().toString().trim());//el trim ignora los espacios en blanco
                persona.setCorreo(etCorreo.getText().toString().trim());
                persona.setPassword(etApellidos.getText().toString().trim());
                databaseReference.child("Persona").child(persona.getuId()).setValue(persona);//accedemos a nuestro nodo Persona y donde esta la id de la persona que vamos a actualizar pasamos los datos que estan en ese mismo objeto persona
                toastMessage("Actualizado correctamente");
                limpiarCampos();
                break;
        }


        return true;
    }

    private void limpiarCampos() {
        etPassword.setText("");
        etNombres.setText("");
        etCorreo.setText("");
        etApellidos.setText("");
    }

    private void toastMessage(String message) {//metodo para poner mensajes en un toast
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private boolean validacion(String sNombres, String sApellidos, String sCorreo, String sPassword) {//metodo que retorna un booleano y me dice si los campos estan vacios o llenos

        boolean validacion;
        if(sNombres.isEmpty() || sApellidos.isEmpty() || sCorreo.isEmpty() || sPassword.isEmpty()){//si estan vacios
            validacion = false;//devuelve un false
        }else{//sino
            validacion = true;//devuelve un true
        }

        return validacion;
    }
}
