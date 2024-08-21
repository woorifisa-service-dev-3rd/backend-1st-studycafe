# 🛋️ 스터디카페 좌석 예약 시스템

본 프로젝트는 스터디카페에서 좌석을 예약하고 시간을 충전할 수 있는 간단한 Java 프로그램입니다. 사용자는 로그인 후 좌석 현황을 확인하고, 좌석을 예약하거나 시간을 충전할 수 있습니다. 모든 동작은 MySQL 데이터베이스와 연동되어 관리됩니다.

<br>

## 👨‍💻팀원 소개

<table>
  <tr>
    <td align="center">
      <a href="https://github.com/kimh7537">
        <img src="https://github.com/kimh7537.png" alt="김현우" width="150" height="150"/>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/seonmin5">
        <img src="https://github.com/seonmin5.png" alt="오선민" width="150" height="150"/>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/Kee0304">
        <img src="https://github.com/Kee0304.png" alt="기남석" width="150" height="150"/>
      </a>
    </td>
        <td align="center">
      <a href="https://github.com/AnChanUng">
        <img src="https://github.com/AnChanUng.png" alt="안찬웅" width="150" height="150"/>
      </a>
    </td>
  </tr>
   <tr>
    <td align="center">
      <a href="https://github.com/kimh7537">
        <b>김현우</b>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/seonmin5">
        <b>오선민</b>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/Kee0304">
        <b>기남석</b>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/AnChanUng">
        <b>안찬웅</b>
      </a>
    </td>
  </tr>
  <tr>
    <td align="center">

    </td>
    <td align="center">

    </td>
    <td align="center">

    </td>
    <td align="center">

    </td>
  </tr>
</table>

<br>

## 💻 사용 언어

다음과 같은 언어와 기술을 사용하여 개발되었습니다:
- **Java**: 프로그램의 주요 로직을 처리하며, 객체 지향 프로그래밍 방식으로 설계되었습니다.
- **MySQL**: 사용자 정보, 좌석 정보, 예약 정보를 관리하기 위해 사용된 데이터베이스입니다.

<br>

## 🔧 주요 기능

1. **사용자 인증**: 사용자가 MySQL 데이터베이스에 저장된 정보와 비교하여 로그인할 수 있습니다.
2. **좌석 현황 표시**: 로그인 후, 현재 좌석의 이용 가능 여부를 확인할 수 있습니다. 이미 사용 중인 좌석은 'X'로 표시됩니다.
3. **좌석 예약**: 사용자는 원하는 좌석 번호와 이용 시간을 입력하여 좌석을 예약할 수 있습니다. 예약이 완료되면 예약 시간과 가격이 안내됩니다.
4. **시간 충전**: 사용자는 1시간부터 60시간까지 다양한 옵션을 선택하여 시간을 충전할 수 있습니다. 충전 후, 남은 시간을 확인할 수 있습니다.

<br>

## 🗂️ 디렉토리 구조
src/
├── main/
├── java/
│ └── com/example/
│ ├── common/ # 가격 및 좌석 타입을 관리하는 클래스
│ ├── reservation/ # 예약 관련 DAO, 모델, 서비스
│ ├── seat/ # 좌석 관련 DAO, 모델, 서비스
│ ├── user/ # 사용자 관련 DAO, 모델, 서비스
│ └── util/ # DB 연결 유틸리티 클래스
└── resources/
└── jdbc.properties # MySQL 연결 설정 파일

<br>

### 📂 주요 디렉토리 및 클래스 설명

- **`common/`**: 가격 타입(`PriceType`)과 좌석 타입(`SeatType`)을 정의하고 관리합니다.
- **`reservation/`**: 예약 관련 데이터 접근 객체(DAO), 모델, 서비스가 포함된 디렉토리입니다.
  - `ReservationDAO`: 예약 데이터를 MySQL과 연동합니다.
  - `ReservationService`: 예약 관련 비즈니스 로직을 처리합니다.
- **`seat/`**: 좌석 관련 데이터 접근 객체(DAO), 모델, 서비스가 포함된 디렉토리입니다.
  - `SeatDAO`: 좌석 데이터를 MySQL과 연동합니다.
  - `SeatService`: 좌석 관련 비즈니스 로직을 처리합니다.
- **`user/`**: 사용자 관련 데이터 접근 객체(DAO), 모델, 서비스가 포함된 디렉토리입니다.
  - `UserDAO`: 사용자 데이터를 MySQL과 연동합니다.
  - `UserService`: 사용자 로그인 및 시간 관리 로직을 처리합니다.
- **`util/`**: 데이터베이스 연결 및 해제를 관리하는 유틸리티 클래스가 포함된 디렉토리입니다.
  - `DBUtil`: MySQL 데이터베이스와의 연결 및 종료를 처리합니다.

<br>

## 🗄️ MySQL 연동

이 프로그램은 MySQL 데이터베이스와 연동되어 사용자의 로그인, 좌석 예약, 시간 충전 등의 기능을 관리합니다. 데이터베이스 설정은 `src/main/resources/jdbc.properties` 파일에서 관리되며, 데이터베이스 연결은 `DBUtil` 클래스를 통해 이루어집니다.

<br>

