package com.group.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class NoticiaController {

    public static class Noticia {
        public String titulo;
        public String portal;
        public String jornalista;
        public Date data;
        public List<String> categorias;
        public String texto;  // Novo campo
    
        public Noticia(String titulo, String portal, String jornalista, Date data, List<String> categorias, String texto) {
            this.titulo = titulo;
            this.portal = portal;
            this.jornalista = jornalista;
            this.data = data;
            this.categorias = categorias;
            this.texto = texto;
        }
    }
    

    // Mock de notícias
    private static List<Noticia> mockNoticias = new ArrayList<>() {{
        add(new Noticia("Notícia Exemplo 1", "Portal Exemplo", "Jornalista Exemplo", new Date(1672531200000L), List.of("soja", "agricultura"), "Texto completo da notícia exemplo 1."));
        add(new Noticia("Notícia Exemplo 2", "Portal Exemplo 2", "Outro Jornalista", new Date(1675209600000L), List.of("política", "economia"), "Texto completo da notícia exemplo 2."));
        add(new Noticia("Notícia Exemplo 3", "Portal 3", "Jornalista 3", new Date(1677628800000L), List.of("tecnologia", "educação"), "Texto completo da notícia exemplo 3."));
        add(new Noticia("Notícia Exemplo 4", "Portal 4", "Jornalista 4", new Date(1656633600000L), List.of("ciência", "saúde"), "Texto completo da notícia exemplo 4."));
    }};
    

    // Endpoint para buscar notícias com novos filtros
    @GetMapping("/noticias")
    public List<Noticia> getNoticias(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) List<String> tag, // Agora aceita múltiplas tags
            @RequestParam(required = false) List<String> portal, // Agora aceita múltiplos portais
            @RequestParam(required = false) Long startDate, // Data de início em timestamp
            @RequestParam(required = false) Long endDate // Data de fim em timestamp
    ) {
        return mockNoticias.stream()
                // Filtrar por palavra-chave
                .filter(noticia -> keyword == null || noticia.titulo.toLowerCase().contains(keyword.toLowerCase()))
                
                // Filtrar por tags (OU inclusivo)
                .filter(noticia -> tag == null || tag.isEmpty() || 
                        noticia.categorias.stream().anyMatch(t -> tag.contains(t.toLowerCase())))
                
                // Filtrar por portais (OU inclusivo)
                .filter(noticia -> portal == null || portal.isEmpty() || 
                        portal.contains(noticia.portal.toLowerCase()))
                
                // Filtrar por data
                .filter(noticia -> {
                    if (startDate != null || endDate != null) {
                        Date start = startDate != null ? new Date(startDate) : new Date(Long.MIN_VALUE);
                        Date end = endDate != null ? new Date(endDate) : new Date(Long.MAX_VALUE);
                        return noticia.data.after(start) && noticia.data.before(end);
                    }
                    return true;
                })
                .collect(Collectors.toList());
    }
}
