
    // 이벤트 리스너
    $("#btn-join").click((event)=>{
        join();
     })


    // 버튼에 리스너 달아주기
    $("#btn-login").click((event)=>{
        login();
    });
 
     // 회원가입 요청 메서드
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
         let response = await fetch("/api/join",{
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

     //로그인 요청 메서드
    let login = async ()=>{
        // 1. 오브젝트로 만들기
        let loginDto = {
            username: $("#username").val(),
            password: $("#password").val()
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