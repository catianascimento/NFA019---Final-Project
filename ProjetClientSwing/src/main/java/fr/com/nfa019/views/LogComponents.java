package fr.com.nfa019.views;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutionException;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import fr.com.nfa019.restaurant.beans.UtilisateurBean;
import fr.com.nfa019.restaurant.httpclients.HttpClientLog;

public class LogComponents {

	String password = "";
	String login = "";
	
	UtilisateurBean utilisateur;

	public LogComponents(UtilisateurBean utilisateur) {
//		this.password = password;
//		this.login = login;
		
		this.utilisateur = utilisateur;
	}

	public JScrollPane createLogTable(Boolean debug) {
		String[] columnNames = { "Utilisateur", "Data/heures", "action" };

		JScrollPane scrollPane = null;
		try {
			final JTable table;
			HttpClientLog httpClientLog = new HttpClientLog(this.utilisateur);
			var logs = httpClientLog.getAllLogs();
			Object[][] data = new Object[logs.size()][];

			for (int i = 0; i < logs.size(); i++) {
				var log = logs.get(i);
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				String formatDateTime = log.getDateAndTime().format(formatter);
				String array[] = { log.getUtilisateurLogin(), formatDateTime,
						log.getAction() };
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
}
