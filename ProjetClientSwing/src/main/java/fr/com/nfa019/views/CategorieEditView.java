package fr.com.nfa019.views;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
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
import javax.swing.JSeparator;

public class CategorieEditView extends JFrame {

	private JPanel contentPane;
	private JTextField textField_1;
	String password = "";
	String login = "";
	public CategorieBean categorie;
	
	public UtilisateurBean utilisateur;

	/**
	 * Create the frame.
	 */
	public CategorieEditView(UtilisateurBean utilisateur, CategorieBean categorie) {
//		this.login = login;
//		this.password = password;
		
		this.utilisateur = utilisateur;
		setResizable(false);
		setBounds(100, 100, 382, 244);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("EDITER CATEGORIE");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(113, 21, 159, 20);
		contentPane.add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		textField_1.setText(categorie.getNom());
		textField_1.setColumns(10);
		textField_1.setBounds(113, 89, 215, 20);
		contentPane.add(textField_1);
		
		JButton btnNewButton = new JButton("Save");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				categorie.setNom(textField_1.getText());

				try {
					HttpClientCategorie httpClientCategorie = new HttpClientCategorie(utilisateur);
					httpClientCategorie.updateCategorie(categorie.getId(), categorie);
					
				} catch (InterruptedException | ExecutionException | IOException e) {
					e.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(70, 151, 72, 23);
		contentPane.add(btnNewButton);
		
		JButton btnCancel = new JButton("Anuller");
		btnCancel.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnCancel.setBounds(192, 151, 89, 23);
		contentPane.add(btnCancel);
		
		JLabel lblNewLabel_1_1 = new JLabel("Nom:");
		lblNewLabel_1_1.setBounds(37, 91, 91, 14);
		contentPane.add(lblNewLabel_1_1);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(31, 138, 298, 2);
		contentPane.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(31, 51, 298, 2);
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
