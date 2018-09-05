package coleta.de.dados;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class GeradorArquivos {

    private BufferedReader resultadoColetaJson, sentiStrengthTxt;
    private BufferedWriter coletaJson, coletaTxt, sentimentoJson;

    /* Método para definir quais serão os arquivos referentes à coleta de dados */
    public void iniciarArquivosColeta(String idPagina) throws IOException {
        this.coletaJson = new BufferedWriter(new FileWriter(idPagina + ".json"));
        this.coletaTxt = new BufferedWriter(new FileWriter(idPagina + ".txt"));
        this.coletaTxt.write("\n"); //A primeira linha deve ser vazia pois o SentiStrength a ignora
    }

    /* Método para adicionar uma publicação aos arquivos json e txt */
    public void adicionarPublicacao(PublicacaoLimpa publicacao) throws IOException {
        Json json = new Json();
        String publicacaoJson = json.publicacaoParaJson(publicacao);
        String mensagemPublicacao = json.stringParaJson(publicacao.getMensagem());

        this.coletaJson.write(publicacaoJson + "\n");
        this.coletaTxt.write(mensagemPublicacao + "\n");
    }

    /* Método para adicionar um status aos arquivos json e txt */
    public void adicionarStatus(StatusLimpo status) throws IOException {
        Json json = new Json();
        String statusJson = json.statusParaJson(status);
        String textoStatus = json.stringParaJson(status.getTexto());

        this.coletaJson.write(statusJson + "\n");
        this.coletaTxt.write(textoStatus + "\n");
    }

    /* Método para fechar todos os arquivos referentes à coleta dos dados */
    public void fecharArquivosColeta() throws IOException {
        this.coletaJson.close();
        this.coletaTxt.close();
    }

    /* Método para definir quais serão os arquivos de leitura e escrita relacionados ao sentimento */
    private void iniciarArquivosSentimento(String idPagina) throws FileNotFoundException, IOException {
        this.resultadoColetaJson = new BufferedReader(new FileReader(idPagina + ".json"));
        this.sentiStrengthTxt = new BufferedReader(new FileReader(idPagina + "+results.txt"));
        this.sentimentoJson = new BufferedWriter(new FileWriter(idPagina + "+sentimento.json"));
    }

    /* Método para gerar um arquivo json que contenha, adicionalmente, o sentimento de cada publicação */
    public void gerarSentimentoPublicacao(String idPagina) throws IOException {
        Json json = new Json();

        this.iniciarArquivosSentimento(idPagina);
        while (this.resultadoColetaJson.ready() && this.sentiStrengthTxt.ready()) {
            String linhaJson = this.resultadoColetaJson.readLine(); //Lendo uma linha do arquivo .json
            String linhaTxt = this.sentiStrengthTxt.readLine(); //Lendo uma linha do arquivo .txt
            String linhaTxtSeparada[] = linhaTxt.split("\t"); //Separando as informações do .txt
            short positivo = Short.parseShort(linhaTxtSeparada[1]);
            short negativo = Short.parseShort(linhaTxtSeparada[2]);
            PublicacaoLimpa p = json.jsonParaPublicacao(linhaJson);
            PublicacaoAvaliacao publicacao = new PublicacaoAvaliacao(p.getId(), p.getMensagem(), p.getData(),
                    positivo, negativo);
            this.sentimentoJson.write(json.publicacaoParaJson(publicacao) + "\n");
        }
        this.fecharArquivosSentimento();
    }

    /* Método para gerar um arquivo json que contenha, adicionalmente, o sentimento de cada status */
    public void gerarSentimentoStatus(String idPagina) throws IOException {
        Json json = new Json();

        this.iniciarArquivosSentimento(idPagina);
        while (this.resultadoColetaJson.ready() && this.sentiStrengthTxt.ready()) {
            String linhaJson = this.resultadoColetaJson.readLine(); //Lendo uma linha do arquivo .json
            String linhaTxt = this.sentiStrengthTxt.readLine(); //Lendo uma linha do arquivo .txt
            String linhaTxtSeparada[] = linhaTxt.split("\t"); //Separando as informações do .txt
            short positivo = Short.parseShort(linhaTxtSeparada[1]);
            short negativo = Short.parseShort(linhaTxtSeparada[2]);
            StatusLimpo s = json.jsonParaStatus(linhaJson);
            StatusAvaliacao status = new StatusAvaliacao(s.getId(), s.getQtdRetweet(), s.getData(), s.getTexto(),
                    s.getUsuario(), s.getLinguagem(), positivo, negativo);
            this.sentimentoJson.write(json.statusParaJson(status) + "\n");
        }
        this.fecharArquivosSentimento();
    }

    /* Método para fechar todos os arquivos utilizados na geração do arquivo que contém o sentimento */
    private void fecharArquivosSentimento() throws IOException {
        this.resultadoColetaJson.close();
        this.sentiStrengthTxt.close();
        this.sentimentoJson.close();
    }
}
