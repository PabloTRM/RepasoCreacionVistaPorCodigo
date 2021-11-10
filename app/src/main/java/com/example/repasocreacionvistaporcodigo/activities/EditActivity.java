package com.example.repasocreacionvistaporcodigo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.repasocreacionvistaporcodigo.R;
import com.example.repasocreacionvistaporcodigo.databinding.ActivityEditBinding;
import com.example.repasocreacionvistaporcodigo.modelos.Alumno;

public class EditActivity extends AppCompatActivity {

    private ActivityEditBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        iniciaValores();
        binding.btnGuardarEditAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = binding.txtNombreEditAlumno.getText().toString();
                String apellidos = binding.txtApellidosEditAlumno.getText().toString();

                int indiceSpinner = binding.spinnerEdit.getSelectedItemPosition();
                int idRadioButton = binding.rgGrupoEditAlumno.getCheckedRadioButtonId();

                if(!nombre.isEmpty() && !apellidos.isEmpty() &&
                        indiceSpinner!=0 && idRadioButton !=-1){
                    String ciclo = (String)binding.spinnerEdit.getSelectedItem();
                    RadioButton rb = findViewById(idRadioButton);
                    char grupo = rb.getText().toString().charAt(0);
                    Alumno a = new Alumno(nombre,apellidos, ciclo, grupo);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("ALUMNO",a);
                    bundle.putInt("POSICION",getIntent().getExtras().getInt("POSICION"));
                    Intent intent = new Intent();
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();

                }else{
                    Toast.makeText(EditActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.btnBorrarEditAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("POSICIOn",getIntent().getExtras().getInt("POSICION"));
                Intent intent = new Intent();
                intent.putExtras(bundle);
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    private void iniciaValores() {
        Alumno alumno = getIntent().getExtras().getParcelable("ALUMNO");
        binding.txtNombreEditAlumno.setText(alumno.getNombre());
        binding.txtApellidosEditAlumno.setText(alumno.getApellidos());

        switch (alumno.getCiclo()){
            case "DAM":
                binding.spinnerEdit.setSelection(1);
                break;
            case "SMR":
                binding.spinnerEdit.setSelection(2);
                break;
            case "DAU":
                binding.spinnerEdit.setSelection(3);
                break;
            case "3D":
                binding.spinnerEdit.setSelection(4);
                break;
        }
        switch (alumno.getGroup()){
            case 'A':
                binding.rbAEditAlumno.setChecked(true);
                break;
            case 'B':
                binding.rbBEditAlumno.setChecked(true);
                break;
            case 'C':
                binding.rbCEditAlumno.setChecked(true);
                break;

        }
    }
}