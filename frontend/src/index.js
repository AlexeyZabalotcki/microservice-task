import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './components/App';
import reportWebVitals from './reportWebVitals';
import 'bootstrap/dist/css/bootstrap.min.css';
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import NewRequestPage from './pages/NewRequestPage';
import AuthContentPage from './pages/AuthContentPage'

const router = createBrowserRouter([
    {
      path: "/",
      element: <App />,
    },
    {
      path: "/new",
      element: <NewRequestPage />,
    },
    {
      path: "/currency",
      element: <AuthContentPage />,
    },
  ]);

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <RouterProvider router={router}>
        <App />
    </RouterProvider>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
