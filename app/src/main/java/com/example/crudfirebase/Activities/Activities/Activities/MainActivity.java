package com.example.crudfirebase.Activities.Activities.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.crudfirebase.Activities.Activities.Activities.Models.Persona;
import com.example.crudfirebase.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    EditText etNombres, etApellidos, etCorreo, etPassword;
    ListView lvPersonas;//crear los objetos equivalentes al diseño
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


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
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);//inicializamos firebase app
        firebaseDatabase = FirebaseDatabase.getInstance();
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

        switch(item.getItemId()){
            case R.id.iconAdd://cuando le de click al icono de agregar
                if(validacion(sNombres, sApellidos, sCorreo, sPassword) == true){//creo un metodo donde le paso todos los strings, este metodo me verifica si estan vacios o llenos
                    toastMessage("Informacion agregada");//mostramos en un toast

                    Persona persona = new Persona();//creamos una instancia de la clase modelo persona
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

                break;

            case R.id.iconSave:

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
