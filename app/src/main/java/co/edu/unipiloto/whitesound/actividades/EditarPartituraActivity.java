package co.edu.unipiloto.whitesound.actividades;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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
    private Toolbar toolbar;
    private MediaPlayer[] notas;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_partitura);
        if (Build.VERSION.SDK_INT > 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        initSoundPlayer();
        initActivity();
    }

    private void initSoundPlayer() {
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

        notas = new MediaPlayer[]{
                MediaPlayer.create(this, R.raw.c),
                MediaPlayer.create(this, R.raw.d),
                MediaPlayer.create(this, R.raw.e),
                MediaPlayer.create(this, R.raw.f),
                MediaPlayer.create(this, R.raw.g),
                MediaPlayer.create(this, R.raw.a),
                MediaPlayer.create(this, R.raw.b),
                MediaPlayer.create(this, R.raw.cs_db),
                MediaPlayer.create(this, R.raw.ds_eb),
                MediaPlayer.create(this, R.raw.fs_gb),
                MediaPlayer.create(this, R.raw.gs_ab),
                MediaPlayer.create(this, R.raw.as_bb),
                MediaPlayer.create(this, R.raw.silencio)
        };

        for(MediaPlayer mp : notas){
            mp.setOnCompletionListener(listener);
        }
    }

    public void initActivity() {
        Intent intent = getIntent();
        try {
            archivoPartitura = intent.getStringExtra(ARCHIVO);
            contenidoArchivoPartitura();
        } catch (IOException e) {
            e.printStackTrace();
        }

        toolbar = findViewById(R.id.aep_toolbar);

        //Toolbar
        toolbar.setTitle("Edición");
        setSupportActionBar(toolbar);
        handler = new Handler();
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
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
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

    public void pausarReproduccion(){
        handler.removeCallbacksAndMessages(null);
        for(MediaPlayer mp: notas){
            if(mp.isPlaying()){
                mp.pause();
                mp.seekTo(0);
            }
        }
    }

    public void reproducirNota(int nota, int duracion) {

        for(MediaPlayer mp: notas){
            if(mp.isPlaying()){
                mp.pause();
                mp.seekTo(0);
            }
        }
        
        notas[nota].start();
        handler.removeCallbacksAndMessages(null);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                notas[nota].seekTo(notas[nota].getDuration());
            }
        }, notas[nota].getDuration()/(int) Math.pow(2, duracion));

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
        for(MediaPlayer mp : notas){
            mp.release();
        }
    }
}