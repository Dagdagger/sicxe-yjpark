package assemParsing;
/**
 * 향후 어셈블러로의 발전하기 위해 OPTAB을 별도의 클래스로 구분 작성
 * 고정되어 있는 자료들이므로 static table(2차원 배열) 이용
 * Filename: ParseTest.java
 */

import java.io.*;
import java.util.*;
import sp.interfacepack.XEToyAssemler1;

// 주어진 인터페이스를 상속한다.
public class ParseTest implements XEToyAssemler1 {

	private String inputFile, outputFile;
	File fp;
	
	// 입력되는 SIC 명령어 파일은 그 크기를 알 수 없으므로 Vector 클래스를 활용하여 SIC 명령어 파일을 읽어 들인다.
	static Vector<String> v = new Vector<String>();
	
	// default constuctor
	public ParseTest () {
		// 파일 경로 및 파일명 고정
		inputFile = "input.txt";
		outputFile = "output.txt";
	}
	
	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
		// 파일을 읽어드리고, 해당 파일이 없으면 오류 메세지 출력
		try {
			fp = new File(inputFile);
		} catch (Exception e) {
			System.out.println("input.txt 파일이 없습니다.");
			e.printStackTrace();
		}
	}

	@Override
	public void parseData(File arg0) {
		// TODO Auto-generated method stub
		
		try {
			// 파라미터로 입력받은 파일명의 파일을 읽어드린다.
			BufferedReader reader = new BufferedReader(new FileReader(arg0));
			
			String line = "";
			String opcode = "";
			
			// OPTAB Class에 접근하기 위한 인스턴스 생성
			OPTAB ot = new OPTAB();
			
			// 동적배열인 Vector 로의 입력을 위한 임시 배열
			String[] tmpLine = new String[4];
			
			// 실제 Parsing 및 OPCODE 검색을 위한 수행용 Vector
			Vector<String> tmpV = new Vector<String>();

			// 읽어드릴 파일 내용 길이에 따라 Vector에 가변길이로 저장
			while((line = reader.readLine()) != null) {
				v.add(line);
			}
			
			for(int i=0; i<v.size(); i++) {
				// 각 라인을 탭 기호로 구분
				// LABEL	OPCODE	OPERAND	COMMENT
				tmpLine = v.get(i).toString().split("\t");
				
				// 임시 사용 Vector 초기화 및 값 저장
				tmpV.clear();
				for(int t=0; t<4; t++) {
					if(t < tmpLine.length) {
						tmpV.add(tmpLine[t]);
					} else {
						tmpV.add("");
					}
				}

				// "." 주석은 Parsing 제외 처리
				if(!tmpV.get(0).toString().equals(".")) {
					// OPTAB 클래스에서 opcode 찾아오기
					opcode = ot.searchOPCODE(tmpV.get(1).toString());

					// OPTAB에서 direction에 대한 값을 지정하여 opcode와 구분 처리
					// opcode는 문자열 길이가 '2'고, direction은 문자열 길이가 '1'이다.
					if(opcode.length() == 2) {
						tmpV.set(3, opcode);
					} else tmpV.set(3, "");
				}
				
				// Vector 에서 원래의 내용만 가져오기 위해 치환하고
				// 찾은 OPCODE를 출력하기 위해 기존에 읽어드린 파일내용에 추가한다.
				v.setElementAt(tmpV.toString().replaceAll("\\[*\\]*", "").replaceAll("\\, ", "\t"), i);
			}
			
			// BufferedReader 를 종료한다.
			reader.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

	@Override
	public void printOPCODE() {
		// TODO Auto-generated method stub
		
		try {
			// 찾아낸 OPCODE를 output.txt 파일에 작성한다.
			BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
			
			for(int i=0; i<v.size(); i++) {
				System.out.println(v.get(i).toString());
				writer.write(v.get(i).toString());
				writer.newLine();
			}
			
			//BufferedWrite 를 종료한다.
			writer.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		// ParseTest 를 호출하기 위한 인스턴스를 생성한다.
//		ParseTest pt = new ParseTest();
//		
//		// SIC 명령어 파일을 읽어 들이기 위한 초기화 작업을 수행한다.
//		pt.initialize();
//		
//		// 읽어드린 내용을 Vector 클래스를 활용하여 Parsing 한다.
//		pt.parseData(fp);
//		
//		// Parsing 된 내용을 정해진 파일명으로 출력한다.
//		pt.printOPCODE();
	}
}
