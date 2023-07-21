package hr.fer.ppj.lab1;

public enum TokenType {
	/** za identifikatore **/
	IDN,
	/** za konstante **/
	BROJ, 
	/** za operator = **/
	OP_PRIDRUZI,
	/** za operator + **/
	OP_PLUS,
	/** za operator ­ **/
	OP_MINUS,
	/** za operator * **/
	OP_PUTA,
	/** za operator / **/
	OP_DIJELI,
	/** za lijevu zagradu (**/
	L_ZAGRADA,
	/** za desnu zagradu ) **/
	D_ZAGRADA,
	/** za kljucnu rijec za **/
	KR_ZA, 
	/** za kljucnu rijec od **/
	KR_OD,
	/** za kljucnu rijec do **/
	KR_DO, 
	/** za kljucnu rijec az **/
	KR_AZ,
	COMMENT,
	EOF
}
