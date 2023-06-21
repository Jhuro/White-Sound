package co.edu.unipiloto.whitesound.actividades;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.WindowManager;

import co.edu.unipiloto.whitesound.R;

public class AjustesActivity extends PreferenceActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.main_preferences);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}