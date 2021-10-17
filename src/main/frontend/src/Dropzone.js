import axios from 'axios';
import {useCallback} from 'react'
import {useDropzone} from 'react-dropzone'

function Dropzone({userProfileId}) {
  const onDrop = useCallback(acceptedFiles => {
    const file = acceptedFiles[0];
    console.log(file);
    // Send files to backend
    const formData = new FormData();
    // Add file to formData
    // "file" has to be exactly the same as in UserProfileController.java class
    formData.append("file", file);
    // Post file
    axios.post(`http://localhost:8080/api/v1/user-profile/${userProfileId}/image/upload`,
      formData,
      {
        headers: {
          "Content-Type": "multipart/form-data"
        }
      }
    ).then(() => {
      console.log("File uploaded successfully");
    }).catch(err => {
      console.log(err);
    });
  }, [])
  const {getRootProps, getInputProps, isDragActive} = useDropzone({onDrop})

  return (
    <div {...getRootProps()}>
      <input {...getInputProps()} />
      {
        isDragActive ?
          <p>Drop the image here ...</p> :
          <p>Drag 'n' drop image, or click to select profile image</p>
      }
    </div>
  )
}

export default Dropzone;
