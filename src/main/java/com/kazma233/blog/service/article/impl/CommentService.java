package com.kazma233.blog.service.article.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kazma233.blog.dao.article.CommentDao;
import com.kazma233.blog.entity.article.Article;
import com.kazma233.blog.entity.article.Comment;
import com.kazma233.blog.entity.article.vo.CommentAndArticleVO;
import com.kazma233.blog.entity.article.vo.CommentQueryVO;
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
    public void insert(Comment comment) {
        comment.setId(Utils.generateID());
        comment.setCreateTime(LocalDateTime.now());
        comment.setUid(ShiroUtils.getUidNotMust());

        commentDao.insert(comment);

        ThreadPoolUtils.getCachedThreadPool().execute(() -> sendEmail(comment));
    }

    @Override
    public void deleteById(String cid) {
        commentDao.deleteById(cid);
    }

    @Override
    public PageInfo<CommentAndArticleVO> queryAllCommentAndArticleTitle(CommentQueryVO commentQueryVO) {
        commentQueryVO.setUid(ShiroUtils.getUid());
        PageHelper.startPage(commentQueryVO.getPage(), commentQueryVO.getCount());
        List<CommentAndArticleVO> commentAndArticleVOS = commentDao.findByArgsHasTitle(commentQueryVO);
        return new PageInfo<>(commentAndArticleVOS);
    }

    @Override
    public PageInfo<Comment> queryUserComment(CommentQueryVO commentQueryVO) {
        commentQueryVO.setUid(ShiroUtils.getUidNotMust());
        PageHelper.startPage(commentQueryVO.getPage(), commentQueryVO.getCount());
        List<Comment> comments = commentDao.findByArgs(commentQueryVO);
        return new PageInfo<>(comments);
    }

    @Override
    public List<CommentAndArticleVO> queryRecentlyComment(int num) {
        CommentQueryVO commentQueryVO = new CommentQueryVO();
        commentQueryVO.setLimit(num);
        commentQueryVO.setOffset(0);
        commentQueryVO.setUid(ShiroUtils.getUid());
        return commentDao.findByArgsHasTitle(commentQueryVO);
    }

    @Override
    public PageInfo queryArticleComment(CommentQueryVO commentQueryVO) {
        PageHelper.startPage(commentQueryVO.getPage(), commentQueryVO.getCount());
        List<Comment> comments = commentDao.findByArgs(commentQueryVO);
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
