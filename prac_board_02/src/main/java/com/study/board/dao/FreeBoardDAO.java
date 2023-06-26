package com.study.board.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.study.board.vo.FreeBoardVO;

@Mapper
@Repository
public interface FreeBoardDAO {
    List<FreeBoardVO> getBoardList();
    FreeBoardVO getBoard(int bNo);
    int updateBoard(FreeBoardVO freeBoard);
    int deleteBoard(FreeBoardVO freeBoard);
    int insertBoard(FreeBoardVO freeBoard);
    void updateViewCnt(int bNo);
    List<FreeBoardVO> getNoticeList();
	int updateNoticeYn(@Param("bNo") int bNo, @Param("bNoticeYn") String bNoticeYn);
	int insertReplyBoard(FreeBoardVO freeBoard);
	int getMaxDepth();
}

