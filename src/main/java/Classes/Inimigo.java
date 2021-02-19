package Classes;

    /**
     * Classe Inimigo
     */

public class Inimigo{

    private String nome;

    private double poder;

    private String divisao;

        /**
         * Método Responsável por retornar o nome do inimigo.
         * @return nome
         */

    public String getNome() {
        return nome;
    }

        /**
         * Método Responsável por definir o nome do inimigo.
         * @param nome - nome do inimigo
         */

    public void setNome(String nome) {
        this.nome = nome;
    }

        /**
         * Método Responsável por retornar o dano do inimigo.
         * @return poder - dano do inimigo
         */

    public double getPoder() {
        return poder;
    }

        /**
         * Método Responsável por definir o dano do inimigo.
         * @param poder - dano do inimigo.
         */

    public void setPoder(int poder) {
        this.poder = poder;
    }

        /**
         * Método Responsável por retornar a divisão em que se encontra o inimigo.
         * @return divisao
         */

    public String getDivisão() {
        return divisao;
    }

        /**
         * Método Responsável por definir a divisão em que se encontra o inimigo.
         * @param divisão
         */

    public void setDivisão(String divisão) {
        this.divisao = divisão;
    }

        /**
         * Método Construtor associado à classe Inimigo.
         * @param nome - nome do inimigo
         * @param poder - dano do inimigo
         * @param divisao - divisão em que o inimigo se encontra.
         */

    public Inimigo(String nome, int poder, String divisao) {
        this.nome = nome;
        this.poder = poder;
        this.divisao = divisao;
    }

        /**
         * Método Responsável por retornar a informação do inimigo sob o formato de String na consola.
         * @return
         */

    @Override
    public String toString() {

        return "Inimigo{" +
                "nome='" + nome + '\'' +
                ", poder=" + poder +
                ", divisao='" + divisao + '\'' +
                '}';

    }
}