package co.edu.unipiloto.whitesound.clases;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;

public class Nota implements Serializable{
    private final boolean silencio;
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
    public boolean equals(@Nullable Object obj) {

        Nota notaComparada = (Nota) obj;
        if(altura != null){
            if(alteracion != null){
                if(notaComparada.getAlteracion() == null){
                    return false;
                }else if(alteracion.equals(notaComparada.getAlteracion())){
                    if(!altura.equals(notaComparada.getAltura())){
                        return false;
                    }
                }else if(alteracion.equals("Sostenido")) {
                    switch (altura){
                        case "Do":
                            if(!notaComparada.getAltura().equals("Re")){
                                return false;
                            }
                            break;
                        case "Re":
                            if(!notaComparada.getAltura().equals("Mi")){
                                return false;
                            }
                            break;
                        case "Fa":
                            if(!notaComparada.getAltura().equals("Sol")){
                                return false;
                            }
                            break;
                        case "Sol":
                            if(!notaComparada.getAltura().equals("La")){
                                return false;
                            }
                            break;
                        case "La":
                            if(!notaComparada.getAltura().equals("Si")){
                                return false;
                            }
                    }
                }else {
                    switch (altura){
                        case "Re":
                            if(!notaComparada.getAltura().equals("Do")){
                                return false;
                            }
                            break;
                        case "Mi":
                            if(!notaComparada.getAltura().equals("Re")){
                                return false;
                            }
                            break;
                        case "Sol":
                            if(!notaComparada.getAltura().equals("Fa")){
                                return false;
                            }
                            break;
                        case "La":
                            if(!notaComparada.getAltura().equals("Sol")){
                                return false;
                            }
                            break;
                        case "Si":
                            if(!notaComparada.getAltura().equals("La")){
                                return false;
                            }
                    }
                }
            } else if (notaComparada.getAlteracion() != null) {
                return false;
            } else if (!altura.equals(notaComparada.getAltura())){
                return false;
            }
        }

        if(duracion != null){
            return duracion.equals(notaComparada.getDuracion());
        }

        return true;
    }

    @NonNull
    @Override
    public String toString() {

        String salidaString="";
        if(isSilencio()){
            salidaString = "Silencio " + duracion;
        }else{
            if(altura != null){
                salidaString += altura;
            }
            if(alteracion != null){
                salidaString += " " + alteracion;
            }
            if(duracion != null){
                salidaString += " " + duracion;
            }
        }
        return salidaString.trim();

    }
}
