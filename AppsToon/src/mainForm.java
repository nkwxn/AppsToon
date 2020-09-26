import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class mainForm extends JFrame implements ActionListener {
	JDesktopPane dp;
	JMenuBar mb;
	JMenu mTransaction;
	JMenuItem miView, miLogout;

	private String loginUserID;
	
	public String getLoginUserID() {
		return loginUserID;
	}

	public void setLoginUserID(String loginUserID) {
		this.loginUserID = loginUserID;
	}

	Login l;

	public mainForm() {
		// TODO Auto-generated constructor stub
		ImageIcon img = new ImageIcon("src/books.jpg");
		dp = new JDesktopPane();
		JLabel lbl = new JLabel(img);
		lbl.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());
		dp.add(lbl, new Integer(Integer.MIN_VALUE));
		
		mb = new JMenuBar();
		mTransaction = new JMenu("Transaction");
		miView = new JMenuItem("View Transaction");
		miLogout = new JMenuItem("Logout");		
		
		//logout
		miLogout.addActionListener(this);
		miView.addActionListener(this);
	}
	
	public void addMBarItem() {
		mTransaction.add(miView);
		mb.add(mTransaction);
		mb.add(miLogout);		
	}
	
	public void setLayout() {
		setJMenuBar(mb);
		add(dp);
	}
	
	public void showJFrame() {
		setTitle("Main Form");
		setExtendedState(MAXIMIZED_BOTH);
	//	setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == miLogout) {
			new Login();
			setVisible(false);
		}
		if (e.getSource() == miView) {
			ViewTransaction vt = new ViewTransaction(getLoginUserID());
			dp.add(vt);
			add(dp);
		}
	}

}
