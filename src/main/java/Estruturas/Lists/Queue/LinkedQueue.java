/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Estruturas.Lists.Queue;

import Estruturas.Interfaces.QueueADT;
import Lists.Queue.LinearNode;
import Exceptions.EmptyCollectionException;


/**
 *
 * @author ruidu
 * @param <T>
 */
public class LinkedQueue<T> implements QueueADT<T> {

    private LinearNode<T> front;
    private LinearNode<T> rear;
    private int count;

    public LinkedQueue() {
        this.count = 0;
        this.front = this.rear = null;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public void enqueue(T data) {
        LinearNode<T> temp;

        if (isEmpty()) {
            rear = front = new LinearNode<>(data);
            count++;
        } else {
            temp = new LinearNode<>(data);
            rear.setNext(temp);
            rear = temp;
            count++;
        }
    }

    @Override
    public T first() {
        if (isEmpty()) {
            throw new RuntimeException("empty list");
        }

        return front.getElement();
    }

    @Override
    public T dequeue() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("Queue");
        }

        T element = this.front.getElement();
        this.front = this.front.getNext();
        this.count--;

        return element;
    }

    @Override
    public String toString() {
        LinearNode<T> c = this.front;
        String str = "";

        for (int i = 0; i < this.count; i++) {
            str = str + c.getElement() + " ";
            c = c.getNext();
        }

        return str;
    }
}
