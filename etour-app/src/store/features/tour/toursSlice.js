import { createSlice } from "@reduxjs/toolkit";

const toursSlice = createSlice({
  name: "tours",
  initialState: [],
  reducers: {
    addInitialTours: (state, action) => {
      return action.payload;
    },
  },
});

export const { addInitialTours } = toursSlice.actions;

export default toursSlice;
