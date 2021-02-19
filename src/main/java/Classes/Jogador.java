package Classes;

    /**
     * Classe Jogador
     *
     * @author Rafael Costa
     */

public class Jogador implements Comparable{

    private String nome;

    private double pontos;

    /**
     * Método Construtor da Classe Jogador
     *
     * @param p - pontos de um determinado jogador
     */

    public Jogador(String nome, double p) {
        this.pontos = p;
        this.nome = nome;
    }

    /**
     * Método Responsável por retornar o nome do jogador
     *
     * @return nome
     */

    public String getNome() {
        return nome;
    }

    /**
     * Método Responsável por alterar o nome do jogador
     *
     * @param nome
     */

    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Método Responsável por retornar os pontos do jogador
     *
     * @return pontos
     */

    public double getPontos() {
        return pontos;
    }

    /**
     * Método Responsável por alterar os pontos do jogador
     *
     * @param pontos
     */

    public void setPontos(double pontos) {
        this.pontos = pontos;
    }

    @Override
    public String toString() {

        return "Jogador{" +
                "nome='" + nome + '\'' +
                ", pontos=" + pontos +
                '}';

    }

    /**
     * Método Responsável por Comparar os Jogadores
     * @param o - jogador que se quer comparar
     * @return 0 - ou se os pontos do jogador forem iguais aos pontos do outro jogador
     */

    @Override
    public int compareTo(Object o) {

        Jogador tmp = (Jogador) o;
            if (this.pontos > tmp.pontos) {
                return -1;
            } else if (this.pontos < tmp.pontos) {
                return 1;
            }
            return 0;

    }

}