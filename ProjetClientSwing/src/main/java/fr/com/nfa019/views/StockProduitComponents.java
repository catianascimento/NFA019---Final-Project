package fr.com.nfa019.views;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import fr.com.nfa019.restaurant.beans.StockProduitBean;
import fr.com.nfa019.restaurant.beans.UtilisateurBean;
import fr.com.nfa019.restaurant.httpclients.HttpClientProduit;
import fr.com.nfa019.restaurant.httpclients.HttpClientStock;

public class StockProduitComponents {

	String password = "";
	String login = "";
	public Long selectedItem;
	public StockProduitBean selectedStock;
	public Boolean onlyExpired = false;
	
	UtilisateurBean utilisateur;

	public StockProduitComponents(UtilisateurBean utilisateur) {
//		this.password = password;
//		this.login = login;
		
		this.utilisateur = utilisateur;
	}

	public JScrollPane createStockTable(Boolean debug, Boolean expired) {
		this.onlyExpired = expired;
		String[] columnNames = { "Date d'insertion", "Quantite", "Status", "Produit",
				"Conservation (en jours)" };

		JScrollPane scrollPane = null;
		try {
			final JTable table;

			HttpClientStock httpClientStock = new HttpClientStock(this.utilisateur);
			final List<StockProduitBean> stocks;
			if (onlyExpired) {
				stocks = httpClientStock.getAllExpiredStocks();
			} else {
				stocks = httpClientStock.getAllStocks();
			}
			Object[][] data = new Object[stocks.size()][];

			for (int i = 0; i < stocks.size(); i++) {
				var stock = stocks.get(i);
				HttpClientProduit httpClientProduit = new HttpClientProduit(this.utilisateur);
				var produit = httpClientProduit.getProduitDetailsById(stock.getProduitId());
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				String formatDateTime = stock.getDataDInsertion().format(formatter);
				String array[] = { formatDateTime,
						stock.getQuantite().toString(), stock.getStatus().toString(), produit.getNom(),
						produit.getDureeDeConservationEnJours().toString() };
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
					selectedStock = stocks.get(iSelectedIndex);
					selectedItem = stocks.get(iSelectedIndex).getId();
				}
			});
			scrollPane = new JScrollPane(table);

		} catch (InterruptedException | ExecutionException | IOException e1) {
			e1.printStackTrace();
		}

		scrollPane.setSize(600, 400);
		return scrollPane;
	}

	public void printDebugData(JTable table) {
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

	public JPanel createButtonPane(JPanel filters, JComponent panel1) {
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
		buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		buttonPane.add(Box.createHorizontalGlue());

		// create our jbutton
		JButton ajouterProduitStock = new JButton("Ajouter Produit");
		JButton ajouterStock = new JButton("Ajouter Produit");
		JButton deleteStockButton = new JButton("Delete");
		JButton editStockButton = new JButton("Editer");

		ajouterProduitStock.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectedItem == null) {
					JOptionPane.showMessageDialog(new JFrame(), "Vous devez d'abord cliquer sur un élément du tableau.",
							"Erreur", JOptionPane.ERROR_MESSAGE);
				} else {
					StockProduitView stockEditView = new StockProduitView(utilisateur, selectedStock);
					stockEditView.addWindowListener(new java.awt.event.WindowAdapter() {
						@Override
						public void windowClosing(java.awt.event.WindowEvent windowEvent) {
							refreshPanel2(filters, panel1, StockProduitComponents.this, buttonPane);
						}
					});
				}
			}

			private void refreshPanel2(JPanel filters, JComponent panel5, StockProduitComponents stockComponents,
					JPanel buttonPane) {
				panel5.removeAll();

				JScrollPane jScrollPaneStocks = stockComponents.createStockTable(false, false);
				jScrollPaneStocks.setAlignmentY(Component.TOP_ALIGNMENT);
				jScrollPaneStocks.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

				panel5.add(filters);
				panel5.add(jScrollPaneStocks);
				panel5.add(buttonPane);
				panel5.revalidate();
				panel5.repaint();
			}

		});

		editStockButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectedItem == null) {
					JOptionPane.showMessageDialog(new JFrame(), "Vous devez d'abord cliquer sur un élément du tableau.",
							"Erreur", JOptionPane.ERROR_MESSAGE);
				} else {
					StockEditView stockEditView = new StockEditView(utilisateur, selectedStock);
					stockEditView.addWindowListener(new java.awt.event.WindowAdapter() {
						@Override
						public void windowClosing(java.awt.event.WindowEvent windowEvent) {
							refreshPanel2(panel1, StockProduitComponents.this, buttonPane);
						}
					});
				}
			}

			private void refreshPanel2(JComponent panel5, StockProduitComponents stockComponents, JPanel buttonPane) {
				panel5.removeAll();

				JScrollPane jScrollPaneStocks = stockComponents.createStockTable(false, false);
				jScrollPaneStocks.setAlignmentY(Component.TOP_ALIGNMENT);
				jScrollPaneStocks.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

				panel5.add(filters);
				panel5.add(jScrollPaneStocks);
				panel5.add(buttonPane);
				panel5.revalidate();
				panel5.repaint();
			}

		});

		deleteStockButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectedItem == null) {
					// custom title, error icon
					JOptionPane.showMessageDialog(new JFrame(), "Vous devez d'abord cliquer sur un élément du tableau.",
							"Erreur", JOptionPane.ERROR_MESSAGE);
				} else {
					int result = JOptionPane.showConfirmDialog(panel1,
							"Voulez-vous vraiment supprimer l'élément " + selectedItem + "?", "Delete produit",
							JOptionPane.YES_NO_OPTION);
					System.out.println("Result: " + result);
					if (result == 0) {
						HttpClientStock clientStock = new HttpClientStock(utilisateur);
						try {
							clientStock.deleteStock(selectedItem);
							JOptionPane.showMessageDialog(new Frame(), "L'élément a été supprimé!");
							refreshPanel1(panel1, StockProduitComponents.this, buttonPane);
						} catch (ExecutionException | InterruptedException | IOException e1) {
							e1.printStackTrace();
							JOptionPane.showMessageDialog(new Frame(), "Impossible de supprimer l'élément!", "Erreur",
									JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}

			private void refreshPanel1(JComponent panel1, StockProduitComponents stockComponents, JPanel buttonPane) {
				panel1.removeAll();

				JScrollPane jScrollPaneStocks = stockComponents.createStockTable(false, false);
				jScrollPaneStocks.setAlignmentY(Component.TOP_ALIGNMENT);
				jScrollPaneStocks.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

				panel1.add(filters);
				panel1.add(jScrollPaneStocks);
				panel1.add(buttonPane);
				panel1.revalidate();
				panel1.repaint();
			}
		});

//		buttonPane.add(ajouterProduitStock);
//		buttonPane.add(Box.createRigidArea(new Dimension(20, 10)));
		buttonPane.add(ajouterStock);
		buttonPane.add(Box.createRigidArea(new Dimension(20, 10)));
		buttonPane.add(editStockButton);
		buttonPane.add(Box.createRigidArea(new Dimension(20, 10)));
		buttonPane.add(deleteStockButton);

		ajouterStock.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				StockProduitCreateView createView = new StockProduitCreateView(utilisateur);

				createView.setVisible(true);
				createView.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent e) {
						refreshPanel1(panel1, StockProduitComponents.this, buttonPane);
					}
				});

			}

			private void refreshPanel1(JComponent panel1, StockProduitComponents stockProduitComponents,
					JPanel buttonPane) {
				panel1.removeAll();

				JScrollPane jScrollPaneStockProduits = stockProduitComponents.createStockTable(false, false);
				jScrollPaneStockProduits.setAlignmentY(Component.TOP_ALIGNMENT);
				jScrollPaneStockProduits.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

				panel1.add(filters);
				panel1.add(jScrollPaneStockProduits);
				panel1.add(buttonPane);
				panel1.revalidate();
				panel1.repaint();
			}
		});

		return buttonPane;
	}

}
