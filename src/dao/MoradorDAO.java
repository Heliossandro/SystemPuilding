package src.dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

import src.models.MoradorModelo;

public class MoradorDAO {
    private static final String FILE_NAME = "moradores.dat";

    public void save(MoradorModelo morador) {
        List<MoradorModelo> moradores = getAll();
        boolean found = false;
        
        for (int i = 0; i < moradores.size(); i++) {
            if (moradores.get(i).getId() == morador.getId()) {
                moradores.set(i, morador);
                found = true;
                break;
            }
        }
        
        if (!found) {
            moradores.add(morador);
        }
        
        saveToFile(moradores);
    }

    public void delete(int id) {
        List<MoradorModelo> moradores = getAll();
        moradores.removeIf(morador -> morador.getId() == id);
        saveToFile(moradores);
    }

    public MoradorModelo get(int id) {
        List<MoradorModelo> moradores = getAll();
        for (MoradorModelo morador : moradores) {
            if (morador.getId() == id) {
                return morador;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public List<MoradorModelo> getAll() {
        List<MoradorModelo> moradores = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            moradores = (List<MoradorModelo>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Arquivo não encontrado");
        }
        return moradores;
    }

    private void saveToFile(List<MoradorModelo> moradores) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(moradores);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
