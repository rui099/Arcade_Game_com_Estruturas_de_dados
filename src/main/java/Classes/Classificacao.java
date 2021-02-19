package Classes;

import Estruturas.Lists.ArrayOrderedList.ArrayOrderedList;
import Exceptions.EmptyCollectionException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.activation.UnsupportedDataTypeException;
import java.io.*;
import java.util.Iterator;

    /**
     * Classe Classificação - Responsável por Atribuir os pontos e armazenar num ficheiro a informação,
     * consoante o resultado de cada jogo.
     */

public class Classificacao {

    private File file;

    private ArrayOrderedList<Jogador> orderedClassificacoes = new ArrayOrderedList<>();

        /**
         * Método Responsável por adicionar a pontuação a uma lista ordenada, com o intuito de ordenar
         * as pontuações corretamente.
         * @param jogador
         * @throws FileNotFoundException - Excepção para o caso de o ficheiro não ter sido encontrado.
         * @throws UnsupportedDataTypeException - Excepção para o caso de o tipo de dados não ser suportado.
         * @throws EmptyCollectionException - Excepção para o caso de a coleção estar vazia.
         */

    private void addPontuacao(Jogador jogador) throws FileNotFoundException, UnsupportedDataTypeException, EmptyCollectionException {
        reader();
        orderedClassificacoes.add(jogador);
    }

        /**
         * Método Responsável por guardar a informação das pontuações em ficheiro JSON
         * @param codMissao - nome da missão
         * @param jogador
         * @throws IOException - Excepção para o caso de ter falhado I/O
         * @throws EmptyCollectionException - Excepção para o caso de a coleção estar vazia.
         */

    public void guardarClassificacao(String codMissao,Jogador jogador) throws IOException, EmptyCollectionException {

        getFile(codMissao);
        addPontuacao(jogador);
        FileWriter writer = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(writer);

        Iterator<Jogador> itr = orderedClassificacoes.iterator();
        JSONArray arrayJog = new JSONArray();
        while (itr.hasNext()) {
            Jogador tmpjog = itr.next();
            JSONObject obj = new JSONObject();
            obj.put("nome", tmpjog.getNome());
            obj.put("pontos", tmpjog.getPontos());
            arrayJog.add(obj);
        }
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(arrayJog, writer);
        } catch (Exception E) {
            E.printStackTrace();
        } finally {
            writer.flush();
        }
    }

        /**
         * Método Responsável por imprimir na consola a classificação de um determinado mapa.
         * @param codMissao
         * @throws IOException - Excepção para o caso de I/O falhar
         * @throws EmptyCollectionException - Excepção para o caso de a coleção estar vazia.
         */

    public void printClassificacoes(String codMissao) throws IOException, EmptyCollectionException{

        getFile(codMissao);
        reader();
        Iterator itj = orderedClassificacoes.iterator();
        System.out.println("Missao: " + codMissao);
        int pos = 0;
        while (itj.hasNext()) {
            Jogador obj = (Jogador) itj.next();
            pos++;
            System.out.println("[posição " + pos + "] " + "jogador: " + obj.getNome() + " | pontos: " + obj.getPontos());
        }

    }

        /**
         * Método Responsável por ler o ficheiro json e adicionar os jogadores à lista de classificações.
         * @throws FileNotFoundException - Excepção para o caso de o ficheiro não ser encontrado.
         * @throws UnsupportedDataTypeException - Excepção para o caso de o tipo de dados não ser suportado.
         * @throws EmptyCollectionException - Excepção para o caso de a coleção estar vazia.
         */

    public void reader() throws FileNotFoundException, UnsupportedDataTypeException, EmptyCollectionException {

        orderedClassificacoes.removeAll();
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader(file));
        Jogador[] classificacoes = gson.fromJson(reader, Jogador[].class);
        if(classificacoes != null && classificacoes.length > 0 ) {
            for (Jogador jos : classificacoes) {
                orderedClassificacoes.add(jos);
            }
        }

    }

        /**
         * Método Responsável por criar o ficheiro de classificações
         * @param codMissao - nome do mapa
         * @throws IOException - Excepção para o caso de I/O falhar
         */

    private void getFile(String codMissao) throws IOException {

        String path = "classificacao/Classificacao_" + codMissao + ".json";
        file = new File(path);

        if (!file.exists()) {
            file.createNewFile();
        }

    }
}