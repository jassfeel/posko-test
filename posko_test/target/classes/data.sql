MERGE INTO authority m
USING (
	SELECT
		'ROLE_ADMIN' as authority_name
	) s
ON
	m.authority_name = s.authority_name
WHEN NOT MATCHED THEN 
	INSERT (authority_name) VALUES (s.authority_name);

MERGE INTO authority m
USING (
	SELECT
		'ROLE_USER' as authority_name
	) s
ON
	m.authority_name = s.authority_name
WHEN NOT MATCHED THEN 
	INSERT (authority_name) VALUES (s.authority_name);
	
MERGE INTO users m
USING (
	SELECT
		true as activated, 
		'sangkiham' as nickname, 
		'$2a$10$nyHQj.Nj2tID4UzIkd1/SuMeYwlKaaHT8Gi3Wgg2x/h9K9qLQciLO' as password, 
		'sangkiham' as username
	) s
ON
	m.username = s.username
WHEN MATCHED THEN 
	UPDATE SET
		nickname = s.nickname
		, activated = s.activated
		, password = s.password
WHEN NOT MATCHED THEN 
	INSERT (activated, nickname, password, username) VALUES (s.activated, s.nickname, s.password, s.username);

MERGE INTO user_authority m
USING (
	SELECT
		(SELECT user_id FROM users WHERE username = 'sangkiham') as user_id, 
		'ROLE_ADMIN' as authority_name 
	) s
ON
	m.user_id = s.user_id
	and m.authority_name = s.authority_name
WHEN NOT MATCHED THEN 
	INSERT (user_id, authority_name) VALUES (s.user_id, s.authority_name);
