package com.proj.junit5.domain;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 통합 테스트시 테스트 메서드의 순서는 보장이 되지 않는다.
 * 테서트 메서드가 하나 실행 후 종료되면 데이터가 rollback 되는데, auto_increment값은 초기화 되지 않는다.
 * 참고로 이는 @Transactional 어노테이션과 연관있다.
 */
@DataJpaTest // DB와 관련된 컴포넌트만 메모리에 로딩
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;
//    @Autowired
//    private EntityManager entityManager;

    /**
     * @Before
     * @BeforeAll : 테스트 시작 전에 한번만 실행
     * @BeforeEach : 각 테스트 시작 전에 한번씩 실행, `@BeforeEach + 테스트 코드`가 하나의 트랜잭션이 된다.
     */
    @BeforeEach
    public void initDummyData() {
        final String title = "test-code";
        final String author = "hsh";
        final String isbn = "1234-5678-90";
        Book book = Book.builder()
                .title(title)
                .author(author)
                .isbn(isbn)
                .build();
        bookRepository.save(book);
    }

    /**
     * @After
     * @AfterAll
     * @AfterEach
     */
    /*@AfterEach
    public void initExecutedData() {
        final String query = "ALTER TABLE book ALTER COLUMN id RESTART WITH 1";
        bookRepository.deleteAll();;
        entityManager
                .createNativeQuery(query)
                .executeUpdate();
    }*/

    /**
     * 데이터 등록 과정
     * 클라이언트 -> BookApiController -> BookSaveRequestDto -> BookService -> Book -> BookRepository
     */
    @DisplayName("책을 등록한다.")
    @Test
    void 책_등록_test() {
        // given (데이터 준비)
        final Long id = 2L;
        final String title = "junit5";
        final String author = "workhardslave";
        final String isbn = "1234-5678-90";
        Book book = Book.builder()
                .title(title)
                .author(author)
                .isbn(isbn)
                .build();

        // when (테스트 실행)
        Book savedBook = bookRepository.save(book);

        // then (검증)
//        assertEquals(id, savedBook.getId());
        assertEquals(title, savedBook.getTitle());
        assertEquals(author, savedBook.getAuthor());
    }

    @DisplayName("책 목록을 조회한다.")
    @Test
    void 책_목록_조회_test() {
        // given
        final Long id = 1L;
        final String title = "test-code";
        final String author = "hsh";
        final String isbn = "1234-5678-90";

        // when
        List<Book> bookList = bookRepository.findAll();

        // then
//        assertEquals(id, bookList.get(0).getId());
        assertEquals(title, bookList.get(0).getTitle());
        assertEquals(author, bookList.get(0).getAuthor());
        assertEquals(isbn, bookList.get(0).getIsbn());
    }

    @Sql("classpath:db/tableInit.sql")
    @DisplayName("책 한권을 조회한다.")
    @Test
    void 책_한권_조회_test() {
        // given
        final Long id = 1L;
        final String title = "test-code";
        final String author = "hsh";
        final String isbn = "1234-5678-90";

        // when
        Book foundBook = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("없는 아이디 입니다. " + id));

        // then
        assertEquals(id, foundBook.getId());
        assertEquals(title, foundBook.getTitle());
        assertEquals(author, foundBook.getAuthor());
        assertEquals(isbn, foundBook.getIsbn());
    }

    @Sql("classpath:db/tableInit.sql")
    @DisplayName("책을 삭제한다.")
    @Test
    void 책_삭제_test() {
        // given
        final Long id = 1L;

        // when
        bookRepository.deleteById(id);
        
        // then
        assertThrows(IllegalArgumentException.class,
                () -> bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("없는 아이디 입니다. " + id)));
    }

    @Sql("classpath:db/tableInit.sql") // @BeforeEach에서 insert한 값이 @Sql 어노테이션에 의해 삭제가 되어야 하는데, 왜 삭제가 안됐을까?
    @DisplayName("책을 수정한다.")
    @Test
    void 책_수정_test() {
        // given
        final Long id = 1L;
        final String title = "junit5";
        final String author = "workhardslave";
        final String isbn = "1234-5678-90";
        Book book = new Book(id, title, author, isbn);

        // when
        Book updatedBook = bookRepository.save(book);

        // then
        assertEquals(1, bookRepository.findAll().size());
        assertEquals(title, updatedBook.getTitle());
        assertEquals(author, updatedBook.getAuthor());
        assertEquals(isbn, updatedBook.getIsbn());
    }
}