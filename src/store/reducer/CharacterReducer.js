const initialState = {
    list: [],
    totalPages: 1
};

export const CharacterReducer = (state = initialState, action) => {

    switch (action.type) {

        case "GET_CHARACTERS":
            return {
                ...state,
                list: action.payload.list,            
                totalPages: action.payload.totalPages
            };

        default:
            return state;
    }
};