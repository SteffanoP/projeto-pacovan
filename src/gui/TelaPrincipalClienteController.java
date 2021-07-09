package gui;

import exceptions.EmprestimoInexistenteException;
import exceptions.MovimentacaoDuplicadaException;
import exceptions.PessoaInexistenteException;
import exceptions.PropostaInvalidaException;
import gerenciamento.SessionManager;
import gui.models.EmprestimoModelo;
import gui.models.MovimentacaoModelo;
import gui.models.PropostaModelo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import negocio.Fachada;
import negocio.beans.Cliente;
import negocio.beans.Emprestimo;
import negocio.beans.Movimentacao;
import negocio.beans.Pessoa;
import negocio.beans.Proposta;
import negocio.beans.TipoMovimentacao;

import java.util.ArrayList;
import java.util.List;

public class TelaPrincipalClienteController {
    @FXML Label lblNomeUsuario;
    @FXML Label lblScoreUsuario;

    @FXML TableView<PropostaModelo> tblvPropostas;
    @FXML TableColumn<PropostaModelo, Long> colProtocoloPropostas;
    @FXML TableColumn<PropostaModelo, String> colDataPropostas;
    @FXML TableColumn<PropostaModelo, Double> colValorDesejadoPropostas;
    @FXML TableColumn<PropostaModelo, Double> colParcelasPropostas;

    @FXML TableView<EmprestimoModelo> tblvEmprestimos;
    @FXML TableColumn<EmprestimoModelo, Long> colnumProtocoloEmprestimo;
    @FXML TableColumn<EmprestimoModelo, String> coldataPagamentoEmprestimo;
    @FXML TableColumn<EmprestimoModelo, Double> colparcelasEmprestimo;
    @FXML TableColumn<EmprestimoModelo, Double> colvalorEmprestimo;
    @FXML TableColumn<EmprestimoModelo, String> colempregadoEmprestimo;

    @FXML TableView<MovimentacaoModelo> tblvExtrato;
    @FXML TableColumn<MovimentacaoModelo, String> colInstanteExtrato;
    @FXML TableColumn<MovimentacaoModelo, String> coltipoMovimentacaoExtrato;
    @FXML TableColumn<MovimentacaoModelo, Double> colvalorExtrato;

    @FXML Button btnVerProposta;
    @FXML Button btnVerEmprestimo;

    @FXML
    private void initialize() {
        Cliente usuario = (Cliente) SessionManager.getInstance().getPessoaSessao();
        this.lblNomeUsuario.setText(usuario.getNome());
        this.lblScoreUsuario.setText(usuario.getScore() + "%");
        this.initializeTableViews();

        long uidCliente = SessionManager.getInstance().getPessoaSessao().getUid();
            List<Proposta> pList = new ArrayList<>(Fachada.getInstance().listarPropostasCliente(uidCliente));
        this.atualizarTableViewPropostas(Fachada.getInstance().listarPropostasCliente(uidCliente));
        this.atualizarTableViewEmprestimos(Fachada.getInstance().listarEmprestimosCliente(uidCliente));
        this.atualizarTableViewExtrato(Fachada.getInstance().listarMoveCliente(uidCliente));
    }

    private void initializeTableViews() {
        //Propostas
        colProtocoloPropostas.setCellValueFactory(new PropertyValueFactory<>("numProtocolo"));
        colDataPropostas.setCellValueFactory(new PropertyValueFactory<>("data"));
        colValorDesejadoPropostas.setCellValueFactory(new PropertyValueFactory<>("valorDesejado"));
        colParcelasPropostas.setCellValueFactory(new PropertyValueFactory<>("parcelasDesejadas"));

        //Empréstimos
        colnumProtocoloEmprestimo.setCellValueFactory(new PropertyValueFactory<>("numProtocolo"));
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
        tblvPropostas.getItems().clear();
        for (Proposta proposta : propostaList) {
            PropostaModelo propostaModelo = new PropostaModelo(proposta.getNumProtocolo(),proposta.getData(),
                    proposta.getValorDesejado(),proposta.getParcelasDesejadas());
            tblvPropostas.getItems().add(propostaModelo);
        }
    }

    private void atualizarTableViewEmprestimos(List<Emprestimo> emprestimoList) {
        //TODO: Pensar em mover essa conversão para local apropriado
        tblvEmprestimos.getItems().clear();
        for (Emprestimo emprestimo : emprestimoList) {
            EmprestimoModelo emprestimoModelo = new EmprestimoModelo(emprestimo.getNumProtocolo(),
                    emprestimo.getDataPagamento(), emprestimo.getParcelas(), emprestimo.getValor(),
                    emprestimo.getEmpregado().getNome());
            tblvEmprestimos.getItems().add(emprestimoModelo);
        }
    }

    private void atualizarTableViewExtrato(List<Movimentacao> movimentacaoList) {
        //TODO: Pensar em mover essa conversão para local apropriado
        tblvExtrato.getItems().clear();
        for (Movimentacao movimentacao : movimentacaoList) {
            MovimentacaoModelo movimentacaoModelo = new MovimentacaoModelo(movimentacao.getInstante(),
                    movimentacao.getTipoMovimentacao(), movimentacao.getValor());
            tblvExtrato.getItems().add(movimentacaoModelo);
        }
    }

    private void gerarAlertaErro(String titulo, String subtitulo, String justificativa) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Erro de " + titulo);
        alerta.setHeaderText("Parece que tivemos um erro com " + subtitulo);
        alerta.setContentText(justificativa);
        alerta.showAndWait();
    }
    
    @FXML
    public void btnMinhasInformacoesPressed(ActionEvent event) throws PessoaInexistenteException {
    	String emailCliente = SessionManager.getInstance().getPessoaSessao().getEmail();
        Pessoa pessoa = SessionManager.getInstance().getPessoaSessao();

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
    	String emailCliente = SessionManager.getInstance().getPessoaSessao().getEmail();
        Pessoa pessoa = SessionManager.getInstance().getPessoaSessao();

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
    	String emailCliente = SessionManager.getInstance().getPessoaSessao().getEmail();
        Pessoa pessoa = SessionManager.getInstance().getPessoaSessao();

        try {
            if (Fachada.getInstance().buscarPessoa(emailCliente) != null && pessoa instanceof Cliente) 
    		GerenciadorTelas.getInstance().changeScreen("telaCriarProposta");
        } catch (PessoaInexistenteException e) {
            e.printStackTrace();
            System.out.println("Essa pessoa não existe!");
        }
    }

    @FXML
    public void tblvPropostasOnMouseClicked(MouseEvent event) {
        if (tblvPropostas.getSelectionModel().getSelectedItem() != null) {
            long numProtocolo = tblvPropostas.getSelectionModel().getSelectedItem().getNumProtocolo();
            try {
                SessionManager.getInstance().setPropostaSessao(Fachada.getInstance().buscarProposta(numProtocolo));
                System.out.println(SessionManager.getInstance().getPropostaSessao().toString());
                if (SessionManager.getInstance().getPropostaSessao() != null) {
	                if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() >= 2)
	                    GerenciadorTelas.getInstance().changeScreen("telaFeedbackProposta");
                } else
                    this.gerarAlertaErro("Propostas", "sua Proposta","Parece que você não" +
                            " selecionou sua Proposta");
            } catch (PropostaInvalidaException e) {
                this.gerarAlertaErro("Propostas", "busca de propostas",e.getMessage());
            }
        }
    }

    @FXML
    public void tblvEmprestimosOnMouseClicked(MouseEvent event) {
        if (tblvEmprestimos.getSelectionModel().getSelectedItem() != null) {
            long numProtocolo = tblvEmprestimos.getSelectionModel().getSelectedItem().getNumProtocolo();
            try {
                SessionManager.getInstance().setEmprestimoSessao(Fachada.getInstance().buscarEmprestimo(numProtocolo));
                if (SessionManager.getInstance().getEmprestimoSessao() != null) {
                	if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() >= 2) {
                		GerenciadorTelas.getInstance().changeScreen("telaEmprestimoDetalhe");
                	}
                } else
                    this.gerarAlertaErro("Empréstimos", "seu Empréstimo", "Parece que você não" +
                            " selecionou seu Empréstimo");
            } catch (EmprestimoInexistenteException e) {
                this.gerarAlertaErro("Empréstimos", "busca de empréstimos", e.getMessage());
            }
        }
    }
    
    @FXML
    public void btnPagamentoDebitoPressed() {
    	if (SessionManager.getInstance().getEmprestimoSessao() != null) {
    		Movimentacao movimentacao = new Movimentacao();
    		movimentacao.setCliente(SessionManager.getInstance().getEmprestimoSessao().getCliente());
    		movimentacao.setDescricao(SessionManager.getInstance().getEmprestimoSessao().toString());
    		movimentacao.setValor(SessionManager.getInstance().getEmprestimoSessao().getParcelas());
    		movimentacao.setTipoMovimentacao(TipoMovimentacao.DEBITO);
            try {
                Fachada.getInstance().pagarEmprestimo(
                        SessionManager.getInstance().getEmprestimoSessao().getNumProtocolo(),movimentacao);
                this.atualizarTableViewExtrato(
                        Fachada.getInstance().listarMoveCliente(movimentacao.getCliente().getUid()));
                this.atualizarTableViewEmprestimos(
                        Fachada.getInstance().listarEmprestimosCliente(movimentacao.getCliente().getUid()));
            } catch (MovimentacaoDuplicadaException | EmprestimoInexistenteException e) {
                this.gerarAlertaErro("Erro de Pagamento",
                        "Parece que tivemos um erro no seu pagamento", e.getMessage());
            }
        } else
            this.gerarAlertaErro("Empréstimos", "seu Empréstimo", "Parece que você não" +
                    " selecionou seu Empréstimo");
    }

    @FXML
    public void btnPagamentoCreditoPressed() {
        if (SessionManager.getInstance().getEmprestimoSessao() != null) {
            Movimentacao movimentacao = new Movimentacao();
            movimentacao.setCliente(SessionManager.getInstance().getEmprestimoSessao().getCliente());
            movimentacao.setDescricao(SessionManager.getInstance().getEmprestimoSessao().toString());
            movimentacao.setValor(SessionManager.getInstance().getEmprestimoSessao().getParcelas());
            movimentacao.setTipoMovimentacao(TipoMovimentacao.CREDITO);
            try {
                Fachada.getInstance().pagarEmprestimo(
                        SessionManager.getInstance().getEmprestimoSessao().getNumProtocolo(),movimentacao);
                this.atualizarTableViewExtrato(
                        Fachada.getInstance().listarMoveCliente(movimentacao.getCliente().getUid()));
                this.atualizarTableViewEmprestimos(
                        Fachada.getInstance().listarEmprestimosCliente(movimentacao.getCliente().getUid()));
            } catch (MovimentacaoDuplicadaException | EmprestimoInexistenteException e) {
                this.gerarAlertaErro("Erro de Pagamento",
                        "Parece que tivemos um erro no seu pagamento", e.getMessage());
            }
        } else
            this.gerarAlertaErro("Empréstimos", "seu Empréstimo", "Parece que você não" +
                    " selecionou seu Empréstimo");
    }

    @FXML
    public void btnPagamentoBensPressed() {
        if (SessionManager.getInstance().getEmprestimoSessao() != null) {
            Movimentacao movimentacao = new Movimentacao();
            movimentacao.setCliente(SessionManager.getInstance().getEmprestimoSessao().getCliente());
            movimentacao.setDescricao(SessionManager.getInstance().getEmprestimoSessao().toString());
            movimentacao.setValor(SessionManager.getInstance().getEmprestimoSessao().getParcelas());
            movimentacao.setTipoMovimentacao(TipoMovimentacao.BENS);
            try {
                Fachada.getInstance().pagarEmprestimo(
                        SessionManager.getInstance().getEmprestimoSessao().getNumProtocolo(),movimentacao);
                this.atualizarTableViewExtrato(
                        Fachada.getInstance().listarMoveCliente(movimentacao.getCliente().getUid()));
                this.atualizarTableViewEmprestimos(
                        Fachada.getInstance().listarEmprestimosCliente(movimentacao.getCliente().getUid()));
            } catch (MovimentacaoDuplicadaException | EmprestimoInexistenteException e) {
                this.gerarAlertaErro("Erro de Pagamento",
                        "Parece que tivemos um erro no seu pagamento", e.getMessage());
            }
        } else
            this.gerarAlertaErro("Empréstimos", "seu Empréstimo", "Parece que você não" +
                    " selecionou seu Empréstimo");
    }
    
    @FXML
    public void btnSairPressed() {
    	SessionManager.getInstance().setPessoaSessao(null);
        GerenciadorTelas.getInstance().changeScreen("telaLogin");
    }
}
