package co.edu.unipiloto.whitesound.clases;

import java.io.Serializable;

public class Nota implements Serializable{
    private boolean silencio;
    private String altura;
    private String duracion;
    private String alteracion;

    public Nota(String altura, String duracion) {
        silencio = false;
        this.altura = altura;
        this.duracion = duracion;
    }

    public Nota(String duracion) {
        silencio = true;
        this.duracion = duracion;
    }

    public boolean isSilencio() {
        return silencio;
    }

    public void setSilencio(boolean silencio) {
        this.silencio = silencio;
    }

    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getAlteracion() {
        return alteracion;
    }

    public void setAlteracion(String alteracion) {
        this.alteracion = alteracion;
    }

    @Override
    public String toString() {

        String salidaString;
        if(isSilencio()){
            salidaString = "Silencio " + duracion;
        }else{
            salidaString = altura;
            if(alteracion != null){
                salidaString += " " + alteracion;
            }
            salidaString += " " + duracion;
        }
        return salidaString;

    }
}
