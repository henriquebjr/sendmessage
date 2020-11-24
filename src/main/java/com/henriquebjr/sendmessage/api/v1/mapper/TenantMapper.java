package com.henriquebjr.sendmessage.api.v1.mapper;

import com.henriquebjr.sendmessage.api.v1.dto.TenantDTO;
import com.henriquebjr.sendmessage.model.Tenant;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface TenantMapper {

    TenantDTO map(Tenant tenant);

    List<TenantDTO> map(List<Tenant> tenants);

    Tenant map(TenantDTO tenantDTO);

}
