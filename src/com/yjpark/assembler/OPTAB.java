package com.yjpark.assembler;

// OPTAB 생성 및 관리를 위한 Class
public class OPTAB {
	// 변경되지 않는 고정 정보이므로 2차원 배열에 저장한다.
	private String[][] OPCODE = {
			// Directive Format = 0
			// Memonic, Format, Hexadecimal, Offset, OperandNumber
			// --------------------
			{"ADD", "3", "18", "3", "1"},
			{"ADDF", "3", "58", "3", "1"},
			{"ADDR", "2", "90", "2", "2"},
			{"CLEAR", "2", "B4", "2", "1"},
			{"COMP", "3", "28", "3", "1"},
			{"COMPF", "3", "88", "3", "1"},
			{"COMPR", "2", "A0", "2", "2"},
			{"DIV", "3", "24", "3", "1"},
			{"DIVF", "3", "64", "3", "1"},
			{"DIVR", "2", "9C", "2", "2"},
			{"FIX", "1", "C4", "1", "0"},
			{"FLOAT", "1", "C0", "1", "0"},
			{"HIO", "1", "F4", "1", "0"},
			{"J", "3", "3C", "3", "1"},
			{"JEQ", "3", "30", "3", "1"},
			{"JGT", "3", "34", "3", "1"},
			{"JLT", "3", "38", "3", "1"},
			{"JSUB", "3", "48", "3", "1"},
			{"LDA", "3", "00", "3", "1"},
			{"LDB", "3", "68", "3", "1"},
			{"LDCH", "3", "50", "3", "1"},
			{"LDF", "3", "70", "3", "1"},
			{"LDL", "3", "08", "3", "1"},
			{"LDS", "3", "6C", "3", "1"},
			{"LDT", "3", "74", "3", "1"},
			{"LDX", "3", "04", "3", "1"},
			{"LPS", "3", "D0", "3", "1"},
			{"MUL", "3", "20", "3", "1"},
			{"MULF", "3", "60", "3", "1"},
			{"MULR", "2", "98", "2", "2"},
			{"NORM", "1", "C8", "1", "0"},
			{"OR", "3", "44", "3", "1"},
			{"RD", "3", "D8", "3", "1"},
			{"RMO", "2", "AC", "2", "2"},
			{"RSUB", "3", "4C", "3", "0"},
			{"SHIFTL", "2", "A4", "2", "2"},
			{"SHIFTR", "2", "A8", "2", "2"},
			{"SIO", "1", "F0", "1", "0"},
			{"SSK", "3", "ED", "3", "1"},
			{"STA", "3", "0C", "3", "1"},
			{"STB", "3", "78", "3", "1"},
			{"STCH", "3", "54", "3", "1"},
			{"STF", "3", "80", "3", "1"},
			{"STI", "3", "D4", "3", "1"},
			{"STL", "3", "14", "3", "1"},
			{"STS", "3", "7C", "3", "1"},
			{"STSW", "3", "E8", "3", "1"},
			{"STT", "3", "84", "3", "1"},
			{"STX", "3", "10", "3", "1"},
			{"SUB", "3", "1C", "3", "1"},
			{"SUBF", "3", "5C", "3", "1"},
			{"SUBR", "2", "94", "2", "2"},
			{"SVC", "2", "B0", "2", "1"},
			{"TD", "3", "E0", "3", "1"},
			{"TIO", "1", "F8", "1", "0"},
			{"TIX", "3", "2C", "3", "1"},
			{"TIXR", "2", "B8", "2", "1"},
			{"WD", "3", "DC", "3", "1"},
			
			// Directive
			{"BASE", "0", "FF1", "0", "1"},
			{"BYTE", "0", "FF2", "1", "1"},
			{"CSECT", "0", "FF3", "0", "1"},
			{"END", "0", "FF4", "0", "1"},
			{"EQU", "0", "FF5", "0", "1"},
			{"EXTDEF", "0", "FF6", "0", "1"},
			{"EXTREF", "0", "FF7", "0", "1"},
			{"LTORG", "0", "FF8", "0", "0"},
			{"NOBASE", "0", "FF9", "0", "0"},
			{"ORG", "0", "FFA", "0", "1"},
			{"RESB", "0", "FFB", "1", "1"},
			{"RESW", "0", "FFC", "3", "1"},
			{"START", "0", "FFD", "0", "1"},
			{"USE", "0", "FFE", "0", "1"},
			{"WORD", "0", "FFF", "3", "1"}

		};
	
	private String[][] Register = {
			// Memonic, Number
			// --------------------
			{"A", "0"},
			{"X", "1"},
			{"L", "2"},
			{"B", "3"},
			{"S", "4"},
			{"T", "5"},
			{"F", "6"},
			{"PC", "8"},
			{"SW", "9"}
	};
	
	// 기본 생성자
	public OPTAB () {}
	
	// parameter로 입력된 mnemonic의 OPCODE를 찾아서 반환한다.
	public String searchOPCODE(String mnemonic) {
		String opcode = "";

		// 대상 집합이 크지 않으므로 순차검색을 수행한다.
		// 알파벳 첫글자 인덱스 검색으로 변경??
		for(int i=0; i<this.OPCODE.length; i++) {
			if(mnemonic.equals(this.OPCODE[i][0])) {
				opcode = this.OPCODE[i][2];
				break;
			}
		}

		// 검색된 String OPCODE 값을 반환한다.
		return opcode;
	}
	
	// OPCODE String -> Hex value -> ASCII 로 변환
	public int getOpcode(String mnemonic) {
		
		// extended 인 경우 OPTAB 검색시에는 "+" 제외
		if(mnemonic.matches("^\\+.*")) {
			mnemonic = mnemonic.substring(1);
		}
		
		// OPCODE mnemonic 에 해당하는 Hex value를 찾아온다.
		String str = searchOPCODE(mnemonic);
		int ascii = 0;
		
		// OPTAB 에 없는 경우
		if(str.isEmpty()) {
			// 문자열 "0"
			str = "0";
			// 숫자 0
			ascii = 0;
		}

		// 16진수 -> int ascii 로 변경
		ascii += Integer.parseInt(str, 16);

		// 16진수 -> int 로 변경된 값을 반환한다.
		return ascii;
	}
	
	// parameter로 입력된 opcode의 mnemonic을 찾아서 반환한다.
	public String getMNEMONIC(int opcodeInt) {
		
		String mnemonic = "";
		String opcodeHex = "";
		if(opcodeInt < 16) 
			opcodeHex = "0" + Integer.toHexString(opcodeInt).toUpperCase();
		else 
			opcodeHex = Integer.toHexString(opcodeInt).toUpperCase();

		// 대상 집합이 크지 않으므로 순차검색을 수행한다.
		// 변경??
		for(int i=0; i<this.OPCODE.length; i++) {
			if(opcodeHex.equals(this.OPCODE[i][2])) {
				mnemonic = this.OPCODE[i][0];
				break;
			}
		}
		
		return mnemonic;
	}
	
	// parameter로 입력된 opcode의 파일 포맷을 반환한다.
	public int getFormatType(int opcodeInt) {
		int formattype = 0;
		String opcodeHex = "";
		if(opcodeInt < 16) 
			opcodeHex = "0" + Integer.toHexString(opcodeInt).toUpperCase();
		else 
			opcodeHex = Integer.toHexString(opcodeInt).toUpperCase();
		
		// 대상 집합이 크지 않으므로 순차검색을 수행한다.
		// 변경??
		for(int i=0; i<this.OPCODE.length; i++) {
			if(opcodeHex.equals(this.OPCODE[i][2])) {
				formattype = Integer.parseInt(this.OPCODE[i][1]);
				break;
			}
		}
		
		return formattype;
	}
	
	// parameter로 입력된 opcode의 operand 갯수를 반환한다.
	public int getOperandNumber(int opcodeInt) {
		int operandnumber = 0;
		String opcodeHex = "";
		if(opcodeInt < 16) 
			opcodeHex = "0" + Integer.toHexString(opcodeInt).toUpperCase();
		else 
			opcodeHex = Integer.toHexString(opcodeInt).toUpperCase();
		
		// 대상 집합이 크지 않으므로 순차검색을 수행한다.
		// 변경??
		for(int i=0; i<this.OPCODE.length; i++) {
			if(opcodeHex.equals(this.OPCODE[i][2])) {
				operandnumber = Integer.parseInt(this.OPCODE[i][4]);
				break;
			}
		}
		
		return operandnumber;
	}
	
	// parameter로 입력된 opcode의 offset 을 반환한다.
	public int getOffset(int opcodeInt) {
		int offset = 0;
		String opcodeHex = "";
		if(opcodeInt < 16) 
			opcodeHex = "0" + Integer.toHexString(opcodeInt).toUpperCase();
		else 
			opcodeHex = Integer.toHexString(opcodeInt).toUpperCase();
		
		// 대상 집합이 크지 않으므로 순차검색을 수행한다.
		// 변경??
		for(int i=0; i<this.OPCODE.length; i++) {
			if(opcodeHex.equals(this.OPCODE[i][2])) {
				offset = Integer.parseInt(this.OPCODE[i][3]);
				break;
			}
		}
		
		return offset;
	}
	
	// parameter로 입력된 mnemonic의 register number를 찾아서 반환한다.
	public String getRegisterNumber(String mnemonic) {
		// "0" 으로 초기화, register 없는 경우는 "0"
		String number = "0";

		// 대상 집합이 크지 않으므로 순차검색을 수행한다.
		// 알파벳 첫글자 인덱스 검색으로 변경??
		for(int i=0; i<this.Register.length; i++) {
			if(mnemonic.equals(this.Register[i][0])) {
				number = this.Register[i][1];
				break;
			}
		}

		// 검색된 String number 값을 반환한다.
		return number;
	}
	
	// parameter로 입력된 number의 register symbol 을 찾아서 반환한다.
	public String getRegister(String number) {
		String mnemonic = "";

		// 대상 집합이 크지 않으므로 순차검색을 수행한다.
		// 알파벳 첫글자 인덱스 검색으로 변경??
		for(int i=0; i<this.Register.length; i++) {
			if(mnemonic.equals(this.Register[i][1])) {
				number = this.Register[i][0];
				break;
			}
		}

		// 검색된 String number 값을 반환한다.
		return mnemonic;
	}
}
