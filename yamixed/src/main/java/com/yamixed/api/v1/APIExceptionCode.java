/**
 * API 错误常量
 */
package com.yamixed.api.v1;

/**
 * @author xiaxue
 *
 */
public enum APIExceptionCode {
	
	
	Client{

		@Override
		public String getErrorCode(String code) {
			return "C" + code;
		}
	
	},
	
	Server{

		@Override
		public String getErrorCode(String code) {
			return "S" + code;
		}
		
	};

	public abstract String getErrorCode(String code);
	
	public static String PARAMS_MISSING = "4000";
	
	public static String PARSE_URL_ERROR = "5000";
	
	public static String CREATE_MIX_ERROR = "5001";
	
}
