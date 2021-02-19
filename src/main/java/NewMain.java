import Classes.Divisao;
import Classes.InterfaceJogo;
import Classes.Jogo;
import Classes.Mapa;
import Estruturas.Lists.ArrayUnorderedList.ArrayUnorderedList;
import Estruturas.Network.Graph;
import Estruturas.Network.Network;
import Exceptions.EmptyCollectionException;
import Exceptions.InvalidMapException;

import javax.activation.UnsupportedDataTypeException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ruidu
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws EmptyCollectionException, UnsupportedDataTypeException {

        /*
        ArrayUnorderedList<String> resultList = new ArrayUnorderedList<>();
        Graph<String> a = new Graph<>();
        a.addVertex("aaaaaa");
        a.addVertex("bbbbbb");
        a.addVertex("cccccc");
        a.addVertex("dddddd");
        a.addVertex("eeeeee");

        a.addEdge("aaaaaa", "eeeeee");
        a.addEdge("aaaaaa", "dddddd");
        a.addEdge("eeeeee", "bbbbbb");
        a.addEdge("cccccc", "dddddd");

        System.out.println("BFS");
        Iterator it = a.iteratorBFS("aaaaaa");

        while (it.hasNext()) {
            String obj = (String) it.next();
            System.out.println(obj);
        }
        System.out.println("acabou");
        System.out.println("DFS");
        Iterator it2 = a.iteratorDFS("aaaaaa");

        while (it2.hasNext()) {
            String obj = (String) it2.next();
            System.out.println(obj);
        }

        System.out.println(a.isConnected());
        System.out.println("acabou");
        System.out.println("NetWork");
        Network<String> nt = new Network<String>();
        nt.addVertex("aaaaaa");
        nt.addVertex("bbbbbb");
        nt.addVertex("cccccc");
        nt.addVertex("dddddd");
        nt.addVertex("eeeeee");

        nt.addEdge("aaaaaa", 1, "eeeeee");
        nt.addEdge("aaaaaa", 3, "dddddd");
        nt.addEdge("eeeeee", 2, "bbbbbb");
        nt.addEdge("cccccc", 3, "dddddd");
        nt.addEdge("dddddd", 1, "bbbbbb");
        nt.addEdge("cccccc", 3, "bbbbbb");
        System.out.println(nt.toString());
        //a > d 3           a > e 1
//        //d > b 1           e > b 2
        System.out.println("peso do camiho djikstra");
        System.out.println(nt.minimalPath("aaaaaa", "bbbbbb"));
        //nt.shortestPathWeight("aaaaaa", "bbbbbb");
        
        Iterator it3 = nt.getShortestPath("aaaaaa", "bbbbbb");
        System.out.println("camiho djikstra");
        while (it3.hasNext()) {
            String obj = (String) it3.next();
            System.out.println(obj);
        }
*/
        try {
            /*
            Mapa mapa = Mapa.importMap("C:\\Users\\Rafael Costa\\Desktop\\ED_Grupo_13_TP\\grupo_13_ed\\Mapa.json");
            mapa.CriarNetworkDoJogo();
            System.out.println(mapa.getNetworkDoJogo().isConnected());

            Iterator itj = mapa.getShortesPathMapa("Garagem", "Heliporto");
            System.out.println("camiho djikstra");

            while (itj.hasNext()) {
                Divisao obj = (Divisao) itj.next();
                System.out.println(obj.getNome());
            }
            System.out.println(mapa.getShortestCostMapa("Garagem", "Heliporto"));

            System.out.println(mapa.getNetworkDoJogo().toString());

            System.out.println(mapa.verificarEntradaSaida("Heliporto"));
            Jogo jogo = new Jogo();
            jogo.setMap("C:\\Users\\Rafael Costa\\Desktop\\ED_Grupo_13_TP\\grupo_13_ed\\Mapa.json");
            //jogo.modoAutomatico("Heliporto", "Heliporto");
            jogo.modoManual();
            //Classificacao2.guardarClassificacao("Conaça");
*/
            InterfaceJogo ij = new InterfaceJogo();
            ij.menuInicial();

        } catch (FileNotFoundException | InvalidMapException ex) {
            Logger.getLogger(NewMain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
