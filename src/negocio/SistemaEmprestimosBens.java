package negocio;

import exceptions.*;
import negocio.beans.*;

import java.time.LocalDate;
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

    /**
     * Método que faz a busca de um BENS do cliente por meio do nome de seu BENS. Utiliza a técnica lambda/stream de
     * java 8 que filtra de acordo com o nome do BENS e seleciona o elemento (caso haja uma lista de elementos
     * seleciona apenas o último elemento de uma lista; caso para duplicados) de mesmo nome.
     *
     * @param uidCliente se refere ao {@code uidCliente} do qual se trata o BENS.
     * @param nomeBens se refere ao {@code nome} do Bens do qual se trata o BENS.
     * @return irá retornar o BENS pesquisado sobre os parâmetros anteriores
     */
    Bens buscarBensCliente(long uidCliente, String nomeBens);

    /**
     * Método que retorna todos os BENS cadastrados no repositório de BENS.
     * @return lista de BENS do repositório de Bens
     */
    List<Bens> listarBens();

    /**
     * Método que lista um {@code List<Bens>} de todos os Bens da empresa, filtrado por um {@code CategoriaBens}
     * parametrizada.
     *
     * @param categoria se refere a categoria de bens que se pretende listar.
     * @return uma lista com os bens filtrados por meio de uma {@code CategoriaBens} com parâmetro.
     */
    List<Bens> listarBensEmpresaCategoria(CategoriaBens categoria);

    /**
     * Método que lista dos Bens do Repositório de Bens, filtrado por Cliente, neste caso pelo {@code uidCliente}.
     *
     * @param uidCliente se refere ao identificador único e exclusivo do cliente que filtra os Bens.
     * @return lista de bens filtrado para o cliente especificado.
     */
    List<Bens> listarBensCliente(long uidCliente);

    /**
     * Método que lista os BENS pendentes do cliente do quais são objetos do tipo {@code Bens} que tem seu atributo
     * {@code pendente true}.
     *
     * @param uidCliente se refere ao identificador único e exclusivo do cliente.
     * @return Lista de Bens filtrados por cliente e por pendência.
     */
    List<Bens> listarBensPendentes(long uidCliente);

    /**
     * Método que lista todos os BENS aprovados criado para armazenar objetos do tipo {@code Bens} que tem seu atributo
     * {@code pendente false}.
     *
     * @return Lista de Bens filtrados por aprovação.
     */
    List<Bens> listarBensAprovados();

    /**
     * Método que lista todos os BENS que são garantia criado para armazenar objetos do tipo {@code Bens} que tem seu
     * atributo {@code garantia true}.
     *
     * @return Lista de Bens filtrados por garantia.
     */
    List<Bens> listarBensGarantia();

    double calcularValorBensCliente(long uidCliente) throws PessoaInexistenteException;

    /**
     * Método que, por meio de lista de Bens, filtra os bens e os aplica como garantia; ou seja realiza o set do
     * atributo de {@code garantia} como {@code true}.
     *
     * @param bensAntigos se refere a lista de Bens ao qual será aplicado como garantia.
     * @throws BensInexistenteException poderá acontecer caso haja um BENS que não pertença ao repositório de BENS
     */
    void aplicarBensComoGarantia(List<Bens> bensAntigos) throws BensInexistenteException;

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
    void criarEmprestimo(Proposta proposta, Empregado empregado) throws EmprestimoDuplicadoException,
            BensInexistenteException;

    /**
     * Método que faz a busca de um {@code Emprestimo} no repositório de Empréstimos por meio de um número de protocolo.
     *
     * @param numProtocolo se refere ao número único dado a cada empréstimo, quando cada empréstimo é criado.
     * @return retorna um {@code Emprestimo} referente ao número de protocolo pedido do repositório.
     * @throws EmprestimoInexistenteException poderá acontecer caso o número de protocolo seja inválido ou se a proposta
     * não existir no repositório de empréstimos.
     */
    Emprestimo buscarEmprestimo(long numProtocolo) throws EmprestimoInexistenteException;

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

    /**
     * Método que retorna todos os empréstimos feitos pelo cliente identificado por seu {@code uid} através de uma
     * {@code List} do qual armazena todos os objetos do tipo {@code Emprestimo}.
     *
     * @param uidCliente se refere ao identificador único e exclusivo do cliente.
     * @return List de empréstimos filtrados por cliente.
     */
    List<Emprestimo> listarEmprestimosCliente(long uidCliente);

    /**
     * Método que lista todas as comissões de empréstimos de que um empregado é responsável.
     *
     * @param empregado se refere ao empregado que se quer listar os empréstimos no qual ele é responsável.
     * @return uma lista de empréstimos do qual o empregado é responsável.
     */
    List<Emprestimo> listarComissoesEmprestimo(Empregado empregado);

    /**
     * Método que retorna uma {@code List} de {@code Emprestimo} referente ao {@code prazo} de todos os
     * {@code Emprestimo} que não pagaram até a data de pagamento.
     *
     * @return Lista de Empréstimos filtrados por data de pagamento.
     */
    List<Emprestimo> listarDevedores();

    /**
     * Método que retorna uma {@code List} de {@code Emprestimo} referente ao {@code prazo} de todos os
     * {@code Emprestimo} que não foram pagos até a data de pagamento, porém possuem um {@code score} alto.
     *
     * @return Lista de Empréstimos filtrados por data de pagamento e score alto.
     */
    List<Emprestimo> listarDevedoresProtegidos();

    /**
     * Método que retorna uma {@code List} de {@code Emprestimo} referente ao {@code prazo} de todos os
     * {@code Emprestimo} que não foram pagos até o {@code prazo} e além disso possuem um {@code score} baixo.
     *
     * @return Lista de Empréstimo filtrados por data de pagamento e score baixo.
     */
    List<Emprestimo> listarDevedoresAltoRisco();

    /**
     * Método que realiza o pagamento de um empréstimo e atualiza o empréstimo no repositório de Empréstimos
     *
     * @param numProtocolo se refere ao número de protocolo do empréstimo que se deseja realizar um pagamento.
     * @param valorPago se refere ao valor que será debitado do valor da parcela mensal do pagamento.
     * @throws EmprestimoInexistenteException poderá acontecer se não existir um empréstimo com o número de protocolo
     * veículado em {@code numProtocolo}.
     */
    void pagarEmprestimo(long numProtocolo, double valorPago) throws EmprestimoInexistenteException;
    
    double calcularValorParcelas(Emprestimo emprestimo);
    
    LocalDate calcularProximoPrazo(Emprestimo emprestimo, int parcela);

    /**
     * Método que gera a movimentação e armazena um objeto {@code Movimentacao} dentro do repositório de movimentações
     *
     * @param movimentacao se refere ao objeto {@code Movimentacao} que se deseja inserir dentro do repositório.
     * @throws MovimentacaoDuplicadaException poderá acontecer caso já exista uma movimentação igual no repositório do
     * sistema.
     */
    void gerarMovimentacao(Movimentacao movimentacao) throws MovimentacaoDuplicadaException;

    /**
     * Método que lista as Movimentações do cliente.
     * 
     * @param uidCliente se refere ao identificador único e exclusivo do cliente.
     * @return List de Movimentacao ordenados por data.
     */
    List<Movimentacao> listarMoveCliente(long uidCliente);

    /**
     * Método que lista as Movimentações do cliente num período específico indicado por parâmetro de data inicial e final.
     * 
     * @param uidCliente se refere ao identificador único e exclusivo do cliente.
     * @param dataInicial é a data a partir da qual o cliente deseja ver suas movimentações.
     * @param dataFinal é a data limite em qual o cliente deve pagar a parcela sem aumento.
     * @return List de Movimentacao.
     */
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
     * Método que faz a busca de uma {@code Pessoa} dentro do repositório de todos os usuários.
     *
     * @param uidPessoa se trata do parâmetro de busca do usuário
     * @return retorna um objeto abstrato do tipo {@code Pessoa}
     * @throws PessoaInexistenteException poderá acontecer caso o {@code email} não esteja atribuído a nenhuma
     * {@code Pessoa}.
     */
    Pessoa buscarPessoa(long uidPessoa) throws PessoaInexistenteException;

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

    /**
     * Método que cria e adiciona um objeto do tipo {@code Proposta}, no qual atribui um número de procolo a proposta e
     * seta parâmetros de controle, como {@code data} e {@code contraproposta}.
     * @param p se refere a proposta inicial ao qual se deseja adicionar ao sistema.
     * @throws PropostaInvalidaException poderá acontecer caso a proposta seja inválida por alguma razão.
     */
    void criarProposta(Proposta p) throws PropostaInvalidaException;

    /**
     * Método que altera uma proposta existente e a transformar em uma contraproposta que mantém o mesmo número de
     * protocolo.
     *
     * @param contraproposta se refere a nova contraproposta que irá substituir pela proposta anterior.
     * @throws PropostaInvalidaException poderá acontecer caso o número de protocolo seja inválido e se a proposta não
     * existir no repositório de propostas.
     */
    void criarContraProposta(Proposta contraproposta) throws PropostaInvalidaException;

    /**
     * Método que faz a busca de uma {@code Proposta} no repositório de propostas por meio de um número de protocolo.
     *
     * @param numProtocolo se refere ao número único dado a cada proposta, quando cada proposta é criada.
     * @return retorna uma {@code Proposta} referente ao número do Protocolo pedido do repositório
     * @throws PropostaInvalidaException poderá acontecer caso o número de protocolo seja inválido ou se a proposta
     * não existir no repositório de propostas.
     */
    Proposta buscarProposta(long numProtocolo) throws PropostaInvalidaException;

    /**
     * Método que atualiza apenas as Garantias de uma Proposta do Repositório de Propostas. Consiste em pegar a
     * {@code garantia} de uma {@code Proposta} e passar para a {@code Proposta} do repositório (que tenha o mesmo
     * {@code numProtocolo}) por meio do método {@code alterarProposta}.
     *
     * @param propostaComGarantia se refere a uma proposta com uma nova garantia que se pretende inserir numa proposta
     *                            do repositório.
     * @throws PropostaInvalidaException poderá acontecer caso o número do protocolo da {@code propostaComGarantia}
     * seja inválido ou se não houver {@code Proposta} no repositório de propostas.
     */
    void atualizarGarantias(Proposta propostaComGarantia) throws PropostaInvalidaException;

    /**
     * Método com foco no negócio, que realiza a aprovação de contrapropostas. A ideia aqui é apenas setar como
     * {@code true} sempre que uma contraproposta for aprovada.
     *
     * @param numProtocolo se refere ao número único de uma proposta que se deseja aprovar.
     * @throws PropostaInvalidaException poderá acontecer caso o número do protocolo não seja válido, se a proposta não
     * existir e se esta for uma ação ilegal para esse método (caso a proposta não seja uma contraproposta).
     */
    void aprovarContraProposta(long numProtocolo) throws PropostaInvalidaException;

    /**
     * Método que lista as propostas do cliente criado para armazenar objetos do tipo {@code Proposta} a partir do seu 
     * atributo do tipo {@code Cliente}.
     * 
     * @param uidCliente se refere ao identificador único e exclusivo do cliente.
     * @return List de propostas.
     */
    List<Proposta> listarPropostasCliente(long uidCliente); 
    
    /**
     * Método que lista as contra propostas realizadas ao cliente e que tenham o atributo {@code contraProposta} true a partir 
     * do seu atributo do tipo {@code Cliente}.
     * 
     * @param uidCliente se refere ao identificador único e exclusivo do cliente.
     * @return List de propostas.
     */
    List<Proposta> listarContraPropostas(long uidCliente);

    /**
     * Método que lista as contra propostas realizadas ao cliente e que tenham o atributo {@code contraProposta} false a partir 
     * do seu atributo do tipo {@code Cliente}.
     * 
     * @return List de propostas.
     */
    List<Proposta> listarPropostasPendentes();
}
