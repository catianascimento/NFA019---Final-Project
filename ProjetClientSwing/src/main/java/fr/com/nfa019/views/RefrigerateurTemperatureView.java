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
import fr.com.nfa019.restaurant.beans.RefrigerateurTemperatureBean;
import fr.com.nfa019.restaurant.beans.UtilisateurBean;
import fr.com.nfa019.restaurant.httpclients.HttpClientRefrigerateur;
import java.awt.Font;
import javax.swing.JSlider;

import java.util.Enumeration;
import java.util.Hashtable;
import javax.swing.JSeparator;

public class RefrigerateurTemperatureView extends JFrame {

	private JPanel contentPane;
	String password = "";
	String login = "";
	public RefrigerateurTemperatureBean refrigerateurTemperature;
	public Integer refrigerateurId;
	private JSlider maxTemp;
	private JSlider minTemp;
	
	UtilisateurBean utilisateur;

	/**
	 * Create the frame.
	 */
	public RefrigerateurTemperatureView(UtilisateurBean utilisateur, RefrigerateurBean refrigerateur) {
//		this.refrigerateurTemperature = new RefrigerateurTemperatureBean();
//		this.login = login;
//		this.password = password;
		
		this.utilisateur = utilisateur;
		setResizable(false);
		setBounds(100, 100, 422, 292);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("SAVE TEMPERATURE");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(140, 26, 231, 20);
		contentPane.add(lblNewLabel_1);

		JButton btnNewButton = new JButton("Save");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				refrigerateurTemperature = new RefrigerateurTemperatureBean();
				refrigerateurTemperature.setMinTemperature(Double.valueOf(minTemp.getValue()));
				refrigerateurTemperature.setMaxTemperature(Double.valueOf(maxTemp.getValue()));

				try {
					HttpClientRefrigerateur httpClientRefrigerateur = new HttpClientRefrigerateur(utilisateur);
					httpClientRefrigerateur.saveRefrigerateurTemperature(refrigerateur.getId(),
							refrigerateurTemperature);

				} catch (InterruptedException | ExecutionException | IOException e) {
					e.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(103, 210, 100, 23);
		contentPane.add(btnNewButton);

		JButton btnCancel = new JButton("Annuler");
		btnCancel.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnCancel.setBounds(227, 210, 89, 23);
		contentPane.add(btnCancel);

//		JLabel lblNewLabel_1_1 = new JLabel("Min Temperature:");
//		lblNewLabel_1_1.setBounds(100, 123, 91, 14);
//		contentPane.add(lblNewLabel_1_1);

		JLabel lblNewLabel_1_1_1 = new JLabel("Temperature Min:");
		lblNewLabel_1_1_1.setBounds(40, 86, 107, 14);
		contentPane.add(lblNewLabel_1_1_1);

		JLabel lblNewLabel_1_1_1_1 = new JLabel("Temperature Max:");
		lblNewLabel_1_1_1_1.setBounds(40, 133, 107, 14);
		contentPane.add(lblNewLabel_1_1_1_1);

		minTemp = new JSlider();
		minTemp.setMinimum(-5);
		minTemp.setMaximum(5);
		minTemp.setMinorTickSpacing(1);
		minTemp.setMajorTickSpacing(2);
		minTemp.setPaintTicks(true);
		minTemp.setBounds(149, 74, 222, 45);

		Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
		labelTable.put(-5, new JLabel("-5"));
		labelTable.put(5, new JLabel("5"));
		minTemp.setLabelTable(labelTable);
		minTemp.setPaintLabels(true);
		contentPane.add(minTemp);

		maxTemp = new JSlider();
		maxTemp.setMinimum(-5);
		maxTemp.setMaximum(5);
		maxTemp.setMinorTickSpacing(1);
		maxTemp.setMajorTickSpacing(2);
		maxTemp.setPaintTicks(true);
		maxTemp.setBounds(149, 129, 222, 44);
		maxTemp.setPaintLabels(true);
		maxTemp.setLabelTable(labelTable);
		
		contentPane.add(maxTemp);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(40, 191, 332, 2);
		contentPane.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(40, 56, 332, 2);
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
