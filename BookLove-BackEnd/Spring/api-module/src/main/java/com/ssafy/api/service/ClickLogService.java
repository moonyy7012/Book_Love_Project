package com.ssafy.api.service;

import com.ssafy.api.dto.res.BookListInfoResDTO;
import com.ssafy.api.dto.res.BookRecentInfoResDTO;
import com.ssafy.api.dto.res.BookRecentListResDTO;
import com.ssafy.api.dto.res.PyBooksResDTO;
import com.ssafy.core.entity.Book;
import com.ssafy.core.exception.ApiMessageException;
import com.ssafy.core.repository.ClickLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Transactional
public class ClickLogService {
    private final ClickLogRepository clickLogRepository;

    @Transactional(readOnly = false)
    public List<BookRecentInfoResDTO> findBookRecentList(Long userId) {
        List<BookRecentInfoResDTO> resultList;
        List<Book> recentList;

        recentList = clickLogRepository.findBookRecentList(userId);
        resultList = IntStream.range(0, recentList.size())
                .mapToObj(i -> BookRecentInfoResDTO.builder()
                        .title(recentList.get(i).getTitle())
                        .cover(recentList.get(i).getCover())
                        .bookId(recentList.get(i).getBookId())
                        .description(recentList.get(i).getDescription()).build())
               .collect(Collectors.toList());

        return resultList;
    }

    public List<BookRecentInfoResDTO> findRecentSimilarBooks(Long userId) {
        List<PyBooksResDTO> similarBooks = WebClient.create().get()
                .uri("http://localhost:8000/recommend/books/" + userId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(new ApiMessageException("내부 서버 에러")))
                .bodyToFlux(PyBooksResDTO.class)
                .toStream()
                .collect(Collectors.toList());

        List<BookRecentInfoResDTO> resultList = IntStream.range(0, similarBooks.size())
                .mapToObj(i -> BookRecentInfoResDTO.builder()
                        .title(similarBooks.get(i).getTitle())
                        .cover(similarBooks.get(i).getCover())
                        .bookId(similarBooks.get(i).getBook_id())
                        .description(similarBooks.get(i).getDescription()).build())
                .collect(Collectors.toList());

        return resultList;
    }
}
