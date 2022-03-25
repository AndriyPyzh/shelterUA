DROP TABLE IF EXISTS OAUTH_CLIENT_DETAILS;

CREATE TABLE OAUTH_CLIENT_DETAILS
(
    CLIENT_ID               VARCHAR NOT NULL,
    CLIENT_SECRET           VARCHAR NOT NULL,
    WEB_SERVER_REDIRECT_URI VARCHAR DEFAULT NULL,
    SCOPE                   VARCHAR DEFAULT NULL,
    ACCESS_TOKEN_VALIDITY   INT     DEFAULT NULL,
    REFRESH_TOKEN_VALIDITY  INT     DEFAULT NULL,
    RESOURCE_IDS            VARCHAR DEFAULT NULL,
    AUTHORIZED_GRANT_TYPES  VARCHAR DEFAULT NULL,
    AUTHORITIES             VARCHAR DEFAULT NULL,
    ADDITIONAL_INFORMATION  VARCHAR DEFAULT NULL,
    AUTOAPPROVE             VARCHAR DEFAULT NULL,
    PRIMARY KEY (CLIENT_ID)
);

INSERT INTO oauth_client_details (client_id, client_secret, scope, authorized_grant_types)
VALUES ('web-client',
        '$2a$10$7xs4CvTqKmSKs0HgAmmlCOm55LM7OB8bNXpQrLll7i91sS82mufIS',
        'client',
        'client_credentials,password');