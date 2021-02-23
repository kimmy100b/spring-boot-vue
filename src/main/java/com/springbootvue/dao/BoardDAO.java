package com.springbootvue.dao;

import com.springbootvue.dto.BoardDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BoardDAO {
    /** 게시판 목록 **/
    List<BoardDTO> selectBoardList(
            @Param("startIndex") Integer startIndex,
            @Param("pageSize") Integer pageSize);

    /** 게시물 등록하기 **/
    int insertBoardInfo(
            @Param("writer") String writer,
            @Param("title") String title,
            @Param("content") String content);

    /** 게시물 수정하기 **/
    int updateBoardInfo(
            @Param("bid") Integer bid,
            @Param("writer") String writer,
            @Param("title") String title,
            @Param("content") String content);

    /** 게시물 정보보기 findByBid **/
    BoardDTO selectBoardInfo(@Param("bid") Integer bid);

    /** 게시물 삭제  **/
    int deleteBoard(@Param("bid") Integer bid);

    /** 게시물 조회수 증가 **/
    int updateBoardViews(@Param("bid") Integer bid);

    /** 전체 게시물 수 (페이징 기능할 때 사용) **/
    int selectBoardListCnt();

    /** 게시물 bid 찾기 **/
    int selectMaxBoardBid();
}
