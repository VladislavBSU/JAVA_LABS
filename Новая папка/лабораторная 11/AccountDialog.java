package task_13;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AccountDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldToyCode;
	private JTextField textFieldToyName;
	private JTextField textFieldLowerBound;
	private JTextField textFieldUpperBound;
	private JTextField textFieldPrice;
	private JTextField textFieldCount;
	private JTextField textFieldDate;
	private JTextField textFieldProvider;
	
	private Account readData () {
		return Account.getAccountByArgs(textFieldToyCode.getText(), textFieldToyName.getText(), textFieldLowerBound.getText(), textFieldUpperBound.getText(),
										textFieldPrice.getText(), textFieldCount.getText(), textFieldDate.getText(), textFieldProvider.getText());
	}
	

	public static void main(String[] args) {
		try {
			AccountDialog dialog = new AccountDialog(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public AccountDialog (MyFrame owner) {
		super(owner, "Account Dialog", true);
		setBounds(100, 100, 260, 283);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel labelToyName = new JLabel("Name");
			labelToyName.setBounds(10, 34, 65, 14);
			contentPanel.add(labelToyName);
		}
		{
			JLabel labelToyCode = new JLabel("Toy Code");
			labelToyCode.setBounds(10, 11, 65, 14);
			contentPanel.add(labelToyCode);
		}
		
		{
			JLabel labelLowerBound = new JLabel("Lower age");
			labelLowerBound.setBounds(10, 58, 73, 14);
			contentPanel.add(labelLowerBound);
		}
		{
			JLabel labelUpperBound = new JLabel("Upper age");
			labelUpperBound.setBounds(10, 82, 73, 14);
			contentPanel.add(labelUpperBound);
		}
		{
			JLabel labelPrice = new JLabel("Price");
			labelPrice.setBounds(10, 106, 65, 14);
			contentPanel.add(labelPrice);
		}
		{
			JLabel labelCount = new JLabel("Count");
			labelCount.setBounds(10, 130, 65, 14);
			contentPanel.add(labelCount);
		}
		{
			JLabel labelDate = new JLabel("Post date");
			labelDate.setBounds(10, 154, 65, 14);
			contentPanel.add(labelDate);
		}
		{
			JLabel labelProvider = new JLabel("Providers (,)");
			labelProvider.setBounds(10, 177, 65, 14);
			contentPanel.add(labelProvider);
		}
		{
			textFieldToyCode = new JTextField();
			textFieldToyCode.setColumns(10);
			textFieldToyCode.setBounds(85, 8, 129, 20);
			contentPanel.add(textFieldToyCode);
		}
		{
			textFieldToyName = new JTextField();
			textFieldToyName.setColumns(10);
			textFieldToyName.setBounds(85, 31, 129, 20);
			contentPanel.add(textFieldToyName);
		}
		{
			textFieldLowerBound = new JTextField();
			textFieldLowerBound.setColumns(10);
			textFieldLowerBound.setBounds(85, 55, 129, 20);
			contentPanel.add(textFieldLowerBound);
		}
		{
			textFieldUpperBound = new JTextField();
			textFieldUpperBound.setColumns(10);
			textFieldUpperBound.setBounds(85, 79, 129, 20);
			contentPanel.add(textFieldUpperBound);
		}
		{
			textFieldPrice = new JTextField();
			textFieldPrice.setColumns(10);
			textFieldPrice.setBounds(85, 103, 129, 20);
			contentPanel.add(textFieldPrice);
		}
		{
			textFieldCount = new JTextField();
			textFieldCount.setColumns(10);
			textFieldCount.setBounds(85, 127, 129, 20);
			contentPanel.add(textFieldCount);
		}
		{
			textFieldDate = new JTextField();
			textFieldDate.setColumns(10);
			textFieldDate.setBounds(85, 151, 129, 20);
			contentPanel.add(textFieldDate);
		}
		{
			textFieldProvider = new JTextField();
			textFieldProvider.setColumns(10);
			textFieldProvider.setBounds(85, 174, 129, 20);
			contentPanel.add(textFieldProvider);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Next");
				okButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						try {
							Account acc = readData();
							owner.setAccFromDialog(acc);
							setVisible(false);
							dispose();
						} catch (Exception exception) {
							JOptionPane.showMessageDialog(null, exception.getMessage() + "\nTry again...", "Error message", JOptionPane.ERROR_MESSAGE);
						}
					}
				});
				okButton.setActionCommand("Next");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						owner.setAccFromDialog(null);
						setVisible(false);
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
				
	}
	
}
