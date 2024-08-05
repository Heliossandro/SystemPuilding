package src.pages.morador;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import src.dao.MoradorDAO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ListarMoradores extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private MoradorDAO moradorDAO;
    private JTextField searchField;
    private JButton searchButton;

    public ListarMoradores() {
        setTitle("Listar Moradores");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        moradorDAO = new MoradorDAO();

        tableModel = new DefaultTableModel(new Object[]{"ID", "Nome", "Data de Nascimento", "Telefone", "Sexo", "Proprietário"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);

        carregarMoradores();

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
                AdicionarEditarMorador dialog = new AdicionarEditarMorador(ListarMoradores.this, null);
                dialog.setVisible(true);
                carregarMoradores();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int id = (int) tableModel.getValueAt(selectedRow, 0);
                    MoradorModelo morador = moradorDAO.get(id);
                    if (morador != null) {
                        AdicionarEditarMorador dialog = new AdicionarEditarMorador(ListarMoradores.this, morador);
                        dialog.setVisible(true);
                        carregarMoradores();
                    } else {
                        JOptionPane.showMessageDialog(null, "Morador não encontrado.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione um morador para editar.");
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int id = (int) tableModel.getValueAt(selectedRow, 0);
                    int response = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja apagar este morador?", "Confirmar", JOptionPane.YES_NO_OPTION);
                    if (response == JOptionPane.YES_OPTION) {
                        moradorDAO.delete(id);
                        carregarMoradores();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione um morador para apagar.");
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

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterTable();
            }
        });
    }

    private void carregarMoradores() {
        tableModel.setRowCount(0);
        List<MoradorModelo> moradores = moradorDAO.getAll();
        for (MoradorModelo morador : moradores) {
            tableModel.addRow(new Object[]{morador.getId(), morador.getNome(), morador.getDataDeNascimento(), morador.getNumTelefone(), morador.getGenero(), morador.getProprietario()});
        }
    }

    private void filterTable() {
        String searchText = searchField.getText().toLowerCase();
        tableModel.setRowCount(0);
        List<MoradorModelo> moradores = moradorDAO.getAll();
        for (MoradorModelo morador : moradores) {
            if (morador.getNome().toLowerCase().contains(searchText)) {
                tableModel.addRow(new Object[]{morador.getId(), morador.getNome(), morador.getDataDeNascimento(), morador.getNumTelefone(), morador.getGenero(), morador.getProprietario()});
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ListarMoradores().setVisible(true);
            }
        });
    }
}
