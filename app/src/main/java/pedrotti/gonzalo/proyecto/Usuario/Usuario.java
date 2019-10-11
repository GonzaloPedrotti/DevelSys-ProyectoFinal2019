package pedrotti.gonzalo.proyecto.Usuario;

import android.os.Parcel;
import android.os.Parcelable;

public class Usuario implements Parcelable {

    public static  final String TAG = Usuario.class.getSimpleName();

    private int usuario_id;
    private String nombre;
    private String apellido;
    private String correo;
    private int telefono;
    private String contrasena;


    protected Usuario(Parcel in) {
        usuario_id = in.readInt();
        nombre = in.readString();
        apellido = in.readString();
        correo = in.readString();
        telefono = in.readInt();
        contrasena = in.readString();
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

    public Usuario(int usuario_id, String nombre, String apellido, String correo, int telefono, String contrasena) {
        this.usuario_id = usuario_id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.telefono = telefono;
        this.contrasena = contrasena;
    }

    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Usuario(){

    }
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(usuario_id);
        parcel.writeString(nombre);
        parcel.writeString(apellido);
        parcel.writeString(correo);
        parcel.writeInt(telefono);
        parcel.writeString(contrasena);
    }

}
