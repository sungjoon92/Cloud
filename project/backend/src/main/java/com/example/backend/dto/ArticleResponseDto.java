package com.example.backend.dto;

import com.example.backend.domain.Article;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleResponseDto {
	private Long id;
	private String title;
	private String content;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	// build()Dto
	public static ArticleResponseDto toResponseDto(Article article) {

		return ArticleResponseDto.builder()
				.id(article.getId())
				.title(article.getTitle())
				.content(article.getContent())
				.createdAt(article.getCreatedAt())
				.updatedAt(article.getUpdatedAt())
				.build();

	}
}

