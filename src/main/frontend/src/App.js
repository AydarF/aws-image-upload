import "./App.css";
import { useState, useEffect } from "react";
import axios from "axios";

const UserProfiles = () => {
  const fetchUserProfiles = () => {
    axios.get("http://localhost:8080/api/v1/user-profile").then((res) => {
      console.log(res);
    });
  };

  useEffect(() => {
    fetchUserProfiles();
  }, []);

  return <h1>User Profiles will be here...</h1>
};

function App() {
  return <div className="App">
    <UserProfiles />
  </div>;
}

export default App;
