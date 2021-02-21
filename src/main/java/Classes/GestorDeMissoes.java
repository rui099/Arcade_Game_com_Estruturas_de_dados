package Classes;

import Estruturas.Lists.ArrayUnorderedList.ArrayUnorderedList;
import Exceptions.EmptyCollectionException;
import Exceptions.InvalidMapException;

import javax.activation.UnsupportedDataTypeException;
import java.io.FileNotFoundException;
import java.util.Iterator;

public class GestorDeMissoes {
    private ArrayUnorderedList<Missao> missoes;

    public GestorDeMissoes() {
        this.missoes = new ArrayUnorderedList<>();
    }

    public ArrayUnorderedList<Missao> getMissoes() {
        return missoes;
    }

    public void setMissoes(ArrayUnorderedList<Missao> missoes) {
        this.missoes = missoes;
    }

    public void addMissao(Missao missao) throws UnsupportedDataTypeException {
    missoes.addToFront(missao);
    }

    public void removeMissao(Missao missao) throws EmptyCollectionException {
        missoes.remove(missao);
    }

    public Missao getMissaoByCod(String cod){

        boolean found = false;
        Missao missao = null;
        Iterator<Missao> itr = missoes.iterator();
        while (!found && itr.hasNext()) {
            Missao tmp = itr.next();
            if (tmp.getCod_missao().equalsIgnoreCase(cod)) {
                missao = tmp;
                found = true;
            }
        }

        if (found == false) {
            System.out.println("ERRO Divisao não encontrada");
        }
        return missao;
    }

    public void organizarMissoes() throws FileNotFoundException, UnsupportedDataTypeException, InvalidMapException {
        ArrayUnorderedList<String> nomeDasMissoes = new ArrayUnorderedList<>();
        JsonImporter jsonImporter = new JsonImporter();
        Iterator it = jsonImporter.importMaps();
        while (it.hasNext()) {
            Map obj = (Map) it.next();
            if(!nomeDasMissoes.contains(obj.getCod_missao())){
                addMissao(new Missao(obj.getCod_missao()));
                nomeDasMissoes.addToFront(obj.getCod_missao());
                getMissaoByCod(obj.getCod_missao()).addMapa(obj);
            }else{
                getMissaoByCod(obj.getCod_missao()).addMapa(obj);
            }
        }
    }


}
