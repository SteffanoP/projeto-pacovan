package negocio;

import dados.Repositorio;
import dados.RepositorioCRUD;
import exceptions.ClienteInexistenteException;
import exceptions.ObjetoDuplicadoException;
import jdk.vm.ci.meta.Local;
import negocio.beans.Cliente;
import negocio.beans.Proposta;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class ControladorProposta {
    private Repositorio<Proposta> repoProposta;

    public ControladorProposta() {
        this.repoProposta = new RepositorioCRUD<>();
    }

    public void criarProposta(Proposta p) throws ObjetoDuplicadoException {
        try {
            this.repoProposta.inserir(p);
        } catch (ObjetoDuplicadoException e) {
            throw new ObjetoDuplicadoException(e);
        }
    }

    public String propostaEmDetalhe() {
        Proposta proposta = null;

        return proposta.toString();
    }

    /**
     * Método que lista as propostas do cliente ordenadas por sua data de criação por meio de um {@code Map} criado para armazenar 
     * objetos do tipo {@code Proposta} a partir do seu atributo do tipo {@code Cliente} e ordená-los a partir do seu atributo 
     * do tipo {@code data}.
     * 
     * @param uidCliente se refere ao identificador único e exclusivo do cliente que se vai alterar o cadastro.
     * @throws ClienteInexistenteException poderá acontecer caso o {@code uidCliente} não esteja atribuído a nenhum
     * cliente.
     */
    public Map<LocalDate, Proposta> listarPropostasCliente(long uidCliente) throws ClienteInexistenteException {
        NavigableMap<LocalDate, Proposta> mapaPropostas = new TreeMap<>();
        boolean clienteExiste = false;
        
        for (Proposta proposta : this.repoProposta.listar()) {
            if(proposta.getCliente().getUid() == uidCliente && !proposta.isContraproposta()){
                clienteExiste = true;
                //Preencher mapa
                mapaPropostas.put(proposta.getData(), proposta);
            }
        }
        
        if (!clienteExiste) {
            throw new ClienteInexistenteException("Cliente não existe!");
        }

        return mapaPropostas;
    }
    /**
     * Método que lista as contra propostas realizadas ao cliente ordenadas por sua data de criação por meio de um {@code Map} 
     * criado para armazenar objetos do tipo {@code Proposta} que tenham o atributo {@code contraProposta} true a partir 
     * do seu atributo do tipo {@code Cliente} e ordená-los a partir do seu atributo do tipo {@code data}.
     * 
     * @param uidCliente se refere ao identificador único e exclusivo do cliente que se vai alterar o cadastro.
     * @throws ClienteInexistenteException poderá acontecer caso o {@code uidCliente} não esteja atribuído a nenhum
     * cliente.
     */
    public Map<LocalDate, Proposta> listarContraPropostas(long uidCliente) throws ClienteInexistenteException {
        NavigableMap<LocalDate, Proposta> mapaPropostas = new TreeMap<>();
        boolean clienteExiste = false;
        
        for (Proposta proposta : this.repoProposta.listar()) {
            if(proposta.getCliente().getUid() == uidCliente && proposta.isContraproposta()){
                clienteExiste = true;
                //Preencher mapa
                mapaPropostas.put(proposta.getData(), proposta);
            }
        }
        
        if (!clienteExiste) {
            throw new ClienteInexistenteException("Cliente não existe!");
        }

        return mapaPropostas;
    }

    /**
     * Método que lista as contra propostas realizadas ao cliente ordenadas por sua data de criação por meio de um {@code Map} 
     * criado para armazenar objetos do tipo {@code Proposta} que tenham o atributo {@code contraProposta} false a partir 
     * do seu atributo do tipo {@code Cliente} e ordená-los a partir do seu atributo do tipo {@code data}.
     * 
     * @param uidCliente se refere ao identificador único e exclusivo do cliente que se vai alterar o cadastro.
     * @throws ClienteInexistenteException poderá acontecer caso o {@code uidCliente} não esteja atribuído a nenhum
     * cliente.
     */
    public Map<LocalDate, Proposta> listarPropostasPendentes() throws ClienteInexistenteException {
        NavigableMap<LocalDate, Proposta> mapaPropostas = new TreeMap<>();
        boolean clienteExiste = false;
        
        for (Proposta proposta : this.repoProposta.listar()) {
            if(!proposta.isContraproposta()){
                clienteExiste = true;
                //Preencher mapa
                mapaPropostas.put(proposta.getData(), proposta);
            }
        }
        
        if (!clienteExiste) {
            throw new ClienteInexistenteException("Cliente não existe!");
        }

        return mapaPropostas;
    }
}
