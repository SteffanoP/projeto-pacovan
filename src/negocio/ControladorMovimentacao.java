package negocio;

import dados.Repositorio;
import dados.RepositorioCRUD;
import exceptions.MovimentacaoDuplicadaException;
import exceptions.ObjetoDuplicadoException;
import negocio.beans.Movimentacao;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class ControladorMovimentacao {
    private Repositorio<Movimentacao> repoMovimentacao;

    public ControladorMovimentacao() {
        this.repoMovimentacao = new RepositorioCRUD<>();
    }

    /**
     * Método que gera a movimentação e armazena um objeto {@code Movimentacao} dentro do repositório de movimentações
     *
     * @param movimentacao se refere ao objeto {@code Movimentacao} que se deseja inserir dentro do repositório.
     * @throws MovimentacaoDuplicadaException poderá acontecer caso já exista uma movimentação igual no repositório do
     * sistema.
     */
    public void gerarMovimentacao(Movimentacao movimentacao) throws MovimentacaoDuplicadaException {
        if (movimentacao == null) return;

        //Set do instante atual
        movimentacao.setInstante(LocalDateTime.now());

        try {
            this.repoMovimentacao.inserir(movimentacao);
        } catch (ObjetoDuplicadoException e) {
            throw new MovimentacaoDuplicadaException("Essa movimentação já existe no sistema");
        }
    }

    /**
     * Método que lista as Movimentações do cliente.
     * 
     * @param uidCliente se refere ao identificador único e exclusivo do cliente.
     * @return List de Movimentacao ordenados por data.
     */
    public List<Movimentacao> listarMoveCliente(long uidCliente) {
    	return this.repoMovimentacao.listar().stream()
							                 .filter(movimentacao -> movimentacao.getCliente().getUid() == uidCliente)
							                 .collect(Collectors.toList());
    }

    /**
     * Método que lista as Movimentações do cliente num período específico indicado por parâmetro de data inicial e final.
     * 
     * @param uidCliente se refere ao identificador único e exclusivo do cliente.
     * @param dataInicial é a data a partir da qual o cliente deseja ver suas movimentações.
     * @param dataFinal é a data limite em qual o cliente deve pagar a parcela sem aumento.
     * @return List de Movimentacao.
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
