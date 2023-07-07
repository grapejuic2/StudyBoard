package com.study.board.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.study.board.dao.FreeBoardDAO;
import com.study.board.service.FreeBoardService;
import com.study.board.vo.FreeBoardVO;
import com.study.board.vo.PagingVO;

@Controller
public class FreeController {

	@Autowired
	private FreeBoardDAO freeBoardDAO;

	@Autowired
	private FreeBoardService freeBoardService;

	// 게시물 목록 출력
	@RequestMapping("list.do")
	public String boardList(Model model, @ModelAttribute("paging") PagingVO paging) {
		int totalRowCount = freeBoardDAO.getTotalRowCount(paging);
		paging.setTotalRowCount(totalRowCount);
		paging.pageSetting();
		List<FreeBoardVO> freeBoardList = freeBoardDAO.getBoardList(paging);
		List<FreeBoardVO> noticeList = new ArrayList<>();
		List<FreeBoardVO> normalList = new ArrayList<>();

		for (FreeBoardVO freeBoard : freeBoardList) {
			if ("Y".equals(freeBoard.getbNoticeYn())) {
				noticeList.add(freeBoard);
			} else {
				normalList.add(freeBoard);
			}
		}

		model.addAttribute("noticeList", noticeList);
		model.addAttribute("normalList", normalList);
		return "board/boardList";
	}

	// 게시물 상세 페이지 출력
	@RequestMapping("view.do")
	public String boardView(@RequestParam("bNo") int bNo, HttpSession session, Model model) throws Exception {
		Set<Integer> visitedBoardSet = (Set<Integer>) session.getAttribute("visitedBoardSet");
		if (visitedBoardSet == null) {
			visitedBoardSet = new HashSet<>();
			session.setAttribute("visitedBoardSet", visitedBoardSet);
		}

		if (!visitedBoardSet.contains(bNo)) {
			freeBoardService.updateViewCnt(bNo);
			visitedBoardSet.add(bNo);
		}

		FreeBoardVO freeBoard = freeBoardService.getNotice(bNo);
		if ("Y".equals(freeBoard.getbNoticeYn())) {
			freeBoard.setbCategory("공지");
		}

		model.addAttribute("freeBoard", freeBoard);
		return "board/view";
	}

	// 게시물 수정
	@RequestMapping("edit.do")
	public String boardEdit(Model model, int bNo) {
		FreeBoardVO freeBoard = freeBoardDAO.getBoard(bNo);
		model.addAttribute("freeBoard", freeBoard);
		return "board/edit";
	}

	// 게시물 삭제
	@RequestMapping("delete.do")
	public String boardDelete(@RequestParam("bNo") int bNo) {
		freeBoardDAO.deleteBoard(bNo);
		System.out.println("삭제되는 게시물의 번호는 : " + bNo);
		return "redirect:/list.do";
	}

	// 게시물 등록
	@RequestMapping(value = "regist.do", method = RequestMethod.POST)
	public String boardRegist(@ModelAttribute("freeBoard") FreeBoardVO freeBoard, HttpServletRequest request)
	        throws Exception {
	    String uploadFolder = "C:\\test\\upload";
	    List<MultipartFile> list = ((MultipartHttpServletRequest) request).getFiles("files");
	    List<Map<String, Object>> viewfileList = new ArrayList<>();

	    if (list.size() > 0) {
	        for (int i = 0; i < list.size(); i++) {
	            MultipartFile file = list.get(i);
	            String fileRealName = file.getOriginalFilename();
	            long fileSize = file.getSize();

	            System.out.println("파일명 : " + fileRealName);
	            System.out.println("파일크기 : " + fileSize);

	            File saveFile = new File(uploadFolder + "\\" + fileRealName);
	            Map<String, Object> fileMap = new HashMap<>();
	            fileMap.put("originalFileName", fileRealName);
	            fileMap.put("fileSize", fileSize);

	            try {
	            	System.out.println("업로드 되는 파일이 없습니다.");
	                file.transferTo(saveFile);
	                viewfileList.add(fileMap);
	            } catch (IllegalStateException e) {
	                e.printStackTrace();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    }

	    if ("공지".equals(freeBoard.getbCategory())) {
	        freeBoard.setbNoticeYn("Y");
	    } else {
	        freeBoard.setbNoticeYn("N");
	    }

	    int maxDepth = freeBoardService.getMaxDepth(); // 최대 depth 가져오기
	    freeBoard.setDepth(maxDepth + 1); // 현재 글의 depth는 최대 depth + 1로 설정

	    freeBoardService.insertBoard(freeBoard); // 일반 글 작성

	    return "redirect:/list.do";
	}

	// 답글 등록
	@RequestMapping(value = "replyForm.do", method = RequestMethod.GET)
	public String replyForm(@RequestParam(value = "parentNo", required = false) int parentNo, Model model)
			throws Exception {
		FreeBoardVO parentBoard = freeBoardService.getBoard(parentNo); // 부모 게시물 가져오기
		int parentDepth = parentBoard.getDepth();

		model.addAttribute("parentBoard", parentBoard); // 부모 게시물 정보 전달
		model.addAttribute("parentDepth", parentDepth + 1); // 수정된 부분: 새로운 depth 정보 전달

		return "board/replyForm";
	}

	@RequestMapping(value = "replySubmit.do", method = RequestMethod.POST)
	public String submitReplyForm(@ModelAttribute("freeBoard") FreeBoardVO freeBoard) throws Exception {
		if (freeBoard.getParentNo() != 0 && freeBoard.getParentNo() > 0) {
			FreeBoardVO parentBoard = freeBoardService.getBoard(freeBoard.getParentNo());
			int parentDepth = parentBoard.getDepth();
			freeBoard.setDepth(parentDepth + 1); // 수정된 부분: 답변글의 depth 설정
			freeBoardService.insertReplyBoard(freeBoard);
		}
		return "redirect:/list.do";
	}

	// 게시물 수정본 등록
	@RequestMapping("form.do")
	public String boardForm(@ModelAttribute("freeBoard") FreeBoardVO freeBoard) {
		return "board/form";
	}

}
