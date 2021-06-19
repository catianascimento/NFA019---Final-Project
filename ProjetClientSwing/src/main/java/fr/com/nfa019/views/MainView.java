/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package fr.com.nfa019.views;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

/*
 * TabbedPaneDemo.java requires one additional file:
 *   images/middle.gif.
 */

import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.metal.MetalLookAndFeel;

import fr.com.nfa019.restaurant.beans.UtilisateurBean;

public class MainView extends JPanel {
	private static final int ROLE_ADMIN = 2;
	private static final long serialVersionUID = 1L;
	JTextArea output;
	JScrollPane scrollPane;

	private JScrollPane jScrollPaneStocks;

	public UtilisateurBean utilisateur;

	public MainView(UtilisateurBean utilisateur) {
		super(new GridLayout(1, 1));
		this.utilisateur = utilisateur;

		JTabbedPane tabbedPane = new JTabbedPane();
		int iteratorTabbedPane = 0;

		tabbedPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		ImageIcon icon = createImageIcon("images/middle.gif");

		if (utilisateur.getRoleId() == ROLE_ADMIN) { // If it is an admin
//		// ===============================Produits =====================================
			JComponent panel1 = new JPanel(false);
			panel1.setLayout(new BoxLayout(panel1, BoxLayout.PAGE_AXIS));

			ProduitComponents produitComponents = new ProduitComponents(utilisateur);
			JScrollPane jScrollPaneProduits = produitComponents.createTable(false);
			jScrollPaneProduits.setAlignmentY(TOP_ALIGNMENT);
			jScrollPaneProduits.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
			panel1.add(jScrollPaneProduits);

			JPanel buttonPane = produitComponents.createButtonPane(panel1);
			tabbedPane.addTab("Produits", icon, panel1, "Does twice as much nothing");

			panel1.add(buttonPane);
			tabbedPane.setMnemonicAt(iteratorTabbedPane++, KeyEvent.VK_1);

			// ===============================Categories====================================
			JComponent panel2 = new JPanel(false);
			panel2.setLayout(new BoxLayout(panel2, BoxLayout.PAGE_AXIS));

			CategorieComponents categorieComponents = new CategorieComponents(utilisateur);
			JScrollPane jScrollPaneCategories = categorieComponents.createCategorieTable(false);
			jScrollPaneCategories.setAlignmentY(TOP_ALIGNMENT);
			jScrollPaneCategories.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
			panel2.add(jScrollPaneCategories);

			JPanel buttonPane2 = categorieComponents.createButtonPane(panel2);
			tabbedPane.addTab("Categories", icon, panel2, "Does twice as much nothing");

			panel2.add(buttonPane2);
			tabbedPane.setMnemonicAt(iteratorTabbedPane++, KeyEvent.VK_2);

			// ===============================Utilisateurs===================================
			JComponent panel3 = new JPanel(false);
			panel3.setLayout(new BoxLayout(panel3, BoxLayout.PAGE_AXIS));

			UtilisateurComponents utilisateurComponents = new UtilisateurComponents(utilisateur);
			JScrollPane jScrollPaneUtilisateurs = utilisateurComponents.createUtilisateurTable(false);
			jScrollPaneUtilisateurs.setAlignmentY(TOP_ALIGNMENT);
			jScrollPaneUtilisateurs.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
			panel3.add(jScrollPaneUtilisateurs);

			JPanel buttonPane3 = utilisateurComponents.createButtonPane(panel3);
			tabbedPane.addTab("Utilisateurs", icon, panel3, "Does twice as much nothing");

			panel3.add(buttonPane3);
			tabbedPane.setMnemonicAt(iteratorTabbedPane++, KeyEvent.VK_3);

		}
		// ===============================Refrigerateurs================================
		JComponent panel4 = new JPanel(false);
		panel4.setLayout(new BoxLayout(panel4, BoxLayout.PAGE_AXIS));

		RefrigerateurComponents refrigerateurComponents = new RefrigerateurComponents(utilisateur);
		JScrollPane jScrollPaneRefrigerateurs = refrigerateurComponents.createRefrigerateurTable(false);
		jScrollPaneRefrigerateurs.setAlignmentY(TOP_ALIGNMENT);
		jScrollPaneRefrigerateurs.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
		panel4.add(jScrollPaneRefrigerateurs);

		JPanel buttonPane4 = refrigerateurComponents.createButtonPane(panel4);

		panel4.add(buttonPane4);
		tabbedPane.addTab("Refrigerateurs", icon, panel4, "Still does nothing");
		tabbedPane.setMnemonicAt(iteratorTabbedPane++, KeyEvent.VK_4);

		// ===============================Stocks =====================================
		JComponent panel5 = new JPanel(false);
		panel5.setLayout(new BoxLayout(panel5, BoxLayout.PAGE_AXIS));

		JPanel filterPane = new JPanel();

		JLabel filterByLabel = new JLabel("Filter:");
		filterPane.add(filterByLabel);
		ButtonGroup G1 = new ButtonGroup();
		JRadioButton expiredButton;
		JRadioButton allButton;

		expiredButton = new JRadioButton("EXPIRED");
		expiredButton.setBounds(166, 135, 62, 21);
		G1.add(expiredButton);
		filterPane.add(expiredButton);

		allButton = new JRadioButton("ALL");
		allButton.setSelected(true);
		allButton.setBounds(248, 135, 103, 21);
		G1.add(allButton);
		filterPane.add(allButton);

		StockProduitComponents stockComponents = new StockProduitComponents(utilisateur);
		JPanel buttonPane5 = stockComponents.createButtonPane(filterPane, panel5);

		JButton okButton = new JButton();
		okButton.setText("OK");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if (expiredButton.isSelected()) {
					jScrollPaneStocks = stockComponents.createStockTable(false, true);

				} else {
					jScrollPaneStocks = stockComponents.createStockTable(false, false);

				}
				jScrollPaneStocks.setAlignmentY(Component.TOP_ALIGNMENT);
				jScrollPaneStocks.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
				panel5.removeAll();
				panel5.add(filterPane);
				panel5.add(jScrollPaneStocks);
				panel5.add(buttonPane5);
				panel5.revalidate();
				panel5.repaint();
			}
		});
		filterPane.add(okButton);
		panel5.add(filterPane);

		jScrollPaneStocks = stockComponents.createStockTable(false, false);
		jScrollPaneStocks.setAlignmentY(TOP_ALIGNMENT);
		jScrollPaneStocks.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
		panel5.add(jScrollPaneStocks);

		tabbedPane.addTab("Stocks", icon, panel5, "Does twice as much nothing");

		panel5.add(buttonPane5);
		tabbedPane.setMnemonicAt(iteratorTabbedPane++, KeyEvent.VK_5);

		// ===============================Logs =====================================
		JComponent panel6 = new JPanel(false);
		panel6.setLayout(new BoxLayout(panel6, BoxLayout.PAGE_AXIS));

		LogComponents logComponents = new LogComponents(utilisateur);
		JScrollPane jScrollPaneLogs = logComponents.createLogTable(false);
		jScrollPaneLogs.setAlignmentY(TOP_ALIGNMENT);
		jScrollPaneLogs.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
		panel6.add(jScrollPaneLogs);

		tabbedPane.addTab("Logs", icon, panel6, "Still does nothing");
		tabbedPane.setMnemonicAt(iteratorTabbedPane++, KeyEvent.VK_6);
		// ===================================================================

		// Add the tabbed pane to this panel.
		add(tabbedPane);

		// The following line enables to use scrolling tabs.
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	}

	protected JComponent makeTextPanel(String text) {
		JPanel panel = new JPanel(false);
		JLabel filler = new JLabel(text);
		filler.setHorizontalAlignment(JLabel.CENTER);
		panel.setLayout(new GridLayout(1, 1));
		panel.add(filler);
		return panel;
	}

	/** Returns an ImageIcon, or null if the path was invalid. */
	protected static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = MainView.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
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

	public void createMainViewAndShowGUI(UtilisateurBean utilisateur) {
		// Create and set up the window.
		JFrame frame = new JFrame("Restaurant");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Add content to the window.
		frame.getContentPane().add(this, BorderLayout.CENTER);
		try {
			UIManager.setLookAndFeel(new MetalLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Display the window.
		frame.pack();
		frame.setSize(900, 460);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setResizable(false);
	}

}