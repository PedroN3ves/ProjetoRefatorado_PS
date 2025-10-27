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
- Onde: `util/LanguageManager`
- Propósito: Garantir uma única instância global do gerenciador de idiomas
- Implementação: Enum singleton thread-safe
- Benefício: Acesso consistente ao gerenciador de idiomas em todo o sistema

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
- Implementação: `Hotel` e `GrupoHotel` implementam a interface `IEntidadeHoteleira`.
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
- Remoção automática de pontos em caso de cancelamento

### Sistema de Notificações
- Notificação por email para clientes (Observer Pattern)
- Registro em log do sistema para auditoria (Observer Pattern)
- Notificações automáticas para eventos de reserva e cancelamento

### Avaliações
- Clientes podem avaliar hotéis após a estadia
- Listar todas as avaliações de cada hotel

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
- `util` → funcionalidades auxiliares (gerenciador de idiomas, suporte ao cliente)
- `factory` → implementação do Factory Method para criação de reservas
- `strategy` → implementação do Strategy Pattern para cálculo de pontos
- `observer` → implementação do Observer Pattern para sistema de notificações
- `payment` → implementação do Adapter Pattern para gateways de pagamento
- `resources` → arquivos de propriedades para internacionalização (i18n)

## Como Executar

**Pré-requisitos:**
- JDK 17 ou superior instalado

**Compilar o projeto:**
No terminal, entre na pasta `src` e execute:

```bash
javac app/Main.java