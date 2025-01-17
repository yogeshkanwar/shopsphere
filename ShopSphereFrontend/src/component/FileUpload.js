import React, { useState } from 'react';

const FileUpload = () => {
  const [file, setFile] = useState(null);

  const handleFileChange = (event) => {
    setFile(event.target.files[0]);
  };

  const handleFileUpload = () => {
    if (!file) {
      alert("Please select a file to upload.");
      return;
    }
  

    // Create FormData to send file to the server
    const formData = new FormData();
    formData.append("file", file);

    // Make API request to upload file
    fetch("/api/upload", {
      method: "POST",
      body: formData,
    })
      .then((response) => {
        if (response.ok) {
          alert("File uploaded successfully!");
        } else {
          alert("File upload failed.");
        }
      })
      .catch((error) => {
        console.error("Error:", error);
        alert("File upload failed.");
      });
  };

  return (
    <div>
      <input type="file" onChange={handleFileChange} />
      {file && <p>Selected file: {file.name}</p>}
      <button onClick={handleFileUpload}>Send</button>
    </div>
  );
};

export default FileUpload;
