package co.edu.unipiloto.whitesound.fragmentos;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import co.edu.unipiloto.whitesound.R;


public class JuegosHomeFragment extends Fragment {

    public JuegosHomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_juegos_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
    }

    public void initViews(View view){

        final NavController navController = Navigation.findNavController(view);

        Button fjh_btn_altura = view.findViewById(R.id.fjh_btn_altura);
        Button fjh_btn_duracion = view.findViewById(R.id.fjh_btn_duracion);
        Button fjh_btn_combinado = view.findViewById(R.id.fjh_btn_combinado);

        fjh_btn_altura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("eleccion", 1);
                navController.navigate(R.id.juegosRitmicosFragment, bundle);
            }
        });

        fjh_btn_duracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("eleccion", 2);
                navController.navigate(R.id.juegosRitmicosFragment, bundle);
            }
        });

        fjh_btn_combinado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("eleccion", 3);
                navController.navigate(R.id.juegosRitmicosFragment, bundle);
            }
        });
    }
}