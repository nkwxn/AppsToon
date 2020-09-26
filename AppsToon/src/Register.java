import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Register extends JFrame implements ActionListener {
	
	JPanel pnlNorth, pnlCenter, pnlSouth, pnlGender, pnlDOB;
	JLabel lblAppsToon, lblUsername, lblEmail, lblPassword, lblConfirm, lblGender, lblDOB, lblPhoneNumber, lblAddress;
	JTextField txtUsername, txtEmail, txtPhoneNumber, txtAddress;
	JPasswordField txtPassword, txtConfirm;
	JRadioButton rbMale, rbFemale;
	ButtonGroup bgGender;
	JComboBox<String> cbYear, cbMonth, cbDay;
	JButton btnReset, btnSumbit;
	
	private Connection con;
	private Statement st;
	private ResultSet rs;
	private PreparedStatement pst;

	public Register() {
		// TODO Auto-generated constructor stub
		init();
		
		//header
		Font font = new Font("Segoe UI", Font.BOLD, 26);
		lblAppsToon.setFont(font);
		pnlNorth.add(lblAppsToon);
		
		//Body
		pnlCenter.setLayout(new GridLayout(8, 2, 10, 10));
		pnlCenter.setBorder(new EmptyBorder(10, 10, 10, 10));
		pnlCenter.add(lblUsername);
		pnlCenter.add(txtUsername);
		pnlCenter.add(lblEmail);
		pnlCenter.add(txtEmail);
		pnlCenter.add(lblPassword);
		pnlCenter.add(txtPassword);
		pnlCenter.add(lblConfirm);
		pnlCenter.add(txtConfirm);
		pnlCenter.add(lblGender);
		pnlCenter.add(pnlGender);
		pnlCenter.add(lblDOB);
		pnlCenter.add(pnlDOB);
		pnlCenter.add(lblPhoneNumber);
		pnlCenter.add(txtPhoneNumber);
		pnlCenter.add(lblAddress);
		pnlCenter.add(txtAddress);
		
		//bottom
		pnlSouth.setBorder(new EmptyBorder(0, 10, 10, 10));
		pnlSouth.add(btnReset);
		pnlSouth.add(btnSumbit);

		//insert to JFrame
		add(pnlNorth, BorderLayout.NORTH);
		add(pnlCenter, BorderLayout.CENTER);
		add(pnlSouth, BorderLayout.SOUTH);
		
		//JFrame
		setTitle("Apps Toon");
		setSize(475, 465);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
		//JButton Actions
		btnReset.addActionListener(this);
		btnSumbit.addActionListener(this);
	}
	
	public void init() {
		pnlNorth = new JPanel();
		pnlCenter = new JPanel();
		pnlSouth = new JPanel();
		pnlGender = new JPanel();
		pnlDOB = new JPanel();
		
		lblAppsToon = new JLabel("APPS TOON");
		lblUsername = new JLabel("Username");
		lblEmail = new JLabel("Email");
		lblPassword = new JLabel("Password");
		lblConfirm = new JLabel("Confirm Password");
		lblGender = new JLabel("Gender");
		lblDOB = new JLabel("DOB");
		lblPhoneNumber = new JLabel("Phone Number");
		lblAddress = new JLabel("Address");
		
		txtUsername = new JTextField();
		txtEmail = new JTextField();
		txtPassword = new JPasswordField();
		txtConfirm = new JPasswordField();
		txtPhoneNumber = new JTextField();
		txtAddress = new JTextField();
		
		rbMale = new JRadioButton("Male");
		rbMale.setActionCommand("Male");
		rbFemale = new JRadioButton("Female");
		rbFemale.setActionCommand("Female");
		
		bgGender = new ButtonGroup();
		bgGender.add(rbMale);
		bgGender.add(rbFemale);
		
		pnlGender.add(rbMale);
		pnlGender.add(rbFemale);

		String[] months = {"January", "Febuary", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		
		cbDay = new JComboBox<>();
		cbMonth = new JComboBox<>(months);
		cbYear = new JComboBox<>();
		
		for(int years = Calendar.getInstance().get(Calendar.YEAR); years >= 1900; years--){
			cbYear.addItem(years+"");
		}
		
		for(int days = 1; days <= 31; days++){
			cbDay.addItem(days + "");
		}
		
		pnlDOB.add(cbDay);
		pnlDOB.add(cbMonth);
		pnlDOB.add(cbYear);
		
		btnReset = new JButton("Reset");
		btnSumbit = new JButton("Submit");
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if (e.getSource() == btnReset) {
			txtUsername.setText("");
			txtAddress.setText("");
			txtEmail.setText("");
			txtPassword.setText("");
			txtConfirm.setText("");
			txtPhoneNumber.setText("");
			bgGender.clearSelection();
			cbDay.setSelectedIndex(0);
			cbMonth.setSelectedIndex(0);
			cbYear.setSelectedIndex(0);
		}
		if (e.getSource() == btnSumbit) {
			String pwd = new String(txtPassword.getPassword());
			String cpwd = new String(txtConfirm.getPassword());
			boolean confirmPwdSame = pwd.equals(cpwd);
			boolean genderSelected = rbMale.isSelected() || rbFemale.isSelected();
			boolean phIsNumeric = false;
			for (int i = 0; i < txtPhoneNumber.getText().length(); i++) {
				if (Character.isDigit(txtPhoneNumber.getText().charAt(i))) {
					phIsNumeric = true;
					break;
				}
			}
			boolean vldUsername = txtUsername.getText().length() < 5 || txtUsername.getText().length() > 20;
			
			boolean vldEmailCharLength = txtEmail.getText().length() > 14;
			int countAt = 0;
			int countDot = 0;
			for (int i = 0; i < txtEmail.getText().toString().length(); i++) {
				if (txtEmail.getText().toString().charAt(i) == '@') {
					countAt++;
				}
				if (txtEmail.getText().toString().charAt(i) == '.') {
					countDot++;
				}
			}
			boolean vldEmailAtDot = txtEmail.getText().contains("@.") || txtEmail.getText().contains(".@");
			boolean vldEmailStart = txtEmail.getText().startsWith("@") || txtEmail.getText().startsWith(".");
			boolean vldEmailCount = countAt > 1 || countDot > 1;
			boolean vldEmailends = !txtEmail.getText().endsWith(".com");
			boolean vldEmailHasAt = !txtEmail.getText().contains("@");
			boolean vldEmail = false;
			
			boolean vldPassword = pwd.length() > 10;
			boolean vldPhone = txtPhoneNumber.getText().length() != 12 || !phIsNumeric;
			boolean vldAddress = (txtAddress.getText().length() < 6 || txtAddress.getText().length() > 30) || !txtAddress.getText().endsWith("Street");
			
			boolean allValidationDone = vldUsername || vldEmail || !vldPassword || !confirmPwdSame || !genderSelected || vldPhone || vldAddress;
			
			if (allValidationDone) {				
				if (vldUsername) {
					JOptionPane.showMessageDialog(this, "Username must be around 5-20 characters");
				}
				if (vldEmailCharLength) {
					if (vldEmailAtDot) {
						JOptionPane.showMessageDialog(this, "Character '@' must not be next to '.'");
					}
					if (vldEmailStart) {
						JOptionPane.showMessageDialog(this, "Email cannot starts with '@' or '.'");
					}				
					if (vldEmailCount) {
						JOptionPane.showMessageDialog(this, "Email cannot contains more than one '@' or '.'");
					}
					if (vldEmailHasAt) {
						JOptionPane.showMessageDialog(this, "Email must have '@'");
					}
					if (vldEmailends) {
						JOptionPane.showMessageDialog(this, "Email must end with '.com'");
					}
				} else {
					JOptionPane.showMessageDialog(this, "Email must more than 14 character!");
				}
				if (vldPassword) {
					boolean hasLetter = false;
					boolean hasDigit = false;
					for (int i = 0; i < pwd.length(); i++) {
						if (Character.isLetter(pwd.charAt(i))) {
							hasLetter = true;
						}
						if (Character.isDigit(pwd.charAt(i))) {
							hasDigit = true;
						}
					}				
					if (!hasLetter || !hasDigit) {
						JOptionPane.showMessageDialog(this, "Password must be Alphanumeric");
					}
				} else {
					JOptionPane.showMessageDialog(this, "Password must be longer than 10 characters!");
				}
				if (!confirmPwdSame) {
					JOptionPane.showMessageDialog(this, "Confirm password must be same as password");
				}
				if (!genderSelected) {
					JOptionPane.showMessageDialog(this, "One gender must be selected!");
				}
				if (vldPhone) {
					JOptionPane.showMessageDialog(this, "Phone number must be 12 digits and numeric!");
				}
				if (vldAddress) {
					JOptionPane.showMessageDialog(this, "Address must be between 6 and 30 characters and ends with 'Street'");
				}
				System.out.println("Validation done");
			} else {
				System.out.println("All fields success");
				try {
					Class.forName("com.mysql.jdbc.Driver");
					con = DriverManager.getConnection("jdbc:mysql://localhost:3306/appstoon");
					st = con.createStatement();					
					rs = st.executeQuery("select count(*) from users ");
					rs.next();
					String strUserID = "", strName = txtUsername.getText().toString(), strEmail = txtEmail.getText().toString(), strGender, strDOB, strPhoneNumber, strAddress;
					int intUserNO = rs.getInt("COUNT(*)") + 1;
					int getNumberUID = 0;
					int realUserNO = 0;
					st = con.createStatement();
					rs = st.executeQuery("SELECT UserID FROM Users");
					while (rs.next()) {
						getNumberUID = Integer.parseInt(rs.getString(1).toString().substring(2));
						if ((intUserNO-1) < getNumberUID) {
							break;
						}
					}
					if (getNumberUID >= intUserNO) {
						realUserNO = getNumberUID + 1;
					} else {
						realUserNO = intUserNO;
					}
					switch (String.valueOf(realUserNO).length()) {
					case 1:
						strUserID = "US00" + realUserNO;
						break;
					case 2:
						strUserID = "US0" + realUserNO;
						break;
					case 3:
						strUserID = "US" + realUserNO;
						break;

					default:
						break;
					}
					System.out.println(strUserID);
					strGender = bgGender.getSelection().getActionCommand();
					System.out.println(strGender);
					strDOB = cbYear.getSelectedItem().toString() + "-" + Integer.toString(cbMonth.getSelectedIndex() + 1) + "-" + cbDay.getSelectedItem().toString();
					System.out.println(strDOB);
					strPhoneNumber = txtPhoneNumber.getText().toString();
					strAddress = txtAddress.getText().toString();
					pst = con.prepareStatement("INSERT INTO Users (UserID, UserName, UserEmail, UserPassword, UserGender, UserDOB, UserPhoneNumber, UserAddress) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
					pst.setString(1, strUserID);
					pst.setString(2, strName);
					pst.setString(3, strEmail);
					pst.setString(4, pwd);
					pst.setString(5, strGender);
					pst.setString(6, strDOB);
					pst.setString(7, strPhoneNumber);
					pst.setString(8, strAddress);
					pst.executeUpdate();
					System.out.println("Register success");
					con.close();
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
				new Login();
				setVisible(false);
			}
		}
	}

}
