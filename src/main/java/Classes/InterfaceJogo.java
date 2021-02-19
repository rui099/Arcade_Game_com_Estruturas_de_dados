    package Classes;

    import Exceptions.EmptyCollectionException;
    import Exceptions.InvalidMapException;
    import java.io.File;
    import java.io.IOException;
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
                    boolean mapaValido = false;
                        while(!mapaValido) {
                            System.out.println("Escolha o Mapa ");
                            printMapas();
                            nome = scanner.next();
                            String path = "mapas/" + nome;
                            file = new File(path);
                                 if (file.exists()) {
                                    this.jogo.setMap(path);
                                    mapaValido = true;
                                    jogar();
                                 }
                            System.out.println("Ficheiro Invalido");
                        }

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

                    boolean mapaValido = false;
                    while(!mapaValido) {
                        System.out.println("Escolha o Mapa ");
                        printMapas();
                        nome = scanner.next();
                        String path = "mapas/" + nome;
                        file = new File(path);
                        if (file.exists()) {
                            this.jogo.setMap(path);
                            mapaValido = true;
                            jogar();
                        }
                        System.out.println("Ficheiro Invalido");
                    }

                }

                /**
                 * Consultar Pontuações
                 */

                case 4: {

                    classif.printClassificacoes(jogo.getMap().getCod_missao());
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
    }