package com.springbootvue.controller;

import com.springbootvue.Service.CommentService;
import com.springbootvue.dto.CommentDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    final private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/getCommentList")
    public List<CommentDTO> getCommentList(
            @RequestParam(value = "gubun") Integer gubun,
            @RequestParam(value = "fkId") Integer fkId){

        return commentService.getCommentList(gubun, fkId);
    }

    @PostMapping("/addComment")
    public void addComment(@RequestBody CommentDTO commentDTO){

        commentService.addComment(commentDTO);
    }

    @PostMapping("/modifyComment")
    public void modifyComment(@RequestBody CommentDTO commentDTO){

        commentService.modifyComment(commentDTO);
    }

    @DeleteMapping("/deleteComment")
    public void deleteComment(
            @RequestParam(value = "cid") Integer cid,
            @RequestParam(value = "fkId") Integer fkId){

        commentService.deleteComment(cid, fkId);
    }

    @GetMapping("/getCntComment")
    public int getCommentCnt(
            @RequestParam(value = "gubun") Integer gubun,
            @RequestParam(value = "fkId") Integer fkId){

        return commentService.getCntComment(gubun, fkId);
    }


}
