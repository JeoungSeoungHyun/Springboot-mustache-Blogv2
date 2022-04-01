// 1. 리스너

// 글쓰기 버튼 리스너
$("#btn-write").click((event)=>{
    write();
});

// 글삭제 버튼 리스너
$("#btn-delete").click(()=>{
    deletePost();
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

// 글삭제 함수
let deletePost = async () => {

    let response = await fetch(`/s/api/post/${id}`,{
        method: "DELETE"
    });

    let responeseParse = await response.json();

    if(responeseParse.code == 1){
        alert("삭제성공");
        location.href="/";
    } else {
        alert("삭제실패");
    }


};

// 글 수정,삭제 권한 확인 함수
let postInfoRender = (data)=>{

    let username = data.post.user.username;
    let title = data.post.title;
    let content =  data.post.content;
    let auth = data.auth;

    $("#username").text(username);
    $("#title").text(title);
    $("#content").html(content); //mustache에서 중괄호 3개인것처럼

    if(auth){
        $("#auth-box").css("display","block");
    }
};

