package co.edu.unipiloto.whitesound.actividades;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import co.edu.unipiloto.whitesound.R;
import co.edu.unipiloto.whitesound.adaptadores.AdaptadorListaPartitura;
import co.edu.unipiloto.whitesound.clases.Partitura;

public class GestionarPartituraActivity extends AppCompatActivity {

    private Button agp_btn_crear_partitura;
    private Toolbar toolbar;
    private TextView agp_tv_partituras_anteriores;
    private ListView agp_lv_partituras;
    private List<String> archivos;
    private Dialog dialogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT > 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_gestionar_partituras);

        //Inicializar vistas
        agp_btn_crear_partitura = (Button) findViewById(R.id.agp_btn_crear_partitura);
        agp_tv_partituras_anteriores = (TextView) findViewById(R.id.agp_tv_partituras_anteriores);
        agp_lv_partituras = (ListView) findViewById(R.id.agp_lv_partituras);
        dialogo = new Dialog(this);
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        toolbar = (Toolbar) findViewById(R.id.agp_toolbar);

        //Toolbar
        toolbar.setTitle("Inicio");
        setSupportActionBar(toolbar);

        //Cargar la lista de partituras almacenadas en el dispositivo
        recargarListaPartituras();

        //Item click listener de la lista de partituras
        agp_lv_partituras.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                abrirDialogoOpcionesPartitura(i);
            }
        });

        //Click listener del botón que abre el diálogo para crear una nueva partitura
        agp_btn_crear_partitura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirDialogoNuevaPartitura();
            }
        });
    }

    //**** DIÁLOGOS ****

    //Diálogo para crear una nueva partitura
    private void abrirDialogoNuevaPartitura() {

        //Mostrar diálogo
        dialogo.setContentView(R.layout.popup_datos_partitura);
        dialogo.show();

        //Inicializar vistas del diálogo
        Button pdp_btn_cancelar = (Button) dialogo.findViewById(R.id.pdp_btn_cancelar);
        Button pdp_btn_aceptar = (Button) dialogo.findViewById(R.id.pdp_btn_aceptar);

        //Click listener del botón para cancelar la creación de una nueva partitura
        pdp_btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cerrarDialogo();
            }
        });

        //Click listener del botón para crear una nueva partitura
        pdp_btn_aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crearNuevaPartitura();
            }
        });
    }

    //Diálogo para seleccionar la acción a tomar con una partitura
    private void abrirDialogoOpcionesPartitura(int i) {

        //Mostrar diálogo
        dialogo.setContentView(R.layout.popup_opciones_partitura);
        dialogo.show();

        //Inicializar vistas del diálogo
        ImageButton pop_imgbtn_eliminar_partitura = (ImageButton) dialogo.findViewById(R.id.pop_imgbtn_eliminar_partitura);
        ImageButton pop_imgbtn_editar_partitura = (ImageButton) dialogo.findViewById(R.id.pop_imgbtn_editar_partitura);
        Button pop_btn_cancelar = (Button) dialogo.findViewById(R.id.pop_btn_cancelar);

        //Click listener del botón para eliminar una partitura
        pop_imgbtn_eliminar_partitura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarPartitura(i);
            }
        });

        //Click listener del botón para editar una partitura
        pop_imgbtn_editar_partitura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editarPartitura(i);
            }
        });

        //Click listener del botón para cancelar la edición o eliminación de una nueva partitura
        pop_btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cerrarDialogo();
            }
        });
    }

    //Cerrar cualquier diálogo
    private void cerrarDialogo() {
        dialogo.dismiss();
    }

    //**** CRUD PARTITURAS ****

    //Crear una nueva partitura
    private void crearNuevaPartitura() {

        //Inicializar vistas del dialogo
        TextView pdp_et_nombre_autor = (EditText) dialogo.findViewById(R.id.pdp_et_nombre_autor);
        TextView pdp_et_nombre_partitura = (EditText) dialogo.findViewById(R.id.pdp_et_nombre_partitura);

        //Obtener texto de los edit text del diálogo
        String nombreAutor = pdp_et_nombre_autor.getText().toString();
        String nombrePartitura = pdp_et_nombre_partitura.getText().toString();

        //Caso en el que no se pone un autor
        if (nombreAutor.length() == 0) {
            nombreAutor = "Sin autor";
        }

        //Caso en que no se pone título a la partitura
        if (nombrePartitura.length() == 0) {
            nombrePartitura = "Sin título";
        }

        //Guardar archivo de la nueva partitura
        guardarPartitura(nombrePartitura, nombreAutor);

        //Abrir editor de la última partitura creada
        editarPartitura(-1);
    }

    //Editar una partitura
    private void editarPartitura(int i) {

        //Inicializar variable que contiene el nombre del archivo de la partitura
        String nombreArchivo = "";

        //Asignar nombre del archvio de partitura que se va a editar
        if (i >= 0) {
            nombreArchivo = archivos.get(i);
        } else {
            nombreArchivo = archivos.get(archivos.size() - 1);
        }

        //Inicializar el intent de la actividad editor de partitura
        Intent intent = new Intent(this, EditarPartituraActivity.class);
        intent.putExtra(EditarPartituraActivity.ARCHIVO, nombreArchivo);

        //Cerrar el diálogo para crear una nueva partitura
        cerrarDialogo();

        //Iniciar actividad editor de partitura
        startActivity(intent);
    }

    private void guardarPartitura(String tituloPartitura, String autor) {

        //Reemplazar a minusculas y quitar espacios en blanco en el título de la partitura.
        //Asignar nombre del archivo
        String nombreArchivo = tituloPartitura.trim().toLowerCase().replaceAll("\\s", "_") + ".wsnd";

        //Agregar sufijo si existe un archivo con el mismo nombre
        int sufijoNombre = 2;
        while (archivos.contains(nombreArchivo)) {
            nombreArchivo = tituloPartitura.trim().toLowerCase().replaceAll("\\s", "_") + "_" + sufijoNombre + ".wsnd";
            sufijoNombre++;
        }

        //Escribir contenido de la partitura en el archivo
        try {
            FileOutputStream outputStream = openFileOutput(nombreArchivo, Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(outputStream);
            out.writeObject(new Partitura(tituloPartitura, autor));
            out.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Recargar la lista de partituras
        recargarListaPartituras();
    }

    //Eliminar una partitura
    private void eliminarPartitura(int i) {

        //Inicializar nombre del archivo de partitura a eliminar
        String nombreArchivo = archivos.get(i);

        //Obtener archivo que se desea eliminar
        File archivoPartitura = new File(getFilesDir(), nombreArchivo);

        //Eliminar archivo
        archivoPartitura.delete();

        //Recargar lista de partituras
        recargarListaPartituras();

        //Cerrar diálogo de opciones de partitura
        cerrarDialogo();
    }

    //**** OTRAS FUNCIONES ****

    //Obtener lista de partituras
    private List<Partitura> getPartituras() {

        //Recuperar todos los archivos de partituras que se han guardado
        File[] archivos = getFilesDir().listFiles();
        this.archivos = new ArrayList<>();

        ArrayList partituras = new ArrayList<Partitura>();
        //Añadir el nombre de los archivos a la lista partituras
        try {
            for (File archivo : archivos) {
                this.archivos.add(archivo.getName());
                FileInputStream fileInputStream = new FileInputStream(archivo);
                ObjectInputStream objInputStream = new ObjectInputStream(fileInputStream);
                partituras.add(objInputStream.readObject());
                objInputStream.close();
                fileInputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return partituras;
    }

    //Recargar actividad
    public void onResume() {

        super.onResume();

        recargarListaPartituras();
    }

    //Recargar la lista de partituras en el dispositivo
    public void recargarListaPartituras() {

        AdaptadorListaPartitura adaptador = new AdaptadorListaPartitura(this, getPartituras());
        agp_lv_partituras.setAdapter(adaptador);

        if (!archivos.isEmpty()) {
            agp_tv_partituras_anteriores.setVisibility(View.VISIBLE);
        } else {
            agp_tv_partituras_anteriores.setVisibility(View.INVISIBLE);
        }
    }

    //Inflar menú del toolbar
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        menu.findItem(R.id.mt_guardar).setVisible(false);
        return true;
    }

    //Acciones del toolbar
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
}