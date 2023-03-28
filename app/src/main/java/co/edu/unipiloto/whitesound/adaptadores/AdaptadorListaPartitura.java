package co.edu.unipiloto.whitesound.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import co.edu.unipiloto.whitesound.R;

public class AdaptadorListaPartitura extends BaseAdapter {

    private Context context;
    private List<String> lst;

    public AdaptadorListaPartitura(Context context, List<String> lst) {
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

        String partitura = lst.get(i);

        if(view==null) {
            view = LayoutInflater.from(context).inflate(R.layout.listview_partitura, null);
        }

        nombre = (TextView) view.findViewById(R.id.lp_tv_nombre_archivo_partitura);

        nombre.setText(partitura);

        return view;
    }
}
