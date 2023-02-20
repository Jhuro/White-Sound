package co.edu.unipiloto.whitesound;

public class Partitura {

    public String nombre;
    public String autor;

    public Partitura(String nombre, String autor) {
        this.nombre = nombre;
        this.autor = autor;
    }



    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }
}
