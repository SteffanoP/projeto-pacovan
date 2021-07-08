package negocio;

import exceptions.*;
import negocio.beans.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Fachada implements SistemaEmprestimosBens {
    private ControladorBENS bens;
    private ControladorEmprestimo emprestimos;
    private ControladorMovimentacao movimentacoes;
    private ControladorPessoa pessoas;
    private ControladorProposta propostas;
    
    private static SistemaEmprestimosBens instance;

    /**
     * Implementação do Construtor privado do padrão Singleton.
     */
    private Fachada() {
        this.bens = new ControladorBENS();
        this.emprestimos = new ControladorEmprestimo();
        this.movimentacoes = new ControladorMovimentacao();
        this.pessoas = new ControladorPessoa();
        this.propostas = new ControladorProposta();
    }

    /**
     * Método que implementa a instância de uma fachada.
     *
     * @return a instância fachada sendo utilizada ou uma nova instância de fachada.
     */
    public static SistemaEmprestimosBens getInstance() {
        if (instance == null) {
            instance = new Fachada();
        }
        return instance;
    }

    @Override
    public void inserirBens(Bens bens) throws BensDuplicadoException {
        this.bens.inserirBens(bens);
    }

    @Override
    public Bens buscarBensCliente(long uidCliente, String nomeBens) throws PessoaInexistenteException {
        return bens.buscarBensCliente(uidCliente,nomeBens);
    }

    @Override
    public Map<LocalDate, Bens> listarBensEmpresa() {
        return bens.listarBensEmpresa();
    }

    @Override
    public Map<LocalDate, Bens> listarBensEmpresaCategoria(CategoriaBens categoria) {
        return bens.listarBensEmpresaCategoria(categoria);
    }

    @Override
    public Map<LocalDate, Bens> listarBensCliente(long uidCliente) throws PessoaInexistenteException {
        return bens.listarBensCliente(uidCliente);
    }

    @Override
    public Map<LocalDate, Bens> listarBensPendentes(long uidCliente) throws PessoaInexistenteException {
        return bens.listarBensPendentes(uidCliente);
    }

    @Override
    public Map<LocalDate, Bens> listarBensAprovados() {
        return bens.listarBensAprovados();
    }

    @Override
    public Map<LocalDate, Bens> listarBensGarantia() {
        return bens.listarBensGarantia();
    }

    @Override
    public double calcularValorBensCliente(long uidCliente) throws PessoaInexistenteException {
        return bens.calcularValorBensCliente(uidCliente);
    }

    @Override
    public void alterarBens(Bens bensAntigo, Bens bensNovo) throws BensInexistenteException {
        bens.alterarBens(bensAntigo, bensNovo);
    }

    @Override
    public void removerBens(Bens bens) throws BensInexistenteException {
        this.bens.removerBens(bens);
    }

    @Override
    public void criarEmprestimo(Proposta proposta, Empregado empregado) throws EmprestimoDuplicadoException {
        emprestimos.criarEmprestimo(proposta, empregado);
    }

    @Override
    public Emprestimo buscarEmprestimo(long numProtocolo) throws EmprestimoInexistenteException {
        return emprestimos.buscarEmprestimo(numProtocolo);
    }

    @Override
    public String emprestimoEmDetalhe(Cliente cliente, LocalDate dataEmprestimo) throws EmprestimoInexistenteException {
        return emprestimos.emprestimoEmDetalhe(cliente, dataEmprestimo);
    }

    @Override
    public Map<LocalDate, Emprestimo> listarEmprestimosCliente(long uidCliente) {
        return emprestimos.listarEmprestimosCliente(uidCliente);
    }

    @Override
    public List<Emprestimo> listarComissoesEmprestimo(Empregado empregado) {
        return emprestimos.listarComissoesEmprestimo(empregado);
    }

    @Override
    public Map<LocalDate, Emprestimo> listarDevedores() {
        return emprestimos.listarDevedores();
    }

    @Override
    public Map<LocalDate, Emprestimo> listarDevedoresProtegidos() {
        return emprestimos.listarDevedoresProtegidos();
    }

    @Override
    public Map<LocalDate, Emprestimo> listarDevedoresAltoRisco() {
        return emprestimos.listarDevedoresAltoRisco();
    }
    
    @Override
    public double calcularValorParcelas(Emprestimo emprestimo) {
    	return emprestimos.calcularValorParcelas(emprestimo);
    }
    
    @Override
    public LocalDate calcularProximoPrazo(Emprestimo emprestimo, int parcela) {
    	return emprestimos.calcularProximoPrazo(emprestimo, parcela);
    }

    @Override
    public void gerarMovimentacao(Movimentacao movimentacao) throws MovimentacaoDuplicadaException {
        movimentacoes.gerarMovimentacao(movimentacao);
    }

    @Override
    public List<Movimentacao> listarMoveCliente(long uidCliente) {
        return movimentacoes.listarMoveCliente(uidCliente);
    }

    @Override
    public List<Movimentacao> listarPeriodoMovimentacaoCliente(long uidCliente, LocalDate dataInicial, LocalDate dataFinal) {
        return movimentacoes.listarPeriodoMovimentacaoCliente(uidCliente, dataInicial, dataFinal);
    }

    @Override
    public List<Movimentacao> listarDiasMovimentacaoCliente(long uidCliente, int dias) {
        return movimentacoes.listarDiasMovimentacaoCliente(uidCliente, dias);
    }

    @Override
    public void cadastrarPessoa(Pessoa pessoa, String senhaPessoa) throws PessoaCPFInvalidoException, PessoaDuplicadoException {
        pessoas.cadastrarPessoa(pessoa, senhaPessoa);
    }

    @Override
    public boolean autenticarPessoa(String email, String senha, boolean isEmpregado) {
        return pessoas.autenticarPessoa(email, senha, isEmpregado);
    }

    @Override
    public Pessoa buscarPessoa(String email) throws PessoaInexistenteException {
        return pessoas.buscarPessoa(email);
    }

    @Override
    public Pessoa buscarPessoa(long uidPessoa) throws PessoaInexistenteException {
        return pessoas.buscarPessoa(uidPessoa);
    }

    @Override
    public void alterarDadosPessoais(Pessoa pessoaDadosNovo) throws PessoaInexistenteException {
        pessoas.alterarDadosPessoais(pessoaDadosNovo);
    }

    @Override
    public void alterarSenha(Pessoa pessoa, String novaSenha) throws PessoaInexistenteException {
        pessoas.alterarSenha(pessoa, novaSenha);
    }

    @Override
    public List<Cliente> listarClientes() {
        return pessoas.listarClientes();
    }

    @Override
    public List<Empregado> listarEmpregados() {
        return pessoas.listarEmpregados();
    }

    @Override
    public String informacoesPessoais(Pessoa pessoa) throws PessoaInexistenteException {
        return pessoas.informacoesPessoais(pessoa);
    }

    @Override
    public void criarProposta(Proposta p) throws PropostaInvalidaException {
        propostas.criarProposta(p);
    }

    @Override
    public void criarContraProposta(Proposta proposta) throws PropostaInvalidaException {
        propostas.criarContraProposta(proposta);
    }

    @Override
    public Proposta buscarProposta(long numProtocolo) throws PropostaInvalidaException {
        return propostas.buscarProposta(numProtocolo);
    }

    @Override
    public void atualizarGarantias(Proposta propostaComGarantia) throws PropostaInvalidaException {
        propostas.atualizarGarantias(propostaComGarantia);
    }

    @Override
    public void aprovarContraProposta(long numProtocolo) throws PropostaInvalidaException {
        propostas.aprovarContraProposta(numProtocolo);
    }

    @Override
    public Map<LocalDate, Proposta> listarPropostasCliente(long uidCliente) throws PessoaInexistenteException {
        return propostas.listarPropostasCliente(uidCliente);
    }

    @Override
    public Map<LocalDate, Proposta> listarContraPropostas(long uidCliente) throws PessoaInexistenteException {
        return propostas.listarContraPropostas(uidCliente);
    }

    @Override
    public Map<LocalDate, Proposta> listarPropostasPendentes() {
        return propostas.listarPropostasPendentes();
    }
}
