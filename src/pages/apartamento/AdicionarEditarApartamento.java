package src.pages.apartamento;

import src.dao.ApartamentoDAO;
import src.models.ApartamentoModelo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AdicionarEditarApartamento extends JDialog {
    private JTextField numApartamentoField, andarField;
    private JComboBox<String> estadoComboBox, vagaEstacionamentoComboBox;
    private JButton saveButton, cancelButton;
    private int ultimoId = 0;
    private List<ApartamentoModelo> apartamentos;

    private ApartamentoDAO apartamentoDAO;
    private ApartamentoModelo apartamento;

    public AdicionarEditarApartamento(Frame parent, ApartamentoModelo apartamento) {
        super(parent, true);
        setTitle(apartamento == null ? "Adicionar Apartamento" : "Editar Apartamento");
        setSize(300, 200);
        setLocationRelativeTo(parent);

        this.apartamentoDAO = new ApartamentoDAO();
        this.apartamento = apartamento;
        
        // Inicializando a lista de apartamentos
        this.apartamentos = apartamentoDAO.getAll(); 

        atualizarUltimoId();

        numApartamentoField = new JTextField(20);
        andarField = new JTextField(20);

        // Criando a JComboBox para o estado
        estadoComboBox = new JComboBox<>(new String[]{"Arrendado", "Comprado", "Não usado"});

         // Criando a JComboBox para o estado
         vagaEstacionamentoComboBox = new JComboBox<>(new String[]{"Disponivel", "Indisponivel"});

        if (apartamento != null) {
            numApartamentoField.setText(String.valueOf(apartamento.getNumApartamento()));
            andarField.setText(String.valueOf(apartamento.getAndar()));
            estadoComboBox.setSelectedItem(apartamento.getEstado());
            vagaEstacionamentoComboBox.setSelectedItem(apartamento.getVagaEstacionamento());
        }

        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("Número do Apartamento:"));
        panel.add(numApartamentoField);
        panel.add(new JLabel("Andar:"));
        panel.add(andarField);
        panel.add(new JLabel("Estado:"));
        panel.add(estadoComboBox);
        panel.add(new Label("Vaga de Estacionamento: "));
        panel.add(vagaEstacionamentoComboBox);

        saveButton = new JButton("Salvar");
        cancelButton = new JButton("Cancelar");

        panel.add(saveButton);
        panel.add(cancelButton);

        add(panel, BorderLayout.CENTER);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarApartamento();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    public void atualizarUltimoId() {
        for (ApartamentoModelo apartamento : apartamentos) {
            if (apartamento.getId() > ultimoId) {
                ultimoId = apartamento.getId();
            }
        }
        ultimoId++;
    }

    private void salvarApartamento() {
        int numApartamento = Integer.parseInt(numApartamentoField.getText());
        int andar = Integer.parseInt(andarField.getText());
        String estado = (String) estadoComboBox.getSelectedItem();
        String vagaEstacionamento = (String) vagaEstacionamentoComboBox.getSelectedItem();

        if (estado == null || estado.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "O estado do apartamento deve ser selecionado.");
            return;
        }

        if (apartamento == null) {
            apartamento = new ApartamentoModelo(ultimoId, numApartamento, andar, estado, vagaEstacionamento);
        } else {
            apartamento.setNumApartamento(numApartamento);
            apartamento.setAndar(andar);
            apartamento.setEstado(estado);
            apartamento.setVagaEstacionamento(vagaEstacionamento);
        }

        apartamentoDAO.save(apartamento);
        JOptionPane.showMessageDialog(this, "Apartamento salvo com sucesso!");
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdicionarEditarApartamento(null, null).setVisible(true));
    }
}
