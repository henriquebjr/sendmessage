package com.henriquebjr.sendmessage.api.base;

import java.util.List;

public class CollectionResponse<E> {
    private List<E> items;

    public CollectionResponse() {
    }

    public CollectionResponse(List<E> items) {
        this.items = items;
    }

    public List<E> getItems() {
        return items;
    }

    public void setItems(List<E> items) {
        this.items = items;
    }
}
