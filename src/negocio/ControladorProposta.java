package negocio;

import dados.Repositorio;
import dados.RepositorioCRUD;
import exceptions.ObjetoDuplicadoException;
import exceptions.PessoaInexistenteException;
import negocio.beans.Proposta;

import java.time.LocalDate;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class ControladorProposta {
    private Repositorio<Proposta> repoProposta;

    public ControladorProposta() {
        this.repoProposta = new RepositorioCRUD<>();
    }

    public void criarProposta(Proposta p) throws ObjetoDuplicadoException {

    }

    public String propostaEmDetalhe() {
        Proposta proposta = null;

        return proposta.toString();
    }

    /**
     * Método que lista as propostas do cliente ordenadas por sua data de criação por meio de um {@code Map} criado para armazenar 
     * objetos do tipo {@code Proposta} a partir do seu atributo do tipo {@code Cliente} e ordená-los a partir do seu atributo 
     * {@code data}.
     * 
     * @param uidCliente se refere ao identificador único e exclusivo do cliente que se vai alterar o cadastro.
     * @throws PessoaInexistenteException poderá acontecer caso o {@code uidCliente} não esteja atribuído a nenhum
     * cliente.
     */
    public Map<LocalDate, Proposta> listarPropostasCliente(long uidCliente) throws PessoaInexistenteException {
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
            throw new PessoaInexistenteException("Cliente não existe!");
        }

        return mapaPropostas;
    }
    
    /**
     * Método que lista as contra propostas realizadas ao cliente ordenadas por sua data de criação por meio de um {@code Map} 
     * criado para armazenar objetos do tipo {@code Proposta} que tenham o atributo {@code contraProposta} true a partir 
     * do seu atributo do tipo {@code Cliente} e ordená-los a partir do seu atributo {@code data}.
     * 
     * @param uidCliente se refere ao identificador único e exclusivo do cliente que se vai alterar o cadastro.
     * @throws PessoaInexistenteException poderá acontecer caso o {@code uidCliente} não esteja atribuído a nenhum
     * cliente.
     */
    public Map<LocalDate, Proposta> listarContraPropostas(long uidCliente) throws PessoaInexistenteException {
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
            throw new PessoaInexistenteException("Cliente não existe!");
        }

        return mapaPropostas;
    }

    /**
     * Método que lista as contra propostas realizadas ao cliente ordenadas por sua data de criação por meio de um {@code Map} 
     * criado para armazenar objetos do tipo {@code Proposta} que tenham o atributo {@code contraProposta} false a partir 
     * do seu atributo do tipo {@code Cliente} e ordená-los a partir do seu atributo{@code data}.
     * 
     * @throws PessoaInexistenteException poderá acontecer caso o {@code uidCliente} não esteja atribuído a nenhum
     * cliente.
     */
    public Map<LocalDate, Proposta> listarPropostasPendentes() throws PessoaInexistenteException {
        NavigableMap<LocalDate, Proposta> mapaPropostas = new TreeMap<>();
        
        for (Proposta proposta : this.repoProposta.listar()) {
            if(!proposta.isContraproposta()){
                //Preencher mapa
                mapaPropostas.put(proposta.getData(), proposta);
            }
        }

        return mapaPropostas;
    }
}
