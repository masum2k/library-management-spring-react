import React from "react";
import ReactDOM from "react-dom/client";
import "./App.css";
import LibraryManagementSystem from "./App";

// Performance monitoring (optional)
import { reportWebVitals } from "./reportWebVitals";

const root = ReactDOM.createRoot(document.getElementById("root"));

root.render(
  <React.StrictMode>
    <LibraryManagementSystem />
  </React.StrictMode>
);
