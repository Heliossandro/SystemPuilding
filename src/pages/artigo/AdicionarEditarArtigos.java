package src.pages.artigo;

import src.dao.ArtigoDAO;
import src.models.ArtigoModelo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AdicionarEditarArtigos extends JDialog {
    private JTextField nomeField, precoField, dataCompraField, observacaoField;
    private JComboBox<String> estadoComboBox;
    private JButton saveButton, cancelButton;
    private int ultimoId = 0;
    private List<ArtigoModelo> artigos;

    private ArtigoDAO artigoDAO;
    private ArtigoModelo artigo;

    public AdicionarEditarArtigos(Frame parent, ArtigoModelo artigo) {
        super(parent, true);
        setTitle(artigo == null ? "Adicionar Artigo" : "Editar Artigo");
        setSize(400, 300);
        setLocationRelativeTo(parent);

        this.artigoDAO = new ArtigoDAO();
        this.artigo = artigo;
        this.artigos = artigoDAO.getAll(); // Inicializa a lista com os artigos do DAO
        atualizarUltimoId(); // Atualiza o ultimoId baseado nos artigos existentes

        nomeField = new JTextField(20);
        precoField = new JTextField(20);
        dataCompraField = new JTextField(20);
        observacaoField = new JTextField(20);

        // Inicializa a JComboBox com as opções de estado
        estadoComboBox = new JComboBox<>(new String[]{"Novo", "Semi-novo", "Estragado"});

        if (artigo != null) {
            nomeField.setText(artigo.getNome());
            precoField.setText(String.valueOf(artigo.getPreco()));
            dataCompraField.setText(artigo.getDataCompra());
            estadoComboBox.setSelectedItem(artigo.getEstado());
            observacaoField.setText(artigo.getObservacao());
        }

        JPanel panel = new JPanel(new GridLayout(6, 2));
        panel.add(new JLabel("Nome:"));
        panel.add(nomeField);
        panel.add(new JLabel("Preço:"));
        panel.add(precoField);
        panel.add(new JLabel("Data de Compra:"));
        panel.add(dataCompraField);
        panel.add(new JLabel("Estado:"));
        panel.add(estadoComboBox); // Adiciona a JComboBox ao painel
        panel.add(new JLabel("Observação:"));
        panel.add(observacaoField);

        saveButton = new JButton("Salvar");
        cancelButton = new JButton("Cancelar");

        panel.add(saveButton);
        panel.add(cancelButton);

        add(panel, BorderLayout.CENTER);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarArtigo();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void atualizarUltimoId() {
        for (ArtigoModelo artigo : artigos) {
            if (artigo.getId() > ultimoId) {
                ultimoId = artigo.getId();
            }
        }
        ultimoId++; // Incrementa o último ID para garantir que o próximo ID seja único
    }

    private void salvarArtigo() {
        String nome = nomeField.getText();
        double preco;
        try {
            preco = Double.parseDouble(precoField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Preço inválido. Insira um valor numérico.");
            return;
        }
        String dataCompra = dataCompraField.getText();
        String estado = (String) estadoComboBox.getSelectedItem(); // Obtém o estado selecionado
        String observacao = observacaoField.getText();

        if (artigo == null) {
            artigo = new ArtigoModelo(ultimoId, preco, nome, dataCompra, estado, observacao);
        } else {
            artigo.setNome(nome);
            artigo.setPreco(preco);
            artigo.setDataCompra(dataCompra);
            artigo.setEstado(estado);
            artigo.setObservacao(observacao);
        }

        artigoDAO.save(artigo);
        dispose();
    }
}
