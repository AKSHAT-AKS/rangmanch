package com.rangmanch.title;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TitleRepository extends JpaRepository<Title, Long> {
    List<Title> findByNameContainingIgnoreCaseOrderByNameAsc(String query);
}