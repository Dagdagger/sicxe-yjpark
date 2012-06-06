package com.yjpark.resource;

import java.util.*;

public class ResourceManager {
	private Vector<Register> registers = new Vector<Register>();
	
	public ResourceManager() {
		// register Accumulator
		Register aRegister = new Register(0);
		this.registers.add(aRegister);
		
		// register X index for loop
		Register xRegister = new Register(1);
		this.registers.add(xRegister);
		
		// register Linkage for return address
		Register lRegister = new Register(2);
		this.registers.add(lRegister);
		
		// register PC program counter
		Register pcRegister = new Register(8);
		this.registers.add(pcRegister);
		
		// register Status Word
		Register swRegister = new Register(9);
		this.registers.add(swRegister);
		
		// register Base
		Register bRegister = new Register(3);
		this.registers.add(bRegister);
		
		// register S general
		Register sRegister = new Register(4);
		this.registers.add(sRegister);
		
		// register T general
		Register tRegister = new Register(5);
		this.registers.add(tRegister);
		
		// register Floating
		Register fRegister = new Register(6);
		this.registers.add(fRegister);
	}

	public Vector<Register> getRegisters() {
		return registers;
	}
}
