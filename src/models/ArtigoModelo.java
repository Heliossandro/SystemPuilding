package src.models;

import java.io.Serializable;

import src.components.utils.StringBufferModelo;

public class ArtigoModelo  implements Serializable{
    private int id;
    private double preco;
    private StringBufferModelo nome, dataCompra, estado, observacao;

    public ArtigoModelo(int id, double preco, String nome, String dataCompra, String estado, String observacao){
        this.id = id;
        this.preco = preco;
        this.nome = new StringBufferModelo(nome);
        this.dataCompra = new StringBufferModelo(dataCompra);
        this.estado = new StringBufferModelo(estado);
        this.observacao = observacao != null ? new StringBufferModelo(observacao) : new StringBufferModelo(observacao);
    }

    public int getId(){
        return id;
    } 

    public double getPreco(){
        return preco;
    }

    public String getNome(){
        return nome.get();
    }
    
    public String getDataCompra(){
        return dataCompra.get();
    }

    public String getEstado(){
        return estado.get();
    }
    
    public String getObservacao(){
        return observacao.get();
    }

    public void setId(int id){
        this.id = id;
    }

    public void setPreco(double preco){
        this.preco = preco;
    }

    public void setNome(String nome){
        this.nome.set(nome);
    }
    
    public void setDataCompra(String dataCompra){
        this.dataCompra.set(dataCompra);
    }
    
    public void setEstado(String estado){
        this.estado.set(estado);
    }
    
    public void setObservacao(String observacao){
        this.observacao.set(observacao);
    }

    public String toString(){
        return " Nome: "+ nome +"\n"+
               " >> preco: "+ preco +"\n"+
               " >> Estado: "+ estado +"\n"+
               " >> Observacao: "+ observacao +"\n";
    }
    
}
