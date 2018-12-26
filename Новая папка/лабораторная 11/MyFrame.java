package task_13;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MyFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private final ButtonGroup buttonGroupKeyChoose = new ButtonGroup();
	
	private Account accFromDialog = null;
	void setAccFromDialog (Account acc) {
		accFromDialog = acc;
	}
	
	private JRadioButton rdbtnDate, rdbtnBounds, rdbtnProvider;
	private String radBut () {
		String rb = "";
		if ( rdbtnBounds.isSelected() )
			rb = "b";
		if ( rdbtnDate.isSelected() )
			rb = "d";
		if ( rdbtnProvider.isSelected() )
			rb = "p";
		return rb;
	}
	private String[] callDataDialogs () {
		String[] args = new String[] { "", "" };
		args[0] = radBut();
		if ( args[0].length() == 0 )
			return args;
		switch (args[0].charAt(0)) {
		case 'b':
			String res1 = JOptionPane.showInputDialog("Enter bounds (example: 03-14):");
			if ( Account.validBounds(res1) )
				args[1] = res1;
			else
				JOptionPane.showMessageDialog(null, "Invalid argument.", "Error", JOptionPane.ERROR_MESSAGE);
			break;
		case 'd':
			String res2 = JOptionPane.showInputDialog("Enter date (example: 12.10.2015):");
			if ( Account.validDate(res2) )
				args[1] = res2;
			else
				JOptionPane.showMessageDialog(null, "Invalid argument.", "Error", JOptionPane.ERROR_MESSAGE);
			break;
		case 'p':
			args[1] = JOptionPane.showInputDialog("Enter providers (delimiter: \',\'):");
			if ( args[1].length() == 0 )
				JOptionPane.showMessageDialog(null, "Invalid argument.", "Error", JOptionPane.ERROR_MESSAGE);
			break;
		}
		return args;
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyFrame frame = new MyFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MyFrame() {
		Accounts.deleteFile();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 415);
		
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);		
		JMenuItem mntmOpen = new JMenuItem("Open...");
		mntmOpen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				if ( jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION ) {
					String fn = jfc.getSelectedFile().getAbsolutePath();
					try {
						Accounts.deleteFile();
						Accounts.appendFile(fn, false);
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		mnFile.add(mntmOpen);		
		JMenuItem mntmSaveAs = new JMenuItem("Save as...");
		mntmSaveAs.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				if ( jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION ) {
					try {
						String fn = jfc.getSelectedFile().getAbsolutePath();
						jfc.getSelectedFile().delete();
						new File(fn).createNewFile();
						Accounts.saveTo(fn);
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		mnFile.add(mntmSaveAs);		
		JSeparator separator = new JSeparator();
		mnFile.add(separator);		
		JMenuItem mntmClose = new JMenuItem("Close");
		mntmClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int choice = JOptionPane.showConfirmDialog(null, "Do you want to close application?", "Close", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if ( choice == JOptionPane.YES_OPTION ) {
					setVisible(false);
					dispose();
				}
			}
		});
		mnFile.add(mntmClose);		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);		
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Lab 11.\n11 group, FAMCS.\nRustem.", "About", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		mnHelp.add(mntmAbout);
		
				
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		getContentPane().setLayout(null);
		contentPane.setLayout(null);
		
		JPanel panelChooseKey = new JPanel();
		panelChooseKey.setBorder(new TitledBorder(null, "Choose key:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelChooseKey.setBounds(10, 203, 107, 97);
		contentPane.add(panelChooseKey);
		panelChooseKey.setLayout(null);
		
		rdbtnDate = new JRadioButton("Date");
		buttonGroupKeyChoose.add(rdbtnDate);
		rdbtnDate.setBounds(6, 16, 77, 23);
		panelChooseKey.add(rdbtnDate);		
		rdbtnProvider = new JRadioButton("Provider");
		buttonGroupKeyChoose.add(rdbtnProvider);
		rdbtnProvider.setBounds(6, 42, 77, 23);
		panelChooseKey.add(rdbtnProvider);		
		rdbtnBounds = new JRadioButton("Bounds");
		buttonGroupKeyChoose.add(rdbtnBounds);
		rdbtnBounds.setBounds(6, 68, 77, 23);
		panelChooseKey.add(rdbtnBounds);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(153, 14, 409, 293);
		contentPane.add(scrollPane);				
		JTextArea txtPane = new JTextArea();
		txtPane.setDisabledTextColor(Color.BLACK);
		txtPane.setEnabled(false);
		txtPane.setForeground(new Color(0, 0, 0));
		txtPane.setFont(new Font("Monospaced", Font.PLAIN, 12));
		scrollPane.setViewportView(txtPane);
		
		JButton btnPrint = new JButton("Print");
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String txt = Accounts.printFile();
					txtPane.setText(txt);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnPrint.setBounds(10, 56, 120, 23);
		contentPane.add(btnPrint);
		
		JButton btnPrintSorted = new JButton("Print Sorted");
		btnPrintSorted.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String[] args = new String[] { radBut() };
					String txt = Accounts.printFile(args, false);
					txtPane.setText(txt);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Error! " + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnPrintSorted.setBounds(10, 78, 120, 23);
		contentPane.add(btnPrintSorted);
		
		JButton btnFind = new JButton("Find by Key");
		btnFind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String[] args = callDataDialogs();
					String result = Accounts.findByKey(args);
					JOptionPane.showMessageDialog(null, result, "Find by key", JOptionPane.INFORMATION_MESSAGE);
				}
				catch (NullPointerException npe) {}
				catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Error! " + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnFind.setBounds(10, 124, 120, 23);
		contentPane.add(btnFind);
		
		JButton btnPrintRevSorted = new JButton("Reverse Sorted");
		btnPrintRevSorted.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String[] args = new String[] { radBut() };
					String txt = Accounts.printFile(args, true);
					txtPane.setText(txt);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Error! " + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnPrintRevSorted.setBounds(10, 101, 120, 23);
		contentPane.add(btnPrintRevSorted);
		
		JButton btnFindMore = new JButton("Find > Key");
		btnFindMore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String[] args = callDataDialogs();
					String txt = Accounts.findByKey(args, new KeyCompReverse());
					txtPane.setText(txt);
				}
				catch (NullPointerException npe) {}
				catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Error! " + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnFindMore.setBounds(10, 147, 120, 23);
		contentPane.add(btnFindMore);
		
		JButton btnFindKeyLess = new JButton("Find < Key");
		btnFindKeyLess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String[] args = callDataDialogs();
					String txt = Accounts.findByKey(args, new KeyComp());
					txtPane.setText(txt);
				}
				catch (NullPointerException npe) {}
				catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Error! " + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnFindKeyLess.setBounds(10, 170, 120, 23);
		contentPane.add(btnFindKeyLess);
		
		JButton btnDeleteByKey = new JButton("Delete By Key");
		btnDeleteByKey.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String[] args = callDataDialogs();
					Accounts.deleteFile(args);
				}
				catch (NullPointerException npe) {}
				catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Error! " + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnDeleteByKey.setBounds(10, 33, 120, 23);
		contentPane.add(btnDeleteByKey);
		
		JButton btnAddAccount = new JButton("Add Account");
		btnAddAccount.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AccountDialog dial = new AccountDialog(MyFrame.this);
				dial.setVisible(true);
				while ( dial.isShowing() ) {}
				try {
					Accounts.appendFile(accFromDialog, false);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnAddAccount.setBounds(10, 11, 120, 23);
		contentPane.add(btnAddAccount);
				
	}
}
