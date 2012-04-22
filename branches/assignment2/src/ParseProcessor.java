import java.io.*;
import java.util.*;
import java.util.regex.*;

import sp.interfacepack.*;
import sp.dtopack.*;

public class ParseProcessor implements XEToyAssembler2 {
	
	// 읽어드린 파일 내용을 저장하기 위한 vector 선언
//	Vector<String> IFILE = new Vector<String>();
	
	// SYMTAB 생성하기 위한 vector 선언
	// 각 method 에서 공유하기 위해 클래스 변수로 선언
	Vector<SYMTAB> STAB = new Vector<SYMTAB>();
	
	// 기본 생성자
	public ParseProcessor() {}

	// 입력받은 파일 내용을 모두 분석하여 Vector 타입에 담아 반환한다.
	@Override
	public Vector<CodeLineDTO> parseData(File source) {
		
		// 입력 파일의 내용을 vector 로 처리하기 위한 선언
		Vector<CodeLineDTO> CLDTO = new Vector<CodeLineDTO>();
		
		// 입력 파일을 읽어들이기 위한 변수
		BufferedReader reader = null;
		String[] tmpLine = new String[5];
		String line = "";
		
		// for SYMTAB
		int sIdx = 0;
		
		// OPTAB Class에 접근하기 위한 인스턴스 생성
		OPTAB ot = new OPTAB();
		
		// 파라미터로 입력받은 파일명의 파일을 읽어들인다.
		try {
			reader = new BufferedReader(new FileReader(source));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			// vector 초기화
//			IFILE.clear();
			STAB.clear();
			CLDTO.clear();
			
			// 입력 파일을 라인단위로 읽는다.
			while((line = reader.readLine()) != null) {
				// 읽어드린 파일 내용을 저장
//				IFILE.add(line);
				
				// vector 타입형 instance 생성
				CodeLineDTO c = new CodeLineDTO();
				SYMTAB s = new SYMTAB();
				
				// 1개 라인별로 해당 항목은 탭("\t") 으로 구분한다.
				tmpLine = line.split("\t");
				for(int i=0;i<tmpLine.length;i++) {
					
					// 주석 라인의 경우 comment 에 저장하고 다음 라인으로 이동
					if(tmpLine[i].equals(".")) {
						c.setLineString(tmpLine[i]);
						break;
					}
					
					// 비어있는 항목의 경우 건너뛴다.
					if(tmpLine[i].isEmpty())
						continue;
					
					// 각 항목별 입력 처리
					switch(i) {
					case 0:
						// LABEL
						
						// SYMTAB 에 저장 및 idx 증가
						s.setIdx(sIdx++);
						s.setSYMBOL(tmpLine[i]);
						STAB.add(s);

						// STAB 의 int idx 를 저장
						c.setLabel(s.getIdx());
						break;
					case 1:
						// OPCODE
					
						if(tmpLine[i].matches("^\\+.*")) {
								// 첫글자 하나만
								// @: Extended Addressing
	 							c.setOperand1(tmpLine[i].substring(0, 1));
	 							
	 							// 두번째 글자부터
	 							tmpLine[i] = tmpLine[i].substring(1);
						}
						
						// OPCODE에서 찾은 mnemonic
						// String -> Hex value -> int 변환저장
						c.setOpcode(ot.getOpcode(tmpLine[i]));
						break;
					case 2:
						// OPERAND
						
						// OPERAND1, OPERAND2(@, #, =)
						// 로 구분하여 저장
						if(tmpLine[i].matches("^\\@.*") ||
							tmpLine[i].matches("^\\#.*") ||
							tmpLine[i].matches("^\\=.*")) {
							// 첫글자 하나만
							// @: Indirect Addressing
							// #: Immediate Addressing
							// =: Literals
 							c.setOperand1(tmpLine[i].substring(0, 1));
 							
 							// 두번째 글자부터
 							tmpLine[i] = tmpLine[i].substring(1);
						}
						
						// 나머지 OPERAND 저장
						c.setOperand2(tmpLine[i]);
						break;					
					case 3:
						// COMMENTS
						c.setLineString(tmpLine[i]);
						break;
					}
				}
				
				// CodeLineDTO vector 에 추가
				CLDTO.add(c);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		// BufferedReader 를 종료한다.
		try {
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// 해당 vertor를 반환한다.
		return CLDTO;
	}

	// 1번 함수를 통해 얻어낸 Vector 값을 Immediate 형태로 변환
	@Override
	public Vector<CodeLineDTO> changeImmediateCode(Vector<CodeLineDTO> vector) {
		Vector<CodeLineDTO> CLDTO = vector;
//		CodeLineDTO c = new CodeLineDTO();
		LOCCTR LOCCTR = new LOCCTR();
		
		String label = "";
		String opcode = "";
		String operand = "";
		
		String pgname = "";
		int formattype = 0;
		
//		VectorPrint vp = new VectorPrint();
//		vp.PrintIFILE(IFILE);
		
		for(int i=0; i<vector.size(); i++) {
			CodeLineDTO c = vector.get(i);
			
			// SYMTAB
			// LABEL
			SYMTAB s = STAB.get(c.getLabel());
			label = s.getSYMBOL();

			// OPCODE 검색을 위한 instance 생성
			OPTAB ot = new OPTAB();
			
			formattype = ot.getFormatType(c.getOpcode());

			// OPCODE & OPERAND
			if(c.getOperand1() == null) {
				opcode = ot.getMNEMONIC(c.getOpcode());
				operand = c.getOperand2();
				
			} else if(c.getOperand1().equals("+")) {
				// Extended Addressing
				opcode = c.getOperand1()+ot.getMNEMONIC(c.getOpcode());
				operand = c.getOperand2();
				
				// 
				formattype = 4;
				
			} else if(c.getOperand1().equals("@")) {
				opcode = ot.getMNEMONIC(c.getOpcode());
				operand = c.getOperand1()+c.getOperand2();
				
			} else if(c.getOperand1().equals("#")) {
				opcode = ot.getMNEMONIC(c.getOpcode());
				operand = c.getOperand1()+c.getOperand2();
				
			} else if(c.getOperand1().equals("=")) {
				opcode = ot.getMNEMONIC(c.getOpcode());
				operand = c.getOperand1()+c.getOperand2();
				
			} else {
				opcode = ot.getMNEMONIC(c.getOpcode());
				operand = c.getOperand2();
			}
			
			// 
			if(opcode.equals("START")) {
				
				pgname = label;
				LOCCTR.setLOCCTR(operand);
			}

			//
			LOCCTR.addLOCCTR(formattype);
			
			////////////////
			System.out.println(LOCCTR.getLOCCTR());
		}
		
		// 해당 vertor를 반환한다.
		return CLDTO;

	}

	// 1번 함수를 통해 얻어낸 Vector 값 또는 2번에서 얻어낸 결과 값을 이용하여 최종 ObjectCode로 변환
	@Override
	public Vector<CodeLineDTO> changeObjectCode(Vector<CodeLineDTO> vector) {
		Vector<CodeLineDTO> CLDTO = vector;
//		CodeLineDTO c = new CodeLineDTO();

		for(int i=0; i<vector.size(); i++) {
			CodeLineDTO c = vector.get(i);

		}
		// 해당 vertor를 반환한다.
		return CLDTO;

	}

}
