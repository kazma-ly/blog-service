package com.kazma233.blog.service.article.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kazma233.blog.dao.article.CommentDao;
import com.kazma233.blog.entity.article.Article;
import com.kazma233.blog.entity.comment.Comment;
import com.kazma233.blog.entity.comment.vo.CommentAdd;
import com.kazma233.blog.entity.comment.vo.CommentArticleTitleVO;
import com.kazma233.blog.entity.comment.vo.CommentQuery;
import com.kazma233.blog.service.article.IArticleService;
import com.kazma233.blog.service.article.ICommentService;
import com.kazma233.blog.utils.ShiroUtils;
import com.kazma233.common.ThreadPoolUtils;
import com.kazma233.common.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService implements ICommentService {

    @Autowired
    private CommentDao commentDao;
    @Autowired
    private IArticleService articleService;
    @Autowired
    private JavaMailSender mailSender;

    @Value("${my-setting.mail-username}")
    private String mailUsername;

    private static final Logger LOGGER = LoggerFactory.getLogger(CommentService.class);

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void insert(CommentAdd commentAdd) {

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
                uid(ShiroUtils.getUid()).
                build();

        commentDao.insert(comment);

        ThreadPoolUtils.getCachedThreadPool().execute(() -> sendEmail(comment));
    }

    @Override
    public void deleteById(String cid) {
        commentDao.deleteById(cid);
    }

    @Override
    public PageInfo<CommentArticleTitleVO> queryAllCommentAndArticleTitle(CommentQuery commentQuery) {
        commentQuery.setUid(ShiroUtils.getUid());
        PageHelper.startPage(commentQuery.getPageNo(), commentQuery.getPageSize());
        List<CommentArticleTitleVO> commentArticleTitleVOS = commentDao.findByArgsHasTitle(commentQuery);
        return new PageInfo<>(commentArticleTitleVOS);
    }

    @Override
    public List<CommentArticleTitleVO> queryRecentlyComment(int num) {
        CommentQuery commentQueryVO = new CommentQuery();
        commentQueryVO.setLimit(num);
        commentQueryVO.setOffset(0);
        commentQueryVO.setUid(ShiroUtils.getUid());
        return commentDao.findByArgsHasTitle(commentQueryVO);
    }

    @Override
    public PageInfo queryArticleComment(CommentQuery commentQuery) {
        PageHelper.startPage(commentQuery.getPageNo(), commentQuery.getPageSize());
        List<Comment> comments = commentDao.findByArgs(commentQuery);
        return new PageInfo<>(comments);
    }

    private void sendEmail(Comment comment) {
        Article article = articleService.findById(comment.getArticleId());
        String content = article.getTitle() + ": \n" + comment.getNickname() + ": " + comment.getContent();

        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(mailUsername);
            simpleMailMessage.setTo("a781683161@qq.com");
            simpleMailMessage.setSubject("有新的评论");
            simpleMailMessage.setText(content);
            mailSender.send(simpleMailMessage);
        } catch (Exception e) {
            LOGGER.error("发送邮件失败: ", e);
        }

    }

}
