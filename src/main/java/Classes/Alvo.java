package Classes;

    /**
     *  Classe Alvo
     */

    public class Alvo {

    private String divisao;

    private String tipo;

    /**
     * Método que retorna a divisão do edifício
     * @return divisao
     */
    public String getDivisão() {
        return divisao;
    }

    /**
     * Método que define a divisão do edifício
     * @param divisão
     */
    public void setDivisão(String divisão) {
        this.divisao = divisão;
    }

    /**
     * Método que retorna o tipo do alvo que se pretende capturar
     * @return tipo
     */

    public String getTipo() {
        return tipo;
    }

    /**
     * Método que define o tipo de alvo a capturar
     * @param tipo
     */

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    }