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
    Context context;
    List<String> lst;

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
            view = LayoutInflater.from(context).inflate(R.layout.listview_silencio_musical, null);
        }

        TextView lsm_tv_nombre_silencio = (TextView) view.findViewById(R.id.lsm_tv_nombre_silencio);
        ImageView lsm_iv_icono_silencio = (ImageView) view.findViewById(R.id.lsm_iv_icono_silencio);

        lsm_tv_nombre_silencio.setText(nombreSilencio);

        switch(nombreSilencio){
            case "Silencio de redonda":
                lsm_iv_icono_silencio.setImageResource(R.drawable.ic_silencio_redonda);
                break;
            case "Silencio de blanca":
                lsm_iv_icono_silencio.setImageResource(R.drawable.ic_silencio_blanca);
                break;
            case "Silencio de negra":
                lsm_iv_icono_silencio.setImageResource(R.drawable.ic_silencio_negra);
                break;
            case "Silencio de corchea":
                lsm_iv_icono_silencio.setImageResource(R.drawable.ic_silencio_corchea);
                break;
            case "Silencio de semicorchea":
                lsm_iv_icono_silencio.setImageResource(R.drawable.ic_silencio_semicorchea);
                break;
            case "Silencio de fusa":
                lsm_iv_icono_silencio.setImageResource(R.drawable.ic_silencio_fusa);
                break;
            case "Silencio de semifusa":
                lsm_iv_icono_silencio.setImageResource(R.drawable.ic_silencio_semifusa);
                break;
            case "Silencio de garrapatea":
                lsm_iv_icono_silencio.setImageResource(R.drawable.ic_silencio_garrapatea);
        }

        return view;
    }
}
