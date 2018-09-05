package coleta.de.dados;

import java.io.IOException;
import java.util.Scanner;

public class ColetaDeDados {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        short operacao;

        /* Implementar menu de operações de coleta de dados */
        System.out.println("+------------------------------------------+\n");
        System.out.println("|     Coleta de Dados de Redes Sociais     |\n");
        System.out.println("+------------------------------------------+\n");
        System.out.println("|            Menu de Operações             |\n");
        System.out.println("+------------------------------------------+\n");
        System.out.println("| 1 -> Coleta de dados do Facebook         |\n");
        System.out.println("| 2 -> Coleta de dados do Twitter          |\n");
        /* Necessária a prévia análise utilizando SentiStrength como entrada */
        System.out.println("| 3 -> Adicionar sentimento ao Json        |\n");
        System.out.println("+------------------------------------------+\n\n");
        do {
            System.out.println("Entre com a operação desejada: ");
            operacao = scanner.nextShort();
            if (operacao < 1 && operacao > 2) {
                System.out.println("Operação inválida!\n");
            }
        } while (operacao < 1 && operacao > 2);
    }

}
