import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class ManageProduct extends JInternalFrame implements MouseListener {
	JPanel pnlHeader, pnlBody, pnlMng, pnlProduct, pnlDesc, pnlChooseImg, pnlBtns;
	JTable tblProducts;
	JScrollPane scp;
	JLabel lblPList, lblPID, lblPName, lblPPrice, lblPRating, lblPStock, lblGenre, lblPImage, lblImg;
	JTextField txtPID, txtPName, txtPPrice, txtPRating, txtPImage;
	JSpinner spnPStock;
	JComboBox<String> cbGenre;
	JButton btnInsert, btnUpdate, btnDelete, btnSubmit, btnCancel, btnChoose;
	ImageIcon img;
	DefaultTableModel tblMdlProduct;
	JFileChooser fileImg;
	File fileChoose, fileDest;
	Path pathChooseFile, pathDestination;
	
	InputStream is;
	
	String genProductID;
	
	private Connection con;
	private Statement st;
	private ResultSet rs;
	private PreparedStatement pst;

	public ManageProduct() {
		// TODO Auto-generated constructor stub
		init();
		
		//Add panel to JFrame
		add(pnlHeader, BorderLayout.NORTH);
		add(pnlBody, BorderLayout.CENTER);
		
		//JFrame
		setTitle("Manage Product");
		setResizable(true);
		setClosable(true);
		setSize(1080, 720);
		setVisible(true);
	}

	private void init() {
		// TODO Auto-generated method stub 		
		pnlHeader = new JPanel();
		pnlBody = new JPanel();
		pnlMng = new JPanel();
		pnlProduct = new JPanel();
		pnlDesc = new JPanel();
		pnlChooseImg = new JPanel();
		pnlBtns = new JPanel();
		
		showTable();
		
		lblPList = new JLabel("Product List");
		lblPID = new JLabel("Product ID");
		lblPName = new JLabel("Product Name");
		lblPPrice = new JLabel("Product Price");
		lblPRating = new JLabel("Product Rating");
		lblPStock = new JLabel("Product Stock");
		lblGenre = new JLabel("Genre");
		lblPImage = new JLabel("Image");
		lblImg = new JLabel();
		
		img = new ImageIcon("src/NoImageFound.jpg");
		lblImg.setIcon(img);
		
		txtPID = new JTextField();
		txtPName = new JTextField();
		txtPPrice = new JTextField();
		txtPRating = new JTextField();
		txtPImage = new JTextField();
		
		spnPStock = new JSpinner();
		cbGenre = new JComboBox<>();
		cbGenre.addItem("");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/appstoon");
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM genre");
			while (rs.next()) {
				cbGenre.addItem(rs.getString(2));
			}
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		btnInsert = new JButton("Insert");
		btnUpdate = new JButton("Update");
		btnDelete = new JButton("Delete");
		btnSubmit = new JButton("Submit");
		btnCancel = new JButton("Cancel");
		btnChoose = new JButton("Choose");
		
		//Header
		Font font = new Font("Segoe UI", Font.BOLD, 26);
		lblPList.setFont(font);
		pnlHeader.add(lblPList);
		
		//Body, setLayout
		pnlBody.setLayout(new GridLayout(2, 1, 10, 10));
		pnlMng.setLayout(new BorderLayout());;
		pnlProduct.setLayout(new GridLayout(1, 2, 10, 10));
		pnlDesc.setLayout(new GridLayout(7, 2, 10, 10));
		pnlChooseImg.setLayout(new GridLayout(1, 2));
		pnlBtns.setLayout(new GridLayout(5, 1, 10, 10));
		
		pnlBody.setBorder(new EmptyBorder(10, 10, 10, 10));
		pnlProduct.setBorder(new EmptyBorder(0, 0, 0, 10));
		
		//Body, add to panels
		pnlChooseImg.add(txtPImage);
		pnlChooseImg.add(btnChoose);
		
		pnlDesc.add(lblPID);
		pnlDesc.add(txtPID);
		pnlDesc.add(lblPName);
		pnlDesc.add(txtPName);
		pnlDesc.add(lblPPrice);
		pnlDesc.add(txtPPrice);
		pnlDesc.add(lblPRating);
		pnlDesc.add(txtPRating);
		pnlDesc.add(lblPStock);
		pnlDesc.add(spnPStock);
		pnlDesc.add(lblGenre);
		pnlDesc.add(cbGenre);
		pnlDesc.add(lblPImage);
		pnlDesc.add(pnlChooseImg);
		
		pnlBtns.add(btnInsert);
		pnlBtns.add(btnUpdate);
		pnlBtns.add(btnDelete);
		pnlBtns.add(btnSubmit);
		pnlBtns.add(btnCancel);
		
		pnlProduct.add(lblImg);
		pnlProduct.add(pnlDesc);
		
		pnlMng.add(pnlProduct, BorderLayout.CENTER);
		pnlMng.add(pnlBtns, BorderLayout.EAST);
		
		pnlBody.add(scp);
		pnlBody.add(pnlMng);
		
		//disable and enable button and textfield
		btnInsert.setEnabled(true);
		btnUpdate.setEnabled(true);
		btnDelete.setEnabled(true);
		btnChoose.setEnabled(false);
		btnSubmit.setEnabled(false);
		btnCancel.setEnabled(false);
		
		txtPID.setEnabled(false);
		txtPName.setEnabled(false);
		txtPPrice.setEnabled(false);
		txtPRating.setEnabled(false);
		txtPImage.setEnabled(false);
		spnPStock.setEnabled(false);
		cbGenre.setEnabled(false);
		
		btnInsert.addMouseListener(this);
		btnUpdate.addMouseListener(this);
		btnDelete.addMouseListener(this);
		btnCancel.addMouseListener(this);
		btnSubmit.addMouseListener(this);
		btnChoose.addMouseListener(this);
		tblProducts.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				int intSelectedRow = tblProducts.getSelectedRow();
				String getProductID = tblProducts.getModel().getValueAt(intSelectedRow, 0).toString();
				String getProductName = tblProducts.getModel().getValueAt(intSelectedRow, 2).toString();
				String getProductPrice = tblProducts.getModel().getValueAt(intSelectedRow, 3).toString();
				String getProductRating = tblProducts.getModel().getValueAt(intSelectedRow, 6).toString();
				String getProductStock = tblProducts.getModel().getValueAt(intSelectedRow, 4).toString();
				String getProductGenre = tblProducts.getModel().getValueAt(intSelectedRow, 1).toString();
				String getProductImage = tblProducts.getModel().getValueAt(intSelectedRow, 5).toString();
				
				//set placeholder fields
				txtPID.setText(getProductID);
				txtPName.setText(getProductName);
				txtPPrice.setText(getProductPrice);
				txtPRating.setText(getProductRating);
				spnPStock.setValue(new Integer(Integer.parseInt(getProductStock)));
				cbGenre.setSelectedItem(getProductGenre);
				txtPImage.setText(getProductImage);
				
				//set product image
				img = new ImageIcon("src/images/" + getProductImage);
				Image img1 = img.getImage();
				Image img2 = img1.getScaledInstance(lblImg.getWidth(), lblImg.getHeight(), Image.SCALE_SMOOTH);
				ImageIcon img3 = new ImageIcon(img2);
				lblImg.setIcon(img3);
				
				//tulis path img awal disini
				fileChoose = new File("src/images", txtPImage.getText());
			}
		});
	}

	private void showTable() {
		// TODO Auto-generated method stub

		tblMdlProduct = new DefaultTableModel(new String[] {"ProductID", "GenreName", "ProductName", "ProductPrice", "ProductQuantity", "ProductImage", "ProuctRating"}, 0);
		tblProducts = new JTable();
		tblProducts.setModel(tblMdlProduct);
		scp = new JScrollPane(tblProducts);
		
		getTableDatas();
	}

	private void getTableDatas() {
		// TODO Auto-generated method stub
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/appstoon");
			st = con.createStatement();
			rs = st.executeQuery("SELECT * "
					+ "FROM products p "
					+ "JOIN genre g "
					+ "ON g.genreid = p.genreid");
			while (rs.next()) {
				String a = rs.getString("ProductID");
				String b = rs.getString("GenreName");
				String c = rs.getString("ProductName");
				String d = rs.getString("ProductPrice");
				String e = rs.getString("ProductQuantity");
				String f = rs.getString("ProductImage");
				String g = rs.getString("ProductRating");
				tblMdlProduct.addRow(new Object[] {a, b, c, d, e, f, g});
			}
			
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}		
	}

	private void setDefaultImage() {
		// TODO Auto-generated method stub
		img = new ImageIcon("src/NoImageFound.jpg");
		Image img1 = img.getImage();
		Image img2 = img1.getScaledInstance(lblImg.getWidth(), lblImg.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon img3 = new ImageIcon(img2);
		lblImg.setIcon(img3);
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
		if (e.getSource() == btnInsert && btnInsert.isEnabled()) {
			String genProductID = "";
			int intProdNo;
			try {
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://localhost/appstoon");
				st = con.createStatement();
				rs = st.executeQuery("SELECT COUNT(*) FROM products");
				rs.next();
				intProdNo = rs.getInt("COUNT(*)") + 1;
				int getNumberPID = 0;
				int realProdNumber = 0;
				for (int i = 0; i < tblMdlProduct.getRowCount(); i++) {
					getNumberPID = Integer.parseInt(tblProducts.getModel().getValueAt(i, 0).toString().substring(2));
					System.out.println(getNumberPID);
					if (tblMdlProduct.getRowCount() < getNumberPID) {
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
					genProductID = "PD00" + realProdNumber;
					break;
				case 2:
					genProductID = "PD0" + realProdNumber;
					break;
				case 3:
					genProductID = "PD" + realProdNumber;
					break;

				default:
					break;
				}
				txtPID.setText(genProductID);
				con.close();
			} catch (Exception e1) {
				// TODO: handle exception
				e1.printStackTrace();
			}
			txtPName.setText("");
			txtPPrice.setText("");
			txtPRating.setText("");
			txtPImage.setText("");
			spnPStock.setValue(0);
			cbGenre.setSelectedIndex(0);
			txtPName.setEnabled(true);
			txtPPrice.setEnabled(true);
			txtPRating.setEnabled(true);
			spnPStock.setEnabled(true);
			btnChoose.setEnabled(true);
			btnSubmit.setEnabled(true);
			btnCancel.setEnabled(true);
			cbGenre.setEnabled(true);
			btnInsert.setEnabled(false);
			btnUpdate.setEnabled(false);
			btnDelete.setEnabled(false);
		}
		if (e.getSource() == btnUpdate && btnUpdate.isEnabled()) {
			boolean vldAllFields = txtPID.getText().equals("") || txtPName.getText().equals("") || txtPPrice.getText().equals("") || txtPRating.getText().equals("") || txtPImage.getText().equals("");
			if (vldAllFields) {
				JOptionPane.showMessageDialog(this, "Data must be selected!");
			} else {
				txtPName.setEnabled(true);
				txtPPrice.setEnabled(true);
				txtPRating.setEnabled(true);
				spnPStock.setEnabled(true);
				btnChoose.setEnabled(true);
				btnSubmit.setEnabled(true);
				btnCancel.setEnabled(true);
				cbGenre.setEnabled(true);
				btnInsert.setEnabled(false);
				btnUpdate.setEnabled(false);
				btnDelete.setEnabled(false);
			}
		}
		if (e.getSource() == btnDelete && btnDelete.isEnabled()) {
			boolean vldAllFields = txtPID.getText().equals("") || txtPName.getText().equals("") || txtPPrice.getText().equals("") || txtPRating.getText().equals("") || txtPImage.getText().equals("");
			if (vldAllFields) {
				JOptionPane.showMessageDialog(this, "Data must be selected!");
			} else {
				
				try {
					Class.forName("com.mysql.jdbc.Driver");
					con = DriverManager.getConnection("jdbc:mysql://localhost/appstoon");
					st = con.createStatement();
					rs = st.executeQuery("SELECT ProductID FROM Products");
					String getProductID = "";
					int intDBRow = 0;
					while (rs.next()) {
						if (rs.getString(1).equals(txtPID.getText())) {
							getProductID = rs.getString(1);
							intDBRow = rs.getRow();
							break;
						}
					}
					pst = con.prepareStatement("DELETE FROM products WHERE ProductID LIKE ?");
					pst.setString(1, txtPID.getText());
					pst.executeUpdate();
					JOptionPane.showMessageDialog(this, "Delete success!");
					for (int i = 0; i < tblMdlProduct.getRowCount(); i++) {
						if (getProductID.equals(tblMdlProduct.getValueAt(i, 0))) {
							tblMdlProduct.removeRow(i);
							break;
						}
					}
					con.close();
				} catch (Exception e1) {
					// TODO: handle exception
					e1.printStackTrace();
				}
				setDefaultImage();
				// activate all
				txtPName.setText("");
				txtPPrice.setText("");
				txtPRating.setText("");
				txtPImage.setText("");
				txtPID.setText("");
				spnPStock.setValue(0);
				cbGenre.setSelectedIndex(0);
			}
		}
		if (e.getSource() == btnCancel && btnCancel.isEnabled()) {
			txtPName.setText("");
			txtPPrice.setText("");
			txtPRating.setText("");
			txtPImage.setText("");
			txtPID.setText("");
			spnPStock.setValue(0);
			cbGenre.setSelectedIndex(0);
			txtPName.setEnabled(false);
			txtPPrice.setEnabled(false);
			txtPRating.setEnabled(false);
			spnPStock.setEnabled(false);
			btnChoose.setEnabled(false);
			btnSubmit.setEnabled(false);
			btnCancel.setEnabled(false);
			cbGenre.setEnabled(false);
			btnInsert.setEnabled(true);
			btnUpdate.setEnabled(true);
			btnDelete.setEnabled(true);
			setDefaultImage();
		}
		if (e.getSource() == btnSubmit && btnSubmit.isEnabled()) {
			String getGenreID = "";
			boolean vldStock = Integer.parseInt(spnPStock.getValue().toString()) < 1;
			boolean vldProductPrice = false; //if not numeric true
			for (int i = 0; i < txtPPrice.getText().length(); i++) {
				if (!Character.isDigit(txtPPrice.getText().charAt(i))) {
					vldProductPrice = true;
					break;
				}
			}
			boolean vldGenre = (cbGenre.getSelectedIndex() == 0);
			boolean vldAllFields = txtPID.getText().equals("") || txtPName.getText().equals("") || txtPPrice.getText().equals("") || txtPRating.getText().equals("") || txtPImage.getText().equals("");
			if (vldAllFields || vldGenre || vldStock || vldProductPrice) {
				if (vldAllFields) {
					JOptionPane.showMessageDialog(this, "All fields must be filled!");
				}
				if (vldStock) {
					JOptionPane.showMessageDialog(this, "The minimum product stock is 1");
				}
				if (vldProductPrice) {
					JOptionPane.showMessageDialog(this, "Product Price must be numeric!");
				}
				if (vldGenre) {
					JOptionPane.showMessageDialog(this, "Genre must be selected!");
				}
			} else {
				try {
					Class.forName("com.mysql.jdbc.Driver");
					con = DriverManager.getConnection("jdbc:mysql://localhost/appstoon");
					st = con.createStatement();
					rs = st.executeQuery("SELECT * FROM genre");
					while (rs.next()) {
						int choice = cbGenre.getSelectedIndex();
						int dbgenre = rs.getRow();
						getGenreID = rs.getString(1);
						if (choice == dbgenre) {
							System.out.println(choice);
							System.out.println(dbgenre);
							break;
						}
					}
					st = con.createStatement();
					rs = st.executeQuery("SELECT ProductID FROM Products");
					String getProductID = "";
					while (rs.next()) {
						if (rs.getString(1).equals(txtPID.getText())) {
							getProductID = rs.getString(1);
							break;
						}
					}

					if (!txtPID.getText().equals(getProductID)) {
						//buat insert data
						String dbtablerow = "ProductID, GenreID, ProductName, ProductPrice, ProductQuantity, ProductImage, ProductRating";
						//copy image
						
						fileDest = new File("src/images", fileChoose.getName());
						pathChooseFile = fileChoose.toPath();
						pathDestination = fileDest.toPath();
						try {
							Files.copy(pathChooseFile, pathDestination);
						} catch (IOException e2) {
							// TODO: handle exception
							e2.printStackTrace();
						}
						pst = con.prepareStatement("INSERT INTO products (" + dbtablerow + ") VALUES (?, ?, ?, ?, ?, ?, ?)");
						pst.setString(1, txtPID.getText());
						pst.setString(2, getGenreID);
						pst.setString(3, txtPName.getText());
						pst.setString(4, txtPPrice.getText());
						pst.setString(5, spnPStock.getValue().toString());
						pst.setString(6, txtPImage.getText());
						pst.setString(7, txtPRating.getText());
						pst.executeUpdate();
						tblMdlProduct.addRow(new String[]{txtPID.getText(), cbGenre.getSelectedItem().toString(), txtPName.getText(), txtPPrice.getText(), spnPStock.getValue().toString(), txtPImage.getText(), txtPRating.getText()});
					} else {
						//buat update data
						//copy image
						fileDest = new File("src/images", fileChoose.getName());
						pathChooseFile = fileChoose.toPath();
						pathDestination = fileDest.toPath();
						try {
							Files.copy(pathChooseFile, pathDestination);
						} catch (IOException e2) {
							// TODO: handle exception
							e2.printStackTrace();
						}
						pst = con.prepareStatement("UPDATE products "
								+ "SET GenreID = ?, "
								+ "ProductName = ?, "
								+ "ProductPrice = ?, "
								+ "ProductQuantity = ?, "
								+ "ProductImage = ?, "
								+ "ProductRating = ? "
								+ "WHERE ProductID = ?");
						pst.setString(1, getGenreID);
						pst.setString(2, txtPName.getText());
						pst.setString(3, txtPPrice.getText());
						pst.setString(4, spnPStock.getValue().toString());
						pst.setString(5, txtPImage.getText());
						pst.setString(6, txtPRating.getText());
						pst.setString(7, txtPID.getText());
						pst.executeUpdate();
						String [] updateDatas = {cbGenre.getSelectedItem().toString(), txtPName.getText(), txtPPrice.getText(), spnPStock.getValue().toString(), txtPImage.getText(), txtPRating.getText()};
						for (int i = 0; i < tblMdlProduct.getRowCount(); i++) {
							if (tblMdlProduct.getValueAt(i, 0).equals(txtPID.getText())) {
								for (int j = 0; j < updateDatas.length; j++) {
									tblMdlProduct.setValueAt(updateDatas[j], i, j+1);
								}
							}
						}
					}
					con.close();
				} catch (Exception e1) {
					// TODO: handle exception
					e1.printStackTrace();
				}
				txtPName.setText("");
				txtPPrice.setText("");
				txtPRating.setText("");
				txtPImage.setText("");
				txtPID.setText("");
				spnPStock.setValue(0);
				cbGenre.setSelectedIndex(0);
				
				setDefaultImage();
				//enable dan disable button
				txtPName.setEnabled(false);
				txtPPrice.setEnabled(false);
				txtPRating.setEnabled(false);
				spnPStock.setEnabled(false);
				btnChoose.setEnabled(false);
				btnSubmit.setEnabled(false);
				btnCancel.setEnabled(false);
				cbGenre.setEnabled(false);
				btnInsert.setEnabled(true);
				btnUpdate.setEnabled(true);
				btnDelete.setEnabled(true);
			}
		}
		if (e.getSource() == btnChoose && btnChoose.isEnabled()) {
			fileImg = new JFileChooser();
			int i = fileImg.showOpenDialog(this);
			if (i == JFileChooser.APPROVE_OPTION) {
				txtPImage.setText(fileImg.getSelectedFile().getName());
				fileChoose = fileImg.getSelectedFile();
				img = new ImageIcon(fileImg.getSelectedFile().getPath());
				Image img1 = img.getImage();
				Image img2 = img1.getScaledInstance(lblImg.getWidth(), lblImg.getHeight(), Image.SCALE_SMOOTH);
				ImageIcon img3 = new ImageIcon(img2);
				lblImg.setIcon(img3);
			}
		}
	}

}
