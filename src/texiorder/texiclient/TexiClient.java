package texiorder.texiclient;

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

public class TexiClient extends Block {

	private JFrame frame;
	private JTextArea textArea;
	public String alias_taxi;

	public void show() {
		frame = new JFrame("Client Console" + alias_taxi);
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize=new Dimension((int)(screenSize.width/4),(int)(screenSize.height/4));
		int x=(int)(frameSize.width/4);
		int y=(int)(frameSize.height/4);
		frame.setBounds(x,y,frameSize.width,frameSize.height);
		
		//frame.getContentPane().setLayout(new GridLayout(4,3,10,10));
		Button buttonLogOn = new Button("LogOn");
		buttonLogOn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sendToBlock("LOGON");
			}
		});
		//frame.getContentPane().add(button);
		
		Button buttonLogOff = new Button("LogOff");
		buttonLogOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sendToBlock("LOGOFF");
			}
		});
		
		Button buttonBusy = new Button("Busy");
		buttonBusy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sendToBlock("BUSY");
			}
		});
		
		Button buttonFree = new Button("Free");
		buttonFree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sendToBlock("FREE");
			}
		});
		
		Button buttonAccept = new Button("Accept");
		buttonAccept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sendToBlock("ACCEPT");
			}
		});
		
		textArea = new JTextArea();
		textArea.setEditable(false);

		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(new JScrollPane(textArea),BorderLayout.CENTER);
		//frame.getContentPane().add(buttonLogOn,BorderLayout.SOUTH);
		frame.getContentPane().add(buttonLogOn);
		frame.getContentPane().add(buttonLogOff);
		frame.getContentPane().add(buttonBusy);
		frame.getContentPane().add(buttonFree);
		frame.getContentPane().add(buttonAccept);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				sendToBlock("CLOSED");
			}
		});
		frame.setVisible(true);
		//frame.pack();
	}

}
