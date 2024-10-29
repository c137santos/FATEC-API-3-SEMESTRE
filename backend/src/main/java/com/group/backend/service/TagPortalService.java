package com.group.backend.service;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group.backend.domain.PortalRepository;
import com.group.backend.domain.TagPortalRepository;
import com.group.backend.domain.TagRepository;
import com.group.backend.entity.Portal;
import com.group.backend.entity.TagPortal;
import com.group.backend.entity.Tag;

@Service
public class TagPortalService {

    private final TagPortalRepository tagPortalRepository;
    private final TagRepository tagRepository;
    private final PortalRepository portalRepository;

    @Autowired
    public TagPortalService(TagPortalRepository tagPortalRepository, TagRepository tagRepository, PortalRepository portalRepository){
        this.tagPortalRepository = tagPortalRepository;
        this.tagRepository = tagRepository;
        this.portalRepository = portalRepository;
    }

    public List<TagPortal> cadastrarTagPortal(List<Long> tagsSelecionadas, Portal portal) {
        List<TagPortal> tagPortais = new ArrayList<>();
        
        for(Long tagId : tagsSelecionadas) {
            Tag tag = tagRepository.findById(tagId).orElseThrow(() -> new IllegalArgumentException("Tag not found with id: " + tagsSelecionadas));
            TagPortal tp = new TagPortal(tag, portal);
            tagPortais.add(tp);
            tagPortalRepository.save(tp);
        }
        return tagPortais;
        }
    
    public List<Map<String, Object>> listarPortaisComTags() {
        List<Portal> portais = portalRepository.findAll();
        List<Map<String, Object>> resposta = new ArrayList<>();

        for (Portal portal : portais) {
            Map<String, Object> portalMap = new HashMap<>();
            portalMap.put("id", portal.getId());
            portalMap.put("nome", portal.getNome());
            portalMap.put("url", portal.getUrl());
            portalMap.put("frequencia", portal.getFrequencia());
            portalMap.put("ativo", portal.isAtivo());
            portalMap.put("data", portal.getData());
            List<String> nomesTags = listarTagsPortal(portal.getId());
            portalMap.put("tags", nomesTags);

            resposta.add(portalMap);
        }
        return resposta;
    }

    public List<String> listarTagsPortal(Long portalId) {
        Iterable<Long> IdsTags = tagPortalRepository.getAllTagsByPortal(portalId);
        List<Tag> listaTags = tagRepository.findAllById(IdsTags);

        List<String> nomesTags = new ArrayList<>();
        for (Tag tag : listaTags) {
            nomesTags.add(tag.getTagNome());
        }
        return nomesTags;
    }
}
