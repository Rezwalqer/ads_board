package ru.skypro.avito.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.skypro.avito.dto.CommentDto;
import ru.skypro.avito.mapper.CommentMapper;
import ru.skypro.avito.model.Comment;
import ru.skypro.avito.model.User;
import ru.skypro.avito.repository.AdsRepository;
import ru.skypro.avito.repository.CommentRepository;
import ru.skypro.avito.service.CommentService;
import ru.skypro.avito.service.UserService;

import java.time.Instant;
import java.util.Collection;
import java.util.stream.Collectors;

import static ru.skypro.avito.security.SecurityUtils.checkPermissionToAdsComment;

@RequiredArgsConstructor
@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final AdsRepository adsRepository;

    @Override
    public CommentDto addComment(Integer adKey, CommentDto dto) {
        Comment comment = CommentMapper.INSTANCE.toEntity(dto);
        User user = userService.findUserFromContext();
        comment.setAuthor(user);
        comment.setAds(adsRepository.findById(adKey).orElseThrow());
        comment.setCreatedAt(Instant.now());
        commentRepository.save(comment);
        log.info("Comment with ads id {} was added ", adKey);
        return CommentMapper.INSTANCE.toDto(comment);
    }

    @Override
    public Collection<CommentDto> getComments(Integer adKey) {
        log.info("Found all comments with ads id {} ", adKey);
        return commentRepository.findAllByAdsId(adKey)
                .stream()
                .map(CommentMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteComment(Integer adKey, Integer id) {
        Comment comment = commentRepository.findByIdAndAdsId(id, adKey).orElseThrow();
        commentRepository.delete(comment);
        log.info("Comment with id {} was deleted ", id);
    }

    @Override
    public CommentDto updateComment(Integer adKey, Integer id, CommentDto updateComment) {
        Comment comment = commentRepository.findByIdAndAdsId(id, adKey).orElseThrow();
        checkPermissionToAdsComment(comment);
        comment.setText(updateComment.getText());
        log.info("Comment with id {} was updated ", id);
        return CommentMapper.INSTANCE.toDto(commentRepository.save(comment));
    }
}
