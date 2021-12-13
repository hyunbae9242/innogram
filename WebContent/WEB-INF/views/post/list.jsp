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
<div>
	<table style="background-color: rgba(255, 255, 128, .5);">
		<thead>
			<tr>
				<th colspan="2" style="min-width: 300px; background-color: black; color: white">
					<h2>Innogram</h2>
				</th> 
			</tr>
		</thead>
		<tbody id="tbody">
		</tbody>
	</table>
</div>
<div style="position: fixed; bottom: 10px;">
	<input type="button" onclick="goPost();" value="글쓰기" style="min-width: 300px; background-color: black; color: white;">
</div>
<script type="text/javascript">
	$(document).ready(function(){
		loadPostList();
	});
	
	function loadPostList(){
		$.ajax({
			url : '/ajax/post/getPostList?postDeleteYn=0',
			method : 'GET',
			success : function(res){
				drawTable(res);
			},
			error : function(err){
				console.log(err);
			}
		})
	}
	
	function drawTable(rowData){
		var html = '';
		rowData.forEach(function(data,idx){
			var createdDate = new Date(data.postCreatedDate);
			if(createdDate){
				createdDate = createdDate.toLocaleDateString();	
			}
			html += '<tr>';
			html += '<td colspan="2" width="100%">';
			html += '<table width="100%">';
			html += '<tr>';
			html += '<th>' + data.postTitle + '</th>'
			html += '</tr>';
			html += '<tr>';
			html += '<td align="right">' + data.postUserId + '</th>'
			html += '</tr>';
			html += '<tr>';
			html += '<td><div style="min-height:300px; background-color: white;" ondblclick="postLike(' + data.postId + ')">' + data.postContents + '</div></th>'
			html += '</tr>';
			html += '<tr>';
			html += '<td>좋아요 ' + data.postLike + '</td>'
			html += '</tr>';
			html += '<tr>';
			html += '<td align="right">' + createdDate + '</td>'
			html += '</tr>';
			html += '<tr>';
			html += '<td align="center" style="background-color : black;"></td>'
			html += '</tr>';
			html += '</table>';
			html += '</td>';
			html += '</tr>';
		})
		$('#tbody').html(html);
	}
	
	function postLike(postId){
		var param = {
				postId : postId
			}
		$.ajax({
			url : '/ajax/post/updateLike',
			method : 'POST',
			data : JSON.stringify(param),
			success : function(res){
				console.log(res);
				if(res.result === 'success'){
					loadPostList();
				}
			},
			error : function(err){
				console.log(err);
			}
		})
	}
	
	function goPost(){
		location.href = '/views/post/post';
	}
</script>
</body>
</html>