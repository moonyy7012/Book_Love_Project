<img src="https://img.shields.io/badge/spring_boot-2.3.2-%236DB33F?style=plastic&logo=Spring"> 
<img src="https://img.shields.io/badge/MySQL-8.0-%234479A1?style=plastic&logo=mysql"> 
<img src="https://img.shields.io/badge/docker-20.10.7-%236DB33F?color=blue&style=plastic&logo=docker"> 
<img src="https://img.shields.io/badge/Jenkins-2.319.3-%236DB33F?color=red&style=plastic&logo=Jenkins">

intellij: 2021.3.1
gradle : 7.1.1
ubuntu : 20.04 LTS

------

# server

1. Git clone 

   ```
   git clone https://lab.ssafy.com/s06-bigdata-rec-sub2/S06P22D106.git
   ```

2.  mysql db

   ```
   CREATE SCHEMA 'bookdb'
   ```

3.  application.yml
> 로컬에서할때는 application-local.yml 작성하고 activa : local 로 변경 

   ```
   # application yml active 설정
   spring:
     profiles:
       active: local
   ```

4. application-alpha.yml

   ```
   # 기본 로그 레벨 설정
   logging:
     level:
       root: warn
       com.ssafy.api: debug
       org.hibernate.type.descriptor.sql: warn  # trace
   spring:
     profiles:
       active: alpha
       include:
         - core
         - local
     messages:
       basename: i18n/exception
       encoding: UTF-8
     jwt:
       secret: DvqcGn8mnFjqSL4a
     jpa:
       database-platform: org.hibernate.dialect.MySQL5InnoDBDialect
       properties.hibernate:
         hbm2ddl.auto: none
         format_sql: true
         show_sql: true
         use_sql_comments: true
       generate-ddl: true
       open-in-view: false
   
     # 데이터 베이스 연결 설정
     datasource:
   
       url: jdbc:mysql://j6d106.p.ssafy.io:3306/test1?serverTimezone=Asia/Seoul&characterEncoding=UTF-8
       driver-class-name: com.mysql.jdbc.Driver
       username: ssafy
       password: ssafy
     flyway:
       enabled: false
     config:
       activate:
         on-profile: alpha
     servlet:
       multipart:
         max-file-size: 5MB
         max-request-size: 5MB     
   
   server:
     # 프로젝트 환경의 포트 설정
     port: 8185
     domain: j6d106.p.ssafy.io
     servlet:
       session:
         timeout: 1440m
     max-http-header-size: 3145728
   
   aes256:
     key: WZsExuBV3GSQ55Uf
   
   # swagger에서 테스트 할 때의 host
   swagger:
     host: j6d106.p.ssafy.io:8185
   ```
   

5. . yml 파일 team11/server/api-module/src/main/resources 경로에 만들거나 복사

   ```
   cp application.yml /workspace/team11/server/api-module/src/main/resources/application.yml
   ```

6.  local 
    java
   ```
    cd BookLove-BackEnd/Spring
    chmod +x gradlew
    ./gradlew clean
    ./gradlew bootJar
    java -jar ssafy-api-module-0.0.1.jar
   ```

   python
   ```
    cd BookLove-BackEnd/FastAPI
    python3 main.py
   ```

   

### server 배포 시 특이사항

> > gitlab webhook과  jenkins 연동 후 develop 브랜치에 merge 생길때 빌드 후 run 수행하게 하여 ci/cd 구축
> > nohup을 이용해 백그라운드에서 동작 시키므로 새로 빌드 후 동작시키려면 ps -e 명령어를 통해 java 와 python3 PID를 알아낸후
> > kill -9 {PID} 를 통해 종료시킨 후 빌드


### Test계정
> > id : testid
> > pw : 1234
