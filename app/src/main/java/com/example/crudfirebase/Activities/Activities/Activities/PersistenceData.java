package com.example.crudfirebase.Activities.Activities.Activities;

import com.google.firebase.database.FirebaseDatabase;

public class PersistenceData extends android.app.Application {//esta herencia da privilegios a esta clase ahora hay que llamar esta clase en el manifest

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);//obtenemos la instancia de la base de datos que estamos utilizando y habilitamos la persistencia de datos que ofrece firebase
        //la persistencia de datos consiste en este caso en que podemos agregar informacion (una persona en este caso) sin internet y se agregará
        //en el listview pero no se pierde y cuando habilitemos el internet se subirá automaticamente la informacion
    }
}
