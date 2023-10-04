<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
    <form action="/auth/loginProc" method="post">
        <div class="form-group">
            <label for="username">Username</label>
            <input type="text" class="form-control" placeholder="Enter username" id="username" name="username">
        </div>

        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" class="form-control" placeholder="Enter password" id="password" name="password">
        </div>

        <div class="form-group form-check">
            <label class="form-check-label">
                <input class="form-check-input" type="checkbox" name="remember"> Remember me
            </label>
        </div>
        <button class="btn btn-primary">로그인</button>
        <a href="https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=742bca772c5f7944171e0dfb8194901d&redirect_uri=http://localhost:7070/auth/kakao/callback"><img height="38px" src="/image/kakao_login_button.png"></a>
    </form>
</div>

<%@ include file="../layout/footer.jsp"%>

