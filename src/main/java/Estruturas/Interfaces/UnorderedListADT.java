/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Estruturas.Interfaces;

import Exceptions.ElementNotFoundException;
import javax.activation.UnsupportedDataTypeException;

/**
 *
 * @author ruidu
 * @param <T>
 */
public interface UnorderedListADT<T> extends ListADT<T> {

    /**
     *Adds the specified element to this list at front
     *
     * @param element
     *
     * @throws javax.activation.UnsupportedDataTypeException
     */
    public void addToFront(T element) throws UnsupportedDataTypeException;
    /**
     *Adds the specified element to this list at rear
     *
     * @param element
     *
     * @throws javax.activation.UnsupportedDataTypeException
     */
    public void addToRear(T element) throws UnsupportedDataTypeException;

    /**
     *Adds the specified element after a specified element in the list
     *
     * @param element
     *
     * @throws javax.activation.UnsupportedDataTypeException
     */
    public void addAfter(T element, T target) throws UnsupportedDataTypeException, ElementNotFoundException;
}
