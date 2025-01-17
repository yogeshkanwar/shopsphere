import React, { useEffect, useState, useCallback } from 'react';
import { Stomp } from '@stomp/stompjs';

const ChatComponent = () => {
  const [message, setMessage] = useState('');
  const [messages, setMessages] = useState([]);
  const [client, setClient] = useState(null);
  const [name , setName] = useState();
  const [isConnected, setIsConnected] = useState(false);


  const connectToChat = () => {
    const stompClient = Stomp.client('ws://192.168.2.46:8080/boot-stomp');
    stompClient.connect({}, ()  => {
      console.log('Connected: ');
      setIsConnected(true);
      setClient(stompClient);

      stompClient.subscribe('/subscribe/chat', (message) => {
        const receivedMessage = JSON.parse(message.body);
        receivedMessage.likes = 0;
        setMessages(prevMessages => [...prevMessages, receivedMessage]);
        });

        stompClient.subscribe('/subscribe/like', (message) => {
          const {id} = JSON.parse(message.body);
          console.log("Received like for message ID: ", id); 

          setMessages(prevMessages =>
            prevMessages.map((msg) =>
              msg.id === id ? { ...msg, likes: (msg.likes || 0) + 1 } : msg
          )
          );
        });


      if(name ){
        stompClient.publish({
          destination : '/publish/chat',
          body : JSON.stringify({content : "joined the chat",name : name}), 
        })   
      }
      
        })
    } 

  const handleSendMessage = () => {
    if(client && client.connected ){
      client.publish({
        destination : '/publish/chat',
        body : JSON.stringify({content : message,name : name}), 
      })   
      setMessage("")
    }
  }

  const disconnectToChat = () => {
    if (client && client.connected) {
      client.disconnect(() => {
        console.log("Disconnected");
        setIsConnected(false); 
        setClient(null); 
      });
    }
  };

  const handleKeyDown = (e) => {
    if(e.key === 'Enter') {
      e.preventDefault();
      handleSendMessage();
    }
  } 

  const handleLike = (id) => {
  
    if (client && client.connected) {
      console.log(`Sending like for message ID: ${id}`);
      client.publish({
        destination: '/publish/like',
        body: JSON.stringify({id}),
      });
    }
  };
  
 
  return (
    <div>
      <h1>WebSocket Chat</h1>

      <input
        type="text"
        value={name}
        onChange={(e) => setName(e.target.value)}
        placeholder="Enter name"
      />

      <button onClick={connectToChat}>Connect</button>


      {isConnected && (
        <>
          <button onClick={disconnectToChat}>Disconnect</button>
          <div style={{ position: 'absolute', top: '200px' }}>
            <input
                type="text"
                value={message}
                onChange={(e) => setMessage(e.target.value)}
                onKeyDown={handleKeyDown}
                placeholder="Enter message"
              />
              <button onClick={handleSendMessage}>Send</button>
         

          <div className="messages-container">
            <h2>Received Messages:</h2>
            {messages.length > 0 ? (
              <ul>
                {messages.map((msg, index) => (
                  <li key={index} className="message-item">
                    <strong>{msg.name}:</strong> {msg.content}
                    <button onClick={() => handleLike(msg.id)}> {msg.likes} Like </button>
                  </li>
                ))}
              </ul>
            ) : (
              <p>No messages yet</p>
            )}
          </div>
          </div>

        </>
      )}

      <style jsx>{`
        .messages-container {
          margin-top: 20px;
        }

        .message-item {
          margin-bottom: 10px;
        }

        .message-item strong {
          color: #007bff;
        }
      `}</style>
    </div>
  );
};

export default ChatComponent;