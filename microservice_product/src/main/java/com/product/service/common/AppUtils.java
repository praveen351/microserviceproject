package com.product.service.common;

public class AppUtils {
	public static String IFSC_CODE = "VOLI000584";
	public static long PRODUCT_ID_NUMER_SERIES = 8850200L;
	public static String ID_SAME = "ID_SAME";

	// For Exception
	public static String EXCEPTION_INTERNAL_SERVER = "Internal server error. Please try again after some time.";
	public static String EXCEPTION_RECORD_NOT_FOUND = "No Item Found.";
	public static String EXCEPTION_NOT_EXIST = "Product info does not exist. Please search again with correct product ID.";
	// To Update the customer info
	public static String RECORD_INSERT_SUCCESS = "Product record inserted successfully with product ID : ";

	public static String RECORD_INSERT_FAIL = "Unable to insert Record. Please try again after some time.";
	public static String RECORD_UPDATE_SUCCESS = "Successfully updated the product information.";
	public static String RECORD_UPDATE_FAIL = "Unable to updated the Product information. Please try again after some time.";
	public static String PRODUCT_ID_NOT_MATCH = "Invalid product Id. Please try again with correct product Id.";

	public static boolean isEmpty(String strValue) {
		if (strValue != null && strValue.equalsIgnoreCase("")) {
			return true;
		} else
			return false;
	}

}
