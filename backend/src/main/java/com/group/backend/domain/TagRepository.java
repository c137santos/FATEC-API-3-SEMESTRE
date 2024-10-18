package com.group.backend.domain;

import com.group.backend.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    @Query("SELECT t FROM Tag t LEFT JOIN FETCH t.regionalismos")
    List<Tag> findAllWithRegionalismos();
}

