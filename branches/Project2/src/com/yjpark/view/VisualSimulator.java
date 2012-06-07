package com.yjpark.view;

import java.io.*;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.*;

import com.cloudgarden.resource.SWTResourceManager;

import com.yjpark.assembler.*;
import com.yjpark.resource.*;

/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class VisualSimulator extends org.eclipse.swt.widgets.Composite {

	private Menu menu1;
	private Group headerGroup;
	private Text programNameText;
	private Label lRegisterLabel;
	private Label xRegisterLabel;
	private Label addressFirstInstructionLabel;
	private Group endGroup;
	private Text startAddressText;
	private Text lengthProgramText;
	private Label programNameLabel;
	private Text useDeviceText;
	private Button assembleButton;
	private StyledText logStyledText;
	private Button exitButton;
	private Button allRunButton;
	private Button stepRunButton;
	private Label useDeviceLabel;
	private List instructionsList;
	private Text targetAddressText;
	private Text startAddressMemoryText;
	private Label instructionsLabel;
	private Label targetAddressLabel;
	private Label startAddressMemotyLabel;
	private Text tRegisterHexText;
	private Text sRegisterHexText;
	private Text bRegisterHexText;
	private Text fRegisterText;
	private Text tRegisterDecText;
	private Text sRegisterDecText;
	private Text bRegisterDecText;
	private Label fRegisterLabel;
	private Label tRegisterLabel;
	private Label sRegisterLabel;
	private Label bRegisterLabel;
	private Label xeHexLabel;
	private Label xeDecLabel;
	private Label logLabel;
	private Group xeRegisterGroup;
	private Label hexLabel;
	private Label decLabel;
	private Text aRegisterHexText;
	private Text xRegisterHexText;
	private Text lRegisterHexText;
	private Text pcRegisterHexText;
	private Text swRegisterText;
	private Text pcRegisterDecText;
	private Label swRegisterLabel;
	private Label pcRegisterLabel;
	private Text aRegisterDecText;
	private Label aRegisterLabel;
	private Group registerGroup;
	private Text lRegisterDecText;
	private Text xRegisterDecText;
	private Text addressFirstInstructionText;
	private Label startAddressLabel;
	private Label lengthProgramLabel;
	private Button fileOpenButton;
	private MenuItem aboutMenuItem;
	private Menu helpMenu;
	private MenuItem helpMenuItem;
	private Text fileNameText;
	private Label fileNameLabel;
	private MenuItem exitMenuItem;
	private MenuItem saveAsFileMenuItem;
	private MenuItem saveFileMenuItem;
	private MenuItem newFileMenuItem;
	private MenuItem openFileMenuItem;
	private Menu fileMenu;
	private MenuItem fileMenuItem;

	
	// background color for non-editable component
	private Color whiteBg = Display.getDefault().getSystemColor(SWT.COLOR_WHITE);
	
	// get current file path
	private File f = new File(".");
	
	// for registers
	private Vector<Register> registers = new Vector<Register>();
	
	// for instruction
	int targetAddress = 0;
	private Vector<String> instructionVector = new Vector<String>();
	
	// for run
	int runStatus = 0;
	
	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}
	
	public VisualSimulator(Composite parent, int style) {
		super(parent, style);
		initGUI();
	
		// call Init Manager
		InitManager initMgr = new InitManager();
		
		initMgr.init();
		this.registers = initMgr.getRegisters();

	}
	
	/**
	* Initializes the GUI.
	*/
	private void initGUI() {
		try {
			this.setSize(530, 575);
			this.setLayout(null);
			{
				fileNameLabel = new Label(this, SWT.NONE);
				fileNameLabel.setText("FileName :");
				fileNameLabel.setBounds(12, 7, 58, 20);
			}
			{
				fileNameText = new Text(this, SWT.BORDER);
				fileNameText.setBounds(75, 7, 118, 20);

				// background
				fileNameText.setBackground(whiteBg);
			}
			{
				fileOpenButton = new Button(this, SWT.PUSH | SWT.CENTER);
				fileOpenButton.setText("open");
				fileOpenButton.setBounds(196, 4, 53, 24);
				fileOpenButton.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						fileOpenButtonWidgetSelected(evt);
					}
				});
			}
			{
				assembleButton = new Button(this, SWT.PUSH | SWT.CENTER);
				assembleButton.setText("assemble");
				assembleButton.setBounds(264, 4, 68, 24);
				assembleButton.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						assembleButtonWidgetSelected(evt);
					}
				});
			}
			{
				headerGroup = new Group(this, SWT.NONE);
				headerGroup.setLayout(null);
				headerGroup.setText("H (Header Record)");
				headerGroup.setBounds(12, 38, 238, 104);
				{
					programNameLabel = new Label(headerGroup, SWT.NONE);
					programNameLabel.setText("Program Name :");
					programNameLabel.setBounds(8, 20, 89, 20);
				}
				{
					startAddressLabel = new Label(headerGroup, SWT.NONE);
					startAddressLabel.setText("Start Address of \n  Object Program :");
					startAddressLabel.setBounds(8, 40, 100, 30);
				}
				{
					lengthProgramLabel = new Label(headerGroup, SWT.NONE);
					lengthProgramLabel.setText("Length of Program :");
					lengthProgramLabel.setBounds(8, 75, 109, 20);
				}
				{
					programNameText = new Text(headerGroup, SWT.READ_ONLY | SWT.BORDER);
					programNameText.setBounds(139, 20, 83, 20);
					
					// background
					programNameText.setBackground(whiteBg);
				}
				{
					startAddressText = new Text(headerGroup, SWT.READ_ONLY | SWT.BORDER);
					startAddressText.setBounds(139, 47, 83, 20);
					
					// background
					startAddressText.setBackground(whiteBg);
				}
				{
					lengthProgramText = new Text(headerGroup, SWT.READ_ONLY | SWT.BORDER);
					lengthProgramText.setBounds(139, 75, 83, 20);
					
					// background
					lengthProgramText.setBackground(whiteBg);
				}
			}
			{
				endGroup = new Group(this, SWT.NONE);
				endGroup.setText("E (End Record)");
				endGroup.setBounds(264, 38, 254, 61);
				endGroup.setLayout(null);
				{
					addressFirstInstructionLabel = new Label(endGroup, SWT.NONE);
					addressFirstInstructionLabel.setText("Address of First Instruction \nin Object Program :");
					addressFirstInstructionLabel.setBounds(8, 20, 151, 34);
				}
				{
					addressFirstInstructionText = new Text(endGroup, SWT.READ_ONLY | SWT.BORDER);
					addressFirstInstructionText.setBounds(162, 34, 83, 20);
					
					// background
					addressFirstInstructionText.setBackground(whiteBg);
				}
			}
			{
				registerGroup = new Group(this, SWT.NONE);
				registerGroup.setText("Register");
				registerGroup.setBounds(12, 151, 238, 141);
				registerGroup.setLayout(null);
				{
					decLabel = new Label(registerGroup, SWT.NONE);
					decLabel.setText("Dec");
					decLabel.setBounds(51, 15, 60, 20);
				}
				{
					hexLabel = new Label(registerGroup, SWT.NONE);
					hexLabel.setText("Hex");
					hexLabel.setBounds(141, 14, 60, 20);
				}
				{
					aRegisterLabel = new Label(registerGroup, SWT.NONE);
					aRegisterLabel.setText("A (#0)");
					aRegisterLabel.setBounds(8, 35, 41, 20);
				}
				{
					xRegisterLabel = new Label(registerGroup, SWT.NONE);
					xRegisterLabel.setText("X (#1)");
					xRegisterLabel.setBounds(8, 55, 41, 20);
				}
				{
					lRegisterLabel = new Label(registerGroup, SWT.NONE);
					lRegisterLabel.setText("L (#2)");
					lRegisterLabel.setBounds(8, 75, 41, 20);
				}
				{
					pcRegisterLabel = new Label(registerGroup, SWT.NONE);
					pcRegisterLabel.setText("PC(#8)");
					pcRegisterLabel.setBounds(8, 95, 41, 20);
				}
				{
					swRegisterLabel = new Label(registerGroup, SWT.NONE);
					swRegisterLabel.setText("SW(#9)");
					swRegisterLabel.setBounds(8, 115, 41, 20);
				}
				{
					aRegisterDecText = new Text(registerGroup, SWT.READ_ONLY | SWT.BORDER);
					aRegisterDecText.setBounds(51, 35, 85, 20);
					
					// background
					aRegisterDecText.setBackground(whiteBg);
				}
				{
					xRegisterDecText = new Text(registerGroup, SWT.READ_ONLY | SWT.BORDER);
					xRegisterDecText.setBounds(51, 55, 85, 20);
					
					// background
					xRegisterDecText.setBackground(whiteBg);
				}
				{
					lRegisterDecText = new Text(registerGroup, SWT.READ_ONLY | SWT.BORDER);
					lRegisterDecText.setBounds(51, 75, 85, 20);
					
					// background
					lRegisterDecText.setBackground(whiteBg);
				}
				{
					pcRegisterDecText = new Text(registerGroup, SWT.READ_ONLY | SWT.BORDER);
					pcRegisterDecText.setBounds(51, 95, 85, 20);
					
					// background
					pcRegisterDecText.setBackground(whiteBg);
				}
				{
					swRegisterText = new Text(registerGroup, SWT.READ_ONLY | SWT.BORDER);
					swRegisterText.setBounds(51, 115, 173, 20);
					
					// background
					swRegisterText.setBackground(whiteBg);
				}
				{
					aRegisterHexText = new Text(registerGroup, SWT.READ_ONLY | SWT.BORDER);
					aRegisterHexText.setBounds(141, 35, 83, 20);
					
					// background
					aRegisterHexText.setBackground(whiteBg);
				}
				{
					xRegisterHexText = new Text(registerGroup, SWT.READ_ONLY | SWT.BORDER);
					xRegisterHexText.setBounds(141, 55, 83, 20);
					
					// background
					xRegisterHexText.setBackground(whiteBg);
				}
				{
					lRegisterHexText = new Text(registerGroup, SWT.READ_ONLY | SWT.BORDER);
					lRegisterHexText.setBounds(141, 75, 83, 20);
					
					// background
					lRegisterHexText.setBackground(whiteBg);
				}
				{
					pcRegisterHexText = new Text(registerGroup, SWT.READ_ONLY | SWT.BORDER);
					pcRegisterHexText.setBounds(141, 95, 83, 20);
					
					// background
					pcRegisterHexText.setBackground(whiteBg);
				}
			}
			{
				xeRegisterGroup = new Group(this, SWT.NONE);
				xeRegisterGroup.setText("Register(for XE)");
				xeRegisterGroup.setBounds(12, 301, 238, 125);
				xeRegisterGroup.setLayout(null);
				{
					xeDecLabel = new Label(xeRegisterGroup, SWT.NONE);
					xeDecLabel.setText("Dec");
					xeDecLabel.setBounds(51, 14, 60, 20);
				}
				{
					xeHexLabel = new Label(xeRegisterGroup, SWT.NONE);
					xeHexLabel.setText("Hex");
					xeHexLabel.setBounds(141, 14, 60, 20);
				}
				{
					bRegisterLabel = new Label(xeRegisterGroup, SWT.NONE);
					bRegisterLabel.setText("B (#3)");
					bRegisterLabel.setBounds(8, 35, 41, 20);
				}
				{
					sRegisterLabel = new Label(xeRegisterGroup, SWT.NONE);
					sRegisterLabel.setText("S (#4)");
					sRegisterLabel.setBounds(8, 55, 41, 20);
				}
				{
					tRegisterLabel = new Label(xeRegisterGroup, SWT.NONE);
					tRegisterLabel.setText("T (#5)");
					tRegisterLabel.setBounds(8, 75, 41, 20);
				}
				{
					fRegisterLabel = new Label(xeRegisterGroup, SWT.NONE);
					fRegisterLabel.setText("F (#6)");
					fRegisterLabel.setBounds(8, 95, 41, 20);
				}
				{
					bRegisterDecText = new Text(xeRegisterGroup, SWT.READ_ONLY | SWT.BORDER);
					bRegisterDecText.setBounds(51, 35, 85, 20);
					
					// background
					bRegisterDecText.setBackground(whiteBg);
				}
				{
					sRegisterDecText = new Text(xeRegisterGroup, SWT.READ_ONLY | SWT.BORDER);
					sRegisterDecText.setBounds(51, 55, 85, 20);
					
					// background
					sRegisterDecText.setBackground(whiteBg);
				}
				{
					tRegisterDecText = new Text(xeRegisterGroup, SWT.READ_ONLY | SWT.BORDER);
					tRegisterDecText.setBounds(51, 75, 85, 20);
					
					// background
					tRegisterDecText.setBackground(whiteBg);
				}
				{
					fRegisterText = new Text(xeRegisterGroup, SWT.READ_ONLY | SWT.BORDER);
					fRegisterText.setBounds(51, 95, 173, 20);
					fRegisterText.setEditable(false);
					
					// background
					fRegisterText.setBackground(whiteBg);
				}
				{
					bRegisterHexText = new Text(xeRegisterGroup, SWT.READ_ONLY | SWT.BORDER);
					bRegisterHexText.setBounds(141, 35, 83, 20);
					
					// background
					bRegisterHexText.setBackground(whiteBg);
				}
				{
					sRegisterHexText = new Text(xeRegisterGroup, SWT.READ_ONLY | SWT.BORDER);
					sRegisterHexText.setBounds(141, 55, 83, 20);
					
					// background
					sRegisterHexText.setBackground(whiteBg);
				}
				{
					tRegisterHexText = new Text(xeRegisterGroup, SWT.READ_ONLY | SWT.BORDER);
					tRegisterHexText.setBounds(141, 75, 83, 20);
					
					// background
					tRegisterHexText.setBackground(whiteBg);
				}
			}
			{
				logLabel = new Label(this, SWT.NONE);
				logLabel.setText("Log (\uba85\ub839\uc5b4 \uc218\ud589 \uad00\ub828) :");
				logLabel.setBounds(12, 432, 144, 20);
			}
			{
				logStyledText = new StyledText(this, SWT.WRAP | SWT.V_SCROLL | SWT.BORDER);
				logStyledText.setBounds(12, 454, 506, 109);
				
				// background
				logStyledText.setBackground(whiteBg);
				logStyledText.setEditable(false);
			}
			{
				startAddressMemotyLabel = new Label(this, SWT.NONE);
				startAddressMemotyLabel.setText("Start Address in Memory");
				startAddressMemotyLabel.setBounds(264, 109, 144, 20);
			}
			{
				targetAddressLabel = new Label(this, SWT.NONE);
				targetAddressLabel.setText("TargetAddress :");
				targetAddressLabel.setBounds(264, 135, 144, 20);
			}
			{
				instructionsLabel = new Label(this, SWT.NONE);
				instructionsLabel.setText("Instructions :");
				instructionsLabel.setBounds(264, 172, 82, 20);
			}
			{
				startAddressMemoryText = new Text(this, SWT.RIGHT | SWT.READ_ONLY | SWT.BORDER);
				startAddressMemoryText.setBounds(433, 109, 85, 20);
				startAddressMemoryText.setText("0");
				
				// background
				startAddressMemoryText.setBackground(whiteBg);
			}
			{
				targetAddressText = new Text(this, SWT.RIGHT | SWT.READ_ONLY | SWT.BORDER);
				targetAddressText.setBounds(433, 135, 85, 20);
				
				// background
				targetAddressText.setBackground(whiteBg);
			}
			{
				instructionsList = new List(this, SWT.V_SCROLL | SWT.BORDER);
				instructionsList.setBounds(264, 193, 144, 233);
				instructionsList.setBackground(SWTResourceManager.getColor(240, 240, 240));
				
				// background
				instructionsList.setBackground(whiteBg);
			}
			{
				useDeviceLabel = new Label(this, SWT.RIGHT);
				useDeviceLabel.setText("\uc0ac\uc6a9\uc911\uc778 \uc7a5\uce58");
				useDeviceLabel.setBounds(433, 193, 85, 20);
				useDeviceLabel.setVisible(false);
			}
			{
				useDeviceText = new Text(this, SWT.READ_ONLY | SWT.BORDER);
				useDeviceText.setSize(57, 30);
				useDeviceText.setBounds(458, 219, 60, 20);
				
				// background
				useDeviceText.setBackground(whiteBg);
				useDeviceText.setVisible(false);
			}
			{
				stepRunButton = new Button(this, SWT.CENTER);
				stepRunButton.setText("\uc2e4\ud589(1 Step)");
				stepRunButton.setBounds(433, 304, 85, 30);
				stepRunButton.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						stepRunButtonWidgetSelected(evt);
					}
				});
				stepRunButton.setEnabled(false);
			}
			{
				allRunButton = new Button(this, SWT.PUSH | SWT.CENTER);
				allRunButton.setText("\uc2e4\ud589(All)");
				allRunButton.setBounds(433, 350, 85, 30);
				allRunButton.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						allRunButtonWidgetSelected(evt);
					}
				});
				allRunButton.setEnabled(false);
			}
			{
				exitButton = new Button(this, SWT.PUSH | SWT.CENTER);
				exitButton.setText("\uc885\ub8cc");
				exitButton.setBounds(433, 396, 85, 30);
				exitButton.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent evt) {
						exitButtonWidgetSelected(evt);
					}
				});
			}
			{
				menu1 = new Menu(getShell(), SWT.BAR);
				getShell().setMenuBar(menu1);
				{
					fileMenuItem = new MenuItem(menu1, SWT.CASCADE);
					fileMenuItem.setText("File");
					{
						fileMenu = new Menu(fileMenuItem);
						{
							openFileMenuItem = new MenuItem(fileMenu, SWT.CASCADE);
							openFileMenuItem.setText("Open");
							openFileMenuItem.addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent evt) {
									openFileMenuItemWidgetSelected(evt);
								}
							});
						}
						{
							newFileMenuItem = new MenuItem(fileMenu, SWT.CASCADE);
							newFileMenuItem.setText("New");
						}
						{
							saveFileMenuItem = new MenuItem(fileMenu, SWT.CASCADE);
							saveFileMenuItem.setText("Save");
						}
						{
							saveAsFileMenuItem = new MenuItem(fileMenu, SWT.CASCADE);
							saveAsFileMenuItem.setText("Save As");
						}
						{
							exitMenuItem = new MenuItem(fileMenu, SWT.CASCADE);
							exitMenuItem.setText("Exit");
							exitMenuItem.addSelectionListener(new SelectionAdapter() {
								public void widgetSelected(SelectionEvent evt) {
									exitMenuItemWidgetSelected(evt);
								}
							});
						}
						fileMenuItem.setMenu(fileMenu);
					}
				}
				{
					helpMenuItem = new MenuItem(menu1, SWT.CASCADE);
					helpMenuItem.setText("Help");
					{
						helpMenu = new Menu(helpMenuItem);
						{
							aboutMenuItem = new MenuItem(helpMenu, SWT.CASCADE);
							aboutMenuItem.setText("About");
						}
						helpMenuItem.setMenu(helpMenu);
					}
				}
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * File Handle
	 */
	public void FileHandle(String command) {
		FileDialog fd = null;
		
		if(command.equals("Open")) fd = new FileDialog(getShell(), SWT.OPEN);
		else if(command.equals("Save")) fd = new FileDialog(getShell(), SWT.SAVE);
		
		fd.setText(command);
	    		    
		try {
			fd.setFilterPath(f.getCanonicalPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String[] filterExt = { "*.asm" };
		fd.setFilterExtensions(filterExt);
		
		String selected = fd.open();
		
		//
		if(selected != null) {
			// set filename
			fileNameText.setText(selected);
			
			// logging into logStyledText
			logWrite(LogLV.I, selected);
		}

	}

	
	/**
	 * Event Handle
	 */
	private void fileOpenButtonWidgetSelected(SelectionEvent evt) {
		System.out.println("fileOpenButton.widgetSelected, event="+evt);
		//TODO add your code for fileOpenButton.widgetSelected
		
		FileHandle("Open");
	}
	
	private void openFileMenuItemWidgetSelected(SelectionEvent evt) {
		System.out.println("openFileMenuItem.widgetSelected, event="+evt);
		//TODO add your code for openFileMenuItem.widgetSelected
		
		FileHandle("Open");
	}
	
	private void exitMenuItemWidgetSelected(SelectionEvent evt) {
		System.out.println("exitMenuItem.widgetSelected, event="+evt);
		//TODO add your code for exitMenuItem.widgetSelected
		
		getShell().close();
	}
	
	private void exitButtonWidgetSelected(SelectionEvent evt) {
		System.out.println("exitButton.widgetSelected, event="+evt);
		//TODO add your code for exitButton.widgetSelected
		
		getShell().close();
	}
	
	private static void shellShellClosed(ShellEvent evt, Shell shell) {
		System.out.println("shell.shellClosed, event="+evt);
		//TODO add your code for shell.shellClosed
		
		
		// save 여부 확인 할 것 // ---
//        int style = SWT.APPLICATION_MODAL | SWT.YES | SWT.NO;
//        MessageBox messageBox = new MessageBox(shell, style);
//        messageBox.setText("확인");
//        messageBox.setMessage("프로그램을 종료하시겠습니까?");
//        evt.doit = messageBox.open() == SWT.YES;
	}
	
	private void assembleButtonWidgetSelected(SelectionEvent evt) {
		System.out.println("assembleButton.widgetSelected, event="+evt);
		//TODO add your code for assembleButton.widgetSelected

		//
		if(!fileNameText.getText().equals("")) {
			
			// redirection print
			PrintStream stdout = System.out;
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			System.setOut(new PrintStream(bo, true));
			
			// SIC/XE Runable Manager
			SicxeRunableManager runMgr = new SicxeRunableManager();
			
			// processing assembler
			String fileName = fileNameText.getText().substring(
					fileNameText.getText().lastIndexOf("\\")+1, fileNameText.getText().lastIndexOf('.'));
			String inputFile = fileName+".asm";
			String outputFile = fileName+".obj";
			
			runMgr.assembler(inputFile, outputFile);
			
			System.setOut(stdout);
			
			logWrite(LogLV.I, bo.toString());
			
			logWrite(LogLV.I, "Ready to Run");
			
			
			// set enabled
			stepRunButton.setEnabled(true);
			allRunButton.setEnabled(true);
			
			
//			System.out.println(this.registers);
			
			// set UI for first step
			Vector<ObjectProgram> objectProg = runMgr.getObjectProgramVector();
			Vector<SYMTAB> STAB = runMgr.getSTAB();
			Vector<ESTAB> ESTAB = runMgr.getESTAB();
			Vector<ObjectCode> ObjectCode = runMgr.getObjectCode();
					
			int pgLengthTotal = 0;
			for(int i=0; i<objectProg.size(); i++) {
				ObjectProgram pg = objectProg.get(i);
				
				if(i == 0) {
					// Header Record
					programNameText.setText(pg.getPgName());
					startAddressText.setText(pg.getStartAddress());
					lengthProgramText.setText(pg.getPgLength());
					
					// End Record
					addressFirstInstructionText.setText(pg.getEndStr().substring(2));
				}
				
				pgLengthTotal += Integer.parseInt(pg.getPgLength(), 16);
				
				if (i == 1) {
					ESTAB v = ESTAB.get(4); // RDREC
					v.setAddress(pgLengthTotal);
				}
				
				if(i == 2) {
					ESTAB v = ESTAB.get(5); // WDREC
					v.setAddress(pgLengthTotal);	
				}
			}
			
			// address loading & modification
			// set instruction
			this.instructionVector.clear();
			for(int i=4; i<ObjectCode.size(); i++) {
				ObjectCode oc = ObjectCode.get(i);
				
				this.instructionVector.add(oc.getObjectCode());
				
				// List 에 표시
				this.instructionsList.add(oc.getObjectCode());
				
//				System.out.println("instructionVector:"+oc.getObjectCode());
			}
//			System.out.println(instructionVector.toString());
			
			//
			int startAddressInMemory = this.instructionVector.hashCode();
			startAddressMemoryText.setText(Integer.toHexString(startAddressInMemory).toUpperCase());
			
			this.targetAddress = startAddressInMemory;
			
			// 첫번째 instruction 선택
			instructionsList.setSelection(0);
		}
	}
	
	
	/**
	* V — Verbose (lowest priority)
	* D — Debug
	* I — Info (default priority)
	* W — Warning
	* E — Error
	* F — Fatal
	* S — Silent (highest priority, on which nothing is ever printed)
	*/
	enum LogLV {
		V, D, I, W, E, F, S;
	}
	
	public void logWrite(LogLV level, String logStr) {
		String levelStr = "";
		
		switch(level) {
			case V: levelStr = "Verbose"; break;
			case D: levelStr = "Debug"; break;
			case I: levelStr = "Info"; break;
			case W: levelStr = "Warning"; break;
			case E: levelStr = "Error"; break;
			case F: levelStr = "Fatal"; break;
			case S: levelStr = "Silent"; break;
		}
		
		String logOutput = "["+levelStr+"] "+logStr;
		
		logStyledText.append(logOutput+"\n");
		
		// move cursor to bottom
		logStyledText.setSelection(logStyledText.getCharCount());
	}
	
	
	// =============================================== //
	public void run(Vector<String> instructionVector, int start, int end) {
		final int OPCODE_MASK_BIT = 252;
		final int NI_MASK_BIT = 3;
		final int XBPE_MASK_BIT = 240;
		final int DISP_MASK_BIT = 15;
		
		OPTAB opt = new OPTAB();
		
		// for device
		FileInputStream inputStream = null;
		FileOutputStream outputStream = null;
		try {
			inputStream = new FileInputStream("src.jpg");
			outputStream = new FileOutputStream("dst.jpg");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int bytesRead = 0;
		byte[] buffer = new byte[1024];
		try {
			while ((bytesRead = inputStream.read(buffer, 0, 1024)) != -1) {
			    outputStream.write(buffer, 0, bytesRead);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			outputStream.close();
			inputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		int xIdx = 0;
		
		for(int step = start; step <= end; step++) {
			instructionsList.setSelection(step);
			targetAddressText.setText(Integer.toHexString(targetAddress).toUpperCase());
			
			String instruction = instructionVector.get(step);
			int instructionInt = Integer.parseInt(instruction.substring(0, 2), 16);
			
			String opcode = Integer.toHexString(instructionInt & OPCODE_MASK_BIT);
			int opcodeInt = instructionInt & OPCODE_MASK_BIT;
			String mnemonic = opt.getMNEMONIC(opcodeInt);
			
			int formattype = instruction.length()/2;
			
			String disp = instruction.substring(formattype);
			
			logWrite(LogLV.I, "Running instruction: "+mnemonic);
//			System.out.println("formattype:"+formattype+" Running instruction: "+mnemonic+" disp:"+disp);
			
			if(mnemonic.equals("CLEAR")) {
				doCLEAR(Integer.parseInt(disp.substring(0, 1), 16));
			}
			if(mnemonic.equals("COMP")) {
				doCOMP(Integer.parseInt(disp, 16));
			}
			if(mnemonic.equals("COMPR")) {
				doCOMPR(Integer.parseInt(disp.substring(0, 1), 16), Integer.parseInt(disp.substring(1, 2), 16));
			}
			if(mnemonic.equals("J")) {
				doJ(Integer.parseInt(disp, 16)); //
			}
			if(mnemonic.equals("JEQ")) {
				doJEQ(Integer.parseInt(disp, 16));
			}
			if(mnemonic.equals("JLT")) {
				doJLT(Integer.parseInt(disp, 16));
			}
			if(mnemonic.equals("JSUB")) {
				doJSUB(Integer.parseInt(disp, 16));
			}
			if(mnemonic.equals("LDA")) {
				doLDA(Integer.parseInt(disp, 16));
			}
			if(mnemonic.equals("LDCH")) {
				doLDCH(disp);
			}
			if(mnemonic.equals("LDT")) {
				doLDT(Integer.parseInt(disp, 16));
			}
			if(mnemonic.equals("RD")) {
//				doRD();
			}
			if(mnemonic.equals("RSUB")) {
				doRSUB();
			}
			if(mnemonic.equals("STA")) {
				doSTA();
			}
			if(mnemonic.equals("STCH")) {
				doSTCH();
			}
			if(mnemonic.equals("STL")) {
				doSTL();
			}
			if(mnemonic.equals("STX")) {
				doSTX();
			}
			if(mnemonic.equals("TD")) {
//				doTD();
			}
			if(mnemonic.equals("TIXR")) {
				xIdx = doTIXR(xIdx, Integer.parseInt(disp.substring(0, 1), 16));
			}
			if(mnemonic.equals("WD")) {
//				doWD();
			}
	
			
			refreshDisplay();
	
			
			this.targetAddress += formattype;
		}
	
		// 끝까지 실행한 경우
		if(end >= this.instructionVector.size()-1) {
			int style = SWT.APPLICATION_MODAL | SWT.CHECK;
			MessageBox messageBox = new MessageBox(getShell(), style);
			messageBox.setText("확인");
			messageBox.setMessage("성공적으로 실행하였습니다.");
			if (messageBox.open() != 0) {
				this.runStatus = 1;
			}
		}
	}
	
	/**
	 * register set data & display
	 * 
	 * registerNumber
	 * 0 - register Accumulator
 	 * 1 - register X index for loop
	 * 2 - register Linkage for return address
	 * 3 - register PC program counter
	 * 4 - register Status Word
	 * 5 - register Base
	 * 6 - register S general				
	 * 7 - register T general
	 * 8 - register Floating
	 */
	public void refreshDisplay() {
	
		Object targetComponent1 = null, targetComponent2 = null;
		
		for(int registerNumber = 0; registerNumber < this.registers.size(); registerNumber++) {
			switch(registerNumber) {
			case 0:
				targetComponent1 = aRegisterDecText;
				targetComponent2 = aRegisterHexText;
				break;
			case 1:
				targetComponent1 = xRegisterDecText;
				targetComponent2 = xRegisterHexText;
				break;
			case 2:
				targetComponent1 = lRegisterDecText;
				targetComponent2 = lRegisterHexText;
				break;
			case 3:
				targetComponent1 = pcRegisterDecText;
				targetComponent2 = pcRegisterHexText;
				break;
			case 4:
				targetComponent1 = swRegisterText;
				targetComponent2 = null;
				break;
			case 5:
				targetComponent1 = bRegisterDecText;
				targetComponent2 = bRegisterHexText;
				break;
			case 6:
				targetComponent1 = sRegisterDecText;
				targetComponent2 = sRegisterHexText;
				break;
			case 7:
				targetComponent1 = tRegisterDecText;
				targetComponent2 = tRegisterHexText;
				break;
			case 8:
				targetComponent1 = fRegisterText;
				targetComponent2 = null;
				break;
			}
				
			String regData = this.registers.get(registerNumber).getData();
			if(!regData.equals("")) {
							
				// set display
				if(targetComponent1 != null)
					((Text) targetComponent1).setText( transFormat(
								Integer.toString(Integer.parseInt(regData, 16)), "0", 6, "postfix"
							)
					);
				if(targetComponent2 != null)
					((Text) targetComponent2).setText( transFormat(
							regData, "0", 6, "postfix"
						) 
					);
			}
		}
	}

	// 각 자리별 포맷에 맞추기 위해 변환
	public String transFormat(String orgStr, String pad, int formatLen, String type) {
		String retStr = "";

		// 지정한 양식 길이에서 원본 문자열의 길이를 뺀만큼
		// pad 를 만든다
		for(int i = 0; i<(formatLen-orgStr.length()); i++) {
			retStr = retStr.concat(pad);
		}
		
		if(type.equals("prefix")) {
 			// 원문 앞에 붙여준다.
			retStr = retStr.concat(orgStr);
		} else if(type.equals("postfix")) {
			// 원문 뒤에 붙여준다
			retStr = orgStr.concat(retStr);
		}
		
		return retStr;
	}
	
	// inturction 구현
	public void doCLEAR(int r1) {
		this.registers.get(r1).setData("0");
	}
		
	public int doCOMP(int mValue) {
		// A
		int aRegDataInt = this.registers.get(0).getDataInt(16);
		
		if(aRegDataInt < mValue) return 1; 
		else if(aRegDataInt > mValue) return -1;
		else return 0;
	}
	
	public int doCOMPR(int r1, int r2) {
		int r1RegDataInt = this.registers.get(r1).getDataInt(16);
		int r2RegDataInt = this.registers.get(r2).getDataInt(16);
		
		if(r1RegDataInt < r2RegDataInt) return 1;
		else if(r1RegDataInt > r2RegDataInt) return -1;
		else return 0;
	}
	
	public void doJ(int mValue) {
		// PC
		this.registers.get(3).setData(String.valueOf(mValue));
	}
	
	public void doJEQ(int mValue) {
//		if(conditionCode == mValue)
			// PC
			this.registers.get(3).setData(String.valueOf(mValue));
	}
	
	public void doJLT(int mValue) {
//		if(conditionCode < mValue)
			// PC
			this.registers.get(3).setData(String.valueOf(mValue));
	}
	
	public void doJSUB(int mValue) {
		// L <- (PC); PC <- m
		this.registers.get(2).setData(this.registers.get(3).getData());
		this.registers.get(3).setData(String.valueOf(mValue));		
	}
	
	public void doLDA(int mValue) {
		// A
		this.registers.get(0).setData(String.valueOf(mValue));
	}
	
	public void doLDCH(String mValue) {
		// A[rightmost byte] <- (m)
		this.registers.get(0).setData(mValue.substring(0,1));
	}
	
	public void doLDT(int mValue) {
		// T
		this.registers.get(7).setData(String.valueOf(mValue));
	}
	
	// ?
	public void doRD(char[] cbuf) {
//	public void doRD(Fi) {
		// A[rightmost byte] <- (File fp)

//		this.registers.get(0).setData(String.valueOf(fr.read(cbuf)));
		
		this.registers.get(0).setData(String.valueOf(cbuf));

	}
	
	public void doRSUB() {
		// PC <- (L)
		this.registers.get(3).setData(this.registers.get(2).getData());
	}
	
	public String doSTA() {
		// m <- (A)
		String mValue = this.registers.get(0).getData();
		return mValue;		
	}
	
	public String doSTCH() {
		// m <- (A) [rightmost byte]
		String mValue = this.registers.get(0).getData().substring(0,1);
		return mValue;
	}
	
	public String doSTL() {
		// m <- (L)
		String mValue = this.registers.get(2).getData();
		return mValue;		
	}
	
	public String doSTX() {
		// m <- (X)
		String mValue = this.registers.get(1).getData();
		return mValue;		
	}
	
	public boolean doTD(File fp) {
		return fp.canRead() & fp.canWrite();		
	}
	
	public int doTIXR(int x, int r1) {
		// X <- (X) + 1; (X):(r1)
		this.registers.get(1).setData(this.registers.get(r1).getData());
		return x++;
	}
	
	// ?
	public void doWD(FileWriter fw) {
		try {
			fw.write(this.registers.get(0).getData().substring(0,1));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void stepRunButtonWidgetSelected(SelectionEvent evt) {
		System.out.println("stepRunButton.widgetSelected, event="+evt);
		//TODO add your code for stepRunButton.widgetSelected
		
		// 실행 완료시 재실행 여부 확인
		if(!confirmRunning()) return;
		
		// 현재 선택된 instruction index run step 1
		int idx = instructionsList.getSelectionIndex();
		
		int nextIdx = idx+1;
//		if(nextIdx > this.instructionVector.size()+1)
//			nextIdx--;
	
		System.out.println("idx:"+idx+" next:"+nextIdx);
		
		
		run(this.instructionVector, idx, nextIdx);
		
		// 다음 instruction 으로 선택 이동
		instructionsList.setSelection(nextIdx);
	}
	
	private void allRunButtonWidgetSelected(SelectionEvent evt) {
		System.out.println("allRunButton.widgetSelected, event="+evt);
		//TODO add your code for allRunButton.widgetSelected
		
		// 실행 완료시 재실행 여부 확인
		if(!confirmRunning()) return;
				
		int idx = instructionsList.getSelectionIndex();
		int nextIdx = this.instructionVector.size()-1;
//		if(idx > nextIdx-2)
//			nextIdx--;

		run(this.instructionVector, idx, nextIdx);
		
		instructionsList.setSelection(nextIdx);
	}
	
	
	public boolean confirmRunning() {
		if(this.runStatus == 1) {
	        int style = SWT.APPLICATION_MODAL | SWT.YES | SWT.NO;
	        MessageBox messageBox = new MessageBox(getShell(), style);
	        messageBox.setText("확인");
	        messageBox.setMessage("다시 실행하시겠습니까?");
	        if(messageBox.open() == SWT.YES) {
	        	// 실행 상태 초기화
	        	this.runStatus = 0;
	        	instructionsList.setSelection(0);
	        	
	        	// 레지스터 display 초기화
	        	aRegisterDecText.setText("");
	        	aRegisterHexText.setText("");
	        	xRegisterDecText.setText("");
	        	xRegisterHexText.setText("");
	        	lRegisterDecText.setText("");
	        	lRegisterHexText.setText("");
	        	pcRegisterDecText.setText("");
	        	pcRegisterHexText.setText("");
	        	swRegisterText.setText("");
	        	
	        	bRegisterDecText.setText("");
	        	bRegisterHexText.setText("");
	        	sRegisterDecText.setText("");
	        	sRegisterHexText.setText("");
	        	tRegisterDecText.setText("");
	        	tRegisterHexText.setText("");
	        	fRegisterText.setText("");
	        	
	        	// 레지스터 data 초기화
	        	for(int r = 0; r < this.registers.size(); r++) {
	        		this.registers.get(r).clear();
	        	}
	        	
	        	return true;
			} else {
				return false;
			}
		}
		return true;
	}

	/**
	* Auto-generated main method to display this 
	* org.eclipse.swt.widgets.Composite inside a new Shell.
	*/
	public static void main(String[] args) {

		
		// VisualSimulator
		Display display = Display.getDefault();
		final Shell shell = new Shell(display, SWT.MIN | SWT.CLOSE);
		shell.setText("Sic/XE Simulator(20022992 박용진)");
		
		VisualSimulator inst = new VisualSimulator(shell, SWT.NULL);
		Point size = inst.getSize();
		shell.setLayout(new FillLayout());
		shell.addShellListener(new ShellAdapter() {
			public void shellClosed(ShellEvent evt) {
				shellShellClosed(evt, shell);
			}
		});
		shell.layout();
		if(size.x == 0 && size.y == 0) {
			inst.pack();
			shell.pack();
		} else {
			Rectangle shellBounds = shell.computeTrim(0, 0, size.x, size.y);
			shell.setSize(shellBounds.width, shellBounds.height);
		}
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}
}