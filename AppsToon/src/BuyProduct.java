import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class BuyProduct extends JInternalFrame implements ActionListener, MouseListener {
	JPanel pnlHead, pnlBody, pnlPDesc, pnlPTxt, pnlPlaceholder, pnlFoot;
	JLabel lblOurProduct, lblPID, lblPGenre, lblPName, lblPPrice, lblPQty, lblPRate;
	JLabel lblPIDP, lblPGenreP, lblPNameP, lblPPriceP, lblPRatingP, lblPImg;
	JSpinner spQty;
	JButton btnAdd;
	JTable tblProduct;
	JScrollPane scp;
	DefaultTableModel tblMdlProduct;
	ImageIcon img;
	
	mainForm mf;
	Cart c;
	
	private Connection con;
	private Statement st;
	private ResultSet rs;
	
	int getPStock;
	private String loginUserID;

	public String getLoginUserID() {
		return loginUserID;
	}

	public void setLoginUserID(String loginUserID) {
		this.loginUserID = loginUserID;
	}

	public BuyProduct(String loginUser) {
		// TODO Auto-generated constructor stub
		setLoginUserID(loginUser);
		init();
		
		//header
		Font font = new Font("Segoe UI", Font.BOLD, 26);
		lblOurProduct.setFont(font);
		pnlHead.add(lblOurProduct);
		
		//body
		pnlBody.setLayout(new GridLayout(2, 1, 10, 10));
		pnlPDesc.setLayout(new GridLayout(1, 3, 10, 10));
		pnlPTxt.setLayout(new GridLayout(6, 1, 10, 10));
		pnlPlaceholder.setLayout(new GridLayout(6, 1, 10, 10));
		pnlBody.setBorder(new EmptyBorder(10, 10, 10, 10));

		pnlPTxt.add(lblPID);
		pnlPTxt.add(lblPName);
		pnlPTxt.add(lblPPrice);
		pnlPTxt.add(lblPGenre);
		pnlPTxt.add(lblPQty);
		pnlPTxt.add(lblPRate);
		
		pnlPlaceholder.add(lblPIDP);
		pnlPlaceholder.add(lblPNameP);
		pnlPlaceholder.add(lblPPriceP);
		pnlPlaceholder.add(lblPGenreP);
		pnlPlaceholder.add(spQty);
		pnlPlaceholder.add(lblPRatingP);
		
		pnlBody.add(scp);
		pnlPDesc.add(lblPImg);
		pnlPDesc.add(pnlPTxt);
		pnlPDesc.add(pnlPlaceholder);
		pnlBody.add(pnlPDesc);
		
		//footer
		pnlFoot.setBorder(new EmptyBorder(0, 10, 10, 10));
		pnlFoot.add(btnAdd);
		
		//add to JFrame
		add(pnlHead, BorderLayout.NORTH);
		add(pnlBody, BorderLayout.CENTER);
		add(pnlFoot, BorderLayout.SOUTH);
		
		c = new Cart();
		c.lblUserIDP.setText(getLoginUserID());
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/appstoon");
			st = con.createStatement();
			rs = st.executeQuery("SELECT userid, username FROM users");
			while (rs.next()) {
				if (getLoginUserID().equals(rs.getString(1))) {
					c.lblUsernameP.setText(rs.getString(2));
					break;
				}
			}
			con.close();
		} catch (Exception e2) {
			// TODO: handle exception
			e2.printStackTrace();
		}
		
		//actionlistener button dan klik kolom tabel
		tblProduct.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				// TODO Auto-generated method stub
				int intSelected = tblProduct.getSelectedRow();
				lblPIDP.setText(tblProduct.getModel().getValueAt(intSelected, 0).toString());
				lblPNameP.setText(tblProduct.getModel().getValueAt(intSelected, 2).toString());
				lblPPriceP.setText(tblProduct.getModel().getValueAt(intSelected, 3).toString());
				lblPGenreP.setText(tblProduct.getModel().getValueAt(intSelected, 1).toString());
				lblPRatingP.setText(tblProduct.getModel().getValueAt(intSelected, 6).toString());
				getPStock = Integer.parseInt(tblProduct.getModel().getValueAt(intSelected, 4).toString());

				String getProductImage = tblProduct.getModel().getValueAt(intSelected, 5).toString();
				//set image
				img = new ImageIcon("src/images/" + getProductImage);
				Image img1 = img.getImage();
				Image img2 = img1.getScaledInstance(lblPImg.getWidth(), lblPImg.getHeight(), Image.SCALE_SMOOTH);
				ImageIcon img3 = new ImageIcon(img2);
				lblPImg.setIcon(img3);
			}
		});
		btnAdd.addMouseListener(this);
	}

	private void init() {
		// TODO Auto-generated method stub
		pnlHead = new JPanel();
		pnlBody = new JPanel();
		pnlPDesc = new JPanel();
		pnlPTxt = new JPanel();
		pnlPlaceholder = new JPanel();
		pnlFoot = new JPanel();
		
		lblOurProduct = new JLabel("Our Products");
		lblPID = new JLabel("ProductID");
		lblPName = new JLabel("Product Name");
		lblPPrice = new JLabel("Product Price");
		lblPGenre = new JLabel("Product Genre");
		lblPQty = new JLabel("Quantity");
		lblPRate = new JLabel("Rating");
		
		lblPIDP = new JLabel("-");
		lblPNameP = new JLabel("-");
		lblPPriceP = new JLabel("-");
		lblPGenreP = new JLabel("-");
		lblPRatingP = new JLabel("-");
		lblPImg = new JLabel();
		lblPImg.setIcon(new ImageIcon("src/NoImageFound.jpg"));
		
		String[] column = {"ProductID", "GenreName", "ProductName", "ProductPrice", "ProductQuantity", "ProductImage", "ProuctRating"};
		tblMdlProduct = new DefaultTableModel(column, 0);
		tblProduct = new JTable();
		tblProduct.setModel(tblMdlProduct);
		
		showTable();
		
		spQty = new JSpinner();
		btnAdd = new JButton("Add to Cart");
		scp = new JScrollPane(tblProduct);
		
		//JFrame
		setTitle("Buy Product");
		setResizable(true);
		setClosable(true);
		setSize(1080, 720);
		setVisible(true);
	}

	public void showTable() {
		// TODO Auto-generated method stub
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/appstoon");
			st = con.createStatement();
			rs = st.executeQuery("SELECT productid, genrename, productname, productprice, productquantity, productimage, productrating"
					+ " FROM products p"
					+ " JOIN genre g"
					+ " ON g.genreid = p.genreid");
			if (tblMdlProduct.getRowCount() >= 1) {
				tblMdlProduct.getDataVector().removeAllElements();
			}
			while (rs.next()) {
				String a = rs.getString(1);
				String b = rs.getString(2);
				String c = rs.getString(3);
				String d = rs.getString(4);
				String e = rs.getString(5);
				String f = rs.getString(6);
				String g = rs.getString(7);
				tblMdlProduct.addRow(new Object[] {a, b, c, d, e, f, g});
			}
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
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

		if (e.getSource() == btnAdd) {
			int getBoughtQty = Integer.parseInt(spQty.getValue().toString());
			boolean vldAllFields = lblPIDP.getText().equals("-") || lblPNameP.getText().equals("-") || lblPPriceP.getText().equals("-") || lblPGenreP.getText().equals("-") || lblPRatingP.getText().equals("-");
			boolean vldQty = getBoughtQty < 1 || getPStock < getBoughtQty;
			System.out.println(vldAllFields);
			System.out.println(vldQty);
			if (vldQty || vldAllFields) {
				if (vldAllFields) {
					JOptionPane.showMessageDialog(this, "Product must be selected!");
				} else if (vldQty) {
					if (getBoughtQty < 1) {
						JOptionPane.showMessageDialog(this, "The minimum quantity is 1.");
					}
					if (getPStock < getBoughtQty) {
						JOptionPane.showMessageDialog(this, "Quantity cannot be more than available stock.");
					}
				}				
			} else {
				c.addToCart(lblPIDP.getText(), getLoginUserID(), Integer.toString(getBoughtQty));
				for (int i = 0; i < tblMdlProduct.getRowCount(); i++) {
					int updatePQTY = Integer.parseInt(tblMdlProduct.getValueAt(i, 4).toString()) - getBoughtQty;
					if (tblMdlProduct.getValueAt(i, 0).equals(lblPIDP.getText())) {
						tblMdlProduct.setValueAt(updatePQTY, i, 4);
					}
				}
			}
		}
	}

}
