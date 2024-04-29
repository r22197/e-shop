import React, { useState, useEffect } from 'react';
import { useAuth } from "../user/AuthProvider";
import { Link } from 'react-router-dom';
import { getCart } from "../data/CartApi";

const UserNavigation = () => {
    const { user, handleLogout } = useAuth();
    const [cartItemCount, setCartItemCount] = useState(0);

    useEffect(() => {
        updateCartItemCount();
    }, [cartItemCount]);



    const updateCartItemCount = () => {
        getCart()
            .then(data => {
                setCartItemCount(data.productsInCart.length);
            })
            .catch(error => {
                console.error("Error fetching cart data:", error);
            });
    };

    const handleLogoutClick = () => {
        handleLogout();
        setCartItemCount(0);
    };

    return (
        <div className="d-flex justify-content-end">
            <Link to="/cart" className="nav-link fw-bold me-4">
                {cartItemCount} <img src="https://cdn-icons-png.flaticon.com/512/4221/4221923.png" width="40px" alt="Cart"/>
            </Link>

            {user ? (

                <div className="d-flex align-items-center justify-content-end mb-2">
                    <small className="fw-bolder me-2">{user.sub}</small>
                    <div className="btn btn-outline-danger" onClick={handleLogoutClick}>Odhlásit se</div>
                </div>
            ) : (
                <div className="d-flex align-items-center justify-content-end mb-2">
                    <Link to="/login" className="btn btn-outline-primary">Přihlásit se</Link>
                </div>
            )}
        </div>
    );
};

export default UserNavigation;
