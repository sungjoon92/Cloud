package com.example.backend.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Table(name = "articles")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Article {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false, length = 1000)
	private String content;

	/**
	 * 사용자가 업로드한 원본 이미지 파일명
	 * 웹 서비스 화면에 표시할 파일명
	 * 예: profile.jpg
	 */
	@Column(nullable = true)
	private String originalFileName;

	/**
	 * S3에 업로드된 이미지의 접근 가능한 URL
	 * 이미지를 웹에서 표시하기 위한 전체 URL
	 * 예: https://bucket.s3.region.amazonaws.com/articles/uuid_profile.jpg
	 */
	@Column(nullable = true)
	private String imageUrl;

	/**
	 * S3 버킷에서 객체를 식별하는 키(key)
	 * 버킷 내 객체의 고유 식별자로 전체 경로를 포함
	 * 예: articles/uuid_profile.jpg
	 */
	@Column(nullable = true)
	private String s3Key;



	@CreatedDate
	@Column(nullable = true, updatable = false)
	private LocalDateTime createdAt;

	@LastModifiedDate
	@Column(nullable = true)
	private LocalDateTime updatedAt;
}

