package Classes;

import Estruturas.Lists.ArrayUnorderedList.ArrayUnorderedList;
import Estruturas.Network.GraphDoJogo;
import Exceptions.InvalidMapException;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;

import javax.activation.UnsupportedDataTypeException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;

public class JsonImporter {

    @SerializedName(value = "cod_missao", alternate = {"cod-missao"})
    private String cod_missao;
    private String versao;
    private String[] edificio;
    private String[] ligacoes[];
    private Inimigo[] inimigos;
    private String[] entradas;
    private String[] saidas;
    private Alvo alvo;
    private ArrayUnorderedList<Divisao> divisoes;

    public JsonImporter() {
        this.divisoes = new ArrayUnorderedList<>();
    }

    public Iterator<Map> importMaps() throws FileNotFoundException, InvalidMapException, UnsupportedDataTypeException {
        ArrayUnorderedList<Map> mapas = new ArrayUnorderedList<>();

        Gson gson = new Gson();
        File folder = new File("mapas");
        File[] listOfFiles = folder.listFiles();

        for (File files : listOfFiles) {
            if (files.isFile()) {
                JsonReader reader = new JsonReader(new FileReader("mapas/" + files.getName()));
                JsonImporter importedJson = gson.fromJson(reader, JsonImporter.class);
                Map map = importedJson.criarMapaDoJogo();
                mapas.addToFront(map);
            }
        }
        return mapas.iterator();
    }




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
            System.out.println("ERRO Divisao não encontrada");
        }
        return room;

    }




    /**
     * Método Responsável por criar a network do jogo.
     * @throws UnsupportedDataTypeException - Excepção para o caso de o tipo de dados não ser suportado.
     * @throws InvalidMapException - Excepção para o caso de mapa ser inválido.
     */

    private Map criarMapaDoJogo() throws UnsupportedDataTypeException, InvalidMapException {

        GraphDoJogo<Divisao> graphDoJogo = new GraphDoJogo<>();
        ArrayUnorderedList<Divisao> entradasDivisao = new ArrayUnorderedList<>();
        ArrayUnorderedList<Divisao> saidasDivisao = new ArrayUnorderedList<>();

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

        Iterator itj1 = divisoes.iterator();
        while (itj1.hasNext()) {
            Divisao obj = (Divisao) itj1.next();
            for (int i = 0; i < this.entradas.length; i++) {
                if (obj.getNome().equals(this.entradas[i]))
                    entradasDivisao.addToFront(obj);
            }
        }

        Iterator itj2 = divisoes.iterator();
        while (itj2.hasNext()) {
            Divisao obj = (Divisao) itj2.next();
            for (int i = 0; i < this.saidas.length; i++) {
                if (obj.getNome().equals(this.saidas[i]))
                    saidasDivisao.addToFront(obj);
            }
        }

        for (int i = 0; i < inimigos.length; i++) {
            Divisao inimigoRoom = getByDivisaoName(this.inimigos[i].getDivisão());
            inimigoRoom.addInimigo(this.inimigos[i]);
        }

            Map map = new Map(cod_missao, versao, alvo, entradasDivisao, saidasDivisao,
                divisoes, graphDoJogo,ligacoes, inimigos);
        return map;
    }


}
