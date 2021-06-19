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
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.metal.MetalLookAndFeel;

import fr.com.nfa019.restaurant.beans.RefrigerateurBean;
import fr.com.nfa019.restaurant.beans.UtilisateurBean;
import fr.com.nfa019.restaurant.httpclients.HttpClientRefrigerateur;
import java.awt.Font;
import javax.swing.JSeparator;

public class RefrigerateurEditView extends JFrame {

	private JPanel contentPane;
	String password = "";
	String login = "";
	public RefrigerateurBean refrigerateur;
	private JTextField codeField;
	
	UtilisateurBean utilisateur;

	/**
	 * Create the frame.
	 */
	public RefrigerateurEditView(UtilisateurBean utilisateur, RefrigerateurBean refrigerateur) {
//		this.login = login;
//		this.password = password;
		
		this.utilisateur = utilisateur;
		setResizable(false);
		setBounds(100, 100, 461, 244);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("EDITER REFRIGERATEUR");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(143, 30, 188, 20);
		contentPane.add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("Save");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				refrigerateur.setCode(codeField.getText());
//				refrigerateur.setMinTemperature(Double.valueOf(minTemperatureField.getText()));
//				refrigerateur.setMaxTemperature(Double.valueOf(maxTemperatureField.getText()));
				
				try {
					HttpClientRefrigerateur httpClientRefrigerateur = new HttpClientRefrigerateur(utilisateur);
					httpClientRefrigerateur.updateRefrigerateur(refrigerateur.getId(), refrigerateur);
					
				} catch (InterruptedException | ExecutionException | IOException e) {
					e.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(100, 153, 80, 23);
		contentPane.add(btnNewButton);
		
		JButton btnCancel = new JButton("Annuler");
		btnCancel.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnCancel.setBounds(245, 153, 89, 23);
		contentPane.add(btnCancel);
		
//		JLabel lblNewLabel_1_1 = new JLabel("Min Temperature:");
//		lblNewLabel_1_1.setBounds(100, 123, 91, 14);
//		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Code:");
		lblNewLabel_1_1_1.setBounds(50, 91, 91, 14);
		contentPane.add(lblNewLabel_1_1_1);
		
		codeField = new JTextField();
		codeField.setText(refrigerateur.getCode());
		codeField.setColumns(10);
		codeField.setBounds(189, 89, 215, 20);
		contentPane.add(codeField);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(50, 141, 354, 2);
		contentPane.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(50, 60, 354, 2);
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
