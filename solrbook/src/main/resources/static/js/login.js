var loginBox = new Vue({
    el: "#login-box",
    data: {
        userIcon: "../img/icon1.jpg",
        loginDisplay: true,
        loginUsername: "",
        loginPassword: "",
        loginNickname:'',
        loginMobile:'',
        loginPasswordConfirm: "",
        message: "",
        iforgetPage: "iforget.html",
        registerButtonDisabled: 'disabled'
    },
    computed: {
        title: {
            get: function () {
                if (this.loginDisplay) {
                    this.message = "立即登录";
                    return "登录";
                } else {
                    this.message = "立即注册";
                    return "注册";
                }
            },
            set: function (val) {
                this.message = val;
            }
        },
        subTitle: function () {
            if (this.loginDisplay) {
                return "立即注册";
            } else {
                return "立即登录";
            }
        }
    },
    methods: {
        switchLoginRegister: function () {
            this.loginDisplay = !this.loginDisplay
        },
        doLogin: function () {
            login(this.loginUsername, this.loginPassword);
        },
        doRegister: function () {
            register(this.loginUsername, this.loginPassword,this.loginNickname,this.loginMobile)
        }
    },
    watch: {
        loginPasswordConfirm: function (val) {
            if (val !== this.loginPassword) {
                this.message = '两次密码不一致';
            } else {
                this.message = '立即注册';
            }
        }
    }
})

document.onkeydown = function (event) {
    var e = event || window.event;
    if (e && e.keyCode == 13) { //回车键的键值为13
        loginBox.doLogin(); //调用登录按钮的登录事件
    }
};

function register(username, password,nick,mobile) {
    // 1.验证参数有效性
    if (username == '' || password == '') {
        loginBox.message = '用户名或密码不能为空';
        return;
    }
    var url="/user/register";
    var param = {
        username: username,
        password: password,
        nick:nick,
        mobile:mobile
    }
    param= JSON.stringify(param);
    $.ajax({
        url: url,
        data: param,
        type: "post",
        contentType:"application/json",
        dataType: "json",
        success: function (result) {
            if (result.status != 200) {
                loginBox.message = result.data;
            } else {
                loginBox.message = "注册成功";
                setTimeout(function () {
                    login(username, password);
                },2000)
            }
        }
    })
}

function login(param1, param2) {

    console.log("登录")
    var url = "/admin/login";
    var param = {
        username: param1,
        password: param2
    }

    $.ajax({
        url: url,
        data: param,
        type: "post",
        dataType: "json",
        success: function (result) {
            if (result.status != 200) {
                loginBox.message = result.data;
            } else {
                loginBox.message = "登录成功";
                window.localStorage.setItem("user", JSON.stringify(result.data));
                setTimeout(function () {
                    window.location.href = "/adminIndex";
                }, 1000)
            }
        }
    })

    setTimeout(function () {
        loginBox.message = "欢迎登录";
    }, 2000)
}

$(function () {
    document.oncopy = function () {
        return false;
    }
    document.onselectstart = function () {
        return false;
    }
})
