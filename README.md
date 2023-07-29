# 프로젝트 설명
 Open Weather Map api를 이용해 날씨 정보 사용 및 일기를 작성할 수 있는 클론 코딩 및 추가 기능 구현 프로젝트

 <br>
 <br>

 # 목표
 날씨, 일기를 작성/조회/수정/삭제 하는 백엔드 구현
 - 테스트 코드 작성
 - 외부 API의 데이터 활용하기
 - JPA 방식으로 MySQL 사용하기
 - DB와 관련된 함수들을 트랜잭션 처리
 - 매일 새벽 1시에 날씨 데이터를 외부 API 에서 받아다 DB에 저장해두는 로직을 구현
 - logback 을 이용하여 프로젝트에 로그 남기기
 - ExceptionHandler 을 이용한 예외처리
 - swagger 을 이용하여 API documentation 작성
 <br>
 <br>
 # 최종 구현 API 리스트
- POST : /create/diary
  -  date parameter 로 받아주세요. (date 형식 : yyyy-MM-dd)
  -  text parameter 로 일기 글을 받아주세요.
  -  외부 API 에서 받아온 날씨 데이터와 함께 DB에 저장해주세요.
    ```java
    @ApiOperation(value = "일기 텍스트와 날씨를 이용해서 DB에 일기 저장", notes = "자세한 내용은 아래 사이트를 참고하세요")
    @PostMapping("/create/diary")
    void createDairy(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestBody String text
    ) {
        diaryService.createDiary(date, text);
    }    
    ```
     <br>
    
- GET : /read/diary
  - date parameter 로 조회할 날짜를 받아주세요.
  - 해당 날짜의 일기를 List 형태로 반환해주세요.
    ```java
    @ApiOperation("선택한 날짜의 모든 일기 데이터를 가져옵니다.")
    @GetMapping("/read/diary")
    List<Diary> readDiary(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @ApiParam(value = "날짜 형식: yyyy-MM-dd", example = "2023-07-07") LocalDate date) {
        return diaryService.readDiary(date);
    }
    ```
  <br>
- GET : /read/diaries
  - startDate, ednDate parameter 로 조회할 날짜 기간의 시작일/종료일을 받아주세요.
  - 해당 기간의 일기를 List 형태로 반환해주세요.
    ```java
    @ApiOperation("선택한 기간의 모든 일기 데이터를 가져옵니다.")
    @GetMapping("/read/diaries")
    List<Diary> readDiaries(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @ApiParam(value = "조회할 기간의 첫번째 날", example = "2023-07-07") LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @ApiParam(value = "조회할 기간의 마지막 날", example = "2023-07-31") LocalDate endDate
    ) {
        return diaryService.readDiaries(startDate, endDate);
    }
    ```
  <br>
- PUT : /update/diary
  - date parameter 로 수정할 날짜를 받아주세요.
  - text parameter 로 수정할 새 일기 글을 받아주세요.
  - 해당 날짜의 첫번째 일기 글을 새로 받아온 일기글로 수정해주세요.
    ```java
    @ApiOperation("선택한 날짜의 일기 내용을 업데이트 합니다.")
    @PutMapping("/update/diary")
    void updateDiary(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestBody String text
    ) {
        diaryService.updateDiary(date, text);
    }
    ```
    <br>
- DELETE : /delete/diary
  - date parameter 로 삭제할 날짜를 받아주세요.
  - 해당 날짜의 모든 일기를 지워주세요.
    ```java
    @ApiOperation("선택한 날짜의 모든 일기를 삭제 합니다.")
    @DeleteMapping("/delete/diary")
    void deleteDiary(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        diaryService.deleteDiary(date);
    }
    ```
    <br>
- 매일 새벽 1시에 날씨 데이터를 외부 API 에서 받아다 DB에 저장해두는 로직을 구현
  ```java
    @Transactional
    @Scheduled(cron = "0 0 1 * * *")
    public void saveWeatherDate() {
        dateWeatherRepository.save(getWeatherFromApi());
    }

    private DateWeather getWeatherFromApi() {
        String weatherData = getWeatherString();

        Map<String, Object> parseWeather = parseWeather(weatherData);
        DateWeather dateWeather = new DateWeather();
        dateWeather.setDate(LocalDate.now());
        dateWeather.setWeather(parseWeather.get("main").toString());
        dateWeather.setIcon(parseWeather.get("icon").toString());
        dateWeather.setTemperature((Double) parseWeather.get("temp"));
        return dateWeather;
    }
  ```
  <br>

- swagger 을 이용하여 API documentation 작성
  ![1](https://github.com/wooooozin/WeatherDemo/assets/95316662/8405e246-bc5f-4643-9847-3c30ad6bd171)

# 🐯 실습 프로젝트 및 과제 후기
```text
트랜잭션과 스프링 트랜잭션에 대한 이해,
공공 API를 HttpURLConnection를 이용해 받은 결과를 JSON Parser를 이용해 객체화 하기,
logback, swagger 등 사용하기 등을 통해 관련 학습에 대한 이해도를 높일 수 있었고
API 호출을 적게할 수 있는 방법이나 전역 에러 처리 등에 대한 학습도 배울 수 있었던 프로젝트입니다.
추가적으로 날짜 조회 관련 에러를 커스텀 Exception을 만들어서 적용해보았습니다.
```
 
