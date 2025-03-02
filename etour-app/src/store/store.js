import { combineReducers, configureStore } from "@reduxjs/toolkit";
import { userAPI } from "../services/UserService";
import logger from "redux-logger";
import loggedInUserSlice from "./features/user/loggedInUseSlice";
import { tourCategoryAPI } from "../services/TourCategoryService";
import { tourSubCategoryAPI } from "../services/TourSubCategoryService";
import tourCategorySlice from "./features/tour/tourCategorySlice";
import tourSubCategorySlice from "./features/tour/tourSubCategorySlice";
import toursSlice from "./features/tour/toursSlice";
import { tourAPI } from "../services/TourService";

const rootReducer = combineReducers({
  loggedInUserSlice: loggedInUserSlice.reducer,
  tourCategorySlice: tourCategorySlice.reducer,
  tourSubCategorySlice: tourSubCategorySlice.reducer,
  toursSlice: toursSlice.reducer,
  [userAPI.reducerPath]: userAPI.reducer,
  [tourCategoryAPI.reducerPath]: tourCategoryAPI.reducer,
  [tourSubCategoryAPI.reducerPath]: tourSubCategoryAPI.reducer,
  [tourAPI.reducerPath]: tourAPI.reducer
});

export const setupStore = () => {
  return configureStore({
    reducer: rootReducer,
    middleware: (getDefaultMiddleware) =>
      getDefaultMiddleware({ serializableCheck: false })
        .concat(userAPI.middleware)
        .concat(tourCategoryAPI.middleware)
        .concat(tourSubCategoryAPI.middleware)
        .concat(tourAPI.middleware)
        .concat(logger),
  });
};
