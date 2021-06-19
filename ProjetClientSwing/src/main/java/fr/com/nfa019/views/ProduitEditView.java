package fr.com.nfa019.views;

import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
import javax.swing.JSeparator;

public class ProduitEditView extends JFrame {

//	String password = "";
//	String login = "";
	
	UtilisateurBean utilisateur;

	private JPanel contentPane;
	private JTextField nom;
	private JTextField duree;
	public ProduitBean produit;

	public ProduitEditView(UtilisateurBean utilisateur, ProduitBean produit) {
		setResizable(false);

//		this.login = login;
//		this.password = password;
		
		this.utilisateur = utilisateur;
		setBounds(100, 100, 457, 298);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Duree (en jours):");
		lblNewLabel.setBounds(53, 156, 149, 20);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("EDITER PRODUIT");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(164, 26, 149, 20);
		contentPane.add(lblNewLabel_1);

		nom = new JTextField();
		nom.setText(produit.getNom());
		nom.setColumns(10);
		nom.setBounds(192, 89, 215, 20);
		contentPane.add(nom);

		JComboBox categorie = new JComboBox();
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
			categorie.setModel(new DefaultComboBoxModel<String>(array));
		} catch (InterruptedException | ExecutionException | IOException e1) {
			e1.printStackTrace();
		}

		categorie.setSelectedItem(produit.getCategorie());
		categorie.setBounds(192, 119, 215, 23);
		contentPane.add(categorie);

		JButton btnCancel = new JButton("Annuler");
		btnCancel.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProduitEditView.this.dispose();
			}
		});
		btnCancel.setBounds(245, 210, 89, 23);
		contentPane.add(btnCancel);

		JLabel lblNewLabel_2 = new JLabel("Categorie:");
		lblNewLabel_2.setBounds(53, 122, 91, 20);
		contentPane.add(lblNewLabel_2);

		JLabel lblNewLabel_1_1 = new JLabel("Nom:");
		lblNewLabel_1_1.setBounds(53, 92, 91, 14);
		contentPane.add(lblNewLabel_1_1);

		duree = new JTextField();
		duree.setText(produit.getDureeDeConservationEnJours().toString());
		duree.setColumns(10);
		duree.setBounds(192, 156, 215, 20);
		contentPane.add(duree);

//		JLabel lblStatus = new JLabel("Status:");
//		lblStatus.setBounds(53, 189, 149, 20);
//		contentPane.add(lblStatus);
//
//		JComboBox status = new JComboBox();
//		Status[] array = new Status[] { Status.OK, Status.RETIRE };
//		status.setModel(new DefaultComboBoxModel<Status>(array));
//
//		status.setSelectedItem(produit.getStatus());
//		status.setBounds(192, 186, 215, 23);
//		contentPane.add(status);

		JButton btnNewButton = new JButton("Save");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					produit.setDureeDeConservationEnJours(Integer.valueOf(duree.getText()));
					produit.setCategorieId(categorieMaps.get(categorie.getSelectedItem().toString()));
//					produit.setStatus((Status) status.getSelectedItem());
					produit.setNom(nom.getText());

					HttpClientProduit httpClientProduit = new HttpClientProduit(utilisateur);
					httpClientProduit.updateProduit(produit.getId(), produit);
					JOptionPane.showMessageDialog(new Frame(), "L'article a été mis à jour!");
				} catch (InterruptedException | ExecutionException | IOException e) {
					e.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(94, 210, 103, 23);
		contentPane.add(btnNewButton);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(46, 198, 361, 2);
		contentPane.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(46, 56, 361, 2);
		contentPane.add(separator_1);

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
