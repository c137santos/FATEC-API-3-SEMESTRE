package com.group.backend.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.group.backend.entity.TagPortal;
import com.group.backend.entity.TagPortalId;

@Repository
public interface TagPortalRepository extends JpaRepository<TagPortal, TagPortalId> {

    @Query(value = "SELECT tag_id FROM tag_portal WHERE por_id = :portalId", nativeQuery = true)
    Iterable<Long> getAllTagsByPortal(@Param("portalId") Long portalId);
}
