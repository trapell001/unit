package tower;

import java.awt.Desktop;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class InfoFrame extends JFrame {

	public static String version = "0.0.1.2";

	private JPanel contentPane;

	public InfoFrame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("res" + File.separator + "ico.png"));
		setAlwaysOnTop(true);
		setTitle(Frame.Titel + " - Info");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 340, 240);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblVersion = new JLabel("Version:");
		lblVersion.setFont(new Font("Segoe UI Black", Font.PLAIN, 14));
		lblVersion.setBounds(10, 10, 80, 26);
		contentPane.add(lblVersion);

		JLabel lblEntwickler = new JLabel("Entwickler:");
		lblEntwickler.setFont(new Font("Segoe UI Black", Font.PLAIN, 14));
		lblEntwickler.setBounds(10, 60, 95, 26);
		contentPane.add(lblEntwickler);

		JLabel lblBleuzen = new JLabel("Bleuzen");
		lblBleuzen.setBounds(200, 68, 160, 14);
		contentPane.add(lblBleuzen);

		JLabel lblV = new JLabel(version);
		lblV.setBounds(200, 18, 46, 14);
		contentPane.add(lblV);

		JButton btnNachUpdatesSuchen = new JButton("Nach Updates suchen");
		btnNachUpdatesSuchen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				JFrame frame = new JFrame();
				frame.setAlwaysOnTop(true);


			}
		});
		btnNachUpdatesSuchen.setBounds(10, 166, 160, 25);
		contentPane.add(btnNachUpdatesSuchen);

		JButton btnGitHub = new JButton("GitHub");
		btnGitHub.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {


			}
		});
		btnGitHub.setBounds(204, 167, 110, 23);
		contentPane.add(btnGitHub);
	}
}

