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
import java.util.ArrayList;
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

import fr.com.nfa019.restaurant.beans.UtilisateurBean;
import fr.com.nfa019.restaurant.httpclients.HttpClientRole;
import fr.com.nfa019.restaurant.httpclients.HttpClientUtilisateur;

public class UtilisateurComponents {

//	String password = "";
//	String login = "";
	public Long selectedItem;
	public UtilisateurBean selectedUtilisateur;

	public UtilisateurBean utilisateur;

	public UtilisateurComponents(UtilisateurBean utilisateur) {
//		this.password = password;
//		this.login = login;
		this.utilisateur = utilisateur;
	}

	public JScrollPane createUtilisateurTable(Boolean debug) {
		String[] columnNames = { "Nom", "Prenom", "Adresse Mail", "Login", "Role" };

		JScrollPane scrollPane = null;
		try {
			final JTable table;
			HttpClientUtilisateur httpClientUtilisateur = new HttpClientUtilisateur(utilisateur);
			var utilisateurs = httpClientUtilisateur.getAllUtilisateurs();
			Object[][] data = new Object[utilisateurs.size()][];

			for (int i = 0; i < utilisateurs.size(); i++) {
				HttpClientRole httpClientRole = new HttpClientRole(utilisateur);
				var utilisateur = utilisateurs.get(i);
				List<String> roleNames = new ArrayList<String>();
//				utilisateur.getRoleIds().forEach(roleId -> {
//					RoleBean role;
//					try {
//						role = httpClientRole.getRoleDetailsById(roleId);
//						roleNames.add(role.getNom());
//					} catch (InterruptedException | ExecutionException | IOException e1) {
//					}
//				});
				var role = httpClientRole.getRoleDetailsById(utilisateur.getRoleId());
				roleNames.add(role.getNom());
				String array[] = { utilisateur.getNom(), utilisateur.getPrenom(), utilisateur.getAdresseMail(),
						utilisateur.getLogin(), roleNames.toString() };
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
					selectedUtilisateur = utilisateurs.get(iSelectedIndex);
					selectedItem = utilisateurs.get(iSelectedIndex).getId();
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

	public JPanel createButtonPane(JComponent panel3) {
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
		buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		buttonPane.add(Box.createHorizontalGlue());

		// create our jbutton
		JButton ajouterUtilisateur = new JButton("Ajouter");
		JButton deleteUtilisateurButton = new JButton("Delete");
		JButton editUtilisateurButton = new JButton("Editer");

		editUtilisateurButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectedItem == null) {
					JOptionPane.showMessageDialog(new JFrame(), "Vous devez d'abord cliquer sur un élément du tableau.",
							"Erreur", JOptionPane.ERROR_MESSAGE);
				} else {
					UtilisateurEditView utilisateurEditView = new UtilisateurEditView(utilisateur, selectedUtilisateur);
					utilisateurEditView.addWindowListener(new java.awt.event.WindowAdapter() {
						@Override
						public void windowClosing(java.awt.event.WindowEvent windowEvent) {
							refreshPanel2(panel3, UtilisateurComponents.this, buttonPane);
						}
					});
				}
			}

			private void refreshPanel2(JComponent panel2, UtilisateurComponents categorieComponents,
					JPanel buttonPane) {
				panel2.removeAll();

				JScrollPane jScrollPaneUtilisateurs = categorieComponents.createUtilisateurTable(false);
				jScrollPaneUtilisateurs.setAlignmentY(Component.TOP_ALIGNMENT);
				jScrollPaneUtilisateurs.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

				panel2.add(jScrollPaneUtilisateurs);
				panel2.add(buttonPane);
				panel2.revalidate();
				panel2.repaint();
			}
		});

		deleteUtilisateurButton.addActionListener(new ActionListener() {

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
						HttpClientUtilisateur clientUtilisateur = new HttpClientUtilisateur(utilisateur);
						try {
							clientUtilisateur.deleteUtilisateur(selectedItem);
							JOptionPane.showMessageDialog(new Frame(), "L'élément a été supprimé!");
							refreshPanel1(panel3, UtilisateurComponents.this, buttonPane);
						} catch (ExecutionException | InterruptedException | IOException e1) {
							e1.printStackTrace();
							JOptionPane.showMessageDialog(new Frame(), "Impossible de supprimer l'élément!", "Erreur",
									JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}

			private void refreshPanel1(JComponent panel1, UtilisateurComponents categorieComponents,
					JPanel buttonPane) {
				panel1.removeAll();

				JScrollPane jScrollPaneUtilisateurs = categorieComponents.createUtilisateurTable(false);
				jScrollPaneUtilisateurs.setAlignmentY(Component.TOP_ALIGNMENT);
				jScrollPaneUtilisateurs.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

				panel1.add(jScrollPaneUtilisateurs);
				panel1.add(buttonPane);
				panel1.revalidate();
				panel1.repaint();
			}
		});

		buttonPane.add(ajouterUtilisateur);
		buttonPane.add(Box.createRigidArea(new Dimension(20, 10)));
		buttonPane.add(editUtilisateurButton);
		buttonPane.add(Box.createRigidArea(new Dimension(20, 10)));
		buttonPane.add(deleteUtilisateurButton);

		ajouterUtilisateur.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				UtilisateurView createView = new UtilisateurView(utilisateur);

				createView.setVisible(true);
				createView.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent e) {
						refreshPanel1(panel3, UtilisateurComponents.this, buttonPane);
					}
				});

			}

			private void refreshPanel1(JComponent panel1, UtilisateurComponents categorieComponents,
					JPanel buttonPane) {
				panel1.removeAll();

				JScrollPane jScrollPaneUtilisateurs = categorieComponents.createUtilisateurTable(false);
				jScrollPaneUtilisateurs.setAlignmentY(Component.TOP_ALIGNMENT);
				jScrollPaneUtilisateurs.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

				panel1.add(jScrollPaneUtilisateurs);
				panel1.add(buttonPane);
				panel1.revalidate();
				panel1.repaint();
			}
		});

		return buttonPane;
	}
}
