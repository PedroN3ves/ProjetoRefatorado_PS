# Sistema de Reservas de Hotéis

Projeto Utilizado no refatoramento: Online Booking System in Hotels, este projeto é um sistema de reservas de hotéis desenvolvido em Java para rodar no terminal.
Não foi adicionada nenhuma nova funcionalidade, apenas refarodas as existentes

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

## Como Executar

**Pré-requisitos:**
- JDK 17 ou superior instalado

**Compilar o projeto:**
No terminal, entre na pasta src e execute:

```bash
javac app/Main.java
