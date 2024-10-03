package com.group.backend.dto;

import java.time.LocalDate;
import java.util.List;

public class PortalTagDTO {
    private Long portalId;
    private String portalNome;
    private String portalUrl;
    private String portalFrequencia;
    private List<String> tags;
    private boolean ativo; 
    private LocalDate data;

    public PortalTagDTO(Long portalId, String portalNome, String portalUrl, String portalFrequencia, List<String> tags, boolean ativo, LocalDate data) {
        this.portalId = portalId;
        this.portalNome = portalNome;
        this.portalUrl = portalUrl;
        this.portalFrequencia = portalFrequencia;
        this.tags = tags;
        this.ativo = ativo; 
        this.data = data; // Adiciona a data
    }

    // Getters e Setters
    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Long getPortalId() {
        return portalId;
    }

    public void setPortalId(Long portalId) {
        this.portalId = portalId;
    }

    public String getPortalNome() {
        return portalNome;
    }

    public void setPortalNome(String portalNome) {
        this.portalNome = portalNome;
    }

    public String getPortalUrl() {
        return portalUrl;
    }

    public void setPortalUrl(String portalUrl) {
        this.portalUrl = portalUrl;
    }

    public String getPortalFrequencia() {
        return portalFrequencia;
    }

    public void setPortalFrequencia(String portalFrequencia) {
        this.portalFrequencia = portalFrequencia;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
