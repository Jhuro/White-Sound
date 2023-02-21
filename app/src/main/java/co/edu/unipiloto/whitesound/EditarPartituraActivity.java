package co.edu.unipiloto.whitesound;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class EditarPartituraActivity extends AppCompatActivity {

    public static final String ARCHIVO = "partitura.wsnd";

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

        //Inicializar textos de partitura
        aep_tv_titulo.setText(tituloPartitura);
        aep_tv_autor.setText(nombreAutor);
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