package com.example.backend.controller;

import com.example.backend.dto.ArticleRequestDto;
import com.example.backend.dto.ArticleResponseDto;
import com.example.backend.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 모든 요청에 대해 /api/ 경로를 붙여준다.
@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class ArticleController {
	private final ArticleService articleService;


	@GetMapping("articles")
	public List<ArticleResponseDto> getArticles() {
		return articleService.getArticles();
	}

	@PostMapping("articles")
	public ArticleResponseDto createArticle(@RequestBody ArticleRequestDto requestDto) {
		return articleService.createArticle(requestDto);
	}

}
