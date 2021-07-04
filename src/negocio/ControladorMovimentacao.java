package negocio;

import dados.Repositorio;
import dados.RepositorioCRUD;
import exceptions.PessoaInexistenteException;
import negocio.beans.Bens;
import negocio.beans.Movimentacao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class ControladorMovimentacao {
    private Repositorio<Movimentacao> repoMovimentacao;

    public ControladorMovimentacao() {
        this.repoMovimentacao = new RepositorioCRUD<>();
    }

    public Map<LocalDateTime, Movimentacao> listarMoveCliente(long uidCliente) throws PessoaInexistenteException  {
        NavigableMap<LocalDateTime, Movimentacao> mapaMovimentacaoCliente = new TreeMap<>();
        boolean moveClienteExiste = false;
        List<Movimentacao> moveList = repoMovimentacao.listar();

        for( Movimentacao move : moveList){

            if(move.getCliente().getUid() == uidCliente ){
                moveClienteExiste = true;
                    mapaMovimentacaoCliente.put(move.getInstante(), move);
            }
        }if(!moveClienteExiste)  throw new PessoaInexistenteException ("Cliente Não existe!");


        return mapaMovimentacaoCliente;
    }

    public List<Movimentacao> listarPeriodoMovimentacaoCliente(long uidCliente, LocalDate dataInicial, LocalDate dataFinal) {
        List<Movimentacao> movimentacaoList = new ArrayList<>();
        List<Movimentacao> mList = new ArrayList<>(this.repoMovimentacao.listar());
        
        LocalDate dataPagamento = null;
        long prazo = ChronoUnit.DAYS.between(dataInicial, dataFinal);
        
        for(Movimentacao movimentacao : mList){
        	dataPagamento = movimentacao.getInstante().toLocalDate();
            long pagamento = ChronoUnit.DAYS.between(dataInicial, dataPagamento);
            if(movimentacao.getCliente().getUid() == uidCliente && pagamento > prazo) movimentacaoList.add(movimentacao);
            }
        return movimentacaoList;
    }

    public List<Movimentacao> listarDiasMovimentacaoCliente(long uidCliente, int dias) {
        List<Movimentacao> movimentacaoList = new ArrayList<>();

        return movimentacaoList;
    }
}
