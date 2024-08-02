package src.pages.menuPrincipal;

import src.pages.funcionario.AdicionarEditarFuncionario;
import src.pages.funcionario.ListarFuncionarios;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPrincipal extends JFrame implements ActionListener {
    private JMenu funcionariosMenu, moradoresMenu;
    private JMenuItem cadastrarFuncionario, listarFuncionario, cadastrarVeiculo;

    public MenuPrincipal() {
        setTitle("Menu Principal");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();

        funcionariosMenu = new JMenu("Funcionários");
        moradoresMenu = new JMenu("Moradores");

        cadastrarFuncionario = new JMenuItem("Cadastrar Funcionário");
        listarFuncionario = new JMenuItem("Listar Funcionário");
        cadastrarVeiculo = new JMenuItem("Cadastrar Veículo");

        funcionariosMenu.add(cadastrarFuncionario);
        funcionariosMenu.add(listarFuncionario);
        moradoresMenu.add(cadastrarVeiculo);

        menuBar.add(funcionariosMenu);
        menuBar.add(moradoresMenu);

        setJMenuBar(menuBar);

        cadastrarFuncionario.addActionListener(this);
        listarFuncionario.addActionListener(this);
        cadastrarVeiculo.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cadastrarFuncionario) {
            new AdicionarEditarFuncionario(this, null).setVisible(true);
        } else if (e.getSource() == listarFuncionario) {
            new ListarFuncionarios().setVisible(true);
        } else if (e.getSource() == cadastrarVeiculo) {
            JOptionPane.showMessageDialog(this, "Cadastrar Veículo clicado!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MenuPrincipal().setVisible(true);
            }
        });
    }
}
