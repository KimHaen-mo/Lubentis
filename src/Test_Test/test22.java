package Test_Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Random;

public class test22 {

    public static void main(String[] args) {
        // 시작 날짜 선언
        LocalDate startDate = LocalDate.now();
        // 생성할 날짜 수 설정
        int ddaayy = 30;
        // 먹을 음식 배열 선언
        String[] food = { "아구찜", "찜닭", "미소라멘", "치킨가라아게", "돈카츠" };

        // 시작 날짜가 null이 아니라면 실행
        if (startDate != null) {
            Random random = new Random();

            for (int i = 0; i < ddaayy; i++) {
                // 주말(토요일 또는 일요일)인 경우 다음 평일로 넘어가기
                while (startDate.getDayOfWeek() == DayOfWeek.SATURDAY || startDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
                    startDate = startDate.plusDays(1);
                }

                System.out.println("Day: " + startDate + ", 먹을 5가지 음식:");

                for (int j = 0; j < 1; j++) {
                    // 음식 배열에서 랜덤으로 음식 선택하여 출력
                    int index = random.nextInt(food.length);
                    System.out.println(food[index]);
                }

                // 다음 날짜로 이동
                startDate = startDate.plusDays(1);
            }
        }
    }
}
