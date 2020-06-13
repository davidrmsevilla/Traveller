package com.davidrm.traveller;

public class Actividad {

    private String actividad;
    private String fecha;
    private String hora;

    public Actividad(){

    }

    public Actividad(String actividad, String fecha, String hora){
        this.actividad = actividad;
        this.fecha = fecha;
        this.hora = hora;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
