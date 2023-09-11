package co.edu.unipiloto.whitesound.fragmentos;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import co.edu.unipiloto.whitesound.R;
import co.edu.unipiloto.whitesound.actividades.JuegosActivity;
import co.edu.unipiloto.whitesound.adaptadores.AdaptadorListaElementosEdicion;
import co.edu.unipiloto.whitesound.clases.Nota;


public class JuegosRitmicosFragment extends Fragment {

    private int eleccion, alturaIndex, tiempo, correctos, incorrectos, alturaTemp;
    private ImageButton fjr_imgbtn_responder;
    private Dialog dialogo;
    private Nota notaPregunta, notaRespuesta;
    private TextView fjr_tv_respuesta, fjr_tv_tiempo;
    private List<String> figurasMusicales, alteracionesMusicales, altura;
    private NavController navController;
    private Handler handler;
    private Resources res;

    public JuegosRitmicosFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_juegos_ritmicos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        if(savedInstanceState != null){
            alturaIndex = savedInstanceState.getInt("alturaIndex");
            alturaTemp = savedInstanceState.getInt("alturaTemp");
            tiempo = savedInstanceState.getInt("tiempo");
            correctos = savedInstanceState.getInt("correctos");
            incorrectos = savedInstanceState.getInt("incorrectos");
            eleccion = savedInstanceState.getInt("eleccion");
            notaPregunta = (Nota) savedInstanceState.getSerializable("notaPregunta");
            notaRespuesta = (Nota) savedInstanceState.getSerializable("notaRespuesta");

            fjr_tv_respuesta.setText(notaRespuesta.toString());
            fjr_imgbtn_responder.setContentDescription(res.getString(R.string.fjr_imgbtn_responder, notaRespuesta.toString()));
            fjr_tv_tiempo.setText(String.valueOf(tiempo));
        }
        reproducirNota();
        iniciarTemporizador();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("alturaIndex", alturaIndex);
        outState.putInt("alturaTemp", alturaTemp);
        outState.putInt("tiempo", tiempo);
        outState.putInt("correctos", correctos);
        outState.putInt("incorrectos", incorrectos);
        outState.putInt("eleccion", eleccion);
        outState.putSerializable("notaPregunta", notaPregunta);
        outState.putSerializable("notaRespuesta", notaRespuesta);
    }

    public void initViews(View view) {

        initFiguras();
        initAlteraciones();
        initAlturas();
        res = getResources();
        navController = Navigation.findNavController(view);
        alturaIndex = 0;
        correctos = 0;
        incorrectos = 0;
        tiempo = 120;

        eleccion = getArguments().getInt("eleccion");
        notaPregunta = new Nota(altura.get(0), figurasMusicales.get(2));
        notaRespuesta = new Nota(altura.get(0), figurasMusicales.get(2));

        BottomNavigationView fjr_menu_inferior = view.findViewById(R.id.fjr_menu_inferior);
        ImageButton fjr_imgbtn_bajar_altura = view.findViewById(R.id.fjr_imgbtn_bajar_altura);
        ImageButton fjr_imgbtn_subir_altura = view.findViewById(R.id.fjr_imgbtn_subir_altura);
        ImageButton fjr_imgbtn_repetir_nota = view.findViewById(R.id.fjr_imgbtn_repetir_nota);
        fjr_imgbtn_responder = view.findViewById(R.id.fjr_imgbtn_responder);
        fjr_tv_respuesta = view.findViewById(R.id.fjr_tv_respuesta);
        fjr_tv_tiempo = view.findViewById(R.id.fjr_tv_tiempo);
        dialogo = new Dialog(getContext());
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        fjr_tv_tiempo.setText(String.valueOf(tiempo));

        switch (eleccion) {
            case 1:
                fjr_menu_inferior.getMenu().findItem(R.id.miep_borrar).setVisible(false);
                fjr_menu_inferior.getMenu().findItem(R.id.miep_silencios).setVisible(false);
                fjr_menu_inferior.getMenu().findItem(R.id.miep_figuras_musicales).setVisible(false);

                notaPregunta.setDuracion(null);
                notaRespuesta.setDuracion(null);
                generarAltura();

                ((JuegosActivity)getActivity()).cambiarTitulo("Altura");
                break;
            case 2:
                fjr_imgbtn_subir_altura.setVisibility(View.GONE);
                fjr_imgbtn_bajar_altura.setVisibility(View.GONE);
                fjr_menu_inferior.getMenu().findItem(R.id.miep_alteraciones).setVisible(false);
                fjr_menu_inferior.getMenu().findItem(R.id.miep_borrar).setVisible(false);
                fjr_menu_inferior.getMenu().findItem(R.id.miep_silencios).setVisible(false);

                notaPregunta.setAltura(null);
                notaRespuesta.setAltura(null);
                alturaTemp = (int) (Math.random()*12);
                generarDuracion();

                ((JuegosActivity)getActivity()).cambiarTitulo("Duración");
                break;
            case 3:
                fjr_menu_inferior.getMenu().findItem(R.id.miep_borrar).setVisible(false);
                fjr_menu_inferior.getMenu().findItem(R.id.miep_silencios).setVisible(false);

                generarAltura();
                generarDuracion();

                ((JuegosActivity)getActivity()).cambiarTitulo("Combinada");
                break;
        }

        fjr_tv_respuesta.setText(notaRespuesta.toString());
        fjr_imgbtn_responder.setContentDescription(res.getString(R.string.fjr_imgbtn_responder, notaRespuesta.toString()));

        fjr_imgbtn_subir_altura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subirAltura();
            }
        });

        fjr_imgbtn_bajar_altura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bajarAltura();
            }
        });

        fjr_imgbtn_repetir_nota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reproducirNota();
            }
        });

        fjr_imgbtn_responder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                responder();
            }
        });

        fjr_menu_inferior.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.miep_figuras_musicales:
                        abrirDialogoFiguraMusical();
                        break;
                    case R.id.miep_alteraciones:
                        abrirDialogoAlteraciones();
                        break;
                }
                return true;
            }
        });
    }

    private void generarAltura() {
        int alturaPreguntada;
        int alteracionPreguntada;

        do {
            alturaPreguntada = (int) (Math.random() * 7);
            alteracionPreguntada = (int) (Math.random() * 3);
        } while ((alteracionPreguntada == 0 && (alturaPreguntada == 2 || alturaPreguntada == 6))
                || (alteracionPreguntada == 1 && (alturaPreguntada == 0 || alturaPreguntada == 3)));

        notaPregunta.setAltura(altura.get(alturaPreguntada));
        if (alteracionPreguntada == 2) {
            notaPregunta.setAlteracion(null);
        } else {
            notaPregunta.setAlteracion(alteracionesMusicales.get(alteracionPreguntada));
        }
    }

    private void generarDuracion() {
        notaPregunta.setDuracion(figurasMusicales.get((int) (Math.random() * 5)));
    }

    //Diálogo para elegir una figura musical
    private void abrirDialogoFiguraMusical() {

        //Mostrar diálogo
        dialogo.setContentView(R.layout.popup_elementos_edicion);
        dialogo.show();

        //Inicializar vistas del diálogo
        ListView pee_lv_elementos = dialogo.findViewById(R.id.pee_lv_elementos);
        AdaptadorListaElementosEdicion adaptador = new AdaptadorListaElementosEdicion(getContext(), figurasMusicales);
        pee_lv_elementos.setAdapter(adaptador);

        pee_lv_elementos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                notaRespuesta.setDuracion(figurasMusicales.get(i));
                fjr_tv_respuesta.setText(notaRespuesta.toString());
                Toast.makeText(getContext(), notaRespuesta.toString(), Toast.LENGTH_SHORT).show();
                fjr_imgbtn_responder.setContentDescription(res.getString(R.string.fjr_imgbtn_responder, notaRespuesta.toString()));
                cerrarDialogo();
            }
        });
    }

    //Diálogo para elegir una alteracion musical
    private void abrirDialogoAlteraciones() {

        //Mostrar diálogo
        dialogo.setContentView(R.layout.popup_elementos_edicion);
        dialogo.show();

        //Inicializar vistas del diálogo
        ListView pee_lv_elementos = dialogo.findViewById(R.id.pee_lv_elementos);
        AdaptadorListaElementosEdicion adaptador = new AdaptadorListaElementosEdicion(getContext(), alteracionesMusicales);
        pee_lv_elementos.setAdapter(adaptador);

        pee_lv_elementos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (i == 2) {
                    notaRespuesta.setAlteracion(null);
                } else if ((i == 0 && alturaIndex != 2 && alturaIndex != 6)
                        || (i == 1 && alturaIndex != 0 && alturaIndex != 3)) {
                    notaRespuesta.setAlteracion(alteracionesMusicales.get(i));
                }
                fjr_tv_respuesta.setText(notaRespuesta.toString());
                Toast.makeText(getContext(), notaRespuesta.toString(), Toast.LENGTH_SHORT).show();
                fjr_imgbtn_responder.setContentDescription(res.getString(R.string.fjr_imgbtn_responder, notaRespuesta.toString()));
                cerrarDialogo();
            }
        });
    }

    private void abrirDialogoDeResultados(){

        int total = incorrectos + correctos;
        double precision = 0;
        if(total > 0){
            precision = ((double) correctos / total) * 100;
        }

        dialogo.setContentView(R.layout.popup_resultados_juegos);
        dialogo.show();

        Button prj_btn_reiniciar = dialogo.findViewById(R.id.prj_btn_reiniciar);
        Button prj_btn_salir = dialogo.findViewById(R.id.prj_btn_salir);
        TextView prj_tv_aciertos = dialogo.findViewById(R.id.prj_tv_aciertos);
        TextView prj_tv_fallos = dialogo.findViewById(R.id.prj_tv_fallos);
        TextView prj_tv_precision = dialogo.findViewById(R.id.prj_tv_precision);

        prj_tv_aciertos.setText(res.getString(R.string.txt_prj_tv_aciertos, correctos));
        prj_tv_fallos.setText(res.getString(R.string.txt_prj_tv_fallos, incorrectos));
        prj_tv_precision.setText(res.getString(R.string.txt_prj_tv_precision, String.format("%.2f%%", precision)));

        prj_btn_reiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correctos = 0;
                incorrectos = 0;
                tiempo = 120;
                switch (eleccion) {
                    case 1:
                        generarAltura();
                        alturaIndex = 0;
                        notaRespuesta.setAlteracion(null);
                        notaRespuesta.setAltura(altura.get(alturaIndex));
                        break;
                    case 2:
                        generarDuracion();
                        notaRespuesta.setDuracion(figurasMusicales.get(2));
                        break;
                    case 3:
                        generarAltura();
                        generarDuracion();
                        alturaIndex = 0;
                        notaRespuesta.setAlteracion(null);
                        notaRespuesta.setAltura(altura.get(alturaIndex));
                        notaRespuesta.setDuracion(figurasMusicales.get(2));
                }
                fjr_tv_respuesta.setText(notaRespuesta.toString());
                fjr_imgbtn_responder.setContentDescription(res.getString(R.string.fjr_imgbtn_responder, notaRespuesta.toString()));
                reproducirNota();
                cerrarDialogo();
                iniciarTemporizador();
            }
        });

        prj_btn_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cerrarDialogo();
                navController.navigateUp();
            }
        });
    }

    public void subirAltura() {
        if (alturaIndex < altura.size() - 1) {
            alturaIndex++;
            notaRespuesta.setAlteracion(null);
            notaRespuesta.setAltura(altura.get(alturaIndex));
        }
        fjr_tv_respuesta.setText(notaRespuesta.toString());
        Toast.makeText(getContext(), notaRespuesta.toString(), Toast.LENGTH_SHORT).show();
        fjr_imgbtn_responder.setContentDescription(res.getString(R.string.fjr_imgbtn_responder, notaRespuesta.toString()));
    }

    public void bajarAltura() {
        if (alturaIndex > 0) {
            alturaIndex--;
            notaRespuesta.setAlteracion(null);
            notaRespuesta.setAltura(altura.get(alturaIndex));
        }

        fjr_tv_respuesta.setText(notaRespuesta.toString());
        Toast.makeText(getContext(), notaRespuesta.toString(), Toast.LENGTH_SHORT).show();
        fjr_imgbtn_responder.setContentDescription(res.getString(R.string.fjr_imgbtn_responder, notaRespuesta.toString()));
    }

    public void responder() {
        Log.v("Pregunta", notaPregunta.toString() + " = " + notaRespuesta.toString());
        if (notaPregunta.equals(notaRespuesta)) {
            correctos++;
            Toast.makeText(getContext(), "Correcto", Toast.LENGTH_SHORT).show();
            switch (eleccion) {
                case 1:
                    generarAltura();
                    alturaIndex = 0;
                    notaRespuesta.setAlteracion(null);
                    notaRespuesta.setAltura(altura.get(alturaIndex));
                    break;
                case 2:
                    generarDuracion();
                    notaRespuesta.setDuracion(figurasMusicales.get(2));
                    break;
                case 3:
                    generarAltura();
                    generarDuracion();
                    alturaIndex = 0;
                    notaRespuesta.setAlteracion(null);
                    notaRespuesta.setAltura(altura.get(alturaIndex));
                    notaRespuesta.setDuracion(figurasMusicales.get(2));
            }
            fjr_tv_respuesta.setText(notaRespuesta.toString());
            fjr_imgbtn_responder.setContentDescription(res.getString(R.string.fjr_imgbtn_responder, notaRespuesta.toString()));
            reproducirNota();
        } else {
            Toast.makeText(getContext(), "Incorrecto", Toast.LENGTH_SHORT).show();
            incorrectos++;
        }
    }

    public void reproducirNota() {
        int alturaIdx = altura.indexOf(notaPregunta.getAltura());
        if (notaPregunta.getAlteracion() != null) {
            if (notaPregunta.getAlteracion().equals("Sostenido")) {
                alturaIdx += 7 - (alturaIdx / 3);
            } else if (notaPregunta.getAlteracion().equals("Bemol")) {
                alturaIdx += 6 - (alturaIdx / 4);
            }
        }

        int duracion = 2;
        if(eleccion != 1){
            duracion = figurasMusicales.indexOf(notaPregunta.getDuracion());
        }

        if (eleccion == 2) {
            alturaIdx = alturaTemp;
        }
        ((JuegosActivity) getActivity()).reproducirNota(alturaIdx, duracion);
    }

    private void iniciarTemporizador(){
        handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(tiempo > 0){
                    tiempo--;
                    try {
                        ((JuegosActivity)getActivity()).reproducirReloj();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    fjr_tv_tiempo.setText(String.valueOf(tiempo));
                    handler.postDelayed(this, 1000);
                }else{
                    Toast.makeText(getContext(), "Fin de la práctica", Toast.LENGTH_SHORT).show();
                    abrirDialogoDeResultados();
                }
            }
        });
    }

    private void cerrarDialogo() {
        dialogo.dismiss();
    }

    //**** INICIALIZAR LISTAS ****
    private void initFiguras() {

        figurasMusicales = new ArrayList<>();

        figurasMusicales.add("Redonda");
        figurasMusicales.add("Blanca");
        figurasMusicales.add("Negra");
        figurasMusicales.add("Corchea");
        figurasMusicales.add("Semicorchea");
    }

    private void initAlteraciones() {

        alteracionesMusicales = new ArrayList<>();

        alteracionesMusicales.add("Sostenido");
        alteracionesMusicales.add("Bemol");
        alteracionesMusicales.add("Sin alteración");
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

    public int getEleccion(){
        return eleccion;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        handler.removeCallbacksAndMessages(null);
    }
}