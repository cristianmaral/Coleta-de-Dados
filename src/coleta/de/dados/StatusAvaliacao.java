package coleta.de.dados;

import java.util.Date;

public class StatusAvaliacao extends StatusLimpo {

    private short avaliacaoPos, avaliacaoNeg, sentimento;

    public StatusAvaliacao(long id, int qtdRetweet, Date data, String texto, String usuario, String linguagem,
            short avaliacaoPos, short avaliacaoNeg) 
    {
        super(id, qtdRetweet, data, texto, usuario, linguagem);
        this.avaliacaoPos = avaliacaoPos;
        this.avaliacaoNeg = avaliacaoNeg;
        this.calcularSentimento();
    }

    public short getAvaliacaoPos() {
        return avaliacaoPos;
    }

    public void setAvaliacaoPos(short avaliacaoPos) {
        this.avaliacaoPos = avaliacaoPos;
    }

    public short getAvaliacaoNeg() {
        return avaliacaoNeg;
    }

    public void setAvaliacaoNeg(short avaliacaoNeg) {
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
