CREATE TABLE IF NOT EXISTS account (
  id int NOT NULL AUTO_INCREMENT,
  email varchar(40) NOT NULL,
  username varchar(20) NOT NULL UNIQUE,
  hashed_password char(70) NOT NULL,
  birth_date datetime DEFAULT NULL,
  admin_role bit NULL DEFAULT '0',
  profile_picture varchar(255) DEFAULT 'user-default-64.png',
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS post (
   id  int NOT NULL AUTO_INCREMENT,
   account_id  int NOT NULL,
   title  varchar(50) NOT NULL,
   body  varchar(200) NOT NULL,
   owner_id  int DEFAULT NULL,
   created  datetime DEFAULT (now()),
   track_id varchar(255) DEFAULT '',
  PRIMARY KEY ( id )
);

CREATE TABLE IF NOT EXISTS post_like  (
   account_id  int NOT NULL,
   post_id  int NOT NULL,
   create_time  datetime DEFAULT (now()),
  PRIMARY KEY ( account_id , post_id )
);

CREATE TABLE IF NOT EXISTS refresh_token (
  id int NOT NULL AUTO_INCREMENT,
  user_id int NOT NULL,
  token varchar(512) NOT NULL,
  jwt_token varchar(512) DEFAULT NULL,
  created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  expires_at timestamp NOT NULL,
  is_revoked tinyint(1) DEFAULT '0',
  PRIMARY KEY (id),
  UNIQUE KEY token (token),
  KEY user_id (user_id),
  CONSTRAINT refresh_token_ibfk_1 FOREIGN KEY (user_id) REFERENCES account (id)
)
);
