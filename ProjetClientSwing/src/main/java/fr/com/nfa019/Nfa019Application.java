package fr.com.nfa019;

import javax.swing.JFrame;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import fr.com.nfa019.views.PasswordView;

@SpringBootApplication
public class Nfa019Application {

	PasswordView passwordView = new PasswordView(new JFrame());
}
