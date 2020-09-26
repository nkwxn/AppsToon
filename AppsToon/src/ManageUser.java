import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class ManageUser extends JInternalFrame implements MouseListener {
	JPanel pnlHeader, pnlBody, pnlMng, pnlMngUser, pnlBtns, pnlGender, pnlDOB;
	JTable tblUsers;
	JScrollPane scp;
	DefaultTableModel dtmUsers;
	
	JLabel lblUserLists, lblUserID, lblUserName, lblUserEmail, lblUserPassword, lblUserGender, lblUserDOB, lblUserPhoneNumber, lblUserAddress;
	JTextField txtUserID, txtUserName, txtUserEmail, txtPhone, txtAddress;
	JPasswordField txtPassword;
	JRadioButton rbMale, rbFemale;
	ButtonGroup bgGender;
	JComboBox<String> cbYear, cbMonth, cbDay;
	JButton btnInsert, btnUpdate, btnDelete, btnSubmit, btnCancel;
	
	private Connection con;
	private Statement st;
	private ResultSet rs;
	private PreparedStatement pst;

	public ManageUser() {
		// TODO Auto-generated constructor stub
		init();

		add(pnlHeader, BorderLayout.NORTH);
		add(pnlBody, BorderLayout.CENTER);
		
		//JFrame
		setTitle("Manage Product");
		setResizable(true);
		setClosable(true);
		setSize(1000, 768);
		setVisible(true);
		
		//assign button
		btnInsert.addMouseListener(this);
		btnUpdate.addMouseListener(this);
		btnDelete.addMouseListener(this);
		btnSubmit.addMouseListener(this);
		btnCancel.addMouseListener(this);
		tblUsers.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				int intSelectedRow = tblUsers.getSelectedRow();
				String getID = tblUsers.getModel().getValueAt(intSelectedRow, 0).toString();
				String getUserName = tblUsers.getModel().getValueAt(intSelectedRow, 1).toString();
				String getUserEmail = tblUsers.getModel().getValueAt(intSelectedRow, 2).toString();
				String getUserPassword = tblUsers.getModel().getValueAt(intSelectedRow, 3).toString();
				String getUserGender = tblUsers.getModel().getValueAt(intSelectedRow, 4).toString();
				String getUserDOB = tblUsers.getModel().getValueAt(intSelectedRow, 5).toString();
				String getUserPhoneNumber = tblUsers.getModel().getValueAt(intSelectedRow, 6).toString();
				String getUserAddress = tblUsers.getModel().getValueAt(intSelectedRow, 7).toString();
				
				//set placeholder fields
				txtUserID.setText(getID);
				txtUserName.setText(getUserName);
				txtUserEmail.setText(getUserEmail);
				txtPassword.setText(getUserPassword);
				txtPhone.setText(getUserPhoneNumber);
				txtAddress.setText(getUserAddress);
				switch (getUserGender) {
				case "Male":
					rbMale.setSelected(true);
					break;
				case "Female":
					rbFemale.setSelected(true);
					break;

				default:
					break;
				}
				String getYear = getUserDOB.substring(0, 4);
				String getMonth = getUserDOB.substring(5, 7);
				int getDay = Integer.parseInt(getUserDOB.substring(getUserDOB.length()-2));
				cbYear.setSelectedItem(getYear);
				cbMonth.setSelectedIndex(Integer.parseInt(getMonth)-1);
				cbDay.setSelectedItem(Integer.toString(getDay));
			}
		});
	}

	private void init() {
		// TODO Auto-generated method stub
		pnlHeader = new JPanel();
		pnlBody = new JPanel();
		pnlMng = new JPanel();
		pnlMngUser = new JPanel();
		pnlBtns = new JPanel();
		pnlDOB = new JPanel();
		pnlGender = new JPanel();
		
		lblUserLists = new JLabel("User List");
		lblUserID = new JLabel("User ID");
		lblUserName = new JLabel("Username");
		lblUserEmail = new JLabel("Email");
		lblUserPassword = new JLabel("Password");
		lblUserGender = new JLabel("Gender");
		lblUserDOB = new JLabel("DOB");
		lblUserPhoneNumber = new JLabel("Phone Number");
		lblUserAddress = new JLabel("Address");
		
		txtUserID = new JTextField();
		txtUserName = new JTextField();
		txtUserEmail = new JTextField();
		txtPassword = new JPasswordField();
		txtPhone = new JTextField();
		txtAddress = new JTextField();
		txtUserID.setEnabled(false);
		txtUserName.setEnabled(false);
		txtUserEmail.setEnabled(false);
		txtPassword.setEnabled(false);
		txtPhone.setEnabled(false);
		txtAddress.setEnabled(false);
		
		rbMale = new JRadioButton("Male");
		rbMale.setActionCommand("Male");
		rbFemale = new JRadioButton("Female");
		rbFemale.setActionCommand("Female");
		bgGender = new ButtonGroup();
		bgGender.add(rbMale);
		bgGender.add(rbFemale);
		pnlGender.add(rbMale);
		pnlGender.add(rbFemale);
		rbMale.setEnabled(false);
		rbFemale.setEnabled(false);
		
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
		cbDay.setEnabled(false);
		cbMonth.setEnabled(false);
		cbYear.setEnabled(false);

		btnInsert = new JButton("Insert");
		btnUpdate = new JButton("Update");
		btnDelete = new JButton("Delete");
		btnSubmit = new JButton("Submit");
		btnCancel = new JButton("Cancel");
		btnSubmit.setEnabled(false);
		btnCancel.setEnabled(false);
		
		String[] col = {"UserID", "UserName", "UserEmail", "UserPassword", "UserGender", "UserDOB", "UserPhone", "UserAddress"};
		dtmUsers = new DefaultTableModel(col, 0);
		tblUsers = new JTable(dtmUsers);
		scp = new JScrollPane(tblUsers);
		
		//add to table
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/appstoon");
			st = con.createStatement();
			rs = st.executeQuery("SELECT * "
					+ "FROM Users");
			while (rs.next()) {
				String a = rs.getString("UserID");
				String b = rs.getString("UserName");
				String c = rs.getString("UserEmail");
				String d = rs.getString("UserPassword");
				String e = rs.getString("UserGender");
				String f = rs.getString("UserDOB");
				String g = rs.getString("UserPhoneNumber");
				String h = rs.getString("UserAddress");
				dtmUsers.addRow(new Object[] {a, b, c, d, e, f, g, h});
			}
			
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}	
		
		//Header
		Font font = new Font("Segoe UI", Font.BOLD, 26);
		lblUserLists.setFont(font);
		pnlHeader.add(lblUserLists);
		
		//body
		pnlBody.setLayout(new GridLayout(2, 1, 10, 10));
		pnlMng.setLayout(new BorderLayout());
		pnlMngUser.setLayout(new GridLayout(8, 2, 10, 10));
		pnlBtns.setLayout(new GridLayout(5, 1, 10, 10));
		pnlBody.setBorder(new EmptyBorder(10, 10, 10, 10));
		pnlMngUser.setBorder(new EmptyBorder(0, 0, 0, 10));
		
		pnlMngUser.add(lblUserID);
		pnlMngUser.add(txtUserID);
		pnlMngUser.add(lblUserName);
		pnlMngUser.add(txtUserName);
		pnlMngUser.add(lblUserEmail);
		pnlMngUser.add(txtUserEmail);
		pnlMngUser.add(lblUserPassword);
		pnlMngUser.add(txtPassword);
		pnlMngUser.add(lblUserGender);
		pnlMngUser.add(pnlGender); //button gender
		pnlMngUser.add(lblUserDOB); 
		pnlMngUser.add(pnlDOB); //dob
		pnlMngUser.add(lblUserPhoneNumber);
		pnlMngUser.add(txtPhone);
		pnlMngUser.add(lblUserAddress);
		pnlMngUser.add(txtAddress);
		
		pnlBtns.add(btnInsert);
		pnlBtns.add(btnUpdate);
		pnlBtns.add(btnDelete);
		pnlBtns.add(btnSubmit);
		pnlBtns.add(btnCancel);
		
		pnlMng.add(pnlMngUser, BorderLayout.CENTER);
		pnlMng.add(pnlBtns, BorderLayout.EAST);
		
		pnlBody.add(scp);
		pnlBody.add(pnlMng);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		String pwd;
		if (e.getSource() == btnInsert && btnInsert.isEnabled()) {
			String genProductID = "";
			int intProdNo;
			try {
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://localhost/appstoon");
				st = con.createStatement();
				rs = st.executeQuery("SELECT COUNT(*) FROM users");
				rs.next();
				intProdNo = rs.getInt("COUNT(*)") + 1;
				int getNumberPID = 0;
				int realProdNumber = 0;
				for (int i = 0; i < dtmUsers.getRowCount(); i++) {
					getNumberPID = Integer.parseInt(tblUsers.getModel().getValueAt(i, 0).toString().substring(2));
					System.out.println(getNumberPID);
					if (dtmUsers.getRowCount() < getNumberPID) {
						break;
					}
				}
				if (getNumberPID >= intProdNo) {
					realProdNumber = (getNumberPID) + 1;
				} else {
					realProdNumber = intProdNo;
				}
				switch (String.valueOf(realProdNumber).length()) {
				case 1:
					genProductID = "US00" + realProdNumber;
					break;
				case 2:
					genProductID = "US0" + realProdNumber;
					break;
				case 3:
					genProductID = "US" + realProdNumber;
					break;

				default:
					break;
				}
				txtUserID.setText(genProductID);
				con.close();
			} catch (Exception e1) {
				// TODO: handle exception
				e1.printStackTrace();
			}
			txtUserName.setText("");
			txtUserEmail.setText("");
			txtPassword.setText("");
			txtAddress.setText("");
			txtPhone.setText("");
			bgGender.clearSelection();
			cbDay.setSelectedIndex(0);
			cbMonth.setSelectedIndex(0);
			cbYear.setSelectedIndex(0);
			rbMale.setEnabled(true);
			rbFemale.setEnabled(true);
			txtUserName.setEnabled(true);
			txtUserEmail.setEnabled(true);
			txtPassword.setEnabled(true);
			txtPhone.setEnabled(true);
			txtAddress.setEnabled(true);
			btnSubmit.setEnabled(true);
			btnCancel.setEnabled(true);
			cbDay.setEnabled(true);
			cbMonth.setEnabled(true);
			cbYear.setEnabled(true);
			btnInsert.setEnabled(false);
			btnUpdate.setEnabled(false);
			btnDelete.setEnabled(false);
		}
		if (e.getSource() == btnUpdate && btnUpdate.isEnabled()) {
			pwd = new String(txtPassword.getPassword());
			boolean vldAllFields = txtUserID.getText().equals("") || txtUserName.getText().equals("") || txtUserEmail.getText().equals("") || pwd.equals("") || txtPhone.getText().equals("") || txtAddress.getText().equals("");
			if (vldAllFields) {
				JOptionPane.showMessageDialog(this, "Data must be selected!");
			} else {
				rbMale.setEnabled(true);
				rbFemale.setEnabled(true);
				txtUserName.setEnabled(true);
				txtUserEmail.setEnabled(true);
				txtPassword.setEnabled(true);
				txtPhone.setEnabled(true);
				txtAddress.setEnabled(true);
				btnSubmit.setEnabled(true);
				btnCancel.setEnabled(true);
				cbDay.setEnabled(true);
				cbMonth.setEnabled(true);
				cbYear.setEnabled(true);
				btnInsert.setEnabled(false);
				btnUpdate.setEnabled(false);
				btnDelete.setEnabled(false);
			}
		}

		if (e.getSource() == btnDelete && btnDelete.isEnabled()) {
			pwd = new String(txtPassword.getPassword());
			boolean vldAllFields = txtUserID.getText().equals("") || txtUserName.getText().equals("") || txtUserEmail.getText().equals("") || pwd.equals("") || txtPhone.getText().equals("") || txtAddress.getText().equals("");
			if (vldAllFields) {
				JOptionPane.showMessageDialog(this, "Data must be selected!");
			} else {
				
				try {
					Class.forName("com.mysql.jdbc.Driver");
					con = DriverManager.getConnection("jdbc:mysql://localhost/appstoon");
					st = con.createStatement();
					rs = st.executeQuery("SELECT userid FROM users");
					String getProductID = "";
					int intDBRow = 0;
					while (rs.next()) {
						if (rs.getString(1).equals(txtUserID.getText())) {
							getProductID = rs.getString(1);
							intDBRow = rs.getRow();
							break;
						}
					}
					pst = con.prepareStatement("DELETE FROM users WHERE UserID LIKE ?");
					pst.setString(1, txtUserID.getText());
					pst.executeUpdate();
					JOptionPane.showMessageDialog(this, "Delete success!");
					for (int i = 0; i < dtmUsers.getRowCount(); i++) {
						if (getProductID.equals(dtmUsers.getValueAt(i, 0))) {
							dtmUsers.removeRow(i);
							break;
						}
					}
					con.close();
				} catch (Exception e1) {
					// TODO: handle exception
					e1.printStackTrace();
				}
				// activate all
				txtUserName.setText("");
				txtUserEmail.setText("");
				txtPassword.setText("");
				txtAddress.setText("");
				txtPhone.setText("");
				bgGender.clearSelection();
				cbDay.setSelectedIndex(0);
				cbMonth.setSelectedIndex(0);
				cbYear.setSelectedIndex(0);
			}
		}
		if (e.getSource() == btnSubmit && btnSubmit.isEnabled()) {
			pwd = new String(txtPassword.getPassword());
			boolean vldAllFields = txtUserID.getText().equals("") || txtUserName.getText().equals("") || txtUserEmail.getText().equals("") || pwd.equals("") || txtPhone.getText().equals("") || txtAddress.getText().equals("");
			boolean vldChoose = rbMale.isSelected() || rbFemale.isSelected();
			
			if (vldAllFields || !vldChoose) {
				if (vldAllFields) {
					JOptionPane.showMessageDialog(this, "All fields must be filled!");
				}
				if (!vldChoose) {
					JOptionPane.showMessageDialog(this, "Gender must be chosen");
				}
			} else {
				try {
					Class.forName("com.mysql.jdbc.Driver");
					con = DriverManager.getConnection("jdbc:mysql://localhost/appstoon");
					st = con.createStatement();
					rs = st.executeQuery("SELECT UserID FROM Users");
					String getProductID = "";
					while (rs.next()) {
						if (rs.getString(1).equals(txtUserID.getText())) {
							getProductID = rs.getString(1);
							break;
						}
					}
					String strGender = bgGender.getSelection().getActionCommand();
					String strDOB = cbYear.getSelectedItem().toString() + "-" + Integer.toString(cbMonth.getSelectedIndex() + 1) + "-" + cbDay.getSelectedItem().toString();

					if (!txtUserID.getText().equals(getProductID)) {
						//buat insert data
						String dbtablerow = "UserID, UserName, UserEmail, UserPassword, UserGender, UserDOB, UserPhoneNumber, UserAddress";
						pst = con.prepareStatement("INSERT INTO products (" + dbtablerow + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
						pst.setString(1, txtUserID.getText());
						pst.setString(2, txtUserName.getText());
						pst.setString(3, txtUserEmail.getText());
						pst.setString(4, pwd);
						pst.setString(5, strGender);
						pst.setString(6, strDOB);
						pst.setString(7, txtPhone.getText());
						pst.setString(8, txtAddress.getText());
						pst.executeUpdate();
						dtmUsers.addRow(new String[]{txtUserID.getText(), txtUserName.getText(), txtUserEmail.getText(), pwd, strGender, strDOB, txtPhone.getText(), txtAddress.getText()});
					} else {
						//buat update data
						pst = con.prepareStatement("UPDATE users "
								+ "SET UserName = ?, "
								+ "UserEmail = ?, "
								+ "UserPassword = ?, "
								+ "UserGender = ?, "
								+ "UserDOB = ?, "
								+ "UserPhoneNumber = ?, "
								+ "UserAddress = ? "
								+ "WHERE UserID = ?");
						pst.setString(1, txtUserName.getText());
						pst.setString(2, txtUserEmail.getText());
						pst.setString(3, pwd);
						pst.setString(4, strGender);
						pst.setString(5, strDOB);
						pst.setString(6, txtPhone.getText());
						pst.setString(7, txtAddress.getText());
						pst.setString(8, txtUserID.getText());
						pst.executeUpdate();
						String [] updateDatas = {txtUserName.getText(), txtUserEmail.getText(), pwd, strGender, strDOB, txtPhone.getText(), txtAddress.getText()};
						for (int i = 0; i < dtmUsers.getRowCount(); i++) {
							if (dtmUsers.getValueAt(i, 0).equals(txtUserID.getText())) {
								for (int j = 0; j < updateDatas.length; j++) {
									dtmUsers.setValueAt(updateDatas[j], i, j+1);
								}
							}
						}
					}
					con.close();
				} catch (Exception e1) {
					// TODO: handle exception
					e1.printStackTrace();
				}

				txtUserName.setText("");
				txtUserEmail.setText("");
				txtPassword.setText("");
				txtAddress.setText("");
				txtPhone.setText("");
				bgGender.clearSelection();
				cbDay.setSelectedIndex(0);
				cbMonth.setSelectedIndex(0);
				cbYear.setSelectedIndex(0);
				rbMale.setEnabled(false);
				rbFemale.setEnabled(false);
				txtUserName.setEnabled(false);
				txtUserEmail.setEnabled(false);
				txtPassword.setEnabled(false);
				txtPhone.setEnabled(false);
				txtAddress.setEnabled(false);
				btnSubmit.setEnabled(false);
				btnCancel.setEnabled(false);
				cbDay.setEnabled(false);
				cbMonth.setEnabled(false);
				cbYear.setEnabled(false);
				btnInsert.setEnabled(true);
				btnUpdate.setEnabled(true);
				btnDelete.setEnabled(true);
			}
		}
		if (e.getSource() == btnCancel && btnCancel.isEnabled()) {
			txtUserID.setText("");
			txtUserName.setText("");
			txtUserEmail.setText("");
			txtPassword.setText("");
			txtAddress.setText("");
			txtPhone.setText("");
			bgGender.clearSelection();
			cbDay.setSelectedIndex(0);
			cbMonth.setSelectedIndex(0);
			cbYear.setSelectedIndex(0);
			rbMale.setEnabled(false);
			rbFemale.setEnabled(false);
			txtUserName.setEnabled(false);
			txtUserEmail.setEnabled(false);
			txtPassword.setEnabled(false);
			txtPhone.setEnabled(false);
			txtAddress.setEnabled(false);
			btnSubmit.setEnabled(false);
			btnCancel.setEnabled(false);
			cbDay.setEnabled(false);
			cbMonth.setEnabled(false);
			cbYear.setEnabled(false);
			btnInsert.setEnabled(true);
			btnUpdate.setEnabled(true);
			btnDelete.setEnabled(true);
		}
	}

}
