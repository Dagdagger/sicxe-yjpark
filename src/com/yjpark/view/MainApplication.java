package com.yjpark.view;

import java.io.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import com.cloudgarden.resource.SWTResourceManager;


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
public class MainApplication extends org.eclipse.swt.widgets.Composite {

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
	private Text logText;
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
	private Text swRegisterDecText;
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
	Color whiteBg = Display.getDefault().getSystemColor(SWT.COLOR_WHITE);
	
	// get current file path
	File f = new File(".");
	
	
	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}

	public MainApplication(Composite parent, int style) {
		super(parent, style);
		initGUI();
		
		fileOpenButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent evt) {
				fileOpenButtonWidgetSelected(evt);
			}
		});
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
					addressFirstInstructionLabel.setBounds(8, 20, 151, 29);
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
					swRegisterDecText = new Text(registerGroup, SWT.READ_ONLY | SWT.BORDER);
					swRegisterDecText.setBounds(51, 115, 173, 20);
					
					// background
					swRegisterDecText.setBackground(whiteBg);
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
			}
			{
				useDeviceText = new Text(this, SWT.READ_ONLY | SWT.BORDER);
				useDeviceText.setSize(57, 30);
				useDeviceText.setBounds(458, 219, 60, 20);
				
				// background
				useDeviceText.setBackground(whiteBg);
			}
			{
				stepRunButton = new Button(this, SWT.PUSH | SWT.CENTER);
				stepRunButton.setText("\uc2e4\ud589(1 Step)");
				stepRunButton.setBounds(433, 304, 85, 30);
			}
			{
				allRunButton = new Button(this, SWT.PUSH | SWT.CENTER);
				allRunButton.setText("\uc2e4\ud589(All)");
				allRunButton.setBounds(433, 350, 85, 30);
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
		
		String[] filterExt = { "*.*", "*.txt", ".obj" };
		fd.setFilterExtensions(filterExt);
		String selected = fd.open();
		
		// set filename
		fileNameText.setText(selected);
		
		// logging into logScrolledComposite
		logStyledText.append(this.toString()+selected+"\n");
		// move cursor to bottom
		logStyledText.setSelection(logStyledText.getCharCount());
		
	}

	
	/**
	* Auto-generated main method to display this 
	* org.eclipse.swt.widgets.Composite inside a new Shell.
	*/
	public static void main(String[] args) {
		Display display = Display.getDefault();
		final Shell shell = new Shell(display, SWT.MIN | SWT.CLOSE);
		shell.setText("Sic/XE Simulator(20022992 박용진)");
		
		MainApplication inst = new MainApplication(shell, SWT.NULL);
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
        int style = SWT.APPLICATION_MODAL | SWT.YES | SWT.NO;
        MessageBox messageBox = new MessageBox(shell, style);
        messageBox.setText("확인");
        messageBox.setMessage("프로그램을 종료하시겠습니까?");
        evt.doit = messageBox.open() == SWT.YES;
	}
	
	private void assembleButtonWidgetSelected(SelectionEvent evt) {
		System.out.println("assembleButton.widgetSelected, event="+evt);
		//TODO add your code for assembleButton.widgetSelected
		
		File af = new File(fileNameText.getText());
		
		if( ! (af.canRead() || af.isFile()) ) System.out.println("파일을 읽을 수 없습니다."); // message
		
		
		
	}
}
