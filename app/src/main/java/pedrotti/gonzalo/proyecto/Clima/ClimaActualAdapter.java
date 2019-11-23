package pedrotti.gonzalo.proyecto.Clima;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import pedrotti.gonzalo.proyecto.Actividad.Actividad;
import pedrotti.gonzalo.proyecto.Campo.Campo;
import pedrotti.gonzalo.proyecto.R;

public class ClimaActualAdapter extends RecyclerView.Adapter<ClimaActualAdapter.InformacionClimaticaViewHolder> {

    private Context mCtx;
    private List<ClimaActual> climaActualList;
    private View.OnClickListener listener;
    private ClimaActualAdapter.OnItemClickListener mlistener;


    public interface OnItemClickListener{
        void OnItemClick(int position);
    }

    public ClimaActualAdapter(Context mCtx, List<ClimaActual> climaActualList) {
        this.mCtx = mCtx;
        this.climaActualList =climaActualList;

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return climaActualList.size();
    }


    @Override
    public ClimaActualAdapter.InformacionClimaticaViewHolder onCreateViewHolder(ViewGroup parent,
                                                                                int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.clima_actual_list,null);
        return new ClimaActualAdapter.InformacionClimaticaViewHolder(view);
    }

    //Clase para llenar los TextView
    @Override
    public void onBindViewHolder(InformacionClimaticaViewHolder holder, int position) {

        ClimaActual climaActual = climaActualList.get(position);
        int recomendacion = climaActual.getRecomendacion();

        holder.tvTemperatura.setText(""+climaActual.getTemperatura() + " C°");

        Date dateFecha = parseFecha(climaActual.getFecha());

        String formato1="yyyy";
        SimpleDateFormat dateFormat1 = new SimpleDateFormat(formato1);
        int anio = Integer.parseInt(dateFormat1.format(dateFecha));

        String formato2="MM";
        SimpleDateFormat dateFormat2 = new SimpleDateFormat(formato2);
        int mes = Integer.parseInt(dateFormat2.format(dateFecha));

        String formato3="dd";
        SimpleDateFormat dateFormat3 = new SimpleDateFormat(formato3);
        int dia = Integer.parseInt(dateFormat3.format(dateFecha));

        String formato4="HH";
        SimpleDateFormat dateFormat4 = new SimpleDateFormat(formato4);
        String hora = String.valueOf(dateFormat4.format(dateFecha));

        String formato5="mm";
        SimpleDateFormat dateFormat5 = new SimpleDateFormat(formato5);
        String min = String.valueOf(dateFormat5.format(dateFecha));

        String formato6="ss";
        SimpleDateFormat dateFormat6 = new SimpleDateFormat(formato6);
        String seg = String.valueOf(dateFormat6.format(dateFecha));

        holder.tvFecha.setText(""+dia+"-"+mes+"-"+anio + " "+hora+":"+seg+":"+min);
        holder.tvDescripcion.setText(""+climaActual.getDescripcion());
        holder.tvHumedad.setText(""+ climaActual.getHumedad() + " %");
        holder.tvVelocidadViento.setText(""+climaActual.getViento());
        holder.tvLluvia.setText(""+climaActual.getLluvia());

        switch (climaActual.getRecomendacion()){
            case 1:
                holder.tvRecomendacion.setBackgroundColor(Color.parseColor("#09EB15"));//verde
                break;

            case 2:
                holder.tvRecomendacion.setBackgroundColor(Color.parseColor("#E6F00A"));//amarillo
                break;

            case 3:
                holder.tvRecomendacion.setBackgroundColor(Color.parseColor("#E71313"));//rojo
                 break;

        }

//        switch (actividad_id){
//            case 1:
//                holder.tvRecomendacion.setBackgroundColor(Color.parseColor("#AAAAAA"));//pintado gris
//
//                break;
//            case 2 :
//                if(climaActual.estaPermitidaFumigacion(climaActual.getTemperatura(),climaActual.getViento())==0){
//                    holder.tvRecomendacion.setBackgroundColor(Color.parseColor("#09EB15"));//verde
//                }
//
//                if(climaActual.estaPermitidaFumigacion(climaActual.getTemperatura(),climaActual.getViento())==1){
//                    holder.tvRecomendacion.setBackgroundColor(Color.parseColor("#E6F00A"));//amarillo
//                }
//
//                if(climaActual.estaPermitidaFumigacion(climaActual.getTemperatura(),climaActual.getViento())==2){
//                    holder.tvRecomendacion.setBackgroundColor(Color.parseColor("#E71313"));//rojo
//                }
//            break;
//
//            case 3:
//                holder.tvRecomendacion.setBackgroundColor(Color.parseColor("#AAAAAA"));//pintado gris
//                 break;
//
//            case 4:
//                holder.tvRecomendacion.setBackgroundColor(Color.parseColor("#AAAAAA"));//pintado gris
//                break;
//        }

        Picasso.with(mCtx).load( "https://openweathermap.org/img/wn/"+climaActual.getImagen()+".png").into(holder.ivIcono);

    }

    class InformacionClimaticaViewHolder extends RecyclerView.ViewHolder{

        TextView tvTemperatura, tvFecha, tvDescripcion, tvHumedad, tvVelocidadViento, tvLluvia, tvRecomendacion;
        ImageView ivIcono;

        public InformacionClimaticaViewHolder(View itemView){
            super(itemView);

            tvTemperatura = itemView.findViewById(R.id.tvTemperatura);
            ivIcono = itemView.findViewById(R.id.ivIcono);
            tvFecha = itemView.findViewById(R.id.tvFecha);
            tvDescripcion=itemView.findViewById(R.id.tvDescripcionClima);
            tvHumedad=itemView.findViewById(R.id.tvHumedad);
            tvVelocidadViento=itemView.findViewById(R.id.tvVelocidadViento);
            tvLluvia = itemView.findViewById(R.id.tvLluvia);
            tvRecomendacion = itemView.findViewById(R.id.tvRecomendacion);


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

    public void setOnItemClickListener (OnItemClickListener listener){
        mlistener  = listener;
    }

    public static Date parseFecha(String fecha)
    {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date fechaDate = null;

        try {
            fechaDate = formato.parse(fecha);

        }
        catch (ParseException ex)
        {
            System.out.println(ex);
        }
        return fechaDate;
    }

public static Calendar calendar(String fecha){
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    Calendar cal = null;
    try {
        Date date = sdf.parse(fecha);
        cal = Calendar.getInstance();
        cal.setTime(date);

    }catch (Exception e){

    }
    return cal;
}




}
