package fr.com.nfa019.views;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutionException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.table.DefaultTableCellRenderer;

import fr.com.nfa019.restaurant.beans.RefrigerateurBean;
import fr.com.nfa019.restaurant.beans.RefrigerateurTemperatureBean;
import fr.com.nfa019.restaurant.beans.UtilisateurBean;
import fr.com.nfa019.restaurant.httpclients.HttpClientRefrigerateur;
import fr.com.nfa019.restaurant.httpclients.HttpClientStock;

public class RefrigerateurLogTemperaturesView extends JFrame {

	private JPanel contentPane;
	private JTextField textField_1;
//	String password = "";
//	String login = "";
	public RefrigerateurTemperatureBean temperature;
//	private JTable table;
	public Long selectedItem;
//	public ProduitBean selectedProduit;
	private JTable table_1;
	private JScrollPane scrollPane_1;
	
	UtilisateurBean utilisateur;

	/**
	 * Create the frame.
	 */
	public RefrigerateurLogTemperaturesView(UtilisateurBean utilisateur, RefrigerateurBean refrigerateur) {
//		this.login = login;
//		this.password = password;
		
		this.utilisateur = utilisateur;
		setResizable(false);
		setBounds(100, 100, 453, 405);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JLabel lblNewLabel_1 = new JLabel("HISTORIQUE TEMPERATURES");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(105, 10, 224, 20);
		contentPane.add(lblNewLabel_1);

		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setText(refrigerateur.getCode().toString());
		textField_1.setColumns(10);
		textField_1.setBounds(175, 54, 246, 20);
		contentPane.add(textField_1);

		JLabel lblNewLabel_1_1 = new JLabel("Refrigerateur:");
		lblNewLabel_1_1.setBounds(33, 56, 91, 14);
		contentPane.add(lblNewLabel_1_1);

		JLabel lblNewLabel_1_1_1 = new JLabel("Temperatures enregistrées:");
		lblNewLabel_1_1_1.setBounds(33, 80, 224, 14);
		contentPane.add(lblNewLabel_1_1_1);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(33, 40, 388, 4);
		contentPane.add(separator_1);

		JList produits = new JList();
		produits.setBounds(33, 242, 375, -147);
		HttpClientStock httpClientStock = new HttpClientStock(utilisateur);

		table_1 = new JTable();
		table_1.setPreferredScrollableViewportSize(new Dimension(550, 300));
		table_1.setFillsViewportHeight(true);

		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) table_1.getDefaultRenderer(Object.class);
		table_1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane table = createTemperaturesTable(refrigerateur, true);
		table.setBounds(33, 104, 388, 247);
		scrollPane_1.setViewportView(table_1);
		contentPane.add(table);

		ListSelectionModel selectionModel = table_1.getSelectionModel();

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

	public JScrollPane createTemperaturesTable(RefrigerateurBean refrigerateur, Boolean debug) {
		String[] columnNames = { "Min Temperature", "Max Temperature", "Datetime" };
//		JScrollPane scrollPane = null;
		try {
			HttpClientRefrigerateur httpClientRefrigerateur = new HttpClientRefrigerateur(utilisateur);
			var refrigerateurTemperatures = httpClientRefrigerateur
					.getAllRefrigerateurTemperatures(refrigerateur.getId());
			Object[][] data = new Object[refrigerateurTemperatures.size()][];

			for (int i = 0; i < refrigerateurTemperatures.size(); i++) {
				var temperature = refrigerateurTemperatures.get(i);
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
				String formatDateTime = temperature.getDateTime().format(formatter);
				
				String array[] = { String.valueOf(temperature.getMinTemperature()),
						String.valueOf(temperature.getMaxTemperature()), formatDateTime.toString() };
				data[i] = array;
			}
			table_1 = new JTable(data, columnNames);
			DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) table_1.getDefaultRenderer(Object.class);
			renderer.setHorizontalAlignment(SwingConstants.CENTER);

			if (debug) {
				table_1.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						printDebugData(table_1);
					}
				});
			}

			scrollPane_1 = new JScrollPane();
			scrollPane_1.setSize(600, 400);

		} catch (InterruptedException | ExecutionException | IOException e1) {
			e1.printStackTrace();
		}
		return scrollPane_1;
	}

	public static void printDebugData(JTable table) {
		int numRows = table.getRowCount();
		int numCols = table.getColumnCount();
		javax.swing.table.TableModel model = table.getModel();

		System.out.println("Value of data: ");
		for (int i = 0; i < numRows; i++) {
			System.out.print("    row " + i + ":");
			for (int j = 0; j < numCols; j++) {
				System.out.print("  " + model.getValueAt(i, j));
			}
			System.out.println();
		}
		System.out.println("--------------------------");
	}
}
