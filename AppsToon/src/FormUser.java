import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JMenuItem;

public class FormUser extends mainForm {
	JMenuItem miBuy;
	Connection con;
	Statement st;
	ResultSet rs;
	
	BuyProduct bp;

	public FormUser() {
		// TODO Auto-generated constructor stub
		super();
		init();
		addMBarItem();
		setLayout();
		showJFrame();
		
		miBuy.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				BuyProduct bp = new BuyProduct(getLoginUserID());
			//	Cart c = null;
				dp.add(bp);
				dp.add(bp.c);
				add(dp);
			}
		});
	}

	private void init() {
		// TODO Auto-generated method stub
		miBuy = new JMenuItem("Buy Product");
		mTransaction.add(miBuy);
	}

}
