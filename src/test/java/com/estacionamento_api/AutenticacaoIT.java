package com.estacionamento_api;

import com.estacionamento_api.jwt.JwtToken;
import com.estacionamento_api.web.dto.UsuarioCreateDto;
import com.estacionamento_api.web.dto.UsuarioLoginDto;
import com.estacionamento_api.web.dto.UsuarioResponseDto;
import com.estacionamento_api.web.exception.ErroMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/usuarios/usuarios-insert.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/usuarios/usuarios-delete.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AutenticacaoIT {

    @LocalServerPort
    int port;


    WebTestClient testClient;

    @BeforeEach
    void setUp() {
        this.testClient = WebTestClient
                .bindToServer()
                .baseUrl("http://localhost:" + port)
                .build();
    }

    @Test
    public void autenticar_ComCredenciaisValidas_RetornarTokenComTatus200(){
       JwtToken responseBoby =testClient
                .post()
                .uri("/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioLoginDto("ana@gmail.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(JwtToken.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBoby).isNotNull();

    }

    @Test
    public void autenticar_ComCredenciaisInValidas_RetornarErrorMessageStatus400(){
        ErroMessage responseBoby =testClient
                .post()
                .uri("/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioLoginDto("inavalido@gmail.com", "123456"))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErroMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBoby).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBoby.getStatus()).isEqualTo(400);

        responseBoby =testClient
                .post()
                .uri("/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioLoginDto("ana@gmail.com", "111456"))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ErroMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBoby).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBoby.getStatus()).isEqualTo(400);

    }

    @Test
    public void autenticar_ComUsernameInValido_RetornarErrorMessageStatus422(){
        ErroMessage responseBoby =testClient
                .post()
                .uri("/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioLoginDto("", "123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErroMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBoby).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBoby.getStatus()).isEqualTo(422);

        responseBoby =testClient
                .post()
                .uri("/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioLoginDto("@gmail.com", "123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErroMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBoby).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBoby.getStatus()).isEqualTo(422);



    }

    @Test
    public void autenticar_ComPasswordInValido_RetornarErrorMessageStatus422(){
        ErroMessage responseBoby =testClient
                .post()
                .uri("/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioLoginDto("ana@gmail.com", ""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErroMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBoby).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBoby.getStatus()).isEqualTo(422);

        responseBoby =testClient
                .post()
                .uri("/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioLoginDto("ana@gmail.com", "123"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErroMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBoby).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBoby.getStatus()).isEqualTo(422);

        responseBoby =testClient
                .post()
                .uri("/api/v1/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioLoginDto("ana@gmail.com", "1234567"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErroMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBoby).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBoby.getStatus()).isEqualTo(422);



    }
}
