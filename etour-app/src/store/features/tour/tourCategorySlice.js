import { createSlice } from "@reduxjs/toolkit";

const tourCategorySlice = createSlice({
  name: "tourCategories",
  initialState: [],
  reducers: {
    addInitialTourCategories: (state, action) => {
      return action.payload;
    },
  },
});

export const { addInitialTourCategories } = tourCategorySlice.actions;

export default tourCategorySlice;
