import React, { useState, useEffect } from "react";
import { getAllProducts } from "../utils/ProductApi";

const GetAllProducts = () => {
    const [pageInfo, setPageInfo] = useState({
        products: [],
        totalPages: 1,
        pageNumber: 0
    });
    const pageSize = 10;

    useEffect(() => {
        fetchProducts();
    }, [pageInfo.pageNumber]);

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

    const addToCart = (productId) => {
        console.log(`Product with ID ${productId} added to cart`);
    };

    return (
        <div className="container">
            <h2>Product List</h2>
            <div className="row row-cols-1 row-cols-md-3 g-4">
                {pageInfo.products.map((product) => (
                    <div key={product.id} className="col">
                        <div className="card h-100">
                            <div className="card-body">
                                <h5 className="card-title">{product.name}</h5>
                                <p className="card-text">{product.price} CZK</p>
                                <button className="btn btn-primary" onClick={() => addToCart(product.id)}>Add to Cart</button>
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