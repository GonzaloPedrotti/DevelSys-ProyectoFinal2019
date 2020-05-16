package pedrotti.gonzalo.proyecto.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pedrotti.gonzalo.proyecto.Modelo.Campo;
import pedrotti.gonzalo.proyecto.R;

public class CamposAdapter extends RecyclerView.Adapter<CamposAdapter.CampoViewHolder> implements View.OnClickListener  {


    private ArrayList<Campo> campoList;
    private OnClickListener listener;
    private OnItemClickListener mlistener;

    //Esta se agrega
    List<Campo> copycampos = new ArrayList<>();


    public CamposAdapter(ArrayList<Campo> campoList) {
        this.campoList = campoList;
    }

    public interface OnItemClickListener{
        void OnItemClick(int position);
    }


    @Override
    public CampoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.campo_list, null,false);
        view.setOnClickListener(this);
        return new CampoViewHolder(view);
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;

    }

    //Clase para llenar los TextView
    @Override
    public void onBindViewHolder(CampoViewHolder holder, int position) {
//        Campo campo = campoList.get(position);

        holder.itemView.setOnClickListener(this);
        holder.tvnombre.setText(campoList.get(position).getNombre());
        holder.tvlon.setText("" +campoList.get(position).getLon());
        holder.tvlat.setText(""+campoList.get(position).getLat());
//
//        holder.tvnombre.setText("Nombre: " + campo.getNombre());
//        holder.tvlon.setText( "Longitud: " + (campo.getLon()));
//        holder.tvlat.setText("Latitud: " + (campo.getLat()));
    }

    @Override
    public int getItemCount() {
        return campoList.size();
    }

    @Override
    public void onClick(View v) {
        if(listener != null){
            listener.onClick(v);
        }
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
}
