# 👉 일정관리 앱 과제 소개

* 프로젝트 명 : Java Spring Boot로 일정관리 앱을 구현해보자
    * 배운 내용을 바탕으로 일정 관리 앱를 구현하는 과제입니다.
    * Postman을 이용한 요청 및 응답으로 일정을 CRUD 및 DB에 저장할 수 있습니다.

* 개발 기간 : 2025.01.27 ~ 2025.02.03 (약 8일)

* github : <https://github.com/queenriwon/scheduleProject>
* blog : <https://queenriwon3.tistory.com/103>

* 개발 환경
	* environment : IntelliJ IDEA, git, github
	* development : JAVA JDK 17, Spring Boot 3.4.2, JDBC, MySQL, swagger 2.3.0


# 👉 Lv.0 - API 명세서, ERD 다이어그램
<detail>
  <summary>상세 API 명세서</summary>
  https://flaxen-swan-41e.notion.site/Lv-0-186b649ebbbd80f2a570ccd9ef43adb1

  <img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbXXRcj%2FbtsL5lqBqrN%2FDxKeXAU7zx1nhLKZDJuDeK%2Fimg.png">
  <img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FGSY81%2FbtsL4zXsuOp%2FnRWNJeYIFP0eHfaLtLhLCK%2Fimg.png">


</detail>



# 👉 구현 내용

### 필수 구현사항

* Lv.1
  * Scanner를 이용하여 햄버거 메뉴 출력 및 선택하기
* Lv.2
  * MenuItem 클래스 생성하여 이름, 가격, 설명의 필드를 갖습니다.
  * MenuItem 객체 생성을 통해 이름, 가격, 설명을 세팅합니다.
  * List를 선언하여 여러 MenuItem을 추가합니다.
  * main 함수에서 MenuItem 클래스를 활용하여 햄버거 메뉴를 출력합니다.
* Lv.3
  * Kiosk클래스를 생성하여 MenuItem을 관리하는 리스트, start함수로 관리합니다.
  * 키오스크 프로그램을 시작하는 메서드가 구현되여 사용자의 입력에 따라 메뉴를 선택하거나 프로그램을 종료합니다.
  * 유효하지 않은 입력에 대해 오류 메시지를 출력합니다.
  * List<MenuItem> menuItems 는 Kiosk 클래스 생성자를 통해 값을 할당합니다.
* Lv.4
  * Menu클래스를 만들어 MenuItem을 관리합니다.
* Lv.5
  * 캡슐화를 적용하여 MenuItem, Menu 그리고 Kiosk 클래스의 필드에 직접 접근하지 못하도록 설정합니다.

### 선택 구현사항

* Lv.6
  * 장바구니를 성성하고 관리하는 기능을 제공합니다.(구현)
  * 메뉴를 클릭하면 장바구니에 추가할 지 물어보고, 입력값에 따라 “추가”, “취소” 처리합니다.(구현)
  * enum을 사용하여 사용자 유형별 할인율을 관리합니다. (구현)
  * 제네릭을 활용하여 데이터 유연성을 높이고 재사용이 가능한 코드를 설계합니다. (구현)

  * 람다와 스트림을 활용해 장바구니 조회기능을 제공합니다. (구현)
  * 장바구니에 담긴 모든 항목과 금액 합계를 출력하고 주문을 진행합니다. 주문하기를 누르면 장바구니를 초기화합니다.(구현)
  * 사용자가 결제를 하기 전에 장바구니 출력 및 금액을 계산하는 기능을 제공합니다.(구현)


![Lv.123456](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FyRGMi%2FbtsLSPxb4Fu%2FLJgHf70WWqrAqTar8F4PC0%2Fimg.png)








# 👉 구현 핵심
* 예외 상황을 나누어 출력
* 사용자가 이용하기 편하도록 실수로 입력한 값에 따른 반복 처리
* 사용목적에 따른 클래스를 나누기
* 제네릭 메서드를 사용하여 코드의 중복을 줄이기
* 인덱스를 입력 받는 만큼 메서드마다 인덱스를 반환하거나 ArrayList.get(인덱스)를 이용




# 👉 부족하거나 아쉬운 점, 공부하고 싶은 내용
* 제네릭을 사용할 때 타입변수를 효과적으로 다뤄보기
* 더 많은 양의 메뉴데이터를 다뤄보기
* 각 레이어에 맞추어 구성해보기
* Indent depth 줄여보기








# 👉 클래스 다이어그램/ 플로우차트

(Lv.1,2,3 다이어그램은 생략)

* KioskProject Lv.4

![KioskProject Lv.4 Diagram](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FdaNdTO%2FbtsLRLWJt7K%2FT35j0zg4K6pxspBsYKYTUK%2Fimg.png)


* KioskProject Lv.6

![KioskProject Lv.6 Diagram1](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FdbtzRu%2FbtsLRhVERax%2FKqrYkZj8GKs0XFkpA1AvWk%2Fimg.png)

![KioskProject Lv.6 Diagram2](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fc7pJCH%2FbtsLShVhtTL%2FcnV8r8cBZxPGktkdRDeyZ0%2Fimg.png)







# 👉구현결과

* Lv.1,2,3 과제 결과

![KioskProject Lv.123 result](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FeNONKB%2FbtsLSTzAJnb%2F3PDMCUOdqlodUIxAk1DZ5K%2Fimg.png)



* Lv.4 과제 결과

![KioskProject Lv.4 result1](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FZidgG%2FbtsLSc7J2yK%2FiPpdKSEyCK8Zui6DHIuNPK%2Fimg.png)

![KioskProject Lv.4 result2](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2F83PhT%2FbtsLQTac6Bz%2FkwsH9jkyV1d9sRCLkl1ZQ0%2Fimg.png)






* Lv.6 구현 결과

![KioskProject Lv.6 result1](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FMPEtj%2FbtsLQA24VNT%2FMrkpuh2iONLeC1v6NwsNgK%2Fimg.png)

![KioskProject Lv.6 result2](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbySqk1%2FbtsLSrXE1zH%2FoCj4ki5E45VXzjkDrC9D5k%2Fimg.png)

![KioskProject Lv.6 result3](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FdqYyTj%2FbtsLQN8XpZN%2FtpAjXpfVGb0F7fjzkbHgsK%2Fimg.png)

![KioskProject Lv.6 result4](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbLLQSr%2FbtsLROskc3r%2FpxNEq06umbBeUs41OTYAAk%2Fimg.png)

![KioskProject Lv.6 result5](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fb7gwaj%2FbtsLSx4ARE9%2F7CqEhtsK2QS8IWM7RlUIDK%2Fimg.png)

![KioskProject Lv.6 result6](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2F97c6l%2FbtsLSdr1a0z%2FaODqoR9NzLnqPJqBwOshiK%2Fimg.png)

![KioskProject Lv.6 result7](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FEmem0%2FbtsLSLn2sCt%2FVSUB4jJf8WFY1XtJmjtXr0%2Fimg.png)

![KioskProject Lv.6 result8](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FClzO4%2FbtsLQEj5yVH%2FFxpYkgY7TSO1HNKrAfaTYK%2Fimg.png)

![KioskProject Lv.6 result9](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FcCnKuN%2FbtsLQ5uIJWx%2FrRze7P6JSCeRkWDyrcu0F1%2Fimg.png)

![KioskProject Lv.6 result10](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fl17ch%2FbtsLSAAgN0S%2FEMQQuKcrf0Ss1nXs6Lx380%2Fimg.png)






# 👉 기능소개

Java 기반 계산기 - 구현 코드 및 설명

<https://queenriwon3.tistory.com/89>





# 👉 트러블슈팅


250114 - Java 키오스크 구현과 트러블슈팅: 카테고리화, 문자열 출력, 제네릭 메서드, indexOf()의 한계

<https://queenriwon3.tistory.com/85>


250116 - Java 키오스크 구현과 트러블슈팅: 기능 확장 및 효율성 강화(enum, 람다, stream().forEach(), AtomicInteger, .idea폴더)

<https://queenriwon3.tistory.com/87>

