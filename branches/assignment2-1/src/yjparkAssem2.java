import java.io.*;
import java.util.*;

import sp.dtopack.*;

public class yjparkAssem2 {
	
	// 기본 생성자
	public yjparkAssem2() {}

	public static void main(String[] args) {
		
		// 파일을 읽기 위한 변수 선언
		File ifp = null, ofp = null;
		String infile = "";
		
		// 읽어 드린 파일을 Parsing 하기 위한 instance 생성
		ParseProcessor pp = new ParseProcessor();
		
		// Parsing 에 사용 하기 위한 vector 선언
		Vector<CodeLineDTO> CLDTO = new Vector<CodeLineDTO>();
		
		// 입력파일을 실행인자로 입력 받거나 없으면 기본 파일명으로 설정
		if (args.length != 1) {
			infile = "input.txt";
		} else {
			infile = args[0];
		}
		
		// 파일을 읽어드리고, 해당 파일이 없으면 오류 메세지 출력
		try {
			ifp = new File(infile);
		} catch (Exception e) {
			System.out.println(infile+" 파일이 없습니다.");
			e.printStackTrace();
			System.exit(1);
		}
		
		
		// parseData(): 입력받은 파일 내용을 모두 분석하여 Vector 타입에 담아 반환한다.
		// changeImmediateCode(): 1번 함수를 통해 얻어낸 Vector 값을 Immediate 형태로 변환
		// changeObjectCode(): 1번 함수를 통해 얻어낸 Vector 값 또는 2번에서 얻어낸 결과 값을 이용하여
		//                     최종 ObjectCode로 변환하여 vector 반환
//		CLDTO = pp.parseData(ifp);
		CLDTO = pp.changeObjectCode(pp.changeImmediateCode(pp.parseData(ifp)));
		
//		System.out.println(0x00);
//		System.out.println(0x0);
		
		// 반환된 object program 을 출력 파일에 기록
		// 파일 출력을 실패하면 오류 메세지 출력
		try {
			// 출력 파일 열기
			ofp = new File("output.txt");
			
			// 파일 출력을 위한 instance 생성
			VectorPrint vp = new VectorPrint();
			
			// 파일 출력을 위한 인자 값 전달
//			vp.printObjectProg(ofp, CLDTO);
			
			// 화면 출력용
			vp.PrintCLDTO(CLDTO);
			
		} catch (Exception e) {
			System.out.println("파일출력을 실패했습니다.");
			e.printStackTrace();
		}
	}
}
