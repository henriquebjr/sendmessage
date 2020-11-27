package com.henriquebjr.sendmessage.api.v1.dto;

import com.henriquebjr.sendmessage.api.base.CollectionResponse;

import java.util.List;

public class TenantListDTO extends CollectionResponse<TenantDTO> {

    public TenantListDTO() {
        super();
    }

    public TenantListDTO(List<TenantDTO> items) {
        super(items);
    }
}
