package src.dao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import src.models.FuncionarioModelo;

public class FuncionarioDAO {
    private static final String FILE_NAME = "funcionarios.dat";

    public void save(FuncionarioModelo funcionario) {
        List<FuncionarioModelo> funcionarios = getAll();
        boolean found = false;
        for (int i = 0; i < funcionarios.size(); i++) {
            if (funcionarios.get(i).getId() == funcionario.getId()) {
                funcionarios.set(i, funcionario);
                found = true;
                break;
            }
        }
        if (!found) {
            funcionarios.add(funcionario);
        }
        saveToFile(funcionarios);
    }

    public FuncionarioModelo get(int id) {
        List<FuncionarioModelo> funcionarios = getAll();
        for (FuncionarioModelo funcionario : funcionarios) {
            if (funcionario.getId() == id) {
                return funcionario;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public List<FuncionarioModelo> getAll() {
        List<FuncionarioModelo> funcionarios = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            funcionarios = (List<FuncionarioModelo>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // Arquivo não encontrado ou vazio
        }
        return funcionarios;
    }

    public void delete(int id) {
        List<FuncionarioModelo> funcionarios = getAll();
        funcionarios.removeIf(funcionario -> funcionario.getId() == id);
        saveToFile(funcionarios);
    }

    private void saveToFile(List<FuncionarioModelo> funcionarios) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(funcionarios);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
