/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Estruturas.Network;

import Estruturas.Interfaces.GraphADT;
import Estruturas.Lists.ArrayUnorderedList.ArrayUnorderedList;
import Estruturas.Lists.Queue.LinkedQueue;
import Estruturas.Lists.Stack.LinkedStack;
import Exceptions.EmptyCollectionException;
import org.apache.commons.lang3.ArrayUtils;

import javax.activation.UnsupportedDataTypeException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.apache.commons.lang3.ArrayUtils.contains;

/**
 *
 * @author ruidu
 */
public class Graph<T> implements GraphADT<T> {

    protected final int DEFAULT_CAPACITY = 10;
    protected int numVertices;
    protected boolean[][] adjMatrix;
    public T[] vertices;
    protected double[][] weightMatrix;

    public Graph() {
        this.numVertices = 0;
        this.adjMatrix = new boolean[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
        this.vertices = (T[]) (new Object[DEFAULT_CAPACITY]);
        this.weightMatrix = new double[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
    }


    public boolean[][] getAdjMatrix() {
        return adjMatrix;
    }



    @Override
    public void removeEdge(T vertex1, T vertex2) {
        int v1Pos = ArrayUtils.indexOf(vertices, vertex1);
        int v2Pos = ArrayUtils.indexOf(vertices, vertex2);

        // se o indexof não encontra o valor é -1
        if (v1Pos == -1 || v2Pos == -1) {
            throw new IllegalArgumentException("vertex not found");
        }

        if (this.adjMatrix[v1Pos][v2Pos] == true) {
            this.adjMatrix[v1Pos][v2Pos] = false;
            this.adjMatrix[v2Pos][v1Pos] = false;
        } else {
            throw new IllegalArgumentException("edge não encontrada");
        }
    }

    @Override
    public void addVertex(T vertex) {

        try {
            if (vertices.length == numVertices) {
                expandCapacity();
            }
            if (contains(vertices, vertex)) {
                throw new IllegalArgumentException("vertex já existe");
            }
            this.vertices[numVertices] = (T) vertex;
            this.numVertices++;
        } catch (UnsupportedDataTypeException ex) {
            Logger.getLogger(Graph.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void expandCapacity() throws UnsupportedDataTypeException {
        int newLength = this.vertices.length + 1;

        T[] expandArray = (T[]) (new Object[(newLength)]);
        boolean[][] expandMatrix = new boolean[newLength][newLength];
        double[][] expandWMatrix = new double[newLength][newLength];


        for (int i = 0; i < this.vertices.length; i++) {
            expandArray[i] = this.vertices[i];
        }

        for (int i = 0; i < this.vertices.length; i++) {
            for (int j = 0; j < this.vertices.length; j++) {
                expandWMatrix[i][j] = this.weightMatrix[i][j];
            }
        }

        for (int i = 0; i < this.vertices.length; i++) {
            for (int j = 0; j < this.vertices.length; j++) {
                expandMatrix[i][j] = this.adjMatrix[i][j];
            }
            this.vertices = expandArray;
            this.adjMatrix = expandMatrix;
            this.weightMatrix = expandWMatrix;
        }
    }

    @Override
    public void addEdge(T vertex1, T vertex2) throws UnsupportedDataTypeException {
        int v1Pos = ArrayUtils.indexOf(vertices, vertex1);
        int v2Pos = ArrayUtils.indexOf(vertices, vertex2);

        // se o indexof não encontra o valor é -1
        if (v1Pos == -1 || v2Pos == -1) {
            throw new IllegalArgumentException("vertex not found");
        }
        if (vertices.length == numVertices) {
            expandCapacity();
        }

        if (this.adjMatrix[v1Pos][v2Pos] == false) {
            this.adjMatrix[v1Pos][v2Pos] = true;
            this.adjMatrix[v2Pos][v1Pos] = true;
        } else {
            throw new IllegalArgumentException("edge já existe");
        }
    }

    @Override
    public void removeVertex(T vertex) {
        int pos = ArrayUtils.indexOf(vertices, vertex);
        if (pos == -1) {
            throw new IllegalArgumentException("vertex not found");
        }

        this.numVertices--;
        this.vertices[pos] = null;

        for (int i = 0; i < vertices.length; i++) {
            if (this.adjMatrix[pos][i] == true) { // row check
                this.adjMatrix[pos][i] = false;
            }
            if (this.adjMatrix[i][pos] == true) { // column check
                this.adjMatrix[i][pos] = false;
            }
        }
    }

    @Override
    public Iterator iteratorBFS(T startVertex) {
        int startIndex = ArrayUtils.indexOf(vertices, startVertex);
        Integer x;
        LinkedQueue<Integer> traversalQueue = new LinkedQueue<>();
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<>();

        if (startIndex == -1) {
            throw new IllegalArgumentException("vertex not found");
        }

        boolean[] visited = new boolean[this.numVertices];
        for (int i = 0; i < this.numVertices; i++) {
            visited[i] = false;
        }

        traversalQueue.enqueue(startIndex);
        visited[startIndex] = true;

        while (!traversalQueue.isEmpty()) {
            try {
                x = traversalQueue.dequeue();
                try {
                    resultList.addToRear(this.vertices[x]);
                } catch (UnsupportedDataTypeException ex) {
                    Logger.getLogger(Graph.class.getName()).log(Level.SEVERE, null, ex);
                }

                for (int i = 0; i < this.numVertices; i++) {
                    if (this.adjMatrix[x][i] && !visited[i]) {
                        traversalQueue.enqueue(i);
                        visited[i] = true;
                    }
                }
            } catch (EmptyCollectionException ex) {
                Logger.getLogger(Graph.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return resultList.iterator();

    }

    @Override
    public Iterator iteratorDFS(T startVertex) {

        int startIndex = ArrayUtils.indexOf(vertices, startVertex);
        Integer x;
        boolean found;
        LinkedStack<Integer> traversalStack = new LinkedStack<Integer>();
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<T>();
        boolean[] visited = new boolean[this.numVertices];
        try {
            if (startIndex == -1) {
                throw new IllegalArgumentException("vertex not found");
            }

            for (int i = 0; i < this.numVertices; i++) {
                visited[i] = false;
            }

            traversalStack.push(startIndex);
            resultList.addToRear(this.vertices[startIndex]);
            visited[startIndex] = true;

            while (!traversalStack.isEmpty()) {
                x = traversalStack.peek();
                found = false;

                for (int i = 0; (i < this.numVertices) && !found; i++) {
                    if (this.adjMatrix[x][i] && !visited[i]) {
                        traversalStack.push(i);
                        resultList.addToRear(this.vertices[i]);
                        visited[i] = true;
                        found = true;
                    }
                }

                if (!found && !traversalStack.isEmpty()) {
                    traversalStack.pop();
                }
            }

        } catch (UnsupportedDataTypeException ex) {
            Logger.getLogger(Graph.class.getName()).log(Level.SEVERE, null, ex);
        } catch (EmptyCollectionException ex) {
            Logger.getLogger(Graph.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultList.iterator();
    }

    @Override
    public Iterator iteratorShortestPath(T startVertex, T targetVertex) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    protected boolean validateIndex(int pos){
        if (pos >= 0 && pos < numVertices){
            return true;
        }
        return false;
    }
 
    @Override
    public boolean isConnected() {
        if (isEmpty()) {
            return false;
        }

        Iterator<T> it = iteratorDFS(vertices[0]);
        int count = 0;

        while (it.hasNext()) {
            it.next();
            count++;
        }

        return count == this.numVertices;
    }

    protected int getPosition(T vertex){
        int pos = -1;
        int atual = 0;
        boolean found = false;

        while (!found && atual < numVertices) {
            if (vertex.equals(vertices[atual])){
                found = true;
                pos = atual;
                break;
            }
            atual++;
        }
        return pos;
    }

    @Override
    public int size() {
        return this.numVertices;
    }

}
