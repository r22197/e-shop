import React, { useEffect, useState } from 'react';
import { useAuth } from "../home/AuthProvider";
import { Link } from 'react-router-dom';
import { getCart } from "../data/CartApi";

const UserDetailsNav = () => {
    const { user, handleLogout } = useAuth();
    const [cartData, setCartData] = useState(
        {
            id: null,
            price: null,
            productsInCart: []
        });

    useEffect(() => {
        const updateCartData = async () => {
            try {
                const data = await getCart();
                setCartData(data);
            } catch (error) {
                console.error("Error fetching cart data:", error);
            }
        };
        updateCartData();
    }, []);

    const cartItemCount = cartData.productsInCart.length;

    return (
        <div className="d-flex justify-content-end">
            <Link to="/cart" className="nav-link fw-bold me-4">
                {cartItemCount} <img src="https://cdn-icons-png.flaticon.com/512/4221/4221923.png" width="40px" alt="Cart"/></Link>

            {user ? (
                <div className="d-flex align-items-center justify-content-end mb-2">
                    <small className="fw-bolder me-2">{user.sub}</small>
                    <div className="btn btn-outline-danger" onClick={handleLogout}>Odhlásit se</div>
                </div>
            ) : (
                <div className="d-flex align-items-center justify-content-end mb-2">
                    <Link to="/login" className="btn btn-outline-primary">Přihlásit se</Link>
                </div>
            )}
        </div>
    );
};

export default UserDetailsNav;
