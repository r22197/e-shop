import React, { useState, useEffect } from "react";
import { getAllProducts } from "../data/ProductApi";
import { addToShoppingCart, getCart } from "../data/CartApi";

const GetAllProducts = () => {
    const [pageInfo, setPageInfo] = useState({
        products: [],
        totalPages: 1,
        pageNumber: 0
    });
    const [cartItems, setCartItems] = useState([]);
    const pageSize = 10;

    useEffect(() => {
        fetchProducts();
        fetchCartItems();
    }, [pageInfo.pageNumber]);

    const goToPreviousPage = () => {
        if (pageInfo.pageNumber > 0) {
            setPageInfo(prevPageInfo => ({
                ...prevPageInfo,
                pageNumber: prevPageInfo.pageNumber - 1
            }));
        }
    };

    const goToNextPage = () => {
        if (pageInfo.pageNumber < pageInfo.totalPages - 1) {
            setPageInfo(prevPageInfo => ({
                ...prevPageInfo,
                pageNumber: prevPageInfo.pageNumber + 1
            }));
        }
    };


    const fetchProducts = async () => {
        try {
            const response = await getAllProducts(pageInfo.pageNumber, pageSize);
            setPageInfo({
                products: response.content,
                totalPages: response.totalPages,
                pageNumber: pageInfo.pageNumber
            });
        } catch (error) {
            console.error("Error fetching products:", error);
        }
    };

    const fetchCartItems = async () => {
        try {
            const cartData = await getCart();
            setCartItems(cartData);
        } catch (error) {
            console.error("Error fetching cart items:", error);
        }
    };

    const isInCart = (productId) => {
        return cartItems.some(item => item.product === productId);
    };

    const addToCart = async (productId) => {
        try {
            await addToShoppingCart(productId);
            fetchCartItems(); // Refresh cart items after adding product
        } catch (error) {
            throw new Error("Error while adding product: " + error.message)
        }
    };

    return (
        <div className="container">
            <h2>Product List</h2>
            <div className="row row-cols-2 row-cols-md-3 g-2">
                {pageInfo.products.map((product) => (
                    <div key={product.id} className="col">
                        <div className="card h-100">
                            <div className="card-body">
                                <img src="https://xphotography.ca/wp-content/uploads/2023/11/The_Impact_of_Lifestyle_Photography_in_Modern_Product.jpg" className="card-img" alt={product.name}/>
                                <h5 className="card-title text-center">{product.name}</h5>
                                <p className="card-text">{product.price} CZK</p>
                                <button className={`btn ${isInCart(product.id) ? "btn-success" : "btn-primary"}`} onClick={() => addToCart(product.id)}>
                                    {isInCart(product.id) ? "V košíku" : "Přidat do košíku"}
                                </button>
                            </div>
                        </div>
                    </div>
                ))}
            </div>
            <div className="d-flex justify-content-center mt-4">
                <button className="btn btn-primary me-2" onClick={goToPreviousPage} disabled={pageInfo.pageNumber === 0}>Previous Page</button>
                <span className="align-self-center">Page {pageInfo.pageNumber + 1} of {pageInfo.totalPages}</span>
                <button className="btn btn-primary ms-2" onClick={goToNextPage} disabled={pageInfo.pageNumber === pageInfo.totalPages - 1}>Next Page</button>
            </div>
        </div>
    );
};

export default GetAllProducts;
