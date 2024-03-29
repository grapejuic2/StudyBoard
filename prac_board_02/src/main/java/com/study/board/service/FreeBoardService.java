package com.study.board.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.board.dao.FreeBoardDAO;
import com.study.board.vo.FreeBoardVO;

@Service
public class FreeBoardService {

    private final FreeBoardDAO freeBoardDAO;
    private final SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    public FreeBoardService(FreeBoardDAO freeBoardDAO, SqlSessionTemplate sqlSessionTemplate) {
        this.freeBoardDAO = freeBoardDAO;
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    public List<FreeBoardVO> getBoardList() throws Exception {
        List<FreeBoardVO> boardList = freeBoardDAO.getBoardList();
        for (FreeBoardVO board : boardList) {
            if ("Y".equals(board.getbNoticeYn())) {
                board.setbCategory("공지");
            }
        }
        return boardList;
    }

    public FreeBoardVO getNotice(int bNo) throws Exception {
        FreeBoardVO board = freeBoardDAO.getBoard(bNo);
        if (board != null && "Y".equals(board.getbNoticeYn())) {
            board.setbCategory("공지");
        }
        return board;
    }

    public void insertBoard(FreeBoardVO board) {
        freeBoardDAO.insertBoard(board);
    }

    public List<FreeBoardVO> getNoticeList() throws Exception {
        return freeBoardDAO.getNoticeList();
    }

    public int updateNoticeYn(int bNo, String bNoticeYn) throws Exception {
        return freeBoardDAO.updateNoticeYn(bNo, bNoticeYn);
    }

    public void updateViewCnt(int bNo) throws Exception {
        sqlSessionTemplate.update("com.study.board.dao.FreeBoardDAO.updateViewCnt", bNo);
    }

    public FreeBoardVO getBoard(int bNo) throws Exception {
        FreeBoardVO board = freeBoardDAO.getBoard(bNo);
        if (board != null && "Y".equals(board.getbNoticeYn())) {
            board.setbCategory("공지");
        }
        return board;
    }

    public void insertReply(FreeBoardVO replyBoard) {
    	replyBoard.setParentNo(replyBoard.getbNo()); // 답변 게시물의 부모 번호 설정 예시
        freeBoardDAO.insertBoard(replyBoard);
    }

	public void deleteBoard(FreeBoardVO freeBoard) {
		freeBoardDAO.deleteBoard(freeBoard);
	}
}
