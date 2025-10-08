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

### Padrões Comportamentais
#### 1. Strategy
- Onde: `strategy/` package e `BookingManager`
- Propósito: Definir diferentes algoritmos para cálculo de pontos de fidelidade
- Implementação:
    - `LoyaltyPointsStrategy` interface
    - `StandardPointsStrategy`, `PromoPointsStrategy`, `CorporatePointsStrategy`
- Benefício: Flexibilidade para alterar regras de pontuação sem modificar o código cliente

#### 2. Observer
- Onde: `observer/` package e `BookingManager`
- Propósito: Notificar automaticamente sistemas interessados em eventos de reserva
- Implementação:
    - `ReservationObserver` interface
    - `EmailNotificationObserver`, `SystemLogObserver`
- Benefício: Desacoplamento entre a lógica de negócio e sistemas de notificação

## Funcionalidades Principais

### Gestão de Hotéis
- Adicionar novos hotéis com nome, endereço e descrição
- Listar todos os hotéis cadastrados

### Gestão de Quartos
- Adicionar quartos a hotéis existentes com número, tipo (Single, Couple, Premium etc.) e preço
- Listar quartos disponíveis por hotel

### Gestão de Clientes
- Criar perfis de clientes com nome, e-mail e CPF
- Consultar pontos de fidelidade acumulados

### Reservas
- Reservar quartos disponíveis informando hotel, número do quarto e CPF do cliente
- Cancelar reservas existentes
- Suporte a diferentes tipos de reservas:
  - Padrão
  - Promocional
  - Corporativa
- Exibir relatório de reservas

### Sistema de Pontos de Fidelidade
- Cálculo dinâmico de pontos baseado no tipo de reserva (Strategy Pattern)
- Pontuação diferenciada:
    - Standard: 10% do valor da reserva
    - Promocional: 5% do valor da reserva
    - Corporativa: 15% do valor da reserva
- Remoção automática de pontos em caso de cancelamento

### Sistema de Notificações
- Notificação por email para clientes (Observer Pattern)
- Registro em log do sistema para auditoria
- Notificações automáticas para eventos de reserva e cancelamento

### Avaliações
- Clientes podem avaliar hotéis após a estadia
- Listar todas as avaliações de cada hotel

### Relatórios Analíticos
- Hotel mais reservado
- Quarto mais caro e mais barato
- Hotel com melhor avaliação
- Hotel com mais pontos de fidelidade acumulados

### Suporte a Múltiplos Idiomas
- Suporte completo para:
  - Português 🇧🇷
  - Inglês 🇺🇸
  - Espanhol 🇪🇸
- Seleção do idioma logo ao iniciar o sistema

### Simulação de Pagamento
- Processamento fictício de pagamento no terminal
- Exibição do valor total e confirmação da transação

### Suporte ao Cliente (Simulado)
- Interface de suporte fictícia com interações no terminal
- Simulação de atendimento humano

## Estrutura do Projeto

O código está organizado em pacotes:

- `app` → ponto de entrada da aplicação (Main.java)
- `manager` → classes que implementam a lógica do sistema (reservas, hotéis, clientes, relatórios etc.)
- `model` → entidades principais do sistema (Hotel, Room, Customer, Reservation, Booking, Review, User, Admin etc.)
- `util` → funcionalidades auxiliares como processador de pagamento, gerenciador de idiomas e suporte ao cliente
- `factory` → implementação do Factory Method para criação de reservas
- `strategy` → implementação do Strategy Pattern para cálculo de pontos
- `observer` → implementação do Observer Pattern para sistema de notificações

## Como Executar

**Pré-requisitos:**
- JDK 17 ou superior instalado

**Compilar o projeto:**
No terminal, entre na pasta src e execute:

```bash
javac app/Main.java
