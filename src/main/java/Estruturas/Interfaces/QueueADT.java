/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Estruturas.Interfaces;

import Exceptions.EmptyCollectionException;

/**
 *
 * @author ruidu
 * @param <T>
 */
public interface QueueADT<T> {
    /**
     * Verifica se a queue é vazia, retorna true se for
     */
    public boolean isEmpty();
    /**
     * retorna o tamanho da queue
     */
    public int size();
    /**
     * Adds the specified element to this list at the end
     *
     * @param data the element to be added to this list
     * @throws Exceptions.EmptyCollectionException
     */
    public void enqueue(T data)throws  RuntimeException;
    /**
     * Removes the element at begining
     *
     * @throws Exceptions.EmptyCollectionException
     */
    public T dequeue()throws EmptyCollectionException;
    /**
     * returns the first element
     *
     * @throws Exceptions.EmptyCollectionException
     */
    public T first() throws EmptyCollectionException;

    @Override
    public String toString();
}
