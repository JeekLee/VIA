package com.via.content.infra.skill.search.utils;

import com.via.content.infra.skill.search.entity.AliasEmbedding;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class SkillEmbeddingProvider {
    private final EmbeddingModel embeddingModel;

    public List<AliasEmbedding> createAliasEmbedding(List<String> aliases) {
        if (aliases == null || aliases.isEmpty()) return List.of();
        List<float[]> embeddings = embeddingModel.embed(aliases);
        return IntStream.range(0, aliases.size())
                .mapToObj(i -> new AliasEmbedding(aliases.get(i), embeddings.get(i)))
                .toList();
    }
}
