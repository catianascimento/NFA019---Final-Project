package fr.com.nfa019.views;

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

import fr.com.nfa019.restaurant.beans.ProduitBean;
import fr.com.nfa019.restaurant.beans.RefrigerateurBean;
import fr.com.nfa019.restaurant.beans.UtilisateurBean;
import fr.com.nfa019.restaurant.httpclients.HttpClientRefrigerateur;
import java.awt.Font;
import javax.swing.JSeparator;

public class RefrigerateurView extends JFrame {

	private JPanel contentPane;
	private JTextField textField_1;
	String password = "";
	String login = "";
	public RefrigerateurBean refrigerateur;
	
	UtilisateurBean utilisateur;

	/**
	 * Create the frame.
	 */
	public RefrigerateurView(UtilisateurBean utilisateur) {
//		this.login = login;
//		this.password = password;
		
		this.utilisateur = utilisateur;
		setResizable(false);
		setBounds(100, 100, 391, 244);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("AJOUTER REFRIGERATEUR");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(106, 24, 215, 20);
		contentPane.add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(123, 89, 215, 20);
		contentPane.add(textField_1);
		
		JButton btnNewButton = new JButton("Save");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				refrigerateur = new RefrigerateurBean();
				refrigerateur.setCode(textField_1.getText());
//				refrigerateur.setMinTemperature(Double.valueOf(minTemperatureField.getText()));
//				refrigerateur.setMaxTemperature(Double.valueOf(maxTemperatureField.getText()));

				try {
					HttpClientRefrigerateur httpClientRefrigerateur = new HttpClientRefrigerateur(utilisateur);
					httpClientRefrigerateur.saveRefrigerateur(refrigerateur);
				} catch (InterruptedException | ExecutionException | IOException e) {
					e.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(52, 162, 110, 23);
		contentPane.add(btnNewButton);
		
		JButton btnCancel = new JButton("Annuler");
		btnCancel.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnCancel.setBounds(214, 162, 89, 23);
		contentPane.add(btnCancel);
		
		JLabel lblNewLabel_1_1 = new JLabel("Code:");
		lblNewLabel_1_1.setBounds(40, 91, 91, 14);
		contentPane.add(lblNewLabel_1_1);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(40, 137, 298, 2);
		contentPane.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(40, 54, 298, 2);
		contentPane.add(separator_1);
		
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setResizable(false);
	}
}
