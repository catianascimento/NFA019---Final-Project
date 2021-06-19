package fr.com.nfa019.views;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import fr.com.nfa019.restaurant.beans.StockProduitBean;
import fr.com.nfa019.restaurant.beans.UtilisateurBean;
import fr.com.nfa019.restaurant.httpclients.HttpClientStock;
import java.awt.Font;
import javax.swing.JSeparator;

public class StockView extends JFrame {

	private JPanel contentPane;
	private JTextField textField_1;
	String password = "";
	String login = "";
	UtilisateurBean utilisateur;

	/**
	 * Create the frame.
	 */
	public StockView(UtilisateurBean utilisateur) {
//		this.login = login;
//		this.password = password;
		this.utilisateur = utilisateur;
		setResizable(false);
		setBounds(100, 100, 469, 296);

		contentPane = new JPanel();
		contentPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("AJOUTER STOCK");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(170, 0, 166, 20);
		contentPane.add(lblNewLabel_1);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(170, 40, 215, 20);
		contentPane.add(textField_1);

		JButton btnNewButton = new JButton("Save");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				StockProduitBean stockBean = new StockProduitBean();
//				stockBean.setIdentifiantStock(Integer.parseInt(textField_1.getText()));

				try {
					HttpClientStock httpClientStock = new HttpClientStock(utilisateur);
					httpClientStock.saveStock(stockBean);
				} catch (InterruptedException | ExecutionException | IOException e) {
					e.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(116, 213, 100, 23);
		contentPane.add(btnNewButton);

		JButton btnCancel = new JButton("Annuler");
		btnCancel.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnCancel.setBounds(296, 213, 89, 23);
		contentPane.add(btnCancel);

		JLabel lblNewLabel_1_1 = new JLabel("Nom:");
		lblNewLabel_1_1.setBounds(75, 38, 91, 14);
		contentPane.add(lblNewLabel_1_1);

		JLabel lblNewLabel_1_1_1 = new JLabel("Produits:");
		lblNewLabel_1_1_1.setBounds(75, 82, 91, 14);
		contentPane.add(lblNewLabel_1_1_1);

//		setContentPane(contentPane);

		getContentPane().add(returnJListOfProduits());
		
		JSeparator separator = new JSeparator();
		separator.setBounds(75, 185, 346, 0);
		contentPane.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(75, 195, 346, 2);
		contentPane.add(separator_1);
		
		JSeparator separator_1_1 = new JSeparator();
		separator_1_1.setBounds(75, 28, 346, 2);
		contentPane.add(separator_1_1);

		this.setLocationRelativeTo(null);
//		this.pack();
		this.setVisible(true);
		this.setResizable(false);
	}

	public JPanel returnJListOfProduits() {
		String[] foods = { "bacon", "wings", "ham", "beef", "more bacon" };

		JFrame frame;
		JPanel topPane, bottomPane, pane;
		JList leftList, rightList;
		JButton moveButton, clicMeButton;
		JScrollPane scroll;
		JLabel label;

		frame = new JFrame("title");
		topPane = new JPanel();
		bottomPane = new JPanel();
		pane = new JPanel();
		leftList = new JList(foods);
		rightList = new JList();
		moveButton = new JButton("Move");
		clicMeButton = new JButton("Click me!");
		label = new JLabel("I'm unbreakable");

		leftList.setVisibleRowCount(3);
		rightList.setVisibleRowCount(3);
		leftList.setPrototypeCellValue(String.format("%30s", ""));
		rightList.setPrototypeCellValue(String.format("%30s", ""));

		pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));
		topPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		scroll = new JScrollPane(leftList);

		topPane.add(scroll);
		topPane.add(moveButton);

		scroll = new JScrollPane(rightList);
		topPane.add(scroll);

		bottomPane.setLayout(new FlowLayout());
		bottomPane.add(clicMeButton);
		bottomPane.add(label);

		pane.add(topPane);
		pane.add(bottomPane);

		return pane;

	}
}
