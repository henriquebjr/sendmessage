package com.henriquebjr.sendmessage.service.initialization;

import com.henriquebjr.sendmessage.model.Tenant;
import com.henriquebjr.sendmessage.service.TenantService;
import io.quarkus.runtime.StartupEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class AppInitializationServiceTest {

    @InjectMocks
    private AppInitializationService appInitializationService;

    @Mock
    private TenantService tenantService;

    @BeforeEach
    public void setup() {
        openMocks(this);
    }

    @Test
    public void testOnStartWhenAlreadyExistsTenant() throws Exception {
        when(tenantService.countAll()).thenReturn(1L);

        appInitializationService.onStart(new StartupEvent());

        verify(tenantService, never()).insert(any(Tenant.class));
    }

    @Test
    public void testOnStartWhenDoesNotExistsTenant() throws Exception {
        when(tenantService.countAll()).thenReturn(0L);
        ArgumentCaptor<Tenant> tenantCaptor = ArgumentCaptor.forClass(Tenant.class);

        appInitializationService.onStart(new StartupEvent());

        verify(tenantService).insert(tenantCaptor.capture());
        assertThat(tenantCaptor.getValue().getName()).isEqualTo("default");
    }
}
