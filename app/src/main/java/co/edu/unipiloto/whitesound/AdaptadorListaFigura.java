package co.edu.unipiloto.whitesound;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AdaptadorListaFigura extends BaseAdapter {
    Context context;
    List<String> lst;

    public AdaptadorListaFigura(Context context, List<String> lst) {
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

        String nombreFigura = (String) lst.get(i);

        if(view==null) {
            view = LayoutInflater.from(context).inflate(R.layout.listview_figura_musical, null);
        }

        TextView lfm_tv_nombre_figura = (TextView) view.findViewById(R.id.lfm_tv_nombre_figura);
        ImageView lfm_iv_icono_figura = (ImageView) view.findViewById(R.id.lfm_iv_icono_figura);

        lfm_tv_nombre_figura.setText(nombreFigura);

        switch(nombreFigura){
            case "Redonda":
                lfm_iv_icono_figura.setImageResource(R.drawable.ic_redonda);
                break;
            case "Blanca":
                lfm_iv_icono_figura.setImageResource(R.drawable.ic_blanca);
                break;
            case "Negra":
                lfm_iv_icono_figura.setImageResource(R.drawable.ic_negra);
                break;
            case "Corchea":
                lfm_iv_icono_figura.setImageResource(R.drawable.ic_corchea);
                break;
            case "Semicorchea":
                lfm_iv_icono_figura.setImageResource(R.drawable.ic_semicorchea);
                break;
            case "Fusa":
                lfm_iv_icono_figura.setImageResource(R.drawable.ic_fusa);
                break;
            case "Semifusa":
                lfm_iv_icono_figura.setImageResource(R.drawable.ic_semifusa);
                break;
            case "Garrapatea":
                lfm_iv_icono_figura.setImageResource(R.drawable.ic_garrapatea);
        }

        return view;
    }
}
