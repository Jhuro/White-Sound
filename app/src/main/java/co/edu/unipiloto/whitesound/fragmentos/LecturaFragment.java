package co.edu.unipiloto.whitesound.fragmentos;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import co.edu.unipiloto.whitesound.R;
import co.edu.unipiloto.whitesound.actividades.EditarPartituraActivity;
import co.edu.unipiloto.whitesound.clases.ListaDE;
import co.edu.unipiloto.whitesound.clases.Nota;
import co.edu.unipiloto.whitesound.clases.Partitura;


public class LecturaFragment extends Fragment {

    private ImageButton fl_imgbtn_desplazar_izq, fl_imgbtn_desplazar_der, fl_imgbtn_reproducir;
    private TextView fl_tv_nota;
    private ListaDE partituraLDE;
    private int partituraIndex;
    private boolean reproduccion, solfeo;
    private List<String> altura, figurasMusicales;

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
        loadPreferences();
        if (savedInstanceState != null){
            partituraIndex = savedInstanceState.getInt("partituraIndex");

            if (partituraLDE.isEmpty()) {
                fl_tv_nota.setText(R.string.fl_tv_partitura_vacia);
            } else {
                Nota nota = partituraLDE.obtenerNotaEnPosicion(partituraIndex);
                fl_tv_nota.setText(nota.toString());
            }

            reproduccion = savedInstanceState.getBoolean("reproduccion");

            if(reproduccion){
                reproducirPartitura();
            }
        }
    }

    private void loadPreferences(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        solfeo = sharedPreferences.getBoolean("solfeo",false);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("partituraIndex", partituraIndex);
        outState.putBoolean("reproduccion", reproduccion);
    }

    public void initViews(View view) {

        final NavController navController = Navigation.findNavController(view);
        ((EditarPartituraActivity)getActivity()).cambiarTitulo("Lectura");
        Partitura partitura = ((EditarPartituraActivity) getActivity()).getPartituraTemp();

        TextView fl_tv_titulo = view.findViewById(R.id.fl_tv_titulo);
        TextView fl_tv_autor = view.findViewById(R.id.fl_tv_autor);
        fl_imgbtn_desplazar_izq = view.findViewById(R.id.fl_imgbtn_desplazar_izq);
        fl_imgbtn_desplazar_der = view.findViewById(R.id.fl_imgbtn_desplazar_der);
        ImageButton fl_imgbtn_modo_edicion = view.findViewById(R.id.fe_imgbtn_modo_edicion);
        fl_imgbtn_reproducir = view.findViewById(R.id.fl_imgbtn_reproducir);
        ImageButton fl_imgbtn_pausar = view.findViewById(R.id.fl_imgbtn_pausar);
        ImageButton fl_imgbtn_reiniciar = view.findViewById(R.id.fl_imgbtn_reiniciar);

        fl_tv_nota = view.findViewById(R.id.fl_tv_nota);

        reproduccion = false;
        partituraIndex = 0;

        fl_tv_titulo.setText(partitura.getTitulo());
        fl_tv_autor.setText(partitura.getAutor());

        partituraLDE = partitura.getNotas();
        initAlturas();
        initFiguras();

        if (partituraLDE.isEmpty()) {
            fl_tv_nota.setText(R.string.fl_tv_partitura_vacia);
        } else {
            Nota nota = partituraLDE.obtenerNotaEnPosicion(partituraIndex);
            int alturaIdx = 12;
            if(solfeo){
                alturaIdx = 17;
            }
            if (!nota.isSilencio()) {
                alturaIdx = altura.indexOf(nota.getAltura());
                if (nota.getAlteracion() != null) {
                    if (nota.getAlteracion().equals("Sostenido")) {
                        alturaIdx += 7 - (alturaIdx / 3);
                        if(solfeo){
                            alturaIdx+=5;
                        }
                    } else if (nota.getAlteracion().equals("Bemol")) {
                        alturaIdx += 6 - (alturaIdx / 4);
                    }
                }
            }
            fl_tv_nota.setText(nota.toString());
            ((EditarPartituraActivity) getActivity()).reproducirNota(
                    alturaIdx,
                    figurasMusicales.indexOf(nota.getDuracion()));
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

        fl_imgbtn_reproducir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reproducirPartitura();
            }
        });

        fl_imgbtn_pausar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pausarPartitura();
            }
        });

        fl_imgbtn_reiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reiniciarPartitura();
            }
        });
    }

    public void desplazarIzquierda() {
        if (partituraIndex > 0) {
            partituraIndex--;
        }
        if (!partituraLDE.isEmpty()) {
            Nota nota = partituraLDE.obtenerNotaEnPosicion(partituraIndex);
            int alturaIdx = 12;
            if(solfeo){
                alturaIdx = 17;
            }
            if (!nota.isSilencio()) {
                alturaIdx = altura.indexOf(nota.getAltura());
                if (nota.getAlteracion() != null) {
                    if (nota.getAlteracion().equals("Sostenido")) {
                        alturaIdx += 7 - (alturaIdx / 3);
                        if(solfeo){
                            alturaIdx+=5;
                        }
                    } else if (nota.getAlteracion().equals("Bemol")) {
                        alturaIdx += 6 - (alturaIdx / 4);
                    }
                }
            }

            fl_tv_nota.setText(nota.toString());
            ((EditarPartituraActivity) getActivity()).reproducirNota(
                    alturaIdx,
                    figurasMusicales.indexOf(nota.getDuracion()));
        }
    }

    public void desplazarDerecha() {
        if (partituraIndex < partituraLDE.getSize() - 1) {
            partituraIndex++;
        }
        if (!partituraLDE.isEmpty()) {
            Nota nota = partituraLDE.obtenerNotaEnPosicion(partituraIndex);
            int alturaIdx = 12;
            if(solfeo){
                alturaIdx = 17;
            }
            if (!nota.isSilencio()) {
                alturaIdx = altura.indexOf(nota.getAltura());
                if (nota.getAlteracion() != null) {
                    if (nota.getAlteracion().equals("Sostenido")) {
                        alturaIdx += 7 - (alturaIdx / 3);
                        if(solfeo){
                            alturaIdx+=5;
                        }
                    } else if (nota.getAlteracion().equals("Bemol")) {
                        alturaIdx += 6 - (alturaIdx / 4);
                    }
                }
            }

            fl_tv_nota.setText(nota.toString());
            ((EditarPartituraActivity) getActivity()).reproducirNota(
                    alturaIdx,
                    figurasMusicales.indexOf(nota.getDuracion()));
        }

        if (partituraIndex == partituraLDE.getSize() - 1) {
            if(reproduccion) {
                reproduccion = false;
                fl_imgbtn_reproducir.setEnabled(true);
                fl_imgbtn_desplazar_izq.setEnabled(true);
                fl_imgbtn_desplazar_der.setEnabled(true);
            }
            Toast.makeText(getContext(), "Fin de la partitura", Toast.LENGTH_SHORT).show();
        }
    }

    public void reproducirPartitura() {

        if (!partituraLDE.isEmpty()) {
            reproduccion = true;
            fl_imgbtn_reproducir.setEnabled(false);
            fl_imgbtn_desplazar_der.setEnabled(false);
            fl_imgbtn_desplazar_izq.setEnabled(false);

            Nota nota = partituraLDE.obtenerNotaEnPosicion(partituraIndex);
            int alturaIdx = 12;
            if(solfeo){
                alturaIdx = 17;
            }
            if (!nota.isSilencio()) {
                alturaIdx = altura.indexOf(nota.getAltura());
                if (nota.getAlteracion() != null) {
                    if (nota.getAlteracion().equals("Sostenido")) {
                        alturaIdx += 7 - (alturaIdx / 3);
                        if(solfeo){
                            alturaIdx+=5;
                        }
                    } else if (nota.getAlteracion().equals("Bemol")) {
                        alturaIdx += 6 - (alturaIdx / 4);
                    }
                }
            }

            fl_tv_nota.setText(nota.toString());
            ((EditarPartituraActivity) getActivity()).reproducirNota(
                    alturaIdx,
                    figurasMusicales.indexOf(nota.getDuracion()));

            if (partituraIndex == partituraLDE.getSize() - 1) {
                reproduccion = false;
                fl_imgbtn_reproducir.setEnabled(true);
                fl_imgbtn_desplazar_izq.setEnabled(true);
                fl_imgbtn_desplazar_der.setEnabled(true);
                Toast.makeText(getContext(), "Fin de la partitura", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void pausarPartitura(){
        ((EditarPartituraActivity) getActivity()).pausarReproduccion();
        reproduccion = false;
        fl_imgbtn_reproducir.setEnabled(true);
        fl_imgbtn_desplazar_izq.setEnabled(true);
        fl_imgbtn_desplazar_der.setEnabled(true);

        Toast.makeText(getContext(), "Reproducción pausada", Toast.LENGTH_SHORT).show();
    }

    public void reiniciarPartitura(){
        if(!partituraLDE.isEmpty()) {
            ((EditarPartituraActivity) getActivity()).pausarReproduccion();
            reproduccion = false;
            fl_imgbtn_reproducir.setEnabled(true);
            fl_imgbtn_desplazar_izq.setEnabled(true);
            fl_imgbtn_desplazar_der.setEnabled(true);

            partituraIndex = 0;

            Nota nota = partituraLDE.obtenerNotaEnPosicion(partituraIndex);
            fl_tv_nota.setText(nota.toString());

            Toast.makeText(getContext(), "Partitura reiniciada", Toast.LENGTH_SHORT).show();
        }
    }

    private void initAlturas() {

        altura = new ArrayList<>();

        altura.add("Do");
        altura.add("Re");
        altura.add("Mi");
        altura.add("Fa");
        altura.add("Sol");
        altura.add("La");
        altura.add("Si");
    }

    private void initFiguras() {

        figurasMusicales = new ArrayList<>();

        figurasMusicales.add("Redonda");
        figurasMusicales.add("Blanca");
        figurasMusicales.add("Negra");
        figurasMusicales.add("Corchea");
        figurasMusicales.add("Semicorchea");
        figurasMusicales.add("Fusa");
        figurasMusicales.add("Semifusa");
        figurasMusicales.add("Garrapatea");
    }

    public boolean getReproduccion() {
        return reproduccion;
    }

    @Override
    public void onResume() {
        super.onResume();

        loadPreferences();
    }
}