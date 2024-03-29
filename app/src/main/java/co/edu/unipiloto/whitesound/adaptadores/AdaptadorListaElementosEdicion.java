package co.edu.unipiloto.whitesound.adaptadores;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import co.edu.unipiloto.whitesound.R;

public class AdaptadorListaElementosEdicion extends BaseAdapter {
    private final Context context;
    private final List<String> lst;

    public AdaptadorListaElementosEdicion(Context context, List<String> lst) {
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

        String nombreElemento = lst.get(i);

        if(view==null) {
            view = LayoutInflater.from(context).inflate(R.layout.listview_elementos_edicion, null);
        }

        TextView lee_tv_nombre = view.findViewById(R.id.lee_tv_nombre);
        ImageView lee_iv_icono = view.findViewById(R.id.lee_iv_icono);

        lee_tv_nombre.setText(nombreElemento);

        switch(nombreElemento){
            case "Redonda":
                lee_iv_icono.setImageResource(R.drawable.ic_redonda);
                break;
            case "Blanca":
                lee_iv_icono.setImageResource(R.drawable.ic_blanca);
                break;
            case "Negra":
                lee_iv_icono.setImageResource(R.drawable.ic_negra);
                break;
            case "Corchea":
                lee_iv_icono.setImageResource(R.drawable.ic_corchea);
                break;
            case "Semicorchea":
                lee_iv_icono.setImageResource(R.drawable.ic_semicorchea);
                break;
            case "Silencio de redonda":
                lee_iv_icono.setImageResource(R.drawable.ic_silencio_redonda);
                break;
            case "Silencio de blanca":
                lee_iv_icono.setImageResource(R.drawable.ic_silencio_blanca);
                break;
            case "Silencio de negra":
                lee_iv_icono.setImageResource(R.drawable.ic_silencio_negra);
                break;
            case "Silencio de corchea":
                lee_iv_icono.setImageResource(R.drawable.ic_silencio_corchea);
                break;
            case "Silencio de semicorchea":
                lee_iv_icono.setImageResource(R.drawable.ic_silencio_semicorchea);
                break;
            case "Sostenido":
                lee_iv_icono.setImageResource(R.drawable.ic_sostenido);
                break;
            case "Bemol":
                lee_iv_icono.setImageResource(R.drawable.ic_bemol);
                break;
            case "Sin alteración":
                lee_iv_icono.setImageResource(R.drawable.ic_sin_alteracion);
                break;
        }

        return view;
    }
}
