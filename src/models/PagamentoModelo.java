package src.models;

import src.components.utils.StringBufferModelo;

public class PagamentoModelo {
    private int id;
    private ApartamentoModelo apartamento;
    private StringBufferModelo meses;
    private FuncionarioModelo funcionario;

    public PagamentoModelo(int id, ApartamentoModelo apartamento, String meses, FuncionarioModelo funcionario){
        this.id = id;
        this.apartamento = apartamento;
        this.meses = new StringBufferModelo(meses);
        this.funcionario = funcionario;
    }

    public int getId(){
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ApartamentoModelo getApartamento(){
        return apartamento;
    }

    public void setApartamento(ApartamentoModelo apartamento){
        this.apartamento = apartamento;
    }

    public String getMeses(){
        return meses.get();
    }

    public void setMeses(String meses){
        this.meses.set(meses);
    }

    public FuncionarioModelo getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(FuncionarioModelo funcionario) {
        this.funcionario = funcionario;
    }

    @Override
    public String toString(){
        return "Apartamento: " + getApartamento() + " - Meses: " + getMeses() + " - Funcionario: " + getFuncionario();
    }
}
