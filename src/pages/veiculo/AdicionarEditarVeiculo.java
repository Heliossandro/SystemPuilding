package src.pages.veiculo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import src.models.VeiculoModelo;
import src.dao.VeiculoDAO;
import src.models.MoradorModelo;
import src.dao.MoradorDAO;
import java.util.List;

public class AdicionarEditarVeiculo extends JDialog {
    private JTextField veiculoField;
    private JTextField marcaField;
    private JTextField matriculaField;
    private JTextField corField;
    private JComboBox<String> moradorComboBox;
    private JButton saveButton;
    private VeiculoDAO veiculoDAO;
    private VeiculoModelo veiculo;
    private int ultimoId = 0;

    public AdicionarEditarVeiculo(Frame parent, VeiculoModelo veiculo) {
        super(parent, true);
        setTitle(veiculo == null ? "Adicionar Veículo" : "Editar Veículo");
        setSize(400, 300);
        setLocationRelativeTo(parent);

        veiculoDAO = new VeiculoDAO();
        this.veiculo = veiculo;

        atualizarUltimoId();  // Atualiza o ID para criação de novo veículo

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Veículo:"));
        veiculoField = new JTextField(20);
        panel.add(veiculoField);

        panel.add(new JLabel("Marca:"));
        marcaField = new JTextField(20);
        panel.add(marcaField);

        panel.add(new JLabel("Matrícula:"));
        matriculaField = new JTextField(20);
        panel.add(matriculaField);

        panel.add(new JLabel("Cor:"));
        corField = new JTextField(20);
        panel.add(corField);

        panel.add(new JLabel("Morador:"));
        moradorComboBox = new JComboBox<>();
        carregarMoradores();
        panel.add(moradorComboBox);

        saveButton = new JButton("Salvar");
        panel.add(saveButton);

        if (veiculo != null) {
            preencherCampos();
        }

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarVeiculo();
            }
        });

        add(panel);
    }

    private void carregarMoradores() {
        MoradorDAO moradorDAO = new MoradorDAO();
        for (MoradorModelo morador : moradorDAO.getAll()) {
            moradorComboBox.addItem(morador.getNome());
        }
    }    
    
    private void atualizarUltimoId() {
        List<VeiculoModelo> veiculos = veiculoDAO.getAll();
        for (VeiculoModelo veiculo : veiculos) {
            if (veiculo.getId() > ultimoId) {
                ultimoId = veiculo.getId();
            }
        }
        ultimoId++;  // Incrementa o ID para o novo veículo
    }

    private void preencherCampos() {
        veiculoField.setText(veiculo.getVeiculo());
        marcaField.setText(veiculo.getMarca());
        matriculaField.setText(veiculo.getMatricula());
        corField.setText(veiculo.getCor());
        moradorComboBox.setSelectedItem(veiculo.getMorador());
    }

    private void salvarVeiculo() {
        String veiculoNome = veiculoField.getText();
        String marca = marcaField.getText();
        String matricula = matriculaField.getText();
        String cor = corField.getText();
        String moradorNome = (String) moradorComboBox.getSelectedItem();

        MoradorDAO moradorDAO = new MoradorDAO();
        MoradorModelo morador = moradorDAO.findByName(moradorNome);  // Supondo que há um método para buscar por nome

        if (veiculo == null) {
            veiculo = new VeiculoModelo(ultimoId, veiculoNome, marca, matricula, cor, morador);
            veiculoDAO.save(veiculo);
        } else {
            veiculo.setVeiculo(veiculoNome);
            veiculo.setMarca(marca);
            veiculo.setMatricula(matricula);
            veiculo.setCor(cor);
            veiculo.setMorador(morador);
            veiculoDAO.update(veiculo);
        }
        dispose();
    }
}
