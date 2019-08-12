package com.monologica.poetica.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class IDStorageManager<T, U> {
    protected HashMap<T, U> storage;

    public IDStorageManager() {
        storage = new HashMap<T, U>();
    }

    public U get(T id) {
        return storage.get(id);
    }

    public List<U> getStorage() {
        return new ArrayList<U>(storage.values());
    }
}
