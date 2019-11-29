$(document).ready(function () {
    var trigger = $('.hamburger'),
        overlay = $('.overlay'),
        isClosed = false;

    $('main-content').load('hello.html', function () {

    })

    trigger.click(function () {
        hamburger_cross();
    });

    function hamburger_cross() {

        if (isClosed == true) {
            overlay.hide();
            trigger.removeClass('is-open');
            trigger.addClass('is-closed');
            isClosed = false;
        } else {
            overlay.show();
            trigger.removeClass('is-closed');
            trigger.addClass('is-open');
            isClosed = true;
        }
    }

    $('[data-toggle="offcanvas"]').click(function () {
        $('#wrapper').toggleClass('toggled');
    });
});


var admin = new Vue({
    el: '#wrapper',
    data: {
        currentTabComponent: '',
        sideBarInfos: [
            {
                text: '用户信息管理',
                value: [
                    {
                        text: '所有注册用户',
                        value: 'admin-body-all-users'
                    }
                ]
            },
            {
                text: '文档相关设置',
                value: [
                    {
                        text: '查看所有文档',
                        value: 'admin-body-all-book'
                    },
                    {
                        text: '上传新文档',
                        value: 'admin-body-new-book'
                    }
                ]
            }
        ]
    },
    methods: {
        adminLogout: function () {
            var url = '/admin/logout';
            $.ajax({
                url: url,
                type: 'get',
                dataType:'json',
                success: function (result) {
                    console.log(result);
                    window.location.href = "/adminLoginPage"
                },
                complete:function (a,b) {
                    console.log(a)
                    console.log(b)
                    window.location.href = "/adminLoginPage"
                }
            })
        },

        loadPage: function (val) {
            console.log(val);
            $('#main-content').load('/admin/adminPages/' + val, function () {

            })
        }
    }
})