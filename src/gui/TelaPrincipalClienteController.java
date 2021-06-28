package gui;

import exceptions.PessoaInexistenteException;
import gerenciamento.SessaoUsuario;
import gui.models.EmprestimoModelo;
import gui.models.MovimentacaoModelo;
import gui.models.PropostaModelo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import negocio.Fachada;
import negocio.beans.Cliente;
import negocio.beans.Emprestimo;
import negocio.beans.Movimentacao;
import negocio.beans.Pessoa;
import negocio.beans.Proposta;
import java.util.List;

public class TelaPrincipalClienteController {
    @FXML TableView<PropostaModelo> tblvPropostas;
    @FXML TableColumn<PropostaModelo, Long> colProtocoloPropostas;
    @FXML TableColumn<PropostaModelo, String> colDataPropostas;
    @FXML TableColumn<PropostaModelo, Double> colValorDesejadoPropostas;
    @FXML TableColumn<PropostaModelo, Double> colParcelasPropostas;

    @FXML TableView<EmprestimoModelo> tblvEmprestimos;
    @FXML TableColumn<EmprestimoModelo, String> coldataPagamentoEmprestimo;
    @FXML TableColumn<EmprestimoModelo, Double> colparcelasEmprestimo;
    @FXML TableColumn<EmprestimoModelo, Double> colvalorEmprestimo;
    @FXML TableColumn<EmprestimoModelo, String> colempregadoEmprestimo;

    @FXML TableView<MovimentacaoModelo> tblvExtrato;
    @FXML TableColumn<MovimentacaoModelo, String> colInstanteExtrato;
    @FXML TableColumn<MovimentacaoModelo, String> coltipoMovimentacaoExtrato;
    @FXML TableColumn<MovimentacaoModelo, Double> colvalorExtrato;

    boolean initialized = false;

    private void initialize() {
        this.initializeTableViews();

        long uidCliente = SessaoUsuario.getInstance().getPessoaSessao().getUid();
        try {
            this.atualizarTableViewPropostas((List<Proposta>)
                    Fachada.getInstance().listarPropostasCliente(uidCliente).values());
            this.atualizarTableViewEmprestimos((List<Emprestimo>)
                    Fachada.getInstance().listarEmprestimosCliente(uidCliente).values());
            this.atualizarTableViewExtrato((List<Movimentacao>)
                    Fachada.getInstance().listarMoveCliente(uidCliente).values());
        } catch (PessoaInexistenteException e) {
            e.printStackTrace();
        }
    }

    private void initializeTableViews() {
        //Propostas
        colProtocoloPropostas.setCellValueFactory(new PropertyValueFactory<>("numProtocolo"));
        colDataPropostas.setCellValueFactory(new PropertyValueFactory<>("data"));
        colValorDesejadoPropostas.setCellValueFactory(new PropertyValueFactory<>("valorDesejado"));
        colParcelasPropostas.setCellValueFactory(new PropertyValueFactory<>("parcelasDesejadas"));

        //Empréstimos
        coldataPagamentoEmprestimo.setCellValueFactory(new PropertyValueFactory<>("dataPagamento"));
        colparcelasEmprestimo.setCellValueFactory(new PropertyValueFactory<>("parcelas"));
        colvalorEmprestimo.setCellValueFactory(new PropertyValueFactory<>("valor"));
        colempregadoEmprestimo.setCellValueFactory(new PropertyValueFactory<>("empregado"));

        //Extrato/Movimentação
        colInstanteExtrato.setCellValueFactory(new PropertyValueFactory<>("instante"));
        coltipoMovimentacaoExtrato.setCellValueFactory(new PropertyValueFactory<>("tipoMovimentacao"));
        colvalorExtrato.setCellValueFactory(new PropertyValueFactory<>("valor"));
    }

    private void atualizarTableViewPropostas(List<Proposta> propostaList) {
        //TODO: Pensar em mover essa conversão para local apropriado
        tblvPropostas.getItems().removeAll();
        for (Proposta proposta : propostaList) {
            PropostaModelo propostaModelo = new PropostaModelo(proposta.getNumProtocolo(),proposta.getData(),
                    proposta.getValorDesejado(),proposta.getParcelasDesejadas());
            tblvPropostas.getItems().add(propostaModelo);
        }
    }

    private void atualizarTableViewEmprestimos(List<Emprestimo> emprestimoList) {
        //TODO: Pensar em mover essa conversão para local apropriado
        tblvEmprestimos.getItems().removeAll();
        for (Emprestimo emprestimo : emprestimoList) {
            EmprestimoModelo emprestimoModelo = new EmprestimoModelo(emprestimo.getDataPagamento(),
                    emprestimo.getParcelas(), emprestimo.getValor(),emprestimo.getEmpregado().getNome());
            tblvEmprestimos.getItems().add(emprestimoModelo);
        }
    }

    private void atualizarTableViewExtrato(List<Movimentacao> movimentacaoList)
    {
        //TODO: Pensar em mover essa conversão para local apropriado
        tblvExtrato.getItems().removeAll();
        for (Movimentacao movimentacao : movimentacaoList) {
            MovimentacaoModelo movimentacaoModelo = new MovimentacaoModelo(movimentacao.getInstante(),
                    movimentacao.getTipoMovimentacao(), movimentacao.getValor());
            tblvExtrato.getItems().add(movimentacaoModelo);
        }
    }

    public void onMouseEntered() {
        if (!initialized) {
            initialized = true;
            this.initialize();
        }
    }
    
    @FXML
    public void btnMinhasInformacoesPressed(ActionEvent event) throws PessoaInexistenteException {
    	String emailCliente = SessaoUsuario.getInstance().getPessoaSessao().getEmail();
        Pessoa pessoa = SessaoUsuario.getInstance().getPessoaSessao();

        try {
            if (Fachada.getInstance().buscarPessoa(emailCliente) != null && pessoa instanceof Cliente) 
    		GerenciadorTelas.getInstance().changeScreen("telaInformacoesPessoais");
        } catch (PessoaInexistenteException e) {
            e.printStackTrace();
            System.out.println("Essa pessoa não existe!");
        }
    }
    
    @FXML
    public void btnMeusBENSPressed(ActionEvent event) throws PessoaInexistenteException {
    	String emailCliente = SessaoUsuario.getInstance().getPessoaSessao().getEmail();
        Pessoa pessoa = SessaoUsuario.getInstance().getPessoaSessao();

        try {
            if (Fachada.getInstance().buscarPessoa(emailCliente) != null && pessoa instanceof Cliente) 
    		GerenciadorTelas.getInstance().changeScreen("telaBENS");
        } catch (PessoaInexistenteException e) {
            e.printStackTrace();
            System.out.println("Essa pessoa não existe!");
        }
    }
    
    @FXML
    public void btnNovaPropostaPressed(ActionEvent event) throws PessoaInexistenteException {
    	String emailCliente = SessaoUsuario.getInstance().getPessoaSessao().getEmail();
        Pessoa pessoa = SessaoUsuario.getInstance().getPessoaSessao();

        try {
            if (Fachada.getInstance().buscarPessoa(emailCliente) != null && pessoa instanceof Cliente) 
    		GerenciadorTelas.getInstance().changeScreen("telaCriarProposta");
        } catch (PessoaInexistenteException e) {
            e.printStackTrace();
            System.out.println("Essa pessoa não existe!");
        }
    }
}
