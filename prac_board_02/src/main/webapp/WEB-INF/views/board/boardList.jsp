<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 연습</title>
<style>
a {
	text-decoration: none;
}

.highlight {
	font-weight: bold;
	color: blue;
}

.notice-row {
	background-color: rgba(255, 0, 0, 0.2);
}

.table-container {
	width: 100%;
	display: flex;
	flex-direction: column;
	align-items: center;
}

.table-container table {
	width: 80%;
	border-collapse: collapse;
}

.table-container thead {
	background-color: #f2f2f2;
	font-weight: bold;
}

.table-container th, .table-container td {
	padding: 10px;
	text-align: center;
	word-break: break-all;
}

.table-container th:nth-child(2), .table-container td:nth-child(2) {
	text-align: left;
}

.depth {
	display: inline-block;
}

.depth-1 {
	margin-left: 20px;
}

.depth-2 {
	margin-left: 40px;
}

.depth-3 {
	margin-left: 60px;
}

/* ... 추가적인 depth에 따른 스타일 지정 ... */
</style>
</head>
<body>
	<div class="main-container">
		<div class="table-container">
			<table border="1">
				<thead>
					<tr>
						<th style="width: 10%;"></th>
						<th style="width: 40%; text-align: center;">제목</th>
						<th style="width: 12%;">글 분류</th>
						<th style="width: 12%;">작성자</th>
						<th style="width: 12%;">등록일</th>
						<th style="width: 7%; text-align: center;">조회수</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${noticeList}" var="freeBoard"
						varStatus="varStatus">
						<tr class="notice-row">
							<td>${varStatus.index + 1}</td>
							<td class="title-cell expandable"><c:forEach begin="1"
									end="${freeBoard.depth}" varStatus="loop">
									<span class="depth-${loop.index}"></span>
								</c:forEach> <img src="resources/image/newn.png" alt="New" width="20"
								height="20" /> <a href="view.do?bNo=${freeBoard.bNo}"
								class="highlight">${freeBoard.bTitle}</a></td>
							<td class="fit-content">${freeBoard.bCategory}</td>
							<td class="fit-content">${freeBoard.bWriter}</td>
							<td class="fit-content">${freeBoard.bRegDate}</td>
							<td class="fit-content">${freeBoard.bHit}</td>
						</tr>
						<c:forEach items="${freeBoard.replyList}" var="reply">
							<tr>
								<td>${reply.bNo}</td>
								<td class="title-cell expandable"><c:forEach begin="1"
										end="${freeBoard.depth}" varStatus="loop">
										<span class="depth-${loop.index}"></span>
									</c:forEach> <span class="depth-${freeBoard.depth + 1}"></span> <img
									src="/resources/image/newr.png" alt="New" width="20"
									height="20" /> <a href="view.do?bNo=${reply.bNo}">${reply.bTitle}</a>
								</td>
						</c:forEach>
					</c:forEach>
					<c:forEach items="${normalList}" var="freeBoard"
						varStatus="varStatus">
						<tr>
							<td>${varStatus.index + 1}</td>
							<!-- 변경된 부분 -->
							<td class="title-cell expandable"><c:forEach begin="1"
									end="${freeBoard.depth}" varStatus="loop">
									<span class="depth-${loop.index}"></span>
								</c:forEach> <a href="view.do?bNo=${freeBoard.bNo}">${freeBoard.bTitle}</a>
							</td>
							<td class="fit-content">${freeBoard.bCategory}</td>
							<td class="fit-content">${freeBoard.bWriter}</td>
							<td class="fit-content">${freeBoard.bRegDate}</td>
							<td class="fit-content">${freeBoard.bHit}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div style="text-align: center; margin-top: 20px;">
			<c:if test="${paging.curPage > 1 }">
				<a href="list.do?curPage=${paging.curPage - 1 }">&lt;</a>
			</c:if>
			<c:forEach begin="${paging.firstPage }" end="${paging.lastPage }"
				var="i">
				<a href="list.do?curPage=${i }"> <c:if
						test="${i eq paging.curPage }">
						<span style="color: red">${i }</span>
					</c:if> <c:if test="${i ne paging.curPage }">${i }</c:if>
				</a>
			</c:forEach>
			<c:if test="${paging.curPage ne paging.totalPageCount }">
				<a href="list.do?curPage=${paging.curPage + 1 }">&gt;</a>
			</c:if>
			<a href="list.do?curPage=${paging.totalPageCount }">&raquo;</a>
		</div>
	</div>
	<div style="text-align: center; margin-top: 20px;">
		<a href="form.do">등록하기</a>
	</div>
</body>
</html>
