package com.yjpark.assembler;


import java.io.*;
import java.util.*;

import sp.interfacepack.*;
import sp.dtopack.*;

//Parsing 처리 및 Object Program 출력을 위한 Class
public class ParseProcessor implements XEToyAssembler2 {
	
	// output filename // ---
	String outputName = "";
	
	// SYMTAB 생성하기 위한 vector 선언
	// 각 method 에서 공유하기 위해 클래스 변수로 선언
	static Vector<SYMTAB> STAB = new Vector<SYMTAB>();
	
	// ESTAB
	static Vector<ESTAB> ESTAB = new Vector<ESTAB>();
	
	// address, objectCode
	static Vector<ObjectCode> OCODE = new Vector<ObjectCode>();
	
	VectorPrint pv = new VectorPrint();
	
	// 기본 생성자
	public ParseProcessor() {}
	
	// 파일명 설정 // ---
	public ParseProcessor(String outputName) {
		this.outputName = outputName;
	}

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
			ESTAB.clear();
			CLDTO.clear();
			OCODE.clear();
			
			// 입력 파일을 라인단위로 읽는다.
			while((line = reader.readLine()) != null) {
				// 읽어드린 파일 내용을 저장
//				IFILE.add(line);
				
				// vector 타입형 instance 생성
				CodeLineDTO c = new CodeLineDTO();
				SYMTAB s = new SYMTAB();
				
				// 초기화
				c.setAddress(0xFFFF);
//				c.setLabel(0xFFFF);	// label은 idx로 관리
				c.setOpcode(0xFFFF);
				c.setOperand1("");
				c.setOperand2("");
				c.setLineString("");
				
				// 1개 라인별로 해당 항목은 탭("\t") 으로 구분한다.
				tmpLine = line.split("\t");
				for(int i=0;i<tmpLine.length;i++) {
					
					// 주석 라인의 경우 comment 에 저장하고 다음 라인으로 이동
					if(tmpLine[i].equals(".")) {
						String comment = "";
						for(int ci = i; ci<tmpLine.length; ci++) {
							comment += tmpLine[ci]+"\t";
						}
						c.setLineString(comment);
						break;
					}
					
					// 비어있는 항목의 경우 건너뛴다.
					if(tmpLine[i].isEmpty()) {
						continue;
					}
					
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
								// +: Extended Addressing
	 							c.setOperand1(tmpLine[i].substring(0, 1));
	 							
	 							// 두번째 글자부터
	 							tmpLine[i] = tmpLine[i].substring(1);
						} else if(tmpLine[i].equals("CSECT") || tmpLine[i].equals("START")) {
							// control section 의 시작
							ESTAB et = new ESTAB();
							et.setCsectIdx(s.getIdx());
							et.setContolSection(tmpLine[i-1]);
							ESTAB.add(et);
						} else if(tmpLine[i].equals("EXTDEF")) {
							// EXTDEF 추가
							String[] extDefAr = tmpLine[i+1].split(",");
							for(int id=0; id<extDefAr.length;id++) {
								ESTAB et = new ESTAB();
								et.setSymbol(extDefAr[id]);
								ESTAB.add(et);
							}
							
						}

						// OPCODE에서 찾은 mnemonic
						// String -> Hex value -> int 변환저장
						c.setOpcode(ot.getOpcode(tmpLine[i]));
						break;
					case 2:
						// OPERAND
						
						// OPERAND1(@, #, =), OPERAND2
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

	// 1번 함수를 통해 얻어낸 Vector 값을 Immediate data 형태로 변환
	@Override
	public Vector<CodeLineDTO> changeImmediateCode(Vector<CodeLineDTO> vector) {

		Vector<CodeLineDTO> CLDTO = vector;
		Vector<LITTAB> LITTAB = new Vector<LITTAB>();
		
		CodeLineDTO im = new CodeLineDTO();
		
		int locctr = 0;	// location counter 초기화
		
		// for ObjectCode
		int csectIdx = 0;
		
		String label = "";
		String opcode = "";
		String operand = "";

		int formattype = 0;
		
		for(int i=0; i<vector.size(); i++) {
			CodeLineDTO c = vector.get(i);
			
			// SYMTAB
			// LABEL
			SYMTAB s = STAB.get(c.getLabel());
			label = s.getSYMBOL();

			// 'R' or 'A' for SYMTAB
			char symbolType = 'R';
			
			// OPCODE 검색을 위한 instance 생성
			OPTAB ot = new OPTAB();
			
			// 주석인 경우 다음 라인으로 넘어간다
			if(c.getLineString() != null && c.getLineString().equals("."))
				continue;

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
				
			} else if(c.getOperand1().equals("@")
						|| (c.getOperand1().equals("#"))
						|| (c.getOperand1().equals("="))
					)
			{
				opcode = ot.getMNEMONIC(c.getOpcode());
				operand = c.getOperand1()+c.getOperand2();
				
				// LITTAB에서 검색하고 없으면 LITTAB에 추가
				if(c.getOperand1().equals("=")) {
					LITTAB lt = new LITTAB();
					
					// 존재유무 확인용
					boolean isHear = false;
					
					// LITTAB에서 검색
					for(int il=0; il<LITTAB.size(); il++) {
						LITTAB l = LITTAB.get(il);
						
						// 동일한 literal 존재여부 확인
						if(c.getOperand2().equals(l.getLiteralName())) {
							isHear = true;
							break;
						}
					}

					if(!isHear) {
						// 없으면 LITTAB에 추가
						lt.setIdx((LITTAB.size()));
						lt.setLiteralName(c.getOperand2());					
						LITTAB.add(lt);
					}
				}
			} else {
				opcode = ot.getMNEMONIC(c.getOpcode());
				operand = c.getOperand2();
			}
			
			// Directive 인 경우
			if(formattype  == 0) {
				// 프로그램의 시작
				if(opcode.equals("START")) {
					// 시작주소 설정
					locctr = Integer.parseInt(operand);
					
					// Control Section 설정
					SYMTAB st = STAB.get(c.getLabel());
					csectIdx = st.getIdx();
				} else if(opcode.equals("CSECT")) {
					// location counter 초기화
					locctr = 0;
					c.setAddress(locctr);
					
					// Control Section 설정
					SYMTAB st = STAB.get(c.getLabel());
					csectIdx = st.getIdx();
					
//				} else if(opcode.equals("EXTDEF") || opcode.equals("EXTREF")) {
					// ESTAB에 일괄 저장
					// 현재로서는 불필요한 단계
					
//					String[] operandAr = operand.split(",");
//					
//					for(int esi = 0; esi<operandAr.length; esi++) {
//					
//					}
					
				} else if(opcode.equals("BYTE") || opcode.equals("WORD")) {
					// CodeLineDTO 에 주소값 저장
					c.setAddress(locctr);

					if(operand.matches(".*\\'.*")) {
						// case X'F1' 인 경우 해당 문자 값 직접 할당

						// objectCode 추가
						ObjectCode objectCode = new ObjectCode();
						objectCode.setCsectIdx(csectIdx);
						objectCode.setAddress(locctr);
						
						String[] splitStr = operand.split("'");

						// X 인 경우는 hex 값 직접 할당 하지만, C 인 경우는 해당 ASCII 값을 추가
						if(splitStr[0].equals("X")) {
							// 해당 Directive 와 hex 문자 값 길이 의 곱만큼 location counter 증가
							// ex: F1 -> F1(16진수), 길이 1byte(4bit * 2) 이므로 /2 처리
							locctr += (ot.getOffset(c.getOpcode()) * splitStr[1].length()/2);
							objectCode.setObjectCode(splitStr[1]);					
						} else if(splitStr[0].equals("C")) {
							// 해당 ASCII 값 추가 및 해당 길이 만큼 location counter 증가
							// ex: EOF -> 454F46(16진수), 길이 3bytes
							locctr += (ot.getOffset(c.getOpcode()) * splitStr[1].length());
							
							String splitObjectCodeStr = "";
//							System.out.println(Integer.toHexString(Integer.parseInt(splitStr[1])));
							for(int si = 0; si<splitStr[1].length(); si++) {
								splitObjectCodeStr += Integer.toHexString(Integer.parseInt(splitStr[1].substring(si,si+1), 16));
							}
							objectCode.setObjectCode(splitObjectCodeStr);
						}
						
						OCODE.add(objectCode);
						
					} else if(operand.matches(".*-.*")) {
						// case BUFEND-BUFFER
						// objectCode 에 대한 고려..	
						// OPERAND가 EXTREF에 포함 되어 있으면 objectCode는 '000000' 처리
						// operand에 -, + 포함된 경우
						// 보완사항: 모든 경우의 수에 대한 계산은 추후에..
						
						String[] operandStrAr = operand.split("-");
						
						// EXTREF 여부 확인
						boolean isExt = false;
						
						// 하나라도 EXTREF 에 있으면 objectCode는 알 수 없다
						for(int sti = 0; sti<operandStrAr.length; sti++) {
							for(int esi = 0; esi<ESTAB.size(); esi++) {
								ESTAB est = ESTAB.get(esi);
								
								if(operandStrAr[sti].equals(est.getSymbol())) {
									// objectCode 추가
									ObjectCode objectCode = new ObjectCode();
									objectCode.setCsectIdx(csectIdx);
									objectCode.setAddress(locctr);
									objectCode.setObjectCode("000000");
									OCODE.add(objectCode);
									isExt = true;
									break;
								}
							}
							
							// 하나라도 있으면 for 문 종료
							if(isExt)
								break;
						}
					}
					
				} else if(opcode.equals("RESB") || opcode.equals("RESW")) {
					// CodeLineDTO 에 주소값 저장
					c.setAddress(locctr);
					locctr += (ot.getOffset(c.getOpcode()) * Integer.parseInt(operand));
					
				} else if(opcode.equals("LTORG")) { 
					// LITTAB
					// LITTAB 주소할당 및 immediate line 추가
					CodeLineDTO addCldto = new CodeLineDTO();
					
					for(int i1=0; i1<LITTAB.size(); i1++) {
						LITTAB lit = LITTAB.get(i1);

						// objectCode 추가
						ObjectCode objectCode = new ObjectCode();
						objectCode.setCsectIdx(csectIdx);
						objectCode.setAddress(locctr);
						objectCode.setObjectCode(lit.getOperandValue());
						OCODE.add(objectCode);
						
						// LITTAB address 추가
						lit.setAddress(locctr);
						
						// 분리예정
						// immediate line 추가
						addCldto.setAddress(locctr);
						addCldto.setLabel(0x2A); // * 으로 설정
						addCldto.setOpcode(lit.getIdx()); // LITTAB 의 idx 값으로 설정
						addCldto.setLineString("");
						
						// location counter literal 길이만큼 증가
						locctr += lit.getLength();
						
					}

					CLDTO.setElementAt(c, i);
					
					// 기존 vector에 추가 삽입
					CLDTO.insertElementAt(addCldto, ++i);
					
					// 다음 라인으로 이동
					continue;
					
				} else if(opcode.equals("EQU")) {
					
					// operand에 -, + 포함된 경우
					// 보완사항: 모든 경우의 수에 대한 계산은 추후에..
					if(operand.matches(".-.|.+.")) {
						// Absolte Expression 계산
						symbolType = 'A';
						String[] operandStrAr = operand.split("-");
						int operandInt1=0, operandInt2=0;
						
						for(int si = 0; si<STAB.size(); si++) {
							SYMTAB st = STAB.get(si);
							if(st.getSYMBOL().equals(operandStrAr[0])) {
								operandInt1 = st.getVALUE();
							} else if(st.getSYMBOL().equals(operandStrAr[1])) {
								operandInt2 = st.getVALUE();
							}
						}
						
						// 상대주소 값이 아닌 절대주소 값을 입력
						c.setAddress(operandInt1-operandInt2);
						
					} else {
						// CodeLineDTO 에 location counter 저장
						c.setAddress(locctr);
					}
					
				} else if(opcode.equals("END")) {	// 프로그램의 끝
					// LITTAB 에서 주소할당 안된 literal 모두 주소할당
					CodeLineDTO addCldto = new CodeLineDTO();
					
					for(int i1=0; i1<LITTAB.size(); i1++) {
						LITTAB lit = LITTAB.get(i1);

						// 분리예정
						// 할당된 주소 없을 경우
						if(lit.getAddress() == 0) {
							// objectCode 추가
							ObjectCode objectCode = new ObjectCode();
							objectCode.setCsectIdx(csectIdx);
							objectCode.setAddress(locctr);
							objectCode.setObjectCode(lit.getOperandValue());
							OCODE.add(objectCode);
							
							// immediate line 추가
							addCldto.setAddress(locctr);
							addCldto.setLabel(0x2A); // * 으로 설정
							addCldto.setOpcode(lit.getIdx()); // LITTAB 의 idx 값으로 설정
							addCldto.setLineString("");
	
							// literal 에 주소 추가
							lit.setAddress(locctr);
							
							locctr += lit.getLength();
						}
						
					}

					// 현재 element를 추가된 정보로 변경한다
					CLDTO.setElementAt(c, i);
					
					// 기존 vector에 다음줄에 추가 삽입
					CLDTO.add(++i, addCldto);
					
					break;
				}
			} else {
				////////////////////////////////////////////
				// Instruction 인 경우
				
				// CodeLineDTO 에 주소값 저장
				c.setAddress(locctr);
				
				// formattype 길이만큼 location counter 증가
				locctr += formattype;
			}

			// 현재 element를 추가된 정보로 변경한다
			CLDTO.setElementAt(c, i);
			
			// Label이 있는 경우 SYMTAB 에 해당 주소 입력
			if(c.getLabel() != 0) {				
				s.setTYPE(symbolType);
				s.setVALUE(c.getAddress());
			}
		}	
		
		////////////////////////////////////////////////////////
		// Pass2-objectCode 생성
		// location counter, control section 초기화
		locctr = 0;
		csectIdx = 0;
		
		for(int i=0; i<vector.size(); i++) {
			CodeLineDTO c = vector.get(i);
			// OPCODE 검색을 위한 instance 생성
			OPTAB ot = new OPTAB();
			
			// opcode 가져오기
			opcode = ot.getMNEMONIC(c.getOpcode());
			
			// 주석인 경우 다음 라인으로 넘어간다
			if(c.getLineString().matches("^\\..+"))
				continue;

			// 명령어 형식 설정
			formattype = ot.getFormatType(c.getOpcode());
			
			// 4형식 설정
			if(c.getOperand1() != null && c.getOperand1().equals("+"))
				formattype = 4;
			
			// '*' 이 아닌 경우-literal에 의해 추가 되었으므로 SYMTAB 인덱스를 초과한다
			if(c.getLabel() != 0x2A) {
				
				// symbol 가져오기
				SYMTAB st = STAB.get(c.getLabel());
				String symbol = st.getSYMBOL();	
				
				// opcode 가 "START" 또는 "CSECT" 인 경우만 CSECT 시작
				// Control Section 설정
				if(opcode.equals("START") || opcode.equals("CSECT"))
					csectIdx = st.getIdx();
			}
			
			// Directive 가 아닌 경우, 즉 Instuction 인 경우
			if(formattype != 0) {
				
				// literal label 은 제외
				// '*' == 0x2A(ASCII code-16진수)
				if(c.getOperand1() != null && c.getLabel() == 0x2A)
					continue;
				
				String objectCodeStr = "";
				
				// formattype에 따른 ObjectCodeByte 배열 길이 설정
				ObjectCode objectCode = new ObjectCode(formattype);

				// formattype: 1~4
				// opcode-instruction 에 대한 packing 후 [0] 에 입력
				objectCode.setObjectCodeByte(0, objectCode.packing(c.getOpcode()));
				
				switch(formattype) {
				case 2:
					objectCodeStr = Integer.toHexString(c.getOpcode()).toUpperCase();
					
					// operand 의 register number 가져오기
					for(int ri=0; ri<ot.getOperandNumber(c.getOpcode()); ri++) {
						if(c.getOperand2().matches(".+\\,.+"))
							// operand register 가 2개인 경우
							objectCodeStr += ot.getRegisterNumber(c.getOperand2().split(",")[ri]);
						else
							// operand register 가 1개인 경우
							objectCodeStr += ot.getRegisterNumber(c.getOperand2())+"0";
					}
					
					break;
					
				case 3: case 4:
					// formattype: 3~4
					// 00ni 에 대한 bit 설정 후 [0] 에 입력
					objectCode.setObjectCodeByte(0, objectCode.setAddressingMode(c.getOperand1()));
					
					// format type: 3~4
					// xbpe 에 대한 bit 설정 후 [1] 에 입력
					// operand 있는 경우만
					if(ot.getOperandNumber(c.getOpcode()) > 0) {
						objectCode.setObjectCodeByte(1, objectCode.setRelative(c.getOperand1(), c.getOperand2()));

						// simple addressing(0011) && 3형식 인 경우
						// displacement 계산
						if(c.getOperand2() != null && (objectCode.getObjectCodeByte()[0] & 3) == 3 && formattype == 3) {
							int sVar = 0;
	
							// literal 은 LITTAB에서 찾고, 나머지는 SYMTAB 에서 찾는다
							if(c.getOperand1().equals("=")) {
								for(int li = 0; li<LITTAB.size(); li++) {
									LITTAB lt = LITTAB.get(li);
									if(c.getOperand2().equals(lt.getLiteralName())) {
										sVar = lt.getAddress();
										break;
									}
								}
								
							} else {
								for(int si=0; si<STAB.size(); si++) {
									SYMTAB stmp = STAB.get(si);
									if(c.getOperand2().equals(stmp.getSYMBOL()) && stmp.getTYPE() == 'R') {
										// operand2 symbol의 address 찾기
										// relative type 만
										sVar = stmp.getVALUE();
										break;
									}
								}
							}
							
							// PC relative 연산
							locctr = c.getAddress() + ot.getFormatType(c.getOpcode());
							objectCode.setDisplacement(sVar - locctr);
							
						} else if(c.getOperand2() != null && (objectCode.getObjectCodeByte()[0] & 1) == 1 
								&& formattype == 3 && ot.getOperandNumber(c.getOpcode()) != 0) {
							// immediate addressing(0001) && 3형식 인 경우
							objectCode.setDisplacement(Integer.parseInt(c.getOperand2()));
						}
					}
					
					// format type: 4
					// address 연산 
					// Extended 인 경우 
					if(c.getOperand1() != null && c.getOperand1().equals("+") && formattype == 4) {
						objectCode.setObjectCodeByte(1, objectCode.getObjectCodeByte()[1]);
						objectCode.setObjectCodeByte(2, (char)0);
						objectCode.setObjectCodeByte(3, (char)0);
					}
					
					for(int si=0; si<formattype; si++) {
						String hexStr = Integer.toHexString(objectCode.getObjectCodeByte()[si]).toUpperCase();
						
						// 길이가 1인 경우 앞에 "0"을 붙여준다
						// "0" -> "00"
						if(hexStr.length() < 2)
							objectCodeStr += "0";
						objectCodeStr += hexStr;
					}
					
					break;
				}
				
				// objectCode 추가
				objectCode.setCsectIdx(csectIdx);
				objectCode.setAddress(c.getAddress());
				objectCode.setObjectCode(objectCodeStr);
				OCODE.add(objectCode);
				
			}
		}

		// ESTAB 과 SYMTAB 을 mapping
		for(int ei=0; ei<ESTAB.size(); ei++) {
			ESTAB es = ESTAB.get(ei);
			
			for(int si=0; si< STAB.size(); si++) {
				SYMTAB st = STAB.get(si);

				if(STAB.get(si).getSYMBOL().equals(es.getSymbol())) {
					es.setSymbolIdx(st.getIdx());
					es.setAddress(st.getVALUE());
					break;
				}
			}
		}
		
//		pv.PrintCLDTO(CLDTO);
		pv.PrintImmediate(CLDTO, STAB, LITTAB, OCODE);
//		pv.PrintESTAB(ESTAB);
//		pv.PrintLITTAB(LITTAB);
//		pv.PrintSTAB(STAB);
		pv.PrintObjectCode(OCODE);
		
		// 해당 vertor를 반환한다.
		return CLDTO;

	}

	// 1번 함수를 통해 얻어낸 Vector 값 또는 2번에서 얻어낸 결과 값을 이용하여 최종 Object Program 으로 변환
	@Override
	public Vector<CodeLineDTO> changeObjectCode(Vector<CodeLineDTO> vector) {
		Vector<CodeLineDTO> CLDTO = vector;

		OPTAB ot = new OPTAB();
	
		// control section 설정
		int csectIdx = 0;
		String prevAddress = "";
		String prevObjectCode = "";
		
		// objectCode 설정
		String objectCode = "";

		Vector<ObjectProgram> OPV = new Vector<ObjectProgram>();
		OPV.clear();
		
		ObjectProgram op = new ObjectProgram();
		op.init();
		
		///////////////////////////
		
		for(int i=0; i<vector.size(); i++) {
			CodeLineDTO c = vector.get(i);
			
			// 주석은 건너뛰기
			if(c.getLineString().matches("^\\..+"))
				continue;
			
			// address 설정
			String address = Integer.toHexString(c.getAddress()).toUpperCase();
			
			// label 설정
			String label = "";
			if(c.getLabel() != 0x2A)
				label = STAB.get(c.getLabel()).getSYMBOL();
			
			// opcode 설정
			String opcode = ot.getMNEMONIC(c.getOpcode());
			
			// operand 설정
			String operand = c.getOperand2();
			
			// "+": opcode
			// "#": operand
			// "@": operand
			// "=": operand | label=="*" -> opcode
			if(c.getOperand1() != null && c.getOperand1().equals("+")) {
				opcode = c.getOperand1() + opcode;
			} else if(c.getOperand1() != null && c.getOperand1().equals("#") || c.getOperand1() != null && c.getOperand1().equals("@")) {
				operand = c.getOperand1() + operand;
			} else if(c.getOperand1() != null && c.getOperand1().equals("=")) {
				if(label.equals("*")) {
					opcode = c.getOperand1() + opcode;
				} else {
					operand = c.getOperand1() + operand;
				}
			}
			
			// control section 설정
			if(opcode.equals("START") || opcode.equals("CSECT")) {
				csectIdx = c.getLabel();
			}

			// objectCode 설정
			objectCode = "";
			for(int oi = 0; oi<OCODE.size(); oi++) {
				ObjectCode oc = OCODE.get(oi);
				
				// CSECT 와 Address 가 동일한 경우에 objectCode 설정
				if(oc.getCsectIdx() == csectIdx && oc.getAddress() == c.getAddress()) {
					objectCode = oc.getObjectCode();
					break;
				}
			}

			//////////////////////////////////////////			
			// for Header
			String pgName = "";
			String startAddress = "";
			String pgLength = "";
			
			// for EXTDEF
			String[] extDefAr;
			String extDef = "";
			
			// for EXTREF
			String[] extRefAr;
			String extRef = "";
			
						
			// "START" Header 설정
			if(opcode.equals("START")) {
				pgName = label;
				startAddress = objectCode;
				
				op.setPgName(pgName);
				op.setStartAddress(startAddress);
				
				// pgLength 구하기
				// SYMTAB 에서 relative type 중 가장 큰 value(address)를 찾는다
				int maxValue = 0;
				for(int si = 0; si<STAB.size(); si++) {
					SYMTAB ss = STAB.get(si);
					if(ss.getVALUE() > maxValue)
						maxValue = ss.getVALUE();
				}
				
				// 각 control section 의 길이  입력
				op.setPgLength(Integer.toHexString(maxValue));
				
				// control section 의 시작 주소 및 END record 입력
				op.setEndStr(startAddress);
				op.setEndStr("\n");
				
			} else if(opcode.equals("CSECT")) {
				
				// 각 control section 의 길이 및 END record 입력
				if(op.getPgLength().isEmpty()) {
					op.setPgLength(Integer.toHexString(Integer.parseInt(prevAddress, 16)+prevObjectCode.length()/2).toUpperCase());
					op.setEndStr("\n");
				}
				// Text record 를 종료하는 경우
				// CSECT 를 만나면
				op.setNewTextStr();
				
				// "CSECT" 나오면 기존꺼 추가하고 다시 시작
				OPV.add(op);
				
				
				op = new ObjectProgram();
				op.init();
				
				pgName = label;
				startAddress = "0";
				
				op.setPgName(pgName);
				op.setStartAddress(startAddress);
				
			} else if(opcode.equals("EXTDEF")) {
				// "EXTDEF" 설정
				extDefAr = operand.split(",");
				
				// ESTAB 에서 해당 symbol 별 주소 가져오기
				for(int di = 0; di<extDefAr.length; di++) {
					for(int ei=0; ei<ESTAB.size(); ei++) {
						ESTAB es = ESTAB.get(ei);
						
						if(extDefAr[di].equals(es.getSymbol())) {
							op.setExtDef(extDefAr[di]);
							op.setExtDefAddr(Integer.toHexString(es.getAddress()).toUpperCase());
							break;
						}
					}
				}
				
			} else if(opcode.equals("EXTREF")) {
				// "EXTREF" 설정
				extRefAr = operand.split(",");
				
				for(int ri = 0; ri<extRefAr.length; ri++) {
					op.setExtRef(extRefAr[ri]);
				}
				
			} else if(opcode.equals("LTORG") || opcode.equals("RESW") || opcode.equals("RESB")) {
				// Text record 를 추가하는 경우
				// LTORG, RESW, RESB 를 만나면
				op.setNewTextStr();

			} else {
				// 나머지 Instruction  의 경우 Text, Modification Record 로 설정
				
				// objectCode 가 있는 경우만
				if(!objectCode.equals("")) {

					// Text record 를 추가하는 경우
					// 1 라인의 최대 길이: 29(10) = 1D(16)
					
					// Text record 가 끝나는 경우
					// CSECT 를 만나면
					if(op.getTextStartAddr().isEmpty())
						op.setTextStartAddr(address);
					
					// text 추가
					op.setTextStr(objectCode, address);
					

					//////////////////////////////////////////////////////
					// Modification record 를 추가하는 경우
					// 수정해야 되는 주소 자릿 수
					// '0' 의 갯수 만큼
					int zeroCnt = objectCode.length() - objectCode.indexOf('0', 0);
					
					// 수정 시작 주소
					String modAddress = Integer.toHexString((c.getAddress() + (objectCode.length() - zeroCnt) / 2)).toUpperCase();
					
					// 수정 대상 operand
					String modOperand = c.getOperand2();
	
					// "+" 4형식인 경우
					// WORD, BYTE -> "+/-" : Absolute 연산인 경우
					if(c.getOperand1() != null && c.getOperand1().equals("+")) {
						

						// BUFFER,X 같은 경우
						if(modOperand.matches(".*\\,.*"))
							modOperand = modOperand.split(",")[0];
	
						// address+"address 에서 '0'의 갯수만큼"+"+/-operand2
						op.setModStr(modAddress, zeroCnt, objectCode, "+"+modOperand);
					
					} else if(opcode.equals("WORD") || opcode.equals("BYTE")) {
						// "+" 처리는 추후 보완사항
						if(modOperand.matches(".*-.*")) {
							String[] modOperandAr = modOperand.split("-");
							
							// address+"address 에서 '0'의 갯수만큼"+"+/-operand2
							op.setModStr(address, zeroCnt, objectCode, "+"+modOperandAr[0]);
							
							// 연산기호 변경
							for(int mi = 1; mi < modOperandAr.length; mi++) {
								op.setModStr(address, zeroCnt, objectCode, "-"+modOperandAr[mi]);
							}
						}	
					}
					// END OF MODIFICATION RECORD
					///////////////////////////////////
				}

				// 프로그램의 끝
				if((i == vector.size()-1)) {
					
					// 각 control section 의 길이 입력
					op.setPgLength(Integer.toHexString(Integer.parseInt(address, 16)+objectCode.length()/2).toUpperCase());
					
					// Text record 를 추가하는 경우
					// 프로그램 끝을 만나면
					op.setNewTextStr();
					
					// 종료
					OPV.add(op);
					
				}
			}
			
			// prev 변수 저장
			// 프로그램 길이를 알기 위함
			prevAddress = address;
			prevObjectCode = objectCode;

		}

		// 화면 출력
//		pv.PrintObjectProgram(OPV);
		
		// 파일 출력 // ---
		if(this.outputName.equals(""))
			pv.printObjectProg(OPV);
		else 
			pv.printObjectProg(OPV, outputName);
		
		
		// 해당 vertor를 반환한다.
		return CLDTO;

	}

}
