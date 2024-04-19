import React, { useState, useEffect } from "react";
import { getProductById, updateProduct } from "../data/ProductApi";
import { getAllCategories, getCategoryById } from "../data/CategoryApi";
import { Link, useParams } from "react-router-dom";

const UpdateProduct = () => {
    const { id } = useParams();
    const [product, setProduct] = useState({
        name: "",
        description: "",
        price: "",
        category: ""
    });

    const [categories, setCategories] = useState([]);
    const [loading, setLoading] = useState(true);
    const [successMessage, setSuccessMessage] = useState("");
    const [errorMessage, setErrorMessage] = useState("");

    useEffect(() => {
        fetchProduct();
        fetchCategories();
    }, [id]);

    const fetchProduct = async () => {
        try {
            const fetchedProduct = await getProductById(id);
            setProduct({
                ...fetchedProduct,
                //categoryId: fetchedProduct.category.id
            });
        } catch (error) {
            console.error("Error fetching product:", error);
        }
    };

    const fetchCategories = async () => {
        try {
            const data = await getAllCategories();
            setCategories(data);
            setLoading(false);
        } catch (error) {
            console.error("Error fetching categories:", error);
        }
    };

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setProduct({
            ...product,
            [name]: value
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            console.log(product)
            await updateProduct(id, product);
            setSuccessMessage("Product has been updated successfully");
        } catch (error) {
            setErrorMessage("Error updating product: " + error.message);
        }
    };

    if (loading) {
        return <div>Loading...</div>;
    }

    return (
        <div className="container">
            <h2>Edit Product</h2>
            {errorMessage && <div className="alert alert-danger">{errorMessage}</div>}
            {successMessage && <div className="alert alert-success">{successMessage}</div>}
            <form onSubmit={handleSubmit}>
                <div className="mb-3">
                    <label htmlFor="name" className="form-label">Name</label>
                    <input type="text" className="form-control" id="name" name="name" value={product.name} onChange={handleInputChange} required/>
                </div>
                <div className="mb-3">
                    <label htmlFor="description" className="form-label">Description</label>
                    <textarea className="form-control" id="description" name="description" value={product.description} onChange={handleInputChange} />
                </div>
                <div className="mb-3">
                    <label htmlFor="price" className="form-label">Price</label>
                    <input type="number" className="form-control" id="price" name="price" value={product.price} onChange={handleInputChange} />
                </div>
                <div className="mb-3">
                    <label htmlFor="category" className="form-label">Category</label>
                    <select className="form-select" name="category" value={product.category} onChange={handleInputChange}>
                        <option value="">Select Category</option>
                        {categories.map((category) => (
                            <option key={category.id} value={category.id}>
                                {category.name}
                            </option>
                        ))}
                    </select>
                </div>
                <div className="mb-3">
                    <button type="submit" className="btn btn-primary">Update Product</button>
                </div>
            </form>
            <Link to="/admin/products" className="btn btn-secondary">Cancel</Link>
        </div>
    );
};

export default UpdateProduct;
