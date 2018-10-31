/** Ajax 비동기 데이터 통신을 위한 공통 함수 */
function ajax(obj){
	console.log("bbbbb");
	var method = obj.method ? obj.method.toUpperCase() : "GET";
	var url = obj.url;
	var param =  obj.param; // name=value
	var callback = obj.callback;
	
	var request = createXMLHttpRequest();
	
	// 서버로 보내기전에 이벤트처리
	request.onreadystatechange = function(){
		if(request.readyState == 4){
			if(request.status == 200){
				callback(request); //정상적으로 서버로부터 수신했을때
			}else if(request.status == 400){
				alert("잘못된 요청입니다..");
			}else if(request.status == 404){
				alert("요청한 페이지를 찾을 수 없습니다..");			
			}else{
				alert("서버장애로 서비스 할 수 없습니다..");	
			}
		}
	};
	
	if(method=="GET"){
		if(param) url += ("?" + param);
		request.open(method, url, true);
		request.send();
	}else{
		// post방식 처리....나중에 요청방식을 바꿔서 데이터수신..(RESTful인지뭔지..)
		request.open(method, url, true);
		request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		request.send(param);
	}
}

/** factory처럼 xml리퀘 객체 생성함수 */
function createXMLHttpRequest(){
	var xhr = null;
	try{
		xhr = new XMLHttpRequest();
	}catch(e){
		xhr = new ActiveXObject("Msxml2.XMLHTTP");
	}
	return xhr;
}