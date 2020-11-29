package com.henriquebjr.sendmessage.service;

import com.henriquebjr.sendmessage.model.Tenant;
import com.henriquebjr.sendmessage.model.User;
import com.henriquebjr.sendmessage.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class SecurityServiceTest {

    @InjectMocks
    private SecurityService securityService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Principal principal;

    @BeforeEach
    public void setup() {
        openMocks(this);
        when(securityContext.getUserPrincipal()).thenReturn(principal);
        when(principal.getName()).thenReturn("username_xpto");
    }

    @Disabled
    @Test
    public void testGetCurrentUser() {
        var user = User.Builder.of()
                .name("xpto")
                .tenant(Tenant.Builder.of().id("TENANT-ID-3232").build())
                .build();
        when(userRepository.findByUsername(eq("username_xpto"))).thenReturn(user);

        var result = securityService.getCurrentUser(securityContext);

        verify(userRepository).findByUsername(eq("username_xpto"));
        assertThat(result).isEqualTo(user);
    }

    @Disabled
    @Test
    public void testGetCurrentTenantId() {
        var user = User.Builder.of()
                .name("xpto")
                .tenant(Tenant.Builder.of().id("TENANT-ID-3232").build())
                .build();
        when(userRepository.findByUsername(eq("username_xpto"))).thenReturn(user);

        var result = securityService.getCurrentTenantId(securityContext);

        verify(userRepository).findByUsername(eq("username_xpto"));
        assertThat(result).isEqualTo("TENANT-ID-3232");
    }
}
