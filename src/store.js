import { configureStore } from "@reduxjs/toolkit";
import { CharacterReducer } from "./store/reducer/CharacterReducer";

export const store = configureStore (
    {
        reducer:{ 
            
            characters : CharacterReducer

        }
    }
)