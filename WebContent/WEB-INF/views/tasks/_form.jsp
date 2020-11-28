<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--Varidation--%>
<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容に不備があります。<br />
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" /><br />
        </c:forEach>
    </div>
</c:if>


<%-- 実験使用
<label for="created_at">作成日</label><br />
<input type="text" name="created_at" value="${task.created_at}" />
<br /><br />
--%>

<%-- 実験使用
<label for="updated_at">新しく入れ直した日</label><br />
<input type="text" name="updated_at" value="${task.updated_at}" />
<br /><br />
--%>


<label for="content">やることの内容</label><br />
<input type="text" name="content" value="${task.content}" />
<br /><br />


<input type="hidden" name="_token" value="${_token}" />
<button type="submit">投稿</button>