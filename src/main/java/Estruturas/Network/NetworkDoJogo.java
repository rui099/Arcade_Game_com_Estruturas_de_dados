/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Estruturas.Network;


import Classes.Divisao;
import Estruturas.Lists.ArrayUnorderedList.ArrayUnorderedList;
import Exceptions.EmptyCollectionException;
import org.apache.commons.lang3.ArrayUtils;

import javax.activation.UnsupportedDataTypeException;
import java.util.Iterator;



/**
 * @author ruidu
 */
public class NetworkDoJogo<T> extends Network<T> {

    public void setEdgeWeightOneWay(T de, int weight, T para) {
        if (weight < 0.0) {
            throw new IllegalArgumentException("peso mão pode ser negativo");
        }

        int v1Pos = ArrayUtils.indexOf(vertices, de);
        int v2Pos = ArrayUtils.indexOf(vertices, para);

        // se o indexof não encontra o valor é -1
        if (v1Pos == -1) {
            throw new IllegalArgumentException("vertex not found ( vertex 1 )");
        }
        if (v2Pos == -1) {
            throw new IllegalArgumentException("vertex not found ( vertex 2 )");
        }


        double sumWeight = this.weightMatrix[v1Pos][v2Pos];
        if (this.weightMatrix[v1Pos][v2Pos] != 0.0) {
            weight += sumWeight;
        }
        this.weightMatrix[v1Pos][v2Pos] = weight;

    }

    public ArrayUnorderedList<T> getVerticeNeighboor(T vertice) throws UnsupportedDataTypeException {
        if (vertice == null) {
            throw new NullPointerException("O Vértice é NULO!");
        } else {
            ArrayUnorderedList<T> aul = new ArrayUnorderedList<>();
            int indiceVertice = getPosition(vertice);
            if (validateIndex(indiceVertice)) {
                for (int i = 0; i < numVertices; i++) {
                    if (adjMatrix[i][indiceVertice]) {
                        aul.addToRear(vertices[i]);
                    }
                }
                for (int i = 0; i < numVertices; i++) {
                    if (adjMatrix[indiceVertice][i] && !aul.contains(vertices[i])) {
                        aul.addToRear(vertices[i]);
                    }
                }
                return aul;
            }
            return aul;
        }
    }

    /*protected Iterator<T> dijkstraShortestPath(T startVertex, T endVertex) throws UnsupportedDataTypeException, EmptyCollectionException {
        int startIndex = ArrayUtils.indexOf(vertices, startVertex);
        int endIndex = ArrayUtils.indexOf(vertices, endVertex);

        // se o indexof não encontra o valor é -1
        if (startIndex == -1 || endIndex == -1) {
            throw new IllegalArgumentException("vertex not found");
        }
        ArrayUnorderedList<T> pathVertices = new ArrayUnorderedList<>();
        int[] distancesToOtherVertices = new int[super.size()];
        boolean[] visitedVertex = new boolean[super.size()];
        int[] verticesArray = new int[super.size()];

        for (int i = 0; i < distancesToOtherVertices.length; i++) {
            distancesToOtherVertices[i] = Integer.MAX_VALUE;
            visitedVertex[i] = false;
        }

        distancesToOtherVertices[startIndex] = 0;

        for (int i = 0; i < super.size(); i++) {
            int minVertex = findMinDistance(distancesToOtherVertices, visitedVertex);
            visitedVertex[minVertex] = true;

            for (int j = 0; j < super.size(); j++) {
                if (this.adjMatrix[minVertex][j] && !visitedVertex[j]) {
                    int dist = (int) (distancesToOtherVertices[minVertex] + this.weightMatrix[minVertex][j]);
                    if (dist < distancesToOtherVertices[j]) {
                        distancesToOtherVertices[j] = dist;
                        verticesArray[j] = minVertex;
                    }
                }
            }
        }


        int j;
        for (int i = endIndex; i < super.size(); i++) {
            if (i > 0) {
                j = i;
                do {
                    j = verticesArray[j];
                    pathVertices.addToFront(super.vertices[j]);
                } while (j != 0);
            }
        }

        if (visitedVertex[endIndex]) {
            pathVertices.addToRear(super.vertices[endIndex]);

        }

        int lastIndexOfStart;
        lastIndexOfStart = pathVertices.lastIndexOf(startVertex);
        for (int i = lastIndexOfStart - 1; i >= 0; i--) {

            pathVertices.removeIndex(i);
        }

        return pathVertices.iterator();
    }

    public Iterator<T> dijkstraShortestPathTotalEntrada(T startVertex, T[] arrayEnds) throws UnsupportedDataTypeException, EmptyCollectionException {
        int melhorResultado = Integer.MAX_VALUE;
        Iterator<T> melhorIterator = null;
        for(int i =0 ;i < arrayEnds.length; i++ ) {
            int tempResult = 0;
            Iterator it1 = dijkstraShortestPath(arrayEnds[i],startVertex);
            while (it1.hasNext()) {
                Divisao obj = (Divisao) it1.next();
                tempResult = (int) (tempResult + obj.getPoderTotal());
            }
            if(tempResult < melhorResultado) {
                melhorResultado = tempResult;
                melhorIterator = dijkstraShortestPath(arrayEnds[i],startVertex);
                System.out.println(melhorResultado);
            }
        }
        return melhorIterator ;
    }

    public Iterator<T> dijkstraShortestPathTotalSaida(T startVertex, T[] arrayEnds) throws UnsupportedDataTypeException, EmptyCollectionException {
        int melhorResultado = Integer.MAX_VALUE;
        Iterator<T> melhorIterator = null;
        for(int i =0 ;i < arrayEnds.length; i++ ) {
            int tempResult = 0;
            Iterator it1 = dijkstraShortestPath(startVertex,arrayEnds[i]);
            while (it1.hasNext()) {
                Divisao obj = (Divisao) it1.next();
                tempResult = (int) (tempResult + obj.getPoderTotal());
            }
            if(tempResult < melhorResultado) {
                melhorResultado = tempResult;
                melhorIterator = dijkstraShortestPath(startVertex, arrayEnds[i]);
                System.out.println(melhorResultado);
            }
        }
        return melhorIterator ;
    }*/
}