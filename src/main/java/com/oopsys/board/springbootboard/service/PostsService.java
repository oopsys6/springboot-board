package com.oopsys.board.springbootboard.service;

import com.oopsys.board.springbootboard.domain.posts.Posts;
import com.oopsys.board.springbootboard.domain.posts.PostsRepository;
import com.oopsys.board.springbootboard.web.dto.PostsListResponseDto;
import com.oopsys.board.springbootboard.web.dto.PostsResponseDto;
import com.oopsys.board.springbootboard.web.dto.PostsSaveRequestDto;
import com.oopsys.board.springbootboard.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public PostsResponseDto findById(Long id) {
        final Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));
        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }
}
