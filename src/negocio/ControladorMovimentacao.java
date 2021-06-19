package negocio;

import dados.Repositorio;
import dados.RepositorioCRUD;
import negocio.beans.Movimentacao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ControladorMovimentacao {
    private Repositorio<Movimentacao> repoMovimentacao;

    public ControladorMovimentacao() {
        this.repoMovimentacao = new RepositorioCRUD<>();
    }

    
    public Map<LocalDate, Movimentacao> listarMoveCliente(long uidCliente) throws ClienteInexistenteException {
        NavigableMap<LocalDate, Movimentacao> mapaMovimentacaoCliente = new TreeMap<>();
        boolean moveClienteExiste = false;
        List<Movimentacao> moveList = repoMovimentacao.listar();

        for( Movimentacao move : moveList){

            if(move.getCliente().getUid() == uidCliente ){
                moveClienteExiste = true;
                    mapaMovimentacaoCliente.put(move.getInstante().toLocalDate(), move);  //duvida
            }
        }if(!moveClienteExiste)  throw new ClienteInexistenteException("Cliente Não existe!");


        return mapaMovimentacaoCliente;
    }

    public List<Movimentacao> listarPeriodoMovimentacaoCliente(long uidCliente, LocalDate dataInicial, LocalDate dataFinal) {
        List<Movimentacao> movimentacaoList = new ArrayList<>();

        return movimentacaoList;
    }

    public List<Movimentacao> listarDiasMovimentacaoCliente(long uidCliente, int dias) {
        List<Movimentacao> movimentacaoList = new ArrayList<>();

        return movimentacaoList;
    }
}
