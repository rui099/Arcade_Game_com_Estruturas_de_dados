/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lists.Stack;

/**
 *
 * @author ruidu
 * @param <T>
 */
public class LinearNode <T> {

        private LinearNode<T> next;
        private T element;

        public LinearNode() {
            next = null;
            element = null;
        }

        public LinearNode(T element) {
            this.element = element;
            next = null;
        }

        public LinearNode<T> getNext() {
            return this.next;
        }

        public void setNext(LinearNode<T> next) {
            this.next = next;
        }

        public T getElement() {
            return element;
        }

        public void setElement(T element) {
            this.element = element;
        }
    }

