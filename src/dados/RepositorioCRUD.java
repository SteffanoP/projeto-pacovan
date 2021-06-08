package dados;

import exceptions.ObjetoDuplicadoException;
import exceptions.ObjetoInexistenteException;

import java.util.Collections;
import java.util.List;

public class RepositorioCRUD<T> implements Repositorio<T> {
    protected List<T> objetos;

    /**
     * Método de inserção de objetos genéricos na lista do repositório CRUD.
     *
     * @param obj se refere ao objeto a ser inserido na lista do repositório.
     * @throws ObjetoDuplicadoException poderá acontecer caso haja algum objeto
     * duplicado.
     */
    public void inserir(T obj) throws ObjetoDuplicadoException {
        if (obj != null) {
            if (!this.objetos.contains(obj))
                this.objetos.add(obj);
            else
                throw new ObjetoDuplicadoException(obj);
        }
    }

    /**
     * Método que retorna uma lista dos objetos do repositório CRUD.
     *
     * @return uma List de objetos de tipo genérico.
     */
    public List<T> listar() {
        return Collections.unmodifiableList(this.objetos);
    }

    /**
     * TODO: Operacionalizar a operação atualizar
     * @param obj
     * @throws ObjetoInexistenteException
     */
    public void atualizar(T obj) throws ObjetoInexistenteException {
        if (obj == null) return;

        if (this.objetos.contains(obj)) {
            this.objetos.set(this.objetos.indexOf(obj),obj);
        } else {
            throw new ObjetoInexistenteException(obj);
        }
    }

    /**
     * Método que remove um objeto genérico da lista dos objetos do repositório CRUD.
     *
     * @param obj se refere ao objeto a ser removido na lista do repositório.
     * @throws ObjetoInexistenteException poderá acontecer se objeto requisitado não
     * existir dentro da lista do repositório.
     */
    public void remover(T obj) throws ObjetoInexistenteException {
        if (obj == null) return;

        if (this.objetos.contains(obj)) {
            this.objetos.remove(obj);
        } else {
            throw new ObjetoInexistenteException(obj);
        }
    }
}
