
let index={
    init:function (){
        $("#btn-save").on("click",()=>{ // function(){}, ()=>{} this를 바인딩하기 위해서!!
            this.save();
        });
    },

    save:function(){
        //alert('user의 save함수 호출됨');
        let data = {
            username: $("#username").val(),
            password: $("#password").val(),
            email:$("#email").val()
        };

        //console.log(data);

        //ajax 호풀시 default가 비동기 호출
        //ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청!
        //ajax가 통신을 성공하고 서버가 json을 리턴해주면 자동으로 자바 오브젝트로 변환
        $.ajax({
            type: "POST",
            url: "/blog/api/user", // URL 앞에 슬래시(/)를 추가하여 절대 경로로 지정합니다.
            data: JSON.stringify(data), // 데이터를 JSON 문자열로 변환합니다.
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function(resp) {
                // 성공 시 실행할 코드
                alert("회원가입이 완료되었습니다.");
                console.log(resp); // 서버 응답을 콘솔에 출력합니다.
                location.href = "/blog"; // 페이지를 리다이렉트합니다.
        }).fail(function(error) {
                // 실패 시 실행할 코드
                console.error(JSON.stringify(error)); // 오류를 콘솔에 출력합니다.
                alert("회원가입에 실패했습니다."); // 사용자에게 오류 메시지를 표시합니다.
        });
    }
}

index.init();