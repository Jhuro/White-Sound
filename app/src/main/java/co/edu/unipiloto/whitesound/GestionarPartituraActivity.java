package co.edu.unipiloto.whitesound;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class GestionarPartituraActivity extends AppCompatActivity {

    ListView agp_lv_partituras;
    TextView agp_tv_partituras_anteriores;
    List<Partitura> partituras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_partituras);

        //Inicializar vistas
        agp_lv_partituras = (ListView) findViewById(R.id.agp_lv_partituras);
        agp_tv_partituras_anteriores = (TextView) findViewById(R.id.agp_tv_partituras_anteriores);

        //Preparar lista de partituras
        AdaptadorListaPartitura adaptador = new AdaptadorListaPartitura(this, getPartituras());
        agp_lv_partituras.setAdapter(adaptador);

        //Texto de partituras anteriores
        if(!partituras.isEmpty()){
            agp_tv_partituras_anteriores.setVisibility(View.VISIBLE);
        }

        //Item click listener de la lista de partituras
        agp_lv_partituras.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Partitura partitura = partituras.get(i);
                Toast.makeText(GestionarPartituraActivity.this, partitura.nombre, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Obtener lista de partituras
    private List<Partitura> getPartituras() {

        partituras = new ArrayList<>();

        //Recuperar partituras

        partituras.add(new Partitura("la 1 sinfonia","Pepe"));
        partituras.add(new Partitura("la 2 sinfonia","Pepe"));
        partituras.add(new Partitura("la 3 sinfonia","Pepe"));
        partituras.add(new Partitura("la 4 sinfonia","Pepe"));
        partituras.add(new Partitura("la 5 sinfonia","Pepe"));
        partituras.add(new Partitura("la 6 sinfonia","Pepe"));
        partituras.add(new Partitura("la 7 sinfonia","Pepe"));
        partituras.add(new Partitura("la 8 sinfonia","Pepe"));
        partituras.add(new Partitura("la 9 sinfonia","Pepe"));
        partituras.add(new Partitura("la 10 sinfonia","Pepe"));
        partituras.add(new Partitura("la 1 sinfonia","Pepe"));
        partituras.add(new Partitura("la 2 sinfonia","Pepe"));
        partituras.add(new Partitura("la 3 sinfonia","Pepe"));
        partituras.add(new Partitura("la 4 sinfonia","Pepe"));
        partituras.add(new Partitura("la 5 sinfonia","Pepe"));
        partituras.add(new Partitura("la 6 sinfonia","Pepe"));
        partituras.add(new Partitura("la 7 sinfonia","Pepe"));
        partituras.add(new Partitura("la 8 sinfonia","Pepe"));
        partituras.add(new Partitura("la 9 sinfonia","Pepe"));
        partituras.add(new Partitura("la 10 sinfonia","Pepe"));

        return partituras;

    }
}