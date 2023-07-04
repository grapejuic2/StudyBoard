package com.study.board.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.study.board.vo.FreeBoardVO;
import com.study.board.vo.PagingVO;

@Mapper
public interface FreeBoardDAO {
    FreeBoardVO getBoard(int bNo);
    int updateBoard(FreeBoardVO freeBoard);
    void deleteBoard(int bNo);
    int insertBoard(FreeBoardVO freeBoard);
    void updateViewCnt(int bNo);
    List<FreeBoardVO> getNoticeList();
	int updateNoticeYn(@Param("bNo") int bNo, @Param("bNoticeYn") String bNoticeYn);
	int insertReplyBoard(FreeBoardVO freeBoard);
	int getMaxDepth();
	List<FreeBoardVO> getReplyList(int parentNo);
	int getTotalRowCount(PagingVO paging);
	List<FreeBoardVO> getBoardList(PagingVO paging);
	List<String> getAttachedFiles(int bNo);
	FreeBoardVO getBoardWithFiles(int bNo);

}

