package com.group.backend.domain;

import com.group.backend.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    @Query("SELECT t FROM Tag t LEFT JOIN FETCH t.regionalismos")
    List<Tag> findAllWithRegionalismos();

    @Query("SELECT t FROM Tag t LEFT JOIN FETCH t.regionalismos WHERE t.tagId = :id")
    Optional<Tag> findByIdWithRegionalismos(@Param("id") Long id);
}
