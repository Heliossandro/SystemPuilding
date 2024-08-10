package src.models;

import java.io.Serializable;

import src.components.utils.StringBufferModelo;

public class ApartamentoModelo implements Serializable {
    private int id, numApartamento, andar;
    private StringBufferModelo estado;

    public ApartamentoModelo(int id, int numApartamento, int andar, String estado){
        this.id = id;
        this.numApartamento = numApartamento;
        this.andar = andar;
        this.estado = new StringBufferModelo(estado);
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

    public String toString(){
        return "Numero do apartamento: "+ numApartamento +"\n"+
               "Numero do andar: "+ andar +"\n"+
               "Estado do apartamento: "+ estado +"\n";
    }
}

/* ApartamentosModelo
- int id
- int numApartamento
- Andar andar */