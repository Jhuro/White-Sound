package co.edu.unipiloto.whitesound.actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import co.edu.unipiloto.whitesound.R;
import co.edu.unipiloto.whitesound.adaptadores.AdaptadorListaElementosEdicion;
import co.edu.unipiloto.whitesound.clases.ListaDE;
import co.edu.unipiloto.whitesound.clases.Nota;
import co.edu.unipiloto.whitesound.clases.Partitura;

public class EditarPartituraActivity extends AppCompatActivity {

    public static final String ARCHIVO = "partitura.wsnd";
    private BottomNavigationView aep_menu_inferior;
    private List<String> figurasMusicales, silenciosMusicales, alteracionesMusicales, altura;
    private ListaDE partituraLDE;
    private EditText aep_et_titulo, aep_et_autor;
    private TextView aep_tv_nota;
    private ImageButton aep_imgbtn_añadir_nota, aep_imgbtn_desplazar_izq, aep_imgbtn_desplazar_der, aep_imgbtn_subir_altura, aep_imgbtn_bajar_altura;
    private Toolbar toolbar;
    private Dialog dialogo;
    private String archivoPartitura;
    private Partitura partituraC;
    private boolean nuevaNota;
    private int alturaIndex, duracionIndex, alteracionIndex, elementoIndex, partituraIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT > 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_editar_partitura);
        initViews();
    }

    //Inicializar vistas
    private void initViews(){
        //Obtener valores intent
        Intent intent = getIntent();
        try {
            archivoPartitura = intent.getStringExtra(ARCHIVO);
            contenidoArchivoPartitura(archivoPartitura);
        } catch (IOException e) {
            e.printStackTrace();
        }

        aep_et_titulo = (EditText) findViewById(R.id.aep_et_titulo);
        aep_et_autor = (EditText) findViewById(R.id.aep_et_autor);
        aep_menu_inferior = (BottomNavigationView) findViewById(R.id.aep_menu_inferior);
        dialogo = new Dialog(this);
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        toolbar = (Toolbar) findViewById(R.id.aep_toolbar);
        aep_imgbtn_añadir_nota = (ImageButton) findViewById(R.id.aep_imgbtn_añadir_nota);
        aep_imgbtn_desplazar_izq = (ImageButton) findViewById(R.id.aep_imgbtn_desplazar_izq);
        aep_imgbtn_desplazar_der = (ImageButton) findViewById(R.id.aep_imgbtn_desplazar_der);
        aep_imgbtn_subir_altura = (ImageButton) findViewById(R.id.aep_imgbtn_subir_altura);
        aep_imgbtn_bajar_altura = (ImageButton) findViewById(R.id.aep_imgbtn_bajar_altura);
        aep_tv_nota = (TextView) findViewById(R.id.aep_tv_nota);

        elementoIndex = 0;
        partituraIndex = 0;
        alturaIndex = 0;
        duracionIndex = 2;
        initFiguras();
        initSilencios();
        initAlteraciones();
        initAlturas();

        //Toolbar
        toolbar.setTitle("Editar partitura");
        setSupportActionBar(toolbar);

        //Inicializar textos de partitura
        aep_et_titulo.setText(partituraC.getTitulo());
        aep_et_autor.setText(partituraC.getAutor());
        partituraLDE = partituraC.getNotas();

        if(partituraLDE.isEmpty()){
            nuevaNota = true;
            aep_tv_nota.setText("Agregar nota");
        }else{
            nuevaNota = false;
            aep_tv_nota.setText(partituraLDE.obtenerNotaEnPosicion(partituraIndex).toString());
        }

        aep_imgbtn_desplazar_izq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                desplazarIzquierda();
            }
        });

        aep_imgbtn_desplazar_der.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                desplazarDerecha();
            }
        });

        aep_imgbtn_subir_altura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subirAltura();
            }
        });

        aep_imgbtn_bajar_altura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bajarAltura();
            }
        });

        aep_imgbtn_añadir_nota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                añadirNota();
            }
        });

        //Menu inferior
        aep_menu_inferior.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
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
                }
                return true;
            }
        });
    }

    //**** DIÁLOGOS ****

    //Diálogo para elegir una figura musical
    private void abrirDialogoFiguraMusical(MenuItem item) {

        //Mostrar diálogo
        dialogo.setContentView(R.layout.popup_elementos_edicion);
        dialogo.show();

        //Inicializar vistas del diálogo
        ListView pee_lv_elementos = (ListView) dialogo.findViewById(R.id.pee_lv_elementos);
        AdaptadorListaElementosEdicion adaptador = new AdaptadorListaElementosEdicion(this, figurasMusicales);
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
        ListView pee_lv_elementos = (ListView) dialogo.findViewById(R.id.pee_lv_elementos);
        AdaptadorListaElementosEdicion adaptador = new AdaptadorListaElementosEdicion(this, silenciosMusicales);
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
        ListView pee_lv_elementos = (ListView) dialogo.findViewById(R.id.pee_lv_elementos);
        AdaptadorListaElementosEdicion adaptador = new AdaptadorListaElementosEdicion(this, alteracionesMusicales);
        pee_lv_elementos.setAdapter(adaptador);

        pee_lv_elementos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                cambiarIconoMenuAlteracion(alteracionesMusicales.get(i), item);
            }
        });
    }

    //Cerrar cualquier diálogo
    private void cerrarDialogo() {
        dialogo.dismiss();
    }

    //**** FUNCIONES MENÚS ****

    //Cambio del icono del menú de figuras musicales
    public void cambiarIconoMenuFigura(String figura, MenuItem item){

        switch(figura){
            case "Redonda":
                item.setIcon(R.drawable.ic_redonda);
                duracionIndex = 0;
                aep_imgbtn_añadir_nota.setImageResource(R.drawable.ic_redonda);
                break;
            case "Blanca":
                item.setIcon(R.drawable.ic_blanca);
                duracionIndex = 1;
                aep_imgbtn_añadir_nota.setImageResource(R.drawable.ic_blanca);
                break;
            case "Negra":
                item.setIcon(R.drawable.ic_negra);
                duracionIndex = 2;
                aep_imgbtn_añadir_nota.setImageResource(R.drawable.ic_negra);
                break;
            case "Corchea":
                item.setIcon(R.drawable.ic_corchea);
                duracionIndex = 3;
                aep_imgbtn_añadir_nota.setImageResource(R.drawable.ic_corchea);
                break;
            case "Semicorchea":
                item.setIcon(R.drawable.ic_semicorchea);
                duracionIndex = 4;
                aep_imgbtn_añadir_nota.setImageResource(R.drawable.ic_semicorchea);
                break;
            case "Fusa":
                item.setIcon(R.drawable.ic_fusa);
                duracionIndex = 5;
                aep_imgbtn_añadir_nota.setImageResource(R.drawable.ic_fusa);
                break;
            case "Semifusa":
                item.setIcon(R.drawable.ic_semifusa);
                duracionIndex = 6;
                aep_imgbtn_añadir_nota.setImageResource(R.drawable.ic_semifusa);
                break;
            case "Garrapatea":
                item.setIcon(R.drawable.ic_garrapatea);
                duracionIndex = 7;
                aep_imgbtn_añadir_nota.setImageResource(R.drawable.ic_garrapatea);
        }
        elementoIndex = 0;
        aep_imgbtn_añadir_nota.setContentDescription("Añadir " + figura.toLowerCase());
        item.setTitle("Figuras musicales, " + figura.toLowerCase());

        cerrarDialogo();
    }

    //Cambio del icono del menú de silencios musicales
    public void cambiarIconoMenuSilencio(String silencio, MenuItem item){

        switch(silencio){
            case "Silencio de redonda":
                item.setIcon(R.drawable.ic_silencio_redonda);
                duracionIndex = 0;
                aep_imgbtn_añadir_nota.setImageResource(R.drawable.ic_silencio_redonda);
                break;
            case "Silencio de blanca":
                item.setIcon(R.drawable.ic_silencio_blanca);
                duracionIndex = 1;
                aep_imgbtn_añadir_nota.setImageResource(R.drawable.ic_silencio_blanca);
                break;
            case "Silencio de negra":
                item.setIcon(R.drawable.ic_silencio_negra);
                duracionIndex = 2;
                aep_imgbtn_añadir_nota.setImageResource(R.drawable.ic_silencio_negra);
                break;
            case "Silencio de corchea":
                item.setIcon(R.drawable.ic_silencio_corchea);
                duracionIndex = 3;
                aep_imgbtn_añadir_nota.setImageResource(R.drawable.ic_silencio_corchea);
                break;
            case "Silencio de semicorchea":
                item.setIcon(R.drawable.ic_silencio_semicorchea);
                duracionIndex = 4;
                aep_imgbtn_añadir_nota.setImageResource(R.drawable.ic_silencio_semicorchea);
                break;
            case "Silencio de fusa":
                item.setIcon(R.drawable.ic_silencio_fusa);
                duracionIndex = 5;
                aep_imgbtn_añadir_nota.setImageResource(R.drawable.ic_silencio_fusa);
                break;
            case "Silencio de semifusa":
                item.setIcon(R.drawable.ic_silencio_semifusa);
                duracionIndex = 6;
                aep_imgbtn_añadir_nota.setImageResource(R.drawable.ic_silencio_semifusa);
                break;
            case "Silencio de garrapatea":
                item.setIcon(R.drawable.ic_silencio_garrapatea);
                duracionIndex = 7;
                aep_imgbtn_añadir_nota.setImageResource(R.drawable.ic_silencio_garrapatea);
        }

        elementoIndex = 1;
        aep_imgbtn_añadir_nota.setContentDescription("Añadir " + silencio.toLowerCase());
        item.setTitle("Silencios, " + silencio.toLowerCase());
        cerrarDialogo();
    }

    //Cambio del icono del menú de alteraciones musicales
    public void cambiarIconoMenuAlteracion(String alteracion, MenuItem item){

        switch(alteracion){
            case "Sostenido":
                alteracionIndex = 0;
                item.setIcon(R.drawable.ic_sostenido);
                aep_imgbtn_añadir_nota.setImageResource(R.drawable.ic_sostenido);
                break;
            case "Bemol":
                alteracionIndex = 1;
                item.setIcon(R.drawable.ic_bemol);
                aep_imgbtn_añadir_nota.setImageResource(R.drawable.ic_bemol);
                break;
        }

        elementoIndex = 2;
        item.setTitle("Alteraciones, " + alteracion.toLowerCase());
        cerrarDialogo();
    }

    //**** INICIALIZAR LISTAS ****

    //Inicializar lista de figuras musicales
    private void initFiguras() {

        figurasMusicales = new ArrayList<String>();

        figurasMusicales.add("Redonda");
        figurasMusicales.add("Blanca");
        figurasMusicales.add("Negra");
        figurasMusicales.add("Corchea");
        figurasMusicales.add("Semicorchea");
        figurasMusicales.add("Fusa");
        figurasMusicales.add("Semifusa");
        figurasMusicales.add("Garrapatea");
    }

    //Inicializar lista de silencios musicales
    private void initSilencios() {

        silenciosMusicales = new ArrayList<String>();

        silenciosMusicales.add("Silencio de redonda");
        silenciosMusicales.add("Silencio de blanca");
        silenciosMusicales.add("Silencio de negra");
        silenciosMusicales.add("Silencio de corchea");
        silenciosMusicales.add("Silencio de semicorchea");
        silenciosMusicales.add("Silencio de fusa");
        silenciosMusicales.add("Silencio de semifusa");
        silenciosMusicales.add("Silencio de garrapatea");
    }

    //Inicializar lista de alteraciones musicales
    private void initAlteraciones() {

        alteracionesMusicales = new ArrayList<String>();

        alteracionesMusicales.add("Sostenido");
        alteracionesMusicales.add("Bemol");
    }

    //Inicializar lista de alturas
    private void initAlturas() {

        altura = new ArrayList<String>();

        altura.add("Do");
        altura.add("Re");
        altura.add("Mi");
        altura.add("Fa");
        altura.add("Sol");
        altura.add("La");
        altura.add("Si");
    }

    //**** OTRAS FUNCIONALIDADES ****
    //Obtener contenido del archivo
    public void contenidoArchivoPartitura(String archivo) throws FileNotFoundException {

        try {
            FileInputStream fileInputStream = new FileInputStream(new File(getFilesDir(), archivo));
            ObjectInputStream objInputStream = new ObjectInputStream(fileInputStream);
            partituraC = (Partitura) objInputStream.readObject();
            objInputStream.close();
            fileInputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //Inflar menú del toolbar
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_toolbar,menu);
        return true;
    }

    //Guardar una partitura
    private void guardarPartitura(){

        //Reemplazar a minusculas y quitar espacios en blanco en el título de la partitura.
        String nombreArchivo = aep_et_titulo.getText().toString().trim().toLowerCase().replaceAll("\\s","_") + ".wsnd";

        partituraC.setTitulo(aep_et_titulo.getText().toString());
        partituraC.setAutor(aep_et_autor.getText().toString());
        partituraC.setNotas(partituraLDE);

        //Renombrar el archivo de partitura en caso de cambiar el título
        if(!archivoPartitura.equals(nombreArchivo)){
            //Agregar sufijo si existe un archivo con el mismo nombre
            int sufijoNombre = 2;
            while(nombreRepetido(nombreArchivo)){
                nombreArchivo = aep_et_titulo.getText().toString().trim().toLowerCase().replaceAll("\\s","_") + "_" + sufijoNombre + ".wsnd";
                sufijoNombre++;
            }
            //Descartar archivos con sufijos para renombrar
            if(!archivoPartitura.equals(nombreArchivo)) {
                File archivoActual = new File(getFilesDir(), archivoPartitura);
                File nuevoArchivo = new File(getFilesDir(), nombreArchivo);

                if (archivoActual.renameTo(nuevoArchivo)) {
                    archivoPartitura = nombreArchivo;
                }
            }
        }else{
            //Asignar nombre del archivo
            nombreArchivo = archivoPartitura;
        }

        //Escribir contenido de la partitura en el archivo
        try {
            FileOutputStream outputStream = openFileOutput(nombreArchivo, Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(outputStream);
            out.writeObject(partituraC);
            out.close();
            outputStream.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private boolean nombreRepetido(String nombre) {

        //Recuperar todos los archivos de partituras que se han guardado
        File[] archivos = getFilesDir().listFiles();

        //Añadir el nombre de los archivos a la lista partituras
        for(File archivo : archivos ){
            //Saltar el archivo que se esta editando
            if(archivoPartitura.equals(archivo.getName())){
                continue;
            }
            if(nombre.equals(archivo.getName())){
                return true;
            }
        }
        return false;

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
            aep_tv_nota.setText(partituraLDE.obtenerNotaEnPosicion(partituraIndex).toString());
        }else{
            aep_tv_nota.setText("Agregar nota");
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
            aep_tv_nota.setText(partituraLDE.obtenerNotaEnPosicion(partituraIndex).toString());
        }else{
            aep_tv_nota.setText("Agregar nota");
        }
    }

    public void subirAltura(){
        if(alturaIndex < altura.size()-1) {
            alturaIndex++;
        }
        if(nuevaNota){
            aep_tv_nota.setText(altura.get(alturaIndex));
        } else{
            if(!partituraLDE.obtenerNotaEnPosicion(partituraIndex).isSilencio()){
                partituraLDE.obtenerNotaEnPosicion(partituraIndex).setAlteracion(null);
                partituraLDE.obtenerNotaEnPosicion(partituraIndex).setAltura(altura.get(alturaIndex));
            }
            aep_tv_nota.setText(partituraLDE.obtenerNotaEnPosicion(partituraIndex).toString());
        }
    }

    public void bajarAltura(){
        if(alturaIndex > 0) {
            alturaIndex--;
        }
        if(nuevaNota){
            aep_tv_nota.setText(altura.get(alturaIndex));
        }else{
            if(!partituraLDE.obtenerNotaEnPosicion(partituraIndex).isSilencio()){
                partituraLDE.obtenerNotaEnPosicion(partituraIndex).setAlteracion(null);
                partituraLDE.obtenerNotaEnPosicion(partituraIndex).setAltura(altura.get(alturaIndex));
            }
            aep_tv_nota.setText(partituraLDE.obtenerNotaEnPosicion(partituraIndex).toString());
        }
    }

    //Agregar una nota o alteracion a la partitura
    public void añadirNota(){

        switch(elementoIndex){
            //Nota
            case 0:
                if(nuevaNota) {
                    partituraLDE.agregarNotaEnPosicion(new Nota(altura.get(alturaIndex), figurasMusicales.get(duracionIndex)), partituraIndex);
                    nuevaNota = false;
                }else{
                    System.out.println(partituraLDE.obtenerNotaEnPosicion(partituraIndex).isSilencio() + " " + partituraLDE.obtenerNotaEnPosicion(partituraIndex).getDuracion());
                    if(partituraLDE.obtenerNotaEnPosicion(partituraIndex).isSilencio()) {
                        partituraLDE.cambiarNotaEnPosicion(new Nota(altura.get(alturaIndex), figurasMusicales.get(duracionIndex)), partituraIndex);
                    }else{
                        partituraLDE.obtenerNotaEnPosicion(partituraIndex).setDuracion(figurasMusicales.get(duracionIndex));
                    }
                }
                aep_tv_nota.setText(partituraLDE.obtenerNotaEnPosicion(partituraIndex).toString());
                break;
            //Silencio
            case 1:
                if(nuevaNota) {
                    partituraLDE.agregarNotaEnPosicion(new Nota(figurasMusicales.get(duracionIndex)), partituraIndex);
                    nuevaNota = false;
                }else{
                    if(!partituraLDE.obtenerNotaEnPosicion(partituraIndex).isSilencio()){
                        partituraLDE.cambiarNotaEnPosicion(new Nota(figurasMusicales.get(duracionIndex)), partituraIndex);
                    }else{
                        partituraLDE.obtenerNotaEnPosicion(partituraIndex).setDuracion(figurasMusicales.get(duracionIndex));
                    }
                }
                aep_tv_nota.setText(partituraLDE.obtenerNotaEnPosicion(partituraIndex).toString());
                break;
            //Alteracion
            case 2:
                if(!nuevaNota && !partituraLDE.isEmpty() && !partituraLDE.obtenerNotaEnPosicion(partituraIndex).isSilencio()){
                    if((alteracionIndex == 0 && alturaIndex != 2 && alturaIndex != 6)
                    || (alteracionIndex == 1 && alturaIndex != 0 && alturaIndex != 3)) {
                        partituraLDE.obtenerNotaEnPosicion(partituraIndex).setAlteracion(alteracionesMusicales.get(alteracionIndex));
                        aep_tv_nota.setText(partituraLDE.obtenerNotaEnPosicion(partituraIndex).toString());
                    }
                }
                break;
        }
    }

    //Eliminar nota de la partitura
    public void eliminarNota(){

        if(!partituraLDE.isEmpty() && !nuevaNota){
            partituraLDE.eliminarNotaEnPosicion(partituraIndex);
            if(partituraLDE.isEmpty()){
                nuevaNota = true;
                aep_tv_nota.setText("Agregar nota");
                return;
            }else if(partituraIndex == partituraLDE.getSize()-1){
                partituraIndex--;
            }
            aep_tv_nota.setText(partituraLDE.obtenerNotaEnPosicion(partituraIndex).toString());
        }
    }

    //Acciones del toolbar
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.mt_ajustes:
                Intent intent = new Intent(this, AjustesActivity.class);
                startActivity(intent);
                break;
            case R.id.mt_info:

                break;
            case R.id.mt_guardar:
                guardarPartitura();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}