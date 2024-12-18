package com.tkx.dao;

import com.tkx.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Author tkx
 * @Date 2024 12 10 22 37
 **/
//desc:评论的持久层接口
public interface CommentRepository extends MongoRepository<Comment,String> {

    //根据父id，查询子评论的分页列表
    Page<Comment> findByUserid(String userid, Pageable pageable);
}
