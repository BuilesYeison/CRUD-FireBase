package com.example.crudfirebase.Activities.Activities.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.crudfirebase.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {//creamos este metodo para cargar el menu que creamos anteriormente
        getMenuInflater().inflate(R.menu.menu_main, menu);//le pasamos el diseño de nuestro menu
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {//cuando le damos click a un item del menu
        switch(item.getItemId()){
            case R.id.iconAdd://cuando le de click al icono de agregar

                break;


            case R.id.iconDelete:

                break;

            case R.id.iconSave:

                break;
        }


        return true;
    }
}
