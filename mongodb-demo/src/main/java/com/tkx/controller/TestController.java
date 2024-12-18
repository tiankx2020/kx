package com.tkx.controller;

import com.tkx.entity.Comment;
import com.tkx.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author tkx
 * @Date 2024 12 10 22 40
 **/
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private CommentService commentService;

    @Resource
    private MongoTemplate mongoTemplate;

    /**
     * 通过id查询评论
     */
    @PostMapping("/testFindCommentById")
    public void testFindCommentById(){
        Comment commentById = commentService.findCommentById("1");
        System.out.println(commentById);

    }

    /**
     * 保存一个评论
     */
    @PostMapping("/testSaveComment")
    public void testSaveComment(){
        Comment comment = new Comment();
        comment.setArticleid("100000");
        comment.setContent("测试添加的数据");
        comment.setCreatedatetime(LocalDateTime.now());
        comment.setUserid("1006");
        comment.setNickname("xustudyxu");
        comment.setState("1");
        comment.setReplynum(0);
        comment.setLikenum(0);
        commentService.saveComment(comment);
    }

    /**
     * 查询全部评论
     */
    @PostMapping("/testFindCommentList")
    public void testFindCommentList(){
        List<Comment> commentList = commentService.findCommentList();
        commentList.forEach(System.out::println);
    }

    /**
     * 测试根据父id查询子评论的分页列表
     */
    @PostMapping("/testFindCommentListByParentid")
    public void testFindCommentListByParentid(){
        Page<Comment> pageResponse = commentService.findCommentListByParentid("1006", 1, 2);
        System.out.println("---总记录数---:"+pageResponse.getTotalElements());
        System.out.println("---当前页数据---:"+pageResponse.getContent());

    }

    @PostMapping("/updateCommentLikeNum")
    public void updateCommentLikeNum(String userid){

        //查询条件
        Query query = Query.query(Criteria.where("userid").is(userid));

        //更新条件
        Update update = new Update();

        //局部更新，相当于$set
        // update.set(key,value)
        //递增$inc
        update.inc("likenum",1);


        //参数1：查询对象
        //参数2：更新对象
        //参数3：集合的名字或实体类的类型Comment.class
        mongoTemplate.updateFirst(query,update,Comment.class);
    }
}
