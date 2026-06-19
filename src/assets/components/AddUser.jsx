import axios from "axios";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
const AddUser =() =>
{
    
    const[formData,setFormData]= useState({
        name:"",
        email:"",
        phone:"",
        companyName:""
    }

    );
    
    const handleChange =(e) =>
    {
        setFormData({
            ...formData,
            [e.target.name]:e.target.value
        })
    }
    const handleSubmit =async (e) =>
    {
        e.preventDefault();
        const body={
            name: formData.name,
            email : formData.email,
            phone : formData.phone,
            company: {
                name: formData.companyName
            }
            

        }
        try{
            const resp=await axios.post("https://jsonplaceholder.typicode.com/users",body)
            console.log(resp.data)
            alert("User added succesfully")
        }
        catch(err)
        {
            console.log(err)
        }
        
    }
    return (
    <div className="container mt-4"
        style={{ maxWidth: "500px" }}
    >
        <div className="card p-4 shadow-sm">
            <h2 className="mb-4 text-center">Add User</h2>

            <form onSubmit={handleSubmit}>

                <div className="row mb-3 align-items-center">
                    <label className="col-4 col-form-label">
                        Name
                    </label>

                    <div className="col-8">
                        <input
                            type="text"
                            name="name"
                            className="form-control"
                            value={formData.name}
                            onChange={handleChange}
                            required
                        ></input>
                    </div>
                </div>

                <div className="row mb-3 align-items-center">
                    <label className="col-4 col-form-label">
                        Email
                    </label>

                    <div className="col-8">
                        <input
                            type="email"
                            name="email"
                            className="form-control"
                            value={formData.email}
                            onChange={handleChange}
                            required
                        ></input>
                    </div>
                </div>

                <div className="row mb-3 align-items-center">
                    <label className="col-4 col-form-label">
                        Phone
                    </label>

                    <div className="col-8">
                        <input
                            type="text"
                            name="phone"
                            className="form-control"
                            value={formData.phone}
                            onChange={handleChange}
                            required
                        ></input>
                    </div>
                </div>

                <div className="row mb-4 align-items-center">
                    <label className="col-4 col-form-label">
                        Company
                    </label>

                    <div className="col-8">
                        <input
                            type="text"
                            name="companyName"
                            className="form-control"
                            value={formData.companyName}
                            onChange={handleChange}
                            required
                        ></input>
                    </div>
                </div>

                <div className="text-center">
                    <button
                        type="submit"
                        className="btn btn-success"
                    >
                        Add User
                    </button>
                </div>

            </form>
        </div>
    </div>
);
    
}
export default AddUser;