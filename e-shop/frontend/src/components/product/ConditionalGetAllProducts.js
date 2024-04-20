import React from "react";
import { useParams } from "react-router-dom";
import GetAllProducts from "./getAllProducts";

const ConditionalGetAllProducts = () => {
    const { id } = useParams();

    return id ? <GetAllProducts categoryId={id} /> : null;
};

export default ConditionalGetAllProducts;
