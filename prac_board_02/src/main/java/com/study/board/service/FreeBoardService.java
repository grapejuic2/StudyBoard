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

    /**
     * �Խù� ��� ��ȸ �޼ҵ�
     * 
     * @return �Խù� ���
     * @throws Exception
     */
    public List<FreeBoardVO> getBoardList() throws Exception {
        List<FreeBoardVO> boardList = freeBoardDAO.getBoardList();
        for (FreeBoardVO board : boardList) {
            if ("Y".equals(board.getbNoticeYn())) {
                board.setbCategory("����");
            }
        }
        return boardList;
    }
    
    /**
     * �������� ��ȸ �޼ҵ�
     * 
     * @param bNo �Խù� ��ȣ
     * @return ��������
     * @throws Exception
     */
    public FreeBoardVO getNotice(int bNo) throws Exception {
        FreeBoardVO board = freeBoardDAO.getBoard(bNo);
        if (board != null && "Y".equals(board.getbNoticeYn())) {
            board.setbCategory("����");
        }
        return board;
    }
    
    /**
     * ��ȸ�� ������Ʈ �޼ҵ�
     * 
     * @param bNo �Խù� ��ȣ
     * @throws Exception
     */
    public void updateViewCnt(int bNo) throws Exception {
        sqlSessionTemplate.update("com.study.board.dao.FreeBoardDAO.updateViewCnt", bNo);
    }
    
    /**
     * �������� ��� ��ȸ �޼ҵ�
     * 
     * @return �������� ���
     * @throws Exception
     */
    public List<FreeBoardVO> getNoticeList() throws Exception {
        return freeBoardDAO.getNoticeList();
    }
    
    /**
     * �������� ���� ������Ʈ �޼ҵ�
     * 
     * @param bNo         �Խù� ��ȣ
     * @param bNoticeYn   �������� ���� (Y/N)
     * @return ������Ʈ�� �� ��
     * @throws Exception
     */
    public int updateNoticeYn(int bNo, String bNoticeYn) throws Exception {
        return freeBoardDAO.updateNoticeYn(bNo, bNoticeYn);
    }
    
    /**
     * �Խù� ���� �޼ҵ�
     * 
     * @param freeBoard ������ �Խù� ����
     * @return ������Ʈ�� �� ��
     */
    public int boardReform(FreeBoardVO freeBoard) {
        boolean hasReplies = freeBoardDAO.hasReplies(freeBoard.getbNo());

        if (hasReplies) {
            int parentDepthNo = freeBoardDAO.getParentDepthNo(freeBoard.getbNo());
            freeBoard.setbDepthNo(parentDepthNo + 1);
        }
        
        int maxBgno = freeBoardDAO.getMaxBgno(freeBoard.getbNo());
        freeBoard.setbGNo(maxBgno + 1);

        int parentFkSeq = freeBoardDAO.getFkSeq(freeBoard.getbNo());
        freeBoard.setbFkSeq(parentFkSeq);
        
        return freeBoardDAO.boardReform(freeBoard);
    }
}
