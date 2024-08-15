/*------------------------------------
Tema: Gestão de um predio
Nome: Heliossandro Afonso
Numero: 947024057
Ficheiro: Analise.java
Data: 19.05.2024
--------------------------------------*/

/*
1. Objectivo: cria um sistema, capaz de lidar com os problemas de um predio como, o fluxo de moradores, fluxo de trabalhadores, permitido a melhora da entrada e saida de dados.

2. Visao [Interfaces Graficas]
 - Login   
 - MenuPrincipal
 - MoradoresInterface FEITO
 - FuncionariosInterface FEITO
 - PagamentosInterface
 - ArtigosInterface
 - VisitaInterface
 - VeiculoModelo FEITO

3. Entidades Fortes e Seus Atributos (Modelo)
  MoradoresModelo
  - int id
  - String nome
  - String proprietario (sim, nao)
  - String numTelefone
  - char genero
  - String dataDeNascimento
  - ApartamentosModelo apartamento

  vagaDeEstacionamento
  - int id
  - int numVaga
  - String ocupado (sim, nao)
  - VeiculoModelo veiculo

  VeiculoModelo
  - int id
  - String veiculo
  - String marca
  - String matricula
  - String cor
  - MoradoresModelo morador

   ApartamentosModelo
   - int id
   - int numApartamento
   - Andar andar
   - String Estado (Arrendado, comprado, Não usado)
   - String vagaDeEstacionamentoModelo (Disponivel, indisponivel)

   PagamentosModelo
   - int id
   - ApartamentosModelo apartamento
   - Meses meses
   - FuncionariosModelo funcionarios

   FuncionariosModelo
   - int id
   - String nome
   - String senha
   - String numTelefone
   - char genero
   - Documento documento

   VisitaModelo
   - int id
   - String nome
   - ApartamentoModelo apartamento
   - String horaEntrada
   - String horaSaida

   ArtigosModelo
   - int id
   - double preco
   - String nomeArtigo
   - String dataDeCompra
   - String estado (novo, semi-novo, estragado)

4. Ficheiro

5. Tabelas de Apoio (Auxiliares) = Entidades Fracas
  - Andar.tab 
  - Meses.tab 
  - tipoDePagamento.tab
  - MetodoDePagamento.tab
  - Documento.tab

6. Listagens e Pesquisas

*/