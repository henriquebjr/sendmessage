package com.henriquebjr.sendmessage.service;

import com.henriquebjr.sendmessage.model.Tenant;
import com.henriquebjr.sendmessage.model.User;
import com.henriquebjr.sendmessage.repository.UserRepository;
import com.henriquebjr.sendmessage.service.exception.UserInvalidRoleException;
import com.henriquebjr.sendmessage.service.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        openMocks(this);
    }

    @Disabled
    @Test
    public void testRetrieveAll() {
        var users = Arrays.asList(
                User.Builder.of().name("john").build(),
                User.Builder.of().name("paul").build()
        );
        when(userRepository.listAll(anyString())).thenReturn(users);

        var result = userService.retrieveAll("TENANT-ID-100");

        assertThat(result).hasSize(2);
        assertThat(result).isEqualTo(users);
    }

    @Disabled
    @Test
    public void testRetrieveById() {
        var user = User.Builder.of().name("john").build();
        when(userRepository.findById(anyString(), anyString())).thenReturn(user);

        var result = userService.retrieveById("TENANT-ID-101", "USER-ID-1");

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("john");
    }

    @Disabled
    @Test
    public void testInsert() throws Exception {
        var user = User.Builder.of()
                .role("admin")
                .username("jossef")
                .password("123")
                .active(true)
                .name("Jossef Climber").build();

        var result = userService.insert("TENANT-ID-102", user);

        verify(userRepository).persist(eq(user));

        assertThat(result.getId()).isNotNull();
        assertThat(result.getTenant().getId()).isEqualTo("TENANT-ID-102");
        assertThat(result.getCreatedDate()).isNotNull();
        assertThat(result.getActive()).isTrue();
        assertThat(result.getPassword()).isNotNull();
        assertThat(result.getRole()).isEqualTo("admin");
    }

    @Disabled
    @Test
    public void testInsertInactiveUser() throws Exception {
        var user = User.Builder.of()
                .role("user")
                .username("johnson")
                .password("123")
                .active(false)
                .name("Johnson Wood").build();

        var result = userService.insert("TENANT-ID-103", user);

        verify(userRepository).persist(eq(user));

        assertThat(result.getId()).isNotNull();
        assertThat(result.getTenant().getId()).isEqualTo("TENANT-ID-103");
        assertThat(result.getCreatedDate()).isNotNull();
        assertThat(result.getActive()).isFalse();
        assertThat(result.getPassword()).isNotNull();
        assertThat(result.getRole()).isEqualTo("user");
    }

    @Disabled
    @Test
    public void testInsertWhenNotFilledActiveAndRole() throws Exception {
        var user = User.Builder.of()
                .username("james")
                .password("123")
                .name("James Green").build();

        var result = userService.insert("TENANT-ID-104", user);

        verify(userRepository).persist(eq(user));

        assertThat(result.getId()).isNotNull();
        assertThat(result.getTenant().getId()).isEqualTo("TENANT-ID-104");
        assertThat(result.getCreatedDate()).isNotNull();
        assertThat(result.getActive()).isTrue();
        assertThat(result.getPassword()).isNotNull();
        assertThat(result.getRole()).isEqualTo("user");
    }

    @Disabled
    @Test
    public void testInsertWithInvalidRole() throws Exception {
        var user = User.Builder.of()
                .role("administration")
                .username("james")
                .password("123")
                .name("James Green").build();

        var result = catchThrowable(() -> userService.insert("ID-TENANT-105", user));

        assertThat(result)
                .isExactlyInstanceOf(UserInvalidRoleException.class)
                .hasMessage("Invalid user role.");

        verify(userRepository, never()).persist(eq(user));
    }

    @Disabled
    @Test
    public void testUpdate() throws Exception {
        var oldUser = User.Builder.of()
                .id("ID-USER-300")
                .role("user")
                .username("edward")
                .active(false)
                .password("123")
                .name("Edward Meed")
                .tenant(Tenant.Builder.of().id("ID-TENANT-106").build())
                .build();
        when(userRepository.findByIdOptional(eq("ID-USER-300"))).thenReturn(Optional.of(oldUser));

        var newUser = User.Builder.of()
                .id("ID-USER-300")
                .role("admin")
                .username("edward")
                .active(true)
                .name("Edward Meed III").build();

        var result = userService.update("ID-TENANT-106", "ID-USER-300", newUser);

        assertThat(result.getActive()).isTrue();
        assertThat(result.getName()).isEqualTo("Edward Meed III");
        assertThat(result.getRole()).isEqualTo("admin");
        assertThat(result.getPassword()).isEqualTo("123");
    }

    @Disabled
    @Test
    public void testUpdateWhenChangePassword() throws Exception {
        var oldUser = User.Builder.of()
                .id("ID-USER-300")
                .role("user")
                .username("edward")
                .active(false)
                .password("123")
                .name("Edward Meed")
                .tenant(Tenant.Builder.of().id("ID-TENANT-106").build())
                .build();
        when(userRepository.findByIdOptional(eq("ID-USER-300"))).thenReturn(Optional.of(oldUser));

        var newUser = User.Builder.of()
                .id("ID-USER-300")
                .role("admin")
                .username("edward")
                .active(true)
                .password("12345")
                .name("Edward Meed III").build();

        var result = userService.update("ID-TENANT-106", "ID-USER-300", newUser);

        assertThat(result.getActive()).isTrue();
        assertThat(result.getName()).isEqualTo("Edward Meed III");
        assertThat(result.getRole()).isEqualTo("admin");
        assertThat(result.getPassword()).isNotNull().isNotEqualTo("123");
    }

    @Disabled
    @Test
    public void testUpdateWhenIdIsInvalid() throws Exception {
        when(userRepository.findByIdOptional(eq("ID-USER-310"))).thenReturn(Optional.empty());

        var newUser = User.Builder.of()
                .role("admin")
                .username("john")
                .active(true)
                .password("12345")
                .name("John Ryans").build();

        var result = catchThrowable(() -> userService.update("ID-TENANT-106", "ID-USER-310", newUser));
        assertThat(result)
                .isExactlyInstanceOf(UserNotFoundException.class)
                .hasMessage("User not found. Id: ID-USER-310");
        verify(userRepository).findByIdOptional(eq("ID-USER-310"));

    }

    @Disabled
    @Test
    public void testUpdateWhenTenantIsDifferent() throws Exception {
        var oldUser = User.Builder.of()
                .id("ID-USER-311")
                .role("user")
                .username("richard")
                .active(false)
                .password("123")
                .name("Richard Bush")
                .tenant(Tenant.Builder.of().id("ID-TENANT-120").build())
                .build();
        when(userRepository.findByIdOptional(eq("ID-USER-300"))).thenReturn(Optional.of(oldUser));

        var newUser = User.Builder.of()
                .role("admin")
                .username("richard")
                .active(true)
                .password("12345")
                .name("Richard Bush").build();

        var result = catchThrowable(() -> userService.update("ID-TENANT-110", "ID-USER-311", newUser));
        assertThat(result)
                .isExactlyInstanceOf(UserNotFoundException.class)
                .hasMessage("User not found. Id: ID-USER-311");
        verify(userRepository).findByIdOptional(eq("ID-USER-311"));
    }

    @Disabled
    @Test
    public void testDelete() throws Exception {
        var user = User.Builder.of()
                .id("ID-USER-340")
                .role("user")
                .username("george")
                .active(true)
                .password("123")
                .name("George Wilson")
                .tenant(Tenant.Builder.of().id("ID-TENANT-115").build())
                .build();
        when(userRepository.findByIdOptional(eq("ID-USER-340"))).thenReturn(Optional.of(user));

        userService.delete("ID-TENANT-115", "ID-USER-340");

        verify(userRepository).delete("ID-USER-340");
    }

    @Disabled
    @Test
    public void testDeleteWhenUserDoesNotExists() throws Exception {
        when(userRepository.findByIdOptional(eq("ID-USER-341"))).thenReturn(Optional.empty());

        var result = catchThrowable(() -> userService.delete("ID-TENANT-115", "ID-USER-341"));

        assertThat(result).isExactlyInstanceOf(UserNotFoundException.class)
            .hasMessage("User not found. Id: ID-USER-341");
        verify(userRepository, never()).delete(anyString());
    }

    @Disabled
    @Test
    public void testDeleteWhenUserTenantIsDifferent() throws Exception {
        var user = User.Builder.of()
                .id("ID-USER-350")
                .role("user")
                .username("george")
                .active(true)
                .password("123")
                .name("George Wilson")
                .tenant(Tenant.Builder.of().id("ID-TENANT-1000").build())
                .build();
        when(userRepository.findByIdOptional(eq("ID-USER-350"))).thenReturn(Optional.of(user));

        var result = catchThrowable(() -> userService.delete("ID-TENANT-120", "ID-USER-350"));

        assertThat(result).isExactlyInstanceOf(UserNotFoundException.class)
                .hasMessage("User not found. Id: ID-USER-350");
        verify(userRepository, never()).delete(anyString());
    }
}
