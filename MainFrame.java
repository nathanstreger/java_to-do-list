import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainFrame {

	// Member objects
	private JFrame frmToDoList;
	private JTextField ItemField;
	private int nToDoItems;
	private List<String> labelItems;
	private List<JLabel> LabelObjects;
	private List<JButton> ButtonObjects;
	
	// Member constants
	private int LEFT_ALIGN_X_NEW_ITEM_FIELD = 27;
	private int LEFT_ALIGN_X_TO_DO_ITEM = 35;
	private int NEW_ITEM_FIELD_WIDTH = 243;
	private int TO_DO_ITEM_WIDTH = 200;
	private int DELETE_BUTTON_LEFT_ALIGN_X = TO_DO_ITEM_WIDTH + 45;
	private int DELETE_BUTTON_WIDTH = 60;
	private int TO_DO_HEIGHT = 20;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame window = new MainFrame();
					window.frmToDoList.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		nToDoItems = 0;
		labelItems = new ArrayList<String>();
		LabelObjects = new ArrayList<JLabel>();
		ButtonObjects = new ArrayList<JButton>();
		
		frmToDoList = new JFrame();
		frmToDoList.setTitle("To Do List");
		frmToDoList.setBounds(100, 100, 450, 300);
		frmToDoList.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmToDoList.getContentPane().setLayout(null);
		
		ItemField = new JTextField();		
		ItemField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				//---------------------------------------
				// Limit the character length to 30.
				//
				if (ItemField.getText().length() == 30) {
					e.consume();
				}
				
				//---------------------------------------
				// Trigger the adding functionality if the
				// user presses the "enter" key.
				//
				if (e.getExtendedKeyCode() == 0xa) {
					onAddItem();
				}
			}
		});
		ItemField.setBounds(LEFT_ALIGN_X_NEW_ITEM_FIELD, 38, NEW_ITEM_FIELD_WIDTH, 26);
		frmToDoList.getContentPane().add(ItemField);
		ItemField.setColumns(10);
		
		JButton btnNewButton = new JButton("Add");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onAddItem();
			}
		});
		btnNewButton.setBounds(282, 38, 117, 29);
		frmToDoList.getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("New Item");
		lblNewLabel.setBounds(LEFT_ALIGN_X_NEW_ITEM_FIELD, 18, 61, 16);
		frmToDoList.getContentPane().add(lblNewLabel);
		
	}
	
	private void onAddItem() {
		String newItem = ItemField.getText();
		
		if (newItem.length() == 0) {
			return;
		}
		
		if (nToDoItems == 10) {
			JOptionPane.showConfirmDialog(null, "There are too many items on your To Do list!", "You are too busy!!", 2);
			return;
		}
		
		if (labelItems.contains(newItem)) {
			return;
		}
		
		CreateRow(newItem);
		ItemField.setText(null);
		frmToDoList.repaint();
	}
	
	//----------------------------------------------------------------
	// We can treat this as removing the entire list from the display,
	// then removing the item from the ongoing lists that we are using
	// to handle the display, and adding back everything again after 
	// resetting the boundaries on them all.
	//
	private void OnDelete(JButton btnItem) {
		for (int i = 0; i < LabelObjects.size(); i++)
		{
			frmToDoList.remove(LabelObjects.get(i));
			frmToDoList.remove(ButtonObjects.get(i));
		}
		
		int nIndex = ButtonObjects.indexOf(btnItem);
		
		nToDoItems--;
		labelItems.remove(nIndex);
		LabelObjects.remove(nIndex);
		ButtonObjects.remove(nIndex);
		
		for (int i = 0; i < nToDoItems; i++) {
			LabelObjects.get(i).setBounds(LEFT_ALIGN_X_TO_DO_ITEM, 72 + TO_DO_HEIGHT * i, TO_DO_ITEM_WIDTH, 16);
			LabelObjects.get(i).setText((i + 1) + ". " + labelItems.get(i));
			ButtonObjects.get(i).setBounds(DELETE_BUTTON_LEFT_ALIGN_X, 72 + TO_DO_HEIGHT * i, DELETE_BUTTON_WIDTH, 16);
			
			frmToDoList.add(LabelObjects.get(i));
			frmToDoList.add(ButtonObjects.get(i));
		}
		
		frmToDoList.repaint();
	}
	
	//------------------------------------------
	// Will create a new item in the To Do List.
	// Adds a new Delete button and updates the
	// internal members.
	//
	private void CreateRow(String strTitle) {
		nToDoItems++;
		JLabel lblAddedLabel = new JLabel(nToDoItems + ". " + strTitle);
		lblAddedLabel.setBounds(LEFT_ALIGN_X_TO_DO_ITEM, 72 + TO_DO_HEIGHT * (nToDoItems - 1), TO_DO_ITEM_WIDTH, 16);
		frmToDoList.getContentPane().add(lblAddedLabel);
		
		JButton btnAddedButton = new JButton("Delete");
		btnAddedButton.setBounds(DELETE_BUTTON_LEFT_ALIGN_X, 72 + TO_DO_HEIGHT * (nToDoItems - 1), DELETE_BUTTON_WIDTH, 16);
		btnAddedButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OnDelete(btnAddedButton);
			}
		});
		
		frmToDoList.getContentPane().add(btnAddedButton);
		
		labelItems.add(strTitle);
		LabelObjects.add(lblAddedLabel);
		ButtonObjects.add(btnAddedButton);
	}
}
