package texiorder.userclient;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import no.ntnu.item.arctis.runtime.Block;
import texiorder.commen.ClientOrder;

public class UserClient extends Block {

	private JFrame frame;
	private JTextArea textArea;
	private JTextField inputArea;
	public String alias_user;
	
	public void show() {
		frame = new JFrame("Client Console" + alias_user);
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize=new Dimension((int)(screenSize.width/4),(int)(screenSize.height/4));
		int x=(int)(frameSize.width/4);
		int y=(int)(frameSize.height/4);
		frame.setBounds(x,y,frameSize.width,frameSize.height);
		
		//frame.getContentPane().setLayout(new GridLayout(4,3,10,10));
		Button button = new Button("Order");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sendToBlock("CLICKED");
			}

		});
		frame.getContentPane().add(button);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		
		inputArea = new JTextField();
		inputArea.setEnabled(true);

		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(inputArea, BorderLayout.NORTH);
		frame.getContentPane().add(new JScrollPane(textArea),BorderLayout.CENTER);
		frame.getContentPane().add(button,BorderLayout.SOUTH);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				sendToBlock("CLOSED");
			}
		});
		frame.setVisible(true);
		//frame.pack();
	}

	
	public static String getAlias(String id) {
		return id;
	}
	
	public static String getAlias(ClientOrder order) {
		return order.getAlias();
	}

	public ClientOrder createOrder() {
		if(inputArea.getText() == null)
			return null;
		ClientOrder order = new ClientOrder();
		order.setAlias(alias_user);
		order.setAddress(inputArea.getText());
		return order;
	}

	public void updateConsole(ClientOrder order) {
		if(order == null)
			return;
		if(order.getAck() != null)
			textArea.append(order.getAck());
		else if(order.getQueueNumber() > 0)
			textArea.append("Your are in the position " + String.valueOf(order.getQueueNumber()) + " of the waiting list.");
	}

}
