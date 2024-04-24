import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { getCart, removeFromShoppingCart, updateProductCartQuantity } from "../data/CartApi";
import { getProductById } from "../data/ProductApi";

const GetCart = () => {
    const [updatedCartItems, setUpdatedCartItems] = useState([]);
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();

    useEffect(() => {
        fetchCart();
    }, []);

    const fetchCart = async () => {
        try {
            const token = localStorage.getItem('token');
            if (!token) {
                navigate('/login');
                return;
            }
            const cartData = await getCart();
            const updatedData = await Promise.all(cartData.productsInCart.map(async (item) => {
                const productInfo = await getProductById(item.productId);
                return { ...item, name: productInfo.name, price: productInfo.price };
            }));

            setUpdatedCartItems(updatedData);
            setLoading(false);
        } catch (error) {
            console.error("Error fetching cart data:", error);
        }
    };

    const handleUpdateQuantity = async (id, newQuantity) => {
        try {
            await updateProductCartQuantity(id, newQuantity);
            const updatedData = updatedCartItems.map(item => {
                if (item.id === id) {
                    return { ...item, quantity: item.quantity + newQuantity };
                }
                return item;
            });
            setUpdatedCartItems(updatedData);
        } catch (error) {
            console.error("Error updating item quantity:", error);
        }
    };

    const handleDeleteItem = async (itemId) => {
        try {
            await removeFromShoppingCart(itemId);
            console.log("Deleting item with ID:", itemId);
            const updatedData = updatedCartItems.filter(item => item.id !== itemId);
            setUpdatedCartItems(updatedData);
        } catch (error) {
            console.error("Error deleting item from cart:", error);
        }
    };

    const calculateTotalPriceForItem = (item) => {
        return item.price * item.quantity;
    };

    const calculateTotalPrice = () => {
        return updatedCartItems.reduce((total, item) => total + calculateTotalPriceForItem(item), 0);
    };

    if (loading) {
        return <div>Loading cart...</div>;
    }

    return (
        <div>
            <h2>Nákupní košík</h2>
            <table className="table text-center mt-4">
                <thead>
                <tr>
                    <th>Název</th>
                    <th>Quantity</th>
                    <th>Cena za kus</th>
                    <th>Celková cena</th>
                    <th>Možnosti</th>
                </tr>
                </thead>
                <tbody>
                {updatedCartItems.map((item) => (
                    <tr key={item.id}>
                        <td>{item.name}</td>
                        <td>
                            <div className="quantity-container">
                                <button className="btn btn-circle" onClick={() => handleUpdateQuantity(item.id, 1)}>+</button>
                                <span className="quantity-text">{item.quantity}</span>
                                <button className="btn btn-circle" onClick={() => handleUpdateQuantity(item.id, -1)}>-</button>
                            </div>
                        </td>
                        <td>{item.price} Kč</td>
                        <td>{calculateTotalPriceForItem(item)} Kč</td>
                        <td>
                            <button className="btn btn-danger" onClick={() => handleDeleteItem(item.id)}>Odebrat</button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
            <div className="text-center">
                <h4>Celková cena košíku: {calculateTotalPrice().toLocaleString()} Kč</h4>
            </div>
            <button onClick={fetchCart} className="btn btn-primary">Refresh Cart</button>
        </div>
    );
};

export default GetCart;
