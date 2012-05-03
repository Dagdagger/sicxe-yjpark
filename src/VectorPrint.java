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
				//+"\tComments"
		);
		
		for(int i=0; i<vector.size(); i++) {
			CodeLineDTO v = vector.get(i);
			
			System.out.println(i+":"
								+" "+Integer.toHexString(v.getAddress()).toUpperCase()
								+"\t"+v.getLabel()
								+"\t"+v.getOpcode()
								+"\t"+v.getOperand1()
								+"\t"+v.getOperand2()
								//+"\t"+v.getLineString()
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
								+"\t"+v.getVALUE()
			);
		}		
	}	
	
	public void PrintImmediate(Vector<ImmediateDTO> vector) {
		
		for(int i=0; i<vector.size(); i++) {
			ImmediateDTO v = vector.get(i);
			
			System.out.println(i+":"
								+" "+v.getLabel()
								+"\t"+v.getOpcode()
								+"\t"+v.getOperand1()
								+"\t"+v.getOperand2()
								+"\t"+v.getLineString()
			);
		}		
	}

	// Object Program 파일에 출력
	public void printObjectProg(File fp, Vector<CodeLineDTO> vector) {
		// TODO Auto-generated method stub
		
		try {
			// 전달받은 Object Program 을 output.txt 파일에 작성한다.
			BufferedWriter writer = new BufferedWriter(new FileWriter(fp));
			
			
			for(int i=0; i<vector.size(); i++) {
				CodeLineDTO v = vector.get(i);
				
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