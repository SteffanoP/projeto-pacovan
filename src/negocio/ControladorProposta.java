package negocio;

import dados.Repositorio;
import dados.RepositorioCRUD;
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

    public Map<LocalDate, Proposta> listarPropostasCliente(long uidCliente) {
        NavigableMap<LocalDate, Proposta> mapaPropostas = new TreeMap<>();
        
        for (Proposta proposta : this.repoProposta.listar()) {
            if(proposta.getCliente().getUid() == uidCliente){
                //Preencher mapa
                mapaPropostas.put(proposta.getData(), proposta);
            }
        }
        return mapaPropostas;
    }

    public List<Proposta> listarContraPropostas(long uidCliente) {
        List<Proposta> clienteContraPropostas = new ArrayList<>();

        return clienteContraPropostas;
    }

    public List<Proposta> listarPropostasPendentes() {
        List<Proposta> propostasPendentes = new ArrayList<>();

        return propostasPendentes;
    }
}
