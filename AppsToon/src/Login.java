import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Login extends JFrame implements ActionListener {
	JPanel pnlTop, pnlMid, pnlBtm;
	JLabel lblLogin, lblUsername, lblPassword;
	JTextField txtUsername;
	JPasswordField txtPassword;
	JButton btnRegister, btnSubmit;
	
	mainForm mf;
	
	private Connection con;
	private Statement st;
	private ResultSet rs;
	
	public Login() {
		// TODO Auto-generated constructor stub
		init();
		
		//top
		Font font = new Font("Segoe UI", Font.BOLD, 26);
		lblLogin.setFont(font);
		pnlTop.add(lblLogin);
		
		//middle
		pnlMid.setLayout(new GridLayout(2, 2, 10, 10));
		pnlMid.setBorder(new EmptyBorder(10, 10, 10, 10));
		pnlMid.add(lblUsername);
		pnlMid.add(txtUsername);
		pnlMid.add(lblPassword);
		pnlMid.add(txtPassword);
		
		//bottom
		pnlBtm.setBorder(new EmptyBorder(0, 10, 10, 10));
		pnlBtm.add(btnRegister);
		pnlBtm.add(btnSubmit);
		
		//set to JFrame
		add(pnlTop, BorderLayout.NORTH);
		add(pnlMid, BorderLayout.CENTER);
		add(pnlBtm, BorderLayout.SOUTH);
		
		//JFrame
		setSize(400, 220);
		setTitle("Apps Toon");
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
		//JButton Actions
		btnRegister.addActionListener(this);
		btnSubmit.addActionListener(this);
		txtPassword.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.getKeyCode() == 10) {
					loginAction();
				}
			}
		});
	}
	
	public void init() {
		pnlTop = new JPanel();
		pnlMid = new JPanel();
		pnlBtm = new JPanel();
		
		lblLogin = new JLabel("Login");
		lblUsername = new JLabel("Username");
		lblPassword = new JLabel("Password");
		
		txtUsername = new JTextField();
		txtPassword = new JPasswordField();
		
		btnRegister = new JButton("Register");
		btnSubmit = new JButton("Submit");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Login();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == btnRegister) {
			new Register();
			setVisible(false);
		}
		if (e.getSource() == btnSubmit) {
			loginAction();
		}
	}

	public void loginAction() {
		String pwd = new String(txtPassword.getPassword());
		String getUsername = txtUsername.getText().toString();
		if (!txtUsername.getText().equals("") && !pwd.equals("")) {
			if (txtUsername.getText().equals("staff") && pwd.equals("staff")) {
				mf = new FormStaff();
				mf.setLoginUserID("Staff");
				setVisible(false);
			} else {
				try {
					Class.forName("com.mysql.jdbc.Driver");
					con = DriverManager.getConnection("jdbc:mysql://localhost/appstoon");
					st = con.createStatement();
					String userId = "US";
					rs = st.executeQuery("select count(*) from users");
					rs.next();
					int rows = rs.getInt(1);
					for (int i = 1; i <= rows; i++) {
						int intLength = String.valueOf(i).length();
						switch (intLength) {
						case 1:
							userId = "US00" + i;
							break;
						case 2:
							userId = "US0" + i;
							break;
						case 3:
							userId = "US" + i;
							break;
						}
						rs = st.executeQuery("select * from users "
								+ "where userid like '" + userId + "'");
						rs.next();
						if (rs.getString("username").equals(getUsername) && rs.getString("userpassword").equals(pwd)) {
							mf = new FormUser();
							mf.getLoginUserID();
							mf.setLoginUserID(userId);
							mf.getLoginUserID();
							setVisible(false);
							break;
						} else if (!rs.getString("username").equals(getUsername) || !rs.getString("userpassword").equals(pwd)) {
							System.out.println("Not their username!");
							JOptionPane.showMessageDialog(this, "Wrong Username and Password");
						}
						if (i == rows) {
							JOptionPane.showMessageDialog(this, "Wrong Username and Password");
							break;
						}
					}						
					con.close();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		} else {
			JOptionPane.showMessageDialog(this, "Username and Password must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
