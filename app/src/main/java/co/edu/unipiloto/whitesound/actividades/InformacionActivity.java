package co.edu.unipiloto.whitesound.actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Button;
import android.widget.TextView;

import co.edu.unipiloto.whitesound.R;
import co.edu.unipiloto.whitesound.clases.Partitura;

public class InformacionActivity extends AppCompatActivity {

    public static final String PANTALLA = "pantalla";
    private int posicion;
    private boolean finalizar;
    private Button ai_btn_siguiente;
    private TextView ai_tv_titulo, ai_tv_descripcion;
    private String[] titulos, descripciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initActivity();
        if(savedInstanceState != null){
            posicion = savedInstanceState.getInt("posicion");
            finalizar = savedInstanceState.getBoolean("finalizar");

            ai_tv_titulo.setText(titulos[posicion]);
            ai_tv_descripcion.setText(descripciones[posicion]);

            if(finalizar){
                ai_btn_siguiente.setText("Finalizar");
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("posicion", posicion);
        outState.putBoolean("finalizar", finalizar);
    }

    private void initActivity(){
        Intent intent = getIntent();
        int pantalla = intent.getIntExtra(PANTALLA, -1);
        posicion = 0;
        finalizar = false;

        ai_tv_titulo = findViewById(R.id.ai_tv_titulo);
        ai_tv_descripcion = findViewById(R.id.ai_tv_descripcion);
        ai_btn_siguiente = findViewById(R.id.ai_btn_siguiente);
        Button ai_btn_atras = findViewById(R.id.ai_btn_atras);

        ai_tv_descripcion.setMovementMethod(new ScrollingMovementMethod());
        Resources resources = getResources();
        switch (pantalla) {
            case 0:
                titulos = resources.getStringArray(R.array.titulos_info_inicio);
                descripciones = resources.getStringArray(R.array.descripciones_info_inicio);
                break;
            case 1:
                titulos = resources.getStringArray(R.array.titulos_info_juegos);
                descripciones = resources.getStringArray(R.array.descripciones_info_juegos);
                break;
            case 2:
                titulos = resources.getStringArray(R.array.titulos_info_practica_altura);
                descripciones = resources.getStringArray(R.array.descripciones_info_practica_altura);
                break;
            case 3:
                titulos = resources.getStringArray(R.array.titulos_info_practica_duracion);
                descripciones = resources.getStringArray(R.array.descripciones_info_practica_duracion);
                break;
            case 4:
                titulos = resources.getStringArray(R.array.titulos_info_practica_combinada);
                descripciones = resources.getStringArray(R.array.descripciones_info_practica_combinada);
                break;
            case 5:
                titulos = resources.getStringArray(R.array.titulos_info_edicion);
                descripciones = resources.getStringArray(R.array.descripciones_info_edicion);
                break;
            case 6:
                titulos = resources.getStringArray(R.array.titulos_info_lectura);
                descripciones = resources.getStringArray(R.array.descripciones_info_lectura);
                break;
        }

        ai_tv_titulo.setText(titulos[posicion]);
        ai_tv_descripcion.setText(descripciones[posicion]);

        ai_btn_siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                siguientePagina();
            }
        });

        ai_btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paginaAnterior();
            }
        });
    }

    private void siguientePagina(){
        if(finalizar){
            finish();
        }else{
            posicion++;
            ai_tv_titulo.setText(titulos[posicion]);
            ai_tv_descripcion.setText(descripciones[posicion]);
            if(posicion == titulos.length-1){
                finalizar = true;
                ai_btn_siguiente.setText("Finalizar");
            }
            ai_tv_descripcion.scrollTo(0,0);
            ai_tv_titulo.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED);
        }
    }

    private void paginaAnterior(){
        if(posicion > 0) {
            posicion--;
            ai_tv_titulo.setText(titulos[posicion]);
            ai_tv_descripcion.setText(descripciones[posicion]);
            if(finalizar){
                finalizar = true;
                ai_btn_siguiente.setText("Siguiente");
            }
            ai_tv_descripcion.scrollTo(0,0);
            ai_tv_titulo.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED);
        }else{
            finish();
        }
    }
}