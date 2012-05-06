package yjparkAssem2;

// Vector 출력 및 immediate data, object program 파일 출력을 위한 Class
import java.io.*;
import java.util.*;

import sp.dtopack.*;
import yjparkAssem2.*;

public class VectorPrint extends CodeLineDTO {
	
	// 기본 생성자
	public VectorPrint() {}	
	
	// immediate data 화면 출력용
	public void PrintImmediate(Vector<CodeLineDTO> CLDTO, Vector<SYMTAB> STAB, Vector<LITTAB> LITTAB, Vector<ObjectCode> OCODE) {
		
		// control section
		int csectIdx = 0;
		
		for(int i=0; i<CLDTO.size(); i++) {
			CodeLineDTO v = CLDTO.get(i);
			OPTAB ot = new OPTAB();
			String address = "";
			String label = "";
			String opcode = "";
			String objectCode = "";
		
			// literal인 경우 LITTAB 정보 가져오기
			if(labelFormat(STAB, v.getLabel()).equals("*")) {
				for(int li = 0; li<LITTAB.size(); li++) {
					LITTAB lt = LITTAB.get(li);
					if(lt.getIdx() == v.getOpcode())
						opcode = "="+lt.getLiteralName();
				}
			} else {
				// 아닌 경우 OPTAB 정보 가져오기
				opcode = ot.getMNEMONIC(v.getOpcode());
				
				// '+' 은 opcode에 표시
				if(v.getOperand1().equals("+"))
					opcode = "+"+opcode;
			}
		
			// control section 을 설정
			if(opcode.equals("START") || opcode.equals("CSECT")) {
				// Control Section 설정
				SYMTAB st = STAB.get(v.getLabel());
				csectIdx = st.getIdx();
			}
		
			// Label 정보 가져오기
			// START 인 경우만 '0' 사용
			if(v.getLabel() != 0 || opcode.equals("START"))
				label = labelFormat(STAB, v.getLabel());
			
			// objectCode 정보 가져오기
			for(int oi = 0; oi<OCODE.size();oi++) {
				ObjectCode obCode = OCODE.get(oi);
				
				if(csectIdx == obCode.getCsectIdx() && v.getAddress() == obCode.getAddress() && !opcode.equals("CSECT")) {
//				if( v.getAddress() == obCode.getAddress() && !opcode.equals("CSECT")) {
					// control section 과 address 가 동일한 경우 objectcode 를 가져온다
					objectCode = obCode.getObjectCode();
					break;
				}
			}
			// Address 정보 가져오기
			// START 인 경우와 첫번째 Instruction만  '0000' 사용
			if(v.getAddress() != 0xFFFF || opcode.equals("START")) {
				if(v.getAddress() == 0xFFFF) {
					address = "0000";
				} else {
					address = hexFormat(v.getAddress(), 4);
				}
			}
			
			// 주석 출력 처리
			if(v.getLineString() != null && v.getLineString().matches("^\\..+")) {
				System.out.println("\t"+v.getLineString());
			} else {
				System.out.println(
									address
									+"\t"+label
									+"\t"+opcode
									+"\t"+operandFormat(v.getOperand1(), v.getOperand2())
									+"\t"+objectCode
				);
			}
		}		
	}

	// 화면에 출력하기 위해서 hex String 으로 변화
	public String hexFormat(int hex, int formatLength) {
		String retStr = "";
		
		String orgHex = Integer.toHexString(hex).toUpperCase();

		// 지정한 양식 길이에서 원본 문자열의 길이를 뺀만큼
		// '0'을 붙여준다.
		for(int i = 0; i<(formatLength-orgHex.length()); i++) {
			retStr = retStr.concat("0");
		}
		
		return retStr.concat(orgHex);	
	}
	
	// SYMTAB에서 해당 label 찾아서 반환
	public String labelFormat(Vector<SYMTAB> STAB, int label) {
		
		// '*' == 0x2A (ASCII code-16진수)
		if(label == 0x2A)
			return "*";
		
		SYMTAB s = STAB.get(label);

		return s.getSYMBOL();
	}
	
	// operand format
	public String operandFormat(String operand1, String operand2){
		if(operand1 == null)
			operand1 = "";
		if(operand2 == null)
			operand2 = "";
		
		// '+' 은 opcode에 표시
		if(!operand1.equals("+"))
			return operand1.concat(operand2);
		
		return operand2;
	}
	
	// Object Program 파일에 출력
	public void printObjectProg(Vector<ObjectProgram> vector) {
		// TODO Auto-generated method stub
		
		try {
			// 전달받은 Object Program 을 output.txt 파일에 작성한다.
			// 출력 파일 열기
			File fp = new File("output.txt");
			BufferedWriter writer = new BufferedWriter(new FileWriter(fp));
			
			for(int i=0; i<vector.size(); i++) {
				ObjectProgram v = vector.get(i);
				
				writer.write(v.combineHeader()
						+v.getExtDef()
						+v.getExtRef()
						+v.getTextStr()
						+v.getModStr()
						+v.getEndStr()
				);
				
				writer.newLine();
				
			}
			
			// BufferedWrite 를 종료한다.
			writer.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// CodeLineDTO 원본 vector 출력
	public void PrintCLDTO(Vector<CodeLineDTO> vector) {
		
		System.out.println("line:"
				+" Address"
				+"\tLabel"
				+"\tOpcode"
				+"\tOperand1"
				+"\tOperand2"
				+"\tComments"
		);
		
		for(int i=0; i<vector.size(); i++) {
			CodeLineDTO v = vector.get(i);
			
			System.out.println(i+":"
								+" "+Integer.toHexString(v.getAddress()).toUpperCase()
								+"\t"+v.getLabel()
								+"\t"+v.getOpcode()
								+"\t"+v.getOperand1()
								+"\t"+v.getOperand2()
								+"\t"+v.getLineString()
			);
		}		
	}
	
	// SYMTAB 원본 vector 출력
	public void PrintSTAB(Vector<SYMTAB> vector) {
	
		System.out.println("line:"
				+" idx"
				+"\tSYMBOL"
				+"\tTYPE"
				+"\tVALUE"
		);
		
		for(int i=0; i<vector.size(); i++) {
			SYMTAB v = vector.get(i);
			
			System.out.println(i+":"
								+" "+v.getIdx()
								+"\t"+v.getSYMBOL()
								+"\t"+v.getTYPE()
								+"\t"+Integer.toHexString(v.getVALUE()).toUpperCase()
			);
		}		
	}	

	// ESTAB 원본 vector 출력
	public void PrintESTAB(Vector<ESTAB> vector) {
	
		System.out.println("line:"
				+" CSECT"
				+"\tSymbol"
				+"\tAddress"
				+"\tLength"
		);
		
		for(int i=0; i<vector.size(); i++) {
			ESTAB v = vector.get(i);

			System.out.println(i+":"
								+" "+v.getCsectIdx()
								+"\t"+v.getContolSection()
								+"\t"+v.getSymbolIdx()
								+"\t"+v.getSymbol()
								+"\t"+Integer.toHexString(v.getAddress()).toUpperCase()
								+"\t"+v.getLength()
			);
		}		
	}	
	
	// LITTAB 원본 vector 출력
	public void PrintLITTAB(Vector<LITTAB> vector) {
		
		System.out.println("line:"
				+" idx"
				+"\tAddress"
				+"\tOpValue"
				+"\tLength"
				+"\tLitName"
		);
		
		for(int i=0; i<vector.size(); i++) {
			LITTAB v = vector.get(i);
			
			System.out.println(i+":"
								+" "+v.getIdx()
								+"\t"+Integer.toHexString(v.getAddress()).toUpperCase()
								+"\t"+v.getOperandValue()
								+"\t"+v.getLength()
								+"\t"+v.getLiteralName()
			);
		}		
	}	

	// ObjectCode 원본 vector 출력
	public void PrintObjectCode(Vector<ObjectCode> vector) {
		
		System.out.println("line:"
				+" address"
				+"\tobjectCode"
		);
		
		for(int i=0; i<vector.size(); i++) {
			ObjectCode v = vector.get(i);
			
			System.out.println(i+":"
								+" "+v.getCsectIdx()
								+"\t"+Integer.toHexString(v.getAddress()).toUpperCase()
								+"\t"+v.getObjectCode()
			);
		}		
	}
	
	// ObjectProgram 원본 vector 출력
	public void PrintObjectProgram(Vector<ObjectProgram> vector) {
		
		System.out.println("line:"
				+" pgName"
				+"\tstartAddress"
				+"\tpgLength"
				+"\textDef"
				+"\textRef"
				+"\ttextStr"
				+"\tmodStr"
				+"\tendStr"
		);

		for(int i=0; i<vector.size(); i++) {
			ObjectProgram v = vector.get(i);
			
			System.out.println(i+":"
								+" "+v.getPgName()
								+"\t"+v.getStartAddress()
								+"\t"+v.getPgLength()
								+"\t"+v.getExtDef()
								+"\t"+v.getExtRef()
								+"\t"+v.getTextStr()
								+"\t"+v.getModStr()
								+"\t"+v.getEndStr()
			);
		}		
	}
}