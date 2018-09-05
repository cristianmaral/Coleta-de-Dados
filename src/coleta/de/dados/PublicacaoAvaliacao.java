package coleta.de.dados;

import java.util.Date;

public class PublicacaoAvaliacao extends PublicacaoLimpa {

    private int avaliacaoPos, avaliacaoNeg;
    private short sentimento;

    public PublicacaoAvaliacao(String id, String mensagem, Date data, int avaliacaoPos, int avaliacaoNeg) {
        super(id, mensagem, data);
        this.avaliacaoPos = avaliacaoPos;
        this.avaliacaoNeg = avaliacaoNeg;
        this.calcularSentimento();
    }

    public int getAvaliacaoPos() {
        return avaliacaoPos;
    }

    public void setAvaliacaoPos(int avaliacaoPos) {
        this.avaliacaoPos = avaliacaoPos;
    }

    public int getAvaliacaoNeg() {
        return avaliacaoNeg;
    }

    public void setAvaliacaoNeg(int avaliacaoNeg) {
        this.avaliacaoNeg = avaliacaoNeg;
    }

    public short getSentimento() {
        return sentimento;
    }

    /* Método para calcular o sentimento do status de acordo com a avaliação positiva e negativa */
    private void calcularSentimento() {
        if (this.avaliacaoPos == Math.abs(this.avaliacaoNeg)) {
            this.sentimento = 0; //Neutro
        } else if (this.avaliacaoPos > Math.abs(this.avaliacaoNeg)) {
            this.sentimento = 1; //Positivo
        } else {
            this.sentimento = -1; //Negativo
        }
    }

    /* Método para retornar uma String formatada contendo os atributos da classe */
    @Override
    public String toString() {
        return super.toString() + "\tavaliacaoPos: " + this.avaliacaoPos + "\tavaliacaoNeg: " + this.avaliacaoNeg
                + "\tsentimento: " + this.sentimento;
    }

}
