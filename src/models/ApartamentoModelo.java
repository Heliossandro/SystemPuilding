package src.models;

import java.io.Serializable;

import src.components.utils.StringBufferModelo;

public class ApartamentoModelo implements Serializable {
    private int id, numApartamento, andar;
    private StringBufferModelo estado, vagaEstacionamento;

    public ApartamentoModelo(int id, int numApartamento, int andar, String estado, String vagaEstacionamento){
        this.id = id;
        this.numApartamento = numApartamento;
        this.andar = andar;
        this.estado = new StringBufferModelo(estado);
        this.vagaEstacionamento = new StringBufferModelo(vagaEstacionamento);
    }

    
    public int getId(){
        return id;
    }
    
    public int getNumApartamento(){
        return numApartamento;
    }
    
    public int getAndar(){
        return andar;
    }
    
    public String getEstado(){
        return estado.get();
    } 

    public String getVagaEstacionamento(){
        return vagaEstacionamento.get();
    }
    
    public void setId(int id){
        this.id = id;
    }

    public void setNumApartamento(int numApartamento){
        this.numApartamento = numApartamento;
    }

    public void setAndar(int andar){
        this.andar = andar;
    }

    public void setEstado(String estado){
        this.estado.set(estado);
    }

    public void setVagaEstacionamento(String  vagaEstacionamento){
        this.vagaEstacionamento.set(vagaEstacionamento);
    }
    

    public String toString(){
        return "Apartamento: "+ numApartamento +"\n"+
               " >> andar: "+ andar +"\n"+
               " >> Estado: "+ estado.get() +"\n"+
               " >> Estacionamento: "+ vagaEstacionamento.get() +"\n";
    }
}
