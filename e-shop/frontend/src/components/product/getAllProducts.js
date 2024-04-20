import React, { useState, useEffect } from "react";
import { addToShoppingCart, getCart } from "../data/CartApi";
import { getProductsByCategory } from "../data/ProductApi";
import Slider from '@mui/material/Slider';

const GetAllProducts = ({ categoryId }) => {
    const [pageInfo, setPageInfo] = useState({
        products: [],
        totalPages: 1,
        pageNumber: 0
    });
    const [cartItems, setCartItems] = useState([]);
    const [sortBy, setSortBy] = useState("asc");
    const [priceRange, setPriceRange] = useState([0, 1600]);
    const pageSize = 10;

    const fetchProducts = async () => {
        try {
            const response = await getProductsByCategory(categoryId, pageInfo.pageNumber, pageSize, sortBy, priceRange[0], priceRange[1]);
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

    useEffect(() => {
        fetchProducts();
        fetchCartItems();
    }, [pageInfo.pageNumber, categoryId, sortBy, priceRange]);

    const handleChange = (event, newValue) => {
        setPriceRange(newValue);
    };

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

    const handleSortChange = () => {
        const newSortBy = sortBy === "asc" ? "desc" : "asc";
        setSortBy(newSortBy);
    };

    const isInCart = (productId) => {
        return cartItems.some(item => item.product === productId);
    };

    const addToCart = async (productId) => {
        try {
            await addToShoppingCart(productId);
            fetchCartItems();
        } catch (error) {
            throw new Error("Error while adding product: " + error.message)
        }
    };

    return (
        <div className="container">
            <div className="d-flex justify-content-between align-items-center mb-3">
                <div className=" align-items-center">
                    <Slider
                        getAriaLabel={() => 'Cenový rozsah'}
                        value={priceRange}
                        onChange={handleChange}
                        valueLabelDisplay="auto"
                        min={0}
                        max={1600}
                        step={50}
                    />
                    <p>{priceRange[0]} - {priceRange[1]} Kč</p>
                </div>
                <div className="d-flex">
                <button
                        className={`btn btn-primary me-2 ${sortBy === "asc" ? "disabled" : ""}`}
                        onClick={() => handleSortChange("asc")}
                        disabled={sortBy === "asc"}
                    >
                        Nejlevnější
                    </button>
                    <button
                        className={`btn btn-primary ${sortBy === "desc" ? "disabled" : ""}`}
                        onClick={() => handleSortChange("desc")}
                        disabled={sortBy === "desc"}
                    >
                        Nejdražší
                    </button>
                </div>
            </div>
            <div className="row row-cols-2 row-cols-md-3 g-2">
                {pageInfo.products.map((product) => (
                    <div key={product.id} className="col">
                        <div className="card h-100">
                            <div className="card-body">
                                <img
                                    src="https://xphotography.ca/wp-content/uploads/2023/11/The_Impact_of_Lifestyle_Photography_in_Modern_Product.jpg"
                                    className="card-img" alt="Product"
                                />
                                <h5 className="card-title text-center">{product.name}</h5>
                                <p className="card-text">{product.price} CZK</p>
                                <button className={`btn ${isInCart(product.id) ? "btn-success" : "btn-primary"}`}
                                        onClick={() => addToCart(product.id)}>
                                    {isInCart(product.id) ? "V košíku" : "Přidat do košíku"}
                                </button>
                            </div>
                        </div>
                    </div>
                ))}
            </div>
            <div className="d-flex justify-content-center mt-4">
                <button className="btn btn-primary me-2" onClick={goToPreviousPage}
                        disabled={pageInfo.pageNumber === 0}>Previous Page
                </button>
                <span className="align-self-center">Page {pageInfo.pageNumber + 1} of {pageInfo.totalPages}</span>
                <button className="btn btn-primary ms-2" onClick={goToNextPage}
                        disabled={pageInfo.pageNumber === pageInfo.totalPages - 1}>Next Page
                </button>
            </div>
            <div className="d-flex justify-content-center mt-2">

            </div>
        </div>
    );
};

export default GetAllProducts;
