package src.pages.funcionario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import src.dao.FuncionarioDAO;
import src.models.FuncionarioModelo;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ListarFuncionarios extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private FuncionarioDAO funcionarioDAO;

    public ListarFuncionarios() {
        setTitle("Listar Funcionários");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Para fechar apenas a janela atual
        setLocationRelativeTo(null);

        funcionarioDAO = new FuncionarioDAO();

        tableModel = new DefaultTableModel(new Object[]{"ID", "Nome", "Cargo", "Senha"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Impede a edição direta dos campos na tabela
            }
        };

        table = new JTable(tableModel);

        carregarFuncionarios();

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Adicionar");
        JButton editButton = new JButton("Editar");
        JButton deleteButton = new JButton("Apagar");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdicionarEditarFuncionario dialog = new AdicionarEditarFuncionario(ListarFuncionarios.this, null);
                dialog.setVisible(true);
                carregarFuncionarios();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int id = (int) tableModel.getValueAt(selectedRow, 0);
                    FuncionarioModelo funcionario = funcionarioDAO.get(id);
                    if (funcionario != null) {
                        AdicionarEditarFuncionario dialog = new AdicionarEditarFuncionario(ListarFuncionarios.this, funcionario);
                        dialog.setVisible(true);
                        carregarFuncionarios();
                    } else {
                        JOptionPane.showMessageDialog(null, "Funcionário não encontrado.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione um funcionário para editar.");
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int id = (int) tableModel.getValueAt(selectedRow, 0);
                    int response = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja apagar este funcionário?", "Confirmar", JOptionPane.YES_NO_OPTION);
                    if (response == JOptionPane.YES_OPTION) {
                        funcionarioDAO.delete(id);
                        carregarFuncionarios();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione um funcionário para apagar.");
                }
            }
        });
    }

    private void carregarFuncionarios() {
        tableModel.setRowCount(0);
        List<FuncionarioModelo> funcionarios = funcionarioDAO.getAll();
        for (FuncionarioModelo funcionario : funcionarios) {
            tableModel.addRow(new Object[]{funcionario.getId(), funcionario.getNome(), funcionario.getCargo(), funcionario.getSenha()});
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ListarFuncionarios().setVisible(true);
            }
        });
    }
}
