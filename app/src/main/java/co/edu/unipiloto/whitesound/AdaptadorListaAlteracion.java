package co.edu.unipiloto.whitesound;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AdaptadorListaAlteracion extends BaseAdapter {
    private Context context;
    private List<String> lst;

    public AdaptadorListaAlteracion(Context context, List<String> lst) {
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

        String nombreAlteracion = (String) lst.get(i);

        if(view==null) {
            view = LayoutInflater.from(context).inflate(R.layout.listview_elementos_edicion, null);
        }

        TextView lee_tv_nombre = (TextView) view.findViewById(R.id.lee_tv_nombre);
        ImageView lee_iv_icono = (ImageView) view.findViewById(R.id.lee_iv_icono);

        lee_tv_nombre.setText(nombreAlteracion);

        switch(nombreAlteracion){
            case "Sostenido":
                lee_iv_icono.setImageResource(R.drawable.ic_sostenido);
                break;
            case "Bemol":
                lee_iv_icono.setImageResource(R.drawable.ic_bemol);
                break;
        }

        return view;
    }
}
