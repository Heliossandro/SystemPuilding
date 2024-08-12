package src.models;

import java.io.Serializable;
import src.components.utils.StringBufferModelo;

public class MoradorModelo implements Serializable {
    private int id;
    private StringBufferModelo nome, proprietario, numTelefone, dataDeNascimento;
    private char genero;
    private ApartamentoModelo apartamento; // Relacionamento com ApartamentoModelo

    public MoradorModelo(int id, String nome, String proprietario, String numTelefone, String dataDeNascimento, char genero, ApartamentoModelo apartamento){
        this.id = id;
        this.nome = new StringBufferModelo(nome);
        this.proprietario = new StringBufferModelo(proprietario);
        this.numTelefone = numTelefone != null ? new StringBufferModelo(numTelefone) : new StringBufferModelo(numTelefone);
        this.dataDeNascimento = new StringBufferModelo(dataDeNascimento);
        this.genero = genero;
        this.apartamento = apartamento; // Inicialização do apartamento
    }

    // Métodos get
    public int getId(){
        return id;
    }

    public String getNome(){
        return nome.get();
    }
    
    public String getProprietario(){
        return proprietario.get();
    }

    public String getNumTelefone(){
        return numTelefone.get();
    }
    
    public String getDataDeNascimento(){
        return dataDeNascimento.get();
    }

    public char getGenero(){
        return genero;
    }

    public ApartamentoModelo getApartamento() {
        return apartamento;
    }
    
    // Métodos set
    public void setId(int id){
        this.id = id;
    }

    public void setNome(String nome){
        this.nome.set(nome);
    }

    public void setProprietario(String proprietario){
        this.proprietario.set(proprietario);
    }

    public void setNumTelefone(String numTelefone){
        this.numTelefone.set(numTelefone);
    }

    public void setDataDeNascimento(String dataDeNascimento){
        this.dataDeNascimento.set(dataDeNascimento);
    }

    public void setGenero(char genero){
        this.genero = genero;
    }

    public void setApartamento(ApartamentoModelo apartamento) {
        this.apartamento = apartamento;
    }

    @Override
    public String toString(){
        return "Nome: " + nome + "\n" +
               "Proprietário: " + proprietario + "\n" +
               "Número de Telefone: " + numTelefone + "\n" +
               "Data de Nascimento: " + dataDeNascimento + "\n" +
               "Gênero: " + genero + "\n" +
               "Apartamento: " + (apartamento != null ? apartamento.getNumApartamento() : "Sem apartamento");
    }
}
