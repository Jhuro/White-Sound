package co.edu.unipiloto.whitesound.adaptadores;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import co.edu.unipiloto.whitesound.R;
import co.edu.unipiloto.whitesound.clases.Partitura;

public class AdaptadorListaPartitura extends BaseAdapter {

    private final Context context;
    private final List<Partitura> lst;

    public AdaptadorListaPartitura(Context context, List<Partitura> lst) {
        this.context = context;
        this.lst = lst;
    }

    @Override
    public int getCount() {
        return lst.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        TextView titulo, autor;

        Partitura partitura = lst.get(i);

        if(view==null) {
            view = LayoutInflater.from(context).inflate(R.layout.listview_partitura, null);
        }

        titulo = view.findViewById(R.id.lp_tv_titulo_partitura);
        autor = view.findViewById(R.id.lp_tv_autor_partitura);

        titulo.setText(partitura.getTitulo());
        autor.setText(partitura.getAutor());

        return view;
    }
}
