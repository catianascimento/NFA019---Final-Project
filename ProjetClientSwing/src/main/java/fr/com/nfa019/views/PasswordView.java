package fr.com.nfa019.views;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.metal.MetalLookAndFeel;

import fr.com.nfa019.restaurant.beans.UtilisateurBean;
import fr.com.nfa019.restaurant.httpclients.HttpClientUtilisateur;

/* PasswordDemo.java requires no other files. */

public class PasswordView extends JPanel implements ActionListener {
	private static String OK = "ok";
//	private static String HELP = "help";

	private JFrame controllingFrame; // needed for dialogs
	private JTextField loginField;
	private JPasswordField passwordField;

	public UtilisateurBean utilisateur = new UtilisateurBean();

	public PasswordView(JFrame f) {
		this.setBounds(ALLBITS, ABORT, 294, 139);
		f.setBounds(getVisibleRect());
		// Use the default FlowLayout.
//		controllingFrame = f;

		JComponent buttonPane = createButtonPanel();
		setLayout(null);
		JButton okButton = new JButton("OK");
		okButton.setBounds(66, 88, 54, 21);
		add(okButton);

		okButton.setActionCommand(OK);
		// helpButton.setActionCommand(HELP);
		okButton.addActionListener(this);
		add(buttonPane);

		JLabel lblMotDePasse = new JLabel("Mot de passe: ");
		lblMotDePasse.setBounds(22, 52, 103, 13);
		add(lblMotDePasse);

		// Create everything.
		loginField = new JTextField();
		loginField.setBounds(127, 19, 127, 19);
		add(loginField);
		loginField.setActionCommand(OK);
//		passwordField.addActionListener(this);
		lblMotDePasse.setLabelFor(loginField);

		JLabel lblNewLabel = new JLabel("Login:");
		lblNewLabel.setBounds(22, 22, 103, 13);
		add(lblNewLabel);

		passwordField = new JPasswordField(10);
		passwordField.setBounds(127, 49, 127, 19);
		passwordField.requestFocusInWindow();
		add(passwordField);
		passwordField.setActionCommand(OK);
		passwordField.setColumns(10);

		JButton btnAnnuler = new JButton("Annuler");
		btnAnnuler.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnAnnuler.setBounds(152, 88, 81, 21);
		btnAnnuler.setActionCommand("ok");
		add(btnAnnuler);
	}

	protected JComponent createButtonPanel() {
		JPanel p = new JPanel(new GridLayout(0, 1));
		p.setBounds(404, 19, 0, 0);
		JButton helpButton = new JButton("Help");
//		p.add(helpButton);

		return p;
	}

	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();

		if (OK.equals(cmd)) { // Process the password.
			try {
				utilisateur.setLogin(loginField.getText());
				utilisateur.setMotDePasse(String.valueOf(passwordField.getPassword()));
				
				HttpClientUtilisateur clientUtilisateur = new HttpClientUtilisateur(utilisateur);
				utilisateur = clientUtilisateur.verifyUtilisateurByLogin(utilisateur);
				if (utilisateur!=null) {
					utilisateur.setMotDePasse(String.valueOf(passwordField.getPassword())); //we have to send the password inserted by the user
					MainView demo = new MainView(utilisateur);

					UIManager.setLookAndFeel(new MetalLookAndFeel());
					demo.createMainViewAndShowGUI(utilisateur);
				} else {
					JOptionPane.showMessageDialog(controllingFrame, "Vérifier votre identifiant et votre mot de passe.",
							"Erreur", JOptionPane.ERROR_MESSAGE);
				}
			} catch (InterruptedException | ExecutionException | IOException | UnsupportedLookAndFeelException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			loginField.selectAll();
			resetFocus();
		}
	}

	// Must be called from the event dispatch thread.
	protected void resetFocus() {
		loginField.requestFocusInWindow();
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be invoked
	 * from the event dispatch thread.
	 */
	private static void createAndShowGUI() {
		// Create and set up the window.
		JFrame frame = new JFrame("Restaurant");

		frame.setMinimumSize(new Dimension(300, 165));
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create and set up the content pane.
		final PasswordView newContentPane = new PasswordView(frame);
//		frame.setBounds(ALLBITS, ABORT, 400, 300);

		newContentPane.setOpaque(true); // content panes must be opaque
		frame.setContentPane(newContentPane);

		// Make sure the focus goes to the right component
		// whenever the frame is initially given the focus.
		frame.addWindowListener(new WindowAdapter() {
			public void windowActivated(WindowEvent e) {
				newContentPane.resetFocus();
			}
		});

		// Display the window.
		try {
			UIManager.setLookAndFeel(new MetalLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		// Schedule a job for the event dispatch thread:
		// creating and showing this application's GUI.
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Turn off metal's use of bold fonts
//				UIManager.put("swing.boldMetal", Boolean.FALSE);
				try {
					UIManager.setLookAndFeel(new MetalLookAndFeel());
				} catch (UnsupportedLookAndFeelException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				createAndShowGUI();
			}
		});
	}
}