package fr.com.nfa019.views;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.metal.MetalLookAndFeel;

import fr.com.nfa019.restaurant.beans.ProduitBean;
import fr.com.nfa019.restaurant.beans.StockProduitBean;
import fr.com.nfa019.restaurant.beans.UtilisateurBean;
import fr.com.nfa019.restaurant.httpclients.HttpClientProduit;
import fr.com.nfa019.restaurant.httpclients.HttpClientStock;
import fr.com.nfa019.restaurant.utils.Status;
import javax.swing.JSeparator;

public class StockProduitCreateView extends JFrame {

	private JPanel contentPane;
	String password = "";
	String login = "";
	public StockProduitBean stock;
	ButtonGroup G1;
	private JTextField quantiteField;
	private List<ProduitBean> produits;
	private List<StockProduitBean> stocks;
	
	UtilisateurBean utilisateur;

	/**
	 * Create the frame.
	 */
	public StockProduitCreateView(UtilisateurBean utilisateur) {
		G1 = new ButtonGroup();
//		this.login = login;
//		this.password = password;
		this.utilisateur = utilisateur;
		setResizable(false);
		setBounds(100, 100, 383, 266);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("AJOUTER STOCK-PRODUIT");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(104, 10, 194, 20);
		contentPane.add(lblNewLabel_1);

		JComboBox produitCombo = new JComboBox();
		HashMap<String, Long> produitMaps = new HashMap<String, Long>();
		try {
			HttpClientProduit httpClientProduit = new HttpClientProduit(utilisateur);
			produits = httpClientProduit.getAllProduits();
			String[] array = new String[produits.size()];
			for (int i = 0; i < array.length; i++) {
				array[i] = produits.get(i).getNom();
				produitMaps.put(produits.get(i).getNom(), produits.get(i).getId());
			}
			produitCombo.setModel(new DefaultComboBoxModel<String>(array));
		} catch (InterruptedException | ExecutionException | IOException e1) {
			e1.printStackTrace();
		}

//		produit.setSelectedItem(produit.getCategorie());
		produitCombo.setBounds(156, 65, 182, 20);
		contentPane.add(produitCombo);

//		JComboBox produitCombo_1 = new JComboBox();

		JButton btnNewButton = new JButton("Save");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				StockProduitBean stockProduitBean = new StockProduitBean();
//				stockProduitBean.setIdentifiantStock(stocks.get(produitCombo_1.getSelectedIndex()).getId().intValue());
//				stockProduitBean.setIdentifiantStock(Integer.valueOf(identifiantField.getText()));
				stockProduitBean.setQuantite(Integer.parseInt(quantiteField.getText()));
				stockProduitBean.setProduitId(produits.get(produitCombo.getSelectedIndex()).getId());
				stockProduitBean.setStatus(Status.OK);
				try {
					HttpClientStock httpClientStock = new HttpClientStock(utilisateur);
					httpClientStock.saveStockProduit(stockProduitBean);

				} catch (InterruptedException | ExecutionException | IOException e) {
					e.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(61, 183, 98, 23);
		contentPane.add(btnNewButton);

		JButton btnCancel = new JButton("Annuler");
		btnCancel.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnCancel.setBounds(209, 183, 89, 23);
		contentPane.add(btnCancel);

		JLabel lblNewLabel_1_1_1 = new JLabel("Produit:");
		lblNewLabel_1_1_1.setBounds(40, 68, 91, 14);
		contentPane.add(lblNewLabel_1_1_1);

//		G1.getSelection().add
//		@Override
//	    public void actionPerformed(ActionEvent e) {
//	        if (e.getActionCommand().equals("Check")) {
//	            System.out.println("Selected Radio Button: " + G1.getSelection().getActionCommand());
//	        }
//	    }

		quantiteField = new JTextField();
//		quantiteField.setText(stock.getDataDInsertion().toString());
		quantiteField.setColumns(10);
		quantiteField.setBounds(156, 105, 182, 20);
		contentPane.add(quantiteField);

		JLabel lblNewLabel_1_1_1_2 = new JLabel("Quantite:");
		lblNewLabel_1_1_1_2.setBounds(40, 107, 106, 14);
		contentPane.add(lblNewLabel_1_1_1_2);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(40, 164, 298, 2);
		contentPane.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(40, 40, 298, 2);
		contentPane.add(separator_1);

//		HashMap<Integer, Long> stockMaps = new HashMap<Integer, Long>();
//		try {
//			HttpClientStock httpClientStock = new HttpClientStock(login, password);
//			stocks = httpClientStock.getAllStocks();
//			Integer[] array = new Integer[stocks.size()];
//			for (int i = 0; i < array.length; i++) {
//				if(!Arrays.asList(array).contains(stocks.get(i).getIdentifiantStock())) {
//					array[i] = stocks.get(i).getIdentifiantStock();
//					stockMaps.put(stocks.get(i).getIdentifiantStock(), stocks.get(i).getId());
//				}
//			}
//			produitCombo_1.setModel(new DefaultComboBoxModel<Integer>(array));
//		} catch (InterruptedException | ExecutionException | IOException e1) {
//			e1.printStackTrace();
//		}
//
//		produitCombo_1.setBounds(156, 60, 182, 20);
//		contentPane.add(produitCombo_1);

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
