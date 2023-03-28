package co.edu.unipiloto.whitesound.actividades;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import co.edu.unipiloto.whitesound.R;

public class AjustesActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        toolbar = (Toolbar) findViewById(R.id.aa_toolbar);

        //Toolbar
        toolbar.setTitle("Ajustes");
        setSupportActionBar(toolbar);
    }

    //Inflar menú del toolbar
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_toolbar,menu);
        menu.findItem(R.id.mt_guardar).setVisible(false);
        menu.findItem(R.id.mt_ajustes).setVisible(false);
        return true;
    }

    //Acciones del toolbar
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.mt_info:

            break;
        }
        return super.onOptionsItemSelected(item);
    }
}