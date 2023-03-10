let index ={
		init: function(){
			$("#btn-save").on("click", ()=>{ //function(){}, ()=>{} this를 바인딩하기 위해
				this.save();
			});
		/*	$("#btn-login").on("click", ()=>{ //function(){}, ()=>{} this를 바인딩하기 위해
				this.login();
			});*/
			$("#btn-update").on("click", ()=>{ //function(){}, ()=>{} this를 바인딩하기 위해
				this.update();
			});
		},

	save: function(){
		//alert('user의 save함수 호출 됨');
		let data={
				username: $("#username").val(),
				email : $("#email").val(),
				password: $("#password").val()
		};
		
		//console.log(data);
		
		//ajax호출시 default 비동기 호출
		//ajax통신을 이용해 3개의 데이터를 json을 변경해 insert요청
		//회원가입 수행 요청
		//ajax가 통신성공하고 서버가 json을 리턴해주면 자동으로 자바 오브젝트로 변환
		$.ajax({
			type:"POST",
			url:"/auth/joinProc",
			data:JSON.stringify(data), //http body data
			contentType:"application/json; charset=uft-8"//body데이터가 어떤 타입인지(MIME)
			//dataType:"json" // 서버에서 응답이 왔을 때(기본값 string) => javascriptObject로 변경해줌
		}).done(function(resp){
			if(resp.status === 500){
				alert("회원가입에 실패하였습니다. 다시시도해주세요.");
				location.href="/"
			}else{
				alert("회원가입이 완료되었습니다.");
				location.href="/";
			}
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
	},
	
/*	login: function(){
		//alert('user의 save함수 호출 됨');
		let data={
				username: $("#username").val(),
				password: $("#password").val()
		};
	
		$.ajax({
			type:"POST",
			url:"/api/user/login",
			data:JSON.stringify(data), //http body data
			contentType:"application/json; charset=uft-8"//body데이터가 어떤 타입인지(MIME)
			//dataType:"json" // 서버에서 응답이 왔을 때(기본값 string) => javascriptObject로 변경해줌
		}).done(function(resp){
			alert("로그인이 완료되었습니다");
			//console.log(resp);
			location.href="/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
	}*/
	
	update: function(){
		let data={
				id: $("#id").val(),
				username: $("#username").val(),
				email : $("#email").val(),
				password: $("#password").val()
		};
		
		$.ajax({
			type:"PUT",
			url:"/user",
			data:JSON.stringify(data), 
			contentType:"application/json; charset=uft-8"
		}).done(function(resp){
			alert("회원수정이 완료되었습니다");
			location.href="/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
	},
	
}

index.init();