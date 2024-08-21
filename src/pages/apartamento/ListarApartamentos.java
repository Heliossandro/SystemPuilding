package src.pages.apartamento;

import src.dao.ApartamentoDAO;
import src.models.ApartamentoModelo;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ListarApartamentos extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private ApartamentoDAO apartamentoDAO;
    private JTextField searchField;
    private JButton searchButton;

    public ListarApartamentos() {
        setTitle("Listar Apartamentos");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        apartamentoDAO = new ApartamentoDAO();

        tableModel = new DefaultTableModel(new Object[]{"ID", "Apartamento", "Andar", "Estado", "Estacionamento"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);

        carregarApartamentos();

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel topPanel = new JPanel(new BorderLayout());
        searchField = new JTextField(20);
        searchButton = new JButton("Pesquisar");
        topPanel.add(searchField, BorderLayout.CENTER);
        topPanel.add(searchButton, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

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
                AdicionarEditarApartamento dialog = new AdicionarEditarApartamento(ListarApartamentos.this, null);
                dialog.setVisible(true);
                carregarApartamentos();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int id = (int) table.getValueAt(selectedRow, 0);
                    ApartamentoModelo apartamento = apartamentoDAO.get(id);
                    if (apartamento != null) {
                        AdicionarEditarApartamento dialog = new AdicionarEditarApartamento(ListarApartamentos.this, apartamento);
                        dialog.setVisible(true);
                        carregarApartamentos();
                    } else {
                        JOptionPane.showMessageDialog(null, "Apartamento não encontrado.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione um apartamento para editar.");
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int id = (int) table.getValueAt(selectedRow, 0);
                    int response = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja apagar este apartamento?", "Confirmar", JOptionPane.YES_NO_OPTION);
                    if (response == JOptionPane.YES_OPTION) {
                        apartamentoDAO.delete(id);
                        carregarApartamentos();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione um apartamento para apagar.");
                }
            }
        });

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterTable();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterTable();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterTable();
            }
        });
    }

    private void filterTable() {
        String searchText = searchField.getText().toLowerCase();
        tableModel.setRowCount(0);
        List<ApartamentoModelo> apartamentos = apartamentoDAO.getAll();
        for (ApartamentoModelo apartamento : apartamentos) {
            String numApartamentoStr = String.valueOf(apartamento.getNumApartamento());
            if (numApartamentoStr.contains(searchText)) {
                tableModel.addRow(new Object[]{apartamento.getId(), apartamento.getNumApartamento(), apartamento.getAndar(), apartamento.getEstado(), apartamento.getVagaEstacionamento()});
            }
        }
    }

    private void carregarApartamentos() {
        tableModel.setRowCount(0);
        List<ApartamentoModelo> apartamentos = apartamentoDAO.getAll();
        for (ApartamentoModelo apartamento : apartamentos) {
            tableModel.addRow(new Object[]{apartamento.getId(), apartamento.getNumApartamento(), apartamento.getAndar(), apartamento.getEstado(), apartamento.getVagaEstacionamento()});
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ListarApartamentos().setVisible(true);
            }
        });
    }
}
