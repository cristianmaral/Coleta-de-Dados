package coleta.de.dados;

import java.util.List;
import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import com.restfb.types.Page;
import com.restfb.types.Post;
import java.io.IOException;

public class ColetaFacebook {

    private final String idPagina;
    private final Connection<Post> postFeed;

    public ColetaFacebook(String idPagina) {
        this.idPagina = idPagina;
        String accessToken = "Inserir a chave de acesso aqui";
        FacebookClient fbClient = new DefaultFacebookClient(accessToken, Version.VERSION_2_10);
        Page pagina = fbClient.fetchObject(idPagina, Page.class);
        this.postFeed = fbClient.fetchConnection(pagina.getId() + "/posts", Post.class);
    }

    /* Método para coletar publicações públicas de uma determinada página */
    public void coletarPublicacoes() throws IOException {
        PublicacaoLimpa publicacao;
        GeradorArquivos arquivos = new GeradorArquivos();

        arquivos.iniciarArquivosColeta(this.idPagina);

        /* Percorre "postFeed" obtendo uma lista de publicações */
        for (List<Post> postPage : this.postFeed) {
            /* Percorre a lista de publicações pegando post por post */
            for (Post post : postPage) {
                if (post.getMessage() != null) {
                    publicacao = new PublicacaoLimpa(post.getId(), post.getMessage(), post.getCreatedTime());
                    arquivos.adicionarPublicacao(publicacao);
                }
            }
        }
        arquivos.fecharArquivosColeta();
    }
}
