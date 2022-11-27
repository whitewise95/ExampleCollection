package com.example.examplecollection.valid;

import com.example.examplecollection.valid.dto.UserRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
public class ValidTestControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private ObjectMapper mapper;
    private HttpHeaders headers;

    @BeforeAll
    public void setUp() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        mapper = new ObjectMapper();
    }

    @DisplayName("@Valid 테스트")
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @Order(1)
    @Nested
    class Valid {

        @DisplayName("나이 19세 미만으로 실패")
        @Order(1)
        @Test
        public void 나이_19세_미만으로_실패() throws Exception {
            UserRequestDto userRequestDto = UserRequestDto.builder()
                    .email("whitewise95@gmail.com")
                    .age(18)
                    .name("짱구")
                    .password("1234")
                    .build();

            String requestBody = mapper.writeValueAsString(userRequestDto);
            HttpEntity<String> stringHttpEntity = new HttpEntity<>(requestBody, headers);

            //when
            ResponseEntity response = restTemplate.postForEntity(
                    "/v1/validTest",
                    stringHttpEntity,
                    Void.class
            );

            //then 400 BAD_REQUEST가 확인되어야 정상
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        }

        @DisplayName("비밀번호 빈 문자열 실패")
        @Order(2)
        @Test
        public void 비밀번호_빈_문자열_실패() throws Exception {
            UserRequestDto userRequestDto = UserRequestDto.builder()
                    .email("whitewise95@gmail.com")
                    .age(19)
                    .name("짱구")
                    .password("")
                    .build();

            String requestBody = mapper.writeValueAsString(userRequestDto);
            HttpEntity<String> stringHttpEntity = new HttpEntity<>(requestBody, headers);

            //when
            ResponseEntity response = restTemplate.postForEntity(
                    "/v1/validTest",
                    stringHttpEntity,
                    Void.class
            );

            //then 400 BAD_REQUEST가 확인되어야 정상
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        }

        @DisplayName("제약조건 성공")
        @Order(3)
        @Test
        public void 제약조건_성공() throws Exception {
            UserRequestDto userRequestDto = UserRequestDto.builder()
                    .email("whitewise95@gmail.com")
                    .age(19)
                    .name("짱구")
                    .password("1234")
                    .build();

            String requestBody = mapper.writeValueAsString(userRequestDto);
            HttpEntity<String> stringHttpEntity = new HttpEntity<>(requestBody, headers);

            //when
            ResponseEntity response = restTemplate.postForEntity(
                    "/v1/validTest",
                    stringHttpEntity,
                    Void.class
            );

            //then 400 BAD_REQUEST가 확인되어야 정상
            assertEquals(HttpStatus.OK, response.getStatusCode());
        }
    }

    @DisplayName("@Validated 테스트")
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @Nested
    @Order(2)
    class Validated {

        @DisplayName("이메일 형식 실패")
        @Order(1)
        @Test
        public void 이메일_형식_실패() throws Exception {
            UserRequestDto userRequestDto = UserRequestDto.builder()
                    .email("whitewise")
                    .age(19)
                    .name("짱구")
                    .password("1234")
                    .build();

            String requestBody = mapper.writeValueAsString(userRequestDto);
            HttpEntity<String> stringHttpEntity = new HttpEntity<>(requestBody, headers);

            //when
            ResponseEntity response = restTemplate.postForEntity(
                    "/v1/userUpdateTest",
                    stringHttpEntity,
                    Void.class
            );

            //then 400 BAD_REQUEST가 확인되어야 정상
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        }

        @DisplayName("이름 null 실패")
        @Order(2)
        @Test
        public void 이름_null_실패() throws Exception {
            UserRequestDto userRequestDto = UserRequestDto.builder()
                    .email("whitewise95@gmail.com")
                    .age(19)
                    .password("1234")
                    .build();

            String requestBody = mapper.writeValueAsString(userRequestDto);
            HttpEntity<String> stringHttpEntity = new HttpEntity<>(requestBody, headers);

            //when
            ResponseEntity response = restTemplate.postForEntity(
                    "/v1/userUpdateTest",
                    stringHttpEntity,
                    Void.class
            );

            //then 400 BAD_REQUEST가 확인되어야 정상
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        }

        @DisplayName("Validated 성공")
        @Order(3)
        @Test
        public void Validated_성공() throws Exception {
            UserRequestDto userRequestDto = UserRequestDto.builder()
                    .email("whitewise@gmail.com")
                    .name("짱구")
                    .build();

            String requestBody = mapper.writeValueAsString(userRequestDto);
            HttpEntity<String> stringHttpEntity = new HttpEntity<>(requestBody, headers);

            //when
            ResponseEntity response = restTemplate.postForEntity(
                    "/v1/userUpdateTest",
                    stringHttpEntity,
                    Void.class
            );

            //then 400 BAD_REQUEST가 확인되어야 정상
            assertEquals(HttpStatus.OK, response.getStatusCode());
        }

    }

}
