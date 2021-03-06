package medrex.client.main.admission;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;

import medrex.client.clientManagar.MedrexClientManager;
import medrex.client.constants.Global;
import medrex.client.constants.GlobalSecurity;
import medrex.client.main.scan.FrameScanFormAdd;
import medrex.client.test.ImageSlider;
import medrex.client.test.ThumbnailAdapter;
import medrex.client.utils.SwingUtils;
import medrex.client.utils.controls.FrameToolTip;
import medrex.commons.MedrexMain;
import medrex.commons.enums.GuiModes;
import medrex.commons.enums.SubAppTypes;
import medrex.commons.vo.admission.AdmissionsRecord;
import medrex.commons.vo.admission.SocialServiceInitialAssessmentFormPage1;
import medrex.commons.vo.scan.ScanForm;

import com.jgoodies.looks.plastic.Plastic3DLookAndFeel;
import com.sX.frameFactory.FrameFactory;
import com.sX.swing.JxButton;
import com.sX.swing.JxComboBox;
import com.sX.swing.JxPanel;
import com.sX.swing.JxTable;

public class PanelAdmissionsRecord extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1900334330764361999L;

	private JxComboBox cmbFormType;

	private JxTable table;

	private AdmissionsRecordsJTableNew admissionTable;

	public FrameAdmissionRecordAdd frameAdmissionRecordAdd;
	private ImageSlider imgSlider;
	private String[] formName = new String[] { "Resident Assessment Form",
			"Resident Quarterly Assessment Form",
			"Social Service Initial Assessment Form", "Smokers Assessment Form" };
	private ImageIcon[] formIcon;

	// public FrameScanPageAdd frameScanPageAdd;
	public FrameScanFormAdd frameScanFormAdd;
	public static final Color DEF_COL = new Color(49, 104, 127);
	public static final Color BORDER_COL = new Color(180, 188, 193);

	/**
	 * Create the panel
	 */
	public PanelAdmissionsRecord() {
		super();
		Global.tabName = Global.currentResidentTabs.ASSESSMENTS.Name;
		setLayout(null);
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(720, 496));
		addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getPropertyName() == "DISPLAY_TAB") {
					doUpdate();
				}
			}
		});

		final JxPanel panel = new JxPanel(1f);
		panel.setBackground(Color.WHITE);
		panel.setLayout(null);
		panel.setBounds(0, 20, 720, 466);
		add(panel);

		final JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 52, 680, 186);
		scrollPane.setBackground(Color.WHITE);
		scrollPane.getViewport().setBackground(Color.WHITE);
		scrollPane.setBorder(new LineBorder(DEF_COL, 1, false));
		panel.add(scrollPane);

		table = new JxTable();
		admissionTable = new AdmissionsRecordsJTableNew();
		table.setModel(admissionTable);
		table.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				if (evt.getClickCount() == 2) {
					int id = (Integer) admissionTable.getValueAt(table
							.getSelectedRow(), 5);

					if (id != -1 && id != 0) {
						String formName = admissionTable.getValueAt(table
								.getSelectedRow(), 1)
								+ "";
						Global.currentAdmissionRecordForm = formName;

						Global.currentAdmissionsRecordKey = id;
						Global.viewOrUpdate = "view";
						frameAdmissionRecordAdd = (FrameAdmissionRecordAdd) FrameFactory
								.createWindowOfType(FrameAdmissionRecordAdd.class);
						SwingUtils.center(frameAdmissionRecordAdd);
						frameAdmissionRecordAdd
								.setTitle("View/Update Admissions Record");
						frameAdmissionRecordAdd.setModal(true);
						frameAdmissionRecordAdd.setVisible(true);
					}

					else {
						String file = (String) admissionTable.getValueAt(table
								.getSelectedRow(), 7);
						Global.currentScanFormImageFileName = file;

						frameScanFormAdd = (FrameScanFormAdd) FrameFactory
								.createWindowOfType(FrameScanFormAdd.class);
						SwingUtils.center(frameScanFormAdd);
						frameScanFormAdd.setTitle("View Scan Record");
						frameScanFormAdd.setModal(true);
						frameScanFormAdd.setVisible(true);
					}
					updateAdmissionsRecordJTable();
				} else if (evt.getClickCount() == 1) {
					System.out.println("single click");
				}
			}
		});
		scrollPane.setViewportView(table);

		final JxButton btnAdd = new JxButton(0.4f);
		btnAdd.setBackground(Color.WHITE);
		btnAdd.setForeground(DEF_COL);
		btnAdd.setBorder(new LineBorder(BORDER_COL, 1, false));
		btnAdd.setFont(new Font("", Font.BOLD, 12));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				if (GlobalSecurity
						.isUpdatePriviledge(SubAppTypes.ADMISSION_RECORDS)) {
					Global.currentAdmissionsRecordKey = 0;
					Global.viewOrUpdate = null;
					Global.currentResidentAssessmentFormKey = 0;

					Global.currentAdmissionRecordForm = (String) cmbFormType
							.getSelectedItem();

					frameAdmissionRecordAdd = (FrameAdmissionRecordAdd) FrameFactory
							.createWindowOfType(FrameAdmissionRecordAdd.class);
					SwingUtils.center(frameAdmissionRecordAdd);
					frameAdmissionRecordAdd
							.setTitle("Add New Admission Record");
					frameAdmissionRecordAdd.setModal(true);
					frameAdmissionRecordAdd.setVisible(true);
					updateAdmissionsRecordJTable();
				}
			}
		});
		btnAdd.setText("Add New");
		btnAdd.setBounds(20, 248, 84, 26);
		panel.add(btnAdd);

		final JxButton btnView = new JxButton(0.4f);
		btnView.setBackground(Color.WHITE);
		btnView.setForeground(DEF_COL);
		btnView.setBorder(new LineBorder(BORDER_COL, 1, false));
		btnView.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				try {
					int id = (Integer) admissionTable.getValueAt(table
							.getSelectedRow(), 5);

					if (id != -1 && id != 0) {
						String formName = admissionTable.getValueAt(table
								.getSelectedRow(), 1)
								+ "";
						Global.currentAdmissionRecordForm = formName;

						Global.currentAdmissionsRecordKey = id;
						Global.viewOrUpdate = "view";
						frameAdmissionRecordAdd = (FrameAdmissionRecordAdd) FrameFactory
								.createWindowOfType(FrameAdmissionRecordAdd.class);
						SwingUtils.center(frameAdmissionRecordAdd);
						frameAdmissionRecordAdd
								.setTitle("View Admissions Record");
						frameAdmissionRecordAdd.setModal(true);
						frameAdmissionRecordAdd.setVisible(true);
					}

					else {
						String file = (String) admissionTable.getValueAt(table
								.getSelectedRow(), 7);
						Global.currentScanFormImageFileName = file;

						frameScanFormAdd = (FrameScanFormAdd) FrameFactory
								.createWindowOfType(FrameScanFormAdd.class);
						SwingUtils.center(frameScanFormAdd);
						frameScanFormAdd.setTitle("View Scan Record");
						frameScanFormAdd.setModal(true);
						frameScanFormAdd.setVisible(true);

					}
					updateAdmissionsRecordJTable();
				} catch (Exception ve) {
				}
			}
		});
		btnView.setFont(new Font("", Font.BOLD, 12));
		btnView.setText("View");
		btnView.setBounds(370, 264, 80, 26);
		panel.add(btnView);

		final JxButton btnUpdate = new JxButton(0.4f);
		btnUpdate.setBackground(Color.WHITE);
		btnUpdate.setForeground(DEF_COL);
		btnUpdate.setBorder(new LineBorder(BORDER_COL, 1, false));
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				try {
					String authUser = admissionTable.getValueAt(
							table.getSelectedRow(), 4).toString();
					if (authUser
							.equalsIgnoreCase(Global.currentLoggedInUserName)) {
						int id = (Integer) admissionTable.getValueAt(table
								.getSelectedRow(), 5);
						String formName = admissionTable.getValueAt(table
								.getSelectedRow(), 1)
								+ "";
						Global.currentAdmissionRecordForm = formName;
						if (id != -1) {
							Global.currentAdmissionsRecordKey = id;
							Global.viewOrUpdate = "update";
							frameAdmissionRecordAdd = (FrameAdmissionRecordAdd) FrameFactory
									.createWindowOfType(FrameAdmissionRecordAdd.class);
							SwingUtils.center(frameAdmissionRecordAdd);
							frameAdmissionRecordAdd
									.setTitle("Update Admissions Record");
							frameAdmissionRecordAdd.setModal(true);
							frameAdmissionRecordAdd.setVisible(true);
						}
						updateAdmissionsRecordJTable();
					} else {
						JOptionPane.showMessageDialog(null,
								"You are not authorized user.");
					}

				} catch (Exception ve) {
				}
			}
		});
		btnUpdate.setFont(new Font("", Font.BOLD, 12));
		btnUpdate.setText("Update");
		btnUpdate.setBounds(460, 264, 80, 26);
		panel.add(btnUpdate);

		final JxButton btnDelete = new JxButton(0.4f);
		btnDelete.setBackground(Color.WHITE);
		btnDelete.setForeground(DEF_COL);
		btnDelete.setBorder(new LineBorder(BORDER_COL, 1, false));
		btnDelete.addActionListener(new ActionListener() {
			private Object formType;

			public void actionPerformed(final ActionEvent e) {
				try {
					int id = (Integer) admissionTable.getValueAt(table
							.getSelectedRow(), 5);

					if (id != -1 && id != 0) {
						int formId = (Integer) admissionTable.getValueAt(table
								.getSelectedRow(), 6);
						String formName = admissionTable.getValueAt(table
								.getSelectedRow(), 1)
								+ "";
						if (formId != 0
								&& formName
										.equalsIgnoreCase("Resident Assessment Form")) {
							MedrexClientManager.getInstance()
									.deleteResidentAssessmentFormPage1(formId);
							MedrexClientManager.getInstance()
									.deleteResidentAssessmentFormPage2(formId);
							MedrexClientManager.getInstance()
									.deleteResidentAssessmentFormPage3(formId);
							MedrexClientManager.getInstance()
									.deletePressureSoreAssessment(formId);
							MedrexClientManager.getInstance()
									.deleteResidentAssessmentFormPage4(formId);
							MedrexClientManager.getInstance()
									.deleteResidentAssessmentFormPage5(formId);
							MedrexClientManager.getInstance()
									.deleteResidentAssessmentFormPage6(formId);
							MedrexClientManager.getInstance()
									.deleteResidentAssessmentFormPage7(formId);
							MedrexClientManager.getInstance()
									.deletePainAssessment(formId);
							MedrexClientManager.getInstance()
									.deleteResidentAssessmentFormPage8(formId);
							MedrexClientManager.getInstance()
									.deleteResidentAssessmentFormPage9(formId);
							MedrexClientManager.getInstance()
									.deleteResidentAssessmentFormPage10(formId);
							MedrexClientManager.getInstance()
									.deleteResidentAssessmentFormPage11(formId);
							// MedrexClientManager.getInstance().deleteResidentAssessmentFormPage12(formId);
							MedrexClientManager.getInstance()
									.deleteResidentAssessmentFormPage13(formId);
							// MedrexClientManager.getInstance().deleteResidentAssessmentFormPage14(formId);
							// MedrexClientManager.getInstance().deleteResidentAssessmentFormPage15(formId);
							MedrexClientManager.getInstance()
									.deleteResidentAssessmentFormPage16(formId);
							MedrexClientManager.getInstance()
									.deleteResidentAssessmentFormPage17(formId);
							/*
							 * MedrexClientManager.getInstance().deleteResidentAssessmentFormPage18
							 * (formId); ResidentAssessmentFormPage19 refPage19
							 * =MedrexClientManager.getInstance().
							 * getResidentAssessmentFormPage19(formId);
							 * MedrexClientManager
							 * .getInstance().deleteResidentAssessmentFormPage19
							 * (refPage19.getSerial());
							 * MedrexClientManager.getInstance
							 * ().deleteResidentAssessmentFormPage19Col
							 * (refPage19.getSerial());
							 * MedrexClientManager.getInstance
							 * ().deleteResidentAssessmentFormPage20(formId);
							 */
							// MedrexClientManager.getInstance().deleteResidentAssessmentFormPage21(formId);
							// MedrexClientManager.getInstance().deleteResidentAssessmentFormPage22(formId);
							// if (formId != 0
							// && formType
							// .equals(FormSchedulerConstants.RESIDENT_IMMUNIZATION_FORM))
							// {
							MedrexClientManager.getInstance()
									.deleteResidentImmunizationRecord(formId);
							// }

							MedrexClientManager.getInstance()
									.deleteResidentCarePlanValues(formId,
											Global.currentResidenceKey);
						} else if (formId != 0
								&& formName
										.equalsIgnoreCase("Social Service Initial Assessment Form")) {
							SocialServiceInitialAssessmentFormPage1 refform1 = MedrexClientManager
									.getInstance()
									.getSocialServiceInitialAssessmentFormPage1(
											formId);
							MedrexClientManager
									.getInstance()
									.deleteSocialServiceInitialAssessmentFormPage2(
											refform1.getSerial());
							// MedrexClientManager.getInstance().deleteNutritionSupportAssessmentFormPage1(refform1.getSerial());
							MedrexClientManager
									.getInstance()
									.deleteSocialServiceInitialAssessmentFormPage1(
											formId);
						} else if (formId != 0
								&& formName
										.equalsIgnoreCase("Smokers Assessment Form")) {

							MedrexClientManager.getInstance()
									.deleteAccessingResident3(formId);
							MedrexClientManager.getInstance()
									.deleteAccessingResident2(formId);
							MedrexClientManager.getInstance()
									.deleteAccessingResident1(formId);
						} else {
							MedrexClientManager.getInstance()
									.deleteResidentAssessmentFormPage2(formId);
							MedrexClientManager.getInstance()
									.deleteResidentAssessmentFormPage5(formId);
							MedrexClientManager.getInstance()
									.deleteResidentAssessmentFormPage7(formId);
							MedrexClientManager.getInstance()
									.deleteResidentAssessmentFormPage8(formId);
							MedrexClientManager.getInstance()
									.deleteResidentAssessmentFormPage9(formId);
							MedrexClientManager.getInstance()
									.deleteResidentAssessmentFormPage10(formId);
							MedrexClientManager
									.getInstance()
									.deleteResidentQuarterlyAssessmentFormPage7(
											formId);
							MedrexClientManager
									.getInstance()
									.deleteResidentQuarterlyAssessmentFormPage8(
											formId);
							MedrexClientManager
									.getInstance()
									.deleteResidentQuarterlyAssessmentFormPage9(
											formId);
						}
						MedrexClientManager.getInstance()
								.deleteAdmissionsRecord(id);
						Global.currentAdmissionsRecordKey = 0;
						Global.currentResidentAssessmentFormKey = 0;
						Global.currentResidentAssessmentFormPage2Key = 0;
						Global.currentResidentAssessmentFormPage3Key = 0;
						Global.currentResidentAssessmentFormPage4Key = 0;
						Global.currentResidentAssessmentFormPage5Key = 0;
						Global.currentResidentAssessmentFormPage6Key = 0;
						Global.currentResidentAssessmentFormPage7Key = 0;
						Global.currentResidentAssessmentFormPage8Key = 0;
						Global.currentResidentAssessmentFormPage9Key = 0;
						Global.currentResidentAssessmentFormPage10Key = 0;
						Global.currentResidentAssessmentFormPage11Key = 0;
						Global.currentResidentAssessmentFormPage12Key = 0;
						Global.currentResidentAssessmentFormPage13Key = 0;
						Global.currentResidentAssessmentFormPage14Key = 0;
						Global.currentResidentAssessmentFormPage15Key = 0;
						Global.currentResidentAssessmentFormPage16Key = 0;
						Global.currentResidentAssessmentFormPage17Key = 0;
						Global.currentResidentAssessmentFormPage18Key = 0;
						Global.currentResidentQuarterlyAssessmentFormPage7 = 0;
						Global.currentResidentQuarterlyAssessmentFormPage8 = 0;
						Global.currentResidentQuarterlyAssessmentFormPage9 = 0;
					} else {
						String file = (String) admissionTable.getValueAt(table
								.getSelectedRow(), 7);
						MedrexClientManager.getInstance().deleteScanForm(
								Global.currentResidenceKey,
								Global.currentResidentTabs.ASSESSMENTS.Name,
								file);
						Global.currentScanFormImageFileName = null;
					}
					updateAdmissionsRecordJTable();

				} catch (Exception xe) {

				}

			}
		});
		btnDelete.setFont(new Font("", Font.BOLD, 12));
		btnDelete.setText("Delete");
		btnDelete.setBounds(553, 264, 70, 26);
		panel.add(btnDelete);

		final JxButton btnCancel = new JxButton(0.4f);
		btnCancel.setBackground(Color.WHITE);
		btnCancel.setForeground(DEF_COL);
		btnCancel.setBorder(new LineBorder(BORDER_COL, 1, false));
		btnCancel.setFont(new Font("", Font.BOLD, 12));
		btnCancel.setText("Cancel");
		btnCancel.setBounds(629, 264, 70, 26);
		panel.add(btnCancel);

		final JLabel residentInformationLabel = new JLabel();
		residentInformationLabel.setForeground(DEF_COL);
		residentInformationLabel.setHorizontalAlignment(SwingConstants.CENTER);
		residentInformationLabel.setFont(new Font("Dialog", Font.BOLD, 20));
		residentInformationLabel.setText("ADMISSION RECORDS");
		residentInformationLabel.setBounds(10, 10, 700, 30);
		panel.add(residentInformationLabel);

		cmbFormType = new JxComboBox();
		cmbFormType.setBackground(Color.WHITE);
		cmbFormType.setArc(0.4f);
		cmbFormType.setForeground(DEF_COL);
		cmbFormType.setBorder(new LineBorder(BORDER_COL, 1, false));
		cmbFormType.setBounds(110, 265, 249, 29);
		cmbFormType.setFont(new Font("Dialog", Font.BOLD, 12));
		panel.add(cmbFormType);

		final JxButton scanNewButton = new JxButton(0.4f);
		scanNewButton.setBackground(Color.WHITE);
		scanNewButton.setForeground(DEF_COL);
		scanNewButton.setBorder(new LineBorder(BORDER_COL, 1, false));
		scanNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				Global.currentScanFormImageFileName = null;
				frameScanFormAdd = (FrameScanFormAdd) FrameFactory
						.createWindowOfType(FrameScanFormAdd.class);
				SwingUtils.center(frameScanFormAdd);
				frameScanFormAdd.setTitle("Add New scanning Record");
				frameScanFormAdd.setModal(true);
				frameScanFormAdd.setVisible(true);
				frameScanFormAdd = null;
				updateAdmissionsRecordJTable();
			}
		});
		scanNewButton.setFont(new Font("Dialog", Font.BOLD, 12));
		scanNewButton.setText("Scan New");
		scanNewButton.setBounds(20, 280, 84, 26);
		panel.add(scanNewButton);

		imgSlider = new ImageSlider();
		imgSlider.setBackground(GuiModes.DESIGNER_FORMS
				.getDefaultBackgroundColor()); //
		imgSlider.setBorder(new LineBorder(BORDER_COL, 1, false));
		imgSlider.setNormalSize(128);
		imgSlider.setBounds(20, 312, 680, 144);
		imgSlider.addThumbnailListener(new ThumbnailAdapter() {
			final FrameToolTip frameTooltip = new FrameToolTip("", null, null);

			@Override
			public void thumbnailClicked(final int index, MouseEvent e) {
			}

			@Override
			public void thumbnailMouseOver(final int index, MouseEvent e) {
				if (GlobalSecurity
						.isUpdatePriviledge(SubAppTypes.MEDICATION_TREATMENT)) {
					final ActionListener alistener = new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							Global.currentAdmissionsRecordKey = 0;
							Global.currentAdmissionRecordForm = formName[index];
							// Global.currentResidentAssessmentFormKey = 0;
							Global.viewOrUpdate = null;
							frameAdmissionRecordAdd = (FrameAdmissionRecordAdd) FrameFactory
									.createWindowOfType(FrameAdmissionRecordAdd.class);
							SwingUtils.center(frameAdmissionRecordAdd);
							frameAdmissionRecordAdd
									.setTitle("Add New Admission Record");
							frameAdmissionRecordAdd.setModal(true);
							frameAdmissionRecordAdd.setVisible(true);
							updateAdmissionsRecordJTable();
						}
					};

					frameTooltip.setHeading(formName[index]);
					frameTooltip.setPreviewImage(formIcon[index]);
					frameTooltip.setActionListener(alistener);
					frameTooltip.setModal(false);

					Point pt = imgSlider.getLocationOnScreen();
					frameTooltip.setLocation(e.getXOnScreen()
							- (frameTooltip.getWidth() / 2), pt.y
							- frameTooltip.getHeight());

					frameTooltip.setVisible(true);
				}
			}

			@Override
			public void thumbnailMouseOut(int index, MouseEvent e) {
				if (!frameTooltip.hasFocus() && !frameTooltip.isFocusOwner()) {
					frameTooltip.setVisible(false);
				}
			}
		});
		panel.add(imgSlider);
		fillCombos();

	}

	public void openNewForm(String formName) {
		Global.currentAdmissionsRecordKey = 0;

		Global.currentResidentAssessmentFormKey = 0;

		Global.currentAdmissionRecordForm = formName;

		frameAdmissionRecordAdd = (FrameAdmissionRecordAdd) FrameFactory
				.createWindowOfType(FrameAdmissionRecordAdd.class);
		SwingUtils.center(frameAdmissionRecordAdd);
		frameAdmissionRecordAdd.setTitle("Add New Admission Record");
		frameAdmissionRecordAdd.setModal(true);
		frameAdmissionRecordAdd.setVisible(true);
		updateAdmissionsRecordJTable();
	}

	protected void doUpdate() {
		updateAdmissionsRecordJTable();
	}

	public void fillCombos() {
		String[] imgPath = new String[] { "ResidentAssessmentForm.png",
				"ResidentQuarterlyAssessmentForm.png",
				"SocialServiceInitialAssessment.png", "smokersAssessment.png" };

		cmbFormType.setModel(new javax.swing.DefaultComboBoxModel(formName));

		formIcon = new ImageIcon[imgPath.length];
		for (int i = 0; i < imgPath.length; i++) {
			BufferedImage img = new BufferedImage(100, 100,
					BufferedImage.TYPE_INT_RGB);
			try {
				img = ImageIO.read(new File(
						"img/formThumbnails/Admission Details/" + imgPath[i]));
				formIcon[i] = new ImageIcon(img);
				imgSlider.insertElement(img, formName[i]);

			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}

	}

	public void updateAdmissionsRecordJTable() {
		List residentAdmissionRecords = new ArrayList();
		List<ScanForm> scanRecords = null;
		if (Global.currentResidenceKey != 0) {
			try {
				residentAdmissionRecords = MedrexClientManager.getInstance()
						.getAdmissionsRecords(Global.currentResidenceKey);
				scanRecords = MedrexClientManager.getInstance().doLoadScans(
						Global.currentResidenceKey,
						Global.currentResidentTabs.ASSESSMENTS.Name);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (scanRecords != null) {
			residentAdmissionRecords.addAll(scanRecords);
		}
		admissionTable.setNewList(residentAdmissionRecords);
		admissionTable.fireTableStructureChanged();
		table.updateUI();
		table.repaint();
	}

	public static void main(String[] a) {
		Global.currentResidenceKey = 11;
		(new MedrexMain()).generalInitializations();
		try {
			UIManager.setLookAndFeel(new Plastic3DLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		PanelAdmissionsRecord p = new PanelAdmissionsRecord();
		JFrame frame = SwingUtils.wrapInFrame(p);
		frame.getContentPane().setBackground(Color.RED);
		p.updateAdmissionsRecordJTable();
		frame.setVisible(true);
	}
}

class AdmissionsRecordsJTableNew extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8911286386584352987L;

	private String[] columnNames = { "Date Created", "Form Type",
			"Last Modified", "Status", "User" };

	public List admissionsRecords;

	int n = 0;

	AdmissionsRecordsJTableNew(List admissionRecords) {
		admissionsRecords = admissionRecords;
	}

	AdmissionsRecordsJTableNew() {
		admissionsRecords = new ArrayList();
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		return (admissionsRecords.size());
	}

	public void setRowCount(int count) {
		n = n + count;
	}

	public void setNewList(List admissionRecords) {
		admissionsRecords = admissionRecords;
	}

	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

	public Object getValueAt(int row, int col) {
		try {

			Object o = admissionsRecords.get(row);
			SimpleDateFormat df = new SimpleDateFormat();
			df.applyPattern("MM / dd / yyyy  hh:mm a");
			if (o instanceof AdmissionsRecord) {
				AdmissionsRecord rc = (AdmissionsRecord) o;

				if (col == 0) {
					return (df.format(rc.getDateCreated())); // + "");
				}
				if (col == 1) {
					return (rc.getFormType());
				}
				if (col == 2) {
					return (df.format(rc.getLastModified())); // + "");
				}
				if (col == 3) {
					return (rc.getStatus()); // + "");
				}
				if (col == 4) {
					return (rc.getUser());
				}
				if (col == 5) {
					return (rc.getSerial()); // + "");
				}
				if (col == 6) {
					return (rc.getFormId());
				}
				return ("");
			} else {
				ScanForm rc = (ScanForm) o;
				if (col == 0) {
					return df.format(rc.getDateCreated()) + "";
				}
				if (col == 1) {
					return rc.getScanFormType();
				}
				if (col == 2) {
					return ("");
				}
				if (col == 3) {
					return ("Completed");
				}
				if (col == 4) {
					return "";
				}
				if (col == 5) {
					return (rc.getSerial());
				}
				if (col == 6) {
					return rc.getFileName();
				}
				if (col == 7) {
					return rc.getFileName();
				}

				return ("");

			}
		}

		catch (Exception e) {
			return ("");
		}

	}
}
