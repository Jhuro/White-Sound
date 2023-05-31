package co.edu.unipiloto.whitesound.actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import co.edu.unipiloto.whitesound.R;

public class JuegosActivity extends AppCompatActivity {

    private MediaPlayer[] notas;
    private Handler handler;

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
        Toolbar toolbar = findViewById(R.id.aj_toolbar);
        toolbar.setTitle("Juegos");
        setSupportActionBar(toolbar);
        handler = new Handler();
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
                Intent intent = new Intent(this, AjustesActivity.class);
                startActivity(intent);
                break;
            case R.id.mt_info:

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