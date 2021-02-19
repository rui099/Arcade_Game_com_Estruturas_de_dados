/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Estruturas.Lists.ArrayUnorderedList.ArrayUnorderedList;
import Estruturas.Network.GraphDoJogo;
import Estruturas.Network.NetworkDoJogo;
import Exceptions.EmptyCollectionException;
import Exceptions.InvalidMapException;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;

import javax.activation.UnsupportedDataTypeException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;

    /**
     * Classe Mapa
     * @author ruidu
     */

    public class Mapa {

        @SerializedName(value = "cod_missao", alternate = {"cod-missao"})
        private String cod_missao;
        private String versao;
        private String[] edificio;
        private String[] ligacoes[];
        private Inimigo[] inimigos;
        @SerializedName(value = "entradas_saidas", alternate = {"entradas-saidas"})
        private String[] entradas_saidas;
        private Alvo alvo;
        private ArrayUnorderedList<Divisao> divisoes;
        private GraphDoJogo<Divisao> graphDoJogo;

        /**
         * Método Construtor associado à classe Mapa
         */

        public Mapa() {
            this.graphDoJogo = new GraphDoJogo<>();
            this.divisoes = new ArrayUnorderedList<>();
        }

        public GraphDoJogo<Divisao> getNetworkDoJogo() {
            return graphDoJogo;
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

        public String[] getEdificio() {
            return edificio;
        }

        public void setEdificio(String[] edificio) {
            this.edificio = edificio;
        }

        public String[][] getLigacoes() {
            return ligacoes;
        }

        public void setLigacoes(String[][] ligacoes) {
            this.ligacoes = ligacoes;
        }

        public Inimigo[] getInimigos() {
            return inimigos;
        }

        public void setInimigos(Inimigo[] inimigos) {
            this.inimigos = inimigos;
        }

        public String[] getEntradas_saidas() {
            return entradas_saidas;
        }

        public void setEntradas_saidas(String[] entradas_saidas) {
            this.entradas_saidas = entradas_saidas;
        }

        public Alvo getAlvo() {
            return alvo;
        }

        public void setAlvo(Alvo alvo) {
            this.alvo = alvo;
        }

        public ArrayUnorderedList<Divisao> getDivisoes() {
            return divisoes;
        }

        public void setDivisoes(ArrayUnorderedList<Divisao> divisoes) {
            this.divisoes = divisoes;
        }

        public void setNetworkDoJogo(GraphDoJogo<Divisao> networkDoJogo) {
            this.graphDoJogo = networkDoJogo;
        }

        /**
         * Método Responsável por importar o mapa do jogo.
         * @param file - ficheiro
         * @return map - mapa
         * @throws FileNotFoundException - Excepção para o caso de o ficheiro não ser encontrado.
         */

        public static Mapa importMap(String file) throws FileNotFoundException {

            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader(file));
            Mapa map = gson.fromJson(reader, Mapa.class);
            return map;
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

        /**
         * Método Responsável por adicionar os vértices
         * @throws UnsupportedDataTypeException - Excepção para o caso de o tipo de dados não ser suportado.
         */

        private void addVertices() throws UnsupportedDataTypeException {

            for (int i = 0; i < this.edificio.length; i++) {
                divisoes.addToFront(new Divisao(edificio[i]));
                //nt.addVertex(edificio[i]);
            }

            Iterator itj = divisoes.iterator();
            int j = 0;
            while (itj.hasNext()) {
                Divisao obj = (Divisao) itj.next();
                graphDoJogo.addVertex(obj);
                //System.out.println(j + " " + obj.getNome());
                j++;
            }
        }

        /**
         * Método Responsável por adicionar as arestas aos vértices
         * @throws UnsupportedDataTypeException - Excepção para o caso de o tipo de dados não ser suportado.
         * @throws InvalidMapException - Excepção para o caso de o mapa ser invalido.
         */

        private void addEdges() throws UnsupportedDataTypeException, InvalidMapException {

            for (int i = 0; i < this.ligacoes.length; i++) {
                Divisao Divisao1 = getByDivisaoName(ligacoes[i][0]);
                Divisao Divisao2 = getByDivisaoName(ligacoes[i][1]);
                graphDoJogo.addEdge(Divisao1, Divisao2);
                Divisao1.addLigacao(Divisao2);
                Divisao2.addLigacao(Divisao1);
                //System.out.println("edge " + Divisao1.getNome() + " > " + Divisao2.getNome());
            }
            if (!graphDoJogo.isConnected())
                throw new InvalidMapException("Mapa invalido, as divisões têm que ter todas conexão a outra");

        }




        /**
         * Método Responsável por criar a network do jogo.
         * @throws UnsupportedDataTypeException - Excepção para o caso de o tipo de dados não ser suportado.
         * @throws InvalidMapException - Excepção para o caso de mapa ser inválido.
         */

        public void CriarNetworkDoJogo() throws UnsupportedDataTypeException, InvalidMapException {
            //add vertex
            addVertices();
            //add edges
            addEdges();

        }

        /**
         * Método Responsável por retornar o caminho mais curto do mapa.
         * @param de - inicio
         * @return
         * @throws UnsupportedDataTypeException - Excepção para o caso de o tipo de dados não ser suportado.
         * @throws EmptyCollectionException - Excepção para o caso de a coleção ser vazia.
         */

        public Iterator<Divisao> getShortesPathMapaEntrada(String de) throws UnsupportedDataTypeException, EmptyCollectionException {

            Divisao inicio = getByDivisaoName(de);
            Divisao[] Entradas = new Divisao[this.getEntradas_saidas().length];
            System.out.println(this.getEntradas_saidas().length);
            for(int i = 0; i < this.getEntradas_saidas().length; i++) {
                Entradas[i] = this.getByDivisaoName(this.getEntradas_saidas()[i]);
            }                

            return graphDoJogo.dijkstraShortestPathTotalEntrada(inicio,Entradas,divisoes);

        }
        public Iterator<Divisao> getShortesPathMapaSaida(String de) throws UnsupportedDataTypeException, EmptyCollectionException {

            Divisao inicio = getByDivisaoName(de);
            Divisao[] Saidas = new Divisao[this.getEntradas_saidas().length];
            System.out.println(this.getEntradas_saidas().length);
            for(int i = 0; i < this.getEntradas_saidas().length; i++) {
                Saidas[i] = this.getByDivisaoName(this.getEntradas_saidas()[i]);
            }

            return graphDoJogo.dijkstraShortestPathTotalSaida(inicio,Saidas,divisoes);

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

        public boolean verificarEntradaSaida(String entrada) {
            boolean found = false;
            for (int i = 0; i < this.entradas_saidas.length; i++) {
                if (entrada.equalsIgnoreCase(this.entradas_saidas[i])) {
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
            for (int i = 0; i < this.edificio.length; i++) {
                vertexs += edificio[i] + " | ";
            }
            String edges = "";
            for (int i = 0; i < this.ligacoes.length; i++) {
                edges += ligacoes[i][0] + " > " + ligacoes[i][1] + " | ";
            }
            String weights = "";
            boolean[][] adjMatrix = graphDoJogo.getAdjMatrix();
            for (int i = 0; i < inimigos.length; i++) {

                Iterator itj2 = divisoes.iterator();
                Divisao inimigoRoom = getByDivisaoName(this.inimigos[i].getDivisão());
                while (itj2.hasNext()) {
                    Divisao obj = (Divisao) itj2.next();
                    int inimigoPoder = (int) this.inimigos[i].getPoder();
                    int inimigoPos = divisoes.vertexIndex(inimigoRoom);
                    int currentDivision = divisoes.vertexIndex(obj);

                    if (adjMatrix[inimigoPos][currentDivision]) {
                        weights += inimigoRoom.getNome() + " > " + obj.getNome() + " |poder inimigo  " + inimigoPoder + "\n";

                    }
                }
            }
            System.out.println("Vertices");
            System.out.println(vertexs);
            System.out.println("Ligações");
            System.out.println(edges);
            System.out.println("Pesos");
            System.out.println(weights);
        }
    }
