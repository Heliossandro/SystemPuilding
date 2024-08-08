package src.pages.funcionario;

import src.dao.FuncionarioDAO;
import src.models.FuncionarioModelo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AdicionarEditarFuncionario extends JDialog {
    private JTextField nomeField;
    private JPasswordField senhaField;
    private JComboBox<String> cargoComboBox;
    private JPasswordField authPasswordField;
    private JButton saveButton;
    private JButton cancelButton;
    private int ultimoId = 0;
    private List<FuncionarioModelo> funcionarios;

    private FuncionarioDAO funcionarioDAO;
    private FuncionarioModelo funcionario;

    public AdicionarEditarFuncionario(Frame parent, FuncionarioModelo funcionario) {
        super(parent, true);
        setTitle(funcionario == null ? "Adicionar Funcionário" : "Editar Funcionário");
        setSize(300, 250);
        setLocationRelativeTo(parent);

        this.funcionarioDAO = new FuncionarioDAO();
        this.funcionario = funcionario;
        this.funcionarios = funcionarioDAO.getAll(); // Inicializa a lista com os funcionários do DAO
        atualizarUltimoId(); // Atualiza o ultimoId baseado nos funcionários existentes

        nomeField = new JTextField(20);
        senhaField = new JPasswordField(20);
        cargoComboBox = new JComboBox<>(new String[]{FuncionarioModelo.CARGO_ADM, FuncionarioModelo.CARGO_GERAL});
        authPasswordField = new JPasswordField(20);

        if (funcionario != null) {
            nomeField.setText(funcionario.getNome());
            senhaField.setText(funcionario.getSenha());
            cargoComboBox.setSelectedItem(funcionario.getCargo());
        } else {
            senhaField.setEnabled(false);
        }

        cargoComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCargo = (String) cargoComboBox.getSelectedItem();
                if (FuncionarioModelo.CARGO_ADM.equals(selectedCargo)) {
                    senhaField.setEnabled(true);
                } else {
                    senhaField.setText("");
                    senhaField.setEnabled(false);
                }
            }
        });

        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JLabel("Nome:"));
        panel.add(nomeField);
        panel.add(new JLabel("Cargo:"));
        panel.add(cargoComboBox);
        panel.add(new JLabel("Senha:"));
        panel.add(senhaField);
        panel.add(new JLabel("Senha de Autenticação:"));
        panel.add(authPasswordField);

        saveButton = new JButton("Salvar");
        cancelButton = new JButton("Cancelar");

        panel.add(saveButton);
        panel.add(cancelButton);

        add(panel, BorderLayout.CENTER);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarFuncionario();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void atualizarUltimoId(){
        for(FuncionarioModelo funcionario : funcionarios){
            if(funcionario.getId() > ultimoId){
                ultimoId = funcionario.getId();
            }
        }
        ultimoId++; // Incrementa o último ID para garantir que o próximo ID seja único
    }

    private void salvarFuncionario() {
        String nome = nomeField.getText();
        String senha = new String(senhaField.getPassword());
        String cargo = (String) cargoComboBox.getSelectedItem();
        String authPassword = new String(authPasswordField.getPassword());

        if (!nome.trim().contains(" ")) {
            JOptionPane.showMessageDialog(this, "O nome deve ter pelo menos duas palavras.");
            return;
        }

        if (FuncionarioModelo.CARGO_ADM.equals(cargo) && senha.length() < 8) {
            JOptionPane.showMessageDialog(this, "A senha deve ter pelo menos 8 caracteres.");
            return;
        }

        if (!authPassword.equals("ADM1234567")) {
            JOptionPane.showMessageDialog(this, "Senha de autenticação inválida.");
            return;
        }

        if (funcionario == null) {
            funcionario = new FuncionarioModelo(ultimoId, nome, FuncionarioModelo.CARGO_ADM.equals(cargo) ? senha : "", cargo);
        } else {
            funcionario.setNome(nome);
            funcionario.setSenha(FuncionarioModelo.CARGO_ADM.equals(cargo) ? senha : "");
            funcionario.setCargo(cargo);
        }

        funcionarioDAO.save(funcionario);
        dispose();
    }
}
