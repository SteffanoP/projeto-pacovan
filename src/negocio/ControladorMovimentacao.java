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

    public List<Movimentacao> listarTodasMovimentacaoCliente(long uidCliente) {
        List<Movimentacao> movimentacaoList = new ArrayList<>();

        return movimentacaoList;
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
