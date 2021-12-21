<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Innogram</title>
<jsp:include page="/WEB-INF/common/head.jsp"/>
<style type="text/css">
a {
	text-decoration:none;
	color : #006400;
}

a.report{
	float : right;
	color : red;
}
a.comment_btn{
	float : inherit;
}
.comment{
	border-bottom: dotted 1px black;
}
.comment:hover{
	background-color: #B0C4DE;
}
/* Outer */
.popup {
	width:100%;
	height:100%;
	display:none;
	position:fixed;
	top:0px;
	left:0px;
	background:rgba(0,0,0,0.75);
}

/* Inner */
.popup-inner {
	max-width:700px;
	width:90%;
	padding:40px;
	position:absolute;
	top:50%;
	left:50%;
	-webkit-transform:translate(-50%, -50%);
	transform:translate(-50%, -50%);
	box-shadow:0px 2px 6px rgba(0,0,0,1);
	border-radius:3px;
	background:#fff;
}

/* Close Button */
.popup-close {
	width:30px;
	height:30px;
	padding-top:4px;
	display:inline-block;
	position:absolute;
	top:0px;
	right:0px;
	transition:ease 0.25s all;
	-webkit-transform:translate(50%, -50%);
	transform:translate(50%, -50%);
	border-radius:1000px;
	background:rgba(0,0,0,0.8);
	font-family:Arial, Sans-Serif;
	font-size:20px;
	text-align:center;
	line-height:100%;
	color:#fff;
}

.popup-close:hover {
	-webkit-transform:translate(50%, -50%) rotate(180deg);
	transform:translate(50%, -50%) rotate(180deg);
	background:rgba(0,0,0,1);
	text-decoration:none;
}
</style>
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


<div class="popup" id="commentPopup">
	<div class="popup-inner">
		<h4 id="popupPostUserId"></h4>
		<div id="popupPostContents"></div><br>
		<div id="commentList">
			
		</div><br>
		<input type="hidden" id="popupPostId">
		<input type="hidden" id="popupCommentId">
		<div id="commentToCommentDisplay" style="display: none;"></div>
		<input id="commentContents" type="text" placeholder="댓글입력" style="border: none"/><input type="button" onclick="insertComment()" value="등록" style="background-color: white; border-radius: 5px">
		<p><a  href="javascript:commentPopupClose()">닫기</a></p>
		<a class="popup-close"  href="javascript:commentPopupClose()">x</a>
	</div>
</div>

<script type="text/javascript">
	var userId = '';
	$(document).ready(function(){
		userId = '${sessionScope.postUserId}';
		console.log('userId : ' + userId);
		if(userId === ''){
			alert('로그인해주세요!');
			location.href = '/';
		}
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
			var commentCnt = data.commentList.length;
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
			html += '<td><img src="/img/like.png" width="25" height="25"> ' + data.postLike + ' <a href="javascript:commentPopUp(' + data.postId + ')"><img src="/img/comment.png" width="25" height="25"></a></td>'
			html += '</tr>';
			html += '<tr>';
			html += '<td align="right">' + createdDate + '</td>'
			html += '</tr>';
			if(commentCnt){
				html += '<tr>';
				html += '<td align="center"><a href="javascript:commentPopUp(' + data.postId + ')">' + commentCnt + '개의 댓글보기</a></td>'
				html += '</tr>';
				data.commentList.forEach(function(comment){
					console.log(data.postId + ' , '+ comment.commentUserId)
					if(userId == comment.commentUserId && comment.commentDepth == 0){
						html += '<tr>';
						html += '<td align="left"><p4>' + comment.commentUserId + '</p4> : ' + comment.commentContents + '</td>'
						html += '</tr>';
					}
				})
			}
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

	function commentPopUp(postId){
		$('#popupPostId').val(postId);
		
		$.ajax({
			url : '/ajax/post/getPost?postId='+postId,
			method : 'GET',
			success : function(res){
				console.log(res);
				$('#popupPostUserId').text(res.postUserId);
				$('#popupPostContents').text(res.postContents);
				$('#commentPopup').fadeIn(350);
				$('#commentInput').focus();
				loadCommentList(res.commentList);
			},
			error : function(err){
				console.log(err);
			}
		})
	}
	function commentPopupClose(){
		cancelCommentToComment();
		$('#commentContents').val('');		
		$('#commentPopup').fadeOut(350);
	}
	//<div>유저 아이디 : 댓글 <a href="javascript:void(0)">댓글달기</a></div>
	//<div>&nbsp;ㄴ유저 아이디 : 댓글 </div>
	function loadCommentList(commentList){
		var html = '';
		if(commentList.length > 0){
			commentList.forEach(function(row){
				if(row.commentDepth == 0){
					html += '<div class="comment">­' + row.commentUserId + ' : ' + row.commentContents + ' <a class="comment_btn" href="javascript:void(0)" onclick="setCommentToComment(' + row.commentId + ',\'' + row.commentUserId + '\')">댓글달기</a>&nbsp;&nbsp;<a class="report" href="javascript:void(0)" onclick="deleteComment(' + row.commentId + ')">신고</a></div>'
				}else if(row.commentDepth == 1){ 
					html += '<div class="comment">&nbsp;└' + row.commentUserId + ' : ' + row.commentContents + '&nbsp;&nbsp;<a class="report" href="javascript:void(0)" onclick="deleteComment(' + row.commentId + ')">신고</a></div>'
				}
			})
		}
		if(html === ''){
			html = '첫번째 댓글을 달아보세요!!!!'
		}
		$('#commentList').html(html);
	}
	function setCommentToComment(commentId,commentUserId){
		$('#commentToCommentDisplay').show();
		$('#commentToCommentDisplay').html(commentUserId+' 님께 댓글달기 <input type="button" onclick="cancelCommentToComment()" value="취소"/>');
		$('#commentContents').val('');	
		$('#popupCommentId').val(commentId);
	}
	function cancelCommentToComment(){
		$('#commentToCommentDisplay').hide();
		$('#commentToCommentDisplay').html('');
		$('#commentContents').val('');	
		$('#popupCommentId').val('');
	}
	
	function insertComment(){
		var postId = $('#popupPostId').val(); 
		var commentId = $('#popupCommentId').val();
		var commentContents = $('#commentContents').val();
		if(!commentContents.trim()){
			alert('내용을 입력해주세요.');
			return;
		}
		var params = {
				postId : postId,
				commentContents : commentContents,
				commentDepth : 0
		}
		if(commentId){
			params.commentBaseId = commentId;
			params.commentDepth = 1;
		}
		$.ajax({
			url : '/ajax/comment/setComment',
			data : JSON.stringify(params),
			method : 'POST',
			success : function(res){
				console.log(res);
				if(res === 1){
					commentPopUp(postId);	
				}else{
					if(res.reason == 'need login!'){
						alert('로그인이 필요합니다!');
						location.href = '/';
					}
				}
			},
			error : function(err){
				
			}
		})
	}
	function deleteComment(commentId){
		if(confirm('신고하시면 글이 삭제됩니다.\n신고하시겠습니까?') == true){
			if(commentId){
				var params = {
						commentId : commentId,
						commentDeleteYn : 1
				}
				$.ajax({
					url : '/ajax/comment/deleteComment', 
					data : JSON.stringify(params),
					method : 'POST',
					success : function(res){
						if(res){
							alert('삭제되었습니다.');
							commentPopUp($('#popupPostId').val());
						}else{
							alert('먼가잘못됨..');
							commentPopUp($('#popupPostId').val());
						}
					},
					error : function(err){
						console.log(err)
					}
				})
			}
		}else{
			return ;
		}
	}
	
	function goPost(){
		location.href = '/views/post/post';
	}
</script>
</body>
</html>