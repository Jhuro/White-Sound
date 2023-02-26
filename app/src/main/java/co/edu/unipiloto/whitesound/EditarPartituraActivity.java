package co.edu.unipiloto.whitesound;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class EditarPartituraActivity extends AppCompatActivity {

    public static final String ARCHIVO = "partitura.wsnd";
    private BottomNavigationView aep_menu_inferior;
    List<String> figurasMusicales;
    List<String> silenciosMusicales;
    Dialog dialogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_partitura);

        //Obtener valores intent
        Intent intent = getIntent();

        String partitura = "";
        try {
            partitura = contenidoArchivoPartitura(intent.getStringExtra(ARCHIVO));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] datos = partitura.split(" ");
        String tituloPartitura = datos[0];
        String nombreAutor = datos[1];

        //Inicializar vistas
        TextView aep_tv_titulo = (TextView) findViewById(R.id.aep_tv_titulo);
        TextView aep_tv_autor = (TextView) findViewById(R.id.aep_tv_autor);
        aep_menu_inferior = (BottomNavigationView) findViewById(R.id.aep_menu_inferior);
        dialogo = new Dialog(this);
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        //Inicializar textos de partitura
        aep_tv_titulo.setText(tituloPartitura);
        aep_tv_autor.setText(nombreAutor);

        //Menu inferior
        aep_menu_inferior.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()){

                    case R.id.miep_notas:
                        abrirDialogoFiguraMusical();
                        break;
                    case R.id.miep_silencios:
                        abrirDialogoSilencioMusical();
                        break;
                }

                return true;
            }
        });
    }

    private List<String> getFiguras() {

        figurasMusicales = new ArrayList<String>();

        figurasMusicales.add("Redonda");
        figurasMusicales.add("Blanca");
        figurasMusicales.add("Negra");
        figurasMusicales.add("Corchea");
        figurasMusicales.add("Semicorchea");
        figurasMusicales.add("Fusa");
        figurasMusicales.add("Semifusa");
        figurasMusicales.add("Garrapatea");

        return figurasMusicales;
    }

    private List<String> getSilencios() {

        silenciosMusicales = new ArrayList<String>();

        silenciosMusicales.add("Silencio de redonda");
        silenciosMusicales.add("Silencio de blanca");
        silenciosMusicales.add("Silencio de negra");
        silenciosMusicales.add("Silencio de corchea");
        silenciosMusicales.add("Silencio de semicorchea");
        silenciosMusicales.add("Silencio de fusa");
        silenciosMusicales.add("Silencio de semifusa");
        silenciosMusicales.add("Silencio de garrapatea");

        return silenciosMusicales;
    }

    //**** DIÁLOGOS ****

    //Diálogo para elegir una figura musical
    private void abrirDialogoFiguraMusical() {

        //Mostrar diálogo
        dialogo.setContentView(R.layout.popup_figura_musical);
        dialogo.show();

        //Inicializar vistas del diálogo
        ListView pfm_lv_figura_musical = (ListView) dialogo.findViewById(R.id.pfm_lv_figura_musical);
        AdaptadorListaFigura adaptador = new AdaptadorListaFigura(this, getFiguras());
        pfm_lv_figura_musical.setAdapter(adaptador);
    }

    //Diálogo para elegir un silencio musical
    private void abrirDialogoSilencioMusical() {

        //Mostrar diálogo
        dialogo.setContentView(R.layout.popup_silencio_musical);
        dialogo.show();

        //Inicializar vistas del diálogo
        ListView psm_lv_figura_musical = (ListView) dialogo.findViewById(R.id.psm_lv_silencio_musical);
        AdaptadorListaSilencio adaptador = new AdaptadorListaSilencio(this, getSilencios());
        psm_lv_figura_musical.setAdapter(adaptador);
    }

    //Cerrar cualquier diálogo
    private void cerrarDialogo() {
        dialogo.dismiss();
    }

    public String contenidoArchivoPartitura(String archivo) throws FileNotFoundException {

        File archivoPartitura = new File(getFilesDir(), archivo);
        String archivoCompleto = "";

        try {
            FileInputStream inputStream = new FileInputStream(archivoPartitura);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String linea = reader.readLine();

            while(linea!=null){
                archivoCompleto += linea;
                linea = reader.readLine();
            }

            reader.close();
            inputStream.close();

        }catch (IOException e){
            e.printStackTrace();
        }

        return archivoCompleto;
    }
}