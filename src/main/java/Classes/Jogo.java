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
import java.util.Random;

/**
 * Classe Jogo
 *
 * @author Rafael Costa
 */

public class Jogo {
    private Missao missao;

    private Map map;

    private final QueueADT<String> divisoesPercorridas;

    private Jogador jog;

    private String modoJogo;

    private Divisao currentDivisao;

    private Classificacao classificacao;

    private boolean infraVermelhos;
    /**
     * Método Construtor da Classe Jogo
     */

    public Jogo(Jogador jogador) {
        this.divisoesPercorridas = new LinkedQueue();
        this.jog = jogador;
        this.classificacao = new Classificacao();
        this.infraVermelhos = false;
    }

    /**
     * Método Responsável por retornar o mapa do jogo atual.
     *
     * @return map - mapa escolhido
     */

    public Map getMap() {
        return map;
    }

    /**
     * Método Responsável por alterar o mapa do jogo atual.
     *
     * @param mapa - mapa escolhido
     */

    public void setMap(Map mapa) {
        this.map = mapa ;
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

    public Missao getMissao() {
        return missao;
    }

    public void setMissao(Missao missao) {
        this.missao = missao;
    }

    /**
     * Método Responsável pela jogabilidade Manual.
     */

    private Divisao inicioManual() throws IOException {
        Divisao Dentrada = null;
        boolean entrada = false;
        while (!entrada) {
            System.out.println("Entradas Disponiveis");
            Iterator it0 = map.getEntradas().iterator();
            int index = 0;
            while (it0.hasNext()) {
                Divisao divisao = (Divisao) it0.next();
                System.out.print("<" + index + "> " + divisao.getNome() + "  ");
                index++;
            }
            System.out.println("\nPor onde quer Entrar ?");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String input = reader.readLine();

            if (isNumber(input) && Integer.parseInt(input) < map.getEntradas().size()) {
                entrada = true;
                Dentrada = this.currentDivisao = map.getEntradas().get(Integer.parseInt(input));
            } else if (map.verificarEntrada(input)) {
                entrada = true;
                Dentrada = this.currentDivisao = map.getByDivisaoName(input);
            } else {
                System.out.println("Divisão ou numero de divisão invalido ");
            }
            if (currentDivisao != null)
                this.jog.setPontos(this.jog.getPontos() - currentDivisao.getPoderTotal());
        }
        return Dentrada;
    }


    public void modoManual() throws EmptyCollectionException, IOException {
        LinkedQueue<Divisao> queueCaminho = new LinkedQueue<>();
        boolean objetivo = false;
        boolean jogo = true;
        Divisao divisaoInicial = inicioManual();
        Divisao divisaoIV = infravermelhosDivisao();
        if (divisaoInicial == null)
            divisaoInicial = inicioManual();
        queueCaminho.enqueue(divisaoInicial);
        //sala em sala
        while (jogo) {
            if(infraVermelhos)
                System.out.println("<Visão infravermelhos>");

            moverinimigos();
            if (currentDivisao.getInimigos().size() > 0) {
                System.out.println(currentDivisao.inimigosDivisao());
                System.out.println("***********************************************************");
            }
            System.out.println("Divisões Disponiveis" + "                       "
                    + "Pontos de Vida " + jog.getPontos());
            Iterator it0 = this.currentDivisao.getLigacoesIT();
            int index = 0;
            while (it0.hasNext()) {
                Divisao divisao = (Divisao) it0.next();
                System.out.print("<" + index + "> " + divisao.getNome() + "  ");
                index++;
            }
            System.out.println("\nIntroduza o Nome da Divisao: \n>Localização atual " + this.currentDivisao.getNome());
            playerMove();

            queueCaminho.enqueue(this.currentDivisao);
            if(this.currentDivisao.getNome().equals(divisaoIV.getNome())) {
                this.infraVermelhos = true;
                System.out.println("Encontrou uns oculos com infravermelhos de um dos criminosos");
            }
            if (currentDivisao.getInimigos().size() > 0) {
                System.out.println("***********************************************************");
                System.out.println("Perdeu " + currentDivisao.getPoderTotal() + " Pontos.");
            }
            if (this.currentDivisao.getNome().equals(map.getAlvo().getDivisão())) {
                objetivo = true;
                System.out.println("Alcançou o Objetivo. Saia do Edificio!");
            }
            if (jog.getPontos() <= 0.0) {
                System.out.println("Os seus Pontos de Vida Chegaram a 0. Perdeu!");
                jogo = false;
            }
            if (this.map.verificarSaida(this.currentDivisao.getNome()) && objetivo && jog.getPontos() > 0.0) {
                jogo = false;
                System.out.println("O Jogo Acabou");
                classificacao.guardarClassificacao(this.map, jog);
            }

        }

    }

    /**
     * Método Responsável pela jogabilidade automática. Determina o caminho com menor perda de vida.
     *
     * @return
     */
    public void playerMove() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input = reader.readLine();
        Iterator<Divisao> connectedRoomsItr = this.currentDivisao.getLigacoesIT();
        boolean found = false;
        while (connectedRoomsItr.hasNext() && !found) {
            Divisao tmpRoom = connectedRoomsItr.next();

            if (isNumber(input) && Integer.parseInt(input) < this.currentDivisao.getLigacoes().size()) {
                if (tmpRoom.getNome().equals(this.currentDivisao.getLigacoes().get(Integer.parseInt(input)).getNome())) {
                    this.currentDivisao = tmpRoom;
                    this.jog.setPontos(this.jog.getPontos() - this.currentDivisao.getPoderTotal());
                    found = true;
                }
            } else if (tmpRoom.getNome().equalsIgnoreCase(input)) {
                this.currentDivisao = tmpRoom;
                this.jog.setPontos(this.jog.getPontos() - currentDivisao.getPoderTotal());
                found = true;
            } else {
                System.out.println("nome ou numero de divisao não disponivel");
            }
        }
    }

    public void modoAutomatico() throws IOException, EmptyCollectionException {
        System.out.println("***********************************************************");
        System.out.println("\t\t\t\t\t Modo Automático");
        System.out.println("***********************************************************");
        LinkedQueue<Divisao> queueCaminho = new LinkedQueue<>();
        Iterator it1 = map.getShortesPathMapaEntrada(map.getByDivisaoName(map.getAlvo().getDivisão()));

        Divisao prev = new Divisao("temp");
        while (it1.hasNext()) {
            Divisao obj = (Divisao) it1.next();
            this.currentDivisao = obj;
            if (!prev.getNome().equals(this.currentDivisao.getNome()))
                queueCaminho.enqueue(obj);
            prev = obj;
        }

        Iterator it0 = map.getShortesPathMapaSaida(map.getByDivisaoName(map.getAlvo().getDivisão()));
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

    private boolean isNumber(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (s == "")
                return false;
            if (Character.isDigit(s.charAt(i)) == false)
                return false;
        }
        return true;
    }

    private void moverinimigos() throws UnsupportedDataTypeException, EmptyCollectionException {
        for (int i = 0; i < this.map.getInimigos().length; i++) {
            Divisao divisaoInimigo = map.getByDivisaoName(map.getInimigos()[i].getDivisão());

            Random rand = new Random();
            int rand_int = rand.nextInt(divisaoInimigo.getLigacoes().size());

            divisaoInimigo.getLigacoes().get(rand_int).getInimigos().addToFront(this.map.getInimigos()[i]);
            this.map.getInimigos()[i].setDivisão(divisaoInimigo.getLigacoes().get(rand_int).getNome());
            divisaoInimigo.getInimigos().remove(this.map.getInimigos()[i]);
            if(this.infraVermelhos) {
                System.out.println(map.getInimigos()[i].getNome() + " foi da divisao " + divisaoInimigo.getNome()
                        + " para a divisão " + divisaoInimigo.getLigacoes().get(rand_int).getNome());
            }
        }
    }

    private Divisao infravermelhosDivisao(){
        Random rand = new Random();
        int rand_int = rand.nextInt(map.getDivisoes().size());
        Divisao divisaoIV = map.getDivisoes().get(rand_int);
        System.out.println(divisaoIV.getNome());
        return divisaoIV;
    }
}