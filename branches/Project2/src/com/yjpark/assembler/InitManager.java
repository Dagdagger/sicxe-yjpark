package com.yjpark.assembler;

import java.util.*;

import org.eclipse.swt.*;

import com.yjpark.resource.*;
import com.yjpark.view.*;

public class InitManager {

	private Vector<Register> registers = new Vector<Register>();
	
	public InitManager() {
	}
	
	public void init() {
		
		// SICXE Runable Manager
		SicxeRunableManager runMgr = new SicxeRunableManager();
		
		// Resource Manager
		ResourceManager resourceMgr = new ResourceManager();
		registers = resourceMgr.getRegisters();
	}

	public Vector<Register> getRegisters() {
		return registers;
	}
	
}
