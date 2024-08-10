package src.dao;

import src.models.ApartamentoModelo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

public class ApartamentoDAO {
    private static final String FILE_NAME = "apartamentos.dat";

    public void save(ApartamentoModelo apartamento){
        List<ApartamentoModelo> apartamentos = getAll();
        boolean found = false;
        for(int i = 0 ; i < apartamentos.size() ; i++){
            if(apartamentos.get(i).getId() == apartamento.getId()){
                apartamentos.set(i, apartamento);
                found = true;
                break;
            }
        }
        if(!found){
            apartamentos.add(apartamento);
        }
        saveToFile(apartamentos);
    }    

    public ApartamentoModelo get(int id) {
        List<ApartamentoModelo> apartamentos = getAll();
        for (ApartamentoModelo apartamento : apartamentos) {
            if (apartamento.getId() == id) {
                return apartamento;
            }
        }
        return null;
    }

    public void delete(int id) {
        List<ApartamentoModelo> apartamentos = getAll();
        apartamentos.removeIf(apartamento -> apartamento.getId() == id);
        saveToFile(apartamentos);
    }

     @SuppressWarnings("unchecked")
    public List<ApartamentoModelo> getAll() {
        List<ApartamentoModelo> apartamentos = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            apartamentos = (List<ApartamentoModelo>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // Arquivo não encontrado ou vazio
        }
        return apartamentos;
    }

    private void saveToFile(List<ApartamentoModelo> apartamentos) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(apartamentos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
