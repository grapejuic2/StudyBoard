<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${freeBoard.bTitle}</title>
<style>
body {
	font-family: Arial, sans-serif;
	margin: 0;
	padding: 20px;
}

.container {
	max-width: 800px;
	margin: 0 auto;
	padding: 20px;
	background-color: #f9f9f9;
	border: 1px solid #ccc;
}

h1 {
	font-size: 24px;
	font-weight: bold;
	margin-bottom: 10px;
}

.meta {
	font-size: 14px;
	color: #666;
	margin-bottom: 20px;
}

.content {
	font-size: 16px;
	line-height: 1.5;
	margin-bottom: 20px;
}

.buttons {
	display: flex;
	justify-content: flex-end;
	margin-top: 20px;
}

.buttons a {
	display: inline-block;
	padding: 10px 20px;
	background-color: #333;
	color: #fff;
	text-decoration: none;
	border-radius: 4px;
	margin-right: 10px;
}

.buttons a:first-child {
	margin-left: 0;
}

.notice-label {
	display: inline-block;
	padding: 4px 8px;
	background-color: #ffdd57;
	color: #333;
	font-weight: bold;
	border-radius: 4px;
}

.file-list {
	margin-top: 20px;
}

.file-list a {
	display: block;
	margin-bottom: 10px;
	text-decoration: none;
	color: #333;
}
</style>
</head>
<body>
	<div class="container">
		<h1>
			${freeBoard.bTitle}
			<c:if test="${freeBoard.bNoticeYn}">
				<span class="notice-label">공지</span>
			</c:if>
		</h1>
		<p class="meta">글번호: ${freeBoard.bNo} | 작성자: ${freeBoard.bWriter}
			| 등록일: ${freeBoard.bRegDate} | 조회수: ${freeBoard.bHit} | 글분류:
			${freeBoard.bCategory}</p>

		<div class="content">
			<pre>${freeBoard.bContent}</pre>
		</div>

		<div class="attachments">
		    <c:if test="${not empty freeBoard.viewfileList}">
		        <h3>첨부 파일 목록</h3>
		        <ul>
		            <c:forEach var="fileMap" items="${freeBoard.viewfileList}">
		                <li>
		                    파일명: ${fileMap.originalFileName} (크기: ${fileMap.fileSize} bytes)
		                </li>
		            </c:forEach>
		        </ul>
		    </c:if>
		</div>

		<div class="buttons">
			<a href="edit.do?bNo=${freeBoard.bNo}">수정</a> <a
				href="replyForm.do?parentNo=${freeBoard.bNo}">답글</a> <a
				href="list.do">목록</a>
		</div>

	</div>
</body>
</html>
