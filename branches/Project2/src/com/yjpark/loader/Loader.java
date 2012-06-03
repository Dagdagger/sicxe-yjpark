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
	
	// for Header record
	private String pgName = "";
	private String startAddress = "";
	private String pgLength = "";
	
	// for External Definition record
	private Vector<String> EXTDEF = new Vector<String>();
	
	// for External Reference record
	private Vector<String> EXTREF = new Vector<String>();

	// for External Reference record
	private Vector<String> TRECORD = new Vector<String>();	
	
	// for External Reference record
	private Vector<String> MRECORD = new Vector<String>();
	
	// for End record
	private String firstInstructionAddress = "";
	
	// 기본 생성자
	public Loader() {}
	
	public Loader(String objectFile) {
		this.objectFile = objectFile;
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
	
	public String getFirstInstructionAddress() {
		return firstInstructionAddress;
	}
	
	/**
	 * 1~6 - Program Name
	 * 7~12 - Starting Address
	 * 13~19 - Length of Object Program
	 */	
	public void doHeader(String line) {
		this.pgName = line.substring(1, 6).trim();
		this.startAddress = line.substring(7, 12).trim();
		this.pgLength = line.substring(13, 19).trim();	
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
		for(int i = 1; i < line.length(); i+=2) {
			// 2자리씩 저장
			this.TRECORD.add(line.substring(1*i, 1*i+1).trim());
		}	
	}

	/**
	 * Modify Text Record after doModification() 
	 */
	public void doModifyText() {
		for(int i = 0; i < MRECORD.size(); i+=4) {
			int modifyTargetAddress = Integer.parseInt(MRECORD.get(i), 16);
//			System.out.println(MRECORD.get(i)+"modifyTargetAddress:"+modifyTargetAddress);
			int modifyCount = Integer.parseInt(MRECORD.get(i+1));
//			System.out.println(MRECORD.get(i+1)+"modifyCount:"+modifyCount);
			String modifyOperation = MRECORD.get(i+2);
			String modifySymbol = MRECORD.get(i+3);
			
			// ?
			// 홀짝 확인하고, 그 갯수 만큼 수정
			if( (modifyCount % 2) == 1 ) { // 홀수
//				TRECORD.get(modifyTargetAddress+1);
			} else { // 짝수
				
			}
			
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
			this.MRECORD.add(line.substring(1*i+7, 1*i+8).trim());
			this.MRECORD.add(line.substring(1*i+9, 1*i+9).trim());
			this.MRECORD.add(line.substring(1*i+10).trim());
		}
	}

	/**
	 * 1~6 - First Instruction Address
	 */
	public void doEnd(String line) {
		this.firstInstructionAddress = line.substring(1, 6).trim();
	}

	// start point
	public void loading() {
		
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
					// program name 이 없는 경우 즉, 첫번째만 처리 / 나머지는 control section name
					if(this.pgName.equals(""))
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
					// firstInstructionAddress 이 없는 경우 즉, 첫번째만 처리 / 나머지는 control section
					if(this.firstInstructionAddress.equals(""))
						doEnd(line);
				}
			}
			
			// Modify Text Record
//			doModifyText();
			
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
}
