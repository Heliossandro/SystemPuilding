package src.pages.artigo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import src.dao.ArtigoDAO;
import src.models.ArtigoModelo;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ListarArtigos extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private ArtigoDAO artigoDAO;

    public ListarArtigos() {
        setTitle("Listar Artigos");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Para fechar apenas a janela atual
        setLocationRelativeTo(null);

        artigoDAO = new ArtigoDAO();

        tableModel = new DefaultTableModel(new Object[]{"ID", "Nome", "Preço", "Data de Compra", "Estado", "Observação"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Impede a edição direta dos campos na tabela
            }
        };

        table = new JTable(tableModel);

        carregarArtigos();

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
                AdicionarEditarArtigos dialog = new AdicionarEditarArtigos(ListarArtigos.this, null);
                dialog.setVisible(true);
                carregarArtigos();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int id = (int) tableModel.getValueAt(selectedRow, 0);
                    ArtigoModelo artigo = artigoDAO.get(id);
                    if (artigo != null) {
                        AdicionarEditarArtigos dialog = new AdicionarEditarArtigos(ListarArtigos.this, artigo);
                        dialog.setVisible(true);
                        carregarArtigos();
                    } else {
                        JOptionPane.showMessageDialog(null, "Artigo não encontrado.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione um artigo para editar.");
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int id = (int) tableModel.getValueAt(selectedRow, 0);
                    int response = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja apagar este artigo?", "Confirmar", JOptionPane.YES_NO_OPTION);
                    if (response == JOptionPane.YES_OPTION) {
                        artigoDAO.delete(id);
                        carregarArtigos();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione um artigo para apagar.");
                }
            }
        });
    }

    private void carregarArtigos() {
        tableModel.setRowCount(0);
        List<ArtigoModelo> artigos = artigoDAO.getAll();
        for (ArtigoModelo artigo : artigos) {
            tableModel.addRow(new Object[]{artigo.getId(), artigo.getNome(), artigo.getPreco(), artigo.getDataCompra(), artigo.getEstado(), artigo.getObservacao()});
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ListarArtigos().setVisible(true);
            }
        });
    }
}
