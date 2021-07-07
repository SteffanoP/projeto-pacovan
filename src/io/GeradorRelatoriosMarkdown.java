package io;

import io.models.RelatorioCliente;
import io.models.RelatorioEmpregado;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class GeradorRelatoriosMarkdown implements GeradorRelatorios {
    private RelatorioCliente relatorioCliente;
    private boolean[] escolhaCamposCliente;
    private RelatorioEmpregado relatorioEmpregado;
    private boolean[] escolhaCamposEmpregado;

    //Construtor para Relatório do Cliente
    public GeradorRelatoriosMarkdown(RelatorioCliente relatorioCliente, boolean[] escolhaCamposCliente) {
        this.setRelatorioCliente(relatorioCliente);
        this.setEscolhaCamposCliente(escolhaCamposCliente);
    }

    //Construtor para Relatório do Empregado
    public GeradorRelatoriosMarkdown(RelatorioEmpregado relatorioEmpregado, boolean[] escolhaCamposEmpregado) {
        this.setRelatorioEmpregado(relatorioEmpregado);
        this.setEscolhaCamposEmpregado(escolhaCamposEmpregado);
    }

    @Override
    public void gerarRelatorioCliente(String path) {
        StringBuilder corpoArquivo = new StringBuilder();
        corpoArquivo.append(String.format("# Relatório do Cliente %s \n\n", relatorioCliente.getCliente().getNome()));
        this.preencherCamposCliente(corpoArquivo);
        try {
            if (path == null) path = "./";
            FileWriter arquivo = new FileWriter(path + "\\relatorio-cliente.md", true);
            PrintWriter gravarArquivo = new PrintWriter(arquivo);

            gravarArquivo.print(corpoArquivo);
            arquivo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void gerarRelatorioEmpregado(String path) {
        StringBuilder corpoArquivo = new StringBuilder();
        corpoArquivo.append(String.format("# Relatório do Empregado %s \n\n",
                this.relatorioEmpregado.getEmpregado().getNome()));
        this.preencherCamposEmpregado(corpoArquivo);
        try {
            if (path == null) path = "./";
            FileWriter arquivo = new FileWriter(path + "\\relatorio-empregado.md", true);
            PrintWriter gravarArquivo = new PrintWriter(arquivo);

            gravarArquivo.print(corpoArquivo);
            arquivo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void preencherCamposCliente(StringBuilder corpoArquivo) {
        if (this.escolhaCamposCliente[0]) {
            corpoArquivo.append("## Informações Pessoais\n\n");
            corpoArquivo.append("Nome do cliente: ").append(this.relatorioCliente.getCliente().getNome()).append("\n\n");
            corpoArquivo.append("Data de Nascimento: ")
                    .append(this.relatorioCliente.getCliente().getDataNascimentoToString())
                    .append("\n\n");
            corpoArquivo.append("CPF: ").append(this.relatorioCliente.getCliente().getCpf()).append("\n\n");
            corpoArquivo.append("Endereço: ").append(this.relatorioCliente.getCliente().getEndereco()).append("\n\n");
            corpoArquivo.append("Telefone: ").append(this.relatorioCliente.getCliente().getTelefone()).append("\n\n");
        }

        if (this.escolhaCamposCliente[1]) {
            corpoArquivo.append("## Propostas\n\n");
            this.relatorioCliente.getPropostas().forEach(proposta -> {
                corpoArquivo.append("### Protocolo nº ").append(proposta.getNumProtocolo()).append("\n\n");
                if (!proposta.isContraproposta()) {
                    corpoArquivo.append("Esta proposta é apenas uma proposta realizada pelo cliente e não foi " +
                            "avaliada por um empregado ou o empregado se rejeitou a avaliar a proposta do usuário.")
                            .append("\n\n");
                } else if (!proposta.isAprovado()) {
                    corpoArquivo.append("Esta é uma contraproposta realizada pelo cliente e avaliada " +
                            "por um empregado, no momento essa contraproposta não foi aprovada pelo cliente")
                            .append("\n\n");
                } else {
                    corpoArquivo.append("Esta é uma contraproposta que foi aprovada pelo cliente e revisada por" +
                            "um empregado")
                            .append("\n\n");
                }
                corpoArquivo.append("O Valor da proposta é R$ ").append(proposta.getValorDesejado()).append("\n\n");
                corpoArquivo.append("Foram apresentados os seguintes BENS como garantia:").append("\n\n");
                proposta.getGarantia().forEach(bens -> {
                    corpoArquivo.append("- ").append(bens.getNome()).append("\n");
                });
                corpoArquivo.append("\n").append("Totalizando um valor de R$ ")
                        .append(String.format("%.2f",proposta.valorTotalBENS()))
                        .append("\n\n");
            });
        }

        if (this.escolhaCamposCliente[2]) {
            corpoArquivo.append("## Extrato\n\n");
            this.relatorioCliente.getMovimentacoes().forEach(movimentacao -> {
                corpoArquivo.append("- ")
                            .append(movimentacao.getInstanteToString())
                            .append(": ")
                            .append(movimentacao.getTipoMovimentacao().toString())
                            .append(" - ")
                            .append(movimentacao.getDescricao())
                            .append("\n");
            });
            corpoArquivo.append("\n");
        }

        if (this.escolhaCamposCliente[3]) {
            corpoArquivo.append("## Score\n\n");
            corpoArquivo.append("O score do cliente é de ")
                        .append(this.relatorioCliente.getCliente().getScore())
                        .append("%. ");
            if (this.relatorioCliente.getCliente().getScore() < 15) {
                corpoArquivo.append("Este Score é considerado **péssimo**.");
            } else if (this.relatorioCliente.getCliente().getScore() < 30) {
                corpoArquivo.append("Este Score é considerado **ruim**.");
            } else if (this.relatorioCliente.getCliente().getScore() < 50) {
                corpoArquivo.append("Este Score é considerado **regular**.");
            } else if (this.relatorioCliente.getCliente().getScore() < 75) {
                corpoArquivo.append("Este Score é considerado **bom**.");
            } else if (this.relatorioCliente.getCliente().getScore() < 100) {
                corpoArquivo.append("Este Score é considerado **ótimo**.");
            } else {
                corpoArquivo.append("Este Score é considerado **inválido**.");
            }
        }
        //TODO: Campo de BENS
    }

    private void preencherCamposEmpregado(StringBuilder corpoArquivo) {
        //TODO: Campo de Informações pessoais
        //TODO: Campo de Devedores de negócio
        //TODO: Campo de Reputação de Negócio
        //TODO: Campo de comissões
        //TODO: Campo de Listas Protegidos/Alto-Risco
    }
    //Setters, caso necessário
    private void setRelatorioCliente(RelatorioCliente relatorioCliente) {
        if (relatorioCliente == null) return;
        this.relatorioCliente = relatorioCliente;
    }

    private void setEscolhaCamposCliente(boolean[] escolhaCamposCliente) {
        if (escolhaCamposCliente.length == 5) this.escolhaCamposCliente = escolhaCamposCliente;
    }

    private void setRelatorioEmpregado(RelatorioEmpregado relatorioEmpregado) {
        if (relatorioEmpregado == null) return;
        this.relatorioEmpregado = relatorioEmpregado;
    }

    private void setEscolhaCamposEmpregado(boolean[] escolhaCamposEmpregado) {
        if (escolhaCamposEmpregado.length == 5) this.escolhaCamposEmpregado = escolhaCamposEmpregado;
    }
}
