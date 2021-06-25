package negocio;

import exceptions.*;
import negocio.beans.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface SistemaEmprestimosBens {

    /**
     * Método que insere bem no repositório.
     *
     * @param bens bem a ser inserido no repositório. O objeto receberá a data do cadastro e receberá os parametros
     *             falso para o atributo {@code garantia} e verdadeiro para o atributo {@code pendente}.
     * @throws BensDuplicadoException caso o bem cadastrado já tenha sido inserido.
     */
    void inserirBens(Bens bens) throws BensDuplicadoException;

    Map<LocalDate, Bens> listarBensEmpresa();

    Map<LocalDate, Bens> listarBensCliente(long uidCliente) throws PessoaInexistenteException;

    Map<LocalDate, Bens> listarBensPendentes(long uidCliente) throws PessoaInexistenteException;

    Map<LocalDate, Bens> listarBensAprovados(long uidCliente) throws PessoaInexistenteException;

    Map<LocalDate, Bens> listarBensGarantia(long uidCliente) throws PessoaInexistenteException;

    double calcularValorBensCliente(long uidCliente) throws PessoaInexistenteException;

    /**
     * Método que altera um bem por outro no repositório
     *
     * @param bensAntigo bem inicialmente cadastrado.
     * @param bensNovo   novo bem a ser inserido para substituir
     * @throws BensInexistenteException caso o bem a ser substituído não exista no repositório.
     */
    void alterarBens(Bens bensAntigo, Bens bensNovo) throws BensInexistenteException;

    /**
     * Método que remove bens do repositório.
     *
     * @param bens bem a ser removido do repositório.
     * @throws BensInexistenteException caso o bem a ser substituído não exista no repositório.
     */
    void removerBens(Bens bens) throws BensInexistenteException;

    /**
     * Método que cria um empréstimo a partir de uma proposta aprovada por um empregado, neste método são aplicadas
     * regras de negócio, como a primeira data de pagamento do empréstimo que se baseia na
     * {@code QTD_DIAS_PARA_1_PAGAMENTO}, o responsável pelo empréstimo {@code empregado}, e da
     * {@code CONFIANCA_PAGAMENTO_INICIAL}, que se trata da probalidade do próximo pagamento.
     *
     * @param proposta  se refere a uma proposta aprovada e a ser transformada em empréstimo aprovado.
     * @param empregado se refere ao objeto {@code Empregado} que será responsável pelo empréstimo.
     * @throws EmprestimoDuplicadoException poderá acontecer caso o {@code Emprestimo} já esteja cadastro no sistema de
     *                                      {@code repoEmprestimo}.
     */
    void criarEmprestimo(Proposta proposta, Empregado empregado) throws EmprestimoDuplicadoException;

    /**
     * Método que procura por detalhes de um empréstimo efetuado em uma determinada {@code dataEmprestimo} por um
     * {@code cliente}, visto que, há apenas 1 empréstimo do mesmo cliente por dia. O método lista os empréstimos de
     * um cliente e procura por empréstimos por data.
     *
     * @param cliente        se refere ao {@code Cliente} que realizou o {@code Emprestimo}.
     * @param dataEmprestimo se refere a data em que o {@code Cliente} realizou o {@code Emprestimo}.
     * @return uma {@code String} de dados, com base no método {@code toString()} do objeto {@code Emprestimo}.
     * @throws EmprestimoInexistenteException poderá acontecer caso não exista um empréstimo para esse {@code cliente}
     *                                        ou não existe um empréstimo para esse {@code cliente} na determinada {@code dataEmprestimo}.
     */
    String emprestimoEmDetalhe(Cliente cliente, LocalDate dataEmprestimo) throws EmprestimoInexistenteException;

    Map<LocalDate, Emprestimo> listarEmprestimosCliente(long uidCliente);

    List<Emprestimo> listarComissõesEmprestimo();

    Map<LocalDate, Cliente> listarDevedores();

    Map<LocalDate, Cliente> listarDevedoresProtegidos();

    Map<LocalDate, Cliente> listarDevedoresAltoRisco();

    Map<LocalDateTime, Movimentacao> listarMoveCliente(long uidCliente) throws PessoaInexistenteException;

    List<Movimentacao> listarPeriodoMovimentacaoCliente(long uidCliente, LocalDate dataInicial, LocalDate dataFinal);

    List<Movimentacao> listarDiasMovimentacaoCliente(long uidCliente, int dias);

    /**
     * A função de {@code cadastrarPessoa} é de criar e adiciona um objeto do tipo {@code Pessoa} com senha no formato
     * hash, em especial com o algoritmo SHA-256, dessa forma, armazena apenas o digest da senha com a função hash. Há
     * também a verificação se o cpf é válido, com a validação básica de um número cpf. O objeto criado por essa função
     * é armazenado no objeto {@code repoCliente} {@code repoEmpregado}, que se trata do repositório/banco de dados de
     * clientes e empregados, de acordo com sua instância.
     *
     * @param pessoa      objeto {@code Pessoa} a ser inserido no repoCliente.
     * @param senhaPessoa senha da pessoa a ser tratada para um digest SHA-256.
     * @throws PessoaCPFInvalidoException exceção que determinada quando o CPF da pessoa é considerado inválido.
     * @throws PessoaDuplicadoException   exceção que é determinada quando há uma pessoa duplicada no seu respectivo
     *                                    repositório.
     */
    void cadastrarPessoa(Pessoa pessoa, String senhaPessoa) throws PessoaCPFInvalidoException, PessoaDuplicadoException;

    /**
     * A função de {@code autenticarPessoa} faz o processo de autenticação de um usuário do sistema, com base nas
     * informações cadastradas nos repositórios de pessoas, seja do Cliente ou do Empregado.
     *
     * @param email       email do usuário cadastrado, se trata do atributo {@code email} do objeto {@code Pessoa}.
     * @param senha       senha da pessoa a ser autenticada.
     * @param isEmpregado atributo que especifica se está autenticando um {@code Cliente} ou um {@code Empregado}.
     * @return {@code true} se a pessoa foi autenticada com sucesso e {@code false} caso a pessoa não tenha sido
     * autenticada com sucesso
     */
    boolean autenticarPessoa(String email, String senha, boolean isEmpregado);

    /**
     * Método que faz a busca de uma {@code Pessoa} dentro do repositório de todos os usuários.
     *
     * @param email se trata do parâmetro de busca do usuário
     * @return retorna um objeto abstrato do tipo {@code Pessoa}
     * @throws PessoaInexistenteException poderá acontecer caso o {@code email} não esteja atribuído a nenhuma
     * {@code Pessoa}.
     */
    Pessoa buscarPessoa(String email) throws PessoaInexistenteException;

    /**
     * Método que altera os dados cadastrados de uma pessoa por meio da substituição do objeto {@code Pessoa} antigo
     * por um novo objeto do tipo {@code Pessoa}.
     *
     * @param pessoaDadosNovo o novo objeto que irá substituir a {@code Pessoa} antiga.
     * @throws PessoaInexistenteException poderá acontecer caso o {@code uidCliente} não esteja atribuído a nenhum
     *                                    cliente.
     */
    void alterarDadosPessoais(Pessoa pessoaDadosNovo) throws PessoaInexistenteException;

    /**
     * Método que altera o atributo {@code senha} de um objeto do tipo {@code Cliente} por meio da substituição do
     * atributo anterior por um novo atributo de senha.
     *
     * @param pessoa    se refere a pessoa que se vai alterar a senha
     * @param novaSenha se refere a nova senha que será cadastrada no repositório que armazena o digest da senha.
     * @throws PessoaInexistenteException poderá acontecer caso a {@code Pessoa} não esteja atribuída a nenhuma
     *                                    instância Pessoa dos repositórios.
     */
    void alterarSenha(Pessoa pessoa, String novaSenha) throws PessoaInexistenteException;

    /**
     * Método que lista todos os clientes do repositório de clientes {@code repoCliente}.
     *
     * @return uma lista não modificável do repositório de clientes.
     */
    List<Cliente> listarClientes();

    /**
     * Método que lista todos os empregados do repositório de empregados {@code repoEmpregado}.
     *
     * @return uma lista não modificável do repositório de empregados.
     */
    List<Empregado> listarEmpregados();

    /**
     * Método que retorna uma String de dados de uma pessoa.
     *
     * @param pessoa se refere ao usuário que irá ser verificado os dados no repositório de pessoas.
     * @return uma {@code String} de dados dos atributos do usuário.
     * @throws PessoaInexistenteException poderá acontecer caso a {@code Pessoa} não esteja atribuída a nenhuma
     *                                    instância Pessoa dos repositórios.
     */
    String informacoesPessoais(Pessoa pessoa) throws PessoaInexistenteException;

    void criarProposta(Proposta p) throws ObjetoDuplicadoException;

    String propostaEmDetalhe();

    /**
     * Método que lista as propostas do cliente ordenadas por sua data de criação por meio de um {@code Map} criado para armazenar
     * objetos do tipo {@code Proposta} a partir do seu atributo do tipo {@code Cliente} e ordená-los a partir do seu atributo
     * {@code data}.
     *
     * @param uidCliente se refere ao identificador único e exclusivo do cliente que se vai alterar o cadastro.
     * @throws PessoaInexistenteException poderá acontecer caso o {@code uidCliente} não esteja atribuído a nenhum
     *                                    cliente.
     */
    Map<LocalDate, Proposta> listarPropostasCliente(long uidCliente) throws PessoaInexistenteException;

    /**
     * Método que lista as contra propostas realizadas ao cliente ordenadas por sua data de criação por meio de um {@code Map}
     * criado para armazenar objetos do tipo {@code Proposta} que tenham o atributo {@code contraProposta} true a partir
     * do seu atributo do tipo {@code Cliente} e ordená-los a partir do seu atributo {@code data}.
     *
     * @param uidCliente se refere ao identificador único e exclusivo do cliente que se vai alterar o cadastro.
     * @throws PessoaInexistenteException poderá acontecer caso o {@code uidCliente} não esteja atribuído a nenhum
     *                                    cliente.
     */
    Map<LocalDate, Proposta> listarContraPropostas(long uidCliente) throws PessoaInexistenteException;

    /**
     * Método que lista as contra propostas realizadas ao cliente ordenadas por sua data de criação por meio de um {@code Map}
     * criado para armazenar objetos do tipo {@code Proposta} que tenham o atributo {@code contraProposta} false a partir
     * do seu atributo do tipo {@code Cliente} e ordená-los a partir do seu atributo{@code data}.
     *
     * @throws PessoaInexistenteException poderá acontecer caso o {@code uidCliente} não esteja atribuído a nenhum
     *                                    cliente.
     */
    Map<LocalDate, Proposta> listarPropostasPendentes() throws PessoaInexistenteException;
}
