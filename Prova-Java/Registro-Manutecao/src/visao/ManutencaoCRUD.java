package visao;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import controle.ManutencaoProcessa;
import modelo.Manutencao;
import uteis.Cripto;

public class ManutencaoCRUD extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel painel;
	private JLabel title, lbID, lbData, lbEquipamento,lbCusto, lbtempo,lbtotal;
	private JTextField tfID, tfData, tfEquipamento,tfCusto, tftempo,tftotal;
	
	private JButton create, read, update, delete;
	private JScrollPane scroll;
	private JTable table;
	private DefaultTableModel tableModel;
	private int indice = -1;

	ManutencaoCRUD() {
		
		// Propriedades B�sicas
		setTitle("Gerenciamento de usuários");
		setBounds(300, 200, 670, 520);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		painel = new JPanel(); // Painel de elementos
		setContentPane(painel); // Configua o painel
		setLayout(null);
		
		title = new JLabel("Cadastro de Manutenção");
		title.setBounds(130,20,1000,30);
		title.setFont( new Font("Arial", Font.BOLD, 35));
		
		painel.add(title);

		// Textos e Bot�es
		lbID = new JLabel("ID:");
		lbID.setBounds(60, 90, 100, 30);
		lbID.setFont( new Font("Arial", Font.BOLD, 20));
		painel.add(lbID);

		lbData = new JLabel("Data:");
		lbData.setBounds(50, 130, 100, 30);
		lbData.setFont( new Font("Arial", Font.BOLD, 20));
		painel.add(lbData);
		
		lbEquipamento = new JLabel("Equipamento:");
		lbEquipamento.setBounds(20, 160, 200, 30);
		lbEquipamento.setFont( new Font("Arial", Font.BOLD, 20));
		painel.add(lbEquipamento);

		lbCusto = new JLabel("Custo:");
		lbCusto.setBounds(310, 83, 200, 30);
		lbCusto.setFont( new Font("Arial", Font.BOLD, 20));
		painel.add(lbCusto);
		
		lbtempo = new JLabel("Tempo:");
		lbtempo.setBounds(300, 122, 200, 30);
		lbtempo.setFont( new Font("Arial", Font.BOLD, 20));
		painel.add(lbtempo);
		
		lbtotal = new JLabel("Total:");
		lbtotal.setBounds(320, 160, 200, 30);
		lbtotal.setFont( new Font("Arial", Font.BOLD, 20));
		painel.add(lbtotal);
		
		
		//Text Field
		
		tfID = new JTextField();
		tfID.setBounds(160, 90, 60, 30);
		painel.add(tfID);

		tfData = new JTextField();
		tfData.setBounds(160, 125, 100, 30);
		painel.add(tfData);
		
		tfEquipamento = new JTextField();
		tfEquipamento.setBounds(160, 160, 135, 30);
		painel.add(tfEquipamento);
		
		tfCusto = new JTextField();
		tfCusto.setBounds(380, 85, 150, 30);
		painel.add(tfCusto);
		
		tftempo = new JTextField();
		tftempo.setBounds(380, 122, 150, 30);
		painel.add(tftempo);
		
		tftotal = new JTextField();
		tftotal.setBounds(380, 160, 150, 30);
		painel.add(tftotal);

		create = new JButton("Cadastrar");
		read = new JButton("Consultar");
		update = new JButton("Alterar");
		delete = new JButton("Excluir");

		create.setBounds(40, 210, 120, 30);
		read.setBounds(180, 210, 120, 30);
		update.setBounds(320, 210, 120, 30);
		delete.setBounds(460, 210, 120, 30);

		painel.add(create);
		painel.add(read);
		painel.add(update);
		painel.add(delete);

		// Tabela de Professores (READ, UPDATE)
		tableModel = new DefaultTableModel();
		tableModel.addColumn("ID");
		tableModel.addColumn("data");
		tableModel.addColumn("Equipamento");
		tableModel.addColumn("Custo da Hora");
		tableModel.addColumn("Tempo");
		tableModel.addColumn("Total");
		if (ManutencaoProcessa.usuarios.size() != 0) {
			preencheTabela();
		}
		table = new JTable(tableModel);
		table.setEnabled(false);
		scroll = new JScrollPane(table);
		scroll.setBounds(20, 255, 605, 215);
		painel.add(scroll);

		create.addActionListener(this);
		read.addActionListener(this);
		update.addActionListener(this);
		delete.addActionListener(this);

		update.setEnabled(false);
		delete.setEnabled(false);

	}

	private void preencheTabela() {
		int totLinhas = tableModel.getRowCount();
		if (tableModel.getRowCount() > 0) {
			for (int i = 0; i < totLinhas; i++) {
				tableModel.removeRow(0);
			}
		}
		for (Manutencao u : ManutencaoProcessa.usuarios) {
			tableModel.addRow(new String[] { u.getEmail(), u.getSenha() });
		}
	}

	private void limparCampos() {
		tfID.setText("");
		tfData.setText("");
		tfEquipamento("");
		tfCusto("");
		tftempo("");
		tftotal("");
	}

	private void create() {
		if (tfID.getText().length() > 0 && new String(pfSenha.getPassword()).length() > 3) {
			Manutencao user = new Manutencao(tfID.getText(), Cripto.encripta(new String(pfSenha.getPassword())));
			if (ManutencaoProcessa.usuarios.contains(user)) {
				JOptionPane.showMessageDialog(this, "Usuário já cadastrado");
			} else {
				ManutencaoProcessa.usuarios.add(user);
				ManutencaoProcessa.salvar();
				preencheTabela();
				limparCampos();
			}
		} else {
			JOptionPane.showMessageDialog(this, "Preencha o email e a senha de no mínimo 4 dígitos");
		}
	}

	private void read() {
		if (tfID.getText().length() > 0) {
			Manutencao user = new Manutencao(tfID.getText(), "");
			if (ManutencaoProcessa.usuarios.contains(user)) {
				indice = ManutencaoProcessa.usuarios.indexOf(user);
				tfID.setEnabled(false);
				create.setEnabled(false);
				read.setEnabled(false);
				update.setEnabled(true);
				delete.setEnabled(true);
			} else {
				JOptionPane.showMessageDialog(this, "Usuário não encontrado");
			}
		} else {
			JOptionPane.showMessageDialog(this, "Preencha o campo email");
		}
	}

	private void update() {
		if (tfID.getText().length() > 0 && new String(tfData.getPassword()).length() > 3) {
			Manutencao user = new Manutencao(tfID.getText(), Cripto.encripta(new String(pfSenha.getPassword())));
			ManutencaoProcessa.usuarios.set(indice, user);
			ManutencaoProcessa.salvar();
			preencheTabela();
			limparCampos();
			tfID.setEnabled(true);
			create.setEnabled(true);
			read.setEnabled(true);
			update.setEnabled(false);
			delete.setEnabled(false);
		} else {
			JOptionPane.showMessageDialog(this, "Preencha o email e a senha de no mínimo 4 dígitos");
		}
	}

	private void delete() {
		ManutencaoProcessa.usuarios.remove(indice);
		ManutencaoProcessa.salvar();
		preencheTabela();
		limparCampos();
		tfID.setEnabled(true);
		create.setEnabled(true);
		read.setEnabled(true);
		update.setEnabled(false);
		delete.setEnabled(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == create) {
			create();
		}
		if (e.getSource() == read) {
			read();
		}
		if (e.getSource() == update) {
			update();
		}
		if (e.getSource() == delete) {
			delete();
		}
	}
	public static void main(String[] args) {
	ManutencaoProcessa.carregar();
	ManutencaoCRUD login = new ManutencaoCRUD();
	login.setVisible(true);
}
}
