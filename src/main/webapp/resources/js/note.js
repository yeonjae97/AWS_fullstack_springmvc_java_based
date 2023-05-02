const URL = cp + '/note/';  // 고정 주소

// 리턴타입을 호출
let noteService = (function(){
	function send(obj, callback){
		$.ajax({
			url : URL + "new",
			method : "post",
			dataType : "json",
			data : JSON.stringify(obj),
			contentType : "application/json; charset=utf-8",
			success : callback
		})
	}
	
	// Controller에서 했던 방식 그대로 구현
	
	function get(noteno, callback){
		$.getJSON(URL + noteno).done(callback);
	}
	function receive(noteno, callback){
		$.ajax({
			url : URL + noteno,
			method : "put",
			dataType : "json",
			data : JSON.stringify(noteno),
			success : callback
		})
	}
	function remove(noteno, callback){}
	
	 // 발송
	function getSendList(id, callback){
		$.getJSON(URL + "s/" + id).done(callback);
	}
	
	function getReceiveList(id, callback){
		$.getJSON(URL + "r/" + id).done(callback);
	}
	function getReceiveCheckedList(id, callback){
		$.getJSON(URL + "r/c/" + id).done(callback);
	}
	return {
		send:send,
		get:get,
		receive:receive,
		remove:remove,
		getSendList:getSendList,
		getReceiveList:getReceiveList,
		getReceiveCheckedList:getReceiveCheckedList
	}
})();