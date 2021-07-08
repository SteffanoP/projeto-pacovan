package io.models;

import negocio.beans.Bens;
import negocio.beans.Cliente;
import negocio.beans.Movimentacao;
import negocio.beans.Proposta;

import java.util.List;

public class RelatorioCliente {
    private Cliente cliente;
    private List<Proposta> propostas;
    private List<Movimentacao> movimentacoes;
    private List<Bens> bens;

    public RelatorioCliente(Cliente cliente, List<Proposta> propostas, List<Movimentacao> movimentacoes,
                            List<Bens> bens) {
        if (cliente != null) {
            this.cliente = cliente;
            this.propostas = propostas;
            this.movimentacoes = movimentacoes;
            this.bens = bens;
        }
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<Proposta> getPropostas() {
        return propostas;
    }

    public List<Movimentacao> getMovimentacoes() {
        return movimentacoes;
    }

    public List<Bens> getBens() {
        return bens;
    }
}
