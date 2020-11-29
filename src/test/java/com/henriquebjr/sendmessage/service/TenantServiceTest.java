package com.henriquebjr.sendmessage.service;

import com.henriquebjr.sendmessage.model.Tenant;
import com.henriquebjr.sendmessage.model.User;
import com.henriquebjr.sendmessage.repository.TenantRepository;
import com.henriquebjr.sendmessage.service.exception.TenantNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class TenantServiceTest {

    @InjectMocks
    private TenantService tenantService;

    @Mock
    private TenantRepository tenantRepository;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setup() {
        openMocks(this);
    }

    @Disabled
    @Test
    public void testRetrieveAll() {
        var tenants = Arrays.asList(
                Tenant.Builder.of().name("tenant1").build(),
                Tenant.Builder.of().name("tenant2").build()
        );
        when(tenantRepository.listAll()).thenReturn(tenants);

        var result = tenantService.retrieveAll();

        verify(tenantRepository).listAll();
        assertThat(result).isEqualTo(tenants);
    }

    @Disabled
    @Test
    public void testRetrieveById() {
        var tenant1 = Tenant.Builder.of().name("tenant1").build();
        when(tenantRepository.findById("ID-TENANT-1")).thenReturn(tenant1);

        var result = tenantService.retrieveById("ID-TENANT-1");

        verify(tenantRepository).findById(eq("ID-TENANT-1"));
        assertThat(result).isEqualTo(tenant1);
    }

    @Disabled
    @Test
    public void testCountAll() {
        when(tenantRepository.count()).thenReturn(10L);

        var result = tenantService.countAll();

        verify(tenantRepository).count();
        assertThat(result).isEqualTo(10L);
    }

    @Disabled
    @Test
    public void testInsert() throws Exception {
        var newTenant = Tenant.Builder.of()
                .active(true)
                .name("tenant 5")
                .build();
        var result = tenantService.insert(newTenant);

        //verify(tenantRepository).persistAndFlush(eq(newTenant));
        verify(userService).insert(anyString(), any(User.class));
        assertThat(result.getId()).isNotNull();
        assertThat(result.getCreatedDate()).isNotNull();
        assertThat(result.getActive()).isTrue();
        assertThat(result.getName()).isEqualTo("tenant 5");
    }

    @Disabled
    @Test
    public void testInsertWhenNotFilledActiveStatus() throws Exception {
        var newTenant = Tenant.Builder.of()
                .name("tenant 6")
                .build();
        var result = tenantService.insert(newTenant);

        //verify(tenantRepository).persistAndFlush(eq(newTenant));
        verify(userService).insert(anyString(), any(User.class));
        assertThat(result.getId()).isNotNull();
        assertThat(result.getCreatedDate()).isNotNull();
        assertThat(result.getActive()).isTrue();
        assertThat(result.getName()).isEqualTo("tenant 6");
    }

    @Disabled
    @Test
    public void testInsertWhenActiveStatusIsFalse() throws Exception {
        var newTenant = Tenant.Builder.of()
                .name("tenant 7")
                .active(false)
                .build();
        var result = tenantService.insert(newTenant);

        //verify(tenantRepository).persistAndFlush(eq(newTenant));
        verify(userService).insert(anyString(), any(User.class));
        assertThat(result.getId()).isNotNull();
        assertThat(result.getCreatedDate()).isNotNull();
        assertThat(result.getActive()).isFalse();
        assertThat(result.getName()).isEqualTo("tenant 7");
    }

    @Disabled
    @Test
    public void testUpdate() throws Exception {
        var oldTenant = Tenant.Builder.of()
                .id("ID-TENANT-10")
                .name("tenant 10")
                .active(false)
                .build();
        when(tenantRepository.findByIdOptional("ID-TENANT-10")).thenReturn(Optional.of(oldTenant));

        var newTenant = Tenant.Builder.of()
                .id("ID-TENANT-10")
                .name("tenant 10 new")
                .active(true)
                .build();

        var result = tenantService.update("ID-TENANT-10", newTenant);

        verify(tenantRepository).findByIdOptional(eq("ID-TENANT-10"));

        assertThat(result.getId()).isEqualTo("ID-TENANT-10");
        assertThat(result.getActive()).isTrue();
        assertThat(result.getName()).isEqualTo("tenant 10 new");
        assertThat(result.getUpdatedDate()).isNotNull();
    }

    @Disabled
    @Test
    public void testUpdateWhenNotFoundTenant() {
        when(tenantRepository.findByIdOptional("ID-TENANT-10")).thenReturn(Optional.empty());

        var newTenant = Tenant.Builder.of()
                .id("ID-TENANT-11")
                .name("tenant 11 new")
                .active(true)
                .build();

        var result = catchThrowable(() -> tenantService.update("ID-TENANT-11", newTenant));

        assertThat(result)
                .isExactlyInstanceOf(TenantNotFoundException.class)
                .hasMessage("Tenant not found. Id: ID-TENANT-11");
        verify(tenantRepository).findByIdOptional(eq("ID-TENANT-11"));
    }

    @Disabled
    @Test
    public void testDelete() {
        tenantService.delete("ID-TENANT-20");

        verify(tenantRepository).deleteById("ID-TENANT-20");
    }
}
