package com.example.repasocreacionvistaporcodigo;

import android.content.Intent;
import android.os.Bundle;

import com.example.repasocreacionvistaporcodigo.activities.AddAlumnoActivity;
import com.example.repasocreacionvistaporcodigo.activities.EditActivity;
import com.example.repasocreacionvistaporcodigo.modelos.Alumno;
import com.google.android.material.snackbar.Snackbar;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.repasocreacionvistaporcodigo.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //private AppBarConfiguration appBarConfiguration;


    /**
     * conjunto de datos para los alumnos->ArrayList
     * contenedor para mostrar todos los alumnos -> Scroll ->LinearLayout (ScrollView)
     * plantilla con formato para cada alumno a mostrar -> xml (específico para cada alumno)
     */
    private ActivityMainBinding binding;

    private ArrayList<Alumno>alumnos;

    private ActivityResultLauncher<Intent> launcherAdd;

    private ActivityResultLauncher<Intent> launcherEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        alumnos = new ArrayList<>();
        launcherEdit = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode()== RESULT_OK){
                    int posicion = result.getData().getExtras().getInt("POSICION");
                    Alumno alumno = result.getData().getExtras().getParcelable("ALUMNO");
                    if(alumno ==null){
                        alumnos.remove(posicion);
                    }else{
                        alumnos.set(posicion,alumno);
                    }
                    mostrarAlumnos();
                }
            }
        });
        launcherAdd = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == RESULT_OK){
                    Alumno a = result.getData().getExtras().getParcelable("ALUMNO");
                    alumnos.add(a);
                    mostrarAlumnos();
                }
            }
        });


        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launcherAdd.launch(new Intent(MainActivity.this, AddAlumnoActivity.class));

            }
        });
        mostrarAlumnos();
    }

    private void mostrarAlumnos() {
        binding.contentView.contenedor.removeAllViews();
        int contador =0;
        for (Alumno a: alumnos){
            // 1- Instancia la vista (card)
            View card = LayoutInflater.from(this).inflate(R.layout.alumno_card, null);

            TextView lblNombre = card.findViewById(R.id.lblNombreAlumnoCard);
            TextView lblApellidos = card.findViewById(R.id.lblApellidosAlumnoCard);
            TextView lblCiclo = card.findViewById(R.id.lblCicloAlumnoCard);
            TextView lblGrupo = card.findViewById(R.id.lblGrupoAlumnoCard);

            int finalContador = contador;
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("Alumno",a);
                    bundle.putInt("POSISICION", finalContador);
                    Intent intent= new Intent(MainActivity.this, EditActivity.class);
                    intent.putExtras(bundle);
                    launcherEdit.launch(intent);
                }
            });
            contador++;
            // 2- rellenar los datos
        lblNombre.setText(a.getNombre());
        lblApellidos.setText(a.getApellidos());
        lblCiclo.setText(a.getCiclo());
        lblGrupo.setText(String.valueOf(a.getGroup()));



            // 3- agregar el card al contedor (acuerdate de darle al contendor un Id )

            binding.contentView.contenedor.addView(card);
        }

    }

}