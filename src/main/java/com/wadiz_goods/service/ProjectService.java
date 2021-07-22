package com.wadiz_goods.service;

import com.wadiz_goods.controller.form.ProjectForm;
import com.wadiz_goods.domain.member.Member;
import com.wadiz_goods.domain.project.Image;
import com.wadiz_goods.domain.project.Project;
import com.wadiz_goods.domain.project.Tag;
import com.wadiz_goods.repository.ImageRepository;
import com.wadiz_goods.repository.MemberRepository;
import com.wadiz_goods.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ImageRepository imageRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long create(ProjectForm form, User user, String[] tags, List<MultipartFile> images) throws Exception {
        Member member = memberRepository.findByMemberName(user.getUsername());
        List<Tag> tagList = new ArrayList<>();

        Project project = Project.createProject(
                form.getTitle(),
                member,
                form.getContent(),
                form.getPrice(),
                form.getPurposePrice(),
                form.getPeriodStart(),
                form.getPeriodEnd()
        );

        List<Image> imageList = parseImageInfo(images);

        if (!imageList.isEmpty()) {
            for (Image image : imageList) {
                project.addImages(imageRepository.save(image));
            }
        }

        project.setProvider(member);

        for (String a : tags) {
            Tag tag = Tag.createTag(a, project);
            tagList.add(tag);
            projectRepository.save(tag);
        }
        projectRepository.save(project);


        return project.getId();

    }

    // 이미지 저장
    public List<Image> parseImageInfo(List<MultipartFile> multipartFiles)
            throws Exception {
        // 반환할 파일 리스트
        List<Image> imageList = new ArrayList<>();

        // 전달된 파일이 존재할 경우
        if (!CollectionUtils.isEmpty(multipartFiles)) {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter =
                    DateTimeFormatter.ofPattern("yyyyMMdd");
            String current_date = now.format(dateTimeFormatter);

            // 프로젝트 디렉터리 내의 저장을 위한 절대 경로 설정
            // 경로 구분자 File.separator 사용
            String absolutePath = new File("").getAbsolutePath() + File.separator + File.separator;

            // 파일을 저장할 세부 경로 지정
            String path = "src\\main\\resources\\static\\images" + File.separator + current_date;
            File file = new File(path);

            // 디렉터리가 존재하지 않을 경우
            if (!file.exists()) {
                boolean wasSuccessful = file.mkdirs();

                // 디렉터리 생성에 실패했을 경우
                if (!wasSuccessful)
                    System.out.println("file: was not successful");
            }

            // 다중 파일 처리
            for (MultipartFile multipartFile : multipartFiles) {
                // 파일의 확장자 추출
                String originalFileExtension;
                String contentType = multipartFile.getContentType();

                // 확장자명이 존재하지 않을 경우 처리 x
                if (ObjectUtils.isEmpty(contentType)) {
                    break;
                } else { // 확장자가 jpeg, png인 파일들만 받아서 처리

                    if (contentType.contains("image/jpeg"))
                        originalFileExtension = ".jpg";
                    else if (contentType.contains("image/png"))
                        originalFileExtension = ".png";
                    else // 다른 확자자일 경우 처리 x
                        break;
                }

                // 파일명 중복을 피하기 위해 나노초까지 설정
                String newImageName = System.nanoTime() + originalFileExtension;

                // 파일 DTO 생성
                Image imageDto = Image.builder()
                        .originImageName(multipartFile.getOriginalFilename())
                        .imagePath(current_date + File.separator + newImageName)
                        .imageSize(multipartFile.getSize())
                        .build();

                Image image = new Image(
                        imageDto.getOriginImageName(),
                        imageDto.getImagePath(),
                        imageDto.getImageSize()
                );

                // 생성 후 리스트에 추가
                imageList.add(image);

                // 업로드 한 파일 데이터를 지정한 파일에 저장
                file = new File(absolutePath + path + File.separator + newImageName);
                multipartFile.transferTo(file);

                // 파일 권한 설정(쓰기, 읽기)
                file.setWritable(true);
                file.setReadable(true);

            }
        }

        return imageList;
    }

    // 프로젝트 전체 조회
    public List<Project> findProjects() {
        return projectRepository.findAll();
    }

    public Project findProject(Long id) {
        return projectRepository.findOne(id);
    }
}
