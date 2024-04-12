import React, { useState, useEffect } from "react";
import { getAllProducts } from "../utils/ApiFunctions";

const GetAllProducts = () => {
    const [products, setProducts] = useState([]);
    const [pageNumber, setPageNumber] = useState(0);
    const [pageSize, setPageSize] = useState(10);
    const [totalPages, setTotalPages] = useState(0);

    useEffect(() => {
        fetchProducts();
    }, [pageNumber, pageSize]);

    const fetchProducts = async () => {
        try {
            const response = await getAllProducts(pageNumber, pageSize);
            setProducts(response.content);
            setTotalPages(response.totalPages);
        } catch (error) {
            console.error("Error fetching products:", error);
        }
    };

    const goToPreviousPage = () => {
        if (pageNumber > 0) {
            setPageNumber(pageNumber - 1);
        }
    };

    const goToNextPage = () => {
        if (pageNumber < totalPages - 1) {
            setPageNumber(pageNumber + 1);
        }
    };

    return (
        <div>
            <h2>Product List</h2>
            {products.map((product) => (
                <div key={product.id}>
                    <h2>{product.name}</h2>
                    <p>{product.price} CZK</p>
                    <hr />
                </div>
            ))}
            <button onClick={goToPreviousPage}>Previous Page</button>
            <span>Page {pageNumber + 1} of {totalPages}</span>
            <button onClick={goToNextPage}>Next Page</button>
        </div>
    );
};

export default GetAllProducts;
