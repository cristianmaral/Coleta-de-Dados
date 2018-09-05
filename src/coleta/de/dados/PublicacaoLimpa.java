package coleta.de.dados;

import java.util.Date;

public class PublicacaoLimpa {

    private String id, mensagem;
    private Date data;

    public PublicacaoLimpa(String id, String mensagem, Date data) {
        this.id = id;
        this.mensagem = mensagem;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    /* Método para retornar uma String formatada contendo os atributos da classe */
    @Override
    public String toString() {
        return "id: " + this.id + "\tmensagem: " + this.mensagem + "\tdata: " + this.data;
    }
}
