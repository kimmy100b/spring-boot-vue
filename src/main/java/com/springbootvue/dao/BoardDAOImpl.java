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
    public List<BoardDTO> getBoardList() {

        return sqlSession.selectList("board.getBoardList");
    }

    @Override
    public int setBoardWrite(BoardDTO boardDTO) {
        int rs = sqlSession.insert("board.setBoardWrite", boardDTO);
        return rs;
    }

    @Override
    public BoardDTO getBoardView(int id) {

        return sqlSession.selectOne("board.getBoardView");
    }
}
