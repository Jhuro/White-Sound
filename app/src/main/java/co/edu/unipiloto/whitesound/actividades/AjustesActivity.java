package co.edu.unipiloto.whitesound.actividades;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import co.edu.unipiloto.whitesound.R;

public class AjustesActivity extends PreferenceActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.main_preferences);

    }
}