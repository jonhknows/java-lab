# java-lab
Testes/Padrões/Melhorias/Performance/Profiling/Refatoração/Novidades...


# 1. Threads
# 2. Generics
# 3. Reflexão(Reflection)
- Reflexão é a capacidade de uma aplicação Java inspecionar e modificar sua própria estrutura e comportamento em tempo de execução. Com a API de Reflection (pacote java.lang.reflect), é possível acessar informações sobre classes, métodos, campos e construtores, além de invocá-los dinamicamente.

## 3.1. Principais usos da Reflexão
- Inspecionar classes, interfaces, campos, métodos e construtores.
- Instanciar objetos dinamicamente.
- Invocar métodos ou acessar campos em tempo de execução.
- Usado em frameworks como Spring, Hibernate e JUnit para configurações dinâmicas.

## 3.2. Como funciona?
A classe java.lang.Class é o ponto de entrada para a reflexão. Você pode obter uma instância de Class de três maneiras:

- Classe.class (ex.: String.class)
- objeto.getClass() (ex.: "texto".getClass())
- Class.forName("nome.da.Classe")

## Ex.: link

# 4. Anotação
