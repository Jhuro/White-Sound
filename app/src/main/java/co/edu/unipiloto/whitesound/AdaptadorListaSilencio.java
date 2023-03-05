package co.edu.unipiloto.whitesound;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AdaptadorListaSilencio extends BaseAdapter {
    private Context context;
    private List<String> lst;

    public AdaptadorListaSilencio(Context context, List<String> lst) {
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

        String nombreSilencio = (String) lst.get(i);

        if(view==null) {
            view = LayoutInflater.from(context).inflate(R.layout.listview_elementos_edicion, null);
        }

        TextView lee_tv_nombre = (TextView) view.findViewById(R.id.lee_tv_nombre);
        ImageView lee_iv_icono = (ImageView) view.findViewById(R.id.lee_iv_icono);

        lee_tv_nombre.setText(nombreSilencio);

        switch(nombreSilencio){
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
        }

        return view;
    }
}
