package Estruturas.Network;

import Classes.Divisao;
import Estruturas.Lists.ArrayUnorderedList.ArrayUnorderedList;
import Exceptions.EmptyCollectionException;
import org.apache.commons.lang3.ArrayUtils;

import javax.activation.UnsupportedDataTypeException;
import java.util.Iterator;

public class GraphDoJogo<T> extends Graph<T> {

    protected Iterator<T> dijkstraShortestPath(Divisao startVertex, Divisao endVertex,ArrayUnorderedList<Divisao> divisoes) throws UnsupportedDataTypeException, EmptyCollectionException {
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
                    int dist = (int) (distancesToOtherVertices[minVertex] + divisoes.get(j).getPoderTotal());
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
        lastIndexOfStart = pathVertices.lastIndexOf((T) startVertex);
        for (int i = lastIndexOfStart - 1; i >= 0; i--) {

            pathVertices.removeIndex(i);
        }

        return pathVertices.iterator();
    }

    protected int findMinDistance(int[] distance, boolean[] visitedVertex) {
        int minIndex = -1;
        int minVertex = Integer.MAX_VALUE;

        for (int i = 0; i < distance.length; i++) {
            if (!visitedVertex[i] && distance[i] <= minVertex) {
                minVertex = distance[i];
                minIndex = i;
            }
        }

        return minIndex;
    }

    public Iterator<T> dijkstraShortestPathTotalEntrada(Divisao startVertex, ArrayUnorderedList<Divisao> arrayEnds,ArrayUnorderedList<Divisao> divisoes) throws UnsupportedDataTypeException, EmptyCollectionException {
        int melhorResultado = Integer.MAX_VALUE;
        Iterator<T> melhorIterator = null;
        Iterator it0 = arrayEnds.iterator();
        while (it0.hasNext()) {
            Divisao entrada = (Divisao) it0.next();
            int tempResult = 0;
            Iterator it1 = dijkstraShortestPath(entrada, startVertex,divisoes);
            while (it1.hasNext()) {
                Divisao obj = (Divisao) it1.next();
                tempResult = (int) (tempResult + obj.getPoderTotal());
            }
            if (tempResult < melhorResultado) {
                melhorResultado = tempResult;
                melhorIterator = dijkstraShortestPath(entrada, startVertex,divisoes);
            }
        }
        return melhorIterator;
    }

    public Iterator<T> dijkstraShortestPathTotalSaida(Divisao startVertex, ArrayUnorderedList<Divisao> arrayEnds,ArrayUnorderedList<Divisao> divisoes) throws UnsupportedDataTypeException, EmptyCollectionException {
        int melhorResultado = Integer.MAX_VALUE;
        Iterator<T> melhorIterator = null;
        Iterator it0 = arrayEnds.iterator();
        while (it0.hasNext()) {
            Divisao saida = (Divisao) it0.next();
            int tempResult = 0;
            Iterator it1 = dijkstraShortestPath(startVertex, saida,divisoes);
            while (it1.hasNext()) {
                Divisao obj = (Divisao) it1.next();
                tempResult = (int) (tempResult + obj.getPoderTotal());
            }
            if (tempResult < melhorResultado) {
                melhorResultado = tempResult;
                melhorIterator = dijkstraShortestPath(startVertex, saida,divisoes);
            }



        }
        return melhorIterator;
    }
}