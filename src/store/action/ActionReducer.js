import axios from "axios";





export const getAllCharacters = (page) => {
    return async (dispatch) => {
        const response = await axios.get(`https://rickandmortyapi.com/api/character/?page=${page}`);
        console.log(response.data.results);

        dispatch({
            type: "GET_CHARACTERS",
             payload: {
                list: response.data.results,
                totalPages: response.data.info.pages   
            }
        })
    }

};