<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no, width=device-width">
    <title>给多个点添加信息窗体</title>
    <link rel="stylesheet" href="http://cache.amap.com/lbs/static/main1119.css"/>
    <script type="text/javascript"
            src="http://webapi.amap.com/maps?v=1.4.5&key=21ccc9860c3ec9e9d1462e93fa78da0c"></script>
    <script type="text/javascript" src="http://cache.amap.com/lbs/static/addToolbar.js"></script>
</head>
<body>

<div id="container"></div>
<script type="text/javascript">
    //初始化地图对象，加载地图
    var map = new AMap.Map("container", {resizeEnable: true});
    var lnglats = [
        [116.368904, 39.923423],
        [116.382122, 39.921176],
        [116.387271, 39.922501],
        [116.398258, 39.914600]
    ];
    var infoWindow = new AMap.InfoWindow({offset: new AMap.Pixel(0, -30)});
    for (var i = 0, marker; i < lnglats.length; i++) {
        var marker = new AMap.Marker({
            position: lnglats[i],
            map: map
        });
        marker.content = '我是第' + (i + 1) + '个Marker';
        marker.on('click', markerClick);
        marker.emit('click', {target: marker});
    }
    function markerClick(e) {
        infoWindow.setContent(e.target.content);
        infoWindow.open(map, e.target.getPosition());
    }
    map.setFitView();

    var  stompClient=null;
    function connect() {
        var  socket=new SockJS("/endpointWisely");
        stompClient = Stomp.over(socket);
        stompClient.connect({},function (frame) {
            setConnected(true);
            console.log("connected : "+frame);
            stompClient.subscribe("/topic/getResponse",function (response) {
                showResponse(JSON.parse(response.body).responseMessage);
            })
        })
    }

</script>
<script type="application/javascript">
    var websocket = null;
    var cahtNum = $('.chat-num').text();
    if ('WebSocket' in window) {
        websocket = new WebSocket('ws://localhost:8080/chat/webSocket');

    } else {
        alert('该浏览器不支持websocket');
    }

    websocket.onopen = function (event) {
        console.log('websocket建立连接');
    }

    websocket.onclose = function (event) {
        console.log('websocket关闭连接');
    }

    websocket.onmessage = function (event) {
        console.log('websocket收到消息' + event.data);
        var result = $.parseJSON(event.data);
        if (result.type == 3) {
            var element = document.getElementById('message-template').innerHTML;
            $('.message-container').append(element);
            $(".message-content:last").html(result.message);
        }
        else {
            $('.chat-num').text(result.userNum);
        }

    }

    websocket.onerror = function (event) {
        console.log('websocket通信发生错误');

    }

    window.onbeforeunload = function (event) {
        websocket.close();
    }


</script>

<script src="http://cdn.bootcss.com/mdui/0.3.0/js/mdui.min.js"></script>
<script src="https://cdn.bootcss.com/jquery/1.12.1/jquery.js"></script>
</body>
</html>