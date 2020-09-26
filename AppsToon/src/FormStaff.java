import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class FormStaff extends mainForm {
	JMenu mManage;
	JMenuItem miUser, miProduct;

	public FormStaff() {
		// TODO Auto-generated constructor stub
		super();
		init();
		addMBarItem();
		setLayout();
		showJFrame();
		
		miProduct.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ManageProduct mp = new ManageProduct();
				dp.add(mp);
				add(dp);
			}
		});
		
		miUser.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				ManageUser mu = new ManageUser();
				dp.add(mu);
				add(dp);
			}
		});
	}

	private void init() {
		// TODO Auto-generated method stub
		mManage = new JMenu("Manage");
		miUser = new JMenuItem("User");
		miProduct = new JMenuItem("Product");
		
		mManage.add(miUser);
		mManage.add(miProduct);
		mb.add(mManage);
	}

}
