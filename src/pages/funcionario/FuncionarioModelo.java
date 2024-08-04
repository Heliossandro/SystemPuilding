package src.pages.funcionario;

import src.components.utils.StringBufferModelo;
import java.io.Serializable;

public class FuncionarioModelo implements Serializable {
    public static final String CARGO_ADM = "funcionarioADM";
    public static final String CARGO_GERAL = "funcionarioGeral";

    private int id;
    private StringBufferModelo nome, senha, cargo;

    public FuncionarioModelo(int id, String nome, String senha, String cargo){
        this.id = id;
        this.nome = new StringBufferModelo(nome);
        this.senha = senha != null ? new StringBufferModelo(senha) : new StringBufferModelo("");
        this.cargo = new StringBufferModelo(cargo);
    }

    public int getId(){
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome(){
        return nome.get();
    }
 
    public String getSenha(){
        return senha.get();
    }

    public String getCargo() {
        return cargo.get();
    }

    public void setNome(String nome){
        this.nome.set(nome);
    }

    public void setSenha(String senha){
        this.senha.set(senha);
    }

    public void setCargo(String cargo) {
        this.cargo.set(cargo);
    }

    public String toString(){
        return "Nome: " + nome + " - Senha: " + senha + " - Cargo: " + cargo;
    }
}
