package Classes;

import Estruturas.Interfaces.QueueADT;
import Estruturas.Lists.Queue.LinkedQueue;
import Exceptions.EmptyCollectionException;
import Exceptions.InvalidMapException;

import javax.activation.UnsupportedDataTypeException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

    /**
     * Classe Jogo
     *
     * @author Rafael Costa
     */

public class Jogo {

    private Mapa map;

    private final QueueADT<String> divisoesPercorridas;

    private Jogador jog;

    private String modoJogo;

    private Divisao currentDivisao;

    private Classificacao classificacao;

    /**
     * Método Construtor da Classe Jogo
     */

    public Jogo(Jogador jogador) {
        this.divisoesPercorridas = new LinkedQueue();
        this.jog = jogador;
        this.classificacao = new Classificacao();
    }

    /**
     * Método Responsável por retornar o mapa do jogo atual.
     *
     * @return map - mapa escolhido
     */

    public Mapa getMap() {
        return map;
    }

    /**
     * Método Responsável por alterar o mapa do jogo atual.
     *
     * @param mapa - mapa escolhido
     */

    public void setMap(String mapa) throws FileNotFoundException, InvalidMapException, UnsupportedDataTypeException {
        this.map = map.importMap(mapa);
        this.map.CriarNetworkDoJogo();
    }

    /**
     * Método Responsável por retornar o jogador que está a jogar.
     *
     * @return jog - jogador
     */

    public Jogador getJog() {
        return jog;
    }

    /**
     * Método Responsável por retornar a divisão atual em que o jogador se encontra.
     *
     * @return divisaoAtual
     */

    public Divisao getcurrentDivisao() {
        return currentDivisao;
    }

    /**
     * Método que altera a divisao atual onde o jogador está
     *
     * @param divisaoAtual
     */

    public void setcurrentDivisao(Divisao divisaoAtual) {
        this.currentDivisao = divisaoAtual;
    }

    /**
     * Método Responsável por retornar o modo de jogo escolhido
     *
     * @return modoJogo - modo de jogo
     */

    public String getModoJogo() {
        return modoJogo;
    }

    /**
     * Método Responsável por alterar o modo de jogo escolhido
     *
     * @param modo
     */

    public void setModoJogo(String modo) {
        this.modoJogo = modo;
    }

    /**
     * Método Responsável pela jogabilidade Manual.
     */

    private void inicioManual() throws IOException {

        boolean entrada = false;
        while (!entrada) {

            System.out.println("Entradas Disponiveis");
            for (int i = 0; i < this.map.getEntradas_saidas().length; i++) {
                System.out.print(map.getEntradas_saidas()[i] + "  ");
            }
            System.out.println("\nPor onde quer Entrar ?");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String input = reader.readLine();
            if (this.map.verificarEntradaSaida(input)) {
                entrada = true;
                this.currentDivisao = map.getByDivisaoName(input);
            }

        }

        this.jog.setPontos(this.jog.getPontos() - currentDivisao.getPoderTotal());

    }


    public void modoManual() throws EmptyCollectionException, IOException{

        boolean objetivo = false;
        boolean jogo = true;
        inicioManual();
        //sala em sala
        while (jogo) {
            if (currentDivisao.getInimigos().size() > 0) {
                System.out.println(currentDivisao.inimigosDivisao());
                System.out.println("***********************************************************");
            }
            System.out.println("Divisões Disponiveis" + "                       "
                    + "Pontos de Vida " + jog.getPontos());
            System.out.println(this.currentDivisao.stringNometLigacoes());
            System.out.println("Introduza o Nome da Divisao: ");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String input = reader.readLine();
            Iterator<Divisao> connectedRoomsItr = this.currentDivisao.getLigacoes();
            boolean found = false;
            while (connectedRoomsItr.hasNext() && !found) {
                Divisao tmpRoom = connectedRoomsItr.next();
                if (tmpRoom.getNome().equalsIgnoreCase(input)) {
                    this.currentDivisao = tmpRoom;
                    this.jog.setPontos(this.jog.getPontos() - currentDivisao.getPoderTotal());
                    if (currentDivisao.getInimigos().size() > 0) {
                        System.out.println("***********************************************************");
                        System.out.println("Perdeu " + currentDivisao.getPoderTotal() + " Pontos.");
                    }

                    found = true;
                }
            }
            if (this.currentDivisao.getNome().equals(map.getAlvo().getDivisão())) {
                objetivo = true;
                System.out.println("Alcançou o Objetivo. Saia do Edificio!");
            }
            if (jog.getPontos() <= 0.0) {
                System.out.println("Os seus Pontos de Vida Chegaram a 0. Perdeu!");
                jogo = false;
            }
            if (this.map.verificarEntradaSaida(this.currentDivisao.getNome()) && objetivo && jog.getPontos() > 0.0) {
                jogo = false;
                System.out.println("O Jogo Acabou");
                classificacao.guardarClassificacao(this.map.getCod_missao(), jog);

            }
        }
    }

    /**
     * Método Responsável pela jogabilidade automática. Determina o caminho com menor perda de vida.
     *
     * @return
     */

    public void modoAutomatico() throws IOException, EmptyCollectionException{
        boolean valido = false;
        String entrada = null;
        String saida = null;


        System.out.println("***********************************************************");
        System.out.println("\t\t\t\t\t Modo Automático");
        System.out.println("***********************************************************");
        LinkedQueue<Divisao> queueCaminho = new LinkedQueue<>();
        Iterator it1 = map.getShortesPathMapaEntrada(map.getAlvo().getDivisão());

        Divisao prev = new Divisao("temp");
        while (it1.hasNext()) {
            Divisao obj = (Divisao) it1.next();
            this.currentDivisao = obj;
            if (!prev.getNome().equals(this.currentDivisao.getNome()))
                queueCaminho.enqueue(obj);
            prev = obj;
        }

        Iterator it0 = map.getShortesPathMapaSaida(map.getAlvo().getDivisão());
        while (it0.hasNext()) {
            Divisao obj = (Divisao) it0.next();
            if (!obj.getNome().equals(map.getAlvo().getDivisão()))
                    queueCaminho.enqueue(obj);
        }

        String caminho = "";
        while (queueCaminho.size() != 0) {
            this.currentDivisao = queueCaminho.dequeue();
            caminho += this.currentDivisao.getNome() + " > ";
            System.out.println(this.currentDivisao.getNome());
            if (this.currentDivisao.getNome().equals(map.getAlvo().getDivisão())) {
                System.out.println("\t\t\t\t\tAlcançou o objetivo, a sair do edificio");
            }
            System.out.println(this.currentDivisao.getPoderTotal());
            this.jog.setPontos(this.jog.getPontos() - this.currentDivisao.getPoderTotal());

            if (currentDivisao.getInimigos().size() > 0) {
                System.out.println(currentDivisao.inimigosDivisao());
                System.out.println("Perdeu " + currentDivisao.getPoderTotal() + " Pontos."
                        + "                            Pontos de vida " + jog.getPontos());
            }
        }
        System.out.println("\n" + "Caminnho: " + caminho);
        System.out.println("PONTOS : " + jog.getPontos());
        jog.setPontos(100);

    }


    /**
     * Método Responsável por retornar toda a informação do jogo, em formato String.
     *
     * @return
     */

    @Override
    public String toString() {
        return "Mapa: " + map + "Divisao Atual: " + currentDivisao + "Pontos de Vida: " + jog.getPontos();
    }

}