package com.tkx.service;

import com.tkx.dao.CommentRepository;
import com.tkx.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author tkx
 * @Date 2024 12 10 22 38
 **/
@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    /**
     * 保存一个评论
     *
     * @param comment
     */
    public void saveComment(Comment comment) {
        //如果需要自定义主键，可以在这里指定主键；如果不指定主键，MongoDB会自动生成主键
        //设置一些默认初始值。。。
        //调用dao
        commentRepository.save(comment);
    }

    /**
     * 更新评论
     *
     * @param comment
     */
    public void updateComment(Comment comment) {
        //调用dao
        commentRepository.save(comment);
    }

    /**
     * 根据id删除评论
     *
     * @param id
     */
    public void deleteCommentById(String id) {
        //调用dao
        commentRepository.deleteById(id);
    }


    public void deleteByUserid(String userid){
        // Page<Comment> resultList = commentRepository.findByUserid(userid, PageRequest.of(0, 10000));
        // Set<String> collect = resultList.stream().map(Comment::getId).collect(Collectors.toSet());
        // commentRepository.deleteAllById(collect);

        commentRepository.deleteByUserid(userid);
    }
    /**
     * 查询所有评论
     *
     * @return
     */
    public List<Comment> findCommentList() {
        //调用dao
        return commentRepository.findAll();
    }

    /**
     * 根据id查询评论
     *
     * @param id
     * @return
     */
    public Comment findCommentById(String id) {
        //调用dao
        return commentRepository.findById(id).get();
    }

    /**
     * 根据parentid查询分页列表
     * @param userid
     * @param page
     * @param size
     * @return
     */
    public Page<Comment> findCommentListByParentid(String userid, int page, int size){
        return commentRepository.findByUserid(userid, PageRequest.of(page-1,size));
    }

    public Page<Comment> findCommentListByContent(String content,int page,int size){
        return commentRepository.findByContent(content,PageRequest.of(page-1,size));
    }

}

