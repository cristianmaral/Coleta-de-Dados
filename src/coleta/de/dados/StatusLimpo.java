package coleta.de.dados;

import java.util.Date;

public class StatusLimpo {

    private long id;
    private int qtdRetweet;
    private Date data;
    private String texto, usuario, linguagem;

    public StatusLimpo(long id, int qtdRetweet, Date data, String texto, String usuario, String linguagem) {
        this.id = id;
        this.qtdRetweet = qtdRetweet;
        this.data = data;
        this.texto = texto;
        this.usuario = usuario;
        this.linguagem = linguagem;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getQtdRetweet() {
        return qtdRetweet;
    }

    public void setQtdRetweet(int qtdRetweet) {
        this.qtdRetweet = qtdRetweet;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getLinguagem() {
        return linguagem;
    }

    public void setLinguagem(String linguagem) {
        this.linguagem = linguagem;
    }

    /* Método para retornar uma String formatada contendo os atributos da classe */
    @Override
    public String toString() {
        return "id: " + this.id + "\tqtdRetweet: " + this.qtdRetweet + "\tdata: " + this.data + "\ttexto: "
                + this.texto + "\tusuario: " + this.usuario + "\tlinguagem: " + this.linguagem;
    }
}
