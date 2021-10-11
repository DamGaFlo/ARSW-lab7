var app = (function () {
    var numeroSala=0;

    class Point{
        constructor(x,y){
            this.x=x;
            this.y=y;
        }        
    }
    
    var stompClient = null;

    var addPointToCanvas = function (point) {        
        var canvas = document.getElementById("canvas");
        var ctx = canvas.getContext("2d");
        ctx.beginPath();
        ctx.arc(point.x, point.y, 3, 0, 2 * Math.PI);
        ctx.stroke();
    };
    
    
    var getMousePosition = function (evt) {
        canvas = document.getElementById("canvas");
        var rect = canvas.getBoundingClientRect();
        return {
            x: evt.clientX - rect.left,
            y: evt.clientY - rect.top
        };
    };


    var connectAndSubscribe = function (nSala) {
        console.info('Connecting to WS...');
        var socket = new SockJS('/stompendpoint');
        stompClient = Stomp.over(socket);
        
        //subscribe to /topic/TOPICXX when connections succeed
        stompClient.connect({}, function (frame) {
            console.log('Connected_: ' + frame);
            stompClient.subscribe('/topic/newpoint'+nSala, function (message) {
                var ptResive=JSON.parse(message.body);
                console.log(ptResive)
                addPointToCanvas(ptResive)


                
            });
        });

    };
    var eListener = function() {
        var c = document.getElementById("canvas");
        var ctx = c.getContext("2d");

        c.addEventListener("mousedown", function (event) {
            var pointMousePosition = getMousePosition(event)
            publishPoint(pointMousePosition)
        });
    }
    var publishPoint = function(pt){
        stompClient.send("/topic/newpoint"+numeroSala, {}, JSON.stringify(pt));
    }
    
    

    return {

        init: function () {
            var can = document.getElementById("canvas");
            
            //websocket connection
            //connectAndSubscribe();
            eListener();
        },

        conectarSocket: function(nSala){
            numeroSala=nSala;
            connectAndSubscribe(nSala)
        },

        disconnect: function () {
            if (stompClient !== null) {
                stompClient.disconnect();
            }
            setConnected(false);
            console.log("Disconnected");
        }
    };

})();