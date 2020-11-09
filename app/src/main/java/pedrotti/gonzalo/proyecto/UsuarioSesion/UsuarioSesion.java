package pedrotti.gonzalo.proyecto.UsuarioSesion;

import pedrotti.gonzalo.proyecto.Usuario.Usuario;

public class UsuarioSesion {

    private Usuario usuario;
    private String ses_token;
    private String ses_fechaAlta;
    private String ses_expiracion_date;

    public UsuarioSesion(Usuario usuario, String ses_token, String ses_fechaAlta, String ses_expiracion_date) {
        this.usuario = usuario;
        this.ses_token = ses_token;
        this.ses_fechaAlta = ses_fechaAlta;
        this.ses_expiracion_date = ses_expiracion_date;
    }


    public UsuarioSesion() {
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getSes_token() {
        return ses_token;
    }

    public void setSes_token(String ses_token) {
        this.ses_token = ses_token;
    }

    public String getSes_fechaAlta() {
        return ses_fechaAlta;
    }

    public void setSes_fechaAlta(String ses_fechaAlta) {
        this.ses_fechaAlta = ses_fechaAlta;
    }

    public String getSes_expiracion_date() {
        return ses_expiracion_date;
    }

    public void setSes_expiracion_date(String ses_expiracion_date) {
        this.ses_expiracion_date = ses_expiracion_date;
    }
}
