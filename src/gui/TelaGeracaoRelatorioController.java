package gui;

import exceptions.PessoaInexistenteException;
import gerenciamento.SessionManager;
import io.GeradorRelatoriosMarkdown;
import io.GeradorRelatorios;
import io.models.RelatorioCliente;
import io.models.RelatorioEmpregado;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import negocio.Fachada;
import negocio.beans.*;

import java.util.ArrayList;
import java.util.List;

public class TelaGeracaoRelatorioController {
    @FXML Label lblNomeEmpregado;
    @FXML Label lblNomeCliente;

    @FXML TextField txtUidCliente;
    @FXML TextField txtUidEmpregado;
    @FXML CheckBox cbxInfoPessoaisEmpregado;
    @FXML CheckBox cbxInfoPessoaisCliente;
    @FXML CheckBox cbxDevedores;
    @FXML CheckBox cbxReputacao;
    @FXML CheckBox cbxComissoes;
    @FXML CheckBox cbxLista;
    @FXML CheckBox cbxPropostas;
    @FXML CheckBox cbxExtrato;
    @FXML CheckBox cbxScore;
    @FXML CheckBox cbxBENS;

    @FXML Button btnLimparCliente;
    @FXML Button btnLimparEmpregado;
    @FXML Button btnGerarCliente;
    @FXML Button btnGerarEmpregado;
    @FXML Button btnVoltar;

    @FXML
    public void btnLimparEmpregadoPressed() {
        this.txtUidEmpregado.setText("");
        this.cbxInfoPessoaisEmpregado.selectedProperty().set(false);
        this.cbxDevedores.selectedProperty().set(false);
        this.cbxReputacao.selectedProperty().set(false);
        this.cbxComissoes.selectedProperty().set(false);
        this.cbxLista.selectedProperty().set(false);
    }

    @FXML
    public void btnGerarEmpregadoPressed() {
        try {
            Pessoa empregado = Fachada.getInstance().buscarPessoa(Long.parseLong(txtUidEmpregado.getText()));
            if (empregado instanceof Empregado) {
                //TODO: Substituir Maps pelas Lists
                List<Emprestimo> emprestimos = new
                        ArrayList<>(Fachada.getInstance().listarComissoesEmprestimo((Empregado) empregado));
                List<Emprestimo> emprestimosProtegidos = new
                        ArrayList<>(Fachada.getInstance().listarDevedoresProtegidos().values());
                List<Emprestimo> emprestimosAltoRisco = new
                        ArrayList<>(Fachada.getInstance().listarDevedoresAltoRisco().values());

                RelatorioEmpregado relatorio = new RelatorioEmpregado((Empregado) empregado, emprestimos,
                        emprestimosProtegidos, emprestimosAltoRisco);
                GeradorRelatorios geradorRelatorios = new
                        GeradorRelatoriosMarkdown(relatorio, this.verificarCheckBoxEmpregado());
                geradorRelatorios.gerarRelatorioEmpregado(null);
            } else {
                this.gerarAlertaErroPessoa("Essa pessoa não é um empregado!");
            }
        } catch (PessoaInexistenteException | NumberFormatException e) {
            this.gerarAlertaErroPessoa("Parece que essa pessoa não existe");
        }

    }

    @FXML
    public void btnLimparClientePressed() {
        this.txtUidCliente.setText("");
        this.cbxInfoPessoaisCliente.selectedProperty().set(false);
        this.cbxPropostas.selectedProperty().set(false);
        this.cbxExtrato.selectedProperty().set(false);
        this.cbxScore.selectedProperty().set(false);
        this.cbxBENS.selectedProperty().set(false);
    }

    @FXML
    public void btnGerarClientePressed() {
        try {
            Pessoa cliente = Fachada.getInstance().buscarPessoa(Long.parseLong(txtUidCliente.getText()));
            if (cliente instanceof Cliente) {
                //TODO: Substituir Maps pelas Lists
                List<Proposta> propostas = new
                        ArrayList<>(Fachada.getInstance().listarPropostasCliente(cliente.getUid()).values());
                List<Movimentacao> movimentacoes = new
                        ArrayList<>(Fachada.getInstance().listarMoveCliente(cliente.getUid()).values());
                List<Bens> bens = new
                        ArrayList<>(Fachada.getInstance().listarBensCliente(cliente.getUid()).values());

                RelatorioCliente relatorio = new RelatorioCliente((Cliente) cliente, propostas,movimentacoes,bens);
                GeradorRelatorios geradorRelatorios = new
                        GeradorRelatoriosMarkdown(relatorio,this.verificarCheckBoxCliente());
                geradorRelatorios.gerarRelatorioCliente(null); //TODO: Fazer um método de selecionar o diretório para salvar o relatório
            } else {
                this.gerarAlertaErroPessoa("Essa pessoa não é um Cliente!");
            }
        } catch (PessoaInexistenteException | NumberFormatException e) {
            this.gerarAlertaErroPessoa("Parece que essa pessoa não existe");
        }
    }

    private boolean[] verificarCheckBoxEmpregado() {
        boolean[] selecao = new boolean[5];
        selecao[0] = this.cbxInfoPessoaisEmpregado.isSelected();
        selecao[1] = this.cbxDevedores.isSelected();
        selecao[2] = this.cbxReputacao.isSelected();
        selecao[3] = this.cbxComissoes.isSelected();
        selecao[4] = this.cbxLista.isSelected();
        return selecao;
    }

    private boolean[] verificarCheckBoxCliente() {
        boolean[] selecao = new boolean[5];
        selecao[0] = this.cbxInfoPessoaisCliente.isSelected();
        selecao[1] = this.cbxPropostas.isSelected();
        selecao[2] = this.cbxExtrato.isSelected();
        selecao[3] = this.cbxScore.isSelected();
        selecao[4] = this.cbxBENS.isSelected();
        return selecao;
    }

    @FXML
    public void btnVoltarPressed() {
        Empregado empregado = null;
        if(SessionManager.getInstance().getPessoaSessao() instanceof Empregado)
            empregado = (Empregado)SessionManager.getInstance().getPessoaSessao();
        if(empregado != null && empregado.getPrivilegio() == 5)
            GerenciadorTelas.getInstance().changeScreen("telaAdmin");
        else if (empregado != null && (empregado.getPrivilegio() >= 1 && empregado.getPrivilegio() <= 4))
            GerenciadorTelas.getInstance().changeScreen("telaEmpregado");
    }

    private void gerarAlertaErroPessoa(String justificativa) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Erro");
        alerta.setHeaderText("Não conseguimos encontrar essa pessoa");
        alerta.setContentText(justificativa);
        alerta.showAndWait();
    }
}
