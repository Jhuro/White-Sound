package co.edu.unipiloto.whitesound;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AdaptadorListaElementosEdicion extends BaseAdapter {
    private Context context;
    private List<String> lst;

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

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        String nombreElemento = (String) lst.get(i);

        if(view==null) {
            view = LayoutInflater.from(context).inflate(R.layout.listview_elementos_edicion, null);
        }

        TextView lee_tv_nombre = (TextView) view.findViewById(R.id.lee_tv_nombre);
        ImageView lee_iv_icono = (ImageView) view.findViewById(R.id.lee_iv_icono);

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
            case "Fusa":
                lee_iv_icono.setImageResource(R.drawable.ic_fusa);
                break;
            case "Semifusa":
                lee_iv_icono.setImageResource(R.drawable.ic_semifusa);
                break;
            case "Garrapatea":
                lee_iv_icono.setImageResource(R.drawable.ic_garrapatea);
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
            case "Silencio de fusa":
                lee_iv_icono.setImageResource(R.drawable.ic_silencio_fusa);
                break;
            case "Silencio de semifusa":
                lee_iv_icono.setImageResource(R.drawable.ic_silencio_semifusa);
                break;
            case "Silencio de garrapatea":
                lee_iv_icono.setImageResource(R.drawable.ic_silencio_garrapatea);
                break;
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
