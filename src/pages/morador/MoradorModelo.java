package src.pages.morador;

import src.components.utils.StringBufferModelo;

public class MoradorModelo {
    private int id;
    private StringBufferModelo nome, proprietario, numTelefone, dataDeNascimento;
    private char genero;

    public MoradorModelo(int id, String nome, String proprietario, String numTelefone, String dataDeNascimento, char genero){
        this.id = id;
        this.nome = new StringBufferModelo(nome);
        this.proprietario = new StringBufferModelo(proprietario);
        this.numTelefone = numTelefone != null ? new StringBufferModelo(numTelefone) : new StringBufferModelo(numTelefone);
        this.dataDeNascimento = new StringBufferModelo(dataDeNascimento);
        this.genero = genero;
    }

    //Mrtodos get
    public int getId(){
        return id;
    }

    public String nome(){
        return nome.get();
    }

    
    public String proprietario(){
        return proprietario.get();
    }

    public String numTelefone(){
        return numTelefone.get();
    }
    
    public String dataDeNascimento(){
        return dataDeNascimento.get();
    }

    public char genero(){
        return genero;
    }
    
    //metodos set
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
}