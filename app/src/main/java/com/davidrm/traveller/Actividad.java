package com.davidrm.traveller;

public class Actividad {

    private String actividad;
    private long fecha;

    public Actividad(){

    }

    public Actividad(String actividad, long fecha){
        this.actividad = actividad;
        this.fecha = fecha;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public long getFecha() {
        return fecha;
    }

    public void setFecha(long fecha) {
        this.fecha = fecha;
    }
}
