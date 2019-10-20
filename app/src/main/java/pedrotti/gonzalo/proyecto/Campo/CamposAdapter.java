package pedrotti.gonzalo.proyecto.Campo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import pedrotti.gonzalo.proyecto.R;

public class CamposAdapter extends RecyclerView.Adapter<CamposAdapter.CampoViewHolder>  {


    private Context mCtx;
    private List<Campo> campoList;
    private View.OnClickListener listener;
    private OnItemClickListener mlistener;

    public interface OnItemClickListener{
        void OnItemClick(int position);
    }


    public CamposAdapter(Context mCtx, List<Campo> campoList) {
        this.mCtx = mCtx;
        this.campoList = campoList;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return campoList.size();
    }


    @Override
    public CampoViewHolder onCreateViewHolder(ViewGroup parent,
                                              int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.campo_list,null);
        return new CampoViewHolder(view);
    }



    //Clase para llenar los TextView
    @Override
    public void onBindViewHolder(CampoViewHolder holder, int position) {
        Campo campo = campoList.get(position);

//        holder.tvid.setText( "Id del Campo:" + campo.getCampo_id());
        holder.tvnombre.setText("Nombre: " + campo.getNombre());
        holder.tvlon.setText( "Longitud: " + (campo.getLon()));
        holder.tvlat.setText("Latitud: " + (campo.getLat()));
    }



    class CampoViewHolder extends RecyclerView.ViewHolder{

        TextView tvid, tvnombre, tvlat,tvlon;

        public CampoViewHolder(View itemView){
            super(itemView);

//            tvid = itemView.findViewById(R.id.tvIdCampo);
            tvnombre = itemView.findViewById(R.id.tvNombre);
            tvlat = itemView.findViewById(R.id.tvLat);
            tvlon = itemView.findViewById(R.id.tvLong);

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
