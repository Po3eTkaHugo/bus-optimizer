package ru.busoptimizer.backend.service;

import java.util.List;

public interface BasicService {
    public List<Object> getAll();

    public Object save();

    public Object get();

    public Object update();

    public void deleteBus();
}
