package com.example.crudfirebase.Activities.Activities.Activities.Models;

public class Persona {//creamos esta clase modelo

    private String uId, Nombre, Apellidos, Correo, Password;//creamos las variables equivalentes a las que vamos a guardar en la base de datos

    public Persona() {//constructor
    }

    //getters and setters de todas las variables


    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String apellidos) {
        Apellidos = apellidos;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    @Override
    public String toString() {
        return Nombre;
    }
}
