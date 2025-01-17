// // src/WebSocketService.js

// import { Client } from '@stomp/stompjs';
// import SockJS from 'sockjs-client';

// const WS_URL = 'http://192.168.2.46:8080/boot-stomp'; 

// class WebSocketService {

//   constructor() {
//     this.client = new Client({
//       brokerURL: WS_URL,
//       connectHeaders: {
//         // add headers if needed
//       },
//       webSocketFactory: () => new SockJS(WS_URL),
//       debug: (str) => console.log(str),
//       onConnect: (frame) => {
//         console.log('Connected: ' + frame);
//         this.subscribeToChat();
//       },    
//       onStompError: (frame) => {
//         console.error('Error: ' + frame.headers['message']);
//       },
//       onDisconnect: (frame) => {
//         console.log('Disconnected: ' + frame);
//       },
//     });

//     this.client.activate();
//   }

//   subscribeToChat() {
//     this.client.subscribe('/subscribe/chat', (message) => {
//       const parsedMessage = JSON.parse(message.body);
//       this.latestMessage = parsedMessage.content;
//       console.log('Received message:', parsedMessage.content);

//     });
//   }

//   getLatestMessage() {
//     return this.latestMessage; // Access the latest message
//   }

//   sendMessage(message) {
//     if (this.client.connected) {
//       this.client.publish({
//         destination: '/publish/chat',
//         body: JSON.stringify(message),
//       });
//     } else {
//       console.error('WebSocket not connected');
//     }
//   }
// }

// const webSocketService = new WebSocketService();

// export default webSocketService;
