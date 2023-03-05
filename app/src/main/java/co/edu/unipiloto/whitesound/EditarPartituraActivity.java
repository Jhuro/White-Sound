package co.edu.unipiloto.whitesound;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
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
    private List<String> figurasMusicales;
    private List<String> silenciosMusicales;
    private List<String> alteracionesMusicales;
    private Dialog dialogo;

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
        String tituloPartitura = datos[0].replace("_", " ");
        String nombreAutor = datos[1].replace("_", " ");

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
        AdaptadorListaFigura adaptador = new AdaptadorListaFigura(this, getFiguras());
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
        AdaptadorListaSilencio adaptador = new AdaptadorListaSilencio(this, getSilencios());
        pee_lv_elementos.setAdapter(adaptador);

        pee_lv_elementos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                cambiarIconoMenuSilencio(silenciosMusicales.get(i), item);
            }
        });
    }

    private void abrirDialogoAlteraciones(MenuItem item) {

        //Mostrar diálogo
        dialogo.setContentView(R.layout.popup_elementos_edicion);
        dialogo.show();

        //Inicializar vistas del diálogo
        ListView pee_lv_elementos = (ListView) dialogo.findViewById(R.id.pee_lv_elementos);
        AdaptadorListaAlteracion adaptador = new AdaptadorListaAlteracion(this, getAlteraciones());
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
                break;
            case "Blanca":
                item.setIcon(R.drawable.ic_blanca);
                break;
            case "Negra":
                item.setIcon(R.drawable.ic_negra);
                break;
            case "Corchea":
                item.setIcon(R.drawable.ic_corchea);
                break;
            case "Semicorchea":
                item.setIcon(R.drawable.ic_semicorchea);
                break;
            case "Fusa":
                item.setIcon(R.drawable.ic_fusa);
                break;
            case "Semifusa":
                item.setIcon(R.drawable.ic_semifusa);
                break;
            case "Garrapatea":
                item.setIcon(R.drawable.ic_garrapatea);
        }

        item.setTitle("Figuras musicales, " + figura);

        cerrarDialogo();
    }

    //Cambio del icono del menú de silencios musicales
    public void cambiarIconoMenuSilencio(String silencio, MenuItem item){

        switch(silencio){
            case "Silencio de redonda":
                item.setIcon(R.drawable.ic_silencio_redonda);
                break;
            case "Silencio de blanca":
                item.setIcon(R.drawable.ic_silencio_blanca);
                break;
            case "Silencio de negra":
                item.setIcon(R.drawable.ic_silencio_negra);
                break;
            case "Silencio de corchea":
                item.setIcon(R.drawable.ic_silencio_corchea);
                break;
            case "Silencio de semicorchea":
                item.setIcon(R.drawable.ic_silencio_semicorchea);
                break;
            case "Silencio de fusa":
                item.setIcon(R.drawable.ic_silencio_fusa);
                break;
            case "Silencio de semifusa":
                item.setIcon(R.drawable.ic_silencio_semifusa);
                break;
            case "Silencio de garrapatea":
                item.setIcon(R.drawable.ic_silencio_garrapatea);
        }

        item.setTitle("Silencios, " + silencio);
        cerrarDialogo();
    }

    //Cambio del icono del menú de alteraciones musicales
    public void cambiarIconoMenuAlteracion(String alteracion, MenuItem item){

        switch(alteracion){
            case "Sostenido":
                item.setIcon(R.drawable.ic_sostenido);
                break;
            case "Bemol":
                item.setIcon(R.drawable.ic_bemol);
                break;
        }

        item.setTitle("Alteraciones, " + alteracion);
        cerrarDialogo();
    }

    //**** INICIALIZAR LISTAS ****

    //Inicializar lista de figuras musicales
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

    //Inicializar lista de silencios musicales
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

    //Inicializar lista de alteraciones musicales
    private List<String> getAlteraciones() {

        alteracionesMusicales = new ArrayList<String>();

        alteracionesMusicales.add("Sostenido");
        alteracionesMusicales.add("Bemol");

        return alteracionesMusicales;
    }

    //**** OTRAS FUNCIONALIDADES ****

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