import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class ViewTransaction extends JInternalFrame {
	JPanel pnlList, pnlDetails, pnlHL, pnlHD;
	JLabel lblList, lblDetails;
	JTable tblList, tblDetails;
	JScrollPane scpList, scpDetails;
	DefaultTableModel tblmList, tblmDetails;
	mainForm mf;
	
	private Connection con;
	private Statement st;
	private ResultSet rs;
	
	String loginUser;
	String sql;

	public ViewTransaction(String login) {
		// TODO Auto-generated constructor stub
		init();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/appstoon");
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM HeaderTransaction");
			while (rs.next()) {
				if (login.equals("Staff")) {
					String a = rs.getString(1);
					String b = rs.getString(2);
					String c = rs.getString(3);
					tblmList.addRow(new String[] {a, b, c});					
				} else if (login.equals(rs.getString(2))) {
					String a = rs.getString(1);
					String b = rs.getString(2);
					String c = rs.getString(3);
					tblmList.addRow(new String[] {a, b, c});
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		//JFrame
		setTitle("View Transaction");
		setResizable(true);
		setClosable(true);
		setSize(1080, 720);
		setVisible(true);
	}

	private void init() {
		// TODO Auto-generated method stub
		pnlList = new JPanel();
		pnlDetails = new JPanel();
		pnlHL = new JPanel();
		pnlHD = new JPanel();
		
		lblList = new JLabel("Transaction List");
		lblDetails = new JLabel("Transaction Detail");
		
		String[] nameList = {"TransactionID", "UserID", "Date"};
		tblmList = new DefaultTableModel(nameList, 0);
		tblList = new JTable();
		scpList = new JScrollPane(tblList);
		tblList.setModel(tblmList);
		
		String[] nameDetails = {"TransactionID", "ProductID", "Qty"};
		tblmDetails = new DefaultTableModel(nameDetails, 0);
		tblDetails = new JTable();
		scpDetails = new JScrollPane(tblDetails);
		tblDetails.setModel(tblmDetails);
		
		Font font = new Font("Segoe UI", Font.BOLD, 26);
		lblList.setFont(font);
		lblDetails.setFont(font);
		
		//Set Layout
		setLayout(new GridLayout(2, 1));
		pnlList.setLayout(new BorderLayout());
		pnlDetails.setLayout(new BorderLayout());
		pnlList.setBorder(new EmptyBorder(10, 10, 10, 10));
		pnlDetails.setBorder(new EmptyBorder(0, 10, 10, 10));
		
		//placement
		pnlHL.add(lblList);
		pnlList.add(pnlHL, BorderLayout.NORTH);
		pnlList.add(scpList, BorderLayout.CENTER);
		
		pnlHD.add(lblDetails);
		pnlDetails.add(pnlHD, BorderLayout.NORTH);
		pnlDetails.add(scpDetails, BorderLayout.CENTER);
		
		add(pnlList);
		add(pnlDetails);
		
		tblList.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				if (tblmDetails.getRowCount() >= 1) {
					tblmDetails.getDataVector().removeAllElements();
				}
				int intSelectedRow = tblList.getSelectedRow();
				String getTransactionID = tblList.getModel().getValueAt(intSelectedRow, 0).toString();
				try {
					Class.forName("com.mysql.jdbc.Driver");
					con = DriverManager.getConnection("jdbc:mysql://localhost:3306/appstoon");
					st = con.createStatement();	
					rs = st.executeQuery("SELECT * FROM DetailTransaction");
					while (rs.next()) {
						if (getTransactionID.equals(rs.getString(1))) {
							String a = rs.getString(1);
							String b = rs.getString(2);
							String c = rs.getString(3);
							tblmDetails.addRow(new String[] {a, b, c});
						}
					}
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		tblList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
			}
		});
	}

}
