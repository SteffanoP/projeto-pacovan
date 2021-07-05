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

    /**
     * Método que lista as Movimenta��es do cliente ordenados por sua data por meio de um {@code Map} criado para armazenar 
     * objetos do tipo {@code Movimentacao} e ordená-los a partir do seu atributo {@code instante}.
     * 
     * @param uidCliente se refere ao identificador único e exclusivo do cliente.
     * @throws PessoaInexistenteException se o cliente n�o for encontrado.
     * @return Map de Movimentacao ordenados por data.
     */
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

    /**
     * Método que lista as Movimenta��es do cliente num per�odo espec�fico indicado por par�metro de data inicial e final. S�o
     * ordenados por sua data por meio de um {@code Map} criado para armazenar 
     * objetos do tipo {@code Movimentacao} e ordená-los a partir do seu atributo {@code instante}.
     * 
     * @param uidCliente se refere ao identificador único e exclusivo do cliente.
     * @param dataInicial � a data a partir da qual o cliente deseja ver suas movimenta��es.
     * @param dataFinal � a data limite 
     * @return Map de Movimentacao ordenados por data.
     */
    public List<Movimentacao> listarPeriodoMovimentacaoCliente(long uidCliente, LocalDate dataInicial, LocalDate dataFinal) {
        List<Movimentacao> movimentacaoList = new ArrayList<>();
        List<Movimentacao> mList = new ArrayList<>(this.repoMovimentacao.listar());
        LocalDate dataMovimentacao = null;
        
        for(Movimentacao movimentacao : mList){
        	dataMovimentacao = movimentacao.getInstante().toLocalDate();
            long move1 = ChronoUnit.DAYS.between(dataInicial, dataMovimentacao);
            long move2 = ChronoUnit.DAYS.between(dataFinal, dataMovimentacao);
            if(movimentacao.getCliente().getUid() == uidCliente && move1 >= 0 && move2 <= 0) movimentacaoList.add(movimentacao);
            }
        return movimentacaoList;
    }

    public List<Movimentacao> listarDiasMovimentacaoCliente(long uidCliente, int dias) {
        List<Movimentacao> movimentacaoList = new ArrayList<>();

        return movimentacaoList;
    }
}
