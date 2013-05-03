package texiorder.mytaxigui;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import java.awt.Button;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

import no.ntnu.item.arctis.runtime.Block;

public class MyTaxiGUI extends Block {
	private JFrame frame;
	private JTextArea text;
	private JTextArea state;
	private final static String[] BUTTONS = {"LOGON", "LOGOFF", "FREE", "BUSY", "ACCEPT"};
	private Map<String, Button> buttonsMap = new HashMap<String, Button>();
	
	public void show(String id) {
		System.out.println("creating GUI: " + id);
		frame = new JFrame(id);
		frame.getContentPane().setLayout(new GridLayout(4,3,10,10));
		
		
		text = new JTextArea(4, 20);
		frame.getContentPane().add(text);
		
		state = new JTextArea(1, 20);
		frame.getContentPane().add(state);
	
		for(final String d: BUTTONS) {
			Button b = new Button(d);
			buttonsMap.put(d.toLowerCase(), b);
			b.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					sendToBlock(d, d);
				}
			});
			frame.getContentPane().add(b);
		}
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				sendToBlock("CLOSED");
			}
		});
		
		// init state: logoff
		changeStatusGUI("offline");
		
		frame.setVisible(true);
		frame.pack();
	}

	/*public void hide() {
		frame.setVisible(false);
	}*/
	
	// triggered when new order or cancel order comes in
	public void showString(String in) {
		text.setText(in);
		
		// when deciding whether to accept the order, 
		// disable logoff button
		if (in.contains("cancel") == false) {
			buttonsMap.get("logoff").setEnabled(false);
		}
		
		// when order comes and current state is busy, 
		// show correct GUI
		if (in.contains("cancel") == false && text.getText().toLowerCase().contains("busy")) {
			buttonsMap.get("logon").setEnabled(false);
			buttonsMap.get("logoff").setEnabled(false);
			buttonsMap.get("free").setEnabled(true);
			buttonsMap.get("busy").setEnabled(false);
			buttonsMap.get("accept").setEnabled(true);
		}
		
		// judge if new order or cancel order
		if (in.contains("cancel")) {
			buttonsMap.get("accept").setEnabled(false);
		} else {
			buttonsMap.get("accept").setEnabled(true);
		}
		
		System.out.println("Incoming GUI string: " + in);
	}

	public void showState(String in) {
		changeStatusGUI(in);
	}

	public void setFree() {
		changeStatusGUI("Free");
	}

	public void setLogOff() {
		changeStatusGUI("Offline");
	}

	public void setBusy() {
		changeStatusGUI("Busy");
	}
	
	private void changeStatusGUI(String status) {
		// set status textarea
		state.setText(status);
		
		// change enabled for buttons according to status
		if (status.toLowerCase().equals("logon")) {
			buttonsMap.get("logon").setEnabled(false);
			buttonsMap.get("logoff").setEnabled(true);
			buttonsMap.get("free").setEnabled(false);
			buttonsMap.get("busy").setEnabled(false);
			buttonsMap.get("accept").setEnabled(true);
		} else if (status.toLowerCase().equals("offline")) {
			buttonsMap.get("logon").setEnabled(true);
			buttonsMap.get("logoff").setEnabled(false);
			buttonsMap.get("free").setEnabled(false);
			buttonsMap.get("busy").setEnabled(false);
			buttonsMap.get("accept").setEnabled(false);
		} else if (status.toLowerCase().equals("free")) {
			buttonsMap.get("logon").setEnabled(false);
			buttonsMap.get("logoff").setEnabled(true);
			buttonsMap.get("free").setEnabled(false);
			buttonsMap.get("busy").setEnabled(true);
			buttonsMap.get("accept").setEnabled(false);
		} else if (status.toLowerCase().equals("busy")) {
			buttonsMap.get("logon").setEnabled(false);
			buttonsMap.get("logoff").setEnabled(false);
			buttonsMap.get("free").setEnabled(true);
			buttonsMap.get("busy").setEnabled(false);
			buttonsMap.get("accept").setEnabled(false);
		} else if (status.toLowerCase().equals("accept")) {
			buttonsMap.get("logon").setEnabled(false);
			buttonsMap.get("logoff").setEnabled(false);
			buttonsMap.get("free").setEnabled(false);
			buttonsMap.get("busy").setEnabled(true);
			buttonsMap.get("accept").setEnabled(false);
		}
	}
}