package com.example.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

    // AWS S3 서비스와 상호작용하기 위한 클라이언트
    private final S3Client s3Client;

    // S3 버킷 이름
    @Value("${BUCKET_NAME}")
    private String bucketName;

    // AWS 리전
    @Value("${REGION}")
    private String region;

    // S3에 저장되는 파일의 기본 경로
    private static final String FILE_PATH_PREFIX = "articles/";

    // s3 파일 업로드 처리 메서드
    // 파일을(file)을 articleService에서 받은 후
    // controller에서 받은 후
    // s3 업로드 후 imageURL 과 객체 키를 반환하는 메서드
    public Map<String, String> uploadFile(MultipartFile file) {
        // s3Key 생성
        String s3Key = FILE_PATH_PREFIX + UUID.randomUUID() + "_" + file.getOriginalFilename();

        // s3 버킷에 파일을 업로드
        // 업로드할 file 과 s3 객체 키(s3Key)를 전달
        uploadFileToS3(s3Key,file);

        String IMAGE_URL_FORMAT = "https://%s.s3.%s.amazonaws.com/%s";
        String imageUrl = String.format(IMAGE_URL_FORMAT, bucketName, region, s3Key);

        return Map.of(
                "imageUrl", imageUrl,
                "s3Key", s3Key
        );
    };

    private void uploadFileToS3(String s3Key, MultipartFile file) {
        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(s3Key)
                    .contentType(file.getContentType())
                    .contentLength(file.getSize())
                    .build();

            s3Client.putObject(putObjectRequest,
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        // S3 URL 형식 (%s: 버킷명, 리전, 파일경로가 순서대로 들어감)
    }

    /**
     * S3 객체를 삭제한다.
     *
     * @param s3Key S3 파일 객체 키
     * @throws RuntimeException 파일 삭제 실패 시 발생
     */
    public void deleteFile(String s3Key) {
        try {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(s3Key)
                    .build();
            s3Client.deleteObject(deleteObjectRequest);
        } catch (Exception e) {
            throw new RuntimeException("파일 삭제 실패: " + e.getMessage());
        }
    }
}