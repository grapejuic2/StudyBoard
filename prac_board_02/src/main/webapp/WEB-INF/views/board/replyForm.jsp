<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE html>
<html>
<head>
<title>답글 작성</title>
<style>
body {
	font-family: Arial, sans-serif;
	margin: 0;
	padding: 20px;
}

.container {
	max-width: 600px;
	margin: 0 auto;
	padding: 20px;
	background-color: #f9f9f9;
	border: 1px solid #ccc;
}

.container h2 {
	font-size: 24px;
	margin-bottom: 20px;
}

.container table {
	width: 100%;
	border-collapse: collapse;
	margin-bottom: 20px;
}

.container table th, .container table td {
	padding: 10px;
	border: 1px solid #ccc;
}

.container table th {
	width: 30%;
	text-align: right;
	font-weight: bold;
}

.container table td input[type="text"], .container table td textarea {
	width: 100%;
	padding: 5px;
	border: 1px solid #ccc;
	box-sizing: border-box;
}

.container table td input[type="submit"] {
	padding: 10px 20px;
	background-color: #333;
	color: #fff;
	border: none;
	cursor: pointer;
}

.container table td input[type="submit"]:hover {
	background-color: #555;
}

</style>
</head>
<body>
	<div class="container">
		<h2>답글 작성</h2>
			<form action="replySubmit.do" method="post">
			<input type="hidden" name="parentNo" value="${parentBoard.bNo}">
			<input type="hidden" name="parentDepth" value="${parentDepth}" />
			<table>
				<tr>
					<th>제목</th>
					<td><input type="text" name="bTitle" value=""
						required="required"></td>
				</tr>
				<tr>
					<th>작성자</th>
					<td><input type="text" name="bWriter" value=""></td>
				</tr>
				<tr>
					<th>비밀번호</th>
					<td><input type="password" name="bPass" value=""
						required="required"></td>
				</tr>
				<tr>
					<th>분류</th>
					<td><label><input type="radio" name="bCategory"
							value="답변" checked>답변글</label></td>
				</tr>
				<tr>
					<th>내용</th>
					<td><textarea rows="10" name="bContent"></textarea></td>
				</tr>
				<tr>
					<td colspan="2" style="text-align: center;"><input
						type="submit" value="등록"></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>
