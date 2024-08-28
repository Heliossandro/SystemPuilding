package src.dao;



import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import src.models.PagamentoModelo;


public class PagamentoDAO {
    private static final String FILE_NAME = "pagamentos.dat";

    public void save(PagamentoModelo pagamento) {
        List<PagamentoModelo> pagamentos = getAll();
        boolean found = false;
        
        for (int i = 0; i < pagamentos.size(); i++) {
            if (pagamentos.get(i).getId() == pagamento.getId()) {
                pagamentos.set(i, pagamento);
                found = true;
                break;
            }
        }
        
        if (!found) {
            pagamentos.add(pagamento);
        }
        
        saveToFile(pagamentos);
    }

    public List<String> getMesesPagos(int numeroApartamento, int ano) {
        List<String> mesesPagos = new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            while (true) {
                PagamentoModelo pagamento = (PagamentoModelo) ois.readObject();
                
                // Verifica se o pagamento foi feito para o apartamento e ano especificados
                if (pagamento.getApartamento().getNumApartamento() == numeroApartamento &&
                    pagamento.getDataPagamento().endsWith(String.valueOf(ano))) {
                    
                    // Adiciona o mês à lista de meses pagos
                    mesesPagos.add(pagamento.getMeses());
                }
            }
        } catch (EOFException eof) {
            // Fim do arquivo
        } catch (FileNotFoundException fnf) {
            System.err.println("Arquivo não encontrado: " + FILE_NAME);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return mesesPagos;
    }

    public void delete(int id) {
        List<PagamentoModelo> pagamentos = getAll();
        pagamentos.removeIf(pagamento -> pagamento.getId() == id);
        saveToFile(pagamentos);
    }

    public PagamentoModelo get(int id) {
        List<PagamentoModelo> pagamentos = getAll();
        for (PagamentoModelo pagamento : pagamentos) {
            if (pagamento.getId() == id) {
                return pagamento;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public List<PagamentoModelo> getAll() {
        List<PagamentoModelo> pagamentos = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            pagamentos = (List<PagamentoModelo>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
        }
        return pagamentos;
    }

    private void saveToFile(List<PagamentoModelo> pagamentos) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(pagamentos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PagamentoModelo findByName(String nome) {
        List<PagamentoModelo> pagamentos = getAll();
        for (PagamentoModelo pagamento : pagamentos) {
            if (String.valueOf(pagamento.getApartamento().getNumApartamento()).equals(nome)) {
                return pagamento;
            }
        }
        return null;
    }
    
    
}