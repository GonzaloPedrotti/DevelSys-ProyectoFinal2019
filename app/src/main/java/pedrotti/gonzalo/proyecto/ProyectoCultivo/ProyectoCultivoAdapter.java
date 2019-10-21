package pedrotti.gonzalo.proyecto.ProyectoCultivo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import pedrotti.gonzalo.proyecto.R;

public class ProyectoCultivoAdapter extends RecyclerView.Adapter<ProyectoCultivoAdapter.ProyectoCultivoViewHolder>  {

    private Context mCtx;
    private List<ProyectoCultivo> proyectoCultivoList;
    private View.OnClickListener listener;
    private ProyectoCultivoAdapter.OnItemClickListener mlistener;

    public interface OnItemClickListener{
        void OnItemClick(int position);
    }

    public ProyectoCultivoAdapter(Context mCtx, List<ProyectoCultivo> proyectoCultivoList) {
        this.mCtx = mCtx;
        this.proyectoCultivoList = proyectoCultivoList;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return proyectoCultivoList.size();
    }


    @Override
    public ProyectoCultivoAdapter.ProyectoCultivoViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.proyecto_list,null);
        return new ProyectoCultivoAdapter.ProyectoCultivoViewHolder(view);
    }

    //Clase para llenar los TextView
    @Override
    public void onBindViewHolder(ProyectoCultivoAdapter.ProyectoCultivoViewHolder holder, int position) {
        ProyectoCultivo proyectoCultivo = proyectoCultivoList.get(position);

//        holder.tvidProyecto.setText("Id del Proyecto: " + proyectoCultivo.getId());
        holder.tvNombreProyecto.setText("Nombre: " + proyectoCultivo.getNombre());
        holder.tvFechaRegistro.setText(proyectoCultivo.getFechaRegistro());
        holder.tvCultivo.setText("Cultivo: " + proyectoCultivo.getCultivo());
        holder.tvPeriodoCultivo.setText("Periodo: " + proyectoCultivo.getPeriodo());
        holder.tvEstadoProyecto.setText("Estado: " + proyectoCultivo.getEstado());

    }

    class ProyectoCultivoViewHolder extends RecyclerView.ViewHolder{

        TextView tvidProyecto , tvNombreProyecto, tvFechaRegistro,tvCultivo,tvPeriodoCultivo,tvEstadoProyecto;

        public ProyectoCultivoViewHolder(View itemView){
            super(itemView);

//            tvidProyecto = itemView.findViewById(R.id.tvProyectoId);
            tvNombreProyecto = itemView.findViewById(R.id.tvNombreProyecto);
            tvFechaRegistro = itemView.findViewById(R.id.tvFechaRegistroDet);
            tvCultivo = itemView.findViewById(R.id.tvCultivoDet);
            tvPeriodoCultivo = itemView.findViewById(R.id.tvPeriodoCultivo);
            tvEstadoProyecto = itemView.findViewById(R.id.tvEstadoProyecto);


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
    }

    public void setOnItemClickListener ( OnItemClickListener listener){
        mlistener  = listener;
    }
}
