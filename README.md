# Sistema de Reservas de Hotéis

Projeto Utilizado no refatoramento: Online Booking System in Hotels, este projeto é um sistema de reservas de hotéis desenvolvido em Java para rodar no terminal.<br>
Não foi adicionada nenhuma nova funcionalidade, apenas refatoradas as existentes com aplicação de padrões de projeto.

## Padrões de Projeto Implementados

### Padrões Criacionais
#### 1.Factory Method
- Onde: `factory/` package e `BookingManager`
- Propósito: Criar diferentes tipos de reservas (Standard, Promocional, Corporativa)
- Implementação:
    - `ReservationFactory` interface
    - `StandardReservationFactory`, `PromoReservationFactory`, `CorporativeReservationFactory`
- Benefício: Desacoplamento na criação de objetos e fácil extensão para novos tipos de reserva

#### 2. Singleton
- Onde: `util/LanguageManager` e `util/DatabaseManager`
- Propósito: Garantir uma única instância global do gerenciador de idiomas e da conexão com o banco.
- Implementação: Enum singleton (`LanguageManager`) e Singleton clássico (`DatabaseManager`).
- Benefício: Acesso consistente aos recursos compartilhados em todo o sistema.

#### 3. Builder
- Onde: `model/Hotel`
- Propósito: Construção flexível de objetos Hotel com muitos parâmetros opcionais
- Implementação: `Hotel.Builder` classe interna
- Benefício: Código mais legível e construção passo-a-passo de objetos complexos

### Padrões Estruturais
#### 1. Facade
- Onde: `facade/HotelSystemFacade`
- Propósito: Prover uma interface simplificada para um subsistema complexo (os vários managers)
- Implementação: `HotelSystemFacade` centraliza as chamadas do `app/Main` e as delega para `HotelManager`, `RoomManager`, `BookingManager`, etc.
- Benefício: Reduz o acoplamento entre o cliente (Main) e a lógica de negócio interna.

#### 2. Adapter
- Onde: `payment/adapters/` package e `payment/PaymentGatewayFactory`
- Propósito: Permitir que diferentes gateways de pagamento sejam usados através de uma interface comum (`IPaymentGateway`)
- Implementação: `CreditCardAdapter`, `PixAdapter`, `BoletoAdapter` implementam `IPaymentGateway`.
- Benefício: Flexibilidade para adicionar novos métodos de pagamento sem alterar a lógica de `BookingManager`.

#### 3. Composite
- Onde: `model/IEntidadeHoteleira`, `model/Hotel`, `model/GrupoHotel`
- Propósito: Tratar objetos individuais (Hotel) e composições de objetos (GrupoHotel) de maneira uniforme.
- Implementação: `Hotel` e `GrupoHotel` implementam η interface `IEntidadeHoteleira`.
- Benefício: Permite que o cliente trate um hotel ou um grupo de hotéis da mesma forma (ex: `exibirDetalhes()`).

### Padrões Comportamentais
#### 1. Strategy
- Onde: `strategy/` package e `BookingManager`
- Propósito: Definir diferentes algoritmos para cálculo de pontos de fidelidade
- Implementação:
    - `LoyaltyPointsStrategy` interface
    - `StandardPointsStrategy`, `PromoPointsStrategy`, `CorporatePoinstsStrategy`
- Benefício: Flexibilidade para alterar regras de pontuação sem modificar o código cliente

#### 2. Observer
- Onde: `observer/` package e `BookingManager`
- Propósito: Notificar automaticamente sistemas interessados em eventos de reserva
- Implementação:
    - `ReservationObserver` interface
    - `EmailNotificationObserver`, `SystemLogObserver`
- Benefício: Desacoplamento entre a lógica de negócio e sistemas de notificação

#### 3. State
- Onde: `model/state/` package e `model/Room`
- Propósito: Permitir que um objeto (Room) altere seu comportamento quando seu estado interno muda.
- Implementação:
    - `RoomState` interface
    - `AvailableState`, `OccupiedState`, `MaintenanceState`
- Benefício: Evita condicionais complexas (if/else) na classe `Room` e facilita a adição de novos estados.

## Tratamento de Exceções

O sistema possui tratamento contra entradas inválidas do usuário, utilizando validações em tempo real para garantir que os dados inseridos estejam corretos antes de serem processados. Caso uma entrada seja inválida, o sistema informa o usuário e solicita a informação novamente.

* **Seleção de Idioma:**
    * Valida se a opção de idioma (1, 2 ou 3) é válida. Caso não seja, reverte automaticamente para o Inglês como padrão.

* **Menu Principal:**
    * Valida se a opção do menu (1-14) é válida. Opções fora desse intervalo resultam em uma mensagem de "Opção inválida".

* **Gestão de Clientes (`CustomerManager`):**
    * **Nome:** Verifica se o nome do cliente não contém números.
    * **Email:** Utiliza Regex para validar se o formato do email é válido (ex: `usuario@dominio.com`).

* **Gestão de Quartos (`RoomManager`):**
    * **Tipo de Quarto:** Valida se o tipo de quarto inserido (ex: "solteiro", "casal") corresponde a um tipo válido definido no sistema.

* **Gestão de Reservas (`BookingManager`):**
    * **Dias:** Valida se a quantidade de dias da reserva é um número (`NumberFormatException`) e se é um valor positivo (maior que 0).
    * **Tipo de Reserva:** Valida se a opção (1-Standard, 2-Promo, 3-Corporate) é um número e se está dentro do intervalo permitido (1, 2 ou 3).
    * **Pagamento:** Valida se a opção de pagamento é um número válido.

* **Gestão de Avaliações (`ReviewManager`):**
    * **Rating (Nota):** Valida se a nota fornecida é um número (`NumberFormatException`) e se está dentro do intervalo permitido (1 a 5).

## Funcionalidades Principais

### Gestão de Hotéis
- Adicionar novos hotéis com nome, endereço e descrição
- Listar todos os hotéis cadastrados

### Gestão de Quartos
- Adicionar quartos a hotéis existentes com número, tipo (Single, Couple, Premium etc.) e preço
- Listar quartos disponíveis por hotel
- Colocar quartos em manutenção (padrão State)

### Gestão de Clientes
- Criar perfis de clientes com nome, e-mail
- Consultar pontos de fidelidade acumulados

### Reservas
- Reservar quartos disponíveis informando hotel, número do quarto e e-mail do cliente
- Cancelar reservas existentes
- Suporte a diferentes tipos de reservas (padrão Factory Method):
    - Padrão
    - Promocional
    - Corporativa

### Sistema de Pontos de Fidelidade
- Cálculo dinâmico de pontos baseado no tipo de reserva (Strategy Pattern)
- Pontuação diferenciada:
    - Standard: 10% do valor da reserva
    - Promocional: 5% do valor da reserva
    - Corporativa: 15% do valor da reserva

### Sistema de Notificações
- Notificação por email para clientes (Observer Pattern)
- Registro em log do sistema para auditoria (Observer Pattern)
- Notificações automáticas para eventos de reserva e cancelamento

### Avaliações
- Clientes podem avaliar hotéis após a estadia
- Listar todas as avaliações de cada hotel

### Persistência de Dados
- O sistema utiliza um banco de dados **SQLite** (`hotel_booking.db`) para persistir todos os dados.
- Hotéis, quartos, clientes, reservas e avaliações são salvos automaticamente e recarregados quando o programa é reiniciado.

### Relatórios Analíticos
- Exibir taxa de ocupação e receita estimada por hotel

### Suporte a Múltiplos Idiomas
- Suporte completo para (Singleton Pattern):
    - Português 🇧🇷
    - Inglês 🇺🇸
    - Espanhol 🇪🇸
- Seleção do idioma logo ao iniciar o sistema

### Simulação de Pagamento
- Processamento de pagamento no terminal (Adapter Pattern)
- Suporte a Cartão de Crédito, PIX e Boleto

### Suporte ao Cliente (Simulado)
- Interface de suporte fictícia com interações no terminal

## Estrutura do Projeto

O código está organizado em pacotes:

- `app` → ponto de entrada da aplicação (Main.java)
- `facade` → implementação do Facade Pattern
- `manager` → classes que implementam a lógica do sistema (reservas, hotéis, clientes, relatórios etc.)
- `model` → entidades principais do sistema (Hotel, Room, Customer, Reservation etc.)
- `model/state` → implementação do State Pattern
- `util` → funcionalidades auxiliares (gerenciador de idiomas, suporte ao cliente, gerenciador de banco de dados)
- `factory` → implementação do Factory Method para criação de reservas
- `strategy` → implementação do Strategy Pattern para cálculo de pontos
- `observer` → implementação do Observer Pattern para sistema de notificações
- `payment` → implementação do Adapter Pattern para gateways de pagamento
- `resources` → arquivos de propriedades para internacionalização (i18n)

## Como Executar

**Pré-requisitos:**
- JDK 17 ou superior instalado
- (Opcional, recomendado) IntelliJ IDEA Community Edition ou outra IDE Java

---

### Método 1: Executando no IntelliJ IDEA

1.  **Clone o repositório** e abra a pasta do projeto no IntelliJ IDEA.
2.  **Adicione a dependência do SQLite:**
    -   Vá em `File` > `Project Structure...` (ou `Ctrl+Alt+Shift+S`).
    -   No menu da esquerda, selecione `Libraries`.
    -   Clique no ícone `+` (Adicionar) e escolha `From Maven...`.
    -   Na caixa de busca, digite: `org.xerial:sqlite-jdbc`
    -   Selecione a versão mais recente na lista (ex: `3.46.0.1`) e clique em `OK`.
    -   Clique em `OK` novamente para fechar a janela de Estrutura do Projeto.
3.  **Execute o projeto:**
    -   Encontre o arquivo `src/app/Main.java` na árvore de projeto.
    -   Clique com o botão direito nele e selecione `Run 'Main.main()'`.
4.  Na primeira execução, o banco de dados `hotel_booking.db` será criado automaticamente na pasta raiz do projeto.

---

### Método 2: Compilando e Executando Manualmente (Avançado)

1.  **Baixe o driver JDBC:**
    -   Faça o download do arquivo `.jar` mais recente do [sqlite-jdbc no GitHub](https://github.com/xerial/sqlite-jdbc/releases).
2.  **Organize os arquivos:**
    -   Crie uma pasta `lib` na raiz do projeto.
    -   Mova o arquivo `sqlite-jdbc-X.X.X.jar` que você baixou para dentro da pasta `lib`.
3.  **Compile o projeto:**
    -   Abra seu terminal e navegue até a pasta `src`.
    -   Execute o comando de compilação, incluindo os `resources` e o driver `lib` no classpath:

    ```bash
    # (No Windows - observe o separador ';')
    javac -cp ".;../lib/sqlite-jdbc-X.X.X.jar;../resources" -d ../out app/Main.java

    # (No macOS/Linux - observe o separador ':')
    javac -cp ".:../lib/sqlite-jdbc-X.X.X.jar:../resources" -d ../out app/Main.java
    ```
    *(Substitua `X.X.X` pela versão do jar que você baixou)*

4.  **Execute o projeto:**
    -   No terminal, navegue para a pasta `out` recém-criada.
    -   Execute o `Main`, incluindo novamente o `lib` e `resources` no classpath:

    ```bash
    # (No Windows - observe o separador ';')
    java -cp ".;../lib/sqlite-jdbc-X.X.X.jar;../resources" app.Main

    # (No macOS/Linux - observe o separador ':')
    java -cp ".:../lib/sqlite-jdbc-X.X.X.jar:../resources" app.Main
    ```
5.  Na primeira execução, o banco de dados `hotel_booking.db` será criado automaticamente.