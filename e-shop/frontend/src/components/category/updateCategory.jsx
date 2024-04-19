import React, { useState, useEffect } from "react";
import {Link, useParams} from "react-router-dom";
import { getAllCategories, getCategoryById, updateCategory } from "../data/CategoryApi";

const UpdateCategory = () => {
    const { id } = useParams();
    const [category, setCategory] = useState({
        name: "",
        parent: ""
    });

    const [categories, setCategories] = useState([]);
    const [loading, setLoading] = useState(true);
    const [successMessage, setSuccessMessage] = useState("");
    const [errorMessage, setErrorMessage] = useState("");

    useEffect(() => {
        fetchCategory();
        fetchCategories();
    }, [id]);

    const fetchCategory = async () => {
        try {
            const fetchedCategory = await getCategoryById(id);
            setCategory({
                ...fetchedCategory,
            });
        } catch (error) {
            console.error("Error fetching category:", error);
            setErrorMessage("Error fetching category: " + error.message);
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
        setCategory({
            ...category,
            [name]: value
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            console.log(category)
            await updateCategory(id, category);
            setSuccessMessage("Category has been updated successfully");
        } catch (error) {
            setErrorMessage("Error while updating category: " + error.message);
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
                    <input type="text" className="form-control" id="name" name="name" value={category.name}
                           onChange={handleInputChange} required/>
                </div>
                <div className="mb-3">
                    <label htmlFor="parent" className="form-label">Parent</label>
                    <select className="form-select" name="parent" value={category.parent ? category.parent : ''}
                            onChange={handleInputChange}>
                        <option value="">Select Category</option>
                        {categories.map((category) => (
                            <option key={category.id ? category.id : ''}
                                    value={category.id ? category.id : ''}>
                                {category.name ? category.name : ''}
                            </option>
                        ))}
                    </select>
                </div>
                <div className="mb-3">
                    <button type="submit" className="btn btn-primary">Update Product</button>
                </div>
            </form>
            <Link to="/admin/categories" className="btn btn-secondary">Cancel</Link>
        </div>
    );
};

export default UpdateCategory;
