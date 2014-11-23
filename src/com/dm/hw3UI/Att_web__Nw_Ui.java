package com.dm.hw3UI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;



import javax.swing.JCheckBox;

import com.dm.hw3.MCLAlgorithm;
import com.dm.hw3.OutputObject;

public class Att_web__Nw_Ui extends JFrame {

	private JPanel contentPane;
	private JTextField inflationP;
	private JTextField textField;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public Att_web__Nw_Ui() {
		this.setTitle("Clustering Analysis for AT & T Web Network");
		setFont(new Font("Dialog", Font.PLAIN, 14));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 658, 436);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		final JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel(
				"Run Custering Analysis (MCL)  for AT & T Web Network :");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(52, 11, 470, 30);
		panel.add(lblNewLabel);

		JLabel lblType = new JLabel("Inflation Parameter:");
		lblType.setHorizontalAlignment(SwingConstants.LEFT);
		lblType.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblType.setBounds(52, 39, 130, 20);
		panel.add(lblType);

		inflationP = new JTextField();
		inflationP.setText("1.35");
		inflationP.setBounds(178, 41, 96, 20);
		panel.add(inflationP);
		inflationP.setColumns(10);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(40, 124, 556, 138);
		panel.add(scrollPane_1);

		final JTextArea txtrAttractersMerging = new JTextArea();
		txtrAttractersMerging.setEditable(false);
		txtrAttractersMerging.setText("Output Window: Attracters Found , Merging sequence (if any)");
		scrollPane_1.setViewportView(txtrAttractersMerging);

		JLabel lblMclExecutionTime = new JLabel("MCL Execution Time :");
		lblMclExecutionTime.setHorizontalAlignment(SwingConstants.LEFT);
		lblMclExecutionTime.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblMclExecutionTime.setBounds(132, 273, 133, 20);
		panel.add(lblMclExecutionTime);

		textField = new JTextField();
		textField.setEditable(false);
		textField.setColumns(10);
		textField.setBounds(275, 275, 130, 20);
		panel.add(textField);

		JButton btnExecute = new JButton("Execute");
		btnExecute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				double inflationParam = Double.parseDouble(inflationP.getText());

				try {
					long current = System.nanoTime();
					MCLAlgorithm mcl = new MCLAlgorithm();
					OutputObject oo = mcl.runMCLAlgorithm("inputFiles"+File.separator+"attweb_net",inflationParam);
					textField.setText(Double.toString(
							(System.nanoTime() - current) / 1000000000.0)
							.substring(0, 4)
							+ " Sec");
					txtrAttractersMerging.setText(oo.outputStr);
				} catch (NumberFormatException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 

			}

		});

		btnExecute.setBounds(287, 40, 89, 23);
		panel.add(btnExecute);
		
		JLabel lblTheClustersWill = new JLabel("Inflation Parameter can be changed. The Clusters will be merged based on attracter sets. ");
		lblTheClustersWill.setVerticalAlignment(SwingConstants.TOP);
		lblTheClustersWill.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTheClustersWill.setBounds(52, 70, 556, 20);
		panel.add(lblTheClustersWill);
		
		JLabel lblBestResultWas = new JLabel("Best Result was observed for Inflation Parameter = 1.35");
		lblBestResultWas.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblBestResultWas.setBounds(52, 88, 470, 30);
		panel.add(lblBestResultWas);
		
		JLabel lblThe = new JLabel("The .clu file can be found at ProjectFolder\\pajekCluFiles\\attweb_net.clu");
		lblThe.setVerticalAlignment(SwingConstants.TOP);
		lblThe.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblThe.setBounds(52, 356, 556, 20);
		panel.add(lblThe);
		
		JLabel label_1 = new JLabel("The .net file can be found at ProjectFolder\\pajekNetFiles\\attweb_net.net");
		label_1.setVerticalAlignment(SwingConstants.TOP);
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label_1.setBounds(52, 335, 556, 20);
		panel.add(label_1);
		
		JLabel lblForVisul = new JLabel("Required files for Pajek Visualization :");
		lblForVisul.setVerticalAlignment(SwingConstants.TOP);
		lblForVisul.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblForVisul.setBounds(52, 309, 556, 20);
		panel.add(lblForVisul);

	}

	public static void fillCombo(JComboBox temp, String variable) {

	}
}
