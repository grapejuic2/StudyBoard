package com.study.board.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.board.dao.FreeBoardDAO;
import com.study.board.vo.FreeBoardVO;
import com.study.board.vo.PagingVO;

@Service
public class FreeBoardService {
    
    private final FreeBoardDAO freeBoardDAO;    
    private final SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    public FreeBoardService(FreeBoardDAO freeBoardDAO, SqlSessionTemplate sqlSessionTemplate) {
        this.freeBoardDAO = freeBoardDAO;
        this.sqlSessionTemplate = sqlSessionTemplate;
    }
    
    public List<FreeBoardVO> getBoardList(PagingVO paging) throws Exception {
        List<FreeBoardVO> boardList = freeBoardDAO.getBoardList(paging);
        
        for (FreeBoardVO board : boardList) {
            if ("Y".equals(board.getbNoticeYn())) {
                board.setbCategory("공지");
            }
            int depth = board.getDepth();
            board.setDepthString(getDepthString(depth));
            
            List<FreeBoardVO> replyList = freeBoardDAO.getReplyList(board.getbNo());
            if (replyList != null) {
                for (FreeBoardVO reply : replyList) {
                    int replyDepth = reply.getDepth();
                    reply.setDepthString(getDepthString(replyDepth));
                }
                board.setReplyList(replyList);
            }
        }
        
        return boardList;
    }

    private String getDepthString(int depth) {
        StringBuilder depthString = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            depthString.append("└─");
        }
        return depthString.toString();
    }


    public FreeBoardVO getNotice(int bNo) throws Exception {
        FreeBoardVO board = freeBoardDAO.getBoard(bNo);
        if (board != null && "Y".equals(board.getbNoticeYn())) {
            board.setbCategory("공지");
        }
        return board;
    }

    public void insertBoard(FreeBoardVO freeBoard) {
        if (freeBoard.getParentNo() == 0) {
            freeBoard.setParentNo(0);
            freeBoard.setDepth(0); 
        } else {
            FreeBoardVO parentBoard = freeBoardDAO.getBoard(freeBoard.getParentNo());
            int parentDepth = parentBoard.getDepth();
            freeBoard.setDepth(parentDepth + 1);
            
            // 부모 글의 replyList 가져오기
            List<FreeBoardVO> replyList = parentBoard.getReplyList();
            if (replyList == null) {
                replyList = new ArrayList<>();
                parentBoard.setReplyList(replyList);
            }
            
            // 새로운 답변글을 추가할 때, 올바른 위치에 삽입
            int index = 0;
            for (; index < replyList.size(); index++) {
                FreeBoardVO reply = replyList.get(index);
                int replyDepth = reply.getDepth();
                if (replyDepth > freeBoard.getDepth()) {
                    break;
                }
            }
            
            replyList.add(index, freeBoard);
            freeBoard.setbTitle("[Re] " + freeBoard.getbTitle());
            
            // 새로운 답변글의 depth 보정
            updateReplyDepth(freeBoard, parentDepth + 1);
        }
        
        freeBoardDAO.insertBoard(freeBoard);
    }

    public void insertReplyBoard(FreeBoardVO freeBoard) throws Exception {
        FreeBoardVO parentBoard = freeBoardDAO.getBoard(freeBoard.getParentNo());
        int parentDepth = parentBoard.getDepth();
        int depth = parentDepth + 1;
        freeBoard.setDepth(depth);
        
        // 부모 글의 replyList 가져오기
        List<FreeBoardVO> replyList = parentBoard.getReplyList();
        if (replyList == null) {
            replyList = new ArrayList<>();
            parentBoard.setReplyList(replyList);
        }
        
        // 새로운 답변글을 추가할 때, 올바른 위치에 삽입
        int index = 0;
        for (; index < replyList.size(); index++) {
            FreeBoardVO reply = replyList.get(index);
            int replyDepth = reply.getDepth();
            if (replyDepth > depth) {
                break;
            }
        }
        
        replyList.add(index, freeBoard);
        freeBoard.setbTitle("[Re] " + freeBoard.getbTitle());
        
        // 새로운 답변글의 depth 보정
        updateReplyDepth(freeBoard, depth + 1);
        
        freeBoardDAO.insertReplyBoard(freeBoard);
    }

    private void updateReplyDepth(FreeBoardVO board, int depth) {
        List<FreeBoardVO> replyList = board.getReplyList();
        if (replyList != null) {
            for (FreeBoardVO reply : replyList) {
                reply.setDepth(depth);
                updateReplyDepth(reply, depth + 1);
            }
        }
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

    public int getMaxDepth() {
        return freeBoardDAO.getMaxDepth();
    }
    
    void deleteBoard(int bNo) {
        freeBoardDAO.deleteBoard(bNo);
    }

	public List<String> getAttachedFiles(int bNo) {
		return freeBoardDAO.getAttachedFiles(bNo);
	}

}
