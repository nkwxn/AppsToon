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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class Cart extends JInternalFrame implements ActionListener {
	JPanel pnlHead, pnlBody, pnlBottom, pnlDetails;
	JLabel lblCart, lblUserID, lblDate, lblUsername, lblTotalPrice;
	JLabel lblUserIDP, lblDateP, lblUsernameP, lblTotalPriceP, lblDetail;
	JTable tblDetail;
	JButton btnCheckOut;
	JScrollPane scp;
	DefaultTableModel dtmCart;
	
	mainForm mf;
	BuyProduct bp;
	
	private Connection con;
	private Statement st;
	private ResultSet rs;
	private PreparedStatement pst;
	
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	LocalDate localDate = LocalDate.now();
	String todayDate = dtf.format(localDate);

	public Cart() {
		// TODO Auto-generated constructor stub
		init();
		
		//set font
		Font title = new Font("Segoe UI", Font.BOLD, 26);
		
		//set layout
		pnlHead.setLayout(new BorderLayout());
		pnlDetails.setLayout(new GridLayout(2, 4, 10, 10));
		pnlBody.setLayout(new BorderLayout());
		pnlBottom.setLayout(new GridLayout(1, 1));
		pnlBody.setBorder(new EmptyBorder(10, 10, 10, 10));
		pnlBottom.setBorder(new EmptyBorder(0, 10, 10, 10));
		
		//atasan
		lblCart.setFont(title);
		lblDetail.setFont(title);
		pnlDetails.add(lblUserID);
		pnlDetails.add(lblUserIDP);
		pnlDetails.add(lblUsername);
		pnlDetails.add(lblUsernameP);
		pnlDetails.add(lblDate);
		pnlDetails.add(lblDateP);
		pnlDetails.add(lblTotalPrice);
		pnlDetails.add(lblTotalPriceP);
		pnlHead.add(lblCart, BorderLayout.NORTH);
		pnlHead.add(pnlDetails, BorderLayout.CENTER);
		
		//detail plus table
		pnlBody.add(lblDetail, BorderLayout.NORTH);
		pnlBody.add(scp, BorderLayout.CENTER);
		
		//Footer
		pnlBottom.add(btnCheckOut);
		btnCheckOut.addActionListener(this);
		
		//masukking ke JFrame
		add(pnlHead, BorderLayout.NORTH);
		add(pnlBody, BorderLayout.CENTER);
		add(pnlBottom, BorderLayout.SOUTH);

		// atur JFrame
		setTitle("Cart");
		setSize(720, 720);
		setLocation(1080, 0);
		setResizable(true);
		setClosable(true);
		setVisible(true);
	}

	private void init() {
		// TODO Auto-generated method stub
		pnlHead = new JPanel();
		pnlBody = new JPanel();
		pnlBottom = new JPanel();
		pnlDetails = new JPanel();
		
		lblCart = new JLabel("Cart");
		lblUserID = new JLabel("User ID : ");
		lblDate = new JLabel("Date : ");
		lblUsername = new JLabel("Username : ");
		lblTotalPrice = new JLabel("Total Price : ");
		lblDetail = new JLabel("Detail");
		lblUserIDP = new JLabel("-");
		lblDateP = new JLabel(todayDate);
		lblUsernameP = new JLabel("-");
		lblTotalPriceP = new JLabel("0");
		
		lblCart.setHorizontalAlignment(JLabel.CENTER);
		lblUserID.setHorizontalAlignment(JLabel.RIGHT);
		lblDate.setHorizontalAlignment(JLabel.RIGHT);
		lblUsername.setHorizontalAlignment(JLabel.RIGHT);
		lblTotalPrice.setHorizontalAlignment(JLabel.RIGHT);
		lblDetail.setHorizontalAlignment(JLabel.CENTER);
		
		String [] columnCart = {"ProductID", "Name", "Price", "Quantity"};
		dtmCart = new DefaultTableModel(columnCart, 0);
		tblDetail = new JTable();
		btnCheckOut = new JButton("Check Out");
		scp = new JScrollPane(tblDetail);
		tblDetail.setModel(dtmCart);
	}
	
	public void addToCart(String productID, String userID, String qty) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/appstoon");
			pst = con.prepareStatement("INSERT INTO cart (UserID, ProductID, Qty) VALUES (?, ?, ?)");
			pst.setString(1, userID);
			pst.setString(2, productID);
			pst.setString(3, qty);
			pst.executeUpdate();
			st = con.createStatement();
			rs = st.executeQuery("SELECT c.ProductID, p.ProductName, p.ProductPrice, c.Qty "
					+ "FROM cart C "
					+ "JOIN products P "
					+ "ON C.productid = P.productid");
			if (dtmCart.getRowCount() >= 1) {
				dtmCart.getDataVector().removeAllElements();
			}
			while (rs.next()) {
				String a = rs.getString(1);
				String b = rs.getString(2);
				String c = rs.getString(3);
				String d = rs.getString(4);
				dtmCart.addRow(new String[] {a, b, c, d});
			}
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == btnCheckOut) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://localhost/appstoon");
				for (int i = 0; i < dtmCart.getRowCount(); i++) {
					String getPID = dtmCart.getValueAt(i, 0).toString();
					String getPQty = dtmCart.getValueAt(i, 3).toString();
					st = con.createStatement();
					rs = st.executeQuery("SELECT * FROM products");
					while (rs.next()) {
						String prodid = rs.getString("productid");
						if (prodid.equals(getPID)) {
							break;
						}
					}
					int updateQty = rs.getInt("productquantity") - Integer.parseInt(getPQty);
					pst = con.prepareStatement("UPDATE products "
							+ "SET productquantity = ? "
							+ "WHERE productid LIKE ?");
					pst.setString(1, Integer.toString(updateQty));
					pst.setString(2, getPID);
					pst.executeUpdate();
				}
				String genTranID = "";
				st = con.createStatement();
				rs = st.executeQuery("SELECT count(*) FROM HeaderTransaction");
				rs.next();
				int getRowCount = rs.getInt(1) + 1;
				switch (Integer.toString(getRowCount).length()) {
				case 1:
					genTranID = "TR00" + getRowCount;
					break;
				case 2:
					genTranID = "TR0" + getRowCount;
					break;
				case 3:
					genTranID = "TR" + getRowCount;
					break;

				default:
					break;
				}
				pst = con.prepareStatement("INSERT INTO HeaderTransaction (TransactionID, UserID, TransactionDate) VALUES (?, ?, ?)");
				pst.setString(1, genTranID);
				pst.setString(2, lblUserIDP.getText());
				pst.setString(3, lblDateP.getText());
				pst.executeUpdate();
				for (int i = 0; i < dtmCart.getRowCount(); i++) {
					String getPID = dtmCart.getValueAt(i, 0).toString();
					String getPQty = dtmCart.getValueAt(i, 3).toString();
					pst = con.prepareStatement("INSERT INTO DetailTransaction (TransactionID, ProductID, Qty) VALUES (?, ?, ?)");
					pst.setString(1, genTranID);
					pst.setString(2, getPID);
					pst.setString(3, getPQty);
					pst.executeUpdate();
				}
				JOptionPane.showMessageDialog(this, "Purchase successful!");
				pst = con.prepareStatement("DELETE FROM Cart WHERE UserID LIKE ?");
				pst.setString(1, lblUserIDP.getText().toString());
				pst.executeUpdate();
				dtmCart.getDataVector().removeAllElements();
				con.close();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
	}

}
