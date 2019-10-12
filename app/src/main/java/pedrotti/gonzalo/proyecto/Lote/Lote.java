package pedrotti.gonzalo.proyecto.Lote;

import android.os.Parcel;
import android.os.Parcelable;

public class Lote implements Parcelable {

    private int campo_id ;
    private int lote_id ;
    private String nombre;
    private double tamano;
    private double latitud;
    private double longitud;


    public Lote() {
    }

    public Lote(int campo_id, int lote_id, String nombre, double tamano, double latitud, double longitud) {
        this.campo_id = campo_id;
        this.lote_id = lote_id;
        this.nombre = nombre;
        this.tamano = tamano;
        this.latitud = latitud;
        this.longitud=  longitud;
    }

    protected Lote(Parcel in) {
        campo_id = in.readInt();
        lote_id = in.readInt();
        nombre = in.readString();
        tamano = in.readDouble();
        latitud = in.readDouble();
        longitud = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(campo_id);
        dest.writeInt(lote_id);
        dest.writeString(nombre);
        dest.writeDouble(tamano);
        dest.writeDouble(latitud);
        dest.writeDouble(longitud);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Lote> CREATOR = new Creator<Lote>() {
        @Override
        public Lote createFromParcel(Parcel in) {
            return new Lote(in);
        }

        @Override
        public Lote[] newArray(int size) {
            return new Lote[size];
        }
    };

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public int getCampo_id() {
        return campo_id;
    }

    public void setCampo_id(int campo_id) {
        this.campo_id = campo_id;
    }

    public int getLote_id() {
        return lote_id;
    }

    public void setLote_id(int lote_id) {
        this.lote_id = lote_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getTamano() {
        return tamano;
    }

    public void setTamano(double tamano) {
        this.tamano = tamano;
    }


}
