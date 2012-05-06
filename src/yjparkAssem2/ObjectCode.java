package yjparkAssem2;

// Object Code 생성 및 관리를 위한 Class
public class ObjectCode {

	int csectIdx;
	int address;
	String objectCode;
	char[] ObjectCodeByte;

	public int getCsectIdx() {
		return csectIdx;
	}
	
	public void setCsectIdx(int csectIdx) {
		this.csectIdx = csectIdx;
	}
	
	public int getAddress() {
		return address;
	}

	public void setAddress(int address) {
		this.address = address;
	}

	public String getObjectCode() {
		return objectCode;
	}

	public void setObjectCode(String objectCode) {
		this.objectCode = objectCode;
	}
	
	public char[] getObjectCodeByte() {
		return ObjectCodeByte;
	}

	public void setObjectCodeByte(int idx, char data) {
		this.ObjectCodeByte[idx] = data;
	}
	
	// 실제로는 String 2글자, 2bytes 만 처리
	// Hex String 2bytes -> 1byte 로 packing
	// 예시) B4(16진수) = 176(10진수) 의 경우
	// B -> 0000 1101 -> (shiftl) -> 1101 0000
	// 4 -> 0000 0100 -> (bit OR) -> 1101 0000 | 0000 0100 -> 1101 0100	
	public char packing(int opcode) {
		String opHex = Integer.toHexString(opcode);
		char packVar = 0;

		for(int i = 0; i<opHex.length();i++) {
			// 왼쪽으로 4bits SHIFTL
			packVar <<= 4;	

			// bit OR
			packVar |= (char)Integer.parseInt(opHex.substring(i, i+1), 16);
		}
//		System.out.println("=="+Integer.toBinaryString(packVar));
		return packVar;
	}
	
	// 00ni bit 설정
	public char setAddressingMode(String operand1) {
		// instruction 과 bit OR 하기 위함
		char bitVar = this.ObjectCodeByte[0];

		if(operand1 != null && operand1.equals("#")) {
			// immediate, 0001
			bitVar |= 1;
		} else if(operand1 != null && operand1.equals("@")) {
			// indirect, 0010
			bitVar |= 2;
		} else {
			// simple, 0011
			// extended 인 경우도 simple
			// literal 인 경우도 simple
			bitVar |= 3;
		}
		
		return bitVar;
	}
	
	// xbpe bit 설정
	// x-LOOP, ',X' 인 경우
	// b-BASE relative, 2순위
	// p-PC relative, 1순위
	// e-Extended, '+' 인 경우, 3순위
	public char setRelative(String operand1, String operand2) {
		char bitVar = 0;

		// Extended 4형식인 경우
		if(operand1 != null && operand1.equals("+")) {
			// Extended set on 'e' flag, 0001
			bitVar |= 1;
		} else if(operand2 != null && !(operand1 != null && operand1.equals("#"))) {
			// PC relative, 0010
			// immediate 제외
			// address 연산에 따른 base relative 처리 여부?
			// bitVar |= 4;			
			bitVar |= 2;
		}
		
		// LOOP 연산인 경우
		if(operand2 != null && operand2.matches(".+\\,X")) {
			// set on 'x' flag, 1000
			bitVar |= 8;
		}
		
		// 4bits SHIFTL 해서 반환
		return bitVar <<= 4;
	}
	
	// simple addressing & 3형식인 경우 displacement 설정
	// 1. OPERAND address - PC count address 결과 입력
	// 2. BASE register address - PC count address 결과 입력
	public void setDisplacement(int dispStr) {		
		// 8bits 가 넘는 경우 12bits 만 사용
		if(Integer.toBinaryString(dispStr).length() > 8) {
			dispStr &= 0xFFF; // 1111 1111 1111
			this.getObjectCodeByte()[1] |= (dispStr >> 8); // SHIFTR 한 결과 만 bit OR 연산
		}
		this.getObjectCodeByte()[2] |= (dispStr &= 0xFF);	
	}
	
	// hex format
	public String hexFormat(String orgHex, int formatLength) {
		String retStr = "";
		
//		String orgHex = Integer.toHexString(hex).toUpperCase();

		// 지정한 양식 길이에서 원본 문자열의 길이를 뺀만큼
		// '0'을 붙여준다.
		for(int i = 0; i<(formatLength-orgHex.length()); i++) {
			retStr = retStr.concat("0");
		}
		
		return retStr.concat(orgHex);	
	}
	
	public ObjectCode(){}
	
	// 명령어 형식에 따른 배열 크기 설정
	// 1~4 형식 -> 1~4 개의 배열
	public ObjectCode(int formattype) {
		ObjectCodeByte = new char[formattype];
		
	}
}
