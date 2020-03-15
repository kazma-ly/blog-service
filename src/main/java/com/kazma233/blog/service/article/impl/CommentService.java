package com.kazma233.blog.service.article.impl;

import com.kazma233.blog.config.properties.EmailConfig;
import com.kazma233.blog.dao.article.CommentDao;
import com.kazma233.blog.entity.article.Article;
import com.kazma233.blog.entity.comment.Comment;
import com.kazma233.blog.entity.comment.enums.CommentStatus;
import com.kazma233.blog.entity.comment.vo.*;
import com.kazma233.blog.entity.common.PageInfo;
import com.kazma233.blog.service.article.IArticleService;
import com.kazma233.blog.service.article.ICommentService;
import com.kazma233.blog.utils.ThreadPoolUtils;
import com.kazma233.blog.utils.UserUtils;
import com.kazma233.blog.utils.id.IDGenerater;
import com.kazma233.blog.utils.pageable.PageableUtils;
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
    private EmailConfig emailConfig;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void commit(CommentAdd commentAdd) {

        Comment comment = Comment.builder().
                id(IDGenerater.generateID()).
                articleId(commentAdd.getArticleId()).
                content(commentAdd.getContent()).
                email(commentAdd.getEmail()).
                net(commentAdd.getNet()).
                nickname(commentAdd.getNickname()).
                referId(commentAdd.getReferId()).
                regerOriginId(commentAdd.getRegerOriginId()).
                createTime(LocalDateTime.now()).
                status(CommentStatus.SHOW.getCode()).
                uid(UserUtils.getUserId()).
                build();

        commentDao.insert(comment);

        ThreadPoolUtils.getCommonThreadPool().execute(() -> sendEmail(comment));
    }

    @Override
    public PageInfo queryAllCommentAndArticleTitle(CommentQuery commentQuery) {
        commentQuery.setUid(UserUtils.getUserId());

        List<CommentArticleTitleVO> commentArticleTitleList = commentDao.queryAllAndArticleTitleByArgs(commentQuery);
        Long total = commentDao.queryAllAndArticleTitleByArgsSize(commentQuery);

        return PageableUtils.pageInfo(total, commentQuery, commentArticleTitleList);
    }

    @Override
    public PageInfo queryArticleComment(CommentArticleQuery commentArticleQuery) {
        List<Comment> comments = commentDao.queryAllShowByArticleId(commentArticleQuery);
        Long total = commentDao.queryAllShowByArticleIdSize(commentArticleQuery);

        return PageableUtils.pageInfo(total, commentArticleQuery, comments);
    }

    @Override
    public void updateStatues(CommentUpdate commentUpdate) {
        commentDao.updateStatus(commentUpdate);
    }

    private void sendEmail(Comment comment) {
        Article article = articleService.findById(comment.getArticleId());
        String content = comment.getNickname() + "说: \n" + comment.getContent();

        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(emailConfig.getFrom());
            simpleMailMessage.setTo(emailConfig.getTo());
            simpleMailMessage.setSubject("文章: \"" + article.getTitle() + "\"有新的评论");
            simpleMailMessage.setText(content);
            mailSender.send(simpleMailMessage);
        } catch (Exception e) {
            log.error("发送邮件失败: ", e);
        }

    }

}
