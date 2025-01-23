package com.example.backend.service;

import com.example.backend.domain.Article;
import com.example.backend.dto.ArticleRequestDto;
import com.example.backend.dto.ArticleResponseDto;
import com.example.backend.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.modeler.AttributeInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleService {

	private final ArticleRepository articleRepository;

	// 글 작성
	@Transactional
	public ArticleResponseDto createArticle(ArticleRequestDto requestDto) {

		Article article = Article.builder()
				.title(requestDto.getTitle())
				.content(requestDto.getContent())
				.build();

		Article savedArticle = articleRepository.save(article);

		return ArticleResponseDto.toResponseDto(savedArticle);
	}

	// 글 조회
	public List<ArticleResponseDto> getArticles() {

		return   articleRepository.findAll()
				.stream()
				.map(ArticleResponseDto::toResponseDto)
				.collect(Collectors.toList());
	}

}