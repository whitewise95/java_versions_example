package java8_기능.dateAndTimeAPI_지원;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.*;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

/*
 * 기존에 사용하던 날짜 관련 클래스인 Date, Calendar 등은 가변 객체이므로 Thread-Safe하지 않다.
 * https://java119.tistory.com/52 참고
 * Java 8부터 LocalDate, LocalTime, LocalDateTime 등의 라이브러리를 통해 기존보다 훨씬 쉽게 날짜 관련 로직을 작성할 수 있게 되었다.
 * */
public class LocalDateTimeApi {
    public static void main(String[] args) throws InterruptedException {

        // Java 8 이전 - 오늘 날짜 구하기
        Calendar cal = Calendar.getInstance();
        String format = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String date = sdf.format(cal.getTime());
        System.out.println(date); //2022-09-25

        /**
         *  기본 사용법
         * */

        // Java 8 이후 - 오늘 날짜 구하기
        LocalDate now = LocalDate.now();
        System.out.println(now); // 2022-09-25

        LocalDateTime now2 = LocalDateTime.now();
        System.out.println(now2); // 2022-09-25T20:44:39.670

        //포맷
        String formatNow = now2.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 , hh:mm:ss"));
        System.out.println(formatNow); //2022년 09월 25일 , 08:43:49

        //필요한 날짜 정보로 LocalDate 객체 생성
        LocalDate christmas = LocalDate.of(2022, 12, 25);
        System.out.println(christmas); // 2022-12-25

        /**
         *  날자 비교
         * */
        LocalDateTime startDateTime = LocalDateTime.of(2022, 9, 25, 15, 10);
        LocalDateTime endDateTime = LocalDateTime.of(2022, 12, 25, 15, 10);

        // startDateTime이 endDateTime 보다 이전 날짜 인지 비교
        System.out.println(startDateTime.isBefore(endDateTime));
        // 결과 : true

        // 동일 날짜인지 비교
        System.out.println(startDateTime.isEqual(endDateTime));
        // 결과 : false

        // startDateTime이 endDateTime 보다 이후 날짜인지 비교
        System.out.println(startDateTime.isAfter(endDateTime));
        // 결과 : false

        /**
         *  시간 비교
         * */
        LocalTime startTime = LocalTime.of(15, 10);

        LocalTime endTime = LocalTime.of(17, 10);

        // startTime이 endTime 보다 이전 시간 인지 비교
        System.out.println(startTime.isBefore(endTime));
        // 결과 : true

        // startTime이 endTime 보다 이후 시간 인지 비교
        System.out.println(startTime.isAfter(endTime));
        // 결과 : false

        /**
         *  날짜 차이 계산
         * */
        LocalDate startDate = LocalDate.of(2022, 10, 01);
        LocalDate endDate = LocalDate.of(2022, 12, 25);

        Period period = Period.between(startDate, endDate);

        System.out.println(period.getYears());   // 년도차이   0년
        System.out.println(period.getMonths());  // 개월 차이  2개월
        System.out.println(period.getDays());   // 일 차이  24일

        /**
         *  전체 시간을 기준으로 차이 계산하기
         * */
        LocalDate startDate2 = LocalDate.of(2022, 10, 24);
        LocalDate endDate2 = LocalDate.of(2022, 12, 25);

        System.out.println(ChronoUnit.DAYS.between(startDate2, endDate2));
        // 2개월 2일  총 62

        /**
         * 해당 월에 마지막 날짜 찾기
         * */
        String targetDate = "2022-09-25";

        YearMonth targetYearMonth = YearMonth.from(LocalDate.parse(targetDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        //해당 월의 일 수(int)
        System.out.println(targetYearMonth.lengthOfMonth()); // 30

        //해당 월의 마지막 날(LocalDate)
        System.out.println(targetYearMonth.atEndOfMonth()); // 2022-09-30


        /**
         * 해당 주차의 날짜 찾기
         * */
        final long calendarWeek = 50; //34주차 입력
        LocalDate desiredDate = LocalDate.now()
                .with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, calendarWeek)
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)); // = 해당 주차에 월요일
        System.out.println(desiredDate);
        // 2022-12-12


        /**
         *  요일 구하기
         * */
        // 1. LocalDate 생성
        LocalDate locaDate = LocalDate.of(2021, 12, 25);
        System.out.println(date); // 2021-12-25

        // 2. DayOfWeek 객체 구하기
        DayOfWeek dayOfWeek = locaDate.getDayOfWeek();

        // 3. 텍스트 요일 구하기 (영문)
        System.out.println(dayOfWeek.getDisplayName(TextStyle.FULL, Locale.US));  // Saturday
        System.out.println(dayOfWeek.getDisplayName(TextStyle.NARROW, Locale.US));  // S
        System.out.println(dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.US));  // Sat

        // 4. 텍스트 요일 구하기 (한글)
        System.out.println(dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREAN));  // 토요일
        System.out.println(dayOfWeek.getDisplayName(TextStyle.NARROW, Locale.KOREAN));  // 토
        System.out.println(dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN));  // 토

        // 5. 텍스트 요일 구하기 (default)
        System.out.println(dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault()));  // 토요일
    }
}
