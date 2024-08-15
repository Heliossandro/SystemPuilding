package src.pages.veiculo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import src.dao.VeiculoDAO;
import src.models.VeiculoModelo;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ListarVeiculos extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private VeiculoDAO veiculoDAO;

    public ListarVeiculos() {
        setTitle("Listar Veículos");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        veiculoDAO = new VeiculoDAO();

        tableModel = new DefaultTableModel(new Object[]{"ID", "Nome", "Marca", "Matrícula", "Cor", "Morador"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);

        carregarVeiculos();

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
                AdicionarEditarVeiculo dialog = new AdicionarEditarVeiculo(ListarVeiculos.this, null);
                dialog.setVisible(true);
                carregarVeiculos();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int id = (int) tableModel.getValueAt(selectedRow, 0);
                    VeiculoModelo veiculo = veiculoDAO.get(id);
                    if (veiculo != null) {
                        AdicionarEditarVeiculo dialog = new AdicionarEditarVeiculo(ListarVeiculos.this, veiculo);
                        dialog.setVisible(true);
                        carregarVeiculos();
                    } else {
                        JOptionPane.showMessageDialog(null, "Veículo não encontrado.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione um veículo para editar.");
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int id = (int) tableModel.getValueAt(selectedRow, 0);
                    int response = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja apagar este veículo?", "Confirmar", JOptionPane.YES_NO_OPTION);
                    if (response == JOptionPane.YES_OPTION) {
                        veiculoDAO.delete(id);
                        carregarVeiculos();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione um veículo para apagar.");
                }
            }
        });
    }

    private void carregarVeiculos() {
        tableModel.setRowCount(0);
        List<VeiculoModelo> veiculos = veiculoDAO.getAll();
        for (VeiculoModelo veiculo : veiculos) {
            tableModel.addRow(new Object[]{veiculo.getId(), veiculo.getVeiculo(), veiculo.getMarca(), veiculo.getMatricula(), veiculo.getCor(), veiculo.getMorador()});
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ListarVeiculos().setVisible(true);
            }
        });
    }
}
