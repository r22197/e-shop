import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import {getAllCategories} from "../data/CategoryApi";


const Categories = () => {
    const [categories, setCategories] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const allCategories = await getAllCategories();
                setCategories(allCategories);
                setLoading(false);
            } catch (error) {
                setError("Error fetching categories: " + error.message);
                setLoading(false);
            }
        };

        fetchData();
    }, []);

    return (
        <div className="p-4">
            <ul className="nav d-flex flex-wrap">
                {categories.map((category) => (
                    <li key={category.id} className="category-square me-3 mb-3">
                        <Link to={`/category/${category.name}`}
                              className="nav-link text-black fw-bold">{category.name}</Link>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default Categories;