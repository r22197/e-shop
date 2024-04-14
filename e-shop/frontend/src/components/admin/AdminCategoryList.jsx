import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { getAllCategories, deleteCategory } from "../utils/CategoryApi";

const AdminCategoryList = () => {
    const [categories, setCategories] = useState([]);
    const [errorMessage, setErrorMessage] = useState("");

    useEffect(() => {
        fetchCategories();
    }, []);

    const fetchCategories = async () => {
        try {
            const data = await getAllCategories();
            setCategories(data);
        } catch (error) {
            setErrorMessage("Error fetching categories: " + error.message);
        }
    };

    const handleDelete = async (categoryId) => {
        try {
            await deleteCategory(categoryId);
            fetchCategories();
        } catch (error) {
            console.error("Error deleting category:", error);
            setErrorMessage("Error deleting category: " + error.message);
        }
    };

    return (
        <div>
            {errorMessage && <div className="alert alert-danger">{errorMessage}</div>}
            <table className="table text-center mt-4">
                <thead>
                <tr>
                <th>Název</th>
                    <th>Nadřazená kategorie</th>
                    <th>Možnosti</th>
                </tr>
                </thead>
                <tbody>
                {categories.map((category) => (
                    <tr key={category.id}>
                        <td>{category.name}</td>
                        <td>{category.parent ? category.parent.name : "-"}</td>
                        <td>
                            <Link to={`/admin/categories/${category.id}`} className="btn btn-info me-2">Upravit</Link>
                            <button className="btn btn-danger" onClick={() => handleDelete(category.id)}>Smazat</button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
            <Link to="/admin/categories/new" className="btn btn-primary mb-3">Vytvořit novou kategorii</Link>

        </div>
    );
};

export default AdminCategoryList;