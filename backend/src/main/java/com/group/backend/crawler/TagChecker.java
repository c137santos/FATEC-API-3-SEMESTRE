package com.group.backend.crawler;

import com.group.backend.entity.Regionalismo;
import com.group.backend.entity.Noticia;
import com.group.backend.entity.Tag;
import com.group.backend.domain.TagRepository;
import com.group.backend.domain.TagPortalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Component
public class TagChecker {

    private static final Logger logger = LoggerFactory.getLogger(TagChecker.class);

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private TagPortalRepository tagPortalRepository;

    /**
     * Método responsável por extrair as tags presentes no corpo da notícia.
     * @param corpo Texto da notícia.
     * @param noticia Objeto que representa a notícia.
     * @return Lista de tags encontradas.
     */
    public List<Tag> extractTags(String corpo, Noticia noticia) {
        List<Tag> tagsEncontradas = new ArrayList<>();

        // Recupera o portalId da notícia
        Long portalId = noticia.getPorId().longValue();
        Iterable<Long> tagIds = tagPortalRepository.getAllTagsByPortal(portalId);

        // Itera sobre cada ID de tag e verifica se está presente no corpo da notícia
        tagIds.forEach(tagId -> {
            Tag tag = tagRepository.findByIdWithRegionalismos(tagId).orElse(null);
            if (tag != null && corpo.contains(tag.getTagNome())) {
                logger.info("Tag '{}' encontrada no corpo da notícia", tag.getTagNome());
                tagsEncontradas.add(tag);
            }
        });

        return tagsEncontradas;
    }

    /**
     * Método responsável por verificar a presença de tags e regionalismos no corpo da notícia.
     * @param corpo Texto da notícia.
     * @param noticia Objeto que representa a notícia.
     * @return Retorna verdadeiro se ao menos uma tag relevante foi encontrada.
     */
    @Transactional
    public boolean checkTagsAndRegionalismosInCorpo(String corpo, Noticia noticia) {
        // Extrai as tags relevantes
        List<Tag> tags = extractTags(corpo, noticia);

        boolean existeTagRelevante = false;

        // Verifica se a tag está presente no corpo e valida regionalismos
        for (Tag tag : tags) {
            if (corpo.contains(tag.getTagNome())) {
                logger.info("Tag '{}' encontrada no corpo da notícia da URL {}", tag.getTagNome(), noticia.getUrl());
                existeTagRelevante = true;

                // Verifica se os regionalismos da tag estão presentes
                for (Regionalismo regionalismo : tag.listRegionalismos()) {
                    if (corpo.contains(regionalismo.getNome())) {
                        logger.info("Regionalismo '{}' encontrado para a tag '{}' na URL {}", regionalismo.getNome(), tag.getTagNome(), noticia.getUrl());
                    } else {
                        logger.debug("Regionalismo '{}' não encontrado para a tag '{}' na URL {}", regionalismo.getNome(), tag.getTagNome(), noticia.getUrl());
                    }
                }
            } else {
                logger.debug("Tag '{}' não encontrada no corpo da notícia da URL {}", tag.getTagNome(), noticia.getUrl());
            }
        }

        return existeTagRelevante;
    }
}
