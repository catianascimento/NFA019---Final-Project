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
import javax.swing.border.EmptyBorder;

import fr.com.nfa019.restaurant.beans.CategorieBean;
import fr.com.nfa019.restaurant.beans.UtilisateurBean;
import fr.com.nfa019.restaurant.httpclients.HttpClientCategorie;
import javax.swing.JSeparator;

public class CategorieView extends JFrame {

	private JPanel contentPane;
	private JTextField textField_1;
	String password = "";
	String login = "";
	private UtilisateurBean utilisateur;

	/**
	 * Create the frame.
	 */
	public CategorieView(UtilisateurBean utilisateur) {
//		this.login = login;
//		this.password = password;
		
		this.utilisateur = utilisateur;
		setResizable(false);
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 388, 244);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("AJOUTER CATEGORIE");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(110, 22, 155, 20);
		contentPane.add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		textField_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		textField_1.setColumns(10);
		textField_1.setBounds(126, 89, 215, 20);
		contentPane.add(textField_1);
		
		JButton btnNewButton = new JButton("Save");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CategorieBean categorieBean = new CategorieBean();
				categorieBean.setNom(textField_1.getText());

				try {
					HttpClientCategorie httpClientCategorie = new HttpClientCategorie(utilisateur);
					httpClientCategorie.saveCategorie(categorieBean);
				} catch (InterruptedException | ExecutionException | IOException e) {
					e.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(53, 151, 78, 23);
		contentPane.add(btnNewButton);
		
		JButton btnCancel = new JButton("Anuller");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnCancel.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnCancel.setBounds(226, 151, 89, 23);
		contentPane.add(btnCancel);
		
		JLabel lblNewLabel_1_1 = new JLabel("Nom:");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblNewLabel_1_1.setBounds(43, 91, 91, 14);
		contentPane.add(lblNewLabel_1_1);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(43, 139, 298, 2);
		contentPane.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(43, 52, 298, 2);
		contentPane.add(separator_1);
		
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setResizable(false);
	}
}
