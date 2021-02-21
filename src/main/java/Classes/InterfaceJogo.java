    package Classes;

    import Exceptions.EmptyCollectionException;
    import Exceptions.InvalidMapException;

    import java.io.BufferedReader;
    import java.io.File;
    import java.io.IOException;
    import java.io.InputStreamReader;
    import java.util.Iterator;
    import java.util.Scanner;

    /**
     *  Classe InterfaceJogo - Responsável por mostrar informacao e interagir com o jogador através da Consola.
     *  @author Rafael Costa
     */

    public class InterfaceJogo{

        private Scanner scanner;

        private Jogador jogador;

        private File file;

        public static String nome = "Nada";

        private Classificacao classif;

        private Jogo jogo;

        /**
         * Método Construtor da Classe InterfaceJogo.
         */

        public InterfaceJogo() {
            this.scanner = new Scanner(System.in);
            this.classif = new Classificacao();
            this.jogador = new Jogador(nome,100);
        }

        /**
         * Método Responsável por apresentar o menu inicial do jogo.
         */

        public void menuInicial() throws IOException, InvalidMapException, EmptyCollectionException {

            System.out.println("***********************************************************");
            System.out.println("\t\t\t\t\t Seja Bem-Vindo e Divirta-se !!!");
            System.out.println("***********************************************************");
            System.out.println("1- Jogar ");
            System.out.println("2- Créditos ");
            System.out.println("3- Sair ");

            switch (scanner.nextInt()) {

                /**
                 * Começar o Jogo
                 */

                case 1: {
                    this.jogo = new Jogo(jogador);
                    System.out.println("Nome: ");
                    nome = scanner.next();
                        if(nome.equals(" ")){
                            System.out.println("Tem de Introduzir um Nome!");
                        }
                    this.jogador.setNome(nome);
                    System.out.println("MISSÕES:");
                    Missao missao = pedirMissao();
                    jogo.setMissao(missao);
                    System.out.println("MAPAS:");
                    Map mapa = pedirMapa(missao);
                    jogo.setMap(mapa);
                    jogar();
                }

                /**
                 * Créditos
                 */

                case 2: {

                    System.out.println("***********************************************************");
                    System.out.println("\t\t\t\t\t Créditos");
                    System.out.println("***********************************************************");
                    System.out.println("Rafael Figueiredo da Costa | Num: 8180246");
                    System.out.println("Rui Duarte Pereira Soares  | Num: 8150289");
                    menuInicial();

                }

                /**
                 * Terminar o Programa
                 */

                case 3: {
                    System.exit(0);
                }
            }
        }

        public void jogar() throws IOException, InvalidMapException, EmptyCollectionException {

            System.out.println("**************************************************************************************");
            System.out.println("\t\t\t\t\t Improbable Mission Force - Game Of the Year 2021");
            System.out.println("**************************************************************************************");
            System.out.println("1- Modo Manual");
            System.out.println("2- Modo Automatico" );
            System.out.println("3- Mudar de Mapa");
            System.out.println("4- Consultar Classificações do Mapa: " + this.jogo.getMap().getCod_missao());
            System.out.println("5- Visualizar mapa");
            System.out.println("6- Ir para o Menu Inicial");

            switch (scanner.nextInt()){

                /**
                 * Escolher o Modo de Jogo
                 */

                case 1: {

                    jogo.modoManual();
                    jogar();

                }

                /**
                 * Iniciar o Jogo
                 */

                case 2: {

                    jogo.modoAutomatico();
                    jogar();

                }

                case 3: {
                    Map mapa = pedirMapa(jogo.getMissao());
                    jogo.setMap(mapa);
                    boolean mapaValido = false;


                }

                /**
                 * Consultar Pontuações
                 */

                case 4: {

                    classif.printClassificacoes(jogo.getMap());
                    jogar();
                }

                /**
                 * Visualizar mapa
                 */

                case 5:{

                    this.jogo.getMap().visualizarMapa();
                    jogar();
                }

                /**
                 * Voltar ao Menu Inicial
                 */
                case 6:{

                    menuInicial();

                }
            }
        }

        /**
         * Método Responsável por mostrar Mapas Existentes.
         */

        public void printMapas(){

            File folder = new File("mapas");
            File[] listOfFiles = folder.listFiles();

            for (File file : listOfFiles) {
                if (file.isFile()) {
                    System.out.print(file.getName() + " | ");
                }
            }

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

        public Missao pedirMissao() throws IOException, InvalidMapException {
            boolean found = false;
            GestorDeMissoes gestorMissoes = new GestorDeMissoes();
            gestorMissoes.organizarMissoes();
            gestorMissoes.getMissoes();
            Missao missaoEscolhida = null;
            while(!found) {

                Iterator it0 = gestorMissoes.getMissoes().iterator();
                int index = 0;
                while (it0.hasNext()) {
                    Missao missao = (Missao) it0.next();
                    System.out.println("<" + index + "> " + missao.getCod_missao() + "  ");
                    index++;
                }
                System.out.println("\nEscolha a missao");
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String input = reader.readLine();

                if (isNumber(input) && Integer.parseInt(input) < gestorMissoes.getMissoes().size()) {
                    found = true;
                    missaoEscolhida = gestorMissoes.getMissoes().get(Integer.parseInt(input));
                } else {
                    System.out.println("Numero de missao invalido ");
                }
            }
            return missaoEscolhida;
        }

        public Map pedirMapa(Missao missao) throws IOException {
            boolean found = false;
            Map mapaEscolhido = null;
            while(!found) {
                Iterator it0 = missao.getMapasDaMissao().iterator();
                int index = 0;
                while (it0.hasNext()) {
                    Map mapa = (Map) it0.next();
                    System.out.println("<" + index + "> " + mapa.getCod_missao() + " " + mapa.getVersao() + "  ");
                    index++;
                }
                System.out.println("\nEscolha o mapa");
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String input = reader.readLine();

                if (isNumber(input) && Integer.parseInt(input) < missao.getMapasDaMissao().size()) {
                    found = true;
                    mapaEscolhido = missao.getMapasDaMissao().get(Integer.parseInt(input));
                } else {
                    System.out.println("Numero de Mapa invalido ");
                }
            }
            return mapaEscolhido;
        }
    }