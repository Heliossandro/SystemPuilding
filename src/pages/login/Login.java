package src.pages.login;

import src.dao.FuncionarioDAO;
import src.pages.funcionario.FuncionarioModelo;
import src.pages.funcionario.AdicionarEditarFuncionario;
import src.pages.funcionario.ListarFuncionarios;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {
    private JTextField idField;
    private JPasswordField senhaField;
    private JButton loginButton;
    private JButton createAccountButton;

    private FuncionarioDAO funcionarioDAO;

    public Login() {
        setTitle("Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        funcionarioDAO = new FuncionarioDAO();

        idField = new JTextField(20);
        senhaField = new JPasswordField(20);
        loginButton = new JButton("Entrar");
        createAccountButton = new JButton("Criar Conta");

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(new JLabel("ID:"));
        panel.add(idField);
        panel.add(new JLabel("Senha:"));
        panel.add(senhaField);
        panel.add(loginButton);
        panel.add(createAccountButton);

        add(panel, BorderLayout.CENTER);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarLogin();
            }
        });

        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdicionarEditarFuncionario dialog = new AdicionarEditarFuncionario(Login.this, null);
                dialog.setVisible(true);
            }
        });
    }

    private void realizarLogin() {
        try {
            int id = Integer.parseInt(idField.getText());
            String senha = new String(senhaField.getPassword());

            FuncionarioModelo funcionario = funcionarioDAO.get(id);
            if (funcionario == null) {
                JOptionPane.showMessageDialog(this, "Funcionário não encontrado!");
            } else if (!funcionario.getSenha().equals(senha)) {
                JOptionPane.showMessageDialog(this, "Senha incorreta!");
            } else if (!funcionario.getCargo().equals("funcionarioADM")) {
                JOptionPane.showMessageDialog(this, "Cargo incorreto! Apenas administradores podem fazer login.");
            } else {
                JOptionPane.showMessageDialog(this, "Login bem-sucedido!");
                new ListarFuncionarios().setVisible(true);
                dispose();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID inválido!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Login().setVisible(true);
            }
        });
    }
}
