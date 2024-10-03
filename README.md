## Projeto de Extensão
### Detalhes:
Desenvolvido em colaboração no curso de Sistemas de Informação da FEF. Minha parte foi o desenvolvimento do back-end utilizando Java/Spring e PostgreSQL. O projeto está dividido em duas APIs:

- **event-extension-project-api-authentication**:  
  Responsável pelo controle de acesso e login, com níveis de permissão diferenciados entre Administrador, Organizador e Participante.

- **event-extension-project-api**:  
  Gerencia as regras de negócio e possui as seguintes funcionalidades:
  - Administradores e Organizadores podem cadastrar eventos acadêmicos, gerenciar o controle financeiro (lançamento de despesas, doações e inscrições) e registrar a presença dos Participantes.
  - Participantes realizam seu cadastro, inscrevem-se em eventos e, ao marcar presença, podem gerar seus certificados.
