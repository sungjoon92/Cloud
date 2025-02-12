package com.example.backend.controller;

import com.example.backend.dto.ArticleRequestDto;
import com.example.backend.dto.ArticleResponseDto;
import com.example.backend.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class ArticleController {
	private final ArticleService articleService;


	@GetMapping
	public List<ArticleResponseDto> getArticles() {
		return articleService.getArticles();
	}

	/**
	 * 새로운 게시글을 생성한다.
	 *
	 * @param title 게시글 제목
	 * @param content 게시글 내용
	 * @param file 업로드할 이미지 파일
	 * @return 생성된 게시글의 응답 DTO
	 */
	@PostMapping
	public ArticleResponseDto createArticle(
			// @RequestParam은 HTTP 요청 폼 데이터를 메서드의 파라미터로 입력한다.
			// 폼 데이터의 각 Key와 파라미터를 매핑한다.
			@RequestParam("title") String title,
			@RequestParam("content") String content,
			@RequestParam("file") MultipartFile file) {

		ArticleRequestDto requestDto = ArticleRequestDto.builder()
				.title(title)
				.content(content)
				.file(file)
				.build();

		return articleService.createArticle(requestDto);
	}

  	@GetMapping("/{id}")
	public ArticleResponseDto getArticleById(@PathVariable Long id) {
		return articleService.getArticleById(id);
	}

	// 게시글 삭제
	@DeleteMapping("/{id}")
	public String  deleteArticleById(@PathVariable Long id) {
		articleService.deleteArticle(id);
		return "삭제완료";
	}// deleteArticleById() end



}
