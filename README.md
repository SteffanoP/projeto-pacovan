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

- Client: será o usuário que utilizará os serviços do sistema para solicitar empréstimos.
  - Serviços liberados para os clients:
      - Lançar propostas para o employee.
      - Visualizar seu score de empréstimos e quitação de dívidas.
      -  Renegociar dívidas com o employee.
- Employee: será o usuário que fornecerá os serviços ao client.
  - Serviços para os employees:
      - Analisar propostas e realizar contrapropostas para o client.
      - Analisar B.E.N.S. do client como garantia.
      - Visualizar o status dos seus empréstimos com seus respectivos clients.
- Admin: será o usuário responsável por administrar as funcionalidades do sistema.
  - Serviços para o admin:
      - Designar clients para employees
      - Acesso a todas as informações geradas pelo sistema

Os serviços necessários serão os sistemas de login e autenticação dos usuários, solicitações de empréstimos, sistema de análise de risco, sistema de gerenciamento do Admin, sistema de gerenciamento de empréstimos do Employee (Cadastrar empréstimos, Aprovar/Rejeitar empréstimos, Gerar Relatórios de Cobrança para a Empresa, Renegociar Empréstimos, Avaliar os B.E.N.S.), Sistemas de interface: Client, Employee e Admin.

## Requisitos

1.	O sistema deve controlar o acesso através de login e senha (Autenticação). Os usuários do sistema serão do tipo client, employee e admin.
2.	O sistema deve permitir que os administradores tenham acesso às informações do cadastro dos clientes (localização, B.E.N.S., motivo para o empréstimo).
3 O sistema deve permitir que os administradores tenham acesso às informações dos employees (localização, preferências de negociação) para que as designações feitas sejam as melhores possíveis.
4.	O sistema deve permitir o contato de clientes e employees através de uma requisição feita pelos clientes, onde a negociação poderá ser feita a partir da análise do Employee.
5. O sistema de análise do employee permitirá uma contra-proposta representada pela empresa para o cliente e que deve ser enviada para o sistema do cliente.
6.	O sistema deve permitir ao usuário: client, a visualização do seu score, definido pela reputação do usuário e também da disponibilidade de B.E.N.S.
7. 	O sistema do employee deve fornecer o status dos seus empréstimos com seus respectivos clients, tais como a listagem de pagamentos.
8.	O sistema deve permitir o gerenciamento dos employees designando quais employees ficarão responsáveis por quaisquer empréstimos de suas preferências.
9. Deverá haver um sistema responsável por gerar relatórios de cobrança de clientes para a Empresa.
10. O Sistema deverá catalogar e avaliar os B.E.N.S. de acordo com os interesses da Empresa a partir de critérios alternativos, convenientes ao mercado.
11. O Sistema deverá conter as interfaces para respectivas funcionalidades aqui apresentadas.

---
