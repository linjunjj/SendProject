<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width">
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no, width=device-width">
    <title>chat</title>
    <style type="text/css">
        .card-container-message {
            margin: 10px 0;
        }
    </style>
    <link rel="stylesheet" href="http://cdn.bootcss.com/mdui/0.3.0/css/mdui.min.css">
    <link rel="stylesheet" href="http://cache.amap.com/lbs/static/main1119.css"/>
    <script type="text/javascript"
            src="http://webapi.amap.com/maps?v=1.4.5&key=21ccc9860c3ec9e9d1462e93fa78da0c"></script>
    <script type="text/javascript" src="http://cache.amap.com/lbs/static/addToolbar.js"></script>
    <!-- UI组件库 1.0 -->
    <script src="//webapi.amap.com/ui/1.0/main.js?v=1.0.11"></script>
</head>
<body class="mdui-theme-primary-indigo mdui-theme-accent-pink">


<div>
    <div id="container"></div>
    <div class="mdui-container container_text">



            <div class="mdui-col-xs-6 mdui-col-sm-4 " style="padding:10px 0">
                <div class="mdui-chip">
                    <span class="mdui-chip-icon mdui-color-blue"><i class="mdui-icon material-icons">face</i></span>
                    <span class="mdui-chip-title">总人数</span>
                </div>

                <div class="mdui-chip">
                    <span class="mdui-chip-icon mdui-color-blue"><i class="mdui-icon material-icons">&#xe420;</i></span>
                    <span class="mdui-chip-title chat-num">0</span>
                </div>
                <div class="message-container">

                </div>
            </div>

        </div>
    </div>
</div>

<script src="http://cdn.bootcss.com/mdui/0.3.0/js/mdui.min.js"></script>
<script src="https://cdn.bootcss.com/jquery/1.12.1/jquery.js"></script>


<script type="text/template" class="package-price-template" id="message-template">
    <div class="mdui-card card-container-message">
        <div class="mdui-card-primary">
            <div class="mdui-card-content message-content"></div>
        </div>
    </div>
</script>

<script type="application/javascript">
    var map = new AMap.Map("container", {resizeEnable: true,zoom:10});

    var websocket = null;
    var cahtNum = $('.chat-num').text();
    if ('WebSocket' in window) {
        websocket = new WebSocket('ws://localhost:8081/ws');

    } else {
        alert('该浏览器不支持websocket');
    }

    websocket.onopen = function (event) {
        console.log('websocket建立连接');
    }

    websocket.onclose = function (event) {
        console.log('websocket关闭连接');
    }
    var temp;
    websocket.onmessage = function (event) {
        console.log('websocket收到消息' + event.data);
        var result = $.parseJSON(event.data);
        $('.chat-num').text(result.CountPackage);
        console.log("sfafsa" + this.jingdu)


        if (this.temp == null) {
            this.temp = result.deviceid;
        } else {
            if (this.temp == result.deviceid) {
                map.clearMap();
                AMapUI.loadUI(['overlay/SimpleInfoWindow'], function (SimpleInfoWindow) {
                    var marker = new AMap.Marker({
                        map: map,
                        position: [result.jingdu, result.weidu]
                    });
                    var infoWindow = new SimpleInfoWindow({

                        infoTitle: result.deviceid,
                        infoBody: '<p class="my-desc"><strong>这里是内容。</strong></p>',
                        //基点指向marker的头部位置
                        offset: new AMap.Pixel(0, -31)
                    });

                    function openInfoWin() {
                        infoWindow.open(map, marker.getPosition());
                    }
                    //marker 点击时打开
                    AMap.event.addListener(marker, 'click', function () {
                        openInfoWin();
                    });

                    openInfoWin();
                });
            } else {
                AMapUI.loadUI(['overlay/SimpleInfoWindow'], function (SimpleInfoWindow) {

                    var marker = new AMap.Marker({
                        map: map,
                        position: [result.jingdu, result.weidu]
                    });
                    var infoWindow = new SimpleInfoWindow({

                        infoTitle: '<strong>result.deviceid</strong>',
                        infoBody: '<p class="my-desc"><strong>这里是内容。</strong></p>',
                        //基点指向marker的头部位置
                        offset: new AMap.Pixel(0, -31)
                    });

                    function openInfoWin() {
                        infoWindow.open(map, marker.getPosition());
                    }

                    //marker 点击时打开
                    AMap.event.addListener(marker, 'click', function () {
                        openInfoWin();
                    });

                    openInfoWin();
                });


            }
        }
    }
    websocket.onerror = function (event) {
        console.log('websocket通信发生错误');

    }

    window.onbeforeunload = function (event) {
        websocket.close();
    }

    $('.send-message').click(function () {
        var message = $('.chat-message').val();
        if (message == "") {
            mdui.alert('请输入要发送的消息');
            return;
        }
        sendmessage(message);
        $('.chat-message').val("");
    })

    function sendmessage(message) {
        websocket.send(message);

    }




</script>

</body>
</html>


