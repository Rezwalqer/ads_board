package ru.skypro.avito.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.avito.service.CommentService;
import ru.skypro.avito.dto.CommentDto;
import ru.skypro.avito.dto.ResponseWrapper;

@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
@RequestMapping("/ads")
@Tag(name = "Комментарии")
public class CommentController {
    private final CommentService commentService;

    @Operation(summary = "Получить комментарии объявления")
    @GetMapping("/{adKey}/comments")
    public ResponseWrapper<CommentDto> getComments(@PathVariable("adKey") Integer adKey) {
        return ResponseWrapper.of(commentService.getComments(adKey));
    }

    @Operation(summary = "Добавить комментарий к объявлению")
    @PostMapping("/{adKey}/comments")
    public ResponseEntity<CommentDto> addComments(@PathVariable("adKey") Integer adKey,
                                                     @RequestBody CommentDto commentDto) {
        return ResponseEntity.ok(commentService.addComment(adKey, commentDto));
    }

    @Operation(summary = "Удалить комментарий")
    @DeleteMapping("/{adKey}/comments/{id}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable("adKey") Integer adKey, @PathVariable("id") Integer id) {
        commentService.deleteComment(adKey, id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Обновить комментарий")
    @PatchMapping("/{adKey}/comment/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable("adKey") Integer adKey,
                                                       @PathVariable("id") Integer id,
                                                       @RequestBody CommentDto updatedCommentDto) {
        return ResponseEntity.ok(commentService.updateComment(adKey, id, updatedCommentDto));
    }
}