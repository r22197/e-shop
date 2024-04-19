import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { getAllProducts, deleteProduct } from "../data/ProductApi";
import { getCategoryById } from "../data/CategoryApi";

const AdminProductList = () => {
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
            const products = await Promise.all(response.content.map(async (product) => {
                const category = await getCategoryById(product.category);
                return { ...product, categoryName: category.name };
            }));
            setPageInfo({
                products: products,
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

    const handleDelete = async (productId) => {
        try {
            await deleteProduct(productId);
            fetchProducts();
        } catch (error) {
            console.error("Error deleting product:", error);
        }
    };

    return (
        <div>
            <table className="table text-center mt-4">
                <thead>
                <tr>
                    <th>Název</th>
                    <th>Kategorie</th>
                    <th>Popis</th>
                    <th>Cena</th>
                    <th>Možnosti</th>
                </tr>
                </thead>
                <tbody>
                {pageInfo.products.map((product) => (
                    <tr key={product.id}>
                        <td>{product.name}</td>
                        <td>{product.categoryName}</td>
                        <td>{product.description}</td>
                        <td>{product.price}</td>
                        <td>
                            <Link to={`/admin/products/update/${product.id}`} className="btn btn-info me-2">Upravit</Link>
                            <button className="btn btn-danger" onClick={() => handleDelete(product.id)}>Smazat</button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
            <div className="text-center mt-4">
                <button className="btn btn-primary me-2" onClick={goToPreviousPage}
                        disabled={pageInfo.pageNumber === 0}>Previous Page
                </button>
                <span className="align-self-center">Page {pageInfo.pageNumber + 1} of {pageInfo.totalPages}</span>
                <button className="btn btn-primary ms-2" onClick={goToNextPage}
                        disabled={pageInfo.pageNumber === pageInfo.totalPages - 1}>Next Page
                </button>
            </div>
            <Link to="/admin/products/new" className="btn btn-primary mt-3 mb-3">Vytvořit nový produkt</Link>

        </div>
    );
};

export default AdminProductList;
