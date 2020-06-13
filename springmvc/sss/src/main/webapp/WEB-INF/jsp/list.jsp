<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>简历列表</title>
</head>
<body>

<a href="${pageContext.request.contextPath}/resume/save">添加</a>
<table border="1">
    <tr>
        <td>id</td>
        <td>address</td>
        <td>name</td>
        <td>phone</td>
        <td>操作</td>
    </tr>
    <c:forEach items="${resumes}" var="resume">
        <tr>
            <td>${resume.id}</td>
            <td>${resume.address}</td>
            <td>${resume.name}</td>
            <td>${resume.phone}</td>
            <td>
                <a href="${pageContext.request.contextPath}/resume/update?id=${resume.id}">修改</a>
                <a href="${pageContext.request.contextPath}/resume/delete?id=${resume.id}">删除</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>