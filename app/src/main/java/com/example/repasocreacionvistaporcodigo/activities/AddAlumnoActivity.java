package com.example.repasocreacionvistaporcodigo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.repasocreacionvistaporcodigo.R;
import com.example.repasocreacionvistaporcodigo.databinding.ActivityAddAlumnoBinding;
import com.example.repasocreacionvistaporcodigo.modelos.Alumno;

public class AddAlumnoActivity extends AppCompatActivity {

    private ActivityAddAlumnoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Estas lineas las modificacmos en la basic activity!!!
        binding= ActivityAddAlumnoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.btnCancelarAddAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();

            }
        });
        binding.btnCancelarAddAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = binding.txtNombreAddAlumno.getText().toString();
                String apellidos = binding.txtApellidosAddAlumno.getText().toString();

                int indiceSpinner = binding.spinner.getSelectedItemPosition();
                int idRadioButton = binding.rgGrupoAddAlumno.getCheckedRadioButtonId();

                if(!nombre.isEmpty() && !apellidos.isEmpty() &&
                        indiceSpinner!=0 && idRadioButton !=-1){
                    String ciclo = (String)binding.spinner.getSelectedItem();
                    RadioButton rb = findViewById(idRadioButton);
                    char grupo = rb.getText().toString().charAt(0);
                    Alumno a = new Alumno(nombre,apellidos, ciclo, grupo);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("ALUMNO",a);
                    Intent intent = new Intent();
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();

                }else{
                    Toast.makeText(AddAlumnoActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}