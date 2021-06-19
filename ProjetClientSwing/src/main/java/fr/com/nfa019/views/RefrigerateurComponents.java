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

import fr.com.nfa019.restaurant.beans.RefrigerateurBean;
import fr.com.nfa019.restaurant.beans.UtilisateurBean;
import fr.com.nfa019.restaurant.httpclients.HttpClientRefrigerateur;

public class RefrigerateurComponents {

	String password;
	String login;
	public Long selectedItem;
	public RefrigerateurBean selectedRefrigerateur;

	public UtilisateurBean utilisateur;

	public RefrigerateurComponents(UtilisateurBean utilisateur) {
//		this.password = password;
//		this.login = login;
//		
		this.utilisateur = utilisateur;
	}

	public JScrollPane createRefrigerateurTable(Boolean debug) {
		String[] columnNames = { "Code" };

		JScrollPane scrollPane = null;
		try {
			final JTable table;
			HttpClientRefrigerateur clientRefrigerateur = new HttpClientRefrigerateur(utilisateur);
			var refrigerateurs = clientRefrigerateur.getAllRefrigerateurs();
			Object[][] data = new Object[refrigerateurs.size()][];

			for (int i = 0; i < refrigerateurs.size(); i++) {
				var refrigerateur = refrigerateurs.get(i);
				String array[] = { refrigerateur.getCode() };
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
					selectedRefrigerateur = refrigerateurs.get(iSelectedIndex);
					selectedItem = refrigerateurs.get(iSelectedIndex).getId();
				}
			});
			scrollPane = new JScrollPane(table);

		} catch (InterruptedException | ExecutionException | IOException e1) {
			e1.printStackTrace();
		}

		scrollPane.setSize(600, 400);
		return scrollPane;
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

	public JPanel createButtonPane(JComponent panel3) {
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
		buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		buttonPane.add(Box.createHorizontalGlue());

		JButton showLogOfTemperature = new JButton("Voir Historique");
		JButton enregistrerTemperature = new JButton("Enregistrer Temperature");
		JButton ajouterRefrigerateur = new JButton("Ajouter");
		JButton deleteRefrigerateurButton = new JButton("Delete");
		JButton editRefrigerateurButton = new JButton("Editer");

		showLogOfTemperature.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RefrigerateurLogTemperaturesView logView = new RefrigerateurLogTemperaturesView(utilisateur,
						selectedRefrigerateur);

				logView.setVisible(true);
				logView.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent e) {
						refreshPanel1(panel3, RefrigerateurComponents.this, buttonPane);
					}
				});

			}

			private void refreshPanel1(JComponent panel1, RefrigerateurComponents refrigerateurComponents,
					JPanel buttonPane) {
				panel1.removeAll();

				JScrollPane jScrollPaneRefrigerateurs = refrigerateurComponents.createRefrigerateurTable(false);
				jScrollPaneRefrigerateurs.setAlignmentY(Component.TOP_ALIGNMENT);
				jScrollPaneRefrigerateurs.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

				panel1.add(jScrollPaneRefrigerateurs);
				panel1.add(buttonPane);
				panel1.revalidate();
				panel1.repaint();
			}
		});

		enregistrerTemperature.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectedItem == null) {
					JOptionPane.showMessageDialog(new JFrame(), "Vous devez d'abord cliquer sur un élément du tableau.",
							"Erreur", JOptionPane.ERROR_MESSAGE);
				} else {
					RefrigerateurTemperatureView refrigerateurTemperatureView = new RefrigerateurTemperatureView(
							utilisateur, selectedRefrigerateur);
					refrigerateurTemperatureView.addWindowListener(new java.awt.event.WindowAdapter() {
						@Override
						public void windowClosing(java.awt.event.WindowEvent windowEvent) {
							refreshPanel2(panel3, RefrigerateurComponents.this, buttonPane);
						}
					});
				}
			}

			private void refreshPanel2(JComponent panel3, RefrigerateurComponents refrigerateurComponents,
					JPanel buttonPane) {
				panel3.removeAll();

				JScrollPane jScrollPaneRefrigerateurs = refrigerateurComponents.createRefrigerateurTable(false);
				jScrollPaneRefrigerateurs.setAlignmentY(Component.TOP_ALIGNMENT);
				jScrollPaneRefrigerateurs.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

				panel3.add(jScrollPaneRefrigerateurs);
				panel3.add(buttonPane);
				panel3.revalidate();
				panel3.repaint();
			}

		});

		editRefrigerateurButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectedItem == null) {
					JOptionPane.showMessageDialog(new JFrame(), "Vous devez d'abord cliquer sur un élément du tableau.",
							"Erreur", JOptionPane.ERROR_MESSAGE);
				} else {
					RefrigerateurEditView refrigerateurEditView = new RefrigerateurEditView(utilisateur,
							selectedRefrigerateur);
					refrigerateurEditView.addWindowListener(new java.awt.event.WindowAdapter() {
						@Override
						public void windowClosing(java.awt.event.WindowEvent windowEvent) {
							refreshPanel2(panel3, RefrigerateurComponents.this, buttonPane);
						}
					});
				}
			}

			private void refreshPanel2(JComponent panel3, RefrigerateurComponents refrigerateurComponents,
					JPanel buttonPane) {
				panel3.removeAll();

				JScrollPane jScrollPaneRefrigerateurs = refrigerateurComponents.createRefrigerateurTable(false);
				jScrollPaneRefrigerateurs.setAlignmentY(Component.TOP_ALIGNMENT);
				jScrollPaneRefrigerateurs.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

				panel3.add(jScrollPaneRefrigerateurs);
				panel3.add(buttonPane);
				panel3.revalidate();
				panel3.repaint();
			}

		});

		deleteRefrigerateurButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectedItem == null) {
					// custom title, error icon
					JOptionPane.showMessageDialog(new JFrame(), "Vous devez d'abord cliquer sur un élément du tableau.",
							"Erreur", JOptionPane.ERROR_MESSAGE);
				} else {
					int result = JOptionPane.showConfirmDialog(panel3,
							"Voulez-vous vraiment supprimer l'élément " + selectedItem + "?", "Delete produit",
							JOptionPane.YES_NO_OPTION);
					System.out.println("Result: " + result);
					if (result == 0) {
						HttpClientRefrigerateur clientRefrigerateur = new HttpClientRefrigerateur(utilisateur);
						try {
							clientRefrigerateur.deleteRefrigerateur(selectedItem);
							JOptionPane.showMessageDialog(new Frame(), "L'élément a été supprimé!");
							refreshPanel1(panel3, RefrigerateurComponents.this, buttonPane);
						} catch (ExecutionException | InterruptedException | IOException e1) {
							e1.printStackTrace();
							JOptionPane.showMessageDialog(new Frame(), "Impossible de supprimer l'élément!", "Erreur",
									JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}

			private void refreshPanel1(JComponent panel1, RefrigerateurComponents refrigerateurComponents,
					JPanel buttonPane) {
				panel1.removeAll();

				JScrollPane jScrollPaneRefrigerateurs = refrigerateurComponents.createRefrigerateurTable(false);
				jScrollPaneRefrigerateurs.setAlignmentY(Component.TOP_ALIGNMENT);
				jScrollPaneRefrigerateurs.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

				panel1.add(jScrollPaneRefrigerateurs);
				panel1.add(buttonPane);
				panel1.revalidate();
				panel1.repaint();
			}
		});

		buttonPane.add(showLogOfTemperature);
		buttonPane.add(Box.createRigidArea(new Dimension(20, 10)));
		buttonPane.add(enregistrerTemperature);
		buttonPane.add(Box.createRigidArea(new Dimension(20, 10)));
		buttonPane.add(ajouterRefrigerateur);
		buttonPane.add(Box.createRigidArea(new Dimension(20, 10)));
		buttonPane.add(editRefrigerateurButton);
		buttonPane.add(Box.createRigidArea(new Dimension(20, 10)));
		buttonPane.add(deleteRefrigerateurButton);

		ajouterRefrigerateur.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				RefrigerateurView createView = new RefrigerateurView(utilisateur);

				createView.setVisible(true);
				createView.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent e) {
						refreshPanel1(panel3, RefrigerateurComponents.this, buttonPane);
					}
				});

			}

			private void refreshPanel1(JComponent panel1, RefrigerateurComponents refrigerateurComponents,
					JPanel buttonPane) {
				panel1.removeAll();

				JScrollPane jScrollPaneRefrigerateurs = refrigerateurComponents.createRefrigerateurTable(false);
				jScrollPaneRefrigerateurs.setAlignmentY(Component.TOP_ALIGNMENT);
				jScrollPaneRefrigerateurs.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

				panel1.add(jScrollPaneRefrigerateurs);
				panel1.add(buttonPane);
				panel1.revalidate();
				panel1.repaint();
			}
		});

		return buttonPane;
	}
}
