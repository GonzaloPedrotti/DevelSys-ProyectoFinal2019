package pedrotti.gonzalo.proyecto.ProyectoCultivo;

import android.os.Parcel;
import android.os.Parcelable;

public class DetalleActividad implements Parcelable {

    private String actividad;
    private int actividad_id;
    private String inicio;
    private String fin;
    private String estado;
    private int detalle_actividad_id;



    protected DetalleActividad(Parcel in) {
        actividad = in.readString();
        actividad_id = in.readInt();
        inicio = in.readString();
        fin = in.readString();
        estado = in.readString();
        detalle_actividad_id = in.readInt();
    }

    public int getDetalle_actividad_id() {
        return detalle_actividad_id;
    }

    public void setDetalle_actividad_id(int detalle_actividad_id) {
        this.detalle_actividad_id = detalle_actividad_id;
    }

    public static final Creator<DetalleActividad> CREATOR = new Creator<DetalleActividad>() {
        @Override
        public DetalleActividad createFromParcel(Parcel in) {
            return new DetalleActividad(in);
        }

        @Override
        public DetalleActividad[] newArray(int size) {
            return new DetalleActividad[size];
        }
    };

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getActividad_id() {
        return actividad_id;
    }

    public void setActividad_id(int actividad_id) {
        this.actividad_id = actividad_id;
    }

    public DetalleActividad(String actividad, int detalle_actividad_id,int actividad_id, String inicio, String fin, String estado) {
        this.actividad = actividad;
        this.detalle_actividad_id = detalle_actividad_id;
        this.actividad_id = actividad_id;
        this.inicio = inicio;
        this.fin = fin;
        this.estado = estado;
    }

    public DetalleActividad() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(actividad);
        dest.writeInt(actividad_id);
        dest.writeString(inicio);
        dest.writeString(fin);
        dest.writeString(estado);
    }
}
