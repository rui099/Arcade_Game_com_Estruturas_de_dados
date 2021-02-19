    package Estruturas.Lists.ArrayOrderedList;

    import Estruturas.Interfaces.OrderedListADT;
    import Estruturas.Lists.ArrayUnorderedList.ArrayList;
    import Exceptions.EmptyCollectionException;

    import javax.activation.UnsupportedDataTypeException;

    /**
     * Classe ArrayOrderedList
     * @author Rafael Costa
     */

    public class ArrayOrderedList<T> extends ArrayList<T> implements OrderedListADT<T>{

    public ArrayOrderedList() {
        super();
    }

    /**
    Método que fornecido um elemento comparável, este é adicionado à lista.
    */
    
    public void add(T element) throws UnsupportedDataTypeException {
        if (!(element instanceof Comparable)) {
            throw new UnsupportedDataTypeException();
        }
        if (size() == array.length) {
            expandTheCapacity();
        }
        Comparable<T> temp = (Comparable<T>) element;
        int i = 0;
        while (i < rear && temp.compareTo(array[i]) > 0){
            i++;
        }
        for (int j = rear; j > i; j--){
            array[j] = array[j - 1];
        }
        array[i] = element;
        rear++;
    }

    /**
     * Método que expande a capacidade da colecao para o dobro.
     */

    public void expandTheCapacity() {
        T[] novo = (T[]) (new Object[array.length * 2]);

        for (int i = 0; i < array.length; i++) {
            novo[i] = array[i];
        }
        array = novo;
    }

        public void removeAll() throws EmptyCollectionException {
            if (size() == 1) {
                this.removeFirst();
            }else{
                for (int i = 0; i < this.rear; i++) {
                    removeIndex(i);
                }
            }
            this.modCount = this.rear = 0;

        }

    /**
    Método que constrói a informação da lista em string e de seguida retorna-a.
    */
    
    public String toString(){
        String s = "";
        for (int i = 0; i < size(); i++) {
            s = s + array[i].toString();
        }
        return s;
    }
}