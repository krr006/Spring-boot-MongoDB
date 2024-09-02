//import React from 'react';
//import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
//import LoginPage from './components/LoginPage';
//import SignupPage from './components/SignUpPage';
//import Dashboard from "./components/DashboardPage";
//
//function App() {
//return (
//	<div className="App">
//	<Router>
//
//			<Routes>
//			<Route path="/" element={<LoginPage/>} />
//			<Route path="/register" element={ <SignupPage/>} />
//				<Route path = "/dashboard" element={<DashboardPage/>}/>
//			</Routes>
//
//	</Router>
//	</div>
//);
//}
//
//export default App;
//

//import React from 'react';
//import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
//import SignupPage from './components/SignUpPage';
//import LoginPage from './components/LoginPage';
//import DashboardPage from './components/DashboardPage';
//
//function App() {
//    return (
//        <Router>
//            <Routes>
//                <Route path="/register" element={<SignupPage />} />
//                <Route path="/" element={<LoginPage />} />
//                <Route path="/dashboard" element={<DashboardPage />} />
//            </Routes>
//        </Router>
//    );
//}
//
//export default App;

import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import LoginPage from './components/LoginPage';
import SignupPage from './components/SignUpPage';
import DashboardPage from './components/DashboardPage';
import { setAuthHeader } from './utils/auth';

function App() {
    setAuthHeader(); // Set JWT token in headers

    return (
        <Router>
            <Routes>
                <Route path="/" element={<LoginPage />} />
                <Route path="/register" element={<SignupPage />} />
                <Route path="/dashboard" element={<DashboardPage />} />
            </Routes>
        </Router>
    );
}

export default App;
