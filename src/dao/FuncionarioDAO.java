package src.dao;

import src.pages.funcionario.FuncionarioModelo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {
    private static final String FILE_NAME = "funcionario.dat";

    @SuppressWarnings("unchecked")
    public List<FuncionarioModelo> getAll() {
        List<FuncionarioModelo> funcionarios = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            funcionarios = (List<FuncionarioModelo>) ois.readObject();
        } catch (FileNotFoundException e) {
            // Arquivo não encontrado, retornando lista vazia
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return funcionarios;
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

    public void save(FuncionarioModelo funcionario) {
        List<FuncionarioModelo> funcionarios = getAll();
        boolean exists = false;
        for (int i = 0; i < funcionarios.size(); i++) {
            if (funcionarios.get(i).getId() == funcionario.getId()) {
                funcionarios.set(i, funcionario);
                exists = true;
                break;
            }
        }
        if (!exists) {
            funcionario.setId(getNextId(funcionarios));
            funcionarios.add(funcionario);
        }
        saveToFile(funcionarios);
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

    private int getNextId(List<FuncionarioModelo> funcionarios) {
        int maxId = 0;
        for (FuncionarioModelo funcionario : funcionarios) {
            if (funcionario.getId() > maxId) {
                maxId = funcionario.getId();
            }
        }
        return maxId + 1;
    }
}
