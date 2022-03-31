// 1. 리스너

// 글쓰기 버튼 리스너
$("#btn-write").click((event)=>{
    write();
});

// 2. 기능

// 글쓰기 함수
let write = async ()=>{

    let writeDto = {
        title:$("#title").val(),
        content:$("#content").val()
    }

    console.log(writeDto);

    let response = await fetch("/s/post",{
        method:"POST",
        body: JSON.stringify(writeDto),
        headers:{
            "Content-Type":"application/json; charset=utf-8"
        }
    });
    let reseponseParse = await response.json();

    if(reseponseParse.code == 1){
        alert("글쓰기 성공");
        location.href="/";
    } else{
        alert("글쓰기 실패");
    }
};