package fr.com.nfa019.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.metal.MetalLookAndFeel;

import fr.com.nfa019.restaurant.beans.CategorieBean;
import fr.com.nfa019.restaurant.beans.ProduitBean;
import fr.com.nfa019.restaurant.beans.UtilisateurBean;
import fr.com.nfa019.restaurant.httpclients.HttpClientCategorie;
import fr.com.nfa019.restaurant.httpclients.HttpClientProduit;
import fr.com.nfa019.restaurant.utils.Status;
import java.awt.Font;
import javax.swing.JSeparator;

public class ProduitView extends JFrame {

	String password = "";
	String login = "";

	private JPanel contentPane;
	private JTextField textField_1;
	private JTextField textField;
	
	UtilisateurBean utilisateur;

	public ProduitView(UtilisateurBean utilisateur) {
		setResizable(false);

//		this.login = login;
//		this.password = password;
		
		this.utilisateur = utilisateur;
		setBounds(100, 100, 459, 293);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Duree (en jours):");
		lblNewLabel.setBounds(53, 156, 149, 20);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("AJOUTER PRODUIT");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(156, 24, 155, 20);
		contentPane.add(lblNewLabel_1);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(192, 89, 215, 20);
		contentPane.add(textField_1);

		JComboBox comboBox2 = new JComboBox();
		HashMap<String, Integer> categorieMaps = new HashMap<String, Integer>();
		List<CategorieBean> categories;
		try {
			HttpClientCategorie httpClientCategorie = new HttpClientCategorie(utilisateur);
			categories = httpClientCategorie.getAllCategories();
			String[] array = new String[categories.size()];
			for (int i = 0; i < array.length; i++) {
				array[i] = categories.get(i).getNom();
				categorieMaps.put(categories.get(i).getNom(), categories.get(i).getId());
			}
			comboBox2.setModel(new DefaultComboBoxModel<String>(array));
		} catch (InterruptedException | ExecutionException | IOException e1) {
			e1.printStackTrace();
		}

		comboBox2.setBounds(192, 119, 215, 23);
		contentPane.add(comboBox2);

		JButton btnCancel = new JButton("Annuler");
		btnCancel.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProduitView.this.dispose();
			}
		});
		btnCancel.setBounds(246, 218, 89, 23);
		contentPane.add(btnCancel);

		JLabel lblNewLabel_2 = new JLabel("Categorie:");
		lblNewLabel_2.setBounds(53, 122, 91, 20);
		contentPane.add(lblNewLabel_2);

		JLabel lblNewLabel_1_1 = new JLabel("Nom:");
		lblNewLabel_1_1.setBounds(53, 92, 91, 14);
		contentPane.add(lblNewLabel_1_1);

		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(192, 156, 215, 20);
		contentPane.add(textField);
		Status[] array = new Status[] { Status.OK, Status.RETIRE };

//		BufferedImage buttonIcon = null;
//		try {
//			buttonIcon = ImageIO.read(getClass().getClassLoader().getResource("images/save-file.png"));
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//		}
//		JButton btnNewButton = new JButton(new ImageIcon(buttonIcon));
		JButton btnNewButton = new JButton("Save");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ProduitBean produitBean = new ProduitBean();
				produitBean.setDureeDeConservationEnJours(Integer.valueOf(textField.getText()));
				produitBean.setCategorieId(categorieMaps.get(comboBox2.getSelectedItem().toString()));
//				produitBean.setStatus((Status) comboBox_3.getSelectedItem());
				produitBean.setNom(textField_1.getText());

				try {
					HttpClientProduit httpClientProduit = new HttpClientProduit(utilisateur);
					httpClientProduit.saveProduit(produitBean);
				} catch (InterruptedException | ExecutionException | IOException e) {
					e.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(113, 218, 89, 23);
		contentPane.add(btnNewButton);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(53, 206, 354, 2);
		contentPane.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(53, 54, 354, 2);
		contentPane.add(separator_1);
		
		try {
			UIManager.setLookAndFeel(new MetalLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setResizable(false);
	}
}
