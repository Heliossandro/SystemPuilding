package src.pages.morador;

import src.dao.MoradorDAO;
import src.dao.ApartamentoDAO;
import src.models.MoradorModelo;
import src.models.ApartamentoModelo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AdicionarEditarMorador extends JDialog {
    private JTextField nomeField, numTelefoneField, dataDeNascimentoField;
    private JRadioButton mOp = new JRadioButton("Masculino"),
                         fOp = new JRadioButton("Feminino"),
                         simOp = new JRadioButton("Sim"),
                         naoOp = new JRadioButton("Não");
    private JComboBox<ApartamentoModelo> apartamentoComboBox;
    private JButton saveButton, cancelButton;
    private int ultimoId = 0;
    private List<MoradorModelo> moradores;
    private List<ApartamentoModelo> apartamentosDisponiveis;

    private MoradorDAO moradorDAO;
    private ApartamentoDAO apartamentoDAO;
    private MoradorModelo morador;

    public AdicionarEditarMorador(Frame parent, MoradorModelo morador) {
        super(parent, true);
        setTitle(morador == null ? "Adicionar Morador" : "Editar Morador");
        setSize(700, 400);
        setLocationRelativeTo(parent);

        this.moradorDAO = new MoradorDAO();
        this.apartamentoDAO = new ApartamentoDAO();
        this.morador = morador;
        this.moradores = moradorDAO.getAll(); // Inicializa a lista de moradores com os dados do DAO
        this.apartamentosDisponiveis = apartamentoDAO.getAll(); // Carrega todos os apartamentos disponíveis
        atualizarUltimoId(); // Atualiza o último ID baseado nos moradores existentes

        nomeField = new JTextField(20);
        numTelefoneField = new JTextField(15);
        dataDeNascimentoField = new JTextField(10);

        apartamentoComboBox = new JComboBox<>(apartamentosDisponiveis.toArray(new ApartamentoModelo[0]));

        // Grupos de botões de rádio
        ButtonGroup sexoGroup = new ButtonGroup();
        sexoGroup.add(mOp);
        sexoGroup.add(fOp);

        ButtonGroup proprietarioGroup = new ButtonGroup();
        proprietarioGroup.add(simOp);
        proprietarioGroup.add(naoOp);

        if (morador != null) {
            nomeField.setText(morador.getNome());
            numTelefoneField.setText(morador.getNumTelefone());
            dataDeNascimentoField.setText(morador.getDataDeNascimento());
            if (morador.getGenero() == 'M') {
                mOp.setSelected(true);
            } else {
                fOp.setSelected(true);
            }
            if ("Sim".equals(morador.getProprietario())) {
                simOp.setSelected(true);
            } else {
                naoOp.setSelected(true);
            }
            // Seleciona o apartamento associado ao morador
            apartamentoComboBox.setSelectedItem(morador.getApartamento().getNumApartamento());
        }

        JPanel panel = new JPanel(new GridLayout(7, 2));
        panel.add(new JLabel("Nome:"));
        panel.add(nomeField);
        panel.add(new JLabel("Telefone:"));
        panel.add(numTelefoneField);
        panel.add(new JLabel("Data de Nascimento:"));
        panel.add(dataDeNascimentoField);
        panel.add(new JLabel("Sexo:"));
        JPanel sexoPanel = new JPanel();
        sexoPanel.add(mOp);
        sexoPanel.add(fOp);
        panel.add(sexoPanel);
        panel.add(new JLabel("Proprietário:"));
        JPanel proprietarioPanel = new JPanel();
        proprietarioPanel.add(simOp);
        proprietarioPanel.add(naoOp);
        panel.add(proprietarioPanel);
        panel.add(new JLabel("Apartamento:"));
        panel.add(apartamentoComboBox);

        saveButton = new JButton("Salvar");
        cancelButton = new JButton("Cancelar");

        panel.add(saveButton);
        panel.add(cancelButton);

        add(panel, BorderLayout.CENTER);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarMorador();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void atualizarUltimoId() {
        for (MoradorModelo morador : moradores) {
            if (morador.getId() > ultimoId) {
                ultimoId = morador.getId();
            }
        }
        ultimoId++; // Incrementa o último ID para garantir que o próximo ID seja único
    }

    private void salvarMorador() {
        String nome = nomeField.getText();
        String telefone = numTelefoneField.getText();
        String dataDeNascimento = dataDeNascimentoField.getText();
        char genero = mOp.isSelected() ? 'M' : 'F';
        String proprietario = simOp.isSelected() ? "Sim" : "Não";
        ApartamentoModelo apartamentoSelecionado = (ApartamentoModelo) apartamentoComboBox.getSelectedItem();

        if (nome.trim().isEmpty() || telefone.trim().isEmpty() || dataDeNascimento.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.");
            return;
        }

        if (morador == null) {
            morador = new MoradorModelo(ultimoId, nome, proprietario, telefone, dataDeNascimento, genero, apartamentoSelecionado);
        } else {
            morador.setNome(nome);
            morador.setNumTelefone(telefone);
            morador.setDataDeNascimento(dataDeNascimento);
            morador.setGenero(genero);
            morador.setProprietario(proprietario);
            morador.setApartamento(apartamentoSelecionado);
        }

        moradorDAO.save(morador);
        JOptionPane.showMessageDialog(this, "Morador salvo com sucesso!");
        dispose();
    }

    public static void main(String[] args) {
        // Testando a interface gráfica
        SwingUtilities.invokeLater(() -> new AdicionarEditarMorador(null, null).setVisible(true));
    }
}
