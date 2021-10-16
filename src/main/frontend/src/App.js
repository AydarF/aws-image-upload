import "./App.css";
import { useState, useEffect } from "react";
import axios from "axios";
import Dropzone from "./Dropzone"

const UserProfiles = () => {
  const [userProfiles, setUserProfiles] = useState([]);

  const fetchUserProfiles = () => {
    axios.get("http://localhost:8080/api/v1/user-profile").then((res) => {
      console.log(res);      
      setUserProfiles(res.data);
    });
  };

  useEffect(() => {
    fetchUserProfiles();
  }, []);

  return userProfiles.map((userProfile, index) => {
    return (
      <div key={index}>
        <br />
        <br />
        <h1>{userProfile.userName}</h1>
        <p>{userProfile.userProfileId}</p>
        <Dropzone />
        <br />
      </div>
    )
  })
};

function App() {
  return <div className="App">
    <UserProfiles />
  </div>;
}

export default App;
