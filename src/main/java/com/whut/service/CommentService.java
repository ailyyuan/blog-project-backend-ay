package com.whut.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.whut.entity.*;
import com.whut.mapper.ArticleMapper;
import com.whut.mapper.ArticleOtmCommentMapper;
import com.whut.mapper.CommentMapper;
import com.whut.mapper.UserOtmCommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CommentService {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserService userService;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private ArticleOtmCommentMapper articleOtmCommentMapper;

    @Autowired
    private UserOtmCommentMapper userOtmCommentMapper;


    public  List<Comment> getAllArticleComments(Integer articleId) {
        System.out.println("getAllArticleComments Service"+articleId);
        // 查询文章信息
        Article article = articleService.getArticle(articleId);
        if (article != null) {
            // 查询文章关联的评论信息
            QueryWrapper<ArticleOtmComment> acqw=new QueryWrapper<>();
            acqw.eq("aid", articleId);
            List<ArticleOtmComment> articleOtmCommentList = articleOtmCommentMapper.selectList(acqw);

            // 根据文章关联的评论ID查询评论详情
            if (!articleOtmCommentList.isEmpty()) {
                QueryWrapper<Comment> aqw = new QueryWrapper<>();
                aqw.in("id", articleOtmCommentList.stream().map(ArticleOtmComment::getCommentId).toArray());
                List<Comment> comments = commentMapper.selectList(aqw);
                System.out.println(comments);
                return comments;
            }
        }

        return null;

    }
    public boolean addComment(Integer userId, Integer articleId, Comment comment) {
        if(comment != null&&userId!=null&&articleId!=null){
            commentMapper.insert(comment);
            System.out.println(comment);

            UserOtmComment userOtmComment = new UserOtmComment();
            userOtmComment.setUid(userId);
            userOtmComment.setCid(comment.getId());
            userOtmCommentMapper.insert(userOtmComment);
            System.out.println(userOtmComment);

            ArticleOtmComment articleOtmComment = new ArticleOtmComment();
            articleOtmComment.setAid(articleId);
            articleOtmComment.setCommentId(comment.getId());
            articleOtmCommentMapper.insert(articleOtmComment);
            System.out.println(articleOtmComment);

            return true;
        }
        return false;
    }

    public boolean deleteComment(Integer userId, Integer commentId){
        Integer uOcId=certifyComment(userId,commentId);
        Integer aOcId=getAOCId(commentId);
        if( uOcId!= null&&aOcId!=null){
            articleOtmCommentMapper.deleteById(aOcId);
            userOtmCommentMapper.deleteById(uOcId);
            commentMapper.deleteById(commentId);
            return true;
        }
        return false;
    }

    public Integer certifyComment(Integer uid, Integer cid){
        QueryWrapper<UserOtmComment> qw = new QueryWrapper<>();
        qw.eq("uid", uid);
        List<UserOtmComment> commentList = userOtmCommentMapper.selectList(qw);
        for(UserOtmComment info: commentList){
            if(Objects.equals(info.getCid(), cid)){
                return info.getId();
            }
        }
        return null;
    }

    public Integer getAOCId(Integer cid){
        QueryWrapper<ArticleOtmComment> qw = new QueryWrapper<>();
        qw.eq("comment_id", cid);
        ArticleOtmComment articleOtmComment = articleOtmCommentMapper.selectOne(qw);
        if(articleOtmComment!=null){
            return articleOtmComment.getId();
        }

        return null;
    }
}
