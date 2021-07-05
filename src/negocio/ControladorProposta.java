package negocio;

import dados.Repositorio;
import dados.RepositorioCRUD;
import exceptions.ObjetoDuplicadoException;
import exceptions.PessoaInexistenteException;
import exceptions.PropostaInvalidaException;
import negocio.beans.Proposta;

import java.time.LocalDate;
import java.util.*;

public class ControladorProposta {
    private Repositorio<Proposta> repoProposta;
    private static long contadorProtocolo = 1;

    public ControladorProposta() {
        this.repoProposta = new RepositorioCRUD<>();
    }

    /**
     * Método que cria e adiciona um objeto do tipo {@code Proposta}, no qual atribuí um número de procolo a proposta e
     * seta parâmetros de controle, como {@code data} e {@code contraproposta}.
     * @param p se refere a proposta inicial ao qual se deseja adicionar ao sistema.
     * @throws PropostaInvalidaException poderá acontecer caso a proposta seja inválida por alguma razão.
     */
    public void criarProposta(Proposta p) throws PropostaInvalidaException {
        if (p == null) return;

        //Set de valores iniciais
        p.setData(LocalDate.now());
        p.setContraproposta(false);
        p.setNumProtocolo(contadorProtocolo);

        try {
            this.repoProposta.inserir(p);
            contadorProtocolo++;
        } catch (ObjetoDuplicadoException e) {
            throw new PropostaInvalidaException("Parece que já existe uma proposta parecida com essa.");
        }
    }

    /**
     * Método que faz a busca de uma {@code Proposta} no repositório de propostas por meio de um número de protocolo.
     *
     * @param numProtocolo se refere ao número único dado a cada proposta, quando cada proposta é criada.
     * @return retorna uma {@code Proposta} referente ao número do Protocolo pedido do repositório
     * @throws PropostaInvalidaException poderá acontecer caso o número de protocolo seja inválido ou se a proposta
     * não existir no repositório de propostas.
     */
    public Proposta buscarProposta(long numProtocolo) throws PropostaInvalidaException {
        if (numProtocolo < 1) throw new PropostaInvalidaException("O Número de protocolo é inválido");
        Proposta proposta = null;

        List<Proposta> propostaList = new ArrayList<>(this.repoProposta.listar());
        boolean propostaEncontrada = false;
        for (int i = 0; i < propostaList.size() && !propostaEncontrada; i++) {
            proposta = propostaList.get(i);
            if (numProtocolo == proposta.getNumProtocolo()) {
                propostaEncontrada = true;
            }
        }

        if (!propostaEncontrada) throw new PropostaInvalidaException("A protocolo para essa proposta não existe!");

        return proposta;
    }

    /**
     * Método que lista as propostas do cliente ordenadas por sua data de criação por meio de um {@code Map} criado para armazenar 
     * objetos do tipo {@code Proposta} a partir do seu atributo do tipo {@code Cliente} e ordená-los a partir do seu atributo 
     * {@code data}.
     * 
     * @param uidCliente se refere ao identificador único e exclusivo do cliente.
     * @return Map de propostas ordenadas por data.
     */
    public Map<LocalDate, Proposta> listarPropostasCliente(long uidCliente) throws PessoaInexistenteException {
        NavigableMap<LocalDate, Proposta> mapaPropostas = new TreeMap<>();
        List<Proposta> propostasList = new ArrayList<>(this.repoProposta.listar()); 
        
        for (Proposta proposta : propostasList) {
            if(proposta.getCliente().getUid() == uidCliente && !proposta.isContraproposta()) {
                //Preencher mapa
                mapaPropostas.put(proposta.getData(), proposta);
            }
        }

        return mapaPropostas;
    }
    
    /**
     * Método que lista as contra propostas realizadas ao cliente ordenadas por sua data de criação por meio de um {@code Map} 
     * criado para armazenar objetos do tipo {@code Proposta} que tenham o atributo {@code contraProposta} true a partir 
     * do seu atributo do tipo {@code Cliente} e ordená-los a partir do seu atributo {@code data}.
     * 
     * @param uidCliente se refere ao identificador único e exclusivo do cliente.
     * @throws PessoaInexistenteException poderá acontecer caso o {@code uidCliente} não esteja atribuído a nenhum
     * cliente.
     * @return Map de propostas ordenadas por data.
     */
    public Map<LocalDate, Proposta> listarContraPropostas(long uidCliente) throws PessoaInexistenteException {
        NavigableMap<LocalDate, Proposta> mapaPropostas = new TreeMap<>();
        List<Proposta> propostasList = new ArrayList<>(this.repoProposta.listar());
        boolean clienteExiste = false;
        
        for (Proposta proposta : propostasList) {
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
     * @return Map de propostas ordenadas por data.
     */
    public Map<LocalDate, Proposta> listarPropostasPendentes() {
        NavigableMap<LocalDate, Proposta> mapaPropostas = new TreeMap<>();
        List<Proposta> propostasList = new ArrayList<>(this.repoProposta.listar());
        
        for (Proposta proposta : propostasList) {
            if(!proposta.isContraproposta()){
                //Preencher mapa
                mapaPropostas.put(proposta.getData(), proposta);
            }
        }

        return mapaPropostas;
    }
}
