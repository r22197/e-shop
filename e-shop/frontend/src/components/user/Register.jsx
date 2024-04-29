import React, { useState } from "react"
import { Link } from "react-router-dom"
import {registerUser} from "../data/UserApi";

const Register = () => {
    const [registration, setRegistration] = useState({
        email: "",
        password: "",
        role: "ROLE_CUSTOMER"
    })

    const [errorMessage, setErrorMessage] = useState("")
    const [successMessage, setSuccessMessage] = useState("")

    const handleInputChange = (e) => {
        setRegistration({ ...registration, [e.target.name]: e.target.value })
    }

    const handleRegistration = async (e) => {
        e.preventDefault()
        try {
            const result = await registerUser(registration)
            setSuccessMessage(result)
            setErrorMessage("")
            setRegistration({ email: "", password: "", role: "ROLE_CUSTOMER" })
        } catch (error) {
            setSuccessMessage("")
            setErrorMessage(`Registration error : ${error.message}`)
        }
    }

    return (
        <section className="container col-6">
            {errorMessage && <p className="alert alert-danger">{errorMessage}</p>}
            {successMessage && <p className="alert alert-success">{successMessage}</p>}
            <h2 className="text-center">Vytvořit účet</h2>
            <form onSubmit={handleRegistration}>
                <div className="row mb-3">
                    <div>
                        <input
                            placeholder="Email"
                            id="email"
                            name="email"
                            type="email"
                            className="form-control"
                            value={registration.email}
                            onChange={handleInputChange}
                        />
                    </div>
                </div>

                <div className="row mb-3">
                    <div>
                        <input
                            placeholder="Heslo"
                            type="password"
                            className="form-control"
                            id="password"
                            name="password"
                            value={registration.password}
                            onChange={handleInputChange}
                        />
                    </div>
                </div>
                <div className="row mb-3">
                    <div className="col">
                        <select
                            id="role"
                            name="role"
                            className="form-control text-center text-bg-light fw-bolder"
                            value={registration.role}
                            onChange={handleInputChange}
                        >
                            <option value="ROLE_CUSTOMER">Zákazník</option>
                            <option value="ROLE_ADMIN">Administrátor</option>
                        </select>
                    </div>
                </div>

                <div className="mb-3">
                    <button className="mb-4 d-block btn btn-primary" type="submit"
                            style={{height: '50px', width: '100%'}}>Zaregistrovat
                    </button>
                    <div className="text-center">
                        <p>Už máš účet? <Link to={"/login"}>Přihlásit se</Link></p>
                    </div>
                </div>
            </form>
        </section>
    )
}

export default Register