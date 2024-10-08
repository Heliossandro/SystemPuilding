package src.dao;

import src.models.VeiculoModelo;

import java.io.*;
import java.util.*;

public class VeiculoDAO {
    private static final String FILE_NAME = "veiculos.dat";

    public void save(VeiculoModelo veiculo) {
        List<VeiculoModelo> veiculos = getAll();
        veiculos.add(veiculo);
        saveToFile(veiculos);
    }

    public void update(VeiculoModelo veiculoAtualizado) {
        List<VeiculoModelo> veiculos = getAll();

        for (int i = 0; i < veiculos.size(); i++) {
            if (veiculos.get(i).getId() == veiculoAtualizado.getId()) {
                veiculos.set(i, veiculoAtualizado);
                break;
            }
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
    public void delete(int id){
        List<VeiculoModelo> veiculos = getAll();
        veiculos.removeIf(veiculo -> veiculo.getId() == id);
        saveToFile(veiculos);
    }
}
