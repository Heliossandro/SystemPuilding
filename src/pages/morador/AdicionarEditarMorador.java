package src.pages.morador;

import src.dao.MoradorDAO;
import src.pages.morador.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdicionarEditarMorador extends JDialog {
    private JTextField nomeField, numTelefoneField, dataDeNascimentoField;
    private JRadioButton mOp = new JRadioButton("Masculino"),
                         fOp = new JRadioButton("Feminino"),
                         simOp = new JRadioButton("Sim"),
                         naoOp = new JRadioButton("Não");
    private JButton saveButton, cancelButton;

    private MoradorDAO moradorDAO;
    private MoradorModelo morador;

    public AdicionarEditarMorador(Frame parent, MoradorModelo morador) {
        super(parent, true);
        setTitle(morador == null ? "Adicionar Morador" : "Editar Morador");
        setSize(400, 300);
        setLocationRelativeTo(parent);

        this.moradorDAO = new MoradorDAO();
        this.morador = morador;

        nomeField = new JTextField(20);
        numTelefoneField = new JTextField(15);
        dataDeNascimentoField = new JTextField(10);

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
        }

        JPanel panel = new JPanel(new GridLayout(6, 2));
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

    private void salvarMorador() {
        String nome = nomeField.getText();
        String telefone = numTelefoneField.getText();
        String dataDeNascimento = dataDeNascimentoField.getText();
        char genero = mOp.isSelected() ? 'M' : 'F';
        String proprietario = simOp.isSelected() ? "Sim" : "Não";

        if (nome.trim().isEmpty() || telefone.trim().isEmpty() || dataDeNascimento.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.");
            return;
        }

        if (morador == null) {
            morador = new MoradorModelo(0, nome, proprietario, dataDeNascimento, telefone, genero);
        } else {
            morador.setNome(nome);
            morador.setNumTelefone(telefone);
            morador.setDataDeNascimento(dataDeNascimento);
            morador.setGenero(genero);
            morador.setProprietario(proprietario);
        }

        moradorDAO.save(morador);
        JOptionPane.showMessageDialog(this, "Morador salvo com sucesso!");
        dispose();
    }

    public static void main(String[] args) {
        // Testando a interface gráfica
        SwingUtilities.invokeLater(() -> new AdicionarEditarMorador(null, null));
    }
}
