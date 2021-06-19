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
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import fr.com.nfa019.restaurant.beans.CategorieBean;
import fr.com.nfa019.restaurant.beans.UtilisateurBean;
import fr.com.nfa019.restaurant.httpclients.HttpClientCategorie;

public class CategorieComponents {

	String password = "";
	String login = "";
	public Integer selectedItem;
	public CategorieBean selectedCategorie;
	
	public UtilisateurBean utilisateur;

	public CategorieComponents(UtilisateurBean utilisateur) {
//		this.password = password;
//		this.login = login;
		this.utilisateur = utilisateur;
	}

	public JScrollPane createCategorieTable(Boolean debug) {
		String[] columnNames = { "Nom" };

		JScrollPane scrollPane = null;
		try {
			final JTable table;
			HttpClientCategorie httpClientCategorie = new HttpClientCategorie(utilisateur);
			var categories = httpClientCategorie.getAllCategories();
			Object[][] data = new Object[categories.size()][];

			for (int i = 0; i < categories.size(); i++) {
				var produit = categories.get(i);
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
					selectedCategorie = categories.get(iSelectedIndex);
					selectedItem = categories.get(iSelectedIndex).getId();
				}
			});
			scrollPane = new JScrollPane(table);

		} catch (InterruptedException | ExecutionException | IOException e1) {
			e1.printStackTrace();
		}

		scrollPane.setSize(600, 400);
		return scrollPane;
	}

	public JPanel createButtonPane(JComponent panel2) {
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
		buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		buttonPane.add(Box.createHorizontalGlue());

		// create our jbutton
		JButton ajouterCategorie = new JButton("Ajouter");
		JButton deleteCategorieButton = new JButton("Delete");
		JButton editCategorieButton = new JButton("Editer");

		editCategorieButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectedItem == null) {
					JOptionPane.showMessageDialog(new JFrame(), "Vous devez d'abord cliquer sur un élément du tableau.",
							"Erreur", JOptionPane.ERROR_MESSAGE);
				} else {
					CategorieEditView categorieEditView = new CategorieEditView(utilisateur, selectedCategorie);
					categorieEditView.addWindowListener(new java.awt.event.WindowAdapter() {
						@Override
						public void windowClosing(java.awt.event.WindowEvent windowEvent) {
							refreshPanel2(panel2, CategorieComponents.this, buttonPane);
						}
					});
				}
			}

			private void refreshPanel2(JComponent panel2, CategorieComponents categorieComponents, JPanel buttonPane) {
				panel2.removeAll();

				JScrollPane jScrollPaneCategories = categorieComponents.createCategorieTable(false);
				jScrollPaneCategories.setAlignmentY(Component.TOP_ALIGNMENT);
				jScrollPaneCategories.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
				
				panel2.add(jScrollPaneCategories);
				panel2.add(buttonPane);
				panel2.revalidate();
				panel2.repaint();
			}
			
		});

		deleteCategorieButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectedItem == null) {
					// custom title, error icon
					JOptionPane.showMessageDialog(new JFrame(), "Vous devez d'abord cliquer sur un élément du tableau.",
							"Erreur", JOptionPane.ERROR_MESSAGE);
				} else {
					int result = JOptionPane.showConfirmDialog(panel2,
							"Voulez-vous vraiment supprimer l'élément " + selectedItem + "?", "Delete produit",
							JOptionPane.YES_NO_OPTION);
					System.out.println("Result: " + result);
					if (result == 0) {
						HttpClientCategorie clientCategorie = new HttpClientCategorie(utilisateur);
						try {
							clientCategorie.deleteCategorie(selectedItem);
							JOptionPane.showMessageDialog(new Frame(), "L'élément a été supprimé!");
							refreshPanel1(panel2, CategorieComponents.this, buttonPane);
						} catch (ExecutionException | InterruptedException | IOException e1) {
							e1.printStackTrace();
							JOptionPane.showMessageDialog(new Frame(), "Impossible de supprimer l'élément!", "Erreur",
									JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}

			private void refreshPanel1(JComponent panel1, CategorieComponents categorieComponents, JPanel buttonPane) {
				panel1.removeAll();

				JScrollPane jScrollPaneCategories = categorieComponents.createCategorieTable(false);
				jScrollPaneCategories.setAlignmentY(Component.TOP_ALIGNMENT);
				jScrollPaneCategories.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
				
				panel1.add(jScrollPaneCategories);
				panel1.add(buttonPane);
				panel1.revalidate();
				panel1.repaint();
			}
		});

		buttonPane.add(ajouterCategorie);
		buttonPane.add(Box.createRigidArea(new Dimension(20, 10)));
		buttonPane.add(editCategorieButton);
		buttonPane.add(Box.createRigidArea(new Dimension(20, 10)));
		buttonPane.add(deleteCategorieButton);

		ajouterCategorie.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CategorieView createView = new CategorieView(utilisateur);

				createView.setVisible(true);
				createView.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent e) {
						refreshPanel1(panel2, CategorieComponents.this, buttonPane);
					}
				});

			}

			private void refreshPanel1(JComponent panel1, CategorieComponents categorieComponents, JPanel buttonPane) {
				panel1.removeAll();

				JScrollPane jScrollPaneCategories = categorieComponents.createCategorieTable(false);
				jScrollPaneCategories.setAlignmentY(Component.TOP_ALIGNMENT);
				jScrollPaneCategories.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
				
				panel1.add(jScrollPaneCategories);
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
