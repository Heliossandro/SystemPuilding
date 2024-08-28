package src.models;

import java.io.Serializable;

import src.components.utils.StringBufferModelo;

public class PagamentoModelo implements Serializable{
    private static final long serialVersionUID = 7915799766965423151L;

    private int id;
    private ApartamentoModelo apartamento;
    private StringBufferModelo meses, pagamento, dataPagamento;
    private FuncionarioModelo funcionario;

    public PagamentoModelo(int id, ApartamentoModelo apartamento, String meses, String pagamento,String dataPagamento, FuncionarioModelo funcionario){
        this.id = id;
        this.apartamento = apartamento;
        this.meses = new StringBufferModelo(meses);
        this.pagamento = new StringBufferModelo(pagamento);
        this.dataPagamento = new StringBufferModelo(dataPagamento);
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

    public String getPagamento(){
        return pagamento.get();
    }

    public void setPagamento(String pagamento){
        this.pagamento.set(pagamento);
    }

    public String getDataPagamento(){
        return dataPagamento.get();
    }

    public void setDataPagamento(String dataPagamento){
        this.dataPagamento.set(dataPagamento);
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
