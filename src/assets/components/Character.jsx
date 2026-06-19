import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { getAllCharacters } from "../../store/action/ActionReducer";

const CharacterList = () => {

    const dispatch = useDispatch();
    const { list, totalPages } = useSelector(
        (state) => state.characters
    )
    const [currentPage, setCurrentPage] = useState(1);

    useEffect(() => {
        dispatch(getAllCharacters(currentPage));
    }, [currentPage]);

    return (
        <div>
            <h1>Characters</h1>

            <div className="container">

                {list.map((char) => (
                    <div className="col-md-10" key={char.id}>
                        <div className="card p-3 shadow-sm">
                            <p>Name: {char.name}</p>
                            <p>Status: {char.status}</p>
                            <p>Species: {char.species}</p>
                            <p>Origin Name: {char.origin?.name}</p>
                            <p>Location Name: {char.location?.name}</p>
                        </div>
                    </div>
                ))}

            </div>
            <div>

                <button
                    disabled={currentPage === 1}
                    onClick={() => setCurrentPage(currentPage - 1)}
                >
                    Previous
                </button>

                {[...Array(totalPages)].map((_, index) => (
                    <button
                        key={index}
                        onClick={() => setCurrentPage(index + 1)}
                    >
                        {index + 1}
                    </button>
                ))}

                <button
                    disabled={currentPage === totalPages}
                    onClick={() => setCurrentPage(currentPage + 1)}
                >
                    Next
                </button>

            </div>
        </div>
    );
};

export default CharacterList;