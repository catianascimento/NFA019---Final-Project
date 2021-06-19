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
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import fr.com.nfa019.restaurant.beans.ProduitBean;
import fr.com.nfa019.restaurant.beans.UtilisateurBean;
import fr.com.nfa019.restaurant.httpclients.HttpClientProduit;

public class ProduitComponents {

	String password = "";
	String login = "";
	public Long selectedItem;
	public ProduitBean selectedProduit;

	public UtilisateurBean utilisateur;

	public ProduitComponents(UtilisateurBean utilisateur) {
//		this.password = password;
//		this.login = login;
		this.utilisateur = utilisateur;
	}

	public JScrollPane createTable(Boolean debug) {
		String[] columnNames = { "Id", "Nom", "Categorie", "Duree de conservation" };

		JTree tree = new JTree();
		tree.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		JScrollPane scrollPane = new JScrollPane(tree);
		scrollPane.setBounds(0, 0, 300, 300);
		try {
			final JTable table;

			HttpClientProduit httpClientProduit = new HttpClientProduit(this.utilisateur);
			var produits = httpClientProduit.getAllProduits();
			Object[][] data = new Object[produits.size()][];

			for (int i = 0; i < produits.size(); i++) {
				var produit = produits.get(i);
				Object array[] = { produit.getId(), produit.getNom(), produit.getCategorie().getNom(),
						String.valueOf(produit.getDureeDeConservationEnJours()) };
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

			table.removeColumn(table.getColumnModel().getColumn(0));
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

	public JPanel createButtonPane(JComponent panel1) {
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
		buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		buttonPane.add(Box.createHorizontalGlue());

		// create our jbutton
		JButton ajouterProduit = new JButton("Ajouter");
		JButton deleteProduitButton = new JButton("Delete");
		JButton editProduitButton = new JButton("Editer");

		editProduitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectedItem == null) {
					JOptionPane.showMessageDialog(new JFrame(), "Vous devez d'abord cliquer sur un élément du tableau.",
							"Erreur", JOptionPane.ERROR_MESSAGE);
				} else {
					ProduitEditView produitEditView = new ProduitEditView(utilisateur, selectedProduit);
					produitEditView.addWindowListener(new java.awt.event.WindowAdapter() {
						@Override
						public void windowClosing(java.awt.event.WindowEvent windowEvent) {
							refreshPanel1(panel1, ProduitComponents.this, buttonPane);
						}
					});
				}
			}

			private void refreshPanel1(JComponent panel1, ProduitComponents produitComponents, JPanel buttonPane) {
				panel1.removeAll();

				JScrollPane jScrollPaneProduits = produitComponents.createTable(false);
				jScrollPaneProduits.setAlignmentY(Component.TOP_ALIGNMENT);
				jScrollPaneProduits.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

				panel1.add(jScrollPaneProduits);
				panel1.add(buttonPane);
				panel1.revalidate();
				panel1.repaint();
			}
		});

		deleteProduitButton.addActionListener(new ActionListener() {

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
						HttpClientProduit clientProduit = new HttpClientProduit(utilisateur);
						try {
							clientProduit.deleteProduit(selectedItem);
							JOptionPane.showMessageDialog(new Frame(), "L'élément a été supprimé!");
							refreshPanel1(panel1, ProduitComponents.this, buttonPane);
						} catch (ExecutionException | InterruptedException | IOException e1) {
							e1.printStackTrace();
							JOptionPane.showMessageDialog(new Frame(), "Impossible de supprimer l'élément!", "Erreur",
									JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}

			private void refreshPanel1(JComponent panel1, ProduitComponents produitComponents, JPanel buttonPane) {
				panel1.removeAll();

				JScrollPane jScrollPaneProduits = produitComponents.createTable(false);
				jScrollPaneProduits.setAlignmentY(Component.TOP_ALIGNMENT);
				jScrollPaneProduits.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

				panel1.add(jScrollPaneProduits);
				panel1.add(buttonPane);
				panel1.revalidate();
				panel1.repaint();
			}
		});

		buttonPane.add(ajouterProduit);
		buttonPane.add(Box.createRigidArea(new Dimension(20, 10)));
		buttonPane.add(editProduitButton);
		buttonPane.add(Box.createRigidArea(new Dimension(20, 10)));
		buttonPane.add(deleteProduitButton);

		ajouterProduit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ProduitView createView = new ProduitView(utilisateur);

				createView.setVisible(true);
				createView.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent e) {
						refreshPanel1(panel1, ProduitComponents.this, buttonPane);
					}
				});

			}

			private void refreshPanel1(JComponent panel1, ProduitComponents produitComponents, JPanel buttonPane) {
				panel1.removeAll();

				JScrollPane jScrollPaneProduits = produitComponents.createTable(false);
				jScrollPaneProduits.setAlignmentY(Component.TOP_ALIGNMENT);
				jScrollPaneProduits.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

				panel1.add(jScrollPaneProduits);
				panel1.add(buttonPane);
				panel1.revalidate();
				panel1.repaint();
			}
		});

		return buttonPane;
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
