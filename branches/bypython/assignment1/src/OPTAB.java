/**
 * 향후 어셈블러로의 발전하기 위해 OPTAB을 별도의 클래스로 구분 작성
 * 고정되어 있는 자료들이므로 static table(2차원 배열) 이용
 * Filename: OPTAB.java
 */

public class OPTAB {
	private String MNEMONIC;
	private String OPCODE;
	
	// 변경되지 않는 고정 정보이므로 2차원 배열에 저장한다.
	private String[][] OPTAB = {
			// Instruction
			{"ADD", "18"},
			{"ADDF", "58"},
			{"ADDR", "90"},
			{"AND", "40"},
			{"CLEAR", "B4"},
			{"COMP", "28"},
			{"COMPF", "88"},
			{"COMPR", "A0"},
			{"DIV", "24"},
			{"DIVF", "64"},
			{"DIVR", "9C"},
			{"FIX", "C4"},
			{"FLOAT", "C0"},
			{"HIO", "F4"},
			{"J", "3C"},
			{"JEQ", "30"},
			{"JGT", "34"},
			{"JLT", "38"},
			{"JSUB", "48"},
			{"LDA", "00"},
			{"LDB", "68"},
			{"LDCH", "50"},
			{"LDF", "70"},
			{"LDL", "08"},
			{"LDS", "6C"},
			{"LDT", "74"},
			{"LDX", "04"},
			{"LPS", "D0"},
			{"MUL", "20"},
			{"MULF", "60"},
			{"MULR", "98"},
			{"NORM", "C8"},
			{"OR", "44"},
			{"RD", "D8"},
			{"RMO", "AC"},
			{"RSUB", "4C"},
			{"SHIFTL", "A4"},
			{"SHIFTR", "A8"},
			{"SIO", "F0"},
			{"SSK", "EC"},
			{"STA", "0C"},
			{"STB", "78"},
			{"STCH", "54"},
			{"STF", "80"},
			{"STI", "D4"},
			{"STL", "14"},
			{"STS", "7C"},
			{"STSW", "E8"},
			{"STT", "84"},
			{"STX", "10"},
			{"SUB", "1C"},
			{"SUBF", "5C"},
			{"SUBR", "94"},
			{"SVC", "B0"},
			{"TD", "E0"},
			{"TIO", "F8"},
			{"TIX", "2C"},
			{"TIXR", "B8"},
			{"WD", "DC"},

			// Direction
			{"START", "0"},
			{"END", "1"},
			{"BYTE", "2"},
			{"WORD", "3"},
			{"RESB", "4"},
			{"RESW", "5"}
		};
	
	// default constructor
	public OPTAB () {}
	
	// parameter로 입력된 mnemonic의 OPCODE를 찾아서 반환한다.
	public String searchOPCODE(String mnemonic) {
		String opcode = "";
		
		// 대상 집합이 크지 않으므로 순차검색을 수행한다.
		for(int i=0; i<this.OPTAB.length; i++) {
			if(mnemonic.equals(this.OPTAB[i][0])) {
				opcode = this.OPTAB[i][1];
				break;
			}
		}
		
		return opcode;
	}
}
