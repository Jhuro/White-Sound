package co.edu.unipiloto.whitesound.fragmentos;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

import co.edu.unipiloto.whitesound.actividades.EditarPartituraActivity;
import co.edu.unipiloto.whitesound.R;
import co.edu.unipiloto.whitesound.actividades.JuegosActivity;
import co.edu.unipiloto.whitesound.adaptadores.AdaptadorListaElementosEdicion;
import co.edu.unipiloto.whitesound.clases.ListaDE;
import co.edu.unipiloto.whitesound.clases.Nota;
import co.edu.unipiloto.whitesound.clases.Partitura;

public class EdicionFragment extends Fragment {

    private EditText fe_et_titulo, fe_et_autor;
    private TextView fe_tv_nota;
    private ImageButton fe_imgbtn_agregar_nota;
    private List<String> figurasMusicales, silenciosMusicales, alteracionesMusicales, altura;
    private Dialog dialogo;
    private Partitura partitura;
    private ListaDE partituraLDE;
    private boolean nuevaNota;
    private int alturaIndex, duracionIndex, alteracionIndex, elementoIndex, partituraIndex;

    //Constructor vacio obligatorio
    public EdicionFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edicion, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);

        if(savedInstanceState != null) {
            partituraIndex = savedInstanceState.getInt("partituraIndex");
            nuevaNota = savedInstanceState.getBoolean("nuevaNota");
            if(!partituraLDE.isEmpty() && !nuevaNota){
                if (!partituraLDE.obtenerNotaEnPosicion(partituraIndex).isSilencio()) {
                    alturaIndex = altura.indexOf(partituraLDE.obtenerNotaEnPosicion(partituraIndex).getAltura());
                }
                fe_tv_nota.setText(partituraLDE.obtenerNotaEnPosicion(partituraIndex).toString());
            }else{
                fe_tv_nota.setText(R.string.fe_tv_agregar_nota);
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("partituraIndex", partituraIndex);
        outState.putBoolean("nuevaNota", nuevaNota);
    }

    public void initViews(View view){

        final NavController navController = Navigation.findNavController(view);
        ((EditarPartituraActivity)getActivity()).cambiarTitulo("Edición");
        fe_et_titulo = view.findViewById(R.id.fe_et_titulo);
        fe_et_autor = view.findViewById(R.id.fe_et_autor);
        BottomNavigationView fe_menu_inferior = view.findViewById(R.id.fe_menu_inferior);
        dialogo = new Dialog(getContext());
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        ImageButton fe_imgbtn_modo_lectura = view.findViewById(R.id.fe_imgbtn_modo_lectura);
        fe_imgbtn_agregar_nota = view.findViewById(R.id.fe_imgbtn_añadir_nota);
        ImageButton fe_imgbtn_desplazar_izq = view.findViewById(R.id.fe_imgbtn_desplazar_izq);
        ImageButton fe_imgbtn_desplazar_der = view.findViewById(R.id.fe_imgbtn_desplazar_der);
        ImageButton fe_imgbtn_subir_altura = view.findViewById(R.id.fe_imgbtn_subir_altura);
        ImageButton fe_imgbtn_bajar_altura = view.findViewById(R.id.fe_imgbtn_bajar_altura);
        fe_tv_nota = view.findViewById(R.id.fe_tv_nota);

        elementoIndex = 0;
        partituraIndex = 0;
        alturaIndex = 0;
        duracionIndex = 2;
        initFiguras();
        initSilencios();
        initAlteraciones();
        initAlturas();

        partitura = ((EditarPartituraActivity)getActivity()).getPartitura();
        fe_et_titulo.setText(partitura.getTitulo());
        fe_et_autor.setText(partitura.getAutor());
        partituraLDE = partitura.getNotas();

        if(partituraLDE.isEmpty()){
            nuevaNota = true;
            fe_tv_nota.setText(R.string.fe_tv_agregar_nota);
        }else{
            if(!partituraLDE.obtenerNotaEnPosicion(partituraIndex).isSilencio()){
                alturaIndex = altura.indexOf(partituraLDE.obtenerNotaEnPosicion(partituraIndex).getAltura());
            }
            nuevaNota = false;
            fe_tv_nota.setText(partituraLDE.obtenerNotaEnPosicion(partituraIndex).toString());
        }

        fe_imgbtn_subir_altura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subirAltura();
            }
        });

        fe_imgbtn_bajar_altura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bajarAltura();
            }
        });

        fe_imgbtn_desplazar_izq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                desplazarIzquierda();
            }
        });

        fe_imgbtn_desplazar_der.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                desplazarDerecha();
            }
        });

        fe_imgbtn_agregar_nota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregarNota();
            }
        });

        fe_imgbtn_modo_lectura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                partitura.setTitulo(fe_et_titulo.getText().toString());
                partitura.setAutor(fe_et_autor.getText().toString());
                ((EditarPartituraActivity)getActivity()).setPartituraTemp(partitura);
                navController.navigate(R.id.lecturaFragment);
            }
        });

        //Menu inferior
        fe_menu_inferior.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()){
                    case R.id.miep_figuras_musicales:
                        abrirDialogoFiguraMusical(item);
                        break;
                    case R.id.miep_silencios:
                        abrirDialogoSilencioMusical(item);
                        break;
                    case R.id.miep_alteraciones:
                        abrirDialogoAlteraciones(item);
                        break;
                    case R.id.miep_borrar:
                        modoEliminarNota();
                        break;
                }
                return true;
            }
        });
    }

    //Diálogo para elegir una figura musical
    private void abrirDialogoFiguraMusical(MenuItem item) {

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

                cambiarIconoMenuFigura(figurasMusicales.get(i), item);
            }
        });
    }

    //Diálogo para elegir un silencio musical
    private void abrirDialogoSilencioMusical(MenuItem item) {

        //Mostrar diálogo
        dialogo.setContentView(R.layout.popup_elementos_edicion);
        dialogo.show();

        //Inicializar vistas del diálogo
        ListView pee_lv_elementos = dialogo.findViewById(R.id.pee_lv_elementos);
        AdaptadorListaElementosEdicion adaptador = new AdaptadorListaElementosEdicion(getContext(), silenciosMusicales);
        pee_lv_elementos.setAdapter(adaptador);

        pee_lv_elementos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                cambiarIconoMenuSilencio(silenciosMusicales.get(i), item);
            }
        });
    }

    //Diálogo para elegir una alteracion musical
    private void abrirDialogoAlteraciones(MenuItem item) {

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

                cambiarIconoMenuAlteracion(alteracionesMusicales.get(i), item);
            }
        });
    }

    //Cambio del icono del menú de figuras musicales
    public void cambiarIconoMenuFigura(String figura, MenuItem item){

        switch(figura){
            case "Redonda":
                item.setIcon(R.drawable.ic_redonda);
                duracionIndex = 0;
                fe_imgbtn_agregar_nota.setImageResource(R.drawable.ic_redonda);
                break;
            case "Blanca":
                item.setIcon(R.drawable.ic_blanca);
                duracionIndex = 1;
                fe_imgbtn_agregar_nota.setImageResource(R.drawable.ic_blanca);
                break;
            case "Negra":
                item.setIcon(R.drawable.ic_negra);
                duracionIndex = 2;
                fe_imgbtn_agregar_nota.setImageResource(R.drawable.ic_negra);
                break;
            case "Corchea":
                item.setIcon(R.drawable.ic_corchea);
                duracionIndex = 3;
                fe_imgbtn_agregar_nota.setImageResource(R.drawable.ic_corchea);
                break;
            case "Semicorchea":
                item.setIcon(R.drawable.ic_semicorchea);
                duracionIndex = 4;
                fe_imgbtn_agregar_nota.setImageResource(R.drawable.ic_semicorchea);
                break;
        }
        elementoIndex = 0;
        fe_imgbtn_agregar_nota.setContentDescription("Añadir " + figura.toLowerCase());
        item.setTitle("Figuras musicales, " + figura.toLowerCase());
        cerrarDialogo();
    }

    //Cambio del icono del menú de silencios musicales
    public void cambiarIconoMenuSilencio(String silencio, MenuItem item){

        switch(silencio){
            case "Silencio de redonda":
                item.setIcon(R.drawable.ic_silencio_redonda);
                duracionIndex = 0;
                fe_imgbtn_agregar_nota.setImageResource(R.drawable.ic_silencio_redonda);
                break;
            case "Silencio de blanca":
                item.setIcon(R.drawable.ic_silencio_blanca);
                duracionIndex = 1;
                fe_imgbtn_agregar_nota.setImageResource(R.drawable.ic_silencio_blanca);
                break;
            case "Silencio de negra":
                item.setIcon(R.drawable.ic_silencio_negra);
                duracionIndex = 2;
                fe_imgbtn_agregar_nota.setImageResource(R.drawable.ic_silencio_negra);
                break;
            case "Silencio de corchea":
                item.setIcon(R.drawable.ic_silencio_corchea);
                duracionIndex = 3;
                fe_imgbtn_agregar_nota.setImageResource(R.drawable.ic_silencio_corchea);
                break;
            case "Silencio de semicorchea":
                item.setIcon(R.drawable.ic_silencio_semicorchea);
                duracionIndex = 4;
                fe_imgbtn_agregar_nota.setImageResource(R.drawable.ic_silencio_semicorchea);
                break;
        }

        elementoIndex = 1;
        fe_imgbtn_agregar_nota.setContentDescription("Añadir " + silencio.toLowerCase());
        item.setTitle("Silencios, " + silencio.toLowerCase());
        cerrarDialogo();
    }

    //Cambio del icono del menú de alteraciones musicales
    public void cambiarIconoMenuAlteracion(String alteracion, MenuItem item){

        switch(alteracion){
            case "Sostenido":
                alteracionIndex = 0;
                item.setIcon(R.drawable.ic_sostenido);
                fe_imgbtn_agregar_nota.setImageResource(R.drawable.ic_sostenido);
                break;
            case "Bemol":
                alteracionIndex = 1;
                item.setIcon(R.drawable.ic_bemol);
                fe_imgbtn_agregar_nota.setImageResource(R.drawable.ic_bemol);
                break;
            case "Sin alteración":
                alteracionIndex = 2;
                item.setIcon(R.drawable.ic_sin_alteracion);
                fe_imgbtn_agregar_nota.setImageResource(R.drawable.ic_sin_alteracion);
                break;
        }

        elementoIndex = 2;
        fe_imgbtn_agregar_nota.setContentDescription("Añadir " + alteracion.toLowerCase());
        item.setTitle("Alteraciones, " + alteracion.toLowerCase());
        cerrarDialogo();
    }

    private void cerrarDialogo() {
        dialogo.dismiss();
    }

    public void subirAltura(){
        if(alturaIndex < altura.size()-1) {
            alturaIndex++;
        }
        if(nuevaNota){
            fe_tv_nota.setText(altura.get(alturaIndex));
        }
        else{
            if(!partituraLDE.obtenerNotaEnPosicion(partituraIndex).isSilencio()){
                partituraLDE.obtenerNotaEnPosicion(partituraIndex).setAlteracion(null);
                partituraLDE.obtenerNotaEnPosicion(partituraIndex).setAltura(altura.get(alturaIndex));
            }
            fe_tv_nota.setText(partituraLDE.obtenerNotaEnPosicion(partituraIndex).toString());
        }
        ((EditarPartituraActivity) getActivity()).reproducirNota(alturaIndex, 2);
    }

    public void bajarAltura(){
        if(alturaIndex > 0) {
            alturaIndex--;
        }
        if(nuevaNota){
            fe_tv_nota.setText(altura.get(alturaIndex));
        }
        else{
            if(!partituraLDE.obtenerNotaEnPosicion(partituraIndex).isSilencio()){
                partituraLDE.obtenerNotaEnPosicion(partituraIndex).setAlteracion(null);
                partituraLDE.obtenerNotaEnPosicion(partituraIndex).setAltura(altura.get(alturaIndex));
            }
            fe_tv_nota.setText(partituraLDE.obtenerNotaEnPosicion(partituraIndex).toString());
        }
        ((EditarPartituraActivity) getActivity()).reproducirNota(alturaIndex, 2);
    }

    public void desplazarIzquierda(){
        if(!nuevaNota && !partituraLDE.obtenerNotaEnPosicion(partituraIndex).isSilencio()){
            alturaIndex = altura.indexOf(partituraLDE.obtenerNotaEnPosicion(partituraIndex).getAltura());
        }

        if(partituraIndex > 0 && nuevaNota) {
            nuevaNota = false;
            partituraIndex--;
        }else{
            nuevaNota = true;
        }

        if(!partituraLDE.isEmpty() && !nuevaNota){
            fe_tv_nota.setText(partituraLDE.obtenerNotaEnPosicion(partituraIndex).toString());
            alturaIndex = altura.indexOf(partituraLDE.obtenerNotaEnPosicion(partituraIndex).getAltura());
        }else{
            fe_tv_nota.setText(R.string.fe_tv_agregar_nota);
        }
    }

    public void desplazarDerecha(){
        if(!nuevaNota && !partituraLDE.obtenerNotaEnPosicion(partituraIndex).isSilencio()){
            alturaIndex = altura.indexOf(partituraLDE.obtenerNotaEnPosicion(partituraIndex).getAltura());
        }
        if(partituraIndex < partituraLDE.getSize() && !nuevaNota) {
            nuevaNota = true;
            partituraIndex++;
        }else if(partituraIndex != partituraLDE.getSize()){
            nuevaNota = false;
        }

        if(!partituraLDE.isEmpty() && !nuevaNota){
            fe_tv_nota.setText(partituraLDE.obtenerNotaEnPosicion(partituraIndex).toString());
            alturaIndex = altura.indexOf(partituraLDE.obtenerNotaEnPosicion(partituraIndex).getAltura());
        }else{
            fe_tv_nota.setText(R.string.fe_tv_agregar_nota);
        }
    }

    private void modoEliminarNota() {

        elementoIndex = 3;
        fe_imgbtn_agregar_nota.setImageResource(R.drawable.ic_eliminar_nota);
        fe_imgbtn_agregar_nota.setContentDescription("Eliminar nota");
    }

    //Agregar una nota o alteracion a la partitura
    public void agregarNota(){

        switch(elementoIndex){
            //Nota
            case 0:
                if(nuevaNota) {
                    partituraLDE.agregarNotaEnPosicion(new Nota(altura.get(alturaIndex), figurasMusicales.get(duracionIndex)), partituraIndex);
                    nuevaNota = false;
                } else{
                    if(partituraLDE.obtenerNotaEnPosicion(partituraIndex).isSilencio()) {
                        partituraLDE.cambiarNotaEnPosicion(new Nota(altura.get(alturaIndex), figurasMusicales.get(duracionIndex)), partituraIndex);
                    }else{
                        partituraLDE.obtenerNotaEnPosicion(partituraIndex).setDuracion(figurasMusicales.get(duracionIndex));
                    }
                }

                fe_tv_nota.setText(partituraLDE.obtenerNotaEnPosicion(partituraIndex).toString());
                break;
            //Silencio
            case 1:
                if(nuevaNota) {
                    partituraLDE.agregarNotaEnPosicion(new Nota(figurasMusicales.get(duracionIndex)), partituraIndex);
                    nuevaNota = false;
                } else{
                    if(!partituraLDE.obtenerNotaEnPosicion(partituraIndex).isSilencio()){
                        partituraLDE.cambiarNotaEnPosicion(new Nota(figurasMusicales.get(duracionIndex)), partituraIndex);
                    }else{
                        partituraLDE.obtenerNotaEnPosicion(partituraIndex).setDuracion(figurasMusicales.get(duracionIndex));
                    }
                }

                fe_tv_nota.setText(partituraLDE.obtenerNotaEnPosicion(partituraIndex).toString());
                break;
            //Alteracion
            case 2:
                if(!nuevaNota && !partituraLDE.isEmpty() && !partituraLDE.obtenerNotaEnPosicion(partituraIndex).isSilencio()){
                    if((alteracionIndex == 0 && alturaIndex != 2 && alturaIndex != 6)
                            || (alteracionIndex == 1 && alturaIndex != 0 && alturaIndex != 3)) {
                        partituraLDE.obtenerNotaEnPosicion(partituraIndex).setAlteracion(alteracionesMusicales.get(alteracionIndex));
                        fe_tv_nota.setText(partituraLDE.obtenerNotaEnPosicion(partituraIndex).toString());
                    } else if(alteracionIndex == 2){
                        partituraLDE.obtenerNotaEnPosicion(partituraIndex).setAlteracion(null);
                        fe_tv_nota.setText(partituraLDE.obtenerNotaEnPosicion(partituraIndex).toString());
                    }
                }
                break;
            case 3:
                eliminarNota();
                break;
        }
    }

    //Eliminar nota de la partitura
    public void eliminarNota(){

        if(!partituraLDE.isEmpty() && !nuevaNota){
            partituraLDE.eliminarNotaEnPosicion(partituraIndex);
            if(partituraLDE.isEmpty()){
                nuevaNota = true;
                fe_tv_nota.setText(R.string.fe_tv_agregar_nota);
                return;
            }else if(partituraIndex == partituraLDE.getSize()){
                partituraIndex--;
            }
            fe_tv_nota.setText(partituraLDE.obtenerNotaEnPosicion(partituraIndex).toString());
        }
    }

    //**** INICIALIZAR LISTAS ****
    //Inicializar lista de figuras musicales
    private void initFiguras() {

        figurasMusicales = new ArrayList<>();

        figurasMusicales.add("Redonda");
        figurasMusicales.add("Blanca");
        figurasMusicales.add("Negra");
        figurasMusicales.add("Corchea");
        figurasMusicales.add("Semicorchea");
    }

    //Inicializar lista de silencios musicales
    private void initSilencios() {

        silenciosMusicales = new ArrayList<>();

        silenciosMusicales.add("Silencio de redonda");
        silenciosMusicales.add("Silencio de blanca");
        silenciosMusicales.add("Silencio de negra");
        silenciosMusicales.add("Silencio de corchea");
        silenciosMusicales.add("Silencio de semicorchea");
    }

    //Inicializar lista de alteraciones musicales
    private void initAlteraciones() {

        alteracionesMusicales = new ArrayList<>();

        alteracionesMusicales.add("Sostenido");
        alteracionesMusicales.add("Bemol");
        alteracionesMusicales.add("Sin alteración");
    }

    //Inicializar lista de alturas
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

    public Partitura getPartitura() {

        Partitura partitura = new Partitura(fe_et_titulo.getText().toString(), fe_et_autor.getText().toString());
        partitura.setNotas(partituraLDE);
        return partitura;
    }
}