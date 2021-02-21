package Classes;

import Estruturas.Lists.ArrayOrderedList.ArrayOrderedList;
import Exceptions.EmptyCollectionException;

import javax.activation.UnsupportedDataTypeException;

public class Missao {
    private String cod_missao;
    private ArrayOrderedList<Map> mapasDaMissao;

    public Missao(String cod_missao) {
        this.cod_missao = cod_missao;
        this.mapasDaMissao = new ArrayOrderedList<>();
    }

    public String getCod_missao() {
        return cod_missao;
    }

    public void setCod_missao(String cod_missao) {
        this.cod_missao = cod_missao;
    }

    public ArrayOrderedList<Map> getMapasDaMissao() {
        return mapasDaMissao;
    }

    public void setMapasDaMissao(ArrayOrderedList<Map> mapasDaMissao) {
        this.mapasDaMissao = mapasDaMissao;
    }

    public void addMapa(Map map) throws UnsupportedDataTypeException {
        mapasDaMissao.add(map);
    }

    public void removeMapa(Map map) throws EmptyCollectionException {
        mapasDaMissao.remove(map);
    }
}
