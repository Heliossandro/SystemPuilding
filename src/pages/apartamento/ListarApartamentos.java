package src.pages.apartamento;

import src.dao.ApartamentoDAO;
import src.models.*;
import src.pages.morador.AdicionarEditarMorador;
import src.pages.morador.ListarMoradores;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class ListarApartamentos {
    private JTable table;
    private DefaultTableModel tableModel;
    private ApartamentoDAO apapartamentoDAO;
    private JTextField searchField;
    private JButton searchButton;

    public ListarApartamentos(){
        setTitle("Listar Apartamentos");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        apapartamentoDAO = new ApartamentoDAO();

        tableModel = new DefaultTableModel(new Object[]{"ID","Apartamento","Andar","Estado","Estacionamento"}, 0){
            public boolean isCellEditable(int row, int column){
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
    }

    private void carregarApartamentos(){
        tableModel.setRowCount(0);
        List<ApartamentoModelo> apartamentos = apapartamentoDAO.getAll();
        for (ApartamentoModelo apartamento : apartamentos){
            tableModel.addRow(new Object[]{apartamento.getId(), apartamento.getNumApartamento(), apartamento.getAndar(), apartamento.getEstado(), apartamento.getVagaEstacionamento()});
        }
    }

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
}
