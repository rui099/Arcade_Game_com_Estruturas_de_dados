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
public interface StackADT<T> {
    /**
     * Adds the specified element to this list at the begining
     *
     * @param element the element to be added to this list
     */
    public void push(T element);
    /**
     * Removes the element at begining
     *
     * @throws Exceptions.EmptyCollectionException
     */
    public T pop() throws  EmptyCollectionException;
    /**
     * returns the first element
     *
     * @throws Exceptions.EmptyCollectionException
     */
    public T peek()throws  EmptyCollectionException;
    /**
     * Verifica se a queue é vazia, retorna true se for
     */
    public boolean isEmpty();
    /**
     * retorna o tamanho da queue
     */
    public int size();
    
    @Override
    public String toString();
    
    
    
    
}
