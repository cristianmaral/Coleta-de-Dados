package coleta.de.dados;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Json {

    /* Método para converter uma publicação para o formato json */
    public String publicacaoParaJson(PublicacaoLimpa publicacao) {
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(publicacao);

        return json;
    }

    /* Método para converter o formato json de uma publicação para uma instância da classe */
    public PublicacaoLimpa jsonParaPublicacao(String json) {
        Gson gson = new Gson();
        PublicacaoLimpa publicacao = gson.fromJson(json, PublicacaoLimpa.class);

        return publicacao;
    }

    /* Método para converter um status para o formato json */
    public String statusParaJson(StatusLimpo status) {
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(status);

        return json;
    }

    /* Método para converter o formato json de um status para uma instância da classe */
    public StatusLimpo jsonParaStatus(String json) {
        Gson gson = new Gson();
        StatusLimpo status = gson.fromJson(json, StatusLimpo.class);

        return status;
    }

    /* Método para converter uma String para o formato json */
    public String stringParaJson(String string) {
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(string);

        return json;
    }
}
