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
 - MoradoresInterface
 - FuncionariosInterface
 - PagamentosInterface
 - ArtigosInterface

3. Entidades Fortes e Seus Atributos (Modelo)
  MoradoresModelo
  - int id
  - String nome
  - String proprietario
  - char genero
  - Date dataDeNascimento

   ApartamentosModelo
   - int id
   - int numApartamento
   - MoradoresModelo morador
   - Andar andar

   PagamentosModelo
   - int id
   - ApartamentosModelo apartamento
   - Meses meses
   - FuncionariosModelo funcionarios

4. Ficheiro

5. Tabelas de Apoio (Auxiliares) = Entidades Fracas
  - Andar.tab 
  - Meses.tab

6. Listagens e Pesquisas

*/