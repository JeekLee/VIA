package com.via.content.infra.skill.search.entity;

public record AliasEmbedding (
        String alias,
        float[] vector
) {
}
