package pedrotti.gonzalo.proyecto.NuevoLote;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import pedrotti.gonzalo.proyecto.R;

public class AyudaRegistrarLote extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayuda_registrar_lote);
        this.setTitle(R.string.ayuda);
    }
}
