var socket = new SockJS('/chat-websocket');
var stompClient = Stomp.over(socket);

// Conectar ao servidor WebSocket
stompClient.connect({}, function (frame) {
    // Subscrever ao tópico de mensagens
    stompClient.subscribe('/topic/messages', function (message) {
        var messagesDiv = document.getElementById("messages");
        var messageSection = document.createElement("section");
        
        // Processar a mensagem recebida
        var data = message.body;
        var username = data.split(":")[0];
        var userMessage = data.split(":")[1];

        // Adicionar a mensagem na lista de mensagens
        messageSection.innerHTML = `
            <section class="message">
                <img src="https://avatar.iran.liara.run/public/boy?username=${username}" alt="avatar" class="avatar">
                <div class="nes-balloon from-left">
                    <p>${userMessage}</p>
                </div>
            </section>
        `;
        messagesDiv.appendChild(messageSection);

        // Scroll automático para a última mensagem
        scrollToBottom();
    });
});

// Enviar mensagem ao servidor
function sendMessage() {
    var username = document.getElementById("username").value;
    var message = document.getElementById("message").value;
    
    // Enviar a mensagem usando WebSocket
    stompClient.send("/app/chat", {}, JSON.stringify({
        'message': username + ": " + message
    }));

}

// Scroll automático para o final da página
window.onload = scrollToBottom;

function scrollToBottom() {
    var anchor = document.getElementById("scroll-anchor");
    anchor.scrollIntoView({ behavior: "smooth" });
}
