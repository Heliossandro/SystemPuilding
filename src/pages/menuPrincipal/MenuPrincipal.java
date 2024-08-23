package src.pages.menuPrincipal;

import src.pages.apartamento.AdicionarEditarApartamento;
import src.pages.apartamento.ListarApartamentos;
import src.pages.funcionario.AdicionarEditarFuncionario;
import src.pages.funcionario.ListarFuncionarios;
import src.pages.morador.AdicionarEditarMorador;
import src.pages.morador.ListarMoradores;
import src.pages.veiculo.AdicionarEditarVeiculo;
import src.pages.veiculo.ListarVeiculos;
import src.pages.artigo.AdicionarEditarArtigos; 
import src.pages.artigo.ListarArtigos; 

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPrincipal extends JFrame implements ActionListener {
    private JMenu funcionariosMenu, moradoresMenu, apartamentosMenu, artigosMenu;  
    private JMenuItem cadastrarFuncionario, listarFuncionario, cadastrarVeiculo, cadastrarMorador,
                      listarVeiculo, cadastrarApartamento, listarApartamento, listarMoradores,
                      cadastrarArtigo, listarArtigos;  

    public MenuPrincipal() {
        setTitle("Menu Principal");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();

        funcionariosMenu = new JMenu("Funcionários");
        moradoresMenu = new JMenu("Moradores");
        apartamentosMenu = new JMenu("Apartamentos");
        artigosMenu = new JMenu("Artigos");  

        cadastrarFuncionario = new JMenuItem("Cadastrar Funcionário");
        listarFuncionario = new JMenuItem("Listar Funcionário");
        cadastrarVeiculo = new JMenuItem("Cadastrar Veículo");
        listarVeiculo = new JMenuItem("Listar Veículo");
        cadastrarMorador = new JMenuItem("Cadastrar Morador");
        listarMoradores = new JMenuItem("Listar Morador");
        cadastrarApartamento = new JMenuItem("Cadastrar Apartamento");
        listarApartamento = new JMenuItem("Listar Apartamento");
        cadastrarArtigo = new JMenuItem("Cadastrar Artigo");  
        listarArtigos = new JMenuItem("Listar Artigos");  

        funcionariosMenu.add(cadastrarFuncionario);
        funcionariosMenu.add(listarFuncionario);
        moradoresMenu.add(cadastrarMorador);
        moradoresMenu.add(listarMoradores);
        moradoresMenu.add(cadastrarVeiculo);
        moradoresMenu.add(listarVeiculo);
        apartamentosMenu.add(cadastrarApartamento);
        apartamentosMenu.add(listarApartamento);
        artigosMenu.add(cadastrarArtigo);
        artigosMenu.add(listarArtigos);  

        menuBar.add(funcionariosMenu);
        menuBar.add(moradoresMenu);
        menuBar.add(apartamentosMenu);
        menuBar.add(artigosMenu);  

        setJMenuBar(menuBar);

        cadastrarFuncionario.addActionListener(this);
        listarFuncionario.addActionListener(this);
        cadastrarVeiculo.addActionListener(this);
        listarVeiculo.addActionListener(this);
        cadastrarMorador.addActionListener(this);
        listarMoradores.addActionListener(this);
        cadastrarApartamento.addActionListener(this);
        listarApartamento.addActionListener(this);
        cadastrarArtigo.addActionListener(this);  
        listarArtigos.addActionListener(this);  
        }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cadastrarFuncionario) {
            new AdicionarEditarFuncionario(this, null).setVisible(true);
        } else if (e.getSource() == listarFuncionario) {
            new ListarFuncionarios().setVisible(true);
        } else if (e.getSource() == cadastrarVeiculo) {
            new AdicionarEditarVeiculo(null, null).setVisible(true);
        } else if (e.getSource() == cadastrarMorador) {
            new AdicionarEditarMorador(this, null).setVisible(true);
        } else if (e.getSource() == listarVeiculo) {
            new ListarVeiculos().setVisible(true);
        } else if (e.getSource() == listarApartamento) {
            new ListarApartamentos().setVisible(true);
        } else if (e.getSource() == listarMoradores) {
            new ListarMoradores().setVisible(true);
        } else if (e.getSource() == cadastrarApartamento) {
            new AdicionarEditarApartamento(this, null).setVisible(true);
        } else if (e.getSource() == cadastrarArtigo) {
            new AdicionarEditarArtigos(this, null).setVisible(true);
        } else if (e.getSource() == listarArtigos) {  
            new ListarArtigos().setVisible(true);
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
