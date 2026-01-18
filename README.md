# Java Cache Caffeine

## Classe User

A classe User representa a entidade de domínio da aplicação.
Ela modela um usuário simples, contendo apenas os atributos necessários para o exemplo de cache.

Responsabilidades:
* Representar os dados de um usuário
* Servir como objeto retornado pelo cache e pelo repositório

Campos principais:
* id — Identificador único do usuário
* nome — Nome do usuário

Esta classe não possui lógica de negócio, sendo apenas um POJO (Plain Old Java Object).

## Classe UserRepository

A classe UserRepository simula uma camada de acesso a dados (DAO / Repository).

Neste projeto, ela representa uma operação custosa, como:
* Consulta a banco de dados
* Chamada a serviço externo
* Acesso a API remota

Responsabilidades:
* Buscar um usuário pelo seu identificador
* Simular latência para evidenciar o ganho de performance com cache

Comportamento importante:
* Cada chamada ao método buscarPorId introduz um atraso artificial
* Esse atraso permite comparar claramente o tempo de resposta com e sem cache

Em um cenário real, esta classe seria responsável por acessar o banco de dados ou outro serviço persistente.

## Classe UserCache

A classe UserCache é responsável por encapsular a lógica de cache em memória, utilizando a biblioteca Caffeine.
Ela atua como uma camada intermediária entre a aplicação e o repositório.

Responsabilidades:
* Gerenciar o cache de usuários
* Evitar chamadas repetidas ao repositório
* Controlar expiração e tamanho do cache

Características do cache:
* Cache do tipo LoadingCache
* Chave: Integer (ID do usuário)
* Valor: User
* Expiração: 5 minutos após a escrita
* Tamanho máximo: 1.000 entradas

Funcionamento:
* Se o usuário existir no cache, o valor é retornado imediatamente
* Caso contrário, o cache invoca automaticamente o UserRepository para carregar o dado

Esta abordagem melhora significativamente a performance e simplifica o código, eliminando verificações manuais de cache.