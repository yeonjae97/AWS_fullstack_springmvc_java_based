console.log("Reply Module");


var xhr = new XMLHttpRequest();
// xhr.open();
// xhr.send();

var replyService = (function(){
  // return {name: "AAAA"};
  // 댓글 추가
  function add(reply, callback, error){
    console.log("add() :: " + reply);
    console.log(reply);
    $.post({
      url: cp + "/replies/new",
      data : JSON.stringify(reply),
      dataType : "json",
      contentType : "application/json; charset=utf-8"
    })
    .done(function(data){
      if(callback){
        callback(data);
      }
    })
    .fail(function(xhr){
    	console.log(xhr);
    });
  }

  // 댓글 단일 조회
  function get(rno, callback){
    // var url = "replies/list/" + param.bno + "/" + (param.rno ? param.rno : "");
    var url = cp + "/replies/" + rno; 
    $.getJSON(url)
    .done(function(data){
        if(callback){
          callback(data);
        }
    });
  }
  // 댓글 목록 조회
  function getList(param, callback, error){
    // var url = "replies/list/" + param.bno + "/" + (param.rno ? param.rno : "");
     var url = cp + "/replies/list/" + param.bno + "/" + (param.rno || "");

    // nullish
//    var url = "replies/list/" + param.bno + "/" + (param.rno ?? "");
    console.log(url);
    $.getJSON(url)
    .done(function(data){
        if(callback){
          callback(data);
        }
    })
    .fail(function(xhr){
        if(error){
            error(xhr);
          }
     })
  }
  // 댓글 수정
  function modify(reply, callback, error){
    console.log("modify()");
    console.log(reply);
    $.ajax({
      url: cp +  "/replies/" + reply.rno,
      method: "put",
      data : JSON.stringify(reply),
      dataType : "json",
      contentType : "application/json; charset=utf-8"
    })
    .done(function(data){
      if(callback){
        callback(data);
      }
    })
    .fail(function(xhr){
    	console.log(xhr);
    });
  }
  // 댓글 삭제
  function remove(reply, callback, error){
    console.log(rno);
    $.ajax({
      url: cp + "/replies/" + reply.rno,
      method: 'delete',
      data : JSON.stringify(reply),
      dataType: 'json',
      contentType : "application/json; charset=utf-8"
    })
    .done(function(data){
      if(callback){
        callback(data);
      }
    })
    .fail(function(xhr){
    	console.log(xhr);
    });
  }
  return {
    add:add,
    getList:getList,
    get:get,
    remove:remove,
    modify:modify
  };
})();

