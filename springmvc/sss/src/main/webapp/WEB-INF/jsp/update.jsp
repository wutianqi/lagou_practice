<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>简历列表</title>
</head>
<body>
    <form action="/resume/saveResume" method="post">
        id:<input type="text" name="id" value="${resume.id}" readonly />
        address:<input type="text" name="address" value="${resume.address}" />
        name:<input type="text" name="name" value="${resume.name}" />
        phone:<input type="text" name="phone" value="${resume.phone}" />
        <input type="submit" value="提交" />
    </form>
</body>
</html>