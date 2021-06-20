package dados;

import exceptions.ObjetoDuplicadoException;
import exceptions.ObjetoInexistenteException;

import java.util.List;

public interface Repositorio<T> {

    /**
     * Método de inserção de objetos genéricos na lista do repositório CRUD.
     *
     * @param obj se refere ao objeto a ser inserido na lista do repositório.
     * @throws ObjetoDuplicadoException poderá acontecer caso haja algum objeto
     * duplicado.
     */
    public void inserir(T obj) throws ObjetoDuplicadoException;

    /**
     * Método que retorna uma lista dos objetos do repositório CRUD
     * @return uma List de objetos de tipo genérico
     */
    public List<T> listar();

    public void atualizar(T objAntigo,T objNovo) throws ObjetoInexistenteException;

    /**
     * Método que remove um objeto genérico da lista dos objetos do repositório CRUD.
     *
     * @param obj se refere ao objeto a ser removido na lista do repositório.
     * @throws ObjetoInexistenteException poderá acontecer se objeto requisitado não
     * existir dentro da lista do repositório.
     */
    public void remover(T obj) throws ObjetoInexistenteException;
}
