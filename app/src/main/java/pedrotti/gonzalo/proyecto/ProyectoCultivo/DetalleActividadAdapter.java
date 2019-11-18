package pedrotti.gonzalo.proyecto.ProyectoCultivo;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pedrotti.gonzalo.proyecto.R;


public class DetalleActividadAdapter extends RecyclerView.Adapter<DetalleActividadAdapter.DetalleProyectoViewHolder  >

    //El código comentado en esta clase se utiliza para agregar el onClick a algun elemento dentro de el adaptador

{

    //implements View.OnClickListener

    private Context mCtx;
    private List<DetalleActividad> detalleActividadList;
    private View.OnClickListener listener;
    private DetalleActividadAdapter.OnItemClickListener mlistener;


    public interface OnItemClickListener{
        void OnItemClick(int position);
    }

    public DetalleActividadAdapter(Context mCtx, List<DetalleActividad> detalleActividadList) {
        this.mCtx = mCtx;
        this.detalleActividadList =detalleActividadList;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return detalleActividadList.size();
    }


    @Override
    public DetalleActividadAdapter.DetalleProyectoViewHolder onCreateViewHolder(ViewGroup parent,
                                                                               int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.actividades_list,null);
        return new DetalleActividadAdapter.DetalleProyectoViewHolder(view);
    }

    //Clase para llenar los TextView
    @Override
    public void onBindViewHolder(DetalleActividadAdapter.DetalleProyectoViewHolder holder, int position) {

        DetalleActividad detalleActividad = detalleActividadList.get(position);

        int pos =0;
        try{
            pos = position%2;

        }catch (Exception e){
            Toast.makeText(mCtx, "Error", Toast.LENGTH_SHORT).show();
        }

        holder.tvActividad.setText(detalleActividad.getActividad());
        holder.tvFechaInicio.setText(detalleActividad.getInicio());
        holder.tvFechaFin.setText(detalleActividad.getFin());
        holder.tvEstadoActividad.setText(detalleActividad.getEstado());


        if(pos== 0){
            holder.tvActividad.setBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.tvFechaInicio.setBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.tvFechaFin.setBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.tvEstadoActividad.setBackgroundColor(Color.parseColor("#FFFFFF"));

        }else{
            holder.tvActividad.setBackgroundColor(Color.parseColor("#00C3FF"));
            holder.tvFechaInicio.setBackgroundColor(Color.parseColor("#00C3FF"));
            holder.tvFechaFin.setBackgroundColor(Color.parseColor("#00C3FF"));
            holder.tvEstadoActividad.setBackgroundColor(Color.parseColor("#00C3FF"));
        }

//      holder.setOnClickListener();
    }


    class DetalleProyectoViewHolder extends RecyclerView.ViewHolder  {

        //implements View.OnClickListener

        TextView tvActividad, tvFechaInicio,tvFechaFin,tvEstadoActividad, tvAccion;
        Context context;


        public DetalleProyectoViewHolder(View itemView){

            super(itemView);
           context =  itemView.getContext();

            tvActividad = itemView.findViewById(R.id.tvNombreActividad);
            tvFechaInicio = itemView.findViewById(R.id.tvFechaInicio);
            tvFechaFin = itemView.findViewById(R.id.tvFechaFin);
            tvEstadoActividad = itemView.findViewById(R.id.tvEstadoActividad);
            //Se comenta, ya se quito la implementacion de un tv con opciones
//            tvAccion = itemView.findViewById(R.id.tvAccion);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mlistener!= null){
                        int position  = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mlistener.OnItemClick(position);
                        }
                    }
                }
            });
        }

//        //prueba
//        void setOnClickListener(){
//            tvAccion.setOnClickListener(this);
//        }

//        @Override
//        public void onClick(View v) {
//            switch (v.getId()){
////                case R.id.tvAccion:
////                    Intent intent = new Intent(context, InicioActividad.class);
////                    intent.putExtra("headerCode",tvActividad.getText());
////                    context.startActivity(intent);
////                break;
//        }
//        }
    }

    public void setOnItemClickListener (OnItemClickListener listener){
        mlistener  = listener;
    }

}
