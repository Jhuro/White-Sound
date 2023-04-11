package co.edu.unipiloto.whitesound.clases;

import java.io.Serializable;

public class Partitura implements Serializable {

    private String titulo;
    private String autor;
    private ListaDE notas;

    public Partitura (String titulo, String autor){
        this.titulo = titulo;
        this.autor = autor;
        notas = new ListaDE();
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public ListaDE getNotas() {
        return notas;
    }

    public void setNotas(ListaDE notas) {
        this.notas = notas;
    }
}
