import React, { useState, useEffect, useRef } from "react";
import { Link } from "react-router-dom";
import { getAllCategories } from "../data/CategoryApi";
import { getProductsContaining } from "../data/ProductApi";
import UserNavigation from "./UserNavigation";

const CategoriesNavigation = () => {
    const [categories, setCategories] = useState([]);
    const [searchValue, setSearchValue] = useState("");
    const [searchResults, setSearchResults] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const searchInputRef = useRef(null);

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

    const handleSearch = async () => {
        setLoading(true);
        try {
            const results = await getProductsContaining(searchValue);
            setSearchResults(results);
            setLoading(false);
        } catch (error) {
            setError("Error fetching search results: " + error.message);
            setLoading(false);
        }
    };

    useEffect(() => {
        handleSearch();
    }, [searchValue]);


    const handleWindowClick = (e) => {
        if (searchInputRef.current && !searchInputRef.current.contains(e.target)) {
            setSearchResults([]);
        }
    };

    useEffect(() => {
        window.addEventListener("click", handleWindowClick);
        return () => {
            window.removeEventListener("click", handleWindowClick);
        };
    }, []);

    return (
        <div className="p-3">
            <UserNavigation/>
            <div className="d-flex">
                <div>
                    <ul className="nav d-flex flex-wrap">
                        {categories.map((category) => (
                            <li key={category.id} className="category-square me-3 mb-3">
                                <Link to={`/category/${category.id}`} className="nav-link text-black fw-bold">
                                    {category.name}
                                </Link>
                            </li>
                        ))}
                    </ul>
                </div>
                <div className="flex-grow-1">
                    <div className="mb-3 d-flex justify-content-end">
                        <button className="btn btn-primary" onClick={handleSearch}>
                            Hledat
                        </button>
                        <input
                            ref={searchInputRef}
                            type="text"
                            className="form-control ms-2"
                            placeholder="Hledat..."
                            value={searchValue}
                            onChange={(e) => setSearchValue(e.target.value)}
                            style={{width: "250px"}}
                        />
                    </div>
                </div>
            </div>
            <hr className="my-0"/>
            <div className="mb-3 d-flex justify-content-end">
                <div className="search-results" style={{ position: "fixed", width: "250px", background: "#fff", zIndex: "999", boxShadow: "0 0 10px rgba(0, 0, 0, 0.1)" }}>
                    {searchResults.length > 0 && (
                        <ul className="nav d-flex flex-column">
                            {searchResults.map((result) => (
                                <li key={result.id} className="category-square mb-2">
                                    <Link to={`/product/${result.id}`} className="nav-link text-black fw-bold">
                                        {result.name}
                                    </Link>
                                </li>
                            ))}
                        </ul>
                    )}
                </div>
            </div>
        </div>
    );
};

export default CategoriesNavigation;
