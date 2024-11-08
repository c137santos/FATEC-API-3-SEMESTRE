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

    @Transactional
    public boolean checkTagsAndRegionalismosInCorpo(String corpo, Noticia noticia) {
        Long portalId = noticia.getPorId().longValue();
        Iterable<Long> tagIds = tagPortalRepository.getAllTagsByPortal(portalId);
        List<Tag> tags = new ArrayList<>();

        tagIds.forEach(tagId -> {
            Tag tag = tagRepository.findByIdWithRegionalismos(tagId).orElse(null);
            if (tag != null) {
                tags.add(tag);
            }
        });

        boolean existeTagRelevante = false;

        for (Tag tag : tags) {
            if (corpo.contains(tag.getTagNome())) {
                logger.info("Tag '{}' encontrada no corpo da notícia da URL {}", tag.getTagNome(), noticia.getUrl());
                existeTagRelevante = true;

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
