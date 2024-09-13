package com.group.backend.repository;
import com.group.backend.entity.Api;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ApiRepository {
    private final List<Api> apiList = new ArrayList<>();

    public ApiRepository() {

        apiList.add(new Api(1L, "API 1", "https://api1.example.com"));
        apiList.add(new Api(2L, "API 2", "https://api2.example.com"));
    }

    public List<Api> findAll() {
        return new ArrayList<>(apiList);
    }

    public Optional<Api> findById(Long id) {
        return apiList.stream()
                .filter(api -> api.getId().equals(id))
                .findFirst();
    }
}
