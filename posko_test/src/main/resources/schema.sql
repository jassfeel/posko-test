CREATE TABLE IF NOT EXISTS batch_tbcr_inner_delng
(
    sys_se character varying(10) NOT NULL,
    accnut_ym character varying(6) NOT NULL,
    sn bigint NOT NULL DEFAULT 0,
    dta_ty character varying(3),
    cpr_code character varying(10),
    partn_cpr character varying(10),
    cpr_acnt_code character varying(20),
    cnnc_acnt_code character varying(20),
    cnnc_acnt_nm character varying(100),
    delng_de character varying(8),
    elcty_de character varying(8),
    chit_no character varying(100),
    cmpnsp_ky character varying(100),
    ext_key1 character varying(100),
    ext_key2 character varying(100),
    ext_key3 character varying(100),
    ext_key4 character varying(100),
    ext_key5 character varying(100),
    delng_crncy character varying(3),
    delng_amt numeric(19,2) DEFAULT 0,
    acntbk_amt numeric(19,2) DEFAULT 0,
    group_amt numeric(19,2) DEFAULT 0,
    suply_amount numeric(19,2) DEFAULT 0,
    sumry character varying(500),
    org_mtch_ty character varying(10),
    org_mtch_ky numeric(10,0),
    new_mtch_ty character varying(10),
    new_mtch_ky numeric(10,0),
    compare_ky character varying(100),
    mtch_sys character varying(10),
    mtch_ty character varying(10),
    mtch_ty_nm character varying(100),
    mtch_ky bigint DEFAULT 0,
    CONSTRAINT batch_tbcr_inner_delng_pkey PRIMARY KEY (sys_se, accnut_ym, sn)
);

COMMENT ON TABLE batch_tbcr_inner_delng
    IS '내부거래대사자료';

COMMENT ON COLUMN batch_tbcr_inner_delng.sys_se
    IS '시스템구분';

COMMENT ON COLUMN batch_tbcr_inner_delng.accnut_ym
    IS '회계연월';

COMMENT ON COLUMN batch_tbcr_inner_delng.sn
    IS '순번';

COMMENT ON COLUMN batch_tbcr_inner_delng.dta_ty
    IS '거래유형';

COMMENT ON COLUMN batch_tbcr_inner_delng.cpr_code
    IS '자기법인';

COMMENT ON COLUMN batch_tbcr_inner_delng.partn_cpr
    IS '상대법인';

COMMENT ON COLUMN batch_tbcr_inner_delng.cpr_acnt_code
    IS '법인계정코드';

COMMENT ON COLUMN batch_tbcr_inner_delng.cnnc_acnt_code
    IS '연결계정코드';

COMMENT ON COLUMN batch_tbcr_inner_delng.cnnc_acnt_nm
    IS '연결계정명';

COMMENT ON COLUMN batch_tbcr_inner_delng.delng_de
    IS '거래일자';

COMMENT ON COLUMN batch_tbcr_inner_delng.elcty_de
    IS '전기일자';

COMMENT ON COLUMN batch_tbcr_inner_delng.chit_no
    IS '법인전표번호';

COMMENT ON COLUMN batch_tbcr_inner_delng.cmpnsp_ky
    IS '대사 Key';

COMMENT ON COLUMN batch_tbcr_inner_delng.ext_key1
    IS '합산Key';

COMMENT ON COLUMN batch_tbcr_inner_delng.ext_key2
    IS 'INVOICE번호';

COMMENT ON COLUMN batch_tbcr_inner_delng.ext_key3
    IS 'LC번호';

COMMENT ON COLUMN batch_tbcr_inner_delng.ext_key4
    IS 'BL번호';

COMMENT ON COLUMN batch_tbcr_inner_delng.ext_key5
    IS '기타Key5';

COMMENT ON COLUMN batch_tbcr_inner_delng.delng_crncy
    IS '거래통화';

COMMENT ON COLUMN batch_tbcr_inner_delng.delng_amt
    IS '거래통화금액';

COMMENT ON COLUMN batch_tbcr_inner_delng.acntbk_amt
    IS '장부통화금액';

COMMENT ON COLUMN batch_tbcr_inner_delng.group_amt
    IS '그룹통화금액';

COMMENT ON COLUMN batch_tbcr_inner_delng.suply_amount
    IS '공급가액';

COMMENT ON COLUMN batch_tbcr_inner_delng.sumry
    IS '적요';

COMMENT ON COLUMN batch_tbcr_inner_delng.org_mtch_ty
    IS 'Org.일치유형';

COMMENT ON COLUMN batch_tbcr_inner_delng.org_mtch_ky
    IS 'Org.일치KEY';

COMMENT ON COLUMN batch_tbcr_inner_delng.new_mtch_ty
    IS 'New.일치유형';

COMMENT ON COLUMN batch_tbcr_inner_delng.new_mtch_ky
    IS 'New.일치KEY';

COMMENT ON COLUMN batch_tbcr_inner_delng.compare_ky
    IS '비교키(시스템)';

COMMENT ON COLUMN batch_tbcr_inner_delng.mtch_sys
    IS '일치시스템';

COMMENT ON COLUMN batch_tbcr_inner_delng.mtch_ty
    IS '일치유형';

COMMENT ON COLUMN batch_tbcr_inner_delng.mtch_ty_nm
    IS '일치유형명칭';

COMMENT ON COLUMN batch_tbcr_inner_delng.mtch_ky
    IS '일치KEY';

CREATE INDEX IF NOT EXISTS batch_tbcr_inner_delng_idx1
    ON batch_tbcr_inner_delng USING btree
    (sys_se ASC NULLS LAST, accnut_ym ASC NULLS LAST, cpr_code ASC NULLS LAST, dta_ty ASC NULLS LAST, partn_cpr ASC NULLS LAST);

CREATE INDEX IF NOT EXISTS batch_tbcr_inner_delng_idx2
    ON batch_tbcr_inner_delng USING btree
    (sys_se ASC NULLS LAST, accnut_ym ASC NULLS LAST, cpr_code ASC NULLS LAST, partn_cpr ASC NULLS LAST, compare_ky ASC NULLS LAST); 
    
    
    
    
CREATE TABLE IF NOT EXISTS tbcr_transaction_history
(
    session_id character varying(5) NOT NULL,
    cons_group character varying(10) NOT NULL,
    ledger character varying(3) NOT NULL,
    account_period character varying(6) NOT NULL,
    seq numeric NOT NULL,
    tran_type character varying(3),
    own_comp character varying(10),
    tran_comp character varying(10),
    comp_acct_code character varying(20),
    comp_prod_code character varying(20),
    recon_key character varying(100),
    tran_date character varying(8),
    tran_currency character varying(3),
    tran_amt numeric(17,2) DEFAULT 0,
    book_amt numeric(17,2) DEFAULT 0,
    group_amt numeric(17,2) DEFAULT 0,
    book_cost numeric(17,2) DEFAULT 0,
    document_no character varying(50),
    remark character varying(500),
    cons_acct_code character varying(10),
    cons_acct_name character varying(100),
    prod_code character varying(10),
    adjust_type character varying(3),
    adjust_remark character varying(255),
    if_account_period character varying(6),
    if_comp_code character varying(10),
    if_file_id character varying(3),
    if_source character varying(3),
    equal_check character varying(3),
    cons_own_comp character varying(10),
    cons_tran_comp character varying(10),
    invoice_no character varying(80),
    lc_no character varying(80),
    bl_no character varying(80),
    own_biz_no character varying(20),
    partner_biz_no character varying(20),
    acct_date character varying(8),
    matching_cause character varying(10),
    match_key numeric(10,0),
    CONSTRAINT tbcr_transaction_history_ky PRIMARY KEY (session_id, cons_group, ledger, account_period, seq)
); 


CREATE TABLE IF NOT EXISTS batch_tbcr_inner_delng_ai
(
    sys_se character varying(10) NOT NULL,
    accnut_ym character varying(6) NOT NULL,
    sn bigint NOT NULL DEFAULT 0,
    ai_key1 character varying(100) ,
    ai_key2 character varying(100) ,
    ai_key3 character varying(100) ,
    ai_key4 character varying(100) ,
    ai_key5 character varying(100) ,
    ai_key6 character varying(100) ,
    ai_key7 character varying(100) ,
    ai_key8 character varying(100) ,
    ai_key9 character varying(100) ,
    ai_key10 character varying(100) ,
    CONSTRAINT batch_tbcr_inner_delng_aikey PRIMARY KEY (sys_se, accnut_ym, sn)
);   


CREATE TABLE IF NOT EXISTS batch_user_job_status
(
    user_job_id character varying(128) COLLATE pg_catalog."default" NOT NULL,
    user_job_name character varying(200) COLLATE pg_catalog."default",
    start_time timestamp with time zone,
    end_time timestamp with time zone,
    status character varying(50) COLLATE pg_catalog."default",
    exit_code character varying(10) COLLATE pg_catalog."default",
    exit_message character varying(2000) COLLATE pg_catalog."default",
    CONSTRAINT batch_user_job_status_pkey PRIMARY KEY (user_job_id)
);

COMMENT ON TABLE batch_user_job_status
    IS 'User Job Status';
    
    
    
-- Table: users

-- DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS users
(
    user_id bigint NOT NULL DEFAULT nextval('users_user_id_seq'::regclass),
    activated boolean,
    nickname character varying(50) COLLATE pg_catalog."default",
    password character varying(100) COLLATE pg_catalog."default",
    username character varying(50) COLLATE pg_catalog."default",
    CONSTRAINT users_pkey PRIMARY KEY (user_id),
    CONSTRAINT uk_r43af9ap4edm43mmtq01oddj6 UNIQUE (username)
);

-- Table: authority

-- DROP TABLE IF EXISTS authority;

CREATE TABLE IF NOT EXISTS authority
(
    authority_name character varying(50) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT authority_pkey PRIMARY KEY (authority_name)
);

-- Table: user_authority

-- DROP TABLE IF EXISTS user_authority;

CREATE TABLE IF NOT EXISTS user_authority
(
    user_id bigint NOT NULL,
    authority_name character varying(50) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT user_authority_pkey PRIMARY KEY (user_id, authority_name),
    CONSTRAINT fk6ktglpl5mjosa283rvken2py5 FOREIGN KEY (authority_name)
        REFERENCES authority (authority_name) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT fkhi46vu7680y1hwvmnnuh4cybx FOREIGN KEY (user_id)
        REFERENCES users (user_id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

-- Table: sy_cmmn_code_mng

-- DROP TABLE IF EXISTS sy_cmmn_code_mng;

CREATE TABLE IF NOT EXISTS sy_cmmn_code_mng
(
    code_ty character varying(20) COLLATE pg_catalog."default" NOT NULL,
    cmmn_code character varying(20) COLLATE pg_catalog."default" NOT NULL,
    mng_iem_1 character varying(50) COLLATE pg_catalog."default",
    mng_iem_2 character varying(50) COLLATE pg_catalog."default",
    mng_iem_3 character varying(50) COLLATE pg_catalog."default",
    mng_iem_4 character varying(50) COLLATE pg_catalog."default",
    ordr bigint DEFAULT 0,
    use_at character varying(1) COLLATE pg_catalog."default",
    register character varying(20) COLLATE pg_catalog."default",
    input_de timestamp without time zone,
    updusr character varying(20) COLLATE pg_catalog."default",
    updt_de timestamp without time zone,
    CONSTRAINT sy_cmmn_code_mng_pkey PRIMARY KEY (code_ty, cmmn_code)
);

COMMENT ON TABLE sy_cmmn_code_mng
    IS 'SY_공통코드관리';

COMMENT ON COLUMN sy_cmmn_code_mng.code_ty
    IS '코드타입';

COMMENT ON COLUMN sy_cmmn_code_mng.cmmn_code
    IS '공통코드';

COMMENT ON COLUMN sy_cmmn_code_mng.mng_iem_1
    IS '관리항목1';

COMMENT ON COLUMN sy_cmmn_code_mng.mng_iem_2
    IS '관리항목2';

COMMENT ON COLUMN sy_cmmn_code_mng.mng_iem_3
    IS '관리항목3';

COMMENT ON COLUMN sy_cmmn_code_mng.mng_iem_4
    IS '관리항목4';

COMMENT ON COLUMN sy_cmmn_code_mng.ordr
    IS '순서';

COMMENT ON COLUMN sy_cmmn_code_mng.use_at
    IS '사용여부';

COMMENT ON COLUMN sy_cmmn_code_mng.register
    IS '입력자';

COMMENT ON COLUMN sy_cmmn_code_mng.input_de
    IS '입력일자';

COMMENT ON COLUMN sy_cmmn_code_mng.updusr
    IS '수정자';

COMMENT ON COLUMN sy_cmmn_code_mng.updt_de
    IS '수정일자';    
    

-- Table: sy_cmmn_code_mng_nls

-- DROP TABLE IF EXISTS sy_cmmn_code_mng_nls;

CREATE TABLE IF NOT EXISTS sy_cmmn_code_mng_nls
(
    code_ty character varying(20) COLLATE pg_catalog."default" NOT NULL,
    cmmn_code character varying(20) COLLATE pg_catalog."default" NOT NULL,
    lcal character varying(5) COLLATE pg_catalog."default" NOT NULL,
    cmmn_code_nm character varying(100) COLLATE pg_catalog."default",
    register character varying(20) COLLATE pg_catalog."default",
    input_de timestamp without time zone,
    updusr character varying(20) COLLATE pg_catalog."default",
    updt_de timestamp without time zone,
    CONSTRAINT sy_cmmn_code_mng_nls_pkey PRIMARY KEY (code_ty, cmmn_code, lcal),
    CONSTRAINT fk_s_mng_to_s_mng_nls FOREIGN KEY (code_ty, cmmn_code)
        REFERENCES sy_cmmn_code_mng (code_ty, cmmn_code) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
);

COMMENT ON TABLE sy_cmmn_code_mng_nls
    IS 'SY_공통코드관리_NLS';

COMMENT ON COLUMN sy_cmmn_code_mng_nls.code_ty
    IS '코드타입';

COMMENT ON COLUMN sy_cmmn_code_mng_nls.cmmn_code
    IS '공통코드';

COMMENT ON COLUMN sy_cmmn_code_mng_nls.lcal
    IS '로케일';

COMMENT ON COLUMN sy_cmmn_code_mng_nls.cmmn_code_nm
    IS '공통코드명';

COMMENT ON COLUMN sy_cmmn_code_mng_nls.register
    IS '입력자';

COMMENT ON COLUMN sy_cmmn_code_mng_nls.input_de
    IS '입력일자';

COMMENT ON COLUMN sy_cmmn_code_mng_nls.updusr
    IS '수정자';

COMMENT ON COLUMN sy_cmmn_code_mng_nls.updt_de
    IS '수정일자';    