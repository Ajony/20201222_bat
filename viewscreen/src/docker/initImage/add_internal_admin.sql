BEGIN;

SET @SVCMGT_ADMIN_NAME="vdiControllerAdmin";

SET @SERVICE_PROJECT_NAME="service";

SET @USER_ID="6a700e6b0f2a11ea9be50a580af0002a";

-- SELECT @SERVICE_PROJECT_ID := id from keystone.project where `name`=@SERVICE_PROJECT_NAME;
SET @SERVICE_PROJECT_ID = (SELECT id from keystone.project where `name`=@SERVICE_PROJECT_NAME);

-- SELECT @ADMIN_ROLE_ID := id from keystone.role where `name`='admin';
SET @ADMIN_ROLE_ID = (SELECT id from keystone.role where `name`='admin');

INSERT INTO keystone.user (id, extra, enabled, default_project_id, created_at, last_active_at, domain_id) VALUES (@USER_ID, '{\"internal\": true}', '1', NULL, '2019-04-08 05:52:03', NULL, 'default');

INSERT INTO keystone.local_user (user_id, domain_id, `name`, failed_auth_count, failed_auth_at) VALUES (@USER_ID, 'default', @SVCMGT_ADMIN_NAME, '0', NULL);

INSERT INTO keystone.password (local_user_id, password, expires_at, self_service, password_hash, created_at_int, expires_at_int, created_at) VALUES (LAST_INSERT_ID(), NULL, NULL, '0', '\$2b\$12\$luj84qbtDcQ4zQorEk6Fq.oBj2rqyPsfcbTqb/D4znzpmbGPLfsGW', '1554702722933981', NULL, '2019-04-08 05:52:03');

INSERT INTO keystone.assignment (`type`, actor_id, target_id, role_id, inherited) VALUES ('UserProject', @USER_ID, @SERVICE_PROJECT_ID, @ADMIN_ROLE_ID, '0');

COMMIT;