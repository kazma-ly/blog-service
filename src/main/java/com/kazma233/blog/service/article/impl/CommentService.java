package com.kazma233.blog.service.article.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kazma233.blog.config.properties.MyConfig;
import com.kazma233.blog.dao.article.CommentDao;
import com.kazma233.blog.entity.article.Article;
import com.kazma233.blog.entity.comment.Comment;
import com.kazma233.blog.entity.comment.enums.CommentStatus;
import com.kazma233.blog.entity.comment.vo.*;
import com.kazma233.blog.service.article.IArticleService;
import com.kazma233.blog.service.article.ICommentService;
import com.kazma233.blog.utils.ShiroUtils;
import com.kazma233.common.ThreadPoolUtils;
import com.kazma233.common.Utils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class CommentService implements ICommentService {

    private CommentDao commentDao;
    private IArticleService articleService;
    private JavaMailSender mailSender;
    private MyConfig myConfig;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void commit(CommentAdd commentAdd) {

        Comment comment = Comment.builder().
                id(Utils.generateID()).
                articleId(commentAdd.getArticleId()).
                content(commentAdd.getContent()).
                email(commentAdd.getEmail()).
                net(commentAdd.getNet()).
                nickname(commentAdd.getNickname()).
                referId(commentAdd.getReferId()).
                regerOriginId(commentAdd.getRegerOriginId()).
                createTime(LocalDateTime.now()).
                status(CommentStatus.SHOW.getCode()).
                uid(ShiroUtils.getUid()).
                build();

        commentDao.insert(comment);

        ThreadPoolUtils.getCachedThreadPool().execute(() -> sendEmail(comment));
    }

    @Override
    public PageInfo<CommentArticleTitleVO> queryAllCommentAndArticleTitle(CommentQuery commentQuery) {
        commentQuery.setUid(ShiroUtils.getUid());
        PageHelper.startPage(commentQuery.getPageNo(), commentQuery.getPageSize());
        List<CommentArticleTitleVO> commentArticleTitleList = commentDao.queryAllAndArticleTitleByArgs(commentQuery);

        return new PageInfo<>(commentArticleTitleList);
    }

    @Override
    public PageInfo queryArticleComment(CommentArticleQuery commentArticleQuery) {
        PageHelper.startPage(commentArticleQuery.getPageNo(), commentArticleQuery.getPageSize());
        List<Comment> comments = commentDao.queryAllShowByArticleId(commentArticleQuery);

        return new PageInfo<>(comments);
    }

    @Override
    public void updateStatues(CommentUpdate commentUpdate) {
        commentDao.updateStatus(commentUpdate);
    }

    private void sendEmail(Comment comment) {
        Article article = articleService.findById(comment.getArticleId());
        String content = article.getTitle() + ": \n" + comment.getNickname() + ": " + comment.getContent();

        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(myConfig.getMailUsername());
            simpleMailMessage.setTo("a781683161@qq.com");
            simpleMailMessage.setSubject("有新的评论");
            simpleMailMessage.setText(content);
            mailSender.send(simpleMailMessage);
        } catch (Exception e) {
            log.error("发送邮件失败: ", e);
        }

    }

}
