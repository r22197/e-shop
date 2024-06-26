import React, { createContext, useState, useContext, useEffect } from "react";
import { jwtDecode } from "jwt-decode";
import { useNavigate } from "react-router-dom";

export const AuthContext = createContext({
    user: null,
    handleLogin: () => {},
    handleLogout: () => {}
});

export const AuthProvider = ({ children }) => {
    const [user, setUser] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        const token = localStorage.getItem("token");
        if (token) {
            const decodedUser = jwtDecode(token);
            setUser(decodedUser);
        }
    }, []);

    const handleLogin = async (token) => {
        const decodedUser = jwtDecode(token);
        localStorage.setItem("token", token);
        localStorage.setItem("sub", decodedUser.sub);
        localStorage.setItem("userRole", decodedUser.roles[0]);
        setUser(decodedUser);
    };

    const handleLogout = () => {
        localStorage.removeItem("token");
        localStorage.clear();
        navigate(`/`);
        setUser(null);
    };

    return (
        <AuthContext.Provider value={{ user, handleLogin, handleLogout }}>
            {children}
        </AuthContext.Provider>
    );
};

export const useAuth = () => {
    return useContext(AuthContext);
};
