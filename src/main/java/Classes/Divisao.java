package Classes;

import Estruturas.Lists.ArrayUnorderedList.ArrayUnorderedList;

import javax.activation.UnsupportedDataTypeException;
import java.util.Iterator;

    /**
     * Classe Divisão
     */

public class Divisao{

    private String nome;

    private ArrayUnorderedList<Inimigo> inimigos;

    private ArrayUnorderedList<Divisao> ligacoes;

        /**
         * Método Construtor associado à classe
         * @param nome - nome da divisão
         */

    public Divisao(String nome){
        this.nome = nome;
        this.inimigos = new ArrayUnorderedList<>();
        this.ligacoes = new ArrayUnorderedList<>();
    }


        /**
         * Método Responsável por retornar o nome da divisão.
         * @return nome
         */

    public String getNome() {
        return nome;
    }

        /**
         * Método Responsável por definir o nome da divisão.
         * @param nome
         */

    public void setNome(String nome) {
        this.nome = nome;
    }

        /**
         * Método Responsável adicionar as ligações de cada divisão
         * @param ligacao - ligação associada à divisão
         * @throws UnsupportedDataTypeException - Excepção para o caso de o tipo de dados não ser suportado.
         */

    public void addLigacao(Divisao ligacao) throws UnsupportedDataTypeException {
        ligacoes.addToFront(ligacao);
    }

        /**
         * Método Responsável por adicionar os inimigos.
         * @param inimigo
         * @throws UnsupportedDataTypeException - Excepção para o caso de o tipo de dados não ser suportado.
         */

    public void addInimigo(Inimigo inimigo) throws UnsupportedDataTypeException {
        inimigos.addToFront(inimigo);
    }

        /**
         * Método Responsável por retornar as ligações existentes.
         * @return
         */

    public Iterator<Divisao> getLigacoes() {
        return ligacoes.iterator();
    }

        /**
         * Método Responsável por retornar os inimigos existentes.
         * @return
         */

    public ArrayUnorderedList<Inimigo> getInimigos() {
        return inimigos;
    }

        /**
         * Método Responsável por definir os inimigos.
         * @param inimigos
         */

    public void setInimigos(ArrayUnorderedList<Inimigo> inimigos) {
        this.inimigos = inimigos;
    }


        /**
         * Método Responsável por retornar o poder total dos inimigos.
         * @return poder - dano de cada inimigo
         */

    public double getPoderTotal(){
        double poder = 0;
        Iterator<Inimigo> inimigoIT = inimigos.iterator();

        while (inimigoIT.hasNext()) {
            Inimigo tmp = inimigoIT.next();
            poder += tmp.getPoder();
        }
        return poder;

    }

        /**
         * Método Responsável por retornar o nome das ligações de cada divisão.
         * @return s - string que vai ser impressa com o nome das ligações de cada divisão.
         */

    public String stringNometLigacoes(){

        String s = "| ";
        Iterator itj = ligacoes.iterator();
        while (itj.hasNext()) {
            Divisao obj = (Divisao) itj.next();
            s = s + obj.getNome() + " | ";
        }
        return s;

    }

        /**
         * Método Responsável por retornar o dano de cada inimigo bem como o seu nome.
         * @return
         */

    public String inimigosDivisao(){

        String s = "";
        Iterator itj = inimigos.iterator();
        while (itj.hasNext()) {
            Inimigo obj = (Inimigo) itj.next();
            s = s + "|" + obj.getNome() + " > " + " poder " + obj.getPoder()+ " | "  ;
        }
        return s;

    }
}