    // 1. 리스너

    // 회원가입 버튼 리스너
    $("#btn-join").click((event)=>{
        join();
     })


    // 로그인 버튼 리스너
    $("#btn-login").click((event)=>{
        login();
    });

    // 회원정보 수정 버튼 리스너
    $("#btn-update").click((event)=>{
        update();
    });

    
    // 2. 기능

    // 유저네임 기억 함수(httpOnly 속성이 걸려있으면 안된다!!!!)
    function usernameRemember(){
        let cookieParse = document.cookie.split("=")
        if(cookieParse[0] == "remember"){
            $("#username").val(cookieParse[1]);
        }
    }

    if($("#remember").prop("cheked") == true){
        usernameRemember();
    }
    
    

     // 회원가입 요청 함수
     async function join(){
         // (1) username,password,email,addr의 dom을 찾아서 오브젝트를 만들어준다.
         let joinDto = {
             username: $("#username").val(),
             password: $("#password").val(),
             email: $("#email").val(),
             addr: $("#addr").val(),
         }

         // (2) JSON으로 변환한다. =>통신은 JSON 형태로 하기 때문에
         let dtoJson = JSON.stringify(joinDto);
         // (3) FETCH 요청을 한다.
         let response = await fetch("/join",{
             method: "POST",
             headers: {
                 "Content-Type": "application/json; charset=utf-8"
             },
             body: dtoJson
         })
         let responseObject = await response.json();
         // (4) 회원가입이 잘되면 알림창 띄우고 로그인페이지로 이동한다.
         if(responseObject.code == 1){
             alert("회원가입 완료");
             location.href = "/loginForm";
         } else {
             alert("회원가입 실패");
         }
     }

     //로그인 요청 함수
    let login = async ()=>{
        // 체크박스의 체크여부를 제이쿼리에서 확인하는 법
        let checked = $("#remember").prop("checked");
        // 1. 오브젝트로 만들기
        let loginDto = {
            username: $("#username").val(),
            password: $("#password").val(),
            remember: checked ? "on" : "off" // 삼항 연산자 사용
        }

        // 2. json으로 파싱
        let dtoJson = JSON.stringify(loginDto);
        
        // 3. fetch 요청
        let response = await fetch("/api/login",{
            method:"POST",
            headers:{
                "Content-Type":"application/json; charset=utf-8"
            },
            body: dtoJson
        })

        let responseObject = await response.json();

        if(responseObject.code == 1){
            alert("로그인 성공")
           location.href="/";
        } else{
            alert("로그인 실패")
        }

    };

    // 회원정보 수정 요청 함수
    let update = async ()=>{

        let id = $("#id").val();

        console.log($("#username").val());

        let updateDto = {
            password: $("#password").val(),
            email: $("#email").val(),
            addr: $("#addr").val()
        }

        let updateDtoJson = JSON.stringify(updateDto);

        let response = await fetch("/s/api/user/" + id,{
            method:"PUT",
            headers:{
                "Content-Type":"application/json; charset=utf-8"
            },
            body: updateDtoJson
        });

        let responseParse = await response.json();

        if(responseParse.code == 1){
            alert("업데이트 성공");
             location.href=`/s/user/${id}`;
        } else{
            alert("업데이트 실패");
        }
    };
    

    