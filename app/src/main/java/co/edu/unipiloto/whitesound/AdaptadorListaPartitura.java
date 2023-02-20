package co.edu.unipiloto.whitesound;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class AdaptadorListaPartitura extends BaseAdapter {

    Context context;
    List<Partitura> lst;

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

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        TextView nombre;
        TextView autor;

        Partitura partitura = lst.get(i);

        if(view==null) {
            view = LayoutInflater.from(context).inflate(R.layout.listview_partitura, null);
        }

        nombre = (TextView) view.findViewById(R.id.lvp_tv_nombre_partitura);
        autor = (TextView) view.findViewById(R.id.lvp_tv_nombre_autor);

        nombre.setText(partitura.nombre);
        autor.setText(partitura.autor);

        return view;
    }
}
