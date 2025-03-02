import { createSlice } from "@reduxjs/toolkit";

const tourSubCategorySlice = createSlice({
  name: "tourSubCategories",
  initialState: [],
  reducers: {
    addInitialTourSubCategories: (state, action) => {
      return action.payload;
    },
  },
});

export const { addInitialTourSubCategories } = tourSubCategorySlice.actions;

export default tourSubCategorySlice;
