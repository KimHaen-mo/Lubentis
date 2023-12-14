package Test_Test;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class test {

	public static void main(String[] args) {
		// 날짜 출력하는 date 선언
		LocalDate date = LocalDate.now();
		// 출력할 비판자, 발표자, 쓰는자 이름 선언
		String[] str = { "황우준", "황의조", "황재성" };
		// 20일 선언
		int days = 20;

		// 만약 데이터가 null이 아니라면
		if (date != null) {
			for (int d = 0; d < days; d++) {
				// 토요일 이나 일요일이 있다면 건나 뛰고 다음 월요일부터 출력되게 함
				while (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
					// 토요일이나 일요일이 있으면 건너뛰고 더 함
					date = date.plusDays(1);
				}
				// 날짜 출력
				System.out.println("Day" + date);

				for (int i = 0; i < 3; i++) {
					// 배열의 인덱스를 현재 날짜의 인덱스로 설정하여 출력
					int index = (i + d) % str.length;
					System.out.println(" 순서 바꿔서 출력 : " + "{" + str[index] + "}");
				}
				// 다음 날짜로 이동
				date = date.plusDays(1);
			}
			// 사실상 이건 date가 null일 가능성이 거이 없기때문에 일단 말어놓긴함
		} else {
			System.out.println("date가 null입니다..");
		}
	}
}
