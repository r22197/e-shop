import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { getAllCategories } from "../data/CategoryApi";

const GetAllCategories = () => {
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
        <>

                <ul>
                    {categories.map((category) => (
                        <li key={category.id}>
                            <Link to={`/category/${category.id}`} className="custom-link">{category.name}</Link>
                        </li>
                    ))}
                </ul>
        </>
    );
};

export default GetAllCategories;
