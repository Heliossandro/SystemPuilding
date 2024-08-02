package src.pages.veiculo;

import src.components.utils.StringBufferModelo;

public class VeiculoModelo {
    private int id;
    private StringBufferModelo veiculo, marca, matricula, cor;

    public VeiculoModelo(int id, String veiculo, String marca, String matricula, String cor){
        this.id = id;
        this.veiculo = new StringBufferModelo(veiculo);
        this.marca = new StringBufferModelo(marca);
        this.matricula = new StringBufferModelo(matricula);
        this.cor = new StringBufferModelo(cor);
    }

            //metodos get

    public int getId(){
        return id;
    }

    public String getVeiculo(){
        return veiculo.get();
    }

    public String getMarca(){
        return marca.get();
    }

    public String getMatricula(){
        return matricula.get();
    }

    public String getCor(){
        return  cor.get();
    }
    
    //metodos Set
    
    public void setId(int id){
        this.id = id;
    }

    public void setVeiculo(String veiculo){
        this.veiculo.set(veiculo);
    }

    public void setMarca(String marca){
        this.marca.set(marca);
    }

    public void setMatricula(String matricula){
        this.matricula.set(matricula);
    }

    public void setCor(String cor){
        this.cor.set(cor);
    }

}

/* VeiculoModelo
- int id
- String veiculo
- String marca
- String matricula
- String cor */