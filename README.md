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

<br><br>


# 👉 Lv.0 - API 명세서, ERD 다이어그램


<details>
  <summary>상세 API 명세서</summary>
	
* 상세 API 명세서(설계단계)
  https://flaxen-swan-41e.notion.site/Lv-0-186b649ebbbd80f2a570ccd9ef43adb1
  <img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FbXXRcj%2FbtsL5lqBqrN%2FDxKeXAU7zx1nhLKZDJuDeK%2Fimg.png">

* 구현 후 API 명세서(`http://localhost:8080/swagger-ui/index.html`)
  <img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FGSY81%2FbtsL4zXsuOp%2FnRWNJeYIFP0eHfaLtLhLCK%2Fimg.png">
</details>

<details>
  <summary>ERD 다이어그램</summary>

  <img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FxYBKG%2FbtsL4HVfPsc%2FAAcCXU1yuVXs9mTbNqSkT0%2Fimg.png">
</details>


<br><br>



# 👉 트러블슈팅

250131 - Java Spring 일정관리 앱 구현과 트러블슈팅: API 명세서, JDBC 연결, DTO와 Entity, CRUD 구현, 동적쿼리 사용

<https://queenriwon3.tistory.com/101>

250203 - Java Spring 일정관리 앱 구현과 트러블슈팅: API 명세서, 멀티table과 Repository, Paging, @ExceptionHandler, 공통응답, 유효성 검사, Swagger

<https://queenriwon3.tistory.com/102>



<br><br>


# 👉 구현 내용

<details>
	<summary>필수 구현사항</summary>

* Lv.0
  * README.md에 API 명세서 작성하기
  * README.md에 ERD 다이어그램 작성하기
  * schedule.sql에 테이블 생성에 필요한 쿼리문 작성하기
* Lv.1
  * 일정 작성 구현(할일, 작성자명, 비밀번호, 작성/수정일 저장)
  * 전체 일정 조회(수정일과 작성자명에 따른 일정 목록 조회)
  * 선택 일정 조회
* Lv.2
  * 선택한 일정 수정(비밀번호를 통한 할일, 작성자명 수정)
  * 선택한 일정 삭제(비밀번호를 통해 삭제)
 
</details>

<details>
	<summary>선택 구현사항</summary>

* Lv.3 (구현)
  * 작성자에 대해 고유 식별자를 부여하여 동명이인을 구분
  * 작성자는 이름, 이메일, 등록일, 수정일 정보를 가지고 있음
  * 작성자 테이블을 생성하고 일정 테이블에 FK를 생성해 연관관계를 설정

* Lv.4 (구현)
  * 페이지네이션을 사용하여 등록된 일정 목록을 페이지 번호와 크기를 기준으로 모두 조회
* Lv.5 (구현)
  * 예외 상황에 대한 처리를 위해 HTTP 상태코드와 에러 메시지를 포함한 정보를 사용하여 예외를 관리
  * 수정 삭제시 요청때 보내는 비밀번호가 일치하지 않을 때 예외 발생
  * 잘못된 정보를 조회하려고 할 때 예외 발생
  * 이미 삭제된 정보를 조회하려고 할 때 예외 발생
* LV.6  (구현)
  * 할일 200자 이내, 필수값 처리
  * 비밀번호는 필수값 처리
  * 이메일 형식이 유효한지 확인
 
</details>


<br><br>




# 👉 구현 핵심
* 예외 상황을 나누어 출력
* 사용자가 이용하기 편하도록 실수로 입력한 값에 따른 반복 처리
* 사용목적에 따른 클래스를 나누기
* 제네릭 메서드를 사용하여 코드의 중복을 줄이기
* 인덱스를 입력 받는 만큼 메서드마다 인덱스를 반환하거나 ArrayList.get(인덱스)를 이용


<br><br>

# 👉 부족하거나 아쉬운 점, 공부하고 싶은 내용
* 제네릭을 사용할 때 타입변수를 효과적으로 다뤄보기
* 더 많은 양의 메뉴데이터를 다뤄보기
* 각 레이어에 맞추어 구성해보기
* Indent depth 줄여보기




<br><br>



# 👉 클래스 다이어그램
<img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FOFDXG%2FbtsL4Zg2eLI%2FXvCY8T5SFVT0VlLulhHqQk%2Fimg.png">




<br><br>




# 👉구현결과

<details>
	<summary>일정 작성(POST)</summary>
	<img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fr4RQc%2FbtsL4AhIv9q%2FMtHRnM4G1u24Phe57CV9H0%2Fimg.png">
	<img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FsnbFp%2FbtsL52YmTr4%2FWEMhs9MKUl5Jl7mkJLYdHk%2Fimg.png">
</details>

<details>
	<summary>조건 일정 조회(GET)</summary>
	<img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FVoIvO%2FbtsL6X9ECea%2FtMrT4DPLt5KBQ1UTEsqiF0%2Fimg.png">	
</details>

<details>
	<summary>전체 일정 조회(GET)</summary>
	<img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2F4Hiz2%2FbtsL6LhjKu6%2Fe3UdnA3zkXXqWJzEjWjkH0%2Fimg.png">
	<img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FdZn1zm%2FbtsL6Ln4et0%2FOtdZRf9CkjrTzYzVkn5x71%2Fimg.png">	
</details>

<details>
	<summary>단일 일정 조회(GET)</summary>
 	<img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FcJMQci%2FbtsL6qdrZ9s%2F5joydw2vVqenKyaPhwqny1%2Fimg.png">
	<img src="https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FFbr04%2FbtsL6t2gzrg%2FIEU5JqthxDGMkFXdfuOlPk%2Fimg.png">

</details>


<details>
	<summary>일정 수정(PATCH)</summary>

</details>

<details>
	<summary>일정 삭제(DELETE)</summary>

</details>



