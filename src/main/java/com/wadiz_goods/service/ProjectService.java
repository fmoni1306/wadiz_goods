package com.wadiz_goods.service;

import com.wadiz_goods.controller.form.ProjectForm;
import com.wadiz_goods.domain.member.Member;
import com.wadiz_goods.domain.project.Buy;
import com.wadiz_goods.domain.project.Image;
import com.wadiz_goods.domain.project.Project;
import com.wadiz_goods.domain.project.Tag;
import com.wadiz_goods.repository.ImageRepository;
import com.wadiz_goods.repository.MemberRepository;
import com.wadiz_goods.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ImageRepository imageRepository;
    private final MemberRepository memberRepository;
    private static final Logger logger = LoggerFactory.getLogger(ProjectService.class);


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

    @Transactional
    public Long buy(User user, Long id) {
        Member member = memberRepository.findByMemberName(user.getUsername());
        Project project = projectRepository.findOne(id);
        if (duplicateBuy(project, member)) {
            Buy buy = Buy.createBuy(
                    project, member
            );

            projectRepository.save(buy);

            return project.getId();
        }else{
            return 0L;
        }


    }

    private Boolean duplicateBuy(Project project, Member member) {
        List<Buy> buyList = projectRepository.findOneBuy(project, member);

        if (!buyList.isEmpty()){
//            throw new IllegalStateException("?????? ??????????????? ?????????????????????.");
            return false;
        }
        return true;
    }

    // ????????? ??????
    public List<Image> parseImageInfo(List<MultipartFile> multipartFiles)
            throws Exception {
        // ????????? ?????? ?????????
        List<Image> imageList = new ArrayList<>();

        // ????????? ????????? ????????? ??????
        if (!CollectionUtils.isEmpty(multipartFiles)) {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter =
                    DateTimeFormatter.ofPattern("yyyyMMdd");
            String current_date = now.format(dateTimeFormatter);

            // ???????????? ???????????? ?????? ????????? ?????? ?????? ?????? ??????
            // ?????? ????????? File.separator ??????
            String absolutePath = new File("").getAbsolutePath() + File.separator + File.separator;

            // ????????? ????????? ?????? ?????? ??????
            String path = "images" + File.separator + current_date;
            File file = new File(path);

            // ??????????????? ???????????? ?????? ??????
            if (!file.exists()) {
                boolean wasSuccessful = file.mkdirs();

                // ???????????? ????????? ???????????? ??????
                if (!wasSuccessful)
                    System.out.println("file: was not successful");
            }

            // ?????? ?????? ??????
            for (MultipartFile multipartFile : multipartFiles) {
                // ????????? ????????? ??????
                String originalFileExtension;
                String contentType = multipartFile.getContentType();

                // ??????????????? ???????????? ?????? ?????? ?????? x
                if (ObjectUtils.isEmpty(contentType)) {
                    break;
                } else { // ???????????? jpeg, png??? ???????????? ????????? ??????

                    if (contentType.contains("image/jpeg"))
                        originalFileExtension = ".jpg";
                    else if (contentType.contains("image/png"))
                        originalFileExtension = ".png";
                    else // ?????? ???????????? ?????? ?????? x
                        break;
                }

                // ????????? ????????? ????????? ?????? ??????????????? ??????
                String newImageName = System.nanoTime() + originalFileExtension;

                // ?????? DTO ??????
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

                // ?????? ??? ???????????? ??????
                imageList.add(image);

                // ????????? ??? ?????? ???????????? ????????? ????????? ??????
                file = new File(absolutePath + path + File.separator + newImageName);
                multipartFile.transferTo(file);

                // ?????? ?????? ??????(??????, ??????)
                file.setWritable(true);
                file.setReadable(true);

            }
        }

        return imageList;
    }

    // ???????????? ?????? ??????
    public List<Project> findProjects() {
        return projectRepository.findAll();
    }

    public Project findProject(Long id) {
        return projectRepository.findOne(id);
    }

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void testScheduler() {
        LocalDate today = LocalDate.now();

        List<Project> projectList = projectRepository.findAll();

        for (Project project: projectList) {
            if (!project.getIsStart()) {
                if (project.getPeriodStart().isEqual(today)) {
                    projectRepository.startUpdate(project);
                }
            }else {
                if (project.getPeriodEnd().isEqual(today)) {
                    projectRepository.endUpdate(project);
                }
            }
        }


        logger.info("=====" + today + "???????????? ?????? " + "=====");

    }


}
