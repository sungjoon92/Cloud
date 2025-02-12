package com.example.backend.service;

import com.example.backend.domain.Article;
import com.example.backend.dto.ArticleRequestDto;
import com.example.backend.dto.ArticleResponseDto;
import com.example.backend.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final S3Service s3Service;
    /**
     * 새로운 게시글을 생성한다.
     *
     * @param requestDto 게시글 생성 요청 DTO
     * @return 생성된 게시글의 응답 DTO
     */
    @Transactional
    public ArticleResponseDto createArticle(ArticleRequestDto requestDto) {
        // S3에 파일을 업로드하고 접근 가능한 URL과 객체 키를 반환받음
        Map<String, String> uploadResult = s3Service.uploadFile(requestDto.getFile());

        String imageUrl = uploadResult.get("imageUrl");
        String s3Key = uploadResult.get("s3Key");

        // 게시글 엔티티를 생성한다
        Article article = Article.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .imageUrl(imageUrl)
                .originalFileName(requestDto.getFile().getOriginalFilename())
                .s3Key(s3Key)
                .build();

        // 게시글을 데이터베이스에 저장한다
        Article savedArticle = articleRepository.save(article);

        // 저장된 게시글을 응답 DTO로 변환하여 반환한다
        return toResponseDto(savedArticle);
    }

    @Transactional
    public List<ArticleResponseDto> getArticles() {

        return articleRepository.findAll().stream().map(this::toResponseDto).collect(Collectors.toList());
    }

    @Transactional
    public ArticleResponseDto getArticleById(Long id) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Article 정보가 존재하지 않습니다" + id));
        return toResponseDto(article);
    }


    /**
     * ID로 특정 게시글을 삭제한다.
     * S3에 저장된 이미지 파일도 함께 삭제한다.
     *
     * @param id 게시글 ID
     * @throws IllegalArgumentException 게시글이 존재하지 않을 경우
     */
    @Transactional
    public void deleteArticle(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Article not found with id: " + id));

        // S3에서 이미지 파일을 삭제한다
        s3Service.deleteFile(article.getS3Key());

        articleRepository.delete(article);
    }
    /**
     * Article 엔티티를 ArticleResponseDto로 변환한다.
     *
     * @param article 변환할 Article 엔티티
     * @return 변환된 ArticleResponseDto
     */
    private ArticleResponseDto toResponseDto(Article article) {

        return ArticleResponseDto.builder()
                .id(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .createdAt(article.getCreatedAt())
                .updatedAt(article.getUpdatedAt())
                .originalFileName(article.getOriginalFileName())
                .imageUrl(article.getImageUrl())
                .build();
    }

}