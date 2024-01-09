package emanuel.catao.investimentos.service;

import emanuel.catao.investimentos.controller.CreateUserDTO;
import emanuel.catao.investimentos.entity.User;
import emanuel.catao.investimentos.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    @Nested
    class CreateUser {
        @Test
        @DisplayName("Should create a user with success")
        void shouldCreateAUser() {
            //Arrange
            var user = new User(
                    UUID.randomUUID(),
                    "username",
                    "email@email.com",
                    "password",
                    Instant.now(),
                    null
            );

            doReturn(user).when(userRepository).save(any());
            var createUserDTO = new CreateUserDTO(
                    "username",
                       "email@email.com",
                    "password"
            );
            //Act
            var userId = userService.createUser(createUserDTO);
            //Assert
            assertNotNull(userId);
        }

        @Test
        @DisplayName("Should throw exception when error occurs")
        void shouldThrowExceptionWhenErrorOccurs() {
            doThrow(new RuntimeException()).when(userRepository).save(any());
            var input = new CreateUserDTO(
                    null,
                    "email",
                    "password"
            );
            assertThrows(RuntimeException.class, () -> userService.createUser(input));
        }
    }


}