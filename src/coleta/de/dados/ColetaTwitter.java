package coleta.de.dados;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

public class ColetaTwitter {

    private final Twitter twitter;
    private final TwitterStream twitterStream;

    public ColetaTwitter() {
        String consumerKey = "Consumer Key";
        String consumerSecret = "Consumer Secret Key";
        String accessToken = "Access Token";
        String accessTokenSecret = "Acess Token Secret";

        this.twitter = conectarTwitter(consumerKey, consumerSecret, accessToken, accessTokenSecret);
        this.twitterStream = conectarTwitterStream(consumerKey, consumerSecret, accessToken, accessTokenSecret);
    }

    /* Método para inicializar a variável do tipo Twitter */
    private Twitter conectarTwitter(String consumerKey, String consumerSecret, String accessToken,
            String accessTokenSecret) 
    {
        ConfigurationBuilder config = new ConfigurationBuilder();
        config.setDebugEnabled(true).setOAuthConsumerKey(consumerKey)
                .setOAuthConsumerSecret(consumerSecret)
                .setOAuthAccessToken(accessToken)
                .setOAuthAccessTokenSecret(accessTokenSecret);

        return new TwitterFactory(config.build()).getInstance();
    }

    /* Método para inicializar a variável do tipo TwitterStream */
    private TwitterStream conectarTwitterStream(String consumerKey, String consumerSecret, String accessToken,
            String accessTokenSecret) 
    {
        ConfigurationBuilder config = new ConfigurationBuilder();
        config.setDebugEnabled(true).setOAuthConsumerKey(consumerKey)
                .setOAuthConsumerSecret(consumerSecret)
                .setOAuthAccessToken(accessToken)
                .setOAuthAccessTokenSecret(accessTokenSecret);

        return new TwitterStreamFactory(config.build()).getInstance();
    }

    /* Método para coletar tweets de um determinado usuário */
    public void coletarTweetsUsuario(String idUsuario) throws IOException {
        GeradorArquivos arquivos = new GeradorArquivos();
        List<Status> status = new ArrayList();
        int pageno = 1;

        arquivos.iniciarArquivosColeta(idUsuario);
        while (true) {
            try {
                int size = status.size();
                Paging page = new Paging(pageno++, 100);
                status.addAll(this.twitter.getUserTimeline(idUsuario, page));
                if (status.size() == size) {
                    break;
                }
            } catch (TwitterException ex) {
                System.out.println("Failed to get timeline: " + ex.getMessage());
                System.exit(-1);
            }
        }

        for (Status tweet : status) {
            StatusLimpo statusLimpo = new StatusLimpo(tweet.getId(), tweet.getRetweetCount(), tweet.getCreatedAt(),
                    tweet.getText(), tweet.getUser().getName(), tweet.getLang());
            arquivos.adicionarStatus(statusLimpo);
        }
        arquivos.fecharArquivosColeta();
    }

    /* Método genérico para coletar tweets de acordo com um determinado parâmetro - filterQuery */
    private void coletarTweets(FilterQuery filterQuery, String nomeArquivo, int limitador) throws IOException {
        GeradorArquivos arquivos = new GeradorArquivos();

        arquivos.iniciarArquivosColeta(nomeArquivo);
        StatusListener listener = new StatusListener() {
            int contador = 0; //Limitador da busca

            @Override
            public void onStatus(Status status) {
                System.out.println(contador);
                if (contador >= limitador) {
                    try {
                        arquivos.fecharArquivosColeta();
                    } catch (IOException ex) {
                        Logger.getLogger(ColetaTwitter.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.exit(0);
                    return;
                }
                StatusLimpo statusLimpo = new StatusLimpo(status.getId(), status.getRetweetCount(),
                        status.getCreatedAt(), status.getText(), status.getUser().getName(), status.getLang());

                try {
                    arquivos.adicionarStatus(statusLimpo);
                } catch (IOException ex) {
                    Logger.getLogger(ColetaTwitter.class.getName()).log(Level.SEVERE, null, ex);
                }
                contador++;
            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice sdn) {
            }

            @Override
            public void onTrackLimitationNotice(int i) {
            }

            @Override
            public void onScrubGeo(long l, long l1) {
            }

            @Override
            public void onStallWarning(StallWarning sw) {
            }

            @Override
            public void onException(Exception excptn) {
            }
        };
        this.twitterStream.addListener(listener);
        this.twitterStream.filter(filterQuery);
    }

    /* Método para coletar tweets relacionados à uma determinada chave */
    public void coletarTweetsChave(String nomeArquivo, String chave, int limitador) throws IOException {
        FilterQuery filterQuery = new FilterQuery();

        filterQuery.track(chave);
        this.coletarTweets(filterQuery, nomeArquivo, limitador);
    }

    /* Método para coletar tweets relacionados à uma determinada localização geográfica */
    public void coletarTweetsLocalizacao(String nomeArquivo, double[][] locations, int limitador) throws IOException {
        FilterQuery filterQuery = new FilterQuery();
        //double[][] locations = {{-74, 40}, {-73, 41}}; //Coordenadas são de NY

        filterQuery.locations(locations);
        this.coletarTweets(filterQuery, nomeArquivo, limitador);
    }
}
