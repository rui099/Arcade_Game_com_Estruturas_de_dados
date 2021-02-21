/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Estruturas.Lists.ArrayUnorderedList;

import Estruturas.Interfaces.UnorderedListADT;
import Exceptions.ElementNotFoundException;
import org.apache.commons.lang3.ArrayUtils;

import javax.activation.UnsupportedDataTypeException;

/**
 *
 * @author ruidu
 * @param <T>
 */
public class ArrayUnorderedList<T> extends ArrayList<T> implements UnorderedListADT<T> {

    public ArrayUnorderedList() {
        super();
    }

    @Override
    public void addToFront(T element) throws UnsupportedDataTypeException {
        if (this.rear == this.array.length) {
            this.expandCapacity();
        }

        int i = this.rear;

        while (i > 0) {
            this.array[i] = this.array[i - 1];
            i--;
        }

        this.array[0] = element;

        this.rear++;

        this.modCount++;
    }
    
 

    @Override
    public void addToRear(T element) throws UnsupportedDataTypeException {
        if (this.rear == this.array.length) {
            this.expandCapacity();
        }

        this.array[this.rear] = element;

        this.rear++;

        this.modCount++;
    }

    @Override
    public void addAfter(T element, T target) throws UnsupportedDataTypeException, ElementNotFoundException {
        if (this.rear == this.array.length) {
            this.expandCapacity();
        }

        int i = 0;
        int afterTarget = 1;

        while (i < this.rear && !(target.equals(this.array[i]))) {
            i++;
            afterTarget++;
        }

        if (i != this.rear) {
            int j = this.rear;

            while (j > afterTarget) {
                j--;
                this.array[j + 1] = this.array[j];
            }

            this.array[afterTarget] = element;

            this.rear++;

            this.modCount++;
        } else {
            throw new ElementNotFoundException();
        }

    }

    private void expandCapacity() throws UnsupportedDataTypeException {
        T[] expandStack = (T[]) (new Object[(this.array.length * 2)]);

        for (int i = 0; i < this.array.length; i++) {
            expandStack[i] = this.array[i];
        }

        this.array = expandStack;
    }

    public int vertexIndex( T vertex){
        int pos = ArrayUtils.indexOf(array, vertex);
        if (pos == -1 ) {
            throw new IllegalArgumentException("vertex not found");
        }
        return pos;
    }


}
