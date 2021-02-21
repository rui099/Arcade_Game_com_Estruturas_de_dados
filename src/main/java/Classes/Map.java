package Classes;

import Estruturas.Lists.ArrayUnorderedList.ArrayUnorderedList;
import Estruturas.Network.GraphDoJogo;
import Exceptions.EmptyCollectionException;

import javax.activation.UnsupportedDataTypeException;
import java.util.Iterator;

public class Map implements Comparable<Map> {
    private String cod_missao;
    private String versao;
    private Alvo alvo;
    private ArrayUnorderedList<Divisao> entradas;
    private ArrayUnorderedList<Divisao> saidas;
    private ArrayUnorderedList<Divisao> divisoes;
    private GraphDoJogo<Divisao> graphDoJogo;
    private Inimigo[] inimigos;
    private String[] ligacoes[];

    public Map(String cod_missao, String versao, Alvo alvo, ArrayUnorderedList<Divisao> entradas,
               ArrayUnorderedList<Divisao> saidas, ArrayUnorderedList<Divisao> divisoes, GraphDoJogo<Divisao> graphDoJogo,
               String[][] ligacoes, Inimigo[] inimigos) {
        this.cod_missao = cod_missao;
        this.versao = versao;
        this.alvo = alvo;
        this.entradas = entradas;
        this.saidas = saidas;
        this.divisoes = divisoes;
        this.graphDoJogo = graphDoJogo;
        this.ligacoes = ligacoes;
        this.inimigos = inimigos;
    }

    public Inimigo[] getInimigos() {
        return inimigos;
    }

    public void setInimigos(Inimigo[] inimigos) {
        this.inimigos = inimigos;
    }

    public String getCod_missao() {
        return cod_missao;
    }

    public void setCod_missao(String cod_missao) {
        this.cod_missao = cod_missao;
    }

    public String getVersao() {
        return versao;
    }

    public void setVersao(String versao) {
        this.versao = versao;
    }

    public Alvo getAlvo() {
        return alvo;
    }

    public void setAlvo(Alvo alvo) {
        this.alvo = alvo;
    }

    public ArrayUnorderedList<Divisao> getEntradas() {
        return entradas;
    }

    public void setEntradas(ArrayUnorderedList<Divisao> entradas) {
        this.entradas = entradas;
    }

    public ArrayUnorderedList<Divisao> getSaidas() {
        return saidas;
    }

    public void setSaidas(ArrayUnorderedList<Divisao> saidas) {
        this.saidas = saidas;
    }

    public ArrayUnorderedList<Divisao> getDivisoes() {
        return divisoes;
    }

    public void setDivisoes(ArrayUnorderedList<Divisao> divisoes) {
        this.divisoes = divisoes;
    }

    public GraphDoJogo<Divisao> getGraphDoJogo() {
        return graphDoJogo;
    }

    public void setGraphDoJogo(GraphDoJogo<Divisao> graphDoJogo) {
        this.graphDoJogo = graphDoJogo;
    }

    /**
     * Método Responsável por retornar a divisao pelo nome
     * @param name - nome da divisão
     * @return room - divisão
     */

    public Divisao getByDivisaoName(String name) {

        boolean found = false;
        Divisao room = null;
        Iterator<Divisao> itr = divisoes.iterator();

        while (!found && itr.hasNext()) {
            Divisao tmp = itr.next();
            if (tmp.getNome().equalsIgnoreCase(name)) {
                room = tmp;
                found = true;
            }
        }

        if (found == false) {
            System.out.println("ERRO");
        }

        return room;

    }

    public Iterator<Divisao> getShortesPathMapaEntrada(Divisao inicio) throws UnsupportedDataTypeException, EmptyCollectionException {

        return graphDoJogo.dijkstraShortestPathTotalEntrada(inicio,getEntradas(),divisoes);
    }

    public Iterator<Divisao> getShortesPathMapaSaida(Divisao inicio) throws UnsupportedDataTypeException, EmptyCollectionException {

        return graphDoJogo.dijkstraShortestPathTotalSaida(inicio,getSaidas(),divisoes);

    }


    /**
     * Método Responsável por retornar o caminho de menor vida tirada no mapa.
     * @param de - inicio
     * @param para - destino
     * @return
     * @throws UnsupportedDataTypeException - Excepção para o caso de o tipo de dados não ser suportado.
     * @throws EmptyCollectionException - Excepção para o caso de a coleção estar vazia.
     */

        /*public double getShortestCostMapa(String de, String para) throws UnsupportedDataTypeException, EmptyCollectionException {

            Divisao inicio = getByDivisaoName(de);
            Divisao fim = getByDivisaoName(para);

            return graphDoJogo.minimalPath(inicio, fim);

        }*/

    /**
     * Método Responsável por verificar a existência da entrada/saida no array respetivo.
     * @param entrada
     * @return true caso seja bem sucedido, false caso contrário
     */

    public boolean verificarEntrada(String entrada) {
        boolean found = false;
        Iterator it0 = getEntradas().iterator();
        while (it0.hasNext()) {
            Divisao entradaD = (Divisao) it0.next();
            if (entrada.equalsIgnoreCase(entradaD.getNome())) {
                found = true;
            }
        }
        return found;
    }

    public boolean verificarSaida(String entrada) {
        boolean found = false;
        Iterator it0 = getSaidas().iterator();
        while (it0.hasNext()) {
            Divisao saidaD = (Divisao) it0.next();
            if (entrada.equalsIgnoreCase(saidaD.getNome())) {
                found = true;
                break;
            }
        }
        return found;
    }

    /**
     * Representação do mapa na consola
     */
    public void visualizarMapa() {
        String vertexs ="";
        Iterator it0 = getSaidas().iterator();
        while (it0.hasNext()) {
            Divisao divisao = (Divisao) it0.next();
            vertexs += divisao.getNome() + " | ";
        }

        String edges = "";
        for (int i = 0; i < this.ligacoes.length; i++) {
            edges += ligacoes[i][0] + " > " + ligacoes[i][1] + " | ";
        }
        String inimigos = "";
        Iterator it1 = getSaidas().iterator();
        while (it1.hasNext()) {
            Divisao divisao = (Divisao) it1.next();
        }
        System.out.println("Vertices");
        System.out.println(vertexs);
        System.out.println("Ligações");
        System.out.println(edges);
        System.out.println("inimigos");
        System.out.println(inimigos);
    }


    @Override
    public int compareTo(Map o) {
        return this.getVersao().compareTo(o.getVersao());
    }
}
