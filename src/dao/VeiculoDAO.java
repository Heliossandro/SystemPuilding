package src.dao;

import src.pages.veiculo.*;

import java.io.*;
import java.util.*;
import javax.swing.*;

public class VeiculoDAO {
    private static final String FILE_NAME = "veiculos.dat";

    public void save(VeiculoModelo veiculo){
        List<VeiculoModelo> veiculos = getAll();
        boolean found = false;

        for(int i = 0; i < veiculos.size(); i++){
            if(veiculos.get(i).getId() == veiculo.getId()){
                veiculos.set(i, veiculo);
                found = true;
                break;
            }
        }
        if(!found){
            veiculos.add(veiculo);
        }
        saveToFile(veiculos);
    }
    public VeiculoModelo get(int id){
        List<VeiculoModelo> veiculos = getAll();
        for (VeiculoModelo veiculo : veiculos){
            if(veiculo.getId() == id){
                return veiculo;
            }
            
        }
        return null;
    }    

    @SuppressWarnings("unchecked")
    public List<VeiculoModelo> getAll(){
        List<VeiculoModelo> veiculos = new ArrayList<>();
        try (ObjectInputStream ios = new ObjectInputStream(new FileInputStream(FILE_NAME))){
            veiculos = (List<VeiculoModelo>) ios.readObject();
        }catch(IOException | ClassNotFoundException e){
            JOptionPane.showMessageDialog(null, "ERROR!");
        }
        return veiculos;
    }

    private void saveToFile(List<VeiculoModelo> veiculos){
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))){
            oos.writeObject(veiculos);;
        }catch( IOException e){
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unused")
    private void delete(int id){
        List<VeiculoModelo> veiculos = getAll();
        veiculos.removeIf(veiculo -> veiculo.getId() == id);
        saveToFile(veiculos);
    }
}
