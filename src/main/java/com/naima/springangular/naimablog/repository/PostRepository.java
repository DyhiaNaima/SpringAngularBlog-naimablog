package com.naima.springangular.naimablog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.naima.springangular.naimablog.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}
