package pedrotti.gonzalo.proyecto.Fertilizacion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

import pedrotti.gonzalo.proyecto.R;

public class Fertilizacion extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ArrayList<TipoFertilizante> fertilizantesList;
    Spinner spFertilizantes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fertilizacion);
        setTitle("Fertilización");
        spFertilizantes = (Spinner)findViewById(R.id.spFertilizantes);
        spFertilizantes.setOnItemSelectedListener(this);
        fertilizantesList = new ArrayList<>();
        llenarSpinnerFertilizantes();

    }

    void llenarSpinnerFertilizantes(){
        TipoFertilizante t = new TipoFertilizante();
        t.setNombre("Fertilizante Granulado");
        t.setDescripcion("Presente en Forma de Gránulos");

        fertilizantesList.add(t);

//        TipoFertilizante t1 = new TipoFertilizante();
//        t.setNombre("Fertilizante En Polvo");
//        t.setDescripcion("Presente en Forma de Polvo");
//
//        fertilizantesList.add(t1);
//
//        TipoFertilizante t2 = new TipoFertilizante();
//        t.setNombre("Fertilizante Líquido");
//        t.setDescripcion("Presente en Forma de Líquido");
//
//        fertilizantesList.add(t2);

        ArrayAdapter<TipoFertilizante> adapter = new ArrayAdapter<TipoFertilizante>(this,android.R.layout.simple_dropdown_item_1line,fertilizantesList);
        spFertilizantes.setAdapter(adapter);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
