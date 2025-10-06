## EFC-01 - Refatoração de Sistema de Loja: De Python para Java com SOLID e Clean Code

### 1. Visão Geral
Este projeto é uma refatoração de um script Python monolítico para uma aplicação Java robusta e bem arquitetada. O objetivo principal foi aplicar os princípios SOLID e práticas de Clean Code para transformar o código original em um sistema manutenível, extensível e fácil de entender.

A aplicação simula um sistema de gerenciamento de pedidos de uma loja, incluindo funcionalidades como criação de pedidos, cálculo de totais com descontos, processamento de pagamentos, atualização de status e geração de relatórios.

## 2. Princípios e Práticas Aplicadas
A arquitetura foi guiada pelos seguintes pilares:

### SOLID
**S**RP (Single Responsibility Principle): Cada classe tem uma única responsabilidade bem definida. 

**O**CP (Open/Closed Principle): O sistema é aberto para extensão e fechado para modificação. 

**L**SP (Liskov Substitution Principle): As classes base podem ser substituídas por suas subclasses sem quebrar a aplicação. 

**I**SP (Interface Segregation Principle): As interfaces são pequenas e focadas em um único propósito.

**D**IP (Dependency Inversion Principle): Os módulos de alto nível dependem de abstrações, e não de implementações concretas.

### Clean Code
**Nomenclatura Clara:** Nomes de classes, métodos e variáveis são expressivos e revelam sua intenção (ex: calculateTotal, CustomerReportGenerator).

**Métodos Pequenos:** Métodos são curtos e realizam uma única tarefa.

**Separação de Responsabilidades:** A lógica de negócio, acesso a dados e apresentação estão em camadas distintas (services, repositories, strategies).

**Uso de Abstrações:** Interfaces são usadas para desacoplar componentes e aumentar a flexibilidade
