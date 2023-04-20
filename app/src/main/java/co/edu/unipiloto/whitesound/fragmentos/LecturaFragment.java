package co.edu.unipiloto.whitesound.fragmentos;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import co.edu.unipiloto.whitesound.R;
import co.edu.unipiloto.whitesound.actividades.EditarPartituraActivity;
import co.edu.unipiloto.whitesound.clases.ListaDE;
import co.edu.unipiloto.whitesound.clases.Partitura;


public class LecturaFragment extends Fragment {

   private ImageButton fl_imgbtn_desplazar_izq, fl_imgbtn_desplazar_der, fl_imgbtn_modo_edicion;
   private TextView fl_tv_titulo, fl_tv_autor, fl_tv_nota;
   private Partitura partitura;
   private ListaDE partituraLDE;
   private int partituraIndex;

    public LecturaFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lectura, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
    }

    public void initViews(View view){

        final NavController navController = Navigation.findNavController(view);


        partitura = ((EditarPartituraActivity)getActivity()).getPartituraTemp();

        fl_tv_titulo = view.findViewById(R.id.fl_tv_titulo);
        fl_tv_autor = view.findViewById(R.id.fl_tv_autor);
        fl_imgbtn_desplazar_izq = view.findViewById(R.id.fl_imgbtn_desplazar_izq);
        fl_imgbtn_desplazar_der = view.findViewById(R.id.fl_imgbtn_desplazar_der);
        fl_imgbtn_modo_edicion = view.findViewById(R.id.fe_imgbtn_modo_edicion);
        fl_tv_nota = view.findViewById(R.id.fl_tv_nota);

        partituraIndex = 0;

        fl_tv_titulo.setText(partitura.getTitulo());
        fl_tv_autor.setText(partitura.getAutor());

        partituraLDE = partitura.getNotas();

        if(partituraLDE.isEmpty()){
            fl_tv_nota.setText("Partitura vacía");
        }else{
            fl_tv_nota.setText(partituraLDE.obtenerNotaEnPosicion(partituraIndex).toString());
        }

        fl_imgbtn_desplazar_izq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                desplazarIzquierda();
            }
        });

        fl_imgbtn_desplazar_der.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                desplazarDerecha();
            }
        });

        fl_imgbtn_modo_edicion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.edicionFragment);
            }
        });
    }

    public void desplazarIzquierda(){
        if(partituraIndex > 0) {
            partituraIndex--;
        }
        fl_tv_nota.setText(partituraLDE.obtenerNotaEnPosicion(partituraIndex).toString());
    }

    public void desplazarDerecha(){
        if(partituraIndex < partituraLDE.getSize()-1) {
            partituraIndex++;
        }
        fl_tv_nota.setText(partituraLDE.obtenerNotaEnPosicion(partituraIndex).toString());
    }
}