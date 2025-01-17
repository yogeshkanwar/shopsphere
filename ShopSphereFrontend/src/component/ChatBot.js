import React, { useState } from 'react';
import { apiService } from '../service/ApiService';

const ChatBot = () => {
  const [inputText, setInputText] = useState('');
  const [file, setFile] = useState(null);
  const [output, setOutput] = useState('');

  const handleInputChange = (e) => {
    setInputText(e.target.value);
  };

  const handleFileChange = (e) => {
    setFile(e.target.files[0]);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    const response = await apiService.ChatApi(inputText);
    console.log("response is bot ",response );
    // Update output state with the response
    setOutput(response.data);
    
    // Clear the input fields
    setInputText('');
    setFile(null);
  };


  return (
    <div style={{ padding: '20px' }}>
      <h1>Chat Input</h1>
      <form onSubmit={handleSubmit}>
        <div>
          <label>
            Input Text:
            <input
              type="text"
              value={inputText}
              onChange={handleInputChange}
              placeholder="Type your message here"
              required
            />
          </label>
        </div>
        {/* <div>
          <label>
            Upload File:
            <input type="file" onChange={handleFileChange} />
          </label>
        </div> */}
        <button type="submit">Submit</button>
      </form>
      <h2>Output:</h2>
      <p>{output}</p>
    </div>
  );
};

export default ChatBot;
