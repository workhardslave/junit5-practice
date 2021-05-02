package com.proj.junit5.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

// Entity 클래스와 Entity Repository를 같은 디렉토리에서 관리하는게 좋음
// 나중에 프로젝트 규모가 커져 도메인별로 프로젝트를 분리해야 한다면, Entity 클래스와 Repository 클래스는 함께 움직여야 함
public interface PostsRepository extends JpaRepository<Posts, Long> {
}
