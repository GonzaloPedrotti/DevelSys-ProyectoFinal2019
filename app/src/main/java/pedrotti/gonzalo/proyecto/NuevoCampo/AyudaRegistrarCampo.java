package pedrotti.gonzalo.proyecto.NuevoCampo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import pedrotti.gonzalo.proyecto.R;

public class AyudaRegistrarCampo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayuda_registrar_campo);
        this.setTitle(R.string.ayuda);
    }
}
