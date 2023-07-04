package com.study.board.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.study.board.vo.FreeBoardVO;
import com.study.board.vo.PagingVO;

@Repository
public class FreeBoardDAOImpl implements FreeBoardDAO {
    private final SqlSession sqlSession;

    @Autowired
    public FreeBoardDAOImpl(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public FreeBoardVO getBoard(int bNo) {
        return sqlSession.selectOne("com.study.board.dao.FreeBoardDAO.getBoard", bNo);
    }

    @Override
    public int updateBoard(FreeBoardVO freeBoard) {
        return sqlSession.update("com.study.board.dao.FreeBoardDAO.updateBoard", freeBoard);
    }

    @Override
    public void deleteBoard(int bNo) {
        sqlSession.update("com.study.board.dao.FreeBoardDAO.deleteBoard", bNo);
    }

    @Override
    public int insertBoard(FreeBoardVO freeBoard) {
        return sqlSession.insert("com.study.board.dao.FreeBoardDAO.insertBoard", freeBoard);
    }

    @Override
    public void updateViewCnt(int bNo) {
        sqlSession.update("com.study.board.dao.FreeBoardDAO.updateViewCnt", bNo);
    }

    @Override
    public List<FreeBoardVO> getNoticeList() {
        return sqlSession.selectList("com.study.board.dao.FreeBoardDAO.getNoticeList");
    }

    @Override
    public int updateNoticeYn(@Param("bNo") int bNo, @Param("bNoticeYn") String bNoticeYn) {
        return sqlSession.update("com.study.board.dao.FreeBoardDAO.updateNoticeYn", bNo);
    }

    @Override
    public int insertReplyBoard(FreeBoardVO freeBoard) {
        return sqlSession.insert("com.study.board.dao.FreeBoardDAO.insertReplyBoard", freeBoard);
    }

    @Override
    public int getMaxDepth() {
        return sqlSession.selectOne("com.study.board.dao.FreeBoardDAO.getMaxDepth");
    }

    @Override
    public List<FreeBoardVO> getReplyList(int parentNo) {
        return sqlSession.selectList("com.study.board.dao.FreeBoardDAO.getReplyList", parentNo);
    }

    @Override
    public int getTotalRowCount(PagingVO paging) {
        return sqlSession.selectOne("com.study.board.dao.FreeBoardDAO.getTotalRowCount", paging);
    }

    @Override
    public List<FreeBoardVO> getBoardList(PagingVO paging) {
        return sqlSession.selectList("com.study.board.dao.FreeBoardDAO.getBoardList", paging);
    }

    @Override
    public List<String> getAttachedFiles(int bNo) {
      return sqlSession.selectList("com.study.board.dao.FreeBoardDAO.getAttachedFiles", bNo);
    }

	@Override
	public FreeBoardVO getBoardWithFiles(int bNo) {
		return sqlSession.selectOne("con.study.board.dao.FreeBoardDAO.getBoardWithFiles", bNo);
	}
}
