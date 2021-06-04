# Sistema de Gerenciamento de Empréstimo Pessoal Alternativo

Inspirado no Sistema de Reputação do SERASA e nos sistemas de empréstimos atuais, com foco no gerenciamento de amortização de empréstimos e renegociação **compulsória** de dívidas com juros exorbitantes. O Sistema será capaz de: registrar o empréstimo sem análise de riscos, ver a quantidade de parcelas restantes para quitação do ágio, amortização por Bens especiais Não Solicitados (B.E.N.S.) e taxas de juros variáveis para cada usuário.

## Equipe

- Daniel Silva de Lima
- George Justino de Queiroz
- Haga Fedra de Brito Cavalcanti
- Steffano Xavier Pereira

## Descrição Geral

O sistema será capaz de registrar empréstimos, sem análise de riscos, ver a quantidade de parcelas restantes para quitação do ágio, amortização por Bens Especiais Não Solicitados (B.E.N.S.) e taxas de juros variáveis para cada usuário.

O programa será utilizado por três tipos de usuários:

- Cliente: será o usuário que utilizará os serviços do sistema para solicitar empréstimos.
  - Serviços liberados para os clientes:
    - Lançar propostas para o employee.
    - Visualizar seu score de empréstimos e quitação de dívidas.
    - Renegociar dívidas com o employee.
- Employee: será o usuário que fornecerá os serviços ao cliente.
  - Serviços para os employees:
    - Analisar propostas e realizar contrapropostas para o cliente.
    - Analisar B.E.N.S. do cliente como garantia.
    - Visualizar o status dos seus empréstimos com seus respectivos clientes.
- Admin: será o usuário responsável por administrar as funcionalidades do sistema.
  - Serviços para o admin:
    - Designar clients para employees
    - Acesso a todas as informações geradas pelo sistema

Os serviços necessários serão os sistemas de login e autenticação dos usuários, solicitações de empréstimos, sistema de análise de risco, sistema de gerenciamento do Admin, sistema de gerenciamento de empréstimos do Employee (Cadastrar empréstimos, Aprovar/Rejeitar empréstimos, Gerar Relatórios de Cobrança para a Empresa, Renegociar Empréstimos, Avaliar os B.E.N.S.), Sistemas de interface: Cliente, Employee e Admin.

Os serviços necessários serão os sistemas de login e autenticação dos usuários, solicitações de empréstimos, sistema de análise de risco, sistema de gerenciamento do Admin, sistema de gerenciamento de empréstimos do Employee (Cadastrar empréstimos, Aprovar/Rejeitar empréstimos, Gerar Relatórios de Cobrança para a Empresa, Renegociar Empréstimos, Avaliar os B.E.N.S.), Sistemas de interface: Cliente, Employee e Admin.

Os relatórios serão gerados a partir dos usuários do sistema, que são o Cliente, Employee e Admin; em especial, o relatório do Admin que será considerado como o relatório do setor em que o Admin é responsável e não necessariamente do desempenho do Admin. No relatório do Cliente, estarão presentes informações pessoais, propostas, status dos empréstimos, extrato das operações, Score (pontuação do cliente), catálogo de BENS do Cliente. Do Relatório do Employee, estarão presentes informações pessoais, informações acerca de devedores, da reputação do negócio, de suas comissões, e de listas de clientes protegidos e também de clientes da lista de alto risco.

Já o relatório do Admin, é direcionado ao desempenho do negócio, no qual, estarão presentes informações do Serviço de Gerenciamento de Empréstimo Pessoal Alternativo. Mais detalhes acerca dos relatórios é possível visualizar na [documentação de entrega 03](docs/entregas-ip2/entrega03), no qual foram anexados documentos que especificam as informações agregadas de cada item do relatório.

## Requisitos

1. O sistema deve controlar o acesso através de login e senha (Autenticação). Os usuários do sistema serão do tipo cliente, employee e admin.
2. O sistema deve permitir que os administradores tenham acesso às informações do cadastro dos clientes (localização, B.E.N.S., motivo para o empréstimo).
3. O sistema deve permitir que os administradores tenham acesso às informações dos employees (localização, preferências de negociação) para que as designações feitas sejam as melhores possíveis.
4. O sistema deve permitir o contato de clientes e employees através de uma requisição feita pelos clientes, onde a negociação poderá ser feita a partir da análise do Employee.
5. O sistema de análise do employee permitirá uma contra-proposta representada pela empresa para o cliente e que deve ser enviada para o sistema do cliente.
6. O sistema deve permitir ao usuário: cliente, a visualização do seu score, definido pela reputação do usuário e também da disponibilidade de B.E.N.S.
7. O sistema do employee deve fornecer o status dos seus empréstimos com seus respectivos clients, tais como a listagem de pagamentos.
8. O sistema deve permitir o gerenciamento dos employees designando quais employees ficarão responsáveis por quaisquer empréstimos de suas preferências.
9. Deverá haver um sistema responsável por gerar relatórios de cobrança de clientes para a Empresa.
10. O Sistema deverá catalogar e avaliar os B.E.N.S. de acordo com os interesses da Empresa a partir de critérios alternativos, convenientes ao mercado.
11. O Sistema deverá conter as interfaces para respectivas funcionalidades aqui apresentadas.

---
