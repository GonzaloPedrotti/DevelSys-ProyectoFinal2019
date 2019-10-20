package pedrotti.gonzalo.proyecto.Lote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import pedrotti.gonzalo.proyecto.R;

public class LoteAdapter extends RecyclerView.Adapter<LoteAdapter.LoteViewHolder> {


    private Context mCtx;
    private List<Lote> loteList;
    private View.OnClickListener listener;
    private OnItemClickListener mlistener;

    public interface OnItemClickListener{
        void OnItemClick(int position);
    }

    public LoteAdapter(Context mCtx, List<Lote> loteList) {
        this.mCtx = mCtx;
        this.loteList = loteList;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return loteList.size();
    }


    @Override
    public LoteAdapter.LoteViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.lote_list,null);
        return new LoteAdapter.LoteViewHolder(view);
    }


    //Clase para llenar los TextView
    @Override
    public void onBindViewHolder(LoteAdapter.LoteViewHolder holder, int position) {
        Lote lote = loteList.get(position);

//        holder.tvLoteId.setText("Id del Lote: "+ lote.getLote_id());
        holder.tvnombre.setText("Nombre del Lote: "+lote.getNombre());
        holder.tvtamano.setText("Tamaño: " + lote.getTamano() + " hectáreas");
//        holder.tvid.setText("Id del Campo: " + (lote.getCampo_id()));
//        holder.tvLoteId.setText(String.valueOf(lote.getLote_id()));
        holder.tvlatitud.setText("Latitud: " + (lote.getLatitud()));
        holder.tvlongitud.setText("Longitud: " + (lote.getLongitud()));

    }


    class LoteViewHolder extends RecyclerView.ViewHolder{
        TextView tvid, tvLoteId,tvnombre, tvtamano, tvlatitud , tvlongitud;
        public LoteViewHolder(View itemView){
            super(itemView);

//            tvLoteId = itemView.findViewById(R.id.tvIdLote);
            tvnombre = itemView.findViewById(R.id.tvNombreLote);
            tvtamano = itemView.findViewById(R.id.tvTamanoLote);
//            tvid = itemView.findViewById(R.id.tvCampoId);
            tvlatitud = itemView.findViewById(R.id.tvLatitudLote);
            tvlongitud = itemView.findViewById(R.id.tvLongitudLote);


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
        mlistener  =  listener;
    }
}
