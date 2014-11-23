package com.dm.hw3UI;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;


import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.table.DefaultTableModel;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingConstants;
import java.awt.Color;

public class MCL {

	private JFrame frmBullseyeDw;
	static Connection con = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MCL window = new MCL();
					window.frmBullseyeDw.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MCL() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frmBullseyeDw = new JFrame();
		frmBullseyeDw.setTitle("BullsEye MCL Algorithm");
		frmBullseyeDw.setBounds(100, 100, 658, 436);
		frmBullseyeDw.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel panel = new JPanel();
		frmBullseyeDw.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 192, 42);
		panel.add(menuBar);

		JMenu mnQueries = new JMenu("CLICK HERE TO ANALYZE");
		mnQueries.setBackground(Color.WHITE);

		menuBar.add(mnQueries);
		JMenuItem mnQuery1 = new JMenuItem("AT & T Web Network");

		mnQuery1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Yea_Met_Nw_Ui qu1 = new Yea_Met_Nw_Ui();
				qu1.setVisible(true);

			}
		});

		mnQueries.add(mnQuery1);

		JMenuItem mnQuery2 = new JMenuItem("Physics Collaboration Network");
		mnQuery2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Att_web__Nw_Ui qu2 = new Att_web__Nw_Ui();
				qu2.setVisible(true);

			}
		});

		mnQueries.add(mnQuery2);
		
		
		JMenuItem mnQuery3 = new JMenuItem("Yeast Metabolic Network");
		mnQuery3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Phy_Col_Nw_Ui qu3 = new Phy_Col_Nw_Ui();
				qu3.setVisible(true);

			}
		});

		mnQueries.add(mnQuery3);

		JLabel lblNewLabel = new JLabel("Data Minning Homework 3" );
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Monotype Corsiva", Font.PLAIN, 25));
		lblNewLabel.setBounds(150, 146, 375, 42);
		panel.add(lblNewLabel);

	}
}
