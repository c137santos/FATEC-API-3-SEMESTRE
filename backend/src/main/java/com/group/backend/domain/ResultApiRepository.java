package com.group.backend.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.group.backend.entity.ResultApi;

@Repository
public interface ResultApiRepository extends JpaRepository<ResultApi, Long> {
	@Query(value="SELECT * FROM result_api ra1\n" + //
				"WHERE api_id = ?1 AND\n" + //
				"res_data = (SELECT MAX(res_data) FROM result_api ra2\n" + //
				"WHERE ra2.res_id = ra2.res_id AND api_id = ?1)\n" + //
				"ORDER BY res_id, res_data;\n" + //
				"", nativeQuery = true)
	ResultApi getLastFromResId(Long id);
}
