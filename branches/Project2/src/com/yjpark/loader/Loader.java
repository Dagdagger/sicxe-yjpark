package com.yjpark.loader;

import java.io.*;
import java.util.*;

import com.yjpark.assembler.*;


/**
 * H - Header
 * D - External Definition
 * R - External Reference
 * T - Text
 * M - Modification
 * E - End
 */
//enum RecordType {
//	H, D, R, T, M, E
//}

public class Loader {
	
	private String objectFile = "";
	
	// for instruction
	private Vector<String> instructionVector = new Vector<String>();
	
	// for EXTAB
	private int pgLengthTotal = 0;
	
	// for control section
	private Vector<Object> controlSection = new Vector<Object>();
	
	// for Header record
	private String pgName = "";
	private String startAddress = "";
	private String pgLength = "";
	private Vector<String> HRECORD = new Vector<String>();
	
	// for External Definition record
	private Vector<String> EXTDEF = new Vector<String>();
	
	// for External Reference record
	private Vector<String> EXTREF = new Vector<String>();

	// for External Reference record
	private Vector<String> TRECORD = new Vector<String>();	
	
	// for External Reference record
	private Vector<String> MRECORD = new Vector<String>();
	
	// for End record
	private String addressFirstInstruction = "";
	
	// 기본 생성자
	public Loader() {}
	
	public Loader(String objectFile) {
		this.objectFile = objectFile;
	}

	public Vector<Object> getControlSection() {
		return controlSection;
	}
	
	public String getPgName() {
		return pgName;
	}
	
	public String getStartAddress() {
		return startAddress;
	}
	
	public String getPgLength() {
		return pgLength;
	}

	public Vector<String> getHRECORD() {
		return HRECORD;
	}
	
	public Vector<String> getEXTDEF() {
		return EXTDEF;
	}

	public Vector<String> getEXTREF() {
		return EXTREF;
	}
	
	public Vector<String> getTRECORD() {
		return TRECORD;
	}	
	
	public Vector<String> getMRECORD() {
		return MRECORD;
	}
	
	public String getAddressFirstInstruction() {
		return addressFirstInstruction;
	}
	
	
	// ESTAB for modification
	Vector<ESTAB> ESTAB = new Vector<ESTAB>();
	
	/**
	 * 1~6 - Program Name
	 * 7~12 - Starting Address
	 * 13~19 - Length of Object Program
	 */	
	public void doHeader(String line) {
	
		if(this.pgName.equals("")) {
			this.pgName = line.substring(1, 6).trim();
			this.startAddress = line.substring(6, 13).trim();
			this.pgLength = line.substring(13).trim();
		}
		
		this.HRECORD.add(line.substring(1, 6).trim());
		this.HRECORD.add(line.substring(6, 13).trim());
		this.HRECORD.add(line.substring(13).trim());
		
		// control section 의 시작
		ESTAB et = new ESTAB();
		et.setContolSection(this.HRECORD.get(0));
		et.setAddress(Integer.parseInt(this.HRECORD.get(1), 16) + this.pgLengthTotal);
		et.setLength(Integer.parseInt(this.HRECORD.get(2), 16));
		this.ESTAB.add(et);
		
		this.pgLengthTotal += et.getLength();
	}
	
	/**
	 * 1~6 - SymbolName1
	 * 7~12 - SymbolAddress1
	 * ...
	 */
	public void doDefinition(String line) {
		for(int i = 1; i < line.length(); i+=6) {
			this.EXTDEF.add(line.substring(1*i, 1*i+6).trim());
		}
		
		for(int didx = 0; didx < EXTDEF.size(); didx++) {
			
			// control section 에 추가
			ESTAB et = new ESTAB();
			et.setContolSection(this.HRECORD.get(0));
			et.setSymbol(EXTDEF.get(didx));
			et.setAddress(Integer.parseInt(this.EXTDEF.get(++didx), 16));
			
			this.ESTAB.add(et);
		}
	}
	
	/**
	 * 1~6 - SymbolName1
	 * 7~12 - SymbolName2
	 * ...
	 */
	public void doReference(String line) {
		for(int i = 1; i < line.length(); i+=6) {
			this.EXTREF.add(line.substring(1*i, 1*i+6).trim());
		}	
	}

	/**
	 * 1~6 - Record Start Address
	 * 7~8 - Record Length
	 * 9~68(Maximum) - Instruction
	 */
	public void doText(String line) {
		for(int i = 9; i < line.length(); i+=2) {
			// 2자리씩 저장
			this.TRECORD.add(line.substring(1*i, 1*i+2).trim());
		}	
	}
		
	/**
	 * 1~6 - Modify Target Address
	 * 7~8 - Modification Count
	 * 9 - operation "+/-" 
	 * 10~15 - SymbolName
	 * ...
	 */
	public void doModification(String line) {
		for(int i = 1; i < line.length(); i+=15) {
			this.MRECORD.add(line.substring(1*i, 1*i+6).trim());
			this.MRECORD.add(line.substring(1*i+6, 1*i+8).trim());
			this.MRECORD.add(line.substring(1*i+8, 1*i+9).trim());
			this.MRECORD.add(line.substring(1*i+9).trim());
		}
	}

	/**
	 * 1~6 - First Instruction Address
	 */
	public void doEnd(String line) {
		this.addressFirstInstruction = line.substring(1, 7).trim();
	}
	
	
	/**
	 *  Move and Store Vector HRECORD, EXTDEF, EXTREF, TRECORD, MRECORD (except END)
	 *  clear Vector
	 */
	public void seperateContolSection() {
		controlSection.add(new String("<Start>"));
		
		// store
		controlSection.add(this.HRECORD.clone());
		controlSection.add(this.EXTDEF.clone());
		controlSection.add(this.EXTREF.clone());
		controlSection.add(this.TRECORD.clone());
		controlSection.add(this.MRECORD.clone());
		
		controlSection.add(new String("<End>"));
		
		// clear
		this.HRECORD.clear();
		this.EXTDEF.clear();
		this.EXTREF.clear();
		this.TRECORD.clear();
		this.MRECORD.clear();
	}

	// start point
	public void init() {
		
		// ESTAB 초기화
		this.ESTAB.clear();
		
		// 파일을 읽기 위한 변수 선언
		File ifp = null;
		String obFile = "";
		String objectFile = this.objectFile;
		
		// 입력파일을 실행인자로 입력 받거나 없으면 기본 파일명으로 설정
		if (objectFile.length() < 0) {
			obFile = "output.txt";
		} else {
			obFile = objectFile;
		}
		
		// 파일을 읽어들이고, 해당 파일이 없으면 오류 메세지 출력
		try {
			ifp = new File(obFile);
		} catch (Exception e) {
			System.out.println(obFile+" 파일이 없습니다.");
			e.printStackTrace();
			System.exit(1);
		}
		
		// 입력 파일을 읽어들이기 위한 변수
		BufferedReader reader = null;
		String line = "";
		
		// 파라미터로 입력받은 파일명의 파일을 읽어들인다.
		try {
			reader = new BufferedReader(new FileReader(obFile));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {					
			// 입력 파일을 라인단위로 읽는다.
			while((line = reader.readLine()) != null) {
				char[] lineArray = line.toCharArray();
				if(lineArray.length <= 0) continue;
				
				char record = lineArray[0];
				
				if(record == 'H') { // Header record
					doHeader(line);
				} else if(record == 'D') {	// External Definition record
					doDefinition(line);
				} else if(record == 'R') {	// External Reference record
					doReference(line);
				} else if(record == 'T') {	// Text record
					doText(line);
				} else if(record == 'M') {	// Modification record
					doModification(line);
				} else if(record == 'E') {	// End record
					// addressFirstInstruction 이 없는 경우 즉, 첫번째만 처리 / 나머지는 control section
					if(this.addressFirstInstruction.equals(""))
						doEnd(line);
					
					// seperate by control section
					seperateContolSection();
				}
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

	}
	
	
	public void loading() {

		// SICXE Maximum memory size: 2^20
		final int maxMemorySize = (int) Math.pow(2,20);
		char[] instructionMemory = new char[maxMemorySize];
		
		ObjectCode oc = new ObjectCode();
		
		System.out.println("instMemory:"+Integer.toHexString(System.identityHashCode(instructionMemory)).toString());
		
		// set controlSection & instruction
		int instructionMemorySize = 0;
		for(int idx = 0; idx < controlSection.size(); idx++) {
			if(controlSection.get(idx).equals("<Start>")) {
				
				Vector<String> HRECORD = (Vector<String>) controlSection.get(++idx);
//				System.out.println(HRECORD.toString());
				
				Vector<String> EXTDEF = (Vector<String>) controlSection.get(++idx);
//				System.out.println(EXTDEF.toString());
				
				Vector<String> EXTREF = (Vector<String>) controlSection.get(++idx);
//				System.out.println(EXTREF.toString());
				
				Vector<String> TRECORD = (Vector<String>) controlSection.get(++idx);
//				System.out.println(TRECORD.toString());
				
				// packing
				for(int tidx = 0; tidx < TRECORD.size(); tidx++) {
					instructionMemory[instructionMemorySize] = oc.packing(Integer.parseInt(TRECORD.get(tidx), 16));
					instructionMemorySize++;
				}
				
				Vector<String> MRECORD = (Vector<String>) controlSection.get(++idx);
//				System.out.println(MRECORD.toString());
				
				if(controlSection.get(idx).equals("<End>")) {
					break;
				}
			}
		}
		
		
		//
//		VectorPrint vp = new VectorPrint();
//		vp.PrintESTAB(this.ESTAB);
		
		
		// modification
		for(int idx = 0; idx < controlSection.size(); idx++) {
			
			if(idx % 7 == 5) { // MRECORD
				Vector<String> MRECORD = (Vector<String>) controlSection.get(idx);

				for(int m = 0; m < MRECORD.size(); m+=4) {
					int targetAddress = Integer.parseInt(MRECORD.get(m), 16);
					int mCount = Integer.parseInt(MRECORD.get(m+1), 16);
					String mOperand = MRECORD.get(m+2);
					String mOpcode = MRECORD.get(m+3);
					
					int realAddress = 0;
					
					// mOpcode 의 주소 가져오기
					// HRECORD - control section
					// EXTDEF - symbol
					for(int i = 0; i < this.ESTAB.size(); i++) {
						ESTAB et = this.ESTAB.get(i);

						if(mOpcode.equals(et.getContolSection())) {
							realAddress = et.getAddress();
							break;			
						} else if(mOpcode.equals(et.getSymbol())) {
							realAddress = et.getAddress();
							break;
						}
					}

					// extended address -> modification
					instructionMemory[targetAddress+2] |= (realAddress) & 255;
					instructionMemory[targetAddress+1] |= (realAddress >>= 8) & 255;
					instructionMemory[targetAddress] |= (realAddress >>= 8) & 255;

				}	
			}
		}
		
		
		final int OPCODE_MASK_BIT = 252;
		final int NI_MASK_BIT = 3;
		final int XBPE_MASK_BIT = 240;
		final int DISP_MASK_BIT = 15;
		
		for(int midx = 0; midx < instructionMemorySize; midx++) {
			
//			System.out.println(instructionMemory[midx]);
			
			String opcode = Integer.toHexString(instructionMemory[midx] & OPCODE_MASK_BIT);
			
			int niFlags = instructionMemory[midx] & NI_MASK_BIT;
			
			// midx + 1
			int flags = instructionMemory[midx+1] & XBPE_MASK_BIT;
			
			OPTAB opt = new OPTAB();
			
			// formattype 의 갯수 만큼 또는 extended 인 경우 4형식으로 처리
			int formattype = 0;
			if((flags >> 4) == 1) { // x flags == 1
				formattype = 4;				
			} else {
				formattype = opt.getFormatType(opcode);
			}
			
			// address value
			int disp = instructionMemory[midx+1] & DISP_MASK_BIT;
			for(int didx = midx+2; didx < (midx + formattype); didx++) {
				// 왼쪽으로 8bits SHIFTL
				disp <<= 8;	
				
				// bit OR
				disp |= instructionMemory[didx];				
			}
			
			if(formattype == 4) {
//				disp |= ;
			}
			
			// instruction vector
			String instruction = "";
			for(int ii = midx; ii < (midx + formattype); ii++) {
				String inst = Integer.toHexString(instructionMemory[ii]).toUpperCase();
				if(inst.length() < 2) {
					inst = "0" + inst;
				}

				instruction += inst;
			}
			System.out.println("instruction:"+instruction);
			this.instructionVector.add(instruction);
		
//			System.out.println("disp:"+Integer.toHexString(disp));
			
			if(formattype != 0)
				midx += formattype-1;

			if(niFlags == 3) {
				// simple addressing
				// 11
				
			} else if(niFlags == 1) {
				// immediate addressing
				// 01
				
			} else if(niFlags == 2) {
				// indirect addressing
				// 10
				
			}
		}
		
//		System.out.println(Integer.toHexString(i)this.instructionVector.toString());
		
	}
}
