<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="../layout/app.jsp">
    <c:param name="content">
        <h2>id : ${task.id} のやることの編集ページ</h2>

        <form method="POST" action="${pageContext.request.contextPath}/update">
            <c:import url="_form.jsp" />
        </form>

        <p><a href="${pageContext.request.contextPath}/index">一覧に戻る</a></p>
		<p><a href="#" onclick="confirmDestroy();">この　やること　を削除する。</a></p>
		<form method="POST" action="${pageContent.request.contextPath }/destroy">
		<input type="hidden" name="_taken" value="${_token}" />
		</form>

<script>
faunction confirmDestroy(){
	if(confirm("放蕩に削除してよろしいでしょうか?"){
		document.forms[1].submit();
	}
}
</script>
    </c:param>
</c:import>