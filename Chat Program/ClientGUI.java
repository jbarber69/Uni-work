import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class ClientGUI implements ActionListener{
	
	//main window
	private JFrame frame;
	//button to send message
	private JButton sendButton;
	//field in which message is typed 
	private JTextField message;
	//displays all messages in chat
	private JTextArea chatDisplay;
	//background to separate chatDisplay and message field
	private JPanel messageBG;
	private ChatClient client;
	private static  String userMessage;

	
	public ClientGUI(ChatClient c){
		messageBG = new JPanel();
		message = new JTextField(20);
        sendButton = new JButton("Send");
        sendButton.addActionListener(this);
        chatDisplay = new JTextArea();
        client = c;
        this.mainFrame();
	}
	
	public void mainFrame(){
		frame = new JFrame("Chat client");
        frame.add(BorderLayout.SOUTH, messageBG);
        messageBG.setLayout(new GridBagLayout());	
		frame.setVisible(true);

        chatDisplay.setEditable(false);
        frame.add(new JScrollPane(chatDisplay), BorderLayout.CENTER);
        chatDisplay.setLineWrap(true);

        GridBagConstraints left = new GridBagConstraints();
        left.anchor = GridBagConstraints.WEST;
        GridBagConstraints right = new GridBagConstraints();
        right.anchor = GridBagConstraints.EAST;
        right.weightx = 2.0;
        messageBG.add(message, left);
        messageBG.add(sendButton, right);
        
        frame.setSize(400, 350);
	}

	public void sendMessage(){
		client.userInput = message.getText();
		
	}
	
	public void actionPerformed(ActionEvent e){
		Object action = e.getSource();
		if (action == sendButton && !message.getText().equals("")){
			this.sendMessage();
			chatDisplay.append(message.getText());
			chatDisplay.append("\n");
		}
	}
	
	public void displayMessage(String message){
		chatDisplay.append(message);
		chatDisplay.append("\n");
	}	
}