package com.springbootvue.dao;

import com.springbootvue.dto.BoardDTO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BoardDAOImpl implements BoardDAO {

    final private SqlSession sqlSession;

    @Autowired
    public BoardDAOImpl(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public List<BoardDTO> boardList() {

        return sqlSession.selectList("board.boardList");
    }

    @Override
    public int boardWrite(BoardDTO boardDTO) {
        int rs = sqlSession.insert("board.boardWrite", boardDTO);
        return rs;
    }
}
