CREATE OR REPLACE PROCEDURE ADM.PR_CL_INVOICE_TBL (P_DATA_DATE   IN     VARCHAR2,
                                                    P_O_RESULT    OUT    VARCHAR2)
IS
   /******************************************************************************
     Author : liu.jianming
     Name   : PR_CL_INVOICE_TBL
     Functions : 贷款还款通知单信息表
     Purpose   :
     Revisions or Comments
     VER        DATE        AUTHOR           DESCRIPTION
   ---------  ----------  ---------------  ------------------------------------
     1.0        2012-9-3     liu.jianming     1. CREATED THIS PACKAGE.
  ******************************************************************************/
   V_STEP              VARCHAR2 (10 CHAR)  := '0';
   V_PROC_NAME         VARCHAR2 (100 CHAR) := 'PR_CL_INVOICE_TBL';
   V_TABLE_NAME        VARCHAR2 (30 CHAR)  := 'CL_INVOICE_TBL';
   V_TABLE_NAME_HIST   VARCHAR2 (30 CHAR)  := 'CL_INVOICE_TBL' || '_HIST';
   V_DATA_DATE         VARCHAR2 (8 CHAR);
   V_SUCCESS           VARCHAR2 (10 CHAR)  := ADM.UTIL.SUCCESS;
   V_FAILED            VARCHAR2 (10 CHAR)  := ADM.UTIL.FAILED;
   V_EDATE             VARCHAR2 (10 CHAR)  := ADM.UTIL.EDATE;
   V_START_TIME        VARCHAR2 (100 CHAR);
   V_END_TIME          VARCHAR2 (100 CHAR);
   V_CNT               NUMBER DEFAULT 0 ;
-------------------------------------------------------

BEGIN
   /*init*/
   V_STEP := '1';
   V_PROC_NAME := 'PR_CL_INVOICE_TBL';
   V_TABLE_NAME := 'CL_INVOICE_TBL';
   V_TABLE_NAME_HIST := 'CL_INVOICE_TBL' || '_HIST';
   P_O_RESULT := V_SUCCESS;
   V_DATA_DATE := P_DATA_DATE;
   V_START_TIME := TO_CHAR (SYSTIMESTAMP, 'YYYY-MM-DD HH24:MI:SS.FF');

   V_STEP := '2';

   --如果是系统上线日期，则清除数据表数据
   IF V_DATA_DATE = ADM.UTIL.GET_SYSTEM_START_DATE
   THEN
      --清除历史表数据
      EXECUTE IMMEDIATE   'TRUNCATE TABLE ADM.'
                       || V_TABLE_NAME_HIST
                       || ' DROP STORAGE ';
   END IF;

   --清除当前数据表
   V_STEP := '3';

   EXECUTE IMMEDIATE   'TRUNCATE TABLE ADM.'
                    || V_TABLE_NAME
                    || ' DROP STORAGE ';

   /*模型数据逻辑处理*/
   V_STEP := '4';
   --注掉驻马店项目增加的四级分类
   INSERT INTO ADM.CL_INVOICE_TBL (SDATE,
                                   EDATE,
                                   INVOICE_TRAN_NO,
                                   LOAN_KEY,
                                   DD_KEY,
                                   FEE_KEY,
                                   INVOICE_TYPE,
                                   CCY,
                                   BILLED_AMT,
                                   OUTSTANDING,
                                   TRAN_DATE,
                                   MATURITY_DATE,
                                   EXTEND_DATE,
                                   FINAL_SETTLE_DATE,
                                   BRANCH,
                                   STATUS,
                                   INT_FROM,
                                   INT_TO,
                                   INT_RATE)
                                 --  FOUR_CLASS)           --驻马店四级分类:按照贷款回收各期区分
                           SELECT  V_DATA_DATE,
                                   V_EDATE,
                                   CIT.INVOICE_TRAN_NO,
                                   CIT.LOAN_KEY,
                                   CIT.DD_KEY,
                                   CIT.FEE_KEY,
                                   CIT.INVOICE_TYPE,
                                   CIT.CCY,
                                   CIT.BILLED_AMT,
                                   CIT.OUTSTANDING,
                                   CIT.TRAN_DATE,
                                   CIT.DUE_DATE,
                                   CIT.GRACE_PERIOD_DATE EXTEND_DATE,
                                   CIT.FINAL_SETTLE_DATE,
                                   CL.BOOK_BRANCH,
                                   CIT.FULLY_SETTLED,
                                   CIT.INT_FROM,
                                   CIT.INT_TO,
                                   CIT.INT_RATE
    --                               CASE WHEN CIT.INVOICE_TYPE = 'PRI' AND (CIT.OUTSTANDING = 0 OR TO_DATE(V_DATA_DATE ,'YYYYMMDD') - CIT.DUE_DATE <= 0) THEN '01'
    --                                    WHEN CIT.INVOICE_TYPE = 'PRI' AND CP.PARAM_VALUE IS NOT NULL AND TO_DATE(V_DATA_DATE ,'YYYYMMDD') - CIT.DUE_DATE BETWEEN 1 AND 7 THEN '01'
    --                                    WHEN CIT.INVOICE_TYPE = 'PRI' AND CP.PARAM_VALUE IS NULL AND CIT.OUTSTANDING > 0 AND TO_DATE(V_DATA_DATE ,'YYYYMMDD') - CIT.DUE_DATE BETWEEN 1 AND 90 THEN '02'
    --                                    WHEN CIT.INVOICE_TYPE = 'PRI' AND CP.PARAM_VALUE IS NOT NULL AND CIT.OUTSTANDING > 0 AND TO_DATE(V_DATA_DATE ,'YYYYMMDD') - CIT.DUE_DATE BETWEEN 8 AND 97 THEN '02'
    --                                    WHEN CIT.INVOICE_TYPE = 'PRI' AND CP.PARAM_VALUE IS NULL AND CIT.OUTSTANDING > 0 AND TO_DATE(V_DATA_DATE ,'YYYYMMDD') - CIT.DUE_DATE > 90 THEN '03'
    --                                    WHEN CIT.INVOICE_TYPE = 'PRI' AND CP.PARAM_VALUE IS NOT NULL AND CIT.OUTSTANDING > 0 AND TO_DATE(V_DATA_DATE ,'YYYYMMDD') - CIT.DUE_DATE > 97 THEN '03'
    --                               END FOUR_CLASS                --驻马店四级分类:按照贷款回收各期区分:逾期贷款 对于助商贷和按揭类贷款，逾期七天以上 当期本金算逾期
                             FROM  ODS.SYM_SYM_CL_INVOICE_TBL CIT,  --13
                                   ODS.SYM_SYM_CL_LOAN CL           --13
                                   -- (SELECT PARAM_VALUE FROM ADM.COM_PARAMETER WHERE PARAM_ID = 'LOAN_TYPE_EXPEND') CP
                            WHERE  CIT.LOAN_KEY = CL.LOAN_KEY
                              AND  V_DATA_DATE BETWEEN CIT.SDATE AND CIT.EDATE
                              AND  V_DATA_DATE BETWEEN CL.SDATE AND CL.EDATE;
                                ---   AND CL.LOAN_SUB_TYPE = CP.PARAM_VALUE(+);

   V_CNT := SQL%ROWCOUNT;                                   --获取目标表记录数
   COMMIT;


   /*将当前数据加载到历史数据区*/
   V_STEP := 'N-1';
   ADM.PACK_COMM.PR_LOAD_DATA (V_DATA_DATE, V_TABLE_NAME, P_O_RESULT);

   /*处理结束，记录日志信息*/
   V_STEP := 'N';
   V_END_TIME := TO_CHAR (SYSTIMESTAMP, 'YYYY-MM-DD HH24:MI:SS.FF');
   ADM.ETL.WRITE_TRACE (V_PROC_NAME,V_START_TIME,V_END_TIME,P_O_RESULT,V_CNT);
EXCEPTION
   WHEN OTHERS
   THEN
      P_O_RESULT := V_FAILED;
      ADM.ETL.WRITE_LOG (V_PROC_NAME,V_STEP,'ERROR OCURR:' || SQLERRM,P_O_RESULT);
      RAISE_APPLICATION_ERROR (-20001, V_PROC_NAME);
END PR_CL_INVOICE_TBL;
/
