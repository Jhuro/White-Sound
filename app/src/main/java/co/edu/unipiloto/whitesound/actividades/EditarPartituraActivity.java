package co.edu.unipiloto.whitesound.actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import co.edu.unipiloto.whitesound.R;
import co.edu.unipiloto.whitesound.clases.Partitura;
import co.edu.unipiloto.whitesound.fragmentos.EdicionFragment;
import co.edu.unipiloto.whitesound.fragmentos.LecturaFragment;

public class EditarPartituraActivity extends AppCompatActivity {

    public static final String ARCHIVO = "partitura.wsnd";
    private String archivoPartitura;
    private Partitura partitura, partituraTemp;
    private MediaPlayer[] notasTeclado, notasSolfeo;
    private Handler handler;
    private Toolbar toolbar;
    private int notaAnterior;
    private boolean solfeo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_partitura);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initMediaPlayer();
        initActivity();
        loadPreferences();
        if(savedInstanceState != null){
            archivoPartitura = savedInstanceState.getString("archivoPartitura");
            partitura = (Partitura) savedInstanceState.getSerializable("partitura");
            partituraTemp = (Partitura) savedInstanceState.getSerializable("partituraTemp");
        }
    }

    private void loadPreferences(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        solfeo = sharedPreferences.getBoolean("solfeo",false);
        notaAnterior = sharedPreferences.getInt("notaAnterior", 0);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("archivoPartitura", archivoPartitura);
        outState.putSerializable("partitura", partitura);
        outState.putSerializable("partituraTemp", partituraTemp);
        outState.putInt("notaAnterior", notaAnterior);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        handler.removeCallbacksAndMessages(null);
    }

    private void initMediaPlayer() {
        /*
        MediaPlayer.OnCompletionListener listener = new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.aep_fragmentContainerView)
                        .getChildFragmentManager().getFragments().get(0);
                if(fragment instanceof LecturaFragment){
                    LecturaFragment lf = (LecturaFragment) fragment;
                    if(lf.getReproduccion()){
                        lf.desplazarDerecha();
                    }
                }
            }
        };
        */

        notasTeclado = new MediaPlayer[]{
                MediaPlayer.create(this, R.raw.t_c),
                MediaPlayer.create(this, R.raw.t_d),
                MediaPlayer.create(this, R.raw.t_e),
                MediaPlayer.create(this, R.raw.t_f),
                MediaPlayer.create(this, R.raw.t_g),
                MediaPlayer.create(this, R.raw.t_a),
                MediaPlayer.create(this, R.raw.t_b),
                MediaPlayer.create(this, R.raw.t_cs_db),
                MediaPlayer.create(this, R.raw.t_ds_eb),
                MediaPlayer.create(this, R.raw.t_fs_gb),
                MediaPlayer.create(this, R.raw.t_gs_ab),
                MediaPlayer.create(this, R.raw.t_as_bb),
                MediaPlayer.create(this, R.raw.silencio)
        };

        notasSolfeo = new MediaPlayer[]{
                MediaPlayer.create(this, R.raw.s_c),
                MediaPlayer.create(this, R.raw.s_d),
                MediaPlayer.create(this, R.raw.s_e),
                MediaPlayer.create(this, R.raw.s_f),
                MediaPlayer.create(this, R.raw.s_g),
                MediaPlayer.create(this, R.raw.s_a),
                MediaPlayer.create(this, R.raw.s_b),
                MediaPlayer.create(this, R.raw.s_db),
                MediaPlayer.create(this, R.raw.s_eb),
                MediaPlayer.create(this, R.raw.s_gb),
                MediaPlayer.create(this, R.raw.s_ab),
                MediaPlayer.create(this, R.raw.s_bb),
                MediaPlayer.create(this, R.raw.s_cs),
                MediaPlayer.create(this, R.raw.s_ds),
                MediaPlayer.create(this, R.raw.s_fs),
                MediaPlayer.create(this, R.raw.s_gs),
                MediaPlayer.create(this, R.raw.s_as),
                MediaPlayer.create(this, R.raw.silencio)
        };

        /*
        for(MediaPlayer mp : notasTeclado){
            mp.setOnCompletionListener(listener);
        }

        for(MediaPlayer mp : notasSolfeo){
            mp.setOnCompletionListener(listener);
        }
        */
    }

    public void initActivity() {
        Intent intent = getIntent();
        notaAnterior = -1;
        try {
            archivoPartitura = intent.getStringExtra(ARCHIVO);
            contenidoArchivoPartitura();
        } catch (IOException e) {
            e.printStackTrace();
        }

        toolbar = findViewById(R.id.aep_toolbar);

        //Toolbar
        cambiarTitulo("Edición");
        setSupportActionBar(toolbar);
        handler = new Handler();
    }

    public void cambiarTitulo(String titulo){
        toolbar.setTitle(titulo);
    }

    //Inflar menú del toolbar
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    //**** OTRAS FUNCIONALIDADES ****
    //Obtener contenido del archivo
    public void contenidoArchivoPartitura() throws FileNotFoundException {

        try {
            FileInputStream fileInputStream = new FileInputStream(new File(getFilesDir(), archivoPartitura));
            ObjectInputStream objInputStream = new ObjectInputStream(fileInputStream);
            partitura = (Partitura) objInputStream.readObject();
            objInputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Guardar una partitura
    private void guardarPartitura() {

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.aep_fragmentContainerView)
                .getChildFragmentManager().getFragments().get(0);

        if (fragment instanceof EdicionFragment) {
            EdicionFragment ef = (EdicionFragment) fragment;
            partitura = ef.getPartitura();
        } else {
            partitura = partituraTemp;
        }

        //Reemplazar a minusculas y quitar espacios en blanco en el título de la partitura.
        String nombreArchivo = partitura.getTitulo().trim().toLowerCase().replaceAll("\\s", "_") + ".wsnd";

        //Renombrar el archivo de partitura en caso de cambiar el título
        if (!archivoPartitura.equals(nombreArchivo)) {
            //Agregar sufijo si existe un archivo con el mismo nombre
            int sufijoNombre = 2;
            while (nombreRepetido(nombreArchivo)) {
                nombreArchivo = partitura.getTitulo().trim().toLowerCase().replaceAll("\\s", "_") + "_" + sufijoNombre + ".wsnd";
                sufijoNombre++;
            }
            //Descartar archivos con sufijos para renombrar
            if (!archivoPartitura.equals(nombreArchivo)) {
                File archivoActual = new File(getFilesDir(), archivoPartitura);
                File nuevoArchivo = new File(getFilesDir(), nombreArchivo);

                if (archivoActual.renameTo(nuevoArchivo)) {
                    archivoPartitura = nombreArchivo;
                }
            }
        } else {
            //Asignar nombre del archivo
            nombreArchivo = archivoPartitura;
        }

        //Escribir contenido de la partitura en el archivo
        try {
            FileOutputStream outputStream = openFileOutput(nombreArchivo, Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(outputStream);
            out.writeObject(partitura);
            out.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean nombreRepetido(String nombre) {
        //Recuperar todos los archivos de partituras que se han guardado
        File[] archivos = getFilesDir().listFiles();

        //Añadir el nombre de los archivos a la lista partituras
        for (File archivo : archivos) {
            //Saltar el archivo que se esta editando
            if (archivoPartitura.equals(archivo.getName())) {
                continue;
            }
            if (nombre.equals(archivo.getName())) {
                return true;
            }
        }
        return false;

    }

    //Acciones del toolbar
    @SuppressLint("NonConstantResourceId")
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.mt_ajustes:
                Intent intentAjustes = new Intent(this, AjustesActivity.class);
                startActivity(intentAjustes);
                break;
            case R.id.mt_info:
                Intent intentInformacion = new Intent(this, InformacionActivity.class);
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.aep_fragmentContainerView)
                        .getChildFragmentManager().getFragments().get(0);

                if(fragment instanceof EdicionFragment){
                    intentInformacion.putExtra(InformacionActivity.PANTALLA, 5);
                }else{
                    intentInformacion.putExtra(InformacionActivity.PANTALLA, 6);
                }
                startActivity(intentInformacion);
                break;
            case R.id.mt_guardar:
                guardarPartitura();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void pausarReproduccion(){
        handler.removeCallbacksAndMessages(null);
        if(solfeo){
            for(MediaPlayer mp: notasSolfeo){
                if(mp.isPlaying()){
                    mp.pause();
                    mp.seekTo(0);
                }
            }
        }else{
            for(MediaPlayer mp: notasTeclado){
                if(mp.isPlaying()){
                    mp.pause();
                    mp.seekTo(0);
                }
            }
        }
    }

    public void reproducirNota(int nota, int duracion) {
/*
        if(solfeo){
            /*
            for(MediaPlayer mp: notasSolfeo){
                if(mp.isPlaying()){
                    mp.pause();
                    mp.seekTo(0);
                }
            }

            if(notaAnterior>=0 && notasSolfeo[notaAnterior].isPlaying()){
                notasSolfeo[notaAnterior].pause();
                notasSolfeo[notaAnterior].seekTo(0);
            }
            notaAnterior = nota;
            notasSolfeo[nota].start();
            handler.removeCallbacksAndMessages(null);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    notasSolfeo[nota].seekTo(notasSolfeo[nota].getDuration());
                }
            }, notasSolfeo[nota].getDuration()/(int) Math.pow(2, duracion));
        }else{
            /*
            for(MediaPlayer mp: notasSolfeo){
                if(mp.isPlaying()){
                    mp.pause();
                    mp.seekTo(0);
                }
            }

            if(notaAnterior>=0 && notasTeclado[notaAnterior].isPlaying()){
                notasTeclado[notaAnterior].pause();
                notasTeclado[notaAnterior].seekTo(0);
            }
            notaAnterior = nota;
            notasTeclado[nota].start();
            handler.removeCallbacksAndMessages(null);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    notasTeclado[nota].seekTo(notasTeclado[nota].getDuration());
                }
            }, notasTeclado[nota].getDuration()/(int) Math.pow(2, duracion));
        }
*/
        if(solfeo){
            /*
            for(MediaPlayer mp: notasSolfeo){
                if(mp.isPlaying()){
                    mp.pause();
                    mp.seekTo(0);
                }
            }
            */
            if(notaAnterior>=0 && notasSolfeo[notaAnterior].isPlaying()){
                notasSolfeo[notaAnterior].pause();
                notasSolfeo[notaAnterior].seekTo(0);
            }
            notaAnterior = nota;
            notasSolfeo[nota].start();
            handler.removeCallbacksAndMessages(null);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        siguienteNota(notasSolfeo[nota]);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }, notasSolfeo[nota].getDuration()/(int) Math.pow(2, duracion));
        }else{
            /*
            for(MediaPlayer mp: notasSolfeo){
                if(mp.isPlaying()){
                    mp.pause();
                    mp.seekTo(0);
                }
            }
            */
            if(notaAnterior>=0 && notasTeclado[notaAnterior].isPlaying()){
                notasTeclado[notaAnterior].pause();
                notasTeclado[notaAnterior].seekTo(0);
            }
            notaAnterior = nota;
            notasTeclado[nota].start();
            handler.removeCallbacksAndMessages(null);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        siguienteNota(notasTeclado[nota]);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }, notasTeclado[nota].getDuration()/(int) Math.pow(2, duracion));
        }

        /*
        for(MediaPlayer mp: notas){
            if(mp.isPlaying()){
                mp.stop();
                try {
                    mp.prepare();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        notas[nota].start();
         */
    }

    public void siguienteNota(MediaPlayer mediaPlayer) throws IOException {
        mediaPlayer.stop();
        mediaPlayer.prepare();
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.aep_fragmentContainerView)
                .getChildFragmentManager().getFragments().get(0);
        if(fragment instanceof LecturaFragment){
            LecturaFragment lf = (LecturaFragment) fragment;
            if(lf.getReproduccion()){
                lf.desplazarDerecha();
            }
        }
    }

    public Partitura getPartitura() {
        return partitura;
    }

    public void setPartituraTemp(Partitura p) {
        this.partituraTemp = p;
    }

    public Partitura getPartituraTemp() {
        return partituraTemp;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        handler.removeCallbacksAndMessages(null);
        for(MediaPlayer mp : notasTeclado){
            mp.release();
        }
        for(MediaPlayer mp : notasSolfeo){
            mp.release();
        }
    }

    public void onResume(){
        super.onResume();

        loadPreferences();
    }
}