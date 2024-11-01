package com.group.backend.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; // Import correto para JPA
import org.springframework.stereotype.Repository;
import com.group.backend.entity.Noticia;

@Repository
public interface NoticiaRepository extends JpaRepository<Noticia, Long> {
    boolean existsByUrl(String url);
    Noticia findByUrl(String url);

    // Método para encontrar o maior ID de Noticia
    @Query("SELECT MAX(n.notiId) FROM Noticia n")
    Long findMaxNotiId();
}
