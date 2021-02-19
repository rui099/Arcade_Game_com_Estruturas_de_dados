/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Estruturas.Network;

import Estruturas.Lists.ArrayUnorderedList.ArrayUnorderedList;
import Exceptions.EmptyCollectionException;
import Estruturas.Interfaces.NetworkADT;
import org.apache.commons.lang3.ArrayUtils;

import javax.activation.UnsupportedDataTypeException;
import java.util.Iterator;

/**
 *
 * @author ruidu
 */
public class Network<T> extends Graph<T> implements NetworkADT<T> {


    protected final double DEFAULT_WEIGHT = 0.0;

    public Network() {
        super();

    }

    @Override
    public void addEdge(T vertex1, int weight, T vertex2) throws UnsupportedDataTypeException {

        if (weight < 0.0) {
            throw new IllegalArgumentException("peso mão pode ser negativo");
        }

        super.addEdge(vertex1, vertex2);
        this.setEdgeWeight(vertex1, weight, vertex2);
    }

    @Override
    public void setEdgeWeight(T vertex1, int weight, T vertex2)  {
        if (weight < 0.0) {
            throw new IllegalArgumentException("peso mão pode ser negativo");
        }

        int v1Pos = ArrayUtils.indexOf(vertices, vertex1);
        int v2Pos = ArrayUtils.indexOf(vertices, vertex2);

        // se o indexof não encontra o valor é -1
        if (v1Pos == -1 || v2Pos == -1) {
            throw new IllegalArgumentException("vertex not found");
        }

        this.weightMatrix[v1Pos][v2Pos] = weight;
        this.weightMatrix[v2Pos][v1Pos] = weight;
    }

    @Override
    public double getEdgeWeight(T vertex1, T vertex2) {
        double result = 0;
        int v1Pos = ArrayUtils.indexOf(vertices, vertex1);
        int v2Pos = ArrayUtils.indexOf(vertices, vertex2);

        // se o indexof não encontra o valor é -1
        if (v1Pos == -1 || v2Pos == -1) {
            throw new IllegalArgumentException("vertex not found");
        }
        return this.weightMatrix[v1Pos][v2Pos];
    }

    @Override
    public double minimalPath(T vertex1, T vertex2) throws UnsupportedDataTypeException, EmptyCollectionException {

        Iterator<T> iterator = this.dijkstraShortestPath(vertex1, vertex2);

        int oldIndex = 0;
        int newIndex;
        int sum = 0;

        while (iterator.hasNext()) {
            newIndex = ArrayUtils.indexOf(vertices, iterator.next());
            sum += this.weightMatrix[oldIndex][newIndex];
            oldIndex = newIndex;
        }

        return sum;
    }

    public Iterator<T> getShortestPath(T vertex1, T vertex2) throws UnsupportedDataTypeException, EmptyCollectionException {

        return this.dijkstraShortestPath(vertex1, vertex2);

    }

    protected Iterator<T> dijkstraShortestPath(T startVertex, T endVertex) throws UnsupportedDataTypeException, EmptyCollectionException {
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

    public void printVertexs(){
        for (int i = 0; i < this.size(); i++) {
            System.out.println(vertices[i].toString());
        }
    }

    @Override
    public String toString() {
        String result = "";

        result +=  " ";
        for (int i = 0; i < numVertices; i++) {
            result +=  "   " + i;
            if (i < 10) {
                result += " ";
            }
        }
        result += "\n---------------------------------------------\n";

        for (int i = 0; i < numVertices; i++) {
            result += "" + i + "\t";

            for (int j = 0; j < numVertices; j++) {
                result += this.weightMatrix[i][j] + "\t";
            }
            result += "\n";
        }

        return result;
    }

}
