package pedrotti.gonzalo.proyecto.Usuario;

import android.os.Parcel;
import android.os.Parcelable;

import pedrotti.gonzalo.proyecto.UsuarioSesion.UsuarioSesion;

public class Usuario implements Parcelable {

    private int usu_id;
    private String usu_nombre;
    private String usu_apellido;
    private String usu_email;
    private String usu_telefono;
    private String usu_pass;


    public Usuario(int usu_id, String usu_nombre, String usu_apellido, String usu_email, String usu_telefono, String usu_pass) {
        this.usu_id = usu_id;
        this.usu_nombre = usu_nombre;
        this.usu_apellido = usu_apellido;
        this.usu_email = usu_email;
        this.usu_telefono = usu_telefono;
        this.usu_pass = usu_pass;
    }

    public Usuario() {
    }


    protected Usuario(Parcel in) {
        usu_id = in.readInt();
        usu_nombre = in.readString();
        usu_apellido = in.readString();
        usu_email = in.readString();
        usu_telefono = in.readString();
        usu_pass = in.readString();
    }

    public static final Creator<Usuario> CREATOR = new Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };

    public int getUsu_id() {
        return usu_id;
    }

    public void setUsu_id(int usu_id) {
        this.usu_id = usu_id;
    }

    public String getUsu_nombre() {
        return usu_nombre;
    }

    public void setUsu_nombre(String usu_nombre) {
        this.usu_nombre = usu_nombre;
    }

    public String getUsu_apellido() {
        return usu_apellido;
    }

    public void setUsu_apellido(String usu_apellido) {
        this.usu_apellido = usu_apellido;
    }

    public String getUsu_email() {
        return usu_email;
    }

    public void setUsu_email(String usu_email) {
        this.usu_email = usu_email;
    }

    public String getUsu_telefono() {
        return usu_telefono;
    }

    public void setUsu_telefono(String usu_telefono) {
        this.usu_telefono = usu_telefono;
    }

    public String getUsu_pass() {
        return usu_pass;
    }

    public void setUsu_pass(String usu_pass) {
        this.usu_pass = usu_pass;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(usu_id);
        dest.writeString(usu_nombre);
        dest.writeString(usu_apellido);
        dest.writeString(usu_email);
        dest.writeString(usu_telefono);
        dest.writeString(usu_pass);
    }
}
