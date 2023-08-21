package co.edu.unipiloto.whitesound.actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.IOException;

import co.edu.unipiloto.whitesound.R;
import co.edu.unipiloto.whitesound.fragmentos.JuegosHomeFragment;
import co.edu.unipiloto.whitesound.fragmentos.JuegosRitmicosFragment;

public class JuegosActivity extends AppCompatActivity {

    private MediaPlayer[] notas;
    private MediaPlayer reloj;
    private Handler handler;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juegos);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initMediaPlayer();
        initActivity();
    }

    public void initActivity(){
        toolbar = findViewById(R.id.aj_toolbar);
        cambiarTitulo("Juegos");
        setSupportActionBar(toolbar);
        handler = new Handler();
    }

    public void cambiarTitulo(String titulo){
        toolbar.setTitle(titulo);
    }

    //Inflar menú del toolbar
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        menu.findItem(R.id.mt_guardar).setVisible(false);
        return true;
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
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.aj_fragmentContainerView)
                        .getChildFragmentManager().getFragments().get(0);

                if(fragment instanceof JuegosHomeFragment){
                    intentInformacion.putExtra(InformacionActivity.PANTALLA, 1);
                } else{
                    JuegosRitmicosFragment jrf = (JuegosRitmicosFragment) fragment;
                    switch(jrf.getEleccion()){
                        case 1:
                            intentInformacion.putExtra(InformacionActivity.PANTALLA, 2);
                            break;
                        case 2:
                            intentInformacion.putExtra(InformacionActivity.PANTALLA, 3);
                            break;
                        case 3:
                            intentInformacion.putExtra(InformacionActivity.PANTALLA, 4);
                            break;
                    }
                }
                startActivity(intentInformacion);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initMediaPlayer() {

        notas = new MediaPlayer[]{
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
        };

        reloj = MediaPlayer.create(this, R.raw.reloj);
    }

    public void reproducirReloj() throws IOException {

        if(reloj.isPlaying()){
            reloj.stop();
            reloj.prepare();
        }
        reloj.start();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();

        handler.removeCallbacksAndMessages(null);
        reloj.release();
        for(MediaPlayer mp : notas){
            mp.release();
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        handler.removeCallbacksAndMessages(null);
    }
}