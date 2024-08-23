package src.dao;



import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import src.models.ArtigoModelo;

public class ArtigoDAO {
    private static final String FILE_NAME = "artigos.dat";

    public void save(ArtigoModelo artigo) {
        List<ArtigoModelo> artigos = getAll();
        boolean found = false;
        
        for (int i = 0; i < artigos.size(); i++) {
            if (artigos.get(i).getId() == artigo.getId()) {
                artigos.set(i, artigo);
                found = true;
                break;
            }
        }
        
        if (!found) {
            artigos.add(artigo);
        }
        
        saveToFile(artigos);
    }

    public void delete(int id) {
        List<ArtigoModelo> artigos = getAll();
        artigos.removeIf(artigo -> artigo.getId() == id);
        saveToFile(artigos);
    }

    public ArtigoModelo get(int id) {
        List<ArtigoModelo> artigos = getAll();
        for (ArtigoModelo artigo : artigos) {
            if (artigo.getId() == id) {
                return artigo;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public List<ArtigoModelo> getAll() {
        List<ArtigoModelo> artigos = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            artigos = (List<ArtigoModelo>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
        }
        return artigos;
    }

    private void saveToFile(List<ArtigoModelo> artigos) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(artigos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArtigoModelo findByName(String nome) {
        List<ArtigoModelo> artigos = getAll();
        for (ArtigoModelo artigo : artigos) {
            if (artigo.getNome().equalsIgnoreCase(nome)) {
                return artigo;
            }
        }
        return null;
    }
}