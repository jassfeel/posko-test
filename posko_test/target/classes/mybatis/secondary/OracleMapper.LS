<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="daeucna.mapper.secondary.batch.OracleMapper">
	
	<!-- 작업리스트 -->
	<select id="getOriginalData" parameterType="map" resultType="map">
		SELECT
		    A.SESSION_ID,
		    A.CONS_GROUP,
		    A.LEDGER,
		    A.ACCOUNT_PERIOD,
		    A.SEQ,
		    A.TRAN_TYPE,
		    A.OWN_COMP,
		    A.TRAN_COMP,
		    A.COMP_ACCT_CODE,
		    A.COMP_PROD_CODE,
		    A.RECON_KEY,
		    A.TRAN_DATE,
		    A.TRAN_CURRENCY,
		    A.TRAN_AMT,
		    A.BOOK_AMT,
		    A.GROUP_AMT,
		    A.BOOK_COST,
		    A.DOCUMENT_NO,
		    A.REMARK,
		    A.CONS_ACCT_CODE,
            B.CONS_ACCT_NAME_0 AS CONS_ACCT_NAME,
		    A.PROD_CODE,
		    A.ADJUST_TYPE,
		    A.ADJUST_REMARK,
		    A.IF_ACCOUNT_PERIOD,
		    A.IF_COMP_CODE,
		    A.IF_FILE_ID,
		    A.IF_SOURCE,
		    A.EQUAL_CHECK,
		    A.CONS_OWN_COMP,
		    A.CONS_TRAN_COMP,
		    A.INVOICE_NO,
		    A.LC_NO,
		    A.BL_NO,
		    A.OWN_BIZ_NO,
		    A.PARTNER_BIZ_NO,
		    A.ACCT_DATE
		FROM
		    TBCR_TRANSACTION_HISTORY A
            LEFT OUTER JOIN TBBC_ACCT_CODE_NLS B
            ON
                B.CONS_ACCT_CODE = A.CONS_ACCT_CODE
                AND B.CONS_COA = 'LS_COA'
                AND B.FS_TYPE = '1'
                AND TO_CHAR(SYSDATE, 'YYYYMM') BETWEEN B.START_YYMM AND B.END_YYMM
		WHERE	
			A.CONS_GROUP = #{sysSe}
			AND A.ACCOUNT_PERIOD = #{accnutYm}
	</select>
 
 	<select id="getMatchingInfo" parameterType="map" resultType="map">
		SELECT
		    A.MATCH_KEY,
		    A.MATCHING_CAUSE,
		    B.SEQ
		FROM TBCR_MATCHING A,
		    TBCR_MATCHING_DETAIL B
		WHERE
		    A.SESSION_ID = B.SESSION_ID
		    AND A.CONS_GROUP = B.CONS_GROUP
		    AND A.LEDGER = B.LEDGER
		    AND A.MATCH_KEY = B.MATCH_KEY
		    AND A.ACCOUNT_PERIOD = B.ACCOUNT_PERIOD
			AND A.CONS_GROUP = #{sysSe}
			AND A.ACCOUNT_PERIOD = #{accnutYm}
	</select>
	
	<update id="updateMatchingResult" parameterType="map">
		--결과 업데이트
	</update>		
	
</mapper>