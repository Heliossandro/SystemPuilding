package src.pages.menuPrincipal;

import src.models.ApartamentoModelo;
import src.models.FuncionarioModelo;
import src.models.MoradorModelo;
import src.models.PagamentoModelo;
import src.pages.apartamento.AdicionarEditarApartamento;
import src.pages.apartamento.ListarApartamentos;
import src.pages.funcionario.AdicionarEditarFuncionario;
import src.pages.funcionario.ListarFuncionarios;
import src.pages.morador.AdicionarEditarMorador;
import src.pages.morador.ListarMoradores;
import src.pages.veiculo.AdicionarEditarVeiculo;
import src.pages.veiculo.ListarVeiculos;
import src.dao.ApartamentoDAO;
import src.dao.MoradorDAO;
import src.dao.PagamentoDAO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MenuPrincipal extends JFrame implements ActionListener {
    private JMenu funcionariosMenu, moradoresMenu, apartamentosMenu, pagamentosMenu;
    private JMenuItem cadastrarFuncionario, listarFuncionario, cadastrarVeiculo, cadastrarMorador,
                      listarVeiculo, cadastrarApartamento, listarApartamento, listarMoradores;
    private JComboBox<String> mesesComboBox, apartamentosComboBox, moradoresComboBox, pagamentoComboBox;
    private JTextField dataPagamentoField;
    private JButton realizarPagamentoButton;

    private ApartamentoDAO apartamentoDAO = new ApartamentoDAO();
    private PagamentoDAO pagamentoDAO = new PagamentoDAO();
    private FuncionarioModelo funcionarioLogado;

    private int ultimoId = 0;

    public MenuPrincipal(FuncionarioModelo funcionario) {
        this.funcionarioLogado = funcionario;
        setTitle("Menu Principal");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Exemplo de uso do nome do funcionário logado
        JLabel welcomeLabel = new JLabel("Bem-vindo, " + funcionarioLogado.getNome() + "!");
        add(welcomeLabel);

        atualizarUltimoId();

        JMenuBar menuBar = new JMenuBar();

        funcionariosMenu = new JMenu("Funcionários");
        moradoresMenu = new JMenu("Moradores");
        apartamentosMenu = new JMenu("Apartamentos");
        pagamentosMenu = new JMenu("Pagamentos");

        cadastrarFuncionario = new JMenuItem("Cadastrar Funcionário");
        listarFuncionario = new JMenuItem("Listar Funcionário");
        cadastrarVeiculo = new JMenuItem("Cadastrar Veículo");
        listarVeiculo = new JMenuItem("Listar Veículo");
        cadastrarMorador = new JMenuItem("Cadastrar Morador");
        listarMoradores = new JMenuItem("Listar Morador");
        cadastrarApartamento = new JMenuItem("Cadastrar Apartamento");
        listarApartamento = new JMenuItem("Listar Apartamento");

        funcionariosMenu.add(cadastrarFuncionario);
        funcionariosMenu.add(listarFuncionario);
        moradoresMenu.add(cadastrarMorador);
        moradoresMenu.add(listarMoradores);
        moradoresMenu.add(cadastrarVeiculo);
        moradoresMenu.add(listarVeiculo);
        apartamentosMenu.add(cadastrarApartamento);
        apartamentosMenu.add(listarApartamento);
        pagamentosMenu.add(new JMenuItem("Realizar Pagamento"));

        menuBar.add(funcionariosMenu);
        menuBar.add(moradoresMenu);
        menuBar.add(apartamentosMenu);
        menuBar.add(pagamentosMenu);

        setJMenuBar(menuBar);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Combobox para os meses
        mesesComboBox = new JComboBox<>();
        panel.add(new JLabel("Mês:"));
        panel.add(mesesComboBox);

        // Campo de data de pagamento
        dataPagamentoField = new JTextField(10);
        dataPagamentoField.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        panel.add(new JLabel("Data de Pagamento:"));
        panel.add(dataPagamentoField);

        // Combobox para apartamentos
        apartamentosComboBox = new JComboBox<>();
        carregarApartamentos();
        panel.add(new JLabel("Apartamento:"));
        panel.add(apartamentosComboBox);

        // Combobox para moradores
        moradoresComboBox = new JComboBox<>();
        panel.add(new JLabel("Morador:"));
        panel.add(moradoresComboBox);

        // Combobox para o tipo de pagamento
        String[] tiposPagamento = {"Mensalidade", "Chave"};
        pagamentoComboBox = new JComboBox<>(tiposPagamento);
        panel.add(new JLabel("Tipo de Pagamento:"));
        panel.add(pagamentoComboBox);

        // Botão para realizar pagamento
        realizarPagamentoButton = new JButton("Realizar Pagamento");
        realizarPagamentoButton.addActionListener(this);
        panel.add(realizarPagamentoButton);

        add(panel);

        cadastrarFuncionario.addActionListener(this);
        listarFuncionario.addActionListener(this);
        cadastrarVeiculo.addActionListener(this);
        listarVeiculo.addActionListener(this);
        cadastrarMorador.addActionListener(this);
        listarMoradores.addActionListener(this);
        cadastrarApartamento.addActionListener(this);
        listarApartamento.addActionListener(this);

        // Listener para atualizar os moradores e meses não pagos ao selecionar um apartamento
        apartamentosComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int numeroApartamento = Integer.parseInt((String) apartamentosComboBox.getSelectedItem());
                carregarMoradores(numeroApartamento);
                carregarMesesNaoPagos(numeroApartamento);
            }
        });

        // Inicializa moradores e meses não pagos para o apartamento selecionado inicialmente
        if (apartamentosComboBox.getItemCount() > 0) {
            int numeroApartamento = Integer.parseInt((String) apartamentosComboBox.getSelectedItem());
            carregarMoradores(numeroApartamento);
            carregarMesesNaoPagos(numeroApartamento);
        }
    }

    private void carregarApartamentos() {
        List<ApartamentoModelo> apartamentos = apartamentoDAO.getAll(); // Obtém todos os apartamentos do DAO
        for (ApartamentoModelo apartamento : apartamentos) {
            apartamentosComboBox.addItem(String.valueOf(apartamento.getNumApartamento()));
        }
    }

    private void carregarMoradores(int numeroApartamento) {
        moradoresComboBox.removeAllItems(); // Limpa a JComboBox antes de adicionar os novos itens
        MoradorDAO moradorDAO = new MoradorDAO();
        List<MoradorModelo> moradores = moradorDAO.getByApartamento(numeroApartamento);// Obtém moradores pelo número do apartamento
        for (MoradorModelo morador : moradores) {
            moradoresComboBox.addItem(morador.getNome()); // Adiciona o nome de cada morador na JComboBox
        }
    }

    private void carregarMesesNaoPagos(int numeroApartamento) {
        mesesComboBox.removeAllItems(); // Limpa a JComboBox antes de adicionar os novos itens
        List<String> todosMeses = List.of("Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", 
                                          "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro");

        List<String> mesesPagos = pagamentoDAO.getMesesPagos(numeroApartamento, LocalDate.now().getYear());
        
        for (String mes : todosMeses) {
            if (!mesesPagos.contains(mes)) {
                mesesComboBox.addItem(mes); // Adiciona apenas os meses que não foram pagos
            }
        }
    }

    private void atualizarUltimoId() {
        List<PagamentoModelo> pagamentos = pagamentoDAO.getAll();
        for (PagamentoModelo pagamento : pagamentos) {
            if (pagamento.getId() > ultimoId) {
                ultimoId = pagamento.getId();
            }
        }
        ultimoId++;  // Incrementa o ID para o novo pagamento
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == realizarPagamentoButton) {
            realizarPagamento();
        } else if (e.getSource() == cadastrarFuncionario) {
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
        }
    }

    private void realizarPagamento() {
        try {
            // Converte o valor selecionado na JComboBox para um inteiro
            int numeroApartamento = Integer.parseInt((String) apartamentosComboBox.getSelectedItem());
            
            // Busca o apartamento pelo número
            ApartamentoModelo apartamento = apartamentoDAO.findyByApartamento(numeroApartamento);
            
            String meses = (String) mesesComboBox.getSelectedItem();
            String dataPagamento = dataPagamentoField.getText();
            String tipoPagamento = (String) pagamentoComboBox.getSelectedItem();
    
            // FuncionarioModelo já está disponível como funcionarioLogado
            PagamentoModelo pagamento = new PagamentoModelo(ultimoId, apartamento, meses, dataPagamento, tipoPagamento, funcionarioLogado);
    
            pagamentoDAO.save(pagamento);
            JOptionPane.showMessageDialog(this, "Pagamento realizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao realizar pagamento: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
