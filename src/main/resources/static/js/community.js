function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    comment2target(questionId, 1, content);
}

function comment2target(targetId, type, content) {
    if (!content) {
        alert("不能回复空内容~~~~");
        return;
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: 'application/json',
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": type
        }),
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();
                // $("#comment_section").hide();
            } else {
                if (response.code == 2003) {
                    var isAccepted = confirm(response.message);
                    if (isAccepted) {
                        window.open("https://github.com/login/oauth/authorize?client_id=d7b547ee6e49b4c9b5a7&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
                        window.localStorage.setItem("closable", true);
                    }

                } else {
                    alert(response.message);
                }
            }
        },
        dataType: "json"
    });
}

function comment(commentId) {

}

function comment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-" + commentId).val();
    comment2target(commentId, 2, content);

}

//展开二级评论
function collapseComment(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);
    //获取二级评论的展开状态
    var collapse = e.getAttribute("data-collapse");
    if (collapse) {
        //折叠二级评论
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    } else {
        var subCommentContainer = $("#comment-" + id);
        if (subCommentContainer.children().length != 1) {
            //展开二级评论
            comments.addClass("in");
            //标记二级评论展开状态
            e.setAttribute("data-collapse", "in");
            e.classList.add("active");
        }else {
            $.getJSON("/comment/" + id, function (data) {
                console.log(data);
                $.each(data.data.reverse(), function (index, comment) {
                    var c = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments",
                        html: comment.content
                    });
                    subCommentContainer.prepend(c);
                });
            });
        }
    }
    console.log(id)
}


// function comment2target(targetId, type, content) {
//     if (!content) {
//         alert("不能回复空内容~~~");
//         return;
//     }
//
//     $.ajax({
//         type: "POST",
//         url: "/comment",
//         contentType: 'application/json',
//         data: JSON.stringify({
//             "parentId": targetId,
//             "content": content,
//             "type": type
//         }),
//         success: function (response) {
//             if (response.code == 200) {
//                 window.location.reload();
//             } else {
//                 if (response.code == 2003) {
//                     var isAccepted = confirm(response.message);
//                     if (isAccepted) {
//                         $('#myModal').modal({});
//                         // window.open("https://github.com/login/oauth/authorize?client_id=7f316909bf70d1eaa2b2&redirect_uri=" + document.location.origin + "/callback&scope=user&state=1");
//                         // window.localStorage.setItem("closable", true);
//                     }
//                 } else {
//                     alert(response.message);
//                 }
//             }
//         },
//         dataType: "json"
//     });
// }