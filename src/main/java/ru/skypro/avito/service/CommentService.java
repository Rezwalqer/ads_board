package ru.skypro.avito.service;

import ru.skypro.avito.dto.CommentDto;

import java.util.Collection;
public interface CommentService {
    CommentDto addComment(Integer adKey, CommentDto comment);
    Collection<CommentDto> getComments(Integer adKey);
    void deleteComment(Integer adKey, Integer id);
    CommentDto updateComment(Integer adKey, Integer id, CommentDto updateComment);
}
