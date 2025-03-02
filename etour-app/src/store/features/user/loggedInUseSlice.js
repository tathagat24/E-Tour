import { createSlice } from "@reduxjs/toolkit";

const loggedInUserSlice = createSlice({
  name: "loggedInUser",
  initialState: {
    loggedIn: false,
    userRole: "",
  },
  reducers: {
    setLoggedInUser: (state, action) => {
      return {
        ...state,
        loggedIn: true,
        userRole: action.payload.userRole,
      };
    },
    setLoggedOutUser: (state) => {
      return {
        ...state,
        loggedIn: false,
        userRole: "",
      };
    },
  },
});

export const { setLoggedInUser, setLoggedOutUser } = loggedInUserSlice.actions;

export default loggedInUserSlice;
