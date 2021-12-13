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
<form id="postForm">
	<div>
		<table style="background-color: rgba(255, 255, 128, .5);">
			<thead>
				<tr>
					<th colspan="2" style="min-width: 300px; background-color: black; color: white">
						<h2>Innogram > post</h2> 
					</th> 
				</tr>
			</thead>
			<tbody>
				<tr>
					<td colspan="2" >
						<input type="text" placeholder="Title" name="postTitle" id="postTitle" style="min-width: 90%; border:none; background: transparent;"> 
					</td>
				</tr>
				<tr>
					<td colspan="2" >
						<textarea placeholder="Contents" name="postContents" id="postContents" style="resize: none; min-width: 90%;min-height : 300px; border:none; background: transparent;"></textarea>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</form>
<div>
	<input type="button" onclick="post();" value="등록" style="min-width: 300px; min-height: 50px; background-color: black; color: white;">
</div>
<script type="text/javascript">
	$(document).ready({
		
	});

	function post(){
		var postTitle = $('#postTitle').val();
		var postContents = $('#postContents').val();
		var params = {
				postTitle :postTitle,
				postContents : postContents
			};
		$.ajax({
			url : '/ajax/post/insertPost',
			method : 'POST',
			data : JSON.stringify(params),
			success : function(res){
				console.log(res);
				console.log(res.result);
				console.log(res.reason);
				if(res.result === 'success'){
					alert('등록되었습니다.');
					location.href = '/views/post/list';
				}else{
					if(res.reason == 'need login!'){
						alert('로그인이 필요합니다!');
						location.href = '/';
					}
					alert('먼가 잘못되어가는것같아..');
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