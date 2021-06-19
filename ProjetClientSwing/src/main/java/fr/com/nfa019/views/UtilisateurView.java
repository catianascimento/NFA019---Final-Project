package fr.com.nfa019.views;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import fr.com.nfa019.restaurant.beans.RoleBean;
import fr.com.nfa019.restaurant.beans.UtilisateurBean;
import fr.com.nfa019.restaurant.httpclients.HttpClientRole;
import fr.com.nfa019.restaurant.httpclients.HttpClientUtilisateur;
import javax.swing.JSeparator;

public class UtilisateurView extends JFrame {

	String password = "";
	String login = "";

	private JPanel contentPane;
	private JTextField nomField;
	private JTextField loginField;
	private JTextField prenomField;
	private JPasswordField motDePasseField;
	private JTextField mailField;

	List<RoleBean> roles;
	HashMap<String, Integer> roleMaps;
	
	UtilisateurBean utilisateurDuLogin;

	public UtilisateurView(UtilisateurBean utilisateurDuLogin) {
		setResizable(false);

		this.utilisateurDuLogin = utilisateurDuLogin;
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 489, 371);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("AJOUTER UTILISATEUR");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
//		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(163, 39, 183, 20);
		contentPane.add(lblNewLabel_1);

		JComboBox profilCombo = new JComboBox();
		profilCombo.setBounds(192, 242, 215, 23);
		contentPane.add(profilCombo);

		nomField = new JTextField();
//		textField_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		nomField.setColumns(10);
		nomField.setBounds(192, 89, 215, 20);
		contentPane.add(nomField);
		roleMaps = new HashMap<String, Integer>();

		try {
			HttpClientRole httpClientRole = new HttpClientRole(utilisateurDuLogin);
			roles = httpClientRole.getAllRoles();
			String[] array = new String[roles.size()];
			for (int i = 0; i < array.length; i++) {
				array[i] = roles.get(i).getNom();
				roleMaps.put(roles.get(i).getNom(), roles.get(i).getId());
			}
			profilCombo.setModel(new DefaultComboBoxModel<String>(array));
		} catch (InterruptedException | ExecutionException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		JButton btnCancel = new JButton("Anuller");
		btnCancel.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnCancel.setBounds(279, 299, 89, 23);
		contentPane.add(btnCancel);

		JLabel lblNewLabel_2 = new JLabel("Adresse mail:");
		lblNewLabel_2.setBounds(53, 153, 91, 20);
		contentPane.add(lblNewLabel_2);

		JLabel lblNewLabel_1_1 = new JLabel("Nom:");
		lblNewLabel_1_1.setBounds(53, 92, 91, 14);
		contentPane.add(lblNewLabel_1_1);

		loginField = new JTextField();
		loginField.setColumns(10);
		loginField.setBounds(192, 184, 215, 20);
		contentPane.add(loginField);

		JButton btnNewButton = new JButton("Save");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UtilisateurBean utilisateurBean = new UtilisateurBean();
				utilisateurBean.setNom(nomField.getText());
				utilisateurBean.setPrenom(prenomField.getText());
				utilisateurBean.setLogin(loginField.getText());
				utilisateurBean.setAdresseMail(mailField.getText());
				utilisateurBean.setMotDePasse(String.valueOf(motDePasseField.getPassword()));
//				ArrayList<RoleBean> rolesList = new ArrayList<RoleBean>();
				ArrayList<Integer> rolesList = new ArrayList<Integer>();
				String selectedIndex = (String) profilCombo.getSelectedItem();
				Integer index = roleMaps.get(selectedIndex);
				RoleBean role = roles.get(index-1);
				rolesList.add(role.getId());
//				utilisateurBean.setRoleIds(rolesList);
				utilisateurBean.setRoleId(role.getId());

				try {
					HttpClientUtilisateur httpClientUtilisateur = new HttpClientUtilisateur(utilisateurDuLogin);
					httpClientUtilisateur.saveUtilisateur(utilisateurBean);
				} catch (InterruptedException | ExecutionException | IOException e) {
					e.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(105, 299, 106, 23);
		contentPane.add(btnNewButton);

		JLabel lblNewLabel_2_1 = new JLabel("Login:");
		lblNewLabel_2_1.setBounds(53, 183, 91, 20);
		contentPane.add(lblNewLabel_2_1);

		prenomField = new JTextField();
		prenomField.setColumns(10);
		prenomField.setBounds(192, 119, 215, 20);
		contentPane.add(prenomField);

		JLabel lblNewLabel_2_1_1 = new JLabel("Profil:");
		lblNewLabel_2_1_1.setBounds(53, 243, 91, 20);
		contentPane.add(lblNewLabel_2_1_1);

		motDePasseField = new JPasswordField();
		motDePasseField.setColumns(10);
		motDePasseField.setBounds(192, 214, 215, 20);
		contentPane.add(motDePasseField);

		JLabel lblNewLabel_2_1_1_1 = new JLabel("Mot de passe:");
		lblNewLabel_2_1_1_1.setBounds(53, 213, 91, 20);
		contentPane.add(lblNewLabel_2_1_1_1);

		JLabel lblNewLabel_2_2 = new JLabel("Prenom:");
		lblNewLabel_2_2.setBounds(53, 123, 91, 20);
		contentPane.add(lblNewLabel_2_2);

		mailField = new JTextField();
		mailField.setColumns(10);
		mailField.setBounds(192, 154, 215, 20);
		contentPane.add(mailField);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(53, 285, 352, 4);
		contentPane.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(55, 69, 352, 4);
		contentPane.add(separator_1);

		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setResizable(false);
	}
}
