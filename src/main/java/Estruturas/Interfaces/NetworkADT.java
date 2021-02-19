/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Estruturas.Interfaces;

import Exceptions.EmptyCollectionException;

import javax.activation.UnsupportedDataTypeException;

/**
 *
 * @author ruidu
 */
public interface NetworkADT<T> extends GraphADT<T> {

    /**
     *Adds a edge bettwen two vertexs with weight
     *
     * @param vertex1
     * @param weight
     * @param vertex2
     *
     * @throws javax.activation.UnsupportedDataTypeException
     */
    public void addEdge(T vertex1,int weight,T vertex2) throws UnsupportedDataTypeException;
    /**
     *Adds weight bettwen the adge between two vertexs
     *
     * @param vertex1
     * @param weight
     * @param vertex2
     *
     * @throws javax.activation.UnsupportedDataTypeException
     */
    public void setEdgeWeight(T vertex1,int weight,T vertex2) throws UnsupportedDataTypeException;
            /**
     *returns the weith of an edge
             *
     * @param vertex1
     * @param vertex2
     */
    public double getEdgeWeight(T vertex1,T vertex2);
            /**
     *
     * @param vertex1
     * @param vertex2
             * @throws javax.activation.UnsupportedDataTypeException,Exceptions.EmptyCollectionException
     */
    public double minimalPath(T vertex1,T vertex2)throws UnsupportedDataTypeException, EmptyCollectionException ;
}