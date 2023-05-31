package co.edu.unipiloto.whitesound.clases;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Iterator;

public class ListaDE implements Iterable, Serializable {

    private Node first;
    private Node last;
    private int size;

    public ListaDE() {
        this.first = null;
        this.last = null;
        size = 0;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public void agregarNotaEnPosicion(Nota nota, int index) {
        Node newNode = new Node(nota);
        if (first == null) {
            first = newNode;
            last = newNode;
        } else if (index == 0) {
            newNode.next = first;
            first.prev = newNode;
            first = newNode;
        } else {
            Node current = first;
            int i = 0;
            while (i < index - 1 && current.next != null) {
                current = current.next;
                i++;
            }
            if (current.next == null) {
                current.next = newNode;
                newNode.prev = current;
                last = newNode;
            } else {
                newNode.prev = current;
                newNode.next = current.next;
                current.next.prev = newNode;
                current.next = newNode;
            }
        }
        size++;
    }

    public void cambiarNotaEnPosicion(Nota nota, int index) {

        Node current = first;
        int i = 0;
        while (i < index && current.next != null) {
            current = current.next;
            i++;
        }
        current.nota = nota;
    }

    public void eliminarNotaEnPosicion(int index) {
        if (first == null || index < 0) {
            return;
        }
        if (index == 0) {
            first = first.next;
            if (first == null) {
                last = null;
            } else {
                first.prev = null;
            }
            size--;
            return;
        }
        Node current = first;
        int i = 0;
        while (i < index && current != null) {
            current = current.next;
            i++;
        }
        if (current == null) {
            return;
        }
        if (current == last) {
            last = last.prev;
            last.next = null;
            size--;
            return;
        }
        current.prev.next = current.next;
        current.next.prev = current.prev;
        size--;
    }

    public Nota obtenerNotaEnPosicion(int index) {
        Node current = first;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        return current.nota;
    }

    @NonNull
    @Override
    public Iterator iterator() {
        return new ListaDEIterator();
    }
    private static class Node implements Serializable{
        Nota nota;
        Node prev;
        Node next;

        public Node(Nota nota) {
            this.nota = nota;
            this.prev = null;
            this.next = null;
        }
    }

    private class ListaDEIterator implements Iterator, Serializable{

        private Node current;

        public ListaDEIterator(){
            current = first;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Object next() {
            if(hasNext()){
                Nota nota = current.nota;
                current = current.next;
                return nota;
            }
            return null;
        }
    }
}