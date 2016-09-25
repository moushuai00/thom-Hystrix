(function ($, undefined) {
    $(function () {
        var $btn = $(".btn");
        var $username = $("#username");
        var $password = $("#password");
        $btn.on(
            "click", function () {
                $.ajax({
                    url: "hello/thom",
                    data: "",
                    dataType: "json",
                    type: "get",
                    success: function () {
                        if (data == Fail) {
                            alert("登陆失败！请重新输入")
                        } else {
                            alert("登陆成功！")
                        }
                    }
                })
            })
    })

}(jQuery, this))

