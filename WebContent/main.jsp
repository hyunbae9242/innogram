<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Innogram</title>
<jsp:include page="/WEB-INF/common/head.jsp"/>
</head>
<body>
<form id="loginForm">
	<table>
		<tr>
			<th colspan="2">
				<h1>Innogram</h1>
				<br/>
				<h5>로그인을 해주세요.</h5>
			</th> 
		</tr>
		<tr>
			<th colspan="2">
				<input type="text" name="userId" id="userId" placeholder="UserId">
			</th>
		</tr>
		<tr>
			<th colspan="2">
				<input type="text" name="password" id="password" placeholder="Password">
			</th>
		</tr>
		<tr>
			<td>
				<input type="button" onclick="login()" value="로그인" style="width: 100%">
			</td>
		</tr>
	</table>
</form>
<script type="text/javascript">
	function login(){
		var userId = $('#userId').val();
		if(userId == null || userId.trim() == ''){
			alert('아이디를 입력해주세요');
			return;
		}
		var password = $('#password').val();
		if(password == null || password.trim() == ''){
			alert('비밀번호를 입력해주세요');
			return;
		}
		var params = $('#loginForm').serialize();
		$.ajax({
			url : '/ajax/membership/login',
			method : 'POST',
			data : params,
			success : function(res){
				if(res.result === 'success'){
					alert('로그인 되었습니다!');
					location.href = '/views/post/list';
				}else{
					alert('뭔가 잘못되었어요...')
				}
			},
			error : function(err){
				console.log(err);
			}
		})
	}
</script>
</body>
</html>