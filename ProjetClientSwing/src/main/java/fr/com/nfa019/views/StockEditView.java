package fr.com.nfa019.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutionException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.metal.MetalLookAndFeel;

import fr.com.nfa019.restaurant.beans.StockProduitBean;
import fr.com.nfa019.restaurant.beans.UtilisateurBean;
import fr.com.nfa019.restaurant.httpclients.HttpClientStock;
import fr.com.nfa019.restaurant.utils.Status;

import java.awt.Font;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSeparator;

public class StockEditView extends JFrame {

	private JPanel contentPane;
	String password = "";
	String login = "";
	public StockProduitBean stock;
	private JTextField textField;
	ButtonGroup G1;
//	// Declaration of object of JRadioButton class.
//	JRadioButton jRadioButton1;
//	// Declaration of object of JRadioButton class.
//	JRadioButton jRadioButton2;
	private JTextField dateDInsertionField;
	private JRadioButton okButton;
	private JRadioButton retireButton;
	
	UtilisateurBean utilisateur;

	/**
	 * Create the frame.
	 */
	public StockEditView(UtilisateurBean utilisateur, StockProduitBean stock) {
		G1 = new ButtonGroup();
//		this.login = login;
//		this.password = password;
		this.utilisateur = utilisateur;
		setResizable(false);
		setBounds(100, 100, 410, 276);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("EDITER STOCK-PRODUIT");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(112, 10, 198, 20);
		contentPane.add(lblNewLabel_1);

		JButton btnNewButton = new JButton("Save");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				stock.setIdentifiantStock(Integer.parseInt(textField_1.getText()));
				stock.setProduitId(Long.parseLong(textField.getText()));
//				stock.setQuantite();
//				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//				LocalDateTime dateTime = LocalDateTime.parse(dateDInsertionField.getText(), formatter);
//				stock.setDataDInsertion(dateTime);
				if (okButton.isSelected()) {
					stock.setStatus(Status.OK);
				} else {
					stock.setStatus(Status.RETIRE);
				}
				try {
					HttpClientStock httpClientStock = new HttpClientStock(utilisateur);
					httpClientStock.updateStock(stock.getId(), stock);

				} catch (InterruptedException | ExecutionException | IOException e) {
					e.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(76, 194, 106, 23);
		contentPane.add(btnNewButton);

		JButton btnCancel = new JButton("Annuler");
		btnCancel.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnCancel.setBounds(221, 194, 89, 23);
		contentPane.add(btnCancel);

		JLabel lblNewLabel_1_1_1 = new JLabel("Produit:");
		lblNewLabel_1_1_1.setBounds(40, 62, 91, 14);
		contentPane.add(lblNewLabel_1_1_1);

		JLabel lblNewLabel_1_1_1_1 = new JLabel("Statut:");
		lblNewLabel_1_1_1_1.setBounds(40, 138, 91, 14);
		contentPane.add(lblNewLabel_1_1_1_1);

		textField = new JTextField();
		textField.setEditable(false);
		textField.setText(stock.getProduitId().toString());
		textField.setColumns(10);
		textField.setBounds(156, 60, 182, 20);
		contentPane.add(textField);

		okButton = new JRadioButton("OK");
		if (stock.getStatus().equals(Status.OK)) {
			okButton.setSelected(true);
		}
		okButton.setBounds(166, 135, 62, 21);
		G1.add(okButton);
		contentPane.add(okButton);

		retireButton = new JRadioButton("RETIRE");
		if (stock.getStatus().equals(Status.RETIRE)) {
			retireButton.setSelected(true);
		}
		retireButton.setBounds(248, 135, 103, 21);
		G1.add(retireButton);
		contentPane.add(retireButton);

		dateDInsertionField = new JTextField();
		dateDInsertionField.setEditable(false);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String formatDateTime = stock.getDataDInsertion().format(formatter);
		dateDInsertionField.setText(formatDateTime);
		dateDInsertionField.setColumns(10);
		dateDInsertionField.setBounds(156, 89, 182, 20);
		contentPane.add(dateDInsertionField);

		JLabel lblNewLabel_1_1_1_2 = new JLabel("Date d'insertion:");
		lblNewLabel_1_1_1_2.setBounds(40, 92, 106, 14);
		contentPane.add(lblNewLabel_1_1_1_2);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(40, 171, 317, 2);
		contentPane.add(separator_1);
		
		JSeparator separator_1_1 = new JSeparator();
		separator_1_1.setBounds(40, 40, 317, 2);
		contentPane.add(separator_1_1);

		try {
			UIManager.setLookAndFeel(new MetalLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setResizable(false);
	}
}
