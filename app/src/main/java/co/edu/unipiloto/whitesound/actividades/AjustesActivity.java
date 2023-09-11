package co.edu.unipiloto.whitesound.actividades;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import co.edu.unipiloto.whitesound.R;

public class AjustesActivity extends PreferenceActivity {

    private AppCompatDelegate mDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.main_preferences);
        setContentView(R.layout.activity_ajustes);

        Toolbar toolbar = findViewById(R.id.aa_toolbar);
        toolbar.setTitle("Ajustes");
        setSupportActionBar(toolbar);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void setSupportActionBar(@Nullable Toolbar toolbar) {
        getDelegate().setSupportActionBar(toolbar);
    }

    //Inflar menú del toolbar
    public boolean onCreateOptionsMenu(Menu menu) {

        getDelegate().getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        menu.findItem(R.id.mt_guardar).setVisible(false);
        menu.findItem(R.id.mt_ajustes).setVisible(false);
        menu.findItem(R.id.mt_inicio).setVisible(false);
        menu.findItem(R.id.mt_salir).setVisible(false);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.mt_info:
                Intent intentInformacion = new Intent(this, InformacionActivity.class);
                intentInformacion.putExtra(InformacionActivity.PANTALLA, 7);
                startActivity(intentInformacion);
            case R.id.mt_volver:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private AppCompatDelegate getDelegate() {
        if (mDelegate == null) {
            mDelegate = AppCompatDelegate.create(this, null);
        }
        return mDelegate;
    }
}