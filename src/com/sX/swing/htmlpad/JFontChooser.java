package com.sX.swing.htmlpad;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JFontChooser.java
 *
 * Created on Apr 9, 2009, 10:02:37 PM
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JColorChooser;

/**
 * 
 * @author user
 */
public class JFontChooser extends javax.swing.JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5593549044811822474L;
	private JColorChooser colorChooser;

	/** Creates new form JFontChooser */

	public JFontChooser(java.awt.Frame parent, boolean modal, Font font) {
		this(parent, modal);
		fontList.setSelectedValue(font.getName(), true);
		lstFontSize.setSelectedValue(String.valueOf(font.getSize()), true);
		switch (font.getStyle()) {
		case Font.PLAIN:
			lstFontStyle.setSelectedValue("Regular", true);
			break;
		case Font.BOLD:
			lstFontStyle.setSelectedValue("Bold", true);
			break;
		case Font.ITALIC:
			lstFontStyle.setSelectedValue("Italic", true);
			break;
		case Font.BOLD | Font.ITALIC:
			lstFontStyle.setSelectedValue("Bold Italic", true);
			break;
		}

	}

	public JFontChooser(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				selectedFont = null;
				choice = CANCEL_OPTION;
				dispose();
			}
		});
		setSize(413, 575);
		if (parent != null) {
			setLocation(parent.getX() + (parent.getWidth() - getWidth()) / 2,
					parent.getY() + (parent.getHeight() - getHeight()) / 2);
		} else {
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			setLocation(0 + (screenSize.width - getWidth()) / 2,
					0 + (screenSize.height - getHeight()) / 2);
		}
		GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		availableFonts = graphicsEnvironment.getAvailableFontFamilyNames();
		fontList.setListData(availableFonts);
		fontList.setSelectedIndex(0);
		preview();

		colorChooser = new JColorChooser();
		colorChooser.setBounds(20, 176, 370, 326);
		txtPreview = colorChooser.getPreviewPanel();
		getContentPane().add(colorChooser);
	}

	public int showFontDialog() {

		setVisible(true);
		return choice;

	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	// @SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		// sclPreview = new javax.swing.JScrollPane();
		// txtPreview = new javax.swing.JTextArea();
		sclFontList = new javax.swing.JScrollPane();
		fontList = new javax.swing.JList();
		lblAvailableFonts = new javax.swing.JLabel();
		sclFontSize = new javax.swing.JScrollPane();
		lstFontSize = new javax.swing.JList();
		sclFontStyle = new javax.swing.JScrollPane();
		lstFontStyle = new javax.swing.JList();
		btnOK = new javax.swing.JButton();
		btnCancel = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		setTitle("Font");
		setBounds(new java.awt.Rectangle(0, 0, 413, 575));
		getContentPane().setLayout(null);

		// txtPreview.setColumns(20);
		// txtPreview.setRows(5);
		// sclPreview.setViewportView(txtPreview);

		// getContentPane().add(sclPreview);
		// sclPreview.setBounds(20, 176, 370, 74);

		fontList.setSelectedIndex(0);
		fontList
				.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
					public void valueChanged(
							javax.swing.event.ListSelectionEvent evt) {
						fontListValueChanged(evt);
					}
				});
		sclFontList.setViewportView(fontList);

		getContentPane().add(sclFontList);
		sclFontList.setBounds(20, 40, 220, 130);

		lblAvailableFonts.setText("Available Fonts");
		getContentPane().add(lblAvailableFonts);
		lblAvailableFonts.setBounds(80, 20, 110, 14);

		lstFontSize.setModel(new javax.swing.AbstractListModel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 2010342890295658828L;
			String[] strings = { "8", "9", "10", "11", "12", "14", "16", "18",
					"20", "22", "24", "26", "28", "36", "48", "72" };

			public int getSize() {
				return strings.length;
			}

			public Object getElementAt(int i) {
				return strings[i];
			}
		});
		lstFontSize.setSelectedIndex(5);
		lstFontSize
				.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
					public void valueChanged(
							javax.swing.event.ListSelectionEvent evt) {
						lstFontSizeValueChanged(evt);
					}
				});
		sclFontSize.setViewportView(lstFontSize);

		getContentPane().add(sclFontSize);
		sclFontSize.setBounds(330, 40, 60, 130);

		lstFontStyle.setModel(new javax.swing.AbstractListModel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 2644078549366183244L;
			String[] strings = { "Regular", "Italic", "Bold", "Bold Italic" };

			public int getSize() {
				return strings.length;
			}

			public Object getElementAt(int i) {
				return strings[i];
			}
		});
		lstFontStyle.setSelectedIndex(0);
		lstFontStyle
				.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
					public void valueChanged(
							javax.swing.event.ListSelectionEvent evt) {
						lstFontStyleValueChanged(evt);
					}
				});
		sclFontStyle.setViewportView(lstFontStyle);

		getContentPane().add(sclFontStyle);
		sclFontStyle.setBounds(250, 40, 70, 130);

		btnOK.setText("OK");
		btnOK.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnOKActionPerformed(evt);
			}
		});
		getContentPane().add(btnOK);
		btnOK.setBounds(234, 508, 70, 23);

		btnCancel.setText("Cancel");
		btnCancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnCancelActionPerformed(evt);
			}
		});
		getContentPane().add(btnCancel);
		btnCancel.setBounds(310, 508, 80, 23);

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void fontListValueChanged(javax.swing.event.ListSelectionEvent evt) {// GEN-FIRST:event_fontListValueChanged

		preview();
	}// GEN-LAST:event_fontListValueChanged

	private void preview() {
		setSelectedFont();
		if (colorChooser != null) {
			colorChooser.setFont(getSelectedFont());
		}
		// txtPreview.setText(fontList.getSelectedValue().toString());
	}

	private void setSelectedFont() {
		String fontName = fontList.getSelectedValue().toString();

		int fontStyle = lstFontStyle.getSelectedIndex();
		switch (fontStyle) {
		case 0:
			fontStyle = Font.PLAIN;
			break;
		case 1:
			fontStyle = Font.ITALIC;
			break;
		case 2:
			fontStyle = Font.BOLD;
			break;
		case 3:
			fontStyle = Font.BOLD | Font.ITALIC;
			break;
		}

		int fontSize = Integer.parseInt(lstFontSize.getSelectedValue()
				.toString());

		selectedFont = new Font(fontName, fontStyle, fontSize);
	}

	private void btnOKActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnOKActionPerformed

		choice = OK_OPTION;
		dispose();
	}// GEN-LAST:event_btnOKActionPerformed

	public Font getSelectedFont() {
		return selectedFont;
	}

	private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnCancelActionPerformed
		choice = CANCEL_OPTION;
		selectedFont = null;
		dispose();
	}// GEN-LAST:event_btnCancelActionPerformed

	private void lstFontStyleValueChanged(
			javax.swing.event.ListSelectionEvent evt) {// GEN-FIRST:event_lstFontStyleValueChanged
		preview();
	}// GEN-LAST:event_lstFontStyleValueChanged

	private void lstFontSizeValueChanged(
			javax.swing.event.ListSelectionEvent evt) {// GEN-FIRST:event_lstFontSizeValueChanged
		preview();
	}// GEN-LAST:event_lstFontSizeValueChanged

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				JFontChooser dialog = new JFontChooser(
						new javax.swing.JFrame(), true);
				dialog.addWindowListener(new java.awt.event.WindowAdapter() {
					@Override
					public void windowClosing(java.awt.event.WindowEvent e) {
						System.exit(0);
					}
				});
				dialog.showFontDialog();
			}
		});
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton btnCancel;
	private javax.swing.JButton btnOK;
	private javax.swing.JList fontList;
	private javax.swing.JLabel lblAvailableFonts;
	private javax.swing.JList lstFontSize;
	private javax.swing.JList lstFontStyle;
	private javax.swing.JScrollPane sclFontList;
	private javax.swing.JScrollPane sclFontSize;
	private javax.swing.JScrollPane sclFontStyle;
	// private javax.swing.JScrollPane sclPreview;
	private javax.swing.JComponent txtPreview;
	// End of variables declaration//GEN-END:variables

	private String[] availableFonts;
	private static final int OK_OPTION = 1;
	private static final int CANCEL_OPTION = 0;
	private int choice = 0;
	private Font selectedFont;

	public Color getSelectedColor() {
		return colorChooser.getColor();
	}
}
