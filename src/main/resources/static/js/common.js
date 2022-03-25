// 1. 리스너

// 로그아웃 버튼 리스너
    
$("#btn-logout").click((event)=>{
    logout();
  });  


// 2. 기능

// 로그아웃 요청 함수
let logout = async () =>{

    let response = await fetch("/logout");

    let responseParse = await response.json();

    if(responseParse.code == 1){
        alert("로그아웃 성공")
        location.href="/";
    }
};
