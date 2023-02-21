package co.edu.unipiloto.whitesound;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GestionarPartituraActivity extends AppCompatActivity {

    Button agp_btn_crear_partitura;
    TextView agp_tv_partituras_anteriores;
    ListView agp_lv_partituras;
    List<String> partituras;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_partituras);

        //Inicializar vistas
        agp_btn_crear_partitura = (Button) findViewById(R.id.agp_btn_crear_partitura);
        agp_tv_partituras_anteriores = (TextView) findViewById(R.id.agp_tv_partituras_anteriores);
        agp_lv_partituras = (ListView) findViewById(R.id.agp_lv_partituras);
        dialog = new Dialog(this);

        //Preparar lista de partituras
        AdaptadorListaPartitura adaptador = new AdaptadorListaPartitura(this, getPartituras());
        agp_lv_partituras.setAdapter(adaptador);

        //Texto de partituras anteriores
        if (!partituras.isEmpty()) {
            agp_tv_partituras_anteriores.setVisibility(View.VISIBLE);
        }

        //Item click listener de la lista de partituras
        agp_lv_partituras.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                abrirOpcionesPartitura(i);
            }
        });

        //Click listener del botón para crear una nueva partitura
        agp_btn_crear_partitura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirDialogo();
            }
        });
    }

    private void abrirOpcionesPartitura(int i) {

        String partitura = partituras.get(i);
        Intent intent = new Intent(this, EditarPartituraActivity.class);
        intent.putExtra(EditarPartituraActivity.ARCHIVO,partitura);
        startActivity(intent);
    }

    //Abrir dialogo de datos de la partitura
    private void abrirDialogo() {

        //Mostrar dialogo
        dialog.setContentView(R.layout.popup_datos_partitura);
        dialog.show();

        //Inicializar vistas del dialogo
        Button pdp_btn_cancelar = (Button) dialog.findViewById(R.id.pdp_btn_cancelar);
        Button pdp_btn_aceptar = (Button) dialog.findViewById(R.id.pdp_btn_aceptar);

        pdp_btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cerrarDialogo();
            }
        });

        pdp_btn_aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crearNuevaPartitura();
            }
        });
    }

    private void crearNuevaPartitura() {

        //Inicializar vistas del dialogo
        TextView pdp_et_nombre_autor = (EditText) dialog.findViewById(R.id.pdp_et_nombre_autor);
        TextView pdp_et_nombre_partitura = (EditText) dialog.findViewById(R.id.pdp_et_nombre_partitura);

        //Obtener texto de los editText
        String nombreAutor = pdp_et_nombre_autor.getText().toString();
        String nombrePartitura = pdp_et_nombre_partitura.getText().toString();

        if(nombreAutor.length()==0){
            nombreAutor = "Sin autor";
        }

        if(nombrePartitura.length()==0){
            nombrePartitura = "Sin título";
        }

        String nombreArchivo = guardarPartitura(nombrePartitura, nombreAutor);

        //Iniciar actividad editor de partitura
        Intent intent = new Intent(this, EditarPartituraActivity.class);

        intent.putExtra(EditarPartituraActivity.ARCHIVO, nombreArchivo);

        startActivity(intent);
    }

    private void cerrarDialogo() {
        dialog.dismiss();
    }

    public String guardarPartitura(String tituloPartitura, String autor){

        //Reemplazar a minusculas y quitar espacios en blanco en el título de la partitura.
        //Asignar nombre del archivo
        String nombreArchivo = tituloPartitura.toLowerCase().replaceAll("\\s","_") + ".wsnd";

        String contenido =   tituloPartitura.replaceAll("\\s","_") + " "
                + autor.replaceAll("\\s", "_");

        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(nombreArchivo, Context.MODE_PRIVATE);
            outputStream.write(contenido.getBytes());
            outputStream.flush();
            outputStream.close();
        }catch(Exception e){
            e.printStackTrace();
        }

        return nombreArchivo;
    }

    //Obtener lista de partituras
    private List<String> getPartituras() {

        //Recuperar partituras
        File[] archivos = getFilesDir().listFiles();
        partituras = new ArrayList<>();

        for(File archivo : archivos ){
            partituras.add(archivo.getName());
        }

        return partituras;

    }
}