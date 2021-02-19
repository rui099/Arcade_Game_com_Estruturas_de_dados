/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Estruturas.Lists.Stack;

import Estruturas.Interfaces.StackADT;
import Exceptions.EmptyCollectionException;
import Lists.Stack.LinearNode;

/**
 *
 * @author ruidu
 * @param <T>
 */
public class LinkedStack<T> implements StackADT<T> {
   

    private LinearNode<T> top;
    private int count;

    @Override
    public boolean isEmpty() {
        return count == 0;
    }
    
    @Override
    public int size(){
        return count;
    }

    @Override
    public T pop() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Stack vazio");
        }
        LinearNode<T> temp = top;
        top = top.getNext();
        count--;
        return temp.getElement();
    }

    @Override
    public void push(T element){
        LinearNode<T> temp = new LinearNode<>(element);
        
        if (isEmpty()) {
            top = temp;
        }else{
        temp.setNext(top);
        top = temp;
        }count++;
    }
    
    @Override
    public String toString(){
        LinearNode<T> temp = top;
        StringBuilder elemento = new StringBuilder();
        
        while(temp != null){
            elemento.append(temp.getElement());
            elemento.append(" > ");
            temp=temp.getNext();
        }
        return elemento.toString();
    }
    
    
    @Override
    public T peek(){
        return top.getElement();
    }

}
