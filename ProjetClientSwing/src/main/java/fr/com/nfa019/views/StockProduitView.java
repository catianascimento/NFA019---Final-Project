package fr.com.nfa019.views;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.table.DefaultTableCellRenderer;

import fr.com.nfa019.restaurant.beans.ProduitBean;
import fr.com.nfa019.restaurant.beans.StockProduitBean;
import fr.com.nfa019.restaurant.beans.UtilisateurBean;
import fr.com.nfa019.restaurant.httpclients.HttpClientProduit;
import fr.com.nfa019.restaurant.httpclients.HttpClientStock;
import fr.com.nfa019.restaurant.utils.Status;

public class StockProduitView extends JFrame {

	private JPanel contentPane;
	private JTextField textField_1;
	String password = "";
	String login = "";
	public StockProduitBean stock;
	private JTable table;
	public Long selectedItem;
	public ProduitBean selectedProduit;
	
	UtilisateurBean utilisateur;

	/**
	 * Create the frame.
	 */
	public StockProduitView(UtilisateurBean utilisateur, StockProduitBean stock) {
//		this.login = login;
//		this.password = password;
		
		this.utilisateur = utilisateur;
		setResizable(false);
		setBounds(100, 100, 576, 406);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JComboBox comboBox = new JComboBox();
		JLabel lblNewLabel_1 = new JLabel("AJOUTER PRODUIT-STOCK");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(186, 0, 210, 20);
		contentPane.add(lblNewLabel_1);

//		textField_1 = new JTextField();
//		textField_1.setEditable(false);
//		textField_1.setText(stock.getIdentifiantStock().toString());
//		textField_1.setColumns(10);
//		textField_1.setBounds(175, 39, 350, 20);
//		contentPane.add(textField_1);

		JButton btnNewButton = new JButton("Ajouter");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				StockProduitBean stockProduitBean = new StockProduitBean();
				stockProduitBean.setProduitId(Long.valueOf(comboBox.getSelectedIndex()));
//				stockProduitBean.setIdentifiantStock(stock.getId());
				stockProduitBean.setStatus(Status.OK);

				try {
					HttpClientStock httpClientStock = new HttpClientStock(utilisateur);
					httpClientStock.saveStockProduit(stockProduitBean);

				} catch (InterruptedException | ExecutionException | IOException e) {
					e.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(76, 333, 89, 23);
		contentPane.add(btnNewButton);

		JButton btnCancel = new JButton("Changer Status");
		btnCancel.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnCancel.setBounds(226, 333, 128, 23);
		contentPane.add(btnCancel);

		JLabel lblNewLabel_1_1_1 = new JLabel("Produits dans le Stock:");
		lblNewLabel_1_1_1.setBounds(33, 80, 156, 14);
		contentPane.add(lblNewLabel_1_1_1);

		JLabel lblNewLabel_1_1_1_1 = new JLabel("Produits disponibles:");
		lblNewLabel_1_1_1_1.setBounds(26, 282, 139, 14);
		contentPane.add(lblNewLabel_1_1_1_1);

		HashMap<String, Long> categorieMaps = new HashMap<String, Long>();
		List<ProduitBean> produitsDisponibles;
		try {
			HttpClientProduit httpClientProduit = new HttpClientProduit(utilisateur);
			produitsDisponibles = httpClientProduit.getAllAvailablesProduits();
			String[] array = new String[produitsDisponibles.size()];
			for (int i = 0; i < array.length; i++) {
				array[i] = produitsDisponibles.get(i).getNom();
				categorieMaps.put(produitsDisponibles.get(i).getNom(), produitsDisponibles.get(i).getId());
			}
			comboBox.setModel(new DefaultComboBoxModel<String>(array));
		} catch (InterruptedException | ExecutionException | IOException e1) {
			e1.printStackTrace();
		}
		comboBox.setBounds(175, 278, 350, 23);
		contentPane.add(comboBox);

		JButton btnSupprimmer = new JButton("Supprimmer");
		btnSupprimmer.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnSupprimmer.setBounds(407, 333, 104, 23);
		contentPane.add(btnSupprimmer);

		JSeparator separator = new JSeparator();
		separator.setBounds(26, 311, 499, 2);
		contentPane.add(separator);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(26, 270, 499, 2);
		contentPane.add(separator_1);

		JList produits = new JList();
		produits.setBounds(33, 242, 375, -147);
		HttpClientStock httpClientStock = new HttpClientStock(utilisateur);

//		produits = new JList(httpClientStock.getAllProduitsFromStocks(stock.getId()));

//		JScrollPane scrollPane = new JScrollPane();
//		scrollPane.setBounds(153, 246, 297, -167);
//		scrollPane.add(returnJListOfProduits(stock));
//		contentPane.add(scrollPane);

//		JPanel panel = returnJListOfProduits(stock);
//		panel.setBounds(175, 75, 350, 160);
//		scrollPane.add(panel);
//		contentPane.add(panel);
		
		
//		table = new JTable();
		JScrollPane table = createProduitTable(stock, true);
		table.setBounds(175, 75, 350, 160);
//		table.setBounds(131, 114, -106, 121);
		contentPane.add(table);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(26, 28, 499, 2);
		contentPane.add(separator_2);

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

	public JScrollPane createProduitTable(StockProduitBean stock, Boolean debug) {
//		String[] columnNames = { "Nom", "Date d'insertion", "Status" };
		String[] columnNames = { "Nom" };
		JScrollPane scrollPane = null;
		try {
			final JTable table;
			HttpClientStock httpClientStock = new HttpClientStock(utilisateur);
			var produits = httpClientStock.getAllProduitsFromStocks(stock.getId());
//			var categories = httpClientCategorie.getAllCategories();
			Object[][] data = new Object[produits.size()][];

			for (int i = 0; i < produits.size(); i++) {
				var produit =produits.get(i);
				String array[] = { produit.getNom() };
				data[i] = array;
			}

			table = new JTable(data, columnNames);
			table.setPreferredScrollableViewportSize(new Dimension(550, 300));
			table.setFillsViewportHeight(true);

			DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) table.getDefaultRenderer(Object.class);
			renderer.setHorizontalAlignment(SwingConstants.CENTER);
			
			if (debug) {
				table.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						printDebugData(table);
					}
				});
			}
			
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

			ListSelectionModel selectionModel = table.getSelectionModel();

			selectionModel.addListSelectionListener(new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent e) {
					handleSelectionEvent(e);
				}

				public void handleSelectionEvent(ListSelectionEvent e) {
					if (e.getValueIsAdjusting())
						return;

					String strSource = e.getSource().toString();
					int start = strSource.indexOf("{") + 1, stop = strSource.length() - 1;
					var iSelectedIndex = Integer.parseInt(strSource.substring(start, stop));
					selectedProduit = produits.get(iSelectedIndex);
					selectedItem = produits.get(iSelectedIndex).getId();
				}
			});
			scrollPane = new JScrollPane(table);

		} catch (InterruptedException | ExecutionException | IOException e1) {
			e1.printStackTrace();
		}

		scrollPane.setSize(600, 400);
		return scrollPane;
	}

//	public JPanel createButtonPane(JComponent panel2) {
//		JPanel buttonPane = new JPanel();
//		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
//		buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
//		buttonPane.add(Box.createHorizontalGlue());
//
//		// create our jbutton
//		JButton ajouterCategorie = new JButton("Ajouter");
//		JButton deleteCategorieButton = new JButton("Delete");
//		JButton editCategorieButton = new JButton("Editer");
//
//		editCategorieButton.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				if (selectedItem == null) {
//					JOptionPane.showMessageDialog(new JFrame(), "Vous devez d'abord cliquer sur un élément du tableau.",
//							"Erreur", JOptionPane.ERROR_MESSAGE);
//				} else {
//					CategorieEditView categorieEditView = new CategorieEditView(login, password, selectedCategorie);
//					categorieEditView.addWindowListener(new java.awt.event.WindowAdapter() {
//						@Override
//						public void windowClosing(java.awt.event.WindowEvent windowEvent) {
//							refreshPanel2(panel2, CategorieComponents.this, buttonPane);
//						}
//					});
//				}
//			}
//
//			private void refreshPanel2(JComponent panel2, CategorieComponents categorieComponents, JPanel buttonPane) {
//				panel2.removeAll();
//
//				JScrollPane jScrollPaneCategories = categorieComponents.createCategorieTable(false);
//				jScrollPaneCategories.setAlignmentY(Component.TOP_ALIGNMENT);
//				jScrollPaneCategories.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
//				
//				panel2.add(jScrollPaneCategories);
//				panel2.add(buttonPane);
//				panel2.revalidate();
//				panel2.repaint();
//			}
//			
//		});
//
//		deleteCategorieButton.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				if (selectedItem == null) {
//					// custom title, error icon
//					JOptionPane.showMessageDialog(new JFrame(), "Vous devez d'abord cliquer sur un élément du tableau.",
//							"Erreur", JOptionPane.ERROR_MESSAGE);
//				} else {
//					int result = JOptionPane.showConfirmDialog(panel2,
//							"Voulez-vous vraiment supprimer l'élément " + selectedItem + "?", "Delete produit",
//							JOptionPane.YES_NO_OPTION);
//					System.out.println("Result: " + result);
//					if (result == 0) {
//						HttpClientCategorie clientCategorie = new HttpClientCategorie(login, String.valueOf(password));
//						try {
//							clientCategorie.deleteCategorie(selectedItem);
//							JOptionPane.showMessageDialog(new Frame(), "L'élément a été supprimé!");
//							refreshPanel1(panel2, CategorieComponents.this, buttonPane);
//						} catch (ExecutionException | InterruptedException | IOException e1) {
//							e1.printStackTrace();
//							JOptionPane.showMessageDialog(new Frame(), "Impossible de supprimer l'élément!", "Erreur",
//									JOptionPane.ERROR_MESSAGE);
//						}
//					}
//				}
//			}
//
//			private void refreshPanel1(JComponent panel1, CategorieComponents categorieComponents, JPanel buttonPane) {
//				panel1.removeAll();
//
//				JScrollPane jScrollPaneCategories = categorieComponents.createCategorieTable(false);
//				jScrollPaneCategories.setAlignmentY(Component.TOP_ALIGNMENT);
//				jScrollPaneCategories.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
//				
//				panel1.add(jScrollPaneCategories);
//				panel1.add(buttonPane);
//				panel1.revalidate();
//				panel1.repaint();
//			}
//		});
//
//		buttonPane.add(ajouterCategorie);
//		buttonPane.add(Box.createRigidArea(new Dimension(20, 10)));
//		buttonPane.add(editCategorieButton);
//		buttonPane.add(Box.createRigidArea(new Dimension(20, 10)));
//		buttonPane.add(deleteCategorieButton);
//
//		ajouterCategorie.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				CategorieView createView = new CategorieView(login, password);
//
//				createView.setVisible(true);
//				createView.addWindowListener(new WindowAdapter() {
//					@Override
//					public void windowClosing(WindowEvent e) {
//						refreshPanel1(panel2, CategorieComponents.this, buttonPane);
//					}
//				});
//
//			}
//
//			private void refreshPanel1(JComponent panel1, CategorieComponents categorieComponents, JPanel buttonPane) {
//				panel1.removeAll();
//
//				JScrollPane jScrollPaneCategories = categorieComponents.createCategorieTable(false);
//				jScrollPaneCategories.setAlignmentY(Component.TOP_ALIGNMENT);
//				jScrollPaneCategories.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
//				
//				panel1.add(jScrollPaneCategories);
//				panel1.add(buttonPane);
//				panel1.revalidate();
//				panel1.repaint();
//			}
//		});
//
//		return buttonPane;
//	}
//
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
