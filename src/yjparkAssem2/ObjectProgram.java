package yjparkAssem2;

// ObjectProgram 생성 및 관리를 위한 Class
public class ObjectProgram {

	String pgName, startAddress, pgLength;
	String extDef;
	String extRef;
	String textStartAddr;
	String textStrBuf;
	String textStr;
	String modStr;
	String endStr;

	public ObjectProgram() {}
	
	public void init() {
		this.pgName = "";
		this.startAddress  = "";
		this.pgLength = "";
		this.extDef = "";
		this.extRef = "";
		this.textStartAddr = "";
		this.textStrBuf = "";
		this.textStr = "";
		this.modStr = "";
		this.endStr = "";
	}

	public String getPgName() {
		return pgName;
	}

	public void setPgName(String pgName) {
		this.pgName = transFormat(pgName, " ", 6, "postfix");
	}

	public String getStartAddress() {
		return startAddress;
	}

	public void setStartAddress(String startAddress) {
		this.startAddress = transFormat(startAddress, "0", 6, "prefix");
	}

	public String getPgLength() {
		return pgLength;
	}

	public void setPgLength(String pgLength) {
		this.pgLength = transFormat(pgLength, "0", 6, "prefix");
	}

	// 1|2~7   |8~13        |14~19
	// H|pgName|startAddress|pgLength
	public String combineHeader() {
		return "H"+transFormat(this.pgName, " ", 6 ,"postfix").concat(transFormat(this.startAddress, "0", 6, "prefix").concat(transFormat(this.pgLength, "0", 6, "prefix")));
	}

	// 1|2~7   |8~13    |14~19 |20~25   |...
	// D|DefNm1|DefAddr1|DefNm2|DefAddr2|...	
	public String getExtDef() {
		if(!this.extDef.isEmpty() && !this.extDef.matches("^\\nD.*"))
			this.extDef = "\nD"+this.extDef;
		
		return this.extDef;
	}

	public void setExtDef(String extDef) {
		this.extDef = this.extDef+transFormat(extDef, " ", 6, "postfix");
	}

	public void setExtDefAddr(String extDef) {
		this.extDef = this.extDef+transFormat(extDef, "0", 6, "prefix");
	}
	
	// 1|2~7   |8~13  |...
	// D|RefNm1|RefNm2|...
	public String getExtRef() {
		return "\nR"+extRef;
	}
	
	public void setExtRef(String extRef) {
		this.extRef = this.extRef+transFormat(extRef, " ", 6, "postfix");
	}
	
	// 1|2~7          |8~9       |10~69
	// T|TextStartAddr|TextLength|ObjectCode....
	
	public String getTextStartAddr() {
		return textStartAddr;
	}
	
	public void setTextStartAddr(String textStartAddr) {
		this.textStartAddr = transFormat(textStartAddr, "0", 6, "prefix");
		this.textStr = this.textStr+"\nT"+this.textStartAddr;	
	}
	
	public String getTextStr() {
		return textStr;
	}

	public String combineTextStr() {
		return textStr;
	}
	
	public void setTextStr(String textStr, String address) {
				
		// 새로운 text record 시작
		if(this.textStrBuf.length()/2 >= 0x1D) {
			
			// textStr 에 추가 및 textStrBuf 관련 초기화
			this.textStr = this.textStr + transFormat(Integer.toHexString(this.textStrBuf.length()/2).toUpperCase(), "0", 2, "prefix") + this.textStrBuf;
					
			setTextStartAddr(address);
			this.textStrBuf = "";
		}
	
		// textStrBuf 에 추가
		this.textStrBuf = this.textStrBuf + textStr;		

	}

	public void setNewTextStr() {
		
		if(!this.textStrBuf.isEmpty()) {
			this.textStr = this.textStr + transFormat(Integer.toHexString(this.textStrBuf.length()/2).toUpperCase(), "0", 2, "prefix") + this.textStrBuf;

			this.textStartAddr = "";
			this.textStrBuf = "";
		}

	}
	
	// 1|2~7         |8~9      |+/-|10~15
	// M|StartModAddr|ModLength|+/-|RefNm...
	public String getModStr() {
		return modStr;
	}
	
	public void setModStr(String modStr, int zeroCnt, String objectCode, String operand) {		
		this.modStr = this.modStr+"\nM"+transFormat(modStr, "0", 6, "prefix")+transFormat(String.valueOf(zeroCnt), "0", 2, "prefix")+operand;
	}

	// 1|2~7
	// E|AddrOfStart
	public String getEndStr() {
		return "\nE"+endStr;
	}

	public void setEndStr(String endStr) {
		if(endStr.equals("\n"))
			this.endStr += "\n";
		else 
			this.endStr = this.endStr+transFormat(endStr, "0", 6, "prefix");
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
	
	
}
