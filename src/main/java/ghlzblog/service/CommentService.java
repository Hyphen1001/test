package ghlzblog.service;

import java.util.List;

import ghlzblog.po.Comment;


public interface CommentService {

    List<Comment> listCommentByBlogId(Long blogId);

    Comment saveComment(Comment comment);
}
