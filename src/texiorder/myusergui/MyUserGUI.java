package texiorder.myusergui;

import no.ntnu.item.arctis.runtime.Block;


	import java.awt.Button;
	import javax.swing.JLabel;
	import java.awt.GridLayout;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
	import java.awt.event.WindowAdapter;
	import java.awt.event.WindowEvent;

	import javax.swing.JFrame;
	import javax.swing.JTextArea;
	import javax.swing.JTextField;
	import java.awt.*;
	

	public class MyUserGUI extends Block {
		private JFrame frame;
		private JTextArea textScreen;
		private JTextField textAddress;
		
		public void show(String id) {
			System.out.println("User GUI created: " +id);
			frame = new JFrame(id);
			frame.getContentPane().setLayout(new GridLayout(5,3,10,10));
			
			textScreen = new JTextArea(4, 20);
			textScreen.setLineWrap(true);
			frame.getContentPane().add(textScreen);
			
			Panel values = new Panel();
			JLabel lAddress = new JLabel("Enter Address");
//			frame.getContentPane().add(lAddress);
			
			textAddress = new JTextField(10);
//			frame.getContentPane().add(textDest);
			values.add (lAddress);
			values.add (textAddress);
			frame.getContentPane().add(values);		
			
			
			Button b = new Button("Order Taxi");
			b.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
		//			    final String address = textAddress.getText();
					    sendToBlock("REQUEST", textAddress.getText());
					    textScreen.append("\n"+textAddress.getText() + "\n");
					    System.out.println("user address: " + textAddress.getText());				    
						
					}
				});
			frame.getContentPane().add(b);
			
			b = new Button("Cancel");
			b.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						sendToBlock("CANCEL", "Cancel");
					}
				});
			frame.getContentPane().add(b);
			
			b = new Button("Get queue number");
			b.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						sendToBlock("GETQUEUENO", "Get queue number");
					}
				});
			frame.getContentPane().add(b);
			
			frame.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					sendToBlock("CLOSED");
				}
			});
			
					
			frame.setVisible(true);
			frame.pack();
		}

		/*public void hide() {
			frame.setVisible(false);
		}*/

		public void showString(String in) {
			textScreen.setText(in);
		}
	}
