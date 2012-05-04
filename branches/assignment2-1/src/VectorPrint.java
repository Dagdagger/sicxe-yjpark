import java.io.*;
import java.util.*;

import sp.dtopack.*;

public class VectorPrint extends CodeLineDTO {
	
	// 기본 생성자
	public VectorPrint() {}

//	public void PrintIFILE(Vector<String> vector) {
//		
//		for(int i=0; i<vector.size(); i++) {
//			String pf = vector.get(i);
//			
//			System.out.println(i+": "+pf);
//		}		
//	}
	
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
	
	public void PrintSTAB(Vector<SYMTAB> vector) {
		
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

	public void PrintESTAB(Vector<ESTAB> vector) {
		
		for(int i=0; i<vector.size(); i++) {
			ESTAB v = vector.get(i);

			System.out.println(i+":"
								+" "+v.getContolSection()
								+"\t"+v.getSymbol()
								+"\t"+Integer.toHexString(v.getAddress())
								+"\t"+v.getLength()
			);
		}		
	}	
	
	public void PrintLITTAB(Vector<LITTAB> vector) {
		
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
	
	// objectCodeList 필요...?
	public void PrintImmediate(Vector<CodeLineDTO> CLDTO, Vector<SYMTAB> STAB) {
		for(int i=0; i<CLDTO.size(); i++) {
			CodeLineDTO v = CLDTO.get(i);
			OPTAB ot = new OPTAB();
			
			System.out.println(
								" "+hexFormat(v.getAddress(), 4)
//								+"\t"+v.getLabel()
								+"\t"+labelFormat(STAB, v.getLabel())
								+"\t"+ot.getMNEMONIC(v.getOpcode())
								+"\t"+operandFormat(v.getOperand1(), v.getOperand2())
			);
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
		
		// 개선필요
		if(label == 0 && !STAB.get(label).equals("START"))
			return "";
		else if(label == 42)
			return "*";
		
		SYMTAB s = STAB.get(label);
		return s.getSYMBOL();
	}
	
	public String operandFormat(String operand1, String operand2){
		if(operand1 == null)
			operand1 = "";
		if(operand2 == null)
			operand2 = "";
		
		return operand1.concat(operand2);
			
	}
	
	// Object Program 파일에 출력
	public void printObjectProg(File fp, Vector<CodeLineDTO> vector) {
		// TODO Auto-generated method stub
		
		try {
			// 전달받은 Object Program 을 output.txt 파일에 작성한다.
			BufferedWriter writer = new BufferedWriter(new FileWriter(fp));
			
			
			for(int i=0; i<vector.size(); i++) {
//				CodeLineDTO v = vector.get(i);
				
				writer.write(vector.get(i).toString());
				writer.newLine();
				
			}
			
			// BufferedWrite 를 종료한다.
			writer.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}